
/**
 * Created by samjoynson on 4/9/16.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array
import java.util.*;

public class HuffModel
    implements IHuffModel
{
    private  int        count;
    private  HuffTree[] htarr;
    private  HuffTree[] htarr2;
    private  MinHeap    Hheap;
    Stack<String> stack = new Stack<String>();


    public HuffTree buildTree()
    {
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = (HuffTree)Hheap.removemin();
            tmp2 = (HuffTree) Hheap.removemin();
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
        htarr = new HuffTree[256];
        count = 0;
        for (int i = 0; i < 256; i++)
        {
            htarr[i] = new HuffTree((char)(i), CharCounter.characters[i]);
            // comment in or out if you want count of all chars or not
            if (htarr[i].weight() != 0)
            {
                count++;
                // System.out.println((char)i + ": " + CharCounter.characters[i]);
            }
        }

        htarr2 = new HuffTree[count];
        count = 0;
        for (int i = 0; i < 256; i++)
        {
            if (htarr[i].weight() != 0)
            {
                htarr2[count] = htarr[i];
                count++;
            }
        }

        Hheap = new MinHeap(htarr2, count, count);
        HuffTree charTree = buildTree();

        preOrderTrav(charTree.root());


    }


    public void preOrderTrav(HuffBaseNode node)
    {

        if (node == null)
        {
            return;
        }

        if (node.isLeaf())
        {
            String z = new String();
            for (int i=0; i<stack.size();i++)
            {
                z = z + stack.get(i);
            }

            System.out.println(((HuffLeafNode)node).element() + " " +  z);

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

        //Use a stack to keep track of where

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


    }


    public void write(InputStream stream, File file, boolean force)
    {
        // TODO Auto-generated method stub

    }


    public void uncompress(InputStream in, OutputStream out)
    {
        // TODO Auto-generated method stub

    }
}
