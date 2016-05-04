/** A Huffman coding tree */
class HuffTree
    implements Comparable
{
    private HuffBaseNode root;


    /** Constructors */
    HuffTree(char el, int wt)
    {
        root = new HuffLeafNode(el, wt);
    }


    HuffTree(HuffBaseNode l, HuffBaseNode r, int wt)
    {
        root = new HuffInternalNode(l, r, wt);
    }


    HuffTree(HuffInternalNode rootNode)
    {
        this.root = rootNode;
    }


    HuffBaseNode root()
    {
        return root;
    }


    public int weight() // Weight of tree is weight of root
    {
        return root.weight();
    }


    public int compareTo(Object t)
    {
        HuffTree that = (HuffTree)t;
        if (root.weight() < that.weight())
            return -1;
        else if (root.weight() == that.weight())
            return 0;
        else
            return 1;
    }

//    public void traverseAndPrint(HuffBaseNode huffBaseNode)
//    {
//        if (huffBaseNode != null)
//        {
//            if(huffBaseNode.isLeaf())
//            {
//                //when leaf node
//                System.out.print("1");
//                return;
//            }
//            else
//            {
//                //when internal node
//                System.out.print("0");
//                traverseAndPrint(((HuffInternalNode)huffBaseNode).left());
//                traverseAndPrint(((HuffInternalNode)huffBaseNode).right());
//            }
//        }
//        System.out.println();
//        return;
//    }
}
