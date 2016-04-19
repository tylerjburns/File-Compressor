
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


    public void showCodings()
    {
        // TODO Auto-generated method stub

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
