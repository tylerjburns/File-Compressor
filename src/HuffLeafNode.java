/** Huffman tree node: Leaf class */
class HuffLeafNode implements HuffBaseNode {
  private char element;      // Element for this node
  private int weight;        // Weight for this node

  /** Constructor */
  HuffLeafNode(char el, int wt)
    { element = el; weight = wt; }

  /** @return The element value */
  char element() { return element; }

  /** @return The weight */
  public int weight() { return weight; }

  /** Return true */
  public boolean isLeaf() { return true; }
}