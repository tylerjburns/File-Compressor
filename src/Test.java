import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

public class Test
    extends TestCase
{
    private static int        count;
    private static HuffTree[] htarr;
    private static HuffTree[] htarr2;
    private static MinHeap    Hheap;


    public static void main(String[] args)
        throws IOException
    {
        FileInputStream text;
        try
        {
            text = new FileInputStream("C:\\Users\\Tibs\\Desktop\\test.txt");
        }
        catch (FileNotFoundException e)
        {
            e = new FileNotFoundException("No such file found!");
            throw e;
        }
        HuffModel testModel = new HuffModel();
        testModel.initialize(text);

    }
}
