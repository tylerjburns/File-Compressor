
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
            // comment in or out if you want count of all chars or not
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


    public void uncompress(InputStream in, OutputStream out)
    {
        // TODO Auto-generated method stub

    }
}
