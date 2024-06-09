import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<Nodo[]> a = new ArrayList<>();
        Nodo[] b = new Nodo[1];
        b[0] = actual;
        a.add(b);

        System.out.println(b[0].value);

        Nodo[] c = new Nodo[2];
        c[0] = a.getFirst()[0].left_child;
        c[1] = a.getFirst()[0].right_child;

        a.add(c);

        System.out.println(Arrays.toString(a.get(0)));
        System.out.println(Arrays.toString(a.get(1)));

//        while (actual.left_child != null) {
//            actual = actual.left_child;
//            System.out.println(actual.value);
//        }
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
        imprimirLista(lista);
    }
}