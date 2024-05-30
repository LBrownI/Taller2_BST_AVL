import java.util.ArrayList;

class ListaEnlazada {
    Nodo cabeza;

    ListaEnlazada() {
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
}

class Nodo {
    int value;
    Nodo partner;
    Nodo left_child;
    Nodo right_child = null;

    Nodo(int value) {
        this.value = value;
        this.partner = null;
        this.left_child = null;
        this.right_child = null;
    }
}

public class Main {

    static void imprimirLista(ListaEnlazada lista) {
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
        ListaEnlazada lista = new ListaEnlazada();
        lista.insertar(3);
        lista.insertar(2);
        lista.insertar(4);
        lista.insertar(1);
    }
}