import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

public class Test
    extends TestCase
{
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
        for (int i = 0; i < 256; i++)
        {
            //comment in or out if you want count of all chars or not
            if(CharCounter.characters[i] != 0)
                System.out.println((char)i + ": " + CharCounter.characters[i]);
        }
    }
}
