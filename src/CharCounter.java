import java.io.IOException;
import java.io.InputStream;

public class CharCounter
    implements ICharCounter
{
    public static int[] characters = new int[257];


    public int getCount(int ch)
    {
        // TODO Auto-generated method stub
        return 0;
    }


    public int countAll(InputStream stream)
        throws IOException
    {
        BitInputStream bitStream = new BitInputStream(stream);
        int inbits = 0;
        while ((inbits = bitStream.read(BITS_PER_WORD)) != -1)
        {
            add(inbits);
        }
        characters[256] = PSEUDO_EOF;
        bitStream.close();
        return 0; // unused return
    }


    public void add(int i)
    {
        characters[i]++;
    }


    public void set(int i, int value)
    {
        // TODO Auto-generated method stub

    }


    public void clear()
    {
        // TODO Auto-generated method stub

    }

}
