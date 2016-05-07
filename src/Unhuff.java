import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Unhuff
{

    public static void main(String[] args) throws IOException
    {
        BitInputStream compressedFile = null;
        BitOutputStream uncompressedFile = null;
        HuffModel unhuffer = new HuffModel();

        try
        {
            compressedFile = new BitInputStream(new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt"));
            uncompressedFile = new BitOutputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\" + args[0]);
            unhuffer.initialize(compressedFile);

            compressedFile = new BitInputStream(new FileInputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\output.txt"));
            uncompressedFile = new BitOutputStream("C:\\Users\\Tibs\\CSE17\\File Compressor\\src\\" + args[0]);
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
