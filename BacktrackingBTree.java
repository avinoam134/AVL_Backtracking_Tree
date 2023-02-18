import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingBTree() {
        super();
    }

    public BacktrackingBTree(int order) {
        super(order);
    }

    public void Backtrack() {
        if (size == 1)
            root = null;
        else {
            if (!stack.isEmpty()) {
                //removes the last inserted key
                T lastInserted = (T)stack.pop();
                Node<T> LastNode = getNode(lastInserted);
                int ind =getValuePosition(LastNode, lastInserted);
                LastNode.removeKey(lastInserted);

                //Handling extreme cases
                if (LastNode.parent == null || LastNode.parent.numOfKeys == 0)
                    root = LastNode;

                //checks if the backtrack process is complete
                boolean keepBacktrack = !stack.isEmpty();
                if (keepBacktrack && ((stack.peek() instanceof String))) {
                    stack.pop();
                    keepBacktrack = false;
                }

                //if during the key's insertion there were nodes splitting, we will merge them
                while(keepBacktrack) {
                    //preparing for the mergegetNode
                    T median = (T)stack.pop();
                    Node<T> parent=  getNode(median);
                    int index =getValuePosition(parent, median);
                    Node<T> left = parent.getChild(index);
                    Node<T> right = parent.getChild(index+1);
                    //merges left, median and right
                    merge(left,median,right);
                    //removes the median key from the parent and it's right child
                    parent.removeKey(index);
                    parent.removeChild(index+1);


                    //checks if the backtrack process is complete
                    if (stack.isEmpty())
                        keepBacktrack = false;
                    else if ( stack.peek() instanceof String) {
                        stack.pop();
                        keepBacktrack = false;
                    }

                }

                //Handling extreme cases
                if (root.numOfKeys == 0)
                    root = root.getChild(0);


            }

        }
        size = size -1;


    }
    private int getValuePosition(Node<T> Node,T value) {
        boolean found = false;
        int i = 0;

        while(i < Node.getNumberOfKeys() && !found) {
            if(value.compareTo(Node.getKey(i)) <= 0){
                found = true;
            } else {
                ++i;
            }
        }

        return i;
    }

    public void merge(Node<T> left, T median, Node<T> right) {
        left.addKey(median);
        for (int i = 0; i < right.numOfKeys; ++i) {
            left.addKey(right.getKey(i));
        }

        for (int i = 0; i < right.numOfChildren; ++i)
            left.addChild(right.getChild(i));

    }



    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> BTreeBacktrackingCounterExample(){
        // You should remove the next two lines, after double-checking that the signature is valid!
        List<Integer> elements = new LinkedList<Integer>();
        for (int i=1; i<10; i++)
            elements.add(0,i);
        return elements;

    }
}
