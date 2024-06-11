import java.util.Scanner;
import static java.lang.Math.max;

class Node {
    int value;
    Node parent;
    Node left;
    Node right = null;
    int height;

    Node(int value) {
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

class BinaryTree {
    Node root;

    BinaryTree() {
        this.root = null;
    }

    void insert(int value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            newNode.value = value;
        } else {
            Node actual = root;
            while (true) {
                if (value < actual.value) {
                    if (actual.left == null) {
                        actual.left = newNode;
                        newNode.parent = actual;
                        return;
                    }
                    actual = actual.left;
                } else {
                    if (actual.right == null) {
                        actual.right = newNode;
                        newNode.parent = actual;
                        return;
                    }
                    actual = actual.right;
                }
            }
        }
    }

    void delete(int value){
        Node actual = root;
        while (actual.value != value) {
            if (value < actual.value) {
                actual = actual.left;
            } else {
                actual = actual.right;
            }
            if (actual == null) {
                System.out.println("The value you are " +
                        "trying to delete was not found.");
                return;
            }
        }
        if (actual.left == null){
            transplant(actual, actual.right);
        } else if (actual.right == null){
            transplant(actual, actual.left);
        } else {
            Node y = treeMin(actual.right);
            if (y.parent != actual){
                transplant(y, y.right);
                y.right = actual.right;
                y.right.parent = y;
            }
            transplant(actual, y);
            y.left = actual.left;
            y.left.parent = y;
        }
    }

    void transplant(Node u, Node v){
        if (u.parent == null){
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null){
            v.parent = u.parent;
        }
    }

    int height(Node x) {
        if (x == null) {
            return -1;
        } else {
            return 1 + max(height(x.left), height(x.right));
        }
    }

    int heightTree() {
        return height(root);
    }

    int numNodes(Node x) {
        if (x == null) {
            return 0;
        } else {
            return 1 + numNodes(x.left) + numNodes(x.right);
        }
    }

    int treeNodecount(Node root) {
        return numNodes(this.root);
    }

    void inOrder(Node x){
        if (x != null){
            inOrder(x.left);
            System.out.println(x.value);
            inOrder(x.right);
        }
    }
    void preOrder(Node x){
        if (x != null){
            System.out.println(x.value);
            preOrder(x.left);
            preOrder(x.right);
        }
    }
    void postOrder(Node x){
        if (x != null){
            postOrder(x.left);
            postOrder(x.right);
            System.out.println(x.value);
        }
    }
    Node treeMin(Node x){ //Could be int I think lol
        while (x.left != null){
            x = x.left;
        }
        return x;
    }
    Node treeMax(Node x){
        while (x.right != null){
            x = x.right;
        }
        return x;
    }
    public void clearTree() {
        root = null;
    }
}

class TreeAVL extends BinaryTree {
    int height(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }
    private void updateHeight(Node N) {
        N.height = 1 + Math.max(height(N.left), height(N.right));
    }

    // Get Balance factor of node N
    int getBalance(Node N)
    {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }
    Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
    Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node insert(Node node, int value) {
        /* 1. Perform the normal BST rotation */
        if (node == null) {
            return (new Node(value));
        }
        if (value < node.value) {
            node.left = insert(node.left, value);
            node.left.parent = node; // Update parent reference
        } else if (value > node.value) {
            node.right = insert(node.right, value);
            node.right.parent = node; // Update parent reference
        } else { // Equal values not allowed
            return node;
        }
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
        node to check whether this node became
        Wunbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // there are 4 cases Left Left Case
        if (balance > 1 && value < node.left.value){
            return rotateRight(node);
        }
        // Right Right Case
        if (balance < -1 && value > node.right.value) {
            return rotateLeft(node);
        }
        // Left Right Case
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        /* return the (unchanged) node pointer */
        return node;
    }

    /* Given a non-empty binary search tree, return the
    node with minimum value value found in that tree.
    Note that the entire tree does not need to be
    searched. */
    Node minValueNode(Node node)
    {
        Node current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    Node delete(Node root, int value)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the value to be deleted is smaller than
        // the root's value, then it lies in left subtree
        if (value < root.value)
            root.left = delete(root.left, value);

            // If the value to be deleted is greater than the
            // root's value, then it lies in right subtree
        else if (value > root.value)
            root.right = delete(root.right, value);

            // if value is same as root's value, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.value = temp.value;

                // Delete the inorder successor
                root.right = delete(root.right, temp.value);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }
}
public class Main {

        static void printTree(BinaryTree binaryTree) {
        if (binaryTree.root == null) {
            System.out.println("El árbol está vacío");
            return;
        }
        preOrder(binaryTree.root, 0);
        System.out.println();
    }

    static void preOrder(Node node, int nodeHeight) {
        if (node == null) {
            return;
        }

        String parentValue;
        if (node.parent != null){
            parentValue = String.valueOf(node.parent.value);
        } else {
            parentValue = "NIL";
        }

        String nodeValue = String.valueOf(node.value);

        String leftOrRightChild = "(root)";
        if (node.parent != null){
            if (node.value < node.parent.value){
                leftOrRightChild = "(left)";
            } else {
                leftOrRightChild = "(right)";
            }
        }

        for (int i = 0; i < nodeHeight; i++) {
            System.out.print("     ");
        }

        System.out.println("└──Parent: " + parentValue + " ---> Node: " + nodeValue + " " +leftOrRightChild);
        preOrder(node.left, nodeHeight+1);
        preOrder(node.right, nodeHeight+1);

    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BinaryTree bst = new BinaryTree();
        TreeAVL avl = new TreeAVL();
        boolean isAVL = false;

        while (true) {
            System.out.println("Seleccione el tipo de árbol:");
            System.out.println("1. Árbol Binario de Búsqueda (BST)");
            System.out.println("2. Árbol Adelson-Velskii y Landis (AVL)");
            System.out.println("0. Salir");

            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                isAVL = false;
            } else if (choice == 2) {
                isAVL = true;
            } else {
                System.out.println("Opción no válida.");
                continue;
            }

            //TODO: make an option that clears the current tree (BST or AVL)

            while (true) {
                System.out.println("Elije la operación a realizar:");
                System.out.println("1. Insertar");
                System.out.println("2. Eliminar");
                System.out.println("3. Determinar altura");
                System.out.println("4. Determinar cantidad de nodos");
                System.out.println("5. Recorrido in-order");
                System.out.println("6. Recorrido pre-order");
                System.out.println("7. Recorrido post-order");
                System.out.println("8. Determinar valor mínimo");
                System.out.println("9. Determinar valor máximo");
                System.out.println("10.Mostrar el árbol");
                System.out.println("11.Limpiar el árbol");
                System.out.println("0. Volver al menú anterior");

                int operation = scanner.nextInt();
                if (operation == 0) {
                    break;
                } else if (operation == 1) {                                                    //INSERT
                    System.out.print("Ingrese el valor a insertar: ");
                    int insertValue = scanner.nextInt();
                    if (isAVL) {
                        avl.root = avl.insert(avl.root, insertValue);
                    } else {
                        bst.insert(insertValue);
                    }
                    printTree(isAVL ? avl : bst);
                } else if (operation == 2) {                                                    //DELETE
                    System.out.print("Ingrese el valor a eliminar: ");
                    int deleteValue = scanner.nextInt();
                    if (isAVL) {
                        avl.root = avl.delete(avl.root, deleteValue);
                    } else {
                        bst.delete(deleteValue);
                    }
                    printTree(isAVL ? avl : bst);
                } else if (operation == 3) {                                                    //HEIGHT
                    if (isAVL) {
                        System.out.println("Altura del árbol AVL: " + avl.heightTree());
                    } else {
                        System.out.println("Altura del BST: " + bst.heightTree());
                    }
                } else if (operation == 4) {                                                    //NODECOUNT
                    if (isAVL) {
                        System.out.println("Número de nodos en el árbol AVL: " + avl.treeNodecount(avl.root));
                    } else {
                        System.out.println("Número de nodos en el BST: " + bst.treeNodecount(bst.root));
                    }
                } else if (operation == 5) {                                                    //IN-ORDER
                    if (isAVL) {
                        avl.inOrder(avl.root);
                    } else {
                        bst.inOrder(bst.root);
                    }
                } else if (operation == 6) {                                                    //PRE-ORDER
                    if (isAVL) {
                        avl.preOrder(avl.root);
                    } else {
                        bst.preOrder(bst.root);
                    }
                } else if (operation == 7) {                                                    //POST-ORDER
                    if (isAVL) {
                        avl.postOrder(avl.root);
                    } else {
                        bst.postOrder(bst.root);
                    }
                } else if (operation == 8) {                                                    //MIN VALUE
                    if (isAVL) {
                        System.out.println("Valor mínimo en el árbol AVL: " + avl.treeMin(avl.root).value);
                    } else {
                        System.out.println("Valor mínimo en el BST: " + bst.treeMin(bst.root).value);
                    }
                } else if (operation == 9) {                                                    //MAX VALUE
                    if (isAVL) {
                        System.out.println("Valor máximo en el árbol AVL: " + avl.treeMax(avl.root).value);
                    } else {
                        System.out.println("Valor máximo en el BST: " + bst.treeMax(bst.root).value);
                    }
                } else if (operation == 10) {                                                   //TREE PRINT
                    if (isAVL) {
                        printTree(avl);
                    } else {
                        printTree(bst);
                    }
                } else if (operation == 11) {                                                   //CLEAR TREE
                    if (isAVL) {
                        avl.clearTree();
                        System.out.println("Se ha limpiado el árbol");
                    } else {
                        bst.clearTree();
                        System.out.println("Se ha limpiado el árbol");
                    }
                } else {
                    System.out.println("Opción no válida.");
                }
            }
        }
        scanner.close();
    }
}

