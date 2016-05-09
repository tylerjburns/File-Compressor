import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// -------------------------------------------------------------------------
/**
 *  Main class for compressing text files. Executing this class on a file will
 *  compress it.
 *  @args force : pass true to force compression even if the file will be bigger
 *  @args file_to_compress : pass the path of the file you wish to compress
 *
 *  @author Tyler Burns
 *  @author Sam Joynson
 *  @version May 8, 2016
 */
public class huff

{

    // ----------------------------------------------------------
    /**
     * Main method in huff. This is what executes actual file compression
     * @param args these are the args passed to the main method.
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException
    {

        FileInputStream text;
        String output;

        //create the input and output references and streams
        try
        {
            text = new FileInputStream(args[1]);
            output = args[1] + ".huff";
        }
        //catch the exception if necessary
        catch (FileNotFoundException e)
        {
            e = new FileNotFoundException("No such file found!");
            throw e;
        }
        //create a HuffModel instance and initialize it
        HuffModel testModel = new HuffModel();
        testModel.initialize(text);

        //show the counts of the HuffModel
        testModel.showCounts();
        System.out.println("--------------");

        //show the codings of the HuffModel
        testModel.showCodings();

        text = new FileInputStream(args[1]);

        boolean forceCompression = false;
        if(args[0].equals("" + true))
        {
            forceCompression = true;
        }

//        System.out.println(forceCompression);

        //write the compressed file of the input
        testModel.write(text, output, forceCompression);
        System.out.println("Compression process closed.");
    }
}