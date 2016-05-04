
/**
 * Created by samjoynson on 4/9/16.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.*;

// -------------------------------------------------------------------------
/**
 * Creates a HuffModel object and implements methods used by this object.
 *
 * @author Tyler
 * @version May 4, 2016
 */

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Tibs
 * @version May 4, 2016
 */
public class HuffModel
    implements IHuffModel
{
    private HuffTree[]       htarr;
    private HuffTree[]       htarr2;
    private MinHeap          Hheap;
    private static int       encodeCount;
    private HuffTree         charTree;
    public static String[][] codeWithChar;
    private CharCounter      charCount;
    private Stack<String>    stack = new Stack<String>();


    // ----------------------------------------------------------
    /**
     * Create a new HuffModel object.
     */
    public HuffModel()
    {

    }


    // ----------------------------------------------------------
    /**
     * Creates the tree from the heap passed to it.
     *
     * @return tmp3 the tree created
     */
    public HuffTree buildTree()
    {
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = (HuffTree)Hheap.removemin();
            tmp2 = (HuffTree)Hheap.removemin();
            tmp3 = new HuffTree(
                tmp1.root(),
                tmp2.root(),
                tmp1.weight() + tmp2.weight());
            Hheap.insert(tmp3); // Return new tree to heap
        }
        return tmp3; // Return the tree
    }


    public void showCodings()
    {
        preOrderTrav(charTree.root());
        for (int i = 0; i < codeWithChar.length; i++)
        {
            System.out.println(codeWithChar[i][0] + ": " + codeWithChar[i][1]);
        }

    }


    // ----------------------------------------------------------
    /**
     * Uses a preorder traversal to fill codeWithChar array with the encodings
     * for each character read in from the file.
     *
     * @param node
     *            the node to start traversing from
     */
    public void preOrderTrav(HuffBaseNode node)
    {
        if (node == null)
        {
            return;
        }

        // If the node is a leaf node, then get the stack content; this is the
        // encoding of that character. Then put it into the array at the
        // position
        // that corresponds to the character it is encoding.
        if (node.isLeaf())
        {
            String z = new String();
// System.out.println(stack.size());
            for (int i = 0; i < stack.size(); i++)
            {
                z = z + stack.get(i);
            }

// encodings[encodeCount] = z;

            for (int i = 0; i < codeWithChar.length; i++)
            {
                if (codeWithChar[i][0]
                    .equals("" + ((HuffLeafNode)node).element()))
                {
                    codeWithChar[i][1] = z;
                }
            }

// codeWithChar[encodeCount][0] = "" + ((HuffLeafNode)node).element();
// codeWithChar[encodeCount][1] = z;
// encodeCount++;

            stack.pop();
        }
        // If it's not a leaf, then push a 0 onto the stack for going left
        // and a 1 onto the stack for going right.
        // Once this recursion unwinds, pop the stack once when finished this
        // level of the tree.
        else
        {
            stack.push("0");
            preOrderTrav(((HuffInternalNode)node).left());
            stack.push("1");
            preOrderTrav(((HuffInternalNode)node).right());
            if (stack.size() > 0)
            {
                stack.pop();
            }
        }

        // Use a stack to keep track of where

    }


    public void showCounts()
    {
        for (int i = 0; i < NUM_CHARS; i++)
        {
            System.out.println((char)i + ": " + charCount.getCount(i));
        }
    }


    // ----------------------------------------------------------
    /**
     * Initialize method takes care of all of the steps necessary to use the
     * HuffModel object. It initializes all fields and fills the arrays
     * necessary to use the HuffModel. It also builds the tree from the heap and
     * closes the BitInputStream.
     */
    public void initialize(InputStream stream)
        throws IOException
    {
        BitInputStream bits = new BitInputStream(stream);
        int count = 0;
        charCount = new CharCounter();
        charCount.countAll(bits);
        htarr = new HuffTree[NUM_CHARS];
        for (int i = 0; i < NUM_CHARS; i++)
        {
            htarr[i] = new HuffTree((char)(i), charCount.getCount(i));
            if (htarr[i].weight() != 0)
            {
                count++;
            }
        }
        int countUnique = count;
        htarr2 = new HuffTree[countUnique];
        count = 0;
        for (int i = 0; i < NUM_CHARS; i++)
        {
            if (htarr[i].weight() != 0)
            {
                htarr2[count] = htarr[i];
                count++;
            }
        }
        Hheap = new MinHeap(htarr2, countUnique, countUnique);
        codeWithChar = new String[countUnique][2];
        count = 0;
        for (int i = 0; i < NUM_CHARS; i++)
        {
            htarr[i] = new HuffTree((char)(i), charCount.getCount(i));
            if (htarr[i].weight() != 0)
            {
                codeWithChar[count][0] = "" + (char)i;
                count++;
            }
        }

        charTree = buildTree();
        bits.close();
    }


    // ----------------------------------------------------------
    /**
     * This is the write method, which writes the compressed file.
     */
    public void write(InputStream stream, String file, boolean force)
        throws IOException
    {
        //create the output stream
        BitOutputStream out = new BitOutputStream(file);
        //write the magic number to file directly
        out.write(BITS_PER_INT, MAGIC_NUMBER);
        //traverse the tree and write the node and element structure in bits.
        traverseAndWrite(charTree.root(), out);
// out.write(1, 1);
// out.write(9, PSEUDO_EOF);

        //create the int that will hold the incoming bits and the input stream
        int inbits = 0;
        BitInputStream bit = new BitInputStream(stream);

        /*While the next byte does not return -1, filling the codeWithChar array
        *by reading the bits and comparing them with the character position
        *in the codeWithChar array, then putting the corresponding encoding
        *in the right index.
        */
        while ((inbits = bit.read(BITS_PER_WORD)) != -1)
        {
            String character = "";
            String code = "";
            // get the code computed in part II
            // by searching the 2D array we created to hold the pairs

            // Why doesn't this print the PSEUDO_EOF value on its own?
            for (int i = 0; i < codeWithChar.length; i++)
            {
                character = "" + ((char)inbits);
                if (character.equals(codeWithChar[i][0]))
                {
                    code = codeWithChar[i][1];
                }
            }

            // convert that code into an array of chars using .toCharArray()
            // method
            char[] codeArray = code.toCharArray();
            // go through the array of char and write each char using 1 bit
            for (int i = 0; i < codeArray.length; i++)
            {
                out.write(1, codeArray[i]);
            }
            // System.out.println("Wrote all the bits.");
        }

        // Here, outside the while loop, after it has printed all of the
        // encodings
        // create a string version of the PSEUDO_EOF value, and search for it in
        // the 2D array. When found, get the corresponding encoding from the
        // array and write it to the file.

        // This also doesn't fix the "unexpected end of input file" error. Why?
        String PEOF = "" + ((char)PSEUDO_EOF);
        System.out.println("About to write PEOF encoding.");
        for (int i = 0; i < codeWithChar.length; i++)
        {
            if (PEOF.equals(codeWithChar[i][0]))
            {
                String code = codeWithChar[i][1];
                char[] codeArray = code.toCharArray();
                for (int x = 0; x < codeArray.length; x++)
                {
                    out.write(1, codeArray[x]);
                    // System.out.print((int)codeArray[x]);
                }
                System.out.println("Wrote PEOF encoding.");
            }
        }
        out.close();
        bit.close();
    }


    // ----------------------------------------------------------
    /**
     * This uses a preorder traversal to move through the tree and write the
     * corresponding node structure with a 1 for a leaf node and a 0 for an
     * internal node.
     * @param node is the starting node for traversal
     * @param out is the output stream to write to
     */
    public void traverseAndWrite(HuffBaseNode node, BitOutputStream out)
    {
        if (node != null)
        {
            if (node.isLeaf())
            {
                // when leaf node
                out.write(1, 1); //write a 1
                //write the actual element
                out.write(9, ((HuffLeafNode)node).element());
                return;
            }
            else
            {
                // when internal node
                out.write(1, 0); //write a 0
                //recursively call the method to move through the tree
                traverseAndWrite(((HuffInternalNode)node).left(), out);
                traverseAndWrite(((HuffInternalNode)node).right(), out);
            }
        }
        return;
    }


    public void uncompress(InputStream in, OutputStream out)
        throws IOException
    {
        //create the input and output streams
        BitInputStream bis = (BitInputStream)in;
        BitOutputStream bos = (BitOutputStream)out;

        //make sure the magic number is present
        int magic = bis.read(BITS_PER_INT);
        //If it's not there, then throw an exception
        if (magic != MAGIC_NUMBER)
        {
            throw new IOException("magic number not right");
        }

        //Rebuild the tree from the file using the rebuild tree method
        HuffInternalNode treeNode =
            (HuffInternalNode)rebuildTree((BitInputStream)in);

        //THIS IS SOLELY FOR TESTING PURPOSES.
        //REMOVE WHEN DONE TESTING.
        //This is supposed to do a preorder traversal of the rebuilt tree, then
        //print the encodings, but the encodings keep coming out terribly wrong.
        preOrderTrav(treeNode);
        for (int i = 0; i < codeWithChar.length; i++)
        {
            System.out.println(codeWithChar[i][0] + ": " + codeWithChar[i][1]);
        }

// HuffTree rebuiltTree = new HuffTree(treeNode);
// rebuiltTree.traverseAndPrint(rebuiltTree.root());

        //Set tmp equal to the returned tree
        HuffBaseNode tmp = treeNode;

        int bits;

        //while true(since we don't know where the end of the file is),
        //traverse the tree, following the file's bit instructions and writing
        //each character to the newly uncompressed file as you go.
        while (true)
        {
            //fill bits with next bit
            bits = bis.read(1);
            //if you get -1, throw an exception that the end of the file has
            //been reached unexpectedly
            if (bits == -1)
            {
                throw new IOException("unexpected end of input file");
            }
            else
            {
                if ((bits & 1) == 0) // read a 0, go left in tree
                {
                    tmp = ((HuffInternalNode)tmp).left();
                }
                else // read a 1, go right in tree
                {
                    tmp = ((HuffInternalNode)tmp).right();
                }
                // if it's a leaf, then grab the character from the leaf node
                // check if it's the PSEUDO_EOF character
                // if it's not, write the character
                // if it is, break out of the while loop
                if (tmp.isLeaf())
                {
                    int character = ((HuffLeafNode)tmp).element();
                    if (character == PSEUDO_EOF)
                    {
                        break;
                    }
                    bos.write(BITS_PER_WORD, character);
                    tmp = treeNode;
                }
            }
        }
        in.close();
        out.close();
    }


    // ----------------------------------------------------------
    /**
     * Rebuilds the tree from the file.
     * @param in the input stream
     * @return the final tree
     * @throws IOException
     */
    public HuffBaseNode rebuildTree(BitInputStream in)
        throws IOException
    {
        //If you read a 0, then return a new Internal Node with recursive calls
        //for the two parameter nodes.
        if (in.read(1) == 0)
        {
            return new HuffInternalNode(rebuildTree(in), rebuildTree(in), 0);
        }
        //If you read a 1, return a leaf node, using the next 9 bits as the
        //value of the node.
        else if (in.read(1) == 1)
        {
            int value = in.read(9);
            return new HuffLeafNode((char)value, 0);
        }
        //If neither a 1 nor a 0 is found, return null.
        else
        {
            return null;
        }
    }
}
