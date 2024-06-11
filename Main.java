import java.util.Scanner;

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

    void insertar(int value) {
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
                System.out.println("The value you are trying to delete was not found.");
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
            return 1 + Math.max(height(x.left), height(x.right));
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
    Node treeMin(Node x){ //Could be int I think??
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
}

class TreeAVL extends BinaryTree {
    int height(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }
    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent != null){
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }
    private void rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null){
            root = x;
        } else if (y == y.parent.right){
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;

    }
}
public class Main {

    static void printTree(BinaryTree binaryTree) {
        if (binaryTree.root == null) {
            System.out.println("The tree is empty");
            return;
        }
        preOrder(binaryTree.root, 0);
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
        BinaryTree arbolActual = null;

        while (true) {
            System.out.println("Seleccione el tipo de árbol:");
            System.out.println("1. Árbol Binario de Búsqueda (BST)");
            System.out.println("2. Árbol Adelson-Velskii y Landis (AVL)");
            System.out.println("0. Salir");

            int option = scanner.nextInt();

            if (option == 0) {
                break;
            } else if (option == 1) {
                arbolActual = bst;
            } else if (option == 2) {
                arbolActual = avl;
            } else {
                System.out.println("Opción no válida.");
                continue;
            }

            while (true) {
                System.out.println("Seleccione la operación a realizar:");
                System.out.println("1. Insertar");
                System.out.println("2. Eliminar");
                System.out.println("3. Determinar altura");
                System.out.println("4. Determinar cantidad de nodos");
                System.out.println("5. Recorrer en in-order");
                System.out.println("6. Recorrer en pre-order");
                System.out.println("7. Recorrer en post-order");
                System.out.println("8. Determinar el mínimo");
                System.out.println("9. Determinar el máximo");
                System.out.println("0. Volver al menú anterior");

                int operation = scanner.nextInt();
                if (operation == 0) {
                    break;
                } else if (operation == 1) {
                    System.out.print("Ingrese el valor a insertar: ");
                    int valorInsertar = scanner.nextInt();
                    arbolActual.insertar(valorInsertar);
                    printTree(arbolActual);
                } else if (operation == 2) {
                    System.out.print("Ingrese el valor a eliminar: ");
                    int deleteValue = scanner.nextInt();
                    arbolActual.delete(deleteValue);                   //PLACEHOLDER
                    printTree(arbolActual);
                } else if (operation == 3) {
                    System.out.println("La altura del árbol es: " + arbolActual.height(arbolActual.root));
                } else if (operation == 4) {
                    System.out.println("La cantidad de nodos del árbol es: " + arbolActual.treeNodecount(arbolActual.root));
                } else if (operation == 5) {
                    System.out.print("Recorrido in-order: ");
                    arbolActual.inOrder(arbolActual.root);
                    System.out.println();
                } else if (operation == 6) {
                    System.out.print("Recorrido pre-order: ");
                    arbolActual.preOrder(arbolActual.root);
                    System.out.println();
                } else if (operation == 7) {
                    System.out.print("Recorrido post-order: ");
                    arbolActual.postOrder(arbolActual.root);
                    System.out.println();
                } else if (operation == 8) {
                    if (arbolActual.root != null) {
                        System.out.println("El valor mínimo del árbol es: " + arbolActual.treeMin(arbolActual.root));
                    } else {
                        System.out.println("El árbol está vacío.");
                    }
                } else if (operation == 9) {
                    if (arbolActual.root != null) {
                        System.out.println("El valor máximo del árbol es: " + arbolActual.treeMax(arbolActual.root));
                    } else {
                        System.out.println("El árbol está vacío.");
                    }
                } else {
                    System.out.println("Opción no válida.");
                }
            }
        }
        scanner.close();
    }
}

