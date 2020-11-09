// Assignment 1 Part 1: Starter Code

class Tree_Test {

	public static void main(String[] args) {
		AbsTree tr = new Tree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

class DupTree_Test {

	public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

abstract class AbsTree {
	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null) {
				right = add_node(n);
				right.parent=this;   //assigning parent as this to the newly created node
			} else
				right.insert(n);
		else if (left == null) {
			left = add_node(n);
			left.parent=this;  //assigning parent as this to the newly created node
		} else
			left.insert(n);
	}

	public void delete(int n) {  
		AbsTree t = find(n);

		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		}

		int c = t.get_count();
		if (c > 1) {
			t.set_count(c-1);
			return;
		}

		if (t.left == null && t.right == null) { // n is a leaf value
			if (t != this)
				case1(t);
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		}
		if (t.left == null || t.right == null) { // t has one subtree only
			if (t != this) { // check whether t is the root of the tree
				case2(t);
				return;
			} else {
				if (t.right == null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		}
		// t has two subtrees; go with smallest in right subtree of t
		case3R(t);
	}

	protected void case1(AbsTree t) { // remove the leaf
		// to be filled by you
		
		//Checking the side of the node that is being removed with respect to its parent
		//And assigning that side of parent as null 
		
		if (t.parent.right==t)
		{
			t.parent.right=null;
		}
		else
		{
			t.parent.left=null;
		}
		t.parent=null; //removing the parent of the node that is being removed. 
	}

	protected void case2(AbsTree t) { // remove internal node
		// to be filled by you
		
		// First : checking the side to which the node being removed is present with respect to its parent node
		
		if (t.parent.right==t) 
		{
			if (t.right!=null)  //Second : checking if the node being removed has any child nodes and if any assigning them to its parent accordingly
			{
				t.parent.right=t.right;
				t.right.parent=t.parent;
				t.right=null;
			}
			else 
			{
				t.parent.right=t.left;
				t.left.parent=t.parent;
				t.left=null;
			}
		}
		else {
			if(t.right!=null) 
			{
				t.parent.left=t.right;
				t.right.parent=t.parent;
				t.right=null;
			}
			else 
			{
				t.parent.left=t.left;
				t.left.parent=t.parent;
				t.left=null;
			}
		}
		
		t.parent=null;  //Now the internal node is free of its child nodes, so lastly removing its connection to parent.
	}

	protected void case3L(AbsTree t) { // replace t.value and t.count
		// to be filled by you
		
		//Finding the node that has maximum value on the left side of root
		
		AbsTree n = t.left.max();
		
		//As we cannot remove root, exchanging the value and also the count between the root and node 'n'
		
		int a = t.value;
		t.value=n.value;
		n.value=a;
		
		t.set_count(n.get_count());
		n.set_count(1);
		
		//As node 'n' has max value it will not have any right sub trees, So checking if it has any left subtrees
		//If node 'n' has subtrees to the left then assigning them to its parent accordingly.
		//else node 'n' will be a leafnode and will be removed using case1
		
		if (n.left!=null)
		{
			n.parent.right=n.left;
			n.left.parent=n.parent;
			n.left=null;
			n.parent=null;
		}
		else
		{
			case1(n);
		}
	}

	protected void case3R(AbsTree t) { // replace t.value
		// to be filled by you
		
		//Finding minimum value node in right subtree and exchanging the value and also the count between the nodes
		
		AbsTree n = t.right.min();
		
		int a = t.value;
		t.value=n.value;
		n.value=a;
		
		t.set_count(n.get_count());
		n.set_count(1);
		
		//As node 'n' is the minimum it wont have any left subtrees
		//if it has right subtree then it will be a internal node and so removed using case2
		//if no right subtree then its a leafnode and so removed by using case1
		
		if (n.right==null) 
		{
			case1(n);
		}
		else
		{
			case2(n);
		}
		
	}

	private AbsTree find(int n) {
		// to be filled by you
		if (this.value==n) 
		{
			return this;
		}
		else if (this.value<n && this.right!=null ) 
		{
			return this.right.find(n);
		}
		else if (this.value>n && this.left!=null) 
		{
			return this.left.find(n);
		}
		else 
		{
			return null;
		}
	}

	public AbsTree min() {
		// to be filled by you
		
		//Traversing to the left until there is no left subtree
		
		if (this.left==null) 
		{
			return this;
		}
		else 
		{
			return this.left.min();		
		}
	}

	public AbsTree max() {
		// to be filled by you
		
		//Traversing to the right until there is no right subtree
		
		if (this.right==null) 
		{
			return this;
		}
		else 
		{
			return this.right.max();
		}
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract void set_count(int v);
}

class Tree extends AbsTree {
	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}

	protected int get_count() {
		// to be filled by you
		return 0;
	}

	protected void set_count(int v) {
		// to be filled by you
	}
}

class DupTree extends AbsTree {
	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	protected int get_count() {
		// to be filled by you
		return count;
	}

	protected void set_count(int v) {
		// to be filled by you
		count = v;
	}

	protected int count;
}
