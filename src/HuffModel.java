
/**
 * Created by samjoynson on 4/9/16.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HuffModel
    implements IHuffModel
{
    private static int        count;
    private static HuffTree[] htarr;
    private static HuffTree[] htarr2;
    private static MinHeap    Hheap;

    static HuffTree buildTree()
    {
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = Hheap.removemin();
            tmp2 = Hheap.removemin();
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
        String[] encodings = new String[count];
        for (int i = 0; i<count; i++)
        {
            encodings[i] =
        }
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
