import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

public class Test extends TestCase
{
    public static void main(String [] args) throws IOException
    {
        FileInputStream text;
        try
        {
            text = new FileInputStream("test.txt");
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
