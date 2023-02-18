import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BacktrackingAVL extends AVLTree {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingAVL() {
        super();
    }



    //You are to implement the function Backtrack.
    public void Backtrack() {
        if (!stack.isEmpty()) {
            Object[] array = stack.pop();
            Node goodbye = (Node)array[0];


            //second rotation inverse operation
            if (array[3] != null) {
                Node Parent = ((Node)array[4]).parent;
                if(array[3].equals("rotateRight"))
                    array[4]  = rotateRight((Node)array[4]);
                else
                    array[4]  = rotateLeft((Node)array[4]);

                //updates parent
                ((Node)array[4]).parent = Parent;
                if (Parent != null) {
                    if (Parent.value > ((Node)array[4]).value)
                        Parent.left = ((Node)array[4]);
                    else
                        Parent.right = ((Node)array[4]);
                }

                //updates fields for changes nodes
                ((Node)array[4]).updateHeight();
                ((Node)array[4]).updateSize();
                if (((Node)array[4]).parent != null)
                    ((Node)array[4]).parent.updateHeight();
                checkRoot();


            }

            //first rotation inverse operation
            if (array[1] != null) {
                Node Parent = ((Node)array[2]).parent;
                if(array[1].equals("rotateRight"))
                    array[2]  = rotateRight((Node)array[2]);
                else
                    array[2]  = rotateLeft((Node)array[2]);

                //updates parent
                ((Node)array[2]).parent = Parent;
                if (Parent != null) {
                    if (Parent.value > ((Node)array[2]).value)
                        Parent.left = ((Node)array[2]);
                    else
                        Parent.right = ((Node)array[2]);}


                //updates fields
                ((Node)array[2]).updateHeight();
                ((Node)array[2]).updateSize();
                if (((Node)array[2]).parent != null)
                    ((Node)array[2]).parent.updateHeight();
                checkRoot();

            }


            if(goodbye.parent==null)
                root = null;
            else {
                if (goodbye.parent.right != null && goodbye.parent.right.equals(goodbye))
                    goodbye.parent.right = null;
                else
                    goodbye.parent.left = null;
            }

            while(goodbye != root) {
                goodbye =goodbye.parent;
                if (goodbye != null)
                    goodbye.updateHeight();
            }

        }

        checkRoot();





    }


    public void checkRoot() {
        if (root!= null && root.parent != null)
            root = root.parent;
    }



    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample() {
        List<Integer> elements = new LinkedList<Integer>();
        for (int i=1; i<6; i++)
            elements.add(0,i);
        return elements;
    }


    public int Select(int index) {
        if (root == null )
            return 0;
        return Select(root,index);
    }

    private int Select(Node node, int index) {
        int leftSize = 0;
        if(node.left != null)
            leftSize = node.left.size;
        int currRank = leftSize + 1;
        if (currRank == index)
            return node.value;
        else if (index < currRank) {
            if(node.left == null)
                return  node.value;
            return Select(node.left,index);
        }
        else {
            if(node.right == null)
                return  node.value;
            return Select(node.right, index- currRank);
        }
    }

    public int Rank(int value) {
        if(root == null)
            return 0;
        else
            return Rank(root,value);

    }

    public int Rank(Node node, int value) {
        int leftSize = 0;
        if (node.left != null)
            leftSize = node.left.size;
        if(node.value == value)
            return leftSize ;
        else if (node.value > value) {
            if(node.left == null)
                return 0;
            return Rank(node.left,value);
        }
        else {
            if(node.right == null && node.left != null)
                return leftSize+1;
            else if (node.right == null && node.left == null)
                return 1;
            else
                return Rank(node.right,value)+leftSize+1;
        }


    }

}
