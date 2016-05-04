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
            text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\test.txt");
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

        text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\test.txt");

        //write the compressed file of the input
        testModel.write(text, output, false);
        System.out.println("Done compressing.");

        //TESTING PIECE, REMOVE WHEN FINISHED
        //UNHUFF OPERATION IS INSIDE OF HUFF CLASS BELOW AS WELL
        //Clear the array to see what's happening between Huff and Unhuff
        //One of them is broken because the codings look reasonable after
        //huffing, then when you unhuff, none of the letters are filled except
        //a and a is filled with gibberish. Some really long encoding.
        for (int i = 0; i<testModel.codeWithChar.length; i++)
        {
            testModel.codeWithChar[i][1] = "";
        }

        //Create the new streams and HuffModel for decompression
        BitInputStream compressedFile = null;
        BitOutputStream uncompressedFile = null;
        HuffModel unhuffer = new HuffModel();

        //Initialize the streams and then uncompress the file
        try
        {
//            compressedFile = new BitInputStream(new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt"));
//            uncompressedFile = new BitOutputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\uncompressed.txt");
//            unhuffer.initialize(compressedFile);

            compressedFile = new BitInputStream(new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt"));
            uncompressedFile = new BitOutputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\uncompressed.txt");
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