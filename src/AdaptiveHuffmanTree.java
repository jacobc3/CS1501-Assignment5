import java.io.IOException;

public class AdaptiveHuffmanTree {
	Node[] rlo; // list of nodes in the tree in reverse-level order rlo[255],
				// rlo[254], ...
	Node NYT; // special node representing unseen characters
	Node[] characters; // pointers to the nodes in the tree holding each
						// character (if null then the character has not been
						// seen)
	int size; // number of Nodes in the tree
	int upBound = 255;

	public AdaptiveHuffmanTree() {
		characters = new Node[256];
		rlo = new Node[256];

		for (int k = 0; k < 256; k++) {
			characters[k] = null;
		}

		NYT = new Node(0);
		NYT.place = 255; // NYT is going to be placed at the root of the tree
		NYT.weight = 0; // NYT has weight 0
		NYT.label = '0'; // NYT is always a left child
		NYT.leaf = true; // NYT is always a leaf
		rlo[NYT.place] = NYT; // place NYT at the root of the tree
		NYT.parent = null; // the root of the tree has no parent
		size = 1;
	}

	/*
	 * Update the adaptive Huffman tree after inserting character c. This is the
	 * UPDATE procedure discussed in class.
	 */
	String output = "";
	public void update(char c) {
		size++;
		//StdOut.print(c+"\t");
		if (characterInTree((int) c)) {
			updateExist(c);
			StdOut.print(""+this.getCodeWordFor(c));
			output +=this.getCodeWordFor(c);
			//StdOut.print("\t");
		} else {
			updateNew(c);
			if(size == 2){
				//StdOut.println(((Integer)(int)c).byteValue());
				output+=this.print8bit(c);
				
			} else {
				StdOut.print(""+this.getCodeWordForNYT());
				output += this.getCodeWordForNYT();
				//StdOut.print("\t");
				output += this.print8bit(c);
			}
		}
		//StdOut.println();
	}
	
	public String print8bit(char c){
		 String b = Integer.toBinaryString((int)c);
			if (b.length() < 8) {
	            b = "000000000".substring(0, 8 - b.length()).concat(b);
	        } else {
	            b = b.substring(b.length() - 8);
	        }

	        System.out.print(b);
	        return b;
	}
	public void updateExist(char c) {
		//StdOut.println("found exist " + c);
		Node n = characters[(int) c];
		Node currentNode = n;
		// is this max ordered node in its weight class?
		while (currentNode != this.root()) {
			int stop = currentNode.place;
			for (int i = upBound; i > stop; i--) {
				//StdOut.println("i=" + i);
				if (rlo[i].character != currentNode.character/* && rlo[i].leaf == true */
						&& rlo[i].weight == currentNode.weight) {// not max order					
					if(rlo[i].leaf){ //not a parent
						//StdOut.println("swapping " + rlo[i].character + " with "
						//		+ currentNode.character);
						swap(rlo[i], currentNode);
					}
				}
			}
			currentNode.weight++;
			//updateParentWeight(n);
			currentNode = currentNode.parent;
		}
		this.root().weight++;
		/*
		 * n.weight++; updateParentWeight(n); if (!satisfiesSiblingProperty()) {
		 * // for each same level pair of node, if they not good, swap them
		 * StdOut.println("swap "); for (int i = 0; i <= upBound; i++) { Node x
		 * = rlo[i]; if (x != null && x.label == '0') { Node right =
		 * getRightNode(n); if (right != null)
		 * StdOut.println("x is "+x.character+" neibor is " + right.character);
		 * if (right != null && x.weight > right.weight) { this.swap(x, right);
		 * } } } }
		 */
	}

	/*
	 * private Node getRightNode(Node n) { for (Node v : characters) { if (v !=
	 * null) { StdOut.println("" + v.character); if (v.character != n.character)
	 * { if (v.parent != null && n.parent != null) { if (v.parent ==
	 * n.parent.parent) { return v; } } } } } return null; }
	 */
	public void updateNew(char c) {
		//StdOut.println("found new char " + c);
		// StdOut.println(" "+ NYT.weight);
		// give birth to NYT
		Node new0 = new Node(1); //new top node
		new0.label = NYT.label;
		new0.parent = NYT.parent;
		new0.place = NYT.place;
		rlo[NYT.place] = new0;
		new0.weight = 0;

		Node new1 = new Node(1, c); // right leaf
		new1.leaf = true;
		new1.label = '1';
		new1.parent = new0;
		new1.place = NYT.place - 1;
		new1.weight = 1;
		rlo[NYT.place - 1] = new1;

		NYT.parent = new0; //left leaf
		NYT.label = '0';
		NYT.place = NYT.place - 2;
		rlo[NYT.place] = NYT;

		characters[(int) c] = new1;
		updateParentWeight(new1);
		// output
	}

