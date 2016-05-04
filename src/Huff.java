import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Huff

{
    private static int        count;
    private static HuffTree[] htarr;
    private static HuffTree[] htarr2;
    private static MinHeap    Hheap;


    public static void main(String[] args)
        throws IOException
    {
        FileInputStream text;
        String output;
        try
        {
//            text = new FileInputStream("//Users//samjoynson//GitHub//File-Compressor//src//test.txt");
            text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\test.txt");
            output = "C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt";
        }
        catch (FileNotFoundException e)
        {
            e = new FileNotFoundException("No such file found!");
            throw e;
        }
        HuffModel testModel = new HuffModel();
        testModel.initialize(text);

        testModel.showCounts();
        System.out.println("--------------");
        testModel.showCodings();

        text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\test.txt");
        testModel.write(text, output, false);
        System.out.println("Done compressing.");

        for (int i = 0; i<testModel.codeWithChar.length; i++)
        {
            testModel.codeWithChar[i][1] = "";
        }

        BitInputStream compressedFile = null;
        BitOutputStream uncompressedFile = null;
        HuffModel unhuffer = new HuffModel();

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