/**
 * Created by samjoynson on 4/9/16.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
public class HuffModel implements IHuffModel
{
    CharCount counter;

    public void showCodings()
    {
        // TODO Auto-generated method stub

    }

    public void showCounts()
    {
        // TODO Auto-generated method stub

    }

    public void initialize(InputStream stream) throws IOException
    {
        int[] charCount = new int[256];
        BitInputStream input = new BitInputStream(stream);
        try
        {
            while(input.read() != -1)
            {
                charCount[input.read()]++;
            }
            System.out.println("Finished reading document!");
            input.close();
        }
        catch (IOException e)
        {
            input.close();
            throw e;
        }
        for (int i = 0; i<charCount.length; i++)
        {
            System.out.println((char)i + ": " + charCount[i]);
        }
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
