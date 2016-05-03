
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
      System.out.println(big);
      while ((inbits = bit.read(BITS_PER_WORD)) != -1)
      {
          String character = "";
          String code = "";
          // get the code computed in part II
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
         //out.write(1, (int)char);

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
        int magic = ((BitInputStream)in).read(BITS_PER_INT);
        if (magic != MAGIC_NUMBER){
            throw new IOException("magic number not right");
        }

        HuffInternalNode rootNode = (HuffInternalNode)rebuildTree((BitInputStream)in);
        HuffTree rebuiltTree = new HuffTree(rootNode.left(), rootNode.right(), 1);

        int bits;
        while (true)
        {
            bits = ((BitInputStream)in).read(1);
            if (bits == -1)
            {
                throw new IOException("unexpected end of input file");
            }
            else
            {
                if(bits == 1)
                {
                    char character = (char)in.read();
                    if(character == PSEUDO_EOF)
                    {
                        break;
                    }
                    out.write(character);
                }

                // use the zero/one value of the bit read
                // to traverse Huffman coding tree
                // if a leaf is reached, decode the character and print UNLESS
                // the character is pseudo-EOF, then decompression done

                if ( (bits & 1) == 0) // read a 0, go left in tree
                {

                }
                else // read a 1, go right in tree
                {

                }

                if (at leaf-node in tree)
                {
                    if (leaf-node stores pseudo-eof char)
                        break; // out of loop
                    else
                        write character stored in leaf-node
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
            return new HuffInternalNode(rebuildTree(in), rebuildTree(in), count);
        }
        else
        {
            char value = (char)in.read(9);
            if(value == PSEUDO_EOF)
            {
                return null;
            }
            return new HuffLeafNode(value, 1);
        }
    }
}
