import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> result = new ArrayList<>();
        preOrder(lista.cabeza, result);
        for (String line : result) {
            System.out.println(line);
        }
    }

    static void preOrder(Nodo node, List<String> result) {
        if (node == null) {
            return;
        }

        String parentValue;
        if (node.partner != null){
            parentValue = String.valueOf(node.partner.value);
        }
        else {
            parentValue = "NIL";
        }

        String nodeValue = String.valueOf(node.value);

        String leftOrRightChild = "(root)";
        if (node.partner != null){
            if (node.value < node.partner.value){
                leftOrRightChild = "(left)";
            }
            else {
                leftOrRightChild = "(right)";
            }
        }

        result.add("Parent: " + parentValue + " <--- Node: " + nodeValue + " " +leftOrRightChild);
        preOrder(node.left_child, result);
        preOrder(node.right_child, result);
    }


    public static void main(String[] args) {
        ListaEnlazada lista = new ListaEnlazada();
        lista.insertar(7);
        lista.insertar(6);
        lista.insertar(4);
        lista.insertar(5);
        lista.insertar(9);
        lista.insertar(8);
        lista.insertar(11);
        imprimirLista(lista);
    }
}