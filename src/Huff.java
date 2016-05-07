import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Huff

{

    // ----------------------------------------------------------
    /**
     * Main method in Huff. This is what executes actual file compression
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
//            text = new FileInputStream("//Users//samjoynson//GitHub//File-Compressor//src//test.txt");
            text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\" + args[1]);
            output = "C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt";
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

        text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\" + args[1]);

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