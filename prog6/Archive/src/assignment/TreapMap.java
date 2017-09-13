package assignment;

import java.util.*;
import static java.lang.System.*;
import assignment.Treap;
import assignment.TreapMap.TreapNode;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K, V> {
   
   //This node represents all of the nullNodes in the Tree.
   //This is used rather than generating actual new nodes because it saves on
   //overhead.
   private TreapNode<K, V> nullNode;
   //This node represents the tree's root.
   private TreapNode<K, V> root;
   
   //These two nodes are used as pointers to traversing the tree.
   private TreapNode<K, V> currentNode;
   private TreapNode<K, V> parent;
   
   //Initializes an empty TreapMap.
   public TreapMap() {
      //Initializes the nullNode.
      nullNode = new TreapNode<K, V>(null, null, nullNode);
      nullNode.setRight(nullNode);
      nullNode.setLeft(nullNode);
      //Sets the root to a nullNode.
      root = nullNode;
   }
   
   //Helper method that changes the root value.
   private void setRoot(TreapNode<K, V> r) {
      root = r;
      root.setParent(nullNode);
   }
   
   //Returns the root value.
   TreapNode<K, V> getRoot() {
      return root;
   }
   
   //Retrieves the value associated with a key in the Treap.
   public V lookup(K key) {
      //Returns null if key is null.
      if(key == null) {
         return null;
      }
      //Sets pointer to the root.
      currentNode = root;
      //Searches through the Treap until the key is found.
      while(currentNode.getKey() != key) {
         //If the pointer finds a null node, then the key has no associated
         //value in the Treap.
         if(currentNode.getKey() == null) {
            return nullNode.getValue();
         }
         //If the key is larger than the present node, then it must be in the 
         //right subtree.
         if(key.compareTo(currentNode.getKey()) > 0) {
            currentNode = currentNode.getRight();
         }
         //If the key is smaller than the present node, then it must be in the 
         //left subtree.
         else {
            currentNode = currentNode.getLeft();
         }
      }
      return currentNode.getValue();
   }
   
   //Inserts a new node with key K and value V.
   public void insert(K key, V value) {
      //Accounts for null key values.
      if(key == null) {
         err.println("Error: null key found.");
         return;
      }
      //Accounts for null values.
      if(value == null) {
         err.println("Error: null value found.");
         return;
      }
      //Removes node with same key value if applicable.
      remove(key);
      //Creates a new node with the proper values.
      TreapNode<K, V> newNode = new TreapNode<K, V>(key, value, nullNode);
      newNode.setLeft(nullNode);
      newNode.setRight(nullNode);
      //Sets pointers to the root.
      currentNode = root;
      parent = root;
      //Finds the proper position for the newNode to be inserted.
      while(currentNode.getKey() != null && 
            key.compareTo(currentNode.getKey()) != 0) {
         parent = currentNode;
         //Pointer goes to the left if the key value is < current key value, 
         //and it goes to the right if the key value is > current key value.
         currentNode = key.compareTo(currentNode.getKey()) < 0 ? 
               currentNode.getLeft() : currentNode.getRight();
      }
      //Incorporates the new node into the tree.
      if(currentNode.getKey() == null || 
            key.compareTo(currentNode.getKey()) == 0) {
         //Adds the children from the pointer to the new node.
         newNode.setLeft(currentNode.getLeft());
         newNode.setRight(currentNode.getRight());
         //Resets root if applicable.
         if(currentNode == root) {
            root = newNode;
         }
         //Sets new node's parent.
         else {
            if(key.compareTo(parent.getKey()) > 0) {
               parent.setRight(newNode);
            }
            else {
               parent.setLeft(newNode);
            }
         }
      }
      //Rotates the node to fulfill the heap property.
      while(parent.getKey() != null && 
            parent.getPriority() < newNode.getPriority()) {
         //Rotates right around the parent node.
         if(parent.getLeft() == newNode) {
            parent.rotateRight();
         }
         //Rotates left around the parent node.
         else {
            parent.rotateLeft();
         }
         parent = newNode.getParent();
         //Resets root if applicable.
         if(newNode.getParent().getKey() == null) {
            root = newNode;
         }
      }
      newNode.setParent(parent);
   }
   
   //Adds a node into an existing tree without deleting any values.
   private void add(TreapNode<K, V> newNode) {
      //Set pointers to the root.
     currentNode = root;
     parent = root;
     //Stores children of node to be inserted.
     TreapNode<K, V> grandL = newNode.getLeft();
     TreapNode<K, V> grandR = newNode.getRight();
     K key = newNode.getKey();
     //Finds the proper position to insert the node into the tree.
     while(currentNode.getKey() != null) {
        parent = currentNode;
        //Pointer goes to the left if the key value is < current key value, 
        //and it goes to the right if the key value is >= current key value.
        currentNode = key.compareTo(currentNode.getKey()) <= 0 ? 
              currentNode.getLeft() : currentNode.getRight();
              currentNode.setParent(parent);
     }
     //Incorporates the new node into the tree.
     if(currentNode.getKey() == null) {
        //Resets root if applicable.
        if(currentNode == root) {
           setRoot(newNode);
        }
        //Sets new node's parents and children.
        else {
           if(key.compareTo(parent.getKey()) > 0) {
              parent.setRight(newNode);
              newNode.setParent(parent);
              newNode.setLeft(currentNode.getLeft());
              newNode.setRight(currentNode.getRight());
           }
           else {
              parent.setLeft(newNode);
              newNode.setParent(parent);
              newNode.setLeft(currentNode.getLeft());
              newNode.setRight(currentNode.getRight());
           }
        }
     }
     //Rotates to restore the heap property.
     while(parent.getKey() != null && parent.getPriority() < 
           newNode.getPriority()) {
        //Rotates right around the parent node.
        if(parent.getLeft() == newNode) {
           parent.rotateRight();
        }
        //Rotates left around the parent node.
        else {
           parent.rotateLeft();
        }
        //Resets the root if applicable.
        parent = newNode.getParent();
        if(newNode.getParent().getKey() == null) {
           root = newNode;
        }
     }
     //Sets the node's parent.
     newNode.setParent(parent);
     //Recursively adds the remaining nodes in right subtree.
     if(grandR.getKey() != null) {
        add(grandR);
     }
     //Recursively adds the remaining nodes in left subtree.
     if(grandL.getKey() != null) {
        add(grandL);
     }
        
   }
   
   //Removes a key from the TreapMap.
   public V remove(K key) {
      //If the key is null, it returns null.
      if(key == null) {
         err.println("Error: null key found.");
         return null;
      }
      //If the only node in the TreapMap's key value is equal to the key
      //parameter, it sets the root to nullNode.
      if(root.getKey() == key && root.getLeft().getKey() == null && 
            root.getRight().getKey() == null) {
         V value = root.getValue();
         //This sets the TreapMap back to its empty state.
         root = nullNode;
         return value;
      }
      //Sets the pointer to the root.
      currentNode = root;
      //Utilizing the lookup function, the pointer iterates to the proper 
      //position.
      lookup(key);
      //If the currentNode is the nullNode, then the TreapMap does not contain
      //the proper node.
      if(currentNode.getKey() == null) {
         return null;
      }
      //Rotates the selected node until it becomes a leaf. 
      while(currentNode.getLeft().getKey() != null || 
            currentNode.getRight().getKey() != null) {
         //If the right child is null or the left child is greater than the 
         //right, it rotates the node to the right.
         if(currentNode.getRight().getKey() == null || 
               currentNode.getLeft().getKey() != null && 
               currentNode.getLeft().getPriority() > 
               currentNode.getRight().getPriority()) {
            currentNode.rotateRight();
         }
         //If the left child is null or the right child is greater than or 
         //equal to the left, it rotates the node to the left.
         else if(currentNode.getLeft().getKey() == null || 
               currentNode.getRight().getPriority() >= 
               currentNode.getLeft().getPriority()) {
            currentNode.rotateLeft();
         }
         //Resets the root if necessary, after the rotation.
         if(currentNode.getParent().getParent().getKey() == null) {
            root = currentNode.getParent();
         }
      }
      //Removes the node from the Treap.
      //Checks if node is a left leaf.
      if(currentNode.getParent().getLeft() == currentNode) {
         currentNode.getParent().setLeft(nullNode);
      }
      //Checks if node is a right leaf.
      else {
         currentNode.getParent().setRight(nullNode);
      }
      return currentNode.getValue();
   }
   
   //Splits a Treap into two Treaps where all nodes with a key < a certain key
   //value are in the first Treap, and all nodes with a key >= a certain key
   //value are in the second Treap.
   public TreapMap<K, V> [] split(K key) {
      //Checks if the key is null.
      if(key == null) {
         err.println("Error: null key found.");
         return new TreapMap[0];
      }
      //Creates some node with maximum priority and the given key value.
      TreapNode<K, V> bigNode = new TreapNode<K, V>(key, null, nullNode);
      bigNode.setPriority(MAX_PRIORITY);
      //Adds the large node to the root of the TreapMap.
      add(bigNode);
      //All values to the left of the big node are < than the big node.
      TreapMap<K, V> left = new TreapMap<K, V>();
      left.setRoot(bigNode.getLeft());
      //All values to the right of the big node are >= than the big node.
      TreapMap<K, V> right = new TreapMap<K, V>();
      right.setRoot(bigNode.getRight());
      //Adds the two subtrees to an array.
      TreapMap<K, V> [] subtrees = 
            (TreapMap<K, V>[]) new TreapMap[] {left, right};
      return subtrees;
   }
   
   //Joins two Treaps together, with all keys in one Treap being smaller than
   //all keys in the other Treap.
   public void join(Treap<K, V> t) {
      //Handles case of null Treap.
      if(t == null) {
         err.println("Error: null TreapMap found.");
         return;
      }
      //Operation is only performed if both Treaps are of the same type.
      if(!(t instanceof TreapMap)) {
         err.println("Error: cannot join Treaps of different types");
         return;
      }
      //Casts Treap to TreapMap after checking.
      TreapMap<K, V> temp = (TreapMap<K, V>) t;
      //If this TreapMap is empty, simply set its root to the new TreapMap's 
      //root. 
      if(root.getKey() == null) {
         root = temp.getRoot();
         return;
      }
      //If the other TreapMap is empty, then no operation is performed.
      if(temp.getRoot().getParent() == null) {
         return;
      }
      //This detects if the TreapMaps have overlapping root values, which is 
      //illegal.
      if(getRoot().getKey() == temp.getRoot().getKey()) {
         err.println("Error: cannot join Treaps with overlapping key values");
         return;
      }
      //Initializes some node with a key equal to the root.
      TreapNode<K, V> bigNode = new TreapNode<K, V>(root.getKey(), 
            root.getValue(), nullNode);
      //Sets the priority to maximum.
      bigNode.setPriority(MAX_PRIORITY);
      //Adds the node to the top of the tree.
      add(bigNode);
      //Adds the root of the other tree to the node.
      add(temp.getRoot());
      //Removes the bigNode, leaving a joined version of the TreapMaps.
      remove(bigNode.getKey());
   }
   
   //Unimplemented
   public void meld(Treap<K, V> t) throws UnsupportedOperationException {
      
   }
   
   //Unimplemented
   public void difference(Treap<K, V> t) throws UnsupportedOperationException {
      
   }
   
   //Represents the nodes as a String with pre-order traversal.
   public String toString() {
      //Calls the toString of the root which recursively adds the subsequent 
      //nodes.
      return root.toString();
   }
   
   //Returns a TreapIterator to traverse the TreapNodes in order.
   public Iterator<K> iterator() {
      return new TreapIterator();
   }
   
   //Unimplemented
   public double balanceFactor() throws UnsupportedOperationException {
      return 0;
   }
   
   
   
   //This class iterates through the TreapMap's TreapNodes in order.
   class TreapIterator<K extends Comparable<K>> implements Iterator<K> {
      //This is a pointer variable.
      TreapMap<K, V>.TreapNode<K, V> currentNode;
      //This keeps track of all unreturned, traversed nodes.
      Stack<TreapMap<K, V>.TreapNode<K, V>> path;
      
      //This initializes the TreapIterator.
      public TreapIterator() {
         //Sets the pointer to the root.
         currentNode = (TreapMap<K, V>.TreapNode<K, V>) root;
         //Creates an empty path.
         path = new Stack<TreapMap<K, V>.TreapNode<K, V>>();
      }
      
      //Checks if there are more iterations in the TreapMap. 
      public boolean hasNext() {
         //There are more iterations if the pointer is not pointed at a
         //nullNode or if there are unreturned, traversed paths.
         if(!path.isEmpty() || currentNode.getKey() != null) {
            return true;
         }
         return false;
      }
   
      //Returns the key value of the next node.
      public K next() {
         //Traverses to the left all the way, and adds traversed nodes to the 
         //path stack.
         while(currentNode.getKey() != null) {
               path.push(currentNode);
               currentNode = currentNode.getLeft();
         }
         //Sets pointer to the last traversed node.
         currentNode = path.pop();
         //Returns the key of that node.
         K ret = currentNode.getKey();
         //Sets pointer to the current node's right child.
         currentNode = currentNode.getRight();
         return ret;
      }
   }
   
   //This helper class creates nodes.
   class TreapNode<K, V> {
      
      //This is the value of the node's key.
      private K key;
      //This is the value stored in the node.
      V value;
      //This is the node's priority.
      private int priority;
      
      //This is the node's left child.
      private TreapNode<K, V> left;
      //This is the node's right child.
      private TreapNode<K, V> right;
      //This is the parent.
      private TreapNode<K, V> parent;
      //This is stores the nullNode within the TreapNode class.
      private TreapNode<K, V> nullNode;
      
      //Initializes the TreapNode.
      protected TreapNode(K k, V v, TreapNode<K, V> n) {
         key = k;
         value = v;
         //This creates a random priority for the node.
         priority = (int)(Math.random() * Treap.MAX_PRIORITY);
         //This sets the nullNode to the given nullNode value.
         nullNode = n;
         left = nullNode;
         right = nullNode;
         parent = nullNode;
      }
      
      //Helper method that changes a TreapNode's parent.
     void setParent(TreapNode<K, V> p) {
         parent = p;
      }
     
      //Returns the parent.
      public TreapNode<K, V> getParent() {
         return parent;
      }
   
      //Helper method that changes a TreapNode's left child.
      void setLeft(TreapNode<K, V> l) {
         left = l;
      }
      
      //Returns the node's left child.
      public TreapNode<K, V> getLeft() {
         return left;
      }
      
      //Helper method that changes a TreapNode's right child.
      void setRight(TreapNode<K, V> r) {
         right = r;
      }
      
      //Returns the node's right child.
      public TreapNode<K, V> getRight() {
         return right;
      }
      
      //This method rotates a node to the right within a tree.
      void rotateRight() {
         //Stores grandchild of left child.
         TreapNode<K, V> grandR = left.getRight();
         //Moves left child to current node's position.
         left.setParent(parent);
         //Gives the parent the node's left child.
         if(parent.getKey() != null) {
            //Checks if the current node is a left child or right child.
            if(parent.getLeft() == this) {
               parent.setLeft(left);
            }
            else {
               parent.setRight(left);
            }
         }
         //Moves currentNode into new position.
         left.setRight(this);
         setParent(getLeft());
         //Adds the stored grandchild as new left child.
         grandR.setParent(this);
         setLeft(grandR); 
      }
      
      //This method rotates a node to the left within a tree.
      void rotateLeft() {
         //Stores grandchild of right child.
         TreapNode<K, V> grandL = right.getLeft();
         //Moves right child to current node's position.
         right.setParent(parent);
         //Gives the parent the node's right child.
         if(parent.getKey() != null) {
            //Checks if the current node is a left child or right child.
            if(parent.getRight() == this) {
               parent.setRight(right);
            }
            else {
               parent.setLeft(right);
            }
         }
         //Moves currentNode into new position.
         right.setLeft(this);
         setParent(getRight());
         //Adds the stored grandchild as new right child.
         grandL.setParent(this);
         setRight(grandL);
      }
      
      //Returns the node's key.
      public K getKey() {
         return key;
      }
      
      //Returns the value stored in the node.
      public V getValue() {
         return value;
      }
      
      //Helper method that modifies a node's priority.
      void setPriority(int p) {
         priority = p;
      }
      
      //Returns the node's priority.
      public int getPriority() {
         return priority;
      }
      
      //Represents each node as a String.
      public String toString() {
         if(this.getKey() == null) {
            return "";
         }
         //Base case for recursive toString calls.
         return toString(0, false, false);
      }
      
      //Recursively adds to the String representation of the TreapMap.
      public String toString(int level, boolean l, boolean r) {
         String ret = "";
         //Adds tabs to each level.
         for(int count = 0; count < level; count++) {
            ret +="\t";
         }
         //Adds an L if it is a left child.
         if(l) {
            ret += "L";
         }
         //Adds an R if it is a right child.
         if(r) {
            ret += "R";
         }
         //Adds the priority, key, and value of the node.
         ret += "["+ getPriority() + "] <" + getKey() + ", " + 
               getValue() + ">";
         //Adds a next line character.
         ret += "\n";
         //Recursively adds the left child.
         if(left.getKey() != null) {
            ret += left.toString(level + 1, true, false);
         }
         //Recursively adds the right child.
         if(right.getKey() != null) {
            ret += right.toString(level + 1, false, true);
         }
         return ret;
      }
   }
}

