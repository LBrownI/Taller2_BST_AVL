import java.util.Scanner;

class Nodo {
    int value;
    Nodo partner;
    Nodo left_child;
    Nodo right_child = null;
    int altura;

    Nodo(int value) {
        this.value = value;
        this.partner = null;
        this.left_child = null;
        this.right_child = null;
        this.altura = 1;
    }
}

class ArbolBinario {
    Nodo cabeza;

    ArbolBinario() {
        this.cabeza = null;
    }

    void insertar(int value) {
        Nodo nuevoNodo = new Nodo(value);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            nuevoNodo.value = value;
        } else {
            Nodo actual = cabeza;
            while (true) {
                if (value < actual.value) {
                    if (actual.left_child == null) {
                        actual.left_child = nuevoNodo;
                        nuevoNodo.partner = actual;
                        return;
                    }
                    actual = actual.left_child;
                } else {
                    if (actual.right_child == null) {
                        actual.right_child = nuevoNodo;
                        nuevoNodo.partner = actual;
                        return;
                    }
                    actual = actual.right_child;
                }
            }
        }
    }
    int altura(Nodo x) {
        if (x == null) {
            return -1;
        } else {
            return 1 + Math.max(altura(x.left_child), altura(x.right_child));
        }
    }

    int alturaArbol() {
        return altura(cabeza);
    }

    int numNodos(Nodo x) {
        if (x == null) {
            return 0;
        } else {
            return 1 + numNodos(x.left_child) + numNodos(x.right_child);
        }
    }

    int numNodosArbol() {
        return numNodos(cabeza);
    }

    void inOrder(Nodo x){
        if (x != null){
            inOrder(x.left_child);
            System.out.println(x.value);
            inOrder(x.right_child);
        }
    }
    void preOrder(Nodo x){
        if (x != null){
            System.out.println(x.value);
            preOrder(x.left_child);
            preOrder(x.right_child);
        }
    }
    void postOrder(Nodo x){
        if (x != null){
            postOrder(x.left_child);
            postOrder(x.right_child);
            System.out.println(x.value);
        }
    }
    Nodo treeMin(Nodo x){ //Could be int I think??
        while (x.left_child != null){
            x = x.left_child;
        }
        return x;
    }
    Nodo treeMax(Nodo x){
        while (x.right_child != null){
            x = x.right_child;
        }
        return x;
    }
}

class ArbolAVL extends ArbolBinario {
    int altura(Nodo N) {
        if (N == null) {
            return 0;
        }
        return N.altura;
}
public class Main {

    static void imprimirLista(ArbolBinario lista) {
        Nodo actual = lista.cabeza;
        
        int[] a = new int[2];
        Nodo[][] height = new Nodo[3][3];
        height[0][0] = actual;
        while (actual != null) {
        }
    }
//            System.out.print(actual.valor + " ");
//            actual = actual.siguiente;
//        }
//        System.out.println();

    // something like this:
    // [[1], [1, 2], [1, null, 2, 3]] (the index of the elements represents the height at that node.
    // for each: actual.left_child, actual.right_child -> new list

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArbolBinario bst = new ArbolBinario();
        ArbolAVL avl = new ArbolAVL();
        ArbolBinario arbolActual = null;

        while (true) {
            System.out.println("Seleccione el tipo de árbol:");
            System.out.println("1. Árbol Binario de Búsqueda (BST)");
            System.out.println("2. Árbol Adelson-Velskii y Landis (AVL)");
            System.out.println("0. Salir");

            int opcion = scanner.nextInt();

            if (opcion == 0) {
                break;
            } else if (opcion == 1) {
                arbolActual = bst;
            } else if (opcion == 2) {
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

                int operacion = scanner.nextInt();

                if (operacion == 0) {
                    break;
                } else if (operacion == 1) {
                    System.out.print("Ingrese el valor a insertar: ");
                    int valorInsertar = scanner.nextInt();
                    arbolActual.insertar(valorInsertar);
                } else if (operacion == 2) {
                    System.out.print("Ingrese el valor a eliminar: ");
                    int valorEliminar = scanner.nextInt();
                    arbolActual.eliminar(valorEliminar);
                } else if (operacion == 3) {
                    System.out.println("La altura del árbol es: " + arbolActual.altura(arbolActual.cabeza));
                } else if (operacion == 4) {
                    System.out.println("La cantidad de nodos del árbol es: " + arbolActual.contarNodos(arbolActual.cabeza));
                } else if (operacion == 5) {
                    System.out.print("Recorrido in-order: ");
                    arbolActual.inOrder(arbolActual.cabeza);
                    System.out.println();
                } else if (operacion == 6) {
                    System.out.print("Recorrido pre-order: ");
                    arbolActual.preOrder(arbolActual.cabeza);
                    System.out.println();
                } else if (operacion == 7) {
                    System.out.print("Recorrido post-order: ");
                    arbolActual.postOrder(arbolActual.cabeza);
                    System.out.println();
                } else if (operacion == 8) {
                    if (arbolActual.cabeza != null) {
                        System.out.println("El valor mínimo del árbol es: " + arbolActual.minValor(arbolActual.cabeza));
                    } else {
                        System.out.println("El árbol está vacío.");
                    }
                } else if (operacion == 9) {
                    if (arbolActual.cabeza != null) {
                        System.out.println("El valor máximo del árbol es: " + arbolActual.maxValor(arbolActual.cabeza));
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

