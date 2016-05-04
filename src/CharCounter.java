import java.io.IOException;
import java.io.InputStream;

// -------------------------------------------------------------------------
/**
 *  Counts characters in file using different methods and different filters.
 *
 *  @author Tyler
 *  @version May 3, 2016
 */
public class CharCounter
    implements ICharCounter
{

    private int[] characters;

    // ----------------------------------------------------------
    /**
     * Create a new CharCounter object.
     * Initializes the characters array.
     */
    public CharCounter()
    {
        this.characters = new int[NUM_CHARS];
    }

    public int getCount(int ch)
    {
        return characters[ch];
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
        characters[NUM_CHARS-1] = 1;
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
