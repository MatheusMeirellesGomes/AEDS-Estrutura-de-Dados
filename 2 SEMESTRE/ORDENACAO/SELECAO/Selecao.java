/*Mesmo código de ordenação por seleção porém na linguagem Java. */

import java.util.Scanner; 

public class Selecao {
    //Método para realizar as trocas de pos
    public static void swap (int array[], int i, int j) {//Como não existem ponteiros, passamos o vetor. 
        //Criar variável temporária
        int temp = array[i]; 
        array[i] = array[j]; 
        array[j] = temp;
    }

    //Método ordenação por seleção 
    public static void SelectionSort (int array[]) {
        //Variável para salvar o menor elemento do vetor. 
        int menor;
        int n = array.length; //indicar que n = tamanho do vetor.

        //Primeiro loop para pegar o indíce atual e servir como base de comparação
        for (int i = 0; i < n - 1; i++) {
            //Menor por enquanto é o elemento inicial do vetor
            menor = i; 

            //Segundo loop que começa uma pos a frente de i, para percorrer fazendo a comparação com o menor já salvo
            for (int j = i + 1; j < n; j++) {
                //Fazer a comparação. 
                if (array[menor] > array[j]) { //Se o menor salvo for maior que o elemento atual de j
                    menor = j; //atualizar o índice.
                }
            }

            //Chamar o método de troca
            swap(array, menor, i); //como não existe ponteiros, mandamos o array completo, o menor atualizado e o i.
        }
    }

     public static void main(String[] args) {
        //Criar um vetor com 5 elementos => n = 5. 
        int array[] = {6, 4, 8, 2, 5};

        //Chamar o método de ordenação
        SelectionSort(array);

        //Imprimir o vetor ordenado.
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}