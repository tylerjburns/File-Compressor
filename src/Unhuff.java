import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Unhuff
{
    private static FileInputStream compressedFile;
    private static FileOutputStream uncompressedFile;
    private static HuffModel unhuffer;


    public static void main(String[] args) throws IOException
    {
        try
        {
            compressedFile = new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt");
        }
        catch (FileNotFoundException e)
        {
            e = new FileNotFoundException("No such file found!");
            throw e;
        }

        try
        {
            uncompressedFile = new FileOutputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\uncompressed.txt");
        }
        catch (FileNotFoundException f)
        {
            f = new FileNotFoundException("No such file found!");
            throw f;
        }

        unhuffer.uncompress(compressedFile, uncompressedFile);
    }
}