	public void updateParentWeight(Node n) {
		//StdOut.println("updating parent");
		Node parent = n.parent;
		while (parent != null) {
			// StdOut.println("parent is "+parent.character);
			parent.weight++;
			parent = parent.parent;
		}
	}

	/*
	 * Swap Nodes u and v in the reverse-level ordered array. Note: Node u is at
	 * index u.place!
	 */
	private void swap(Node u, Node v) {
		int tempPlace = v.place;
		v.place = u.place;
		u.place = tempPlace;

		Node tempParent = v.parent;
		v.parent = u.parent;
		u.parent = tempParent;
		
		char tempLabel = v.label;
		v.label = u.label;
		u.label = tempLabel;

		rlo[u.place] = u;
		rlo[v.place] = v;

	}

	/*
	 * Return true if character c has been seen, otherwise, return false.
	 */
	public boolean characterInTree(int c) {
		/*
		 * for(Node n : rlo){ if((int)n.getChar() == c){ return true; } }
		 */
		if (characters[c] != null) {
			return true;
		}
		return false;
	}

	/*
	 * Return true if the reverse-level order traversal is a monotonically
	 * decreasing sequence, otherwise, return false.
	 */
	private boolean satisfiesSiblingProperty() {
		Node root = this.root();
		for (int i = 0; i <= upBound; i++) {
			if (rlo[i] != null && rlo[i - 1] != null) {
				if (rlo[i].weight > rlo[i - 1].weight) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Return the sequence of labels (characters) from the root to the NYT node.
	 */
	public StringBuffer getCodeWordForNYT() {
		StringBuffer a = new StringBuffer();
		Node n = this.NYT;
		//StdOut.print("NYTprinting ");
		while(n.parent!=this.root()){
			//StdOut.print(n.place+" label "+n.label);
			a.append(n.label);
			n = n.parent;
		}
		a = a.reverse();
		//StdOut.print("NYTfinished ");
		return a; // change to the correct return value.
	}

	/*
	 * Return the sequence of labels (characters from the root to the Node for
	 * character c.
	 */
	public StringBuffer getCodeWordFor(char c) {
		StringBuffer a = new StringBuffer();
		Node n = characters[c];
		while(n!=this.root()){
			//StdOut.print("label"+n.label);
			a.append(n.label);
			n = n.parent;
		}
		a = a.reverse();
		return a; // change to the correct return value.
	}

	/*
	 * return the reference to the root node.
	 */
	public Node root() {
		return rlo[upBound];
		// return null; // change to the correct return value.
	}

	public int size() {
		return size;
	}

	/*
	 * I've provided this to help debug your tree.
	 */
	public String toString() {
		String result = "[";

		for (int k = 255; k >= 0; k--) {
			if (rlo[k] != null)
				result += "(" + rlo[k].weight + "," + rlo[k].place + ","
						+ rlo[k].character + ") ";
		}

		return result + "]\nsize = " + size;
	}

	public static void main(String[] args) throws IOException {
		// your program goes here
		/*
		 * Feel free to add any methods in the above classes that are needed.
		 * After implementing this class, construct a main() to start with an
		 * empty AdaptiveHuffmanTree and read a byte from the file class2.bin
		 * and call update() on that byte until all bytes have been read. Now
		 * display the tree using the toString() method I've provided. For
		 * example:
		 */
		AdaptiveHuffmanTree aht = new AdaptiveHuffmanTree();
		//String filename = "o1.bin";
		String filename = "class2.bin";
		In in = new In(filename);
		StdOut.println("Origin String: " + new In(filename).readAll());
		Out out = new Out("statistics.txt");
		//out.print();
		int i = 1;
		String previous = "";
		while (in.hasNextChar() ) {
			char c = in.readChar();
			if(c != '\n'){				
				StdOut.println("c is "+c);
				previous += c;
				aht.update(c);
				out.printf("(%d)\nInput:%s\nOutput:\n",i,previous);									
				out.println(aht);
				//StdOut.println();
				i++;
			}
		}
		out.close();
		StdOut.println(aht);
	}
}
