
/**
 * Created by samjoynson on 4/9/16.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class HuffModel
    implements IHuffModel
{
    private int        count;
    private HuffTree[] htarr;
    private HuffTree[] htarr2;
    private MinHeap    Hheap;
    private String[]   encodings;
    private int        encodeCount = 0;
    Stack<String>      stack       = new Stack<String>();
    private HuffTree charTree;
    private String[][] codeWithChar;
    private HuffInternalNode rootNode;



    public HuffTree buildTree()
    {
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = (HuffTree)Hheap.removemin();
            tmp2 = (HuffTree)Hheap.removemin();
            tmp3 = new HuffTree(
                tmp1.root(),
                tmp2.root(),
                tmp1.weight() + tmp2.weight());
            Hheap.insert(tmp3); // Return new tree to heap
        }
        return tmp3; // Return the tree
    }


    public void showCodings()
    {
        htarr = new HuffTree[257];
        count = 0;
        for (int i = 0; i < 257; i++)
        {
            htarr[i] = new HuffTree((char)(i), CharCounter.characters[i]);
            if (htarr[i].weight() != 0)
            {
                count++;
                // System.out.println((char)i + ": " +
                // CharCounter.characters[i]);
            }
        }

        htarr2 = new HuffTree[count];
        count = 0;
        for (int i = 0; i < 257; i++)
        {
            if (htarr[i].weight() != 0)
            {
                htarr2[count] = htarr[i];
                count++;
            }
        }

        Hheap = new MinHeap(htarr2, count, count);
        charTree = buildTree();

        preOrderTrav(charTree.root());

    }


    public void preOrderTrav(HuffBaseNode node)
    {
        encodings = new String[count];
        codeWithChar = new String[count][2];
        if (node == null)
        {
            return;
        }

        if (node.isLeaf())
        {
            String z = new String();
            for (int i = 0; i < stack.size(); i++)
            {
                z = z + stack.get(i);
            }

            System.out.println(((HuffLeafNode)node).element() + " " + z);
            encodings[encodeCount] = z;
            codeWithChar[encodeCount][0] = "" + ((HuffLeafNode)node).element();
            codeWithChar[encodeCount][1] = z;
            encodeCount++;
            stack.pop();
        }
        else
        {
            stack.push("0");
            preOrderTrav(((HuffInternalNode)node).left());
            stack.push("1");
            preOrderTrav(((HuffInternalNode)node).right());
            if (stack.size() > 0)
            {
                stack.pop();
            }
        }

        // Use a stack to keep track of where

    }


    public void showCounts()
    {
        for (int i = 0; i < CharCounter.characters.length; i++)
        {
            System.out.println((char)i + ": " + CharCounter.characters[i]);
        }
    }


    public void initialize(InputStream stream)
        throws IOException
    {
        BitInputStream bits = new BitInputStream(stream);
        CharCounter charCount = new CharCounter();
        charCount.countAll(bits);
        bits.close();
    }


    public void write(InputStream stream, String file, boolean force) throws IOException
    {
      BitOutputStream out = new BitOutputStream(file);
      out.write(BITS_PER_INT, MAGIC_NUMBER);
      traverseAndWrite(charTree.root(), out);
      out.write(1, 1);
      out.write(9, PSEUDO_EOF);

      int inbits = 0;
      BitInputStream bit = new BitInputStream(stream);
      int big = bit.available();

      while ((inbits = bit.read(BITS_PER_WORD)) != -1)
      {
          String character = "";
          String code = "";
          // get the code computed in part II
          //by searching the 2D array we created to hold the pairs

          //Why doesn't this print the PSEUDO_EOF value on its own?
          for (int i = 0; i<codeWithChar.length; i++)
          {
              character = "" + ((char)inbits);
              if (character.equals(codeWithChar[i][0]))
              {
                  code = codeWithChar[i][1];
              }
          }


         // convert that code into an array of chars using .toCharArray() method
          char[] codeArray = code.toCharArray();
         //go through the array of char and write each char (cast as int) using 1 bit
          for (int i = 0; i<codeArray.length; i++)
          {
              out.write(1, (int)codeArray[i]);
          }
      }

      //Here, outside the while loop, after it has printed all of the encodings
      //create a string version of the PSEUDO_EOF value, and search for it in
      //the 2D array. When found, get the corresponding encoding from the array
      //and write it to the file.

      //This also doesn't fix the "unexpected end of input file" error. Why?
      String PEOF = "" + ((char)PSEUDO_EOF);
      for (int i = 0; i<codeWithChar.length; i++)
      {
          if (PEOF.equals(codeWithChar[i][0]))
          {
              String code = codeWithChar[i][1];
              char[] codeArray = code.toCharArray();
              for (int x = 0; x<codeArray.length; x++)
              {
                  out.write(1, (int)codeArray[x]);
              }
          }
      }
      out.close();
      bit.close();
    }

    public void traverseAndWrite(HuffBaseNode node, BitOutputStream out)
    {
        if (node != null)
        {
            if(node.isLeaf())
            {
                //when leaf node
                out.write(1, 1);
                out.write(9, ((HuffLeafNode)node).element());
                return;
            }
            else
            {
                //when internal node
                out.write(1, 0);
                traverseAndWrite(((HuffInternalNode)node).left(), out);
                traverseAndWrite(((HuffInternalNode)node).right(), out);
            }
        }
        return;
    }


    public void uncompress(InputStream in, OutputStream out) throws IOException
    {
        BitInputStream bis = (BitInputStream)in;
        BitOutputStream bos = (BitOutputStream)out;


        int magic = bis.read(BITS_PER_INT);
        if (magic != MAGIC_NUMBER){
            throw new IOException("magic number not right");
        }

        HuffInternalNode treeNode = (HuffInternalNode)rebuildTree((BitInputStream)in);
        HuffBaseNode tmp = treeNode;

        int bits;
        while (true)
        {
            bits = bis.read(1);
            if (bits == -1)
            {
                throw new IOException("unexpected end of input file");
            }
            else
            {
                if ( (bits & 1) == 0) // read a 0, go left in tree
                {
                    tmp = ((HuffInternalNode)tmp).left();
                }
                else // read a 1, go right in tree
                {
                    tmp = ((HuffInternalNode)tmp).right();
                }
                // if it's a leaf, then grab the character from the leaf node
                //check if it's the PSEUDO_EOF character
                //if it's not, write the character
                //if it is, break out of the while loop
                if (tmp.isLeaf())
                {
                    int character = ((HuffLeafNode)tmp).element();
                    if(character == PSEUDO_EOF)
                    {
                        break;
                    }
                    bos.write(BITS_PER_WORD, character);
                    tmp = treeNode;
                }
            }
        }
        in.close();
        out.close();
    }

    public HuffBaseNode rebuildTree(BitInputStream in) throws IOException
    {
        if(in.read(1) == 0)
        {
            return new HuffInternalNode(rebuildTree(in), rebuildTree(in), 0);
        }
        else if(in.read(1) == 1)
        {
            int value = in.read(9);
            if(value == PSEUDO_EOF)
            {
                return null;
            }
            return new HuffLeafNode((char)value, 0);
        }
        else
        {
            return null;
        }
    }
}
