import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// -------------------------------------------------------------------------
/**
 *  This is the main class that uncompresses files. Calling this will uncompress
 *  a file.
 *  @args file_to_uncompress : pass the path of the file you want to uncompress
 *
 *  @author Tyler Burns
 *  @author Sam Joynson
 *  @version May 8, 2016
 */
public class unhuff
{

    // ----------------------------------------------------------
    /**
     * Main method of main class unhuff. This is what executes the uncompressing
     * @param args : these are the args passed to the class when it's called
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        BitInputStream compressedFile = null;
        BitOutputStream uncompressedFile = null;
        HuffModel unhuffer = new HuffModel();

        try
        {
            compressedFile = new BitInputStream(new FileInputStream(args[0]));
            unhuffer.initialize(compressedFile);

            compressedFile = new BitInputStream(new FileInputStream(args[0]));
            uncompressedFile = new BitOutputStream(args[0] + ".unhuff");
            unhuffer.uncompress(compressedFile, uncompressedFile);

            compressedFile.close();
            uncompressedFile.close();
        }
        catch (FileNotFoundException e)
        {
            e = new FileNotFoundException("No such file found!");
            throw e;
        }
    }
}
