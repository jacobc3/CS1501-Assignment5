public class AdaptiveHuffmanTree{
     Node[] rlo;          //list of nodes in the tree in reverse-level order rlo[255], rlo[254], ...
     Node NYT;            //special node representing unseen characters
     Node[] characters;   //pointers to the nodes in the tree holding each character (if null then the character has not been seen)
     int size;            //number of Nodes in the tree

     public AdaptiveHuffmanTree(){
          characters = new Node[256];
          rlo = new Node[256];

          for(int k=0; k<256; k++){
              characters[k] = null;
          }

          NYT = new Node(0);
          NYT.place = 255;               //NYT is going to be placed at the root of the tree
          NYT.weight = 0;                //NYT has weight 0
          NYT.label = '0';               //NYT is always a leftchild
          NYT.leaf = true;               //NYT is always a leaf
          rlo[NYT.place] = NYT;          //place NYT at the root of the tree
          NYT.parent = null;             //the root of the tree has no parent
          size = 1;
     }

   /*
    * Update the adaptive Huffman tree after inserting character c. This is the UPDATE procedure discussed in class.
    */
   public void update(char c){
   }


   /*
    * Swap Nodes u and v in the reverse-level ordered array. Note: Node u is at index u.place!
    */
   private void swap(Node u, Node v){
   }

   /*
    * Return true if character c has been seen, otherwise, return false.
    */
   public boolean characterInTree(int  c){
	return false;
   }


   /*
    * Return true if the reverse-level order traversal is a monotonically decreasing sequence, otherwise, return false.
    */
   private boolean satisfiesSiblingProperty(){
	return false;
   }


   /*
    * Return the sequence of labels (characters) from the root to the NYT node.
    */
   public StringBuffer getCodeWordForNYT(){
       return null;   //change to the correct return value.
   }


   /*
    * Return the sequence of labels (characters from the root to the Node for character c.
    */
   public StringBuffer getCodeWordFor(char c){
       return null;    //change to the correct return value.
   }


   /*
    * return the reference to the root node.
    */
   public Node root(){
      return null;   //change to the correct return value.
   }


   public int size(){
      return size;
   }


   /*
    * I've provided this to help debug your tree.
    */
   public String toString(){
       String result = "[";

       for(int k=255; k>=0 ; k--){
           if(rlo[k] != null)
                result += "(" + rlo[k].weight + "," + rlo[k].place + "," + rlo[k].character + ") ";
       }

       return result + "]\nsize = " + size;
   }
}
