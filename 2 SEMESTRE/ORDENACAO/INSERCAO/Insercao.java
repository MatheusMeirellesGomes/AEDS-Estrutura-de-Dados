/*Mesmo método de ordenação que é o inserção, porém na linguagem java */

import java.util.Scanner; 

public class Insercao {
    public static void insercao(int array[]) {
        //Variável temporária
        int temp; 

        //Primeiro loop começa a partir da segunda pos 
        for (int i = 1; i < n; i++) {
            //Salvar o elemento da segunda posição na variável temp para fazer a comparação. 
            temp = array[i]; 

            //J começa na pos anterior 
            int j = i - 1; 
            
            //Enquanto j for maior que 0 (ter espaço no vetor) e o elemento salvo em j for maior que temp
            while (j >= 0 && array[j] > temp) {
                //Deslocar o elemento
                array[j + 1] = array[j]; 

                //Decrementar o contador para voltar uma pos
                j--; 
            }

            //Realizar a troca e colocar o elemento salvo em temp no lugar certo. 
            array[j + 1] = temp;
        }
    }
}