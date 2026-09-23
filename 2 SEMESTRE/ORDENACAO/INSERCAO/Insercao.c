/* A ideia principal do inserção consiste em considerar uma parte já ordenada do vetor e inserir o próximo elemento na pos correta
dessa parte, como se vc tivesse organizando cartas de um baralho na mão. 
*/

#include <stdio.h> 
#include <stdlib.h> 

//Método de ordenação por inserção 
void insercao(int array[], int n) { //novamente passamos o array[com os elementos], e o tamanho dele.
    //Criar uma variável temporária para armazenar o elemento que será inserido. 
    int temp; 

    //Primeiro loop começa na segunda pos do vetor, pois consideramos a pos inicial como ordenada.
    for (int i = 1; i < n; i++) {
        //Salvar o elemento atual na variável temporária, para fazer a comparação depois. 
        temp = array[i]; 

        //O j começa uma pos antes do i, parte "ordenada"
        int j = i - 1; 

        //Enquanto estiver dentro do vetor (pos válida) e o elemento da pos j for maior que temp, deslocamos esse elemento para a direita
        while (j >= 0 && array[j] > temp) {
            //Deslocar o elemento maior para a direita
            array[j + 1] = array[j]; 

            //Decrementar o contador (voltar uma pos) para continuar procurando
            j--; 
        }

        //Quando o while finalizar e não tiver mais elementos maiores que o elemento salvo em temp 
        array[j + 1] = temp; //Inserir na pos correta o elemento salvo na variáve temp
    }
}