import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test

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

        testModel.showCodings();

        text = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\test.txt");
        testModel.write(text, output, false);
    }
}
