/*Mesmo exercício porém na linguagem de java */

import java.util.Scanner; 

public class FilaEstatica {
    //Atributos da fila
    private int array[]; 
    private int n; 

    //Método para inicializar o tamano da fila
    public static void inicializa(int tamanho) {
        //Inicializar lista como vazia
        this.array = new int (tamanho); 
        this.n = 0; 
    }

    //Método para inserir no fiim da fila
    public static void inserir(int x) throws Exception {
        //Verificar se a fila está cheia
        if (n >= array.length) {
            throw new Exception ("Full List"); 
        }

        //Inserir no fim da fila
        array[n] = x; 
        n++; 
    }

    //Método para remover do inicio da fila
    public static void remover() throws Exception { 
        //Verificar se a fila está vazia. 
        if (n == 0) {
            throw new Exception ("Void List"); 
        }

        //Elemento removido vai ser o elemento inicial
        int elementoRem = array[0]; 

        //Deslocar os elementos para a esquerda
        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1]; 
        }

        //Decrementar o contador
        n--; 

        //Retornar o elemento removido
        return elementoRem; 
    }

    // Método auxiliar para ajudar você a testar e ver a fila
    public void mostrar() {
       //Mostrar a fila preenchida
        for (int i = 0; i < n; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}