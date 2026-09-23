/*Código que vai mostrar e explicar como funciona um algoritmo por seleção. Sua ideia principal consiste em procurar o menor elemento
da parte não ordenada do vetor, e colocá-lo na posição correta
*/

#include <stdio.h> 
#include <stdlib.h> 

/*No algoritmo de ordenação por seleção, primeiramente temos que ter um método swap que vai receber os ponteiros referentes ao menor atual 
e o menor que vai ser trocado com ele de posição, para manter ordenado */
void swap (int *a, int *b) {
    //Variável temporária para realizar a troca
    int temp = *a; 
    *a = *b; 
    *b = temp; 
}

//Ordenação por Seleção 
void selecao(int array[], int n) {
    //Variável para armazenar o menor índice. 
    int menor; 

    //Primeiro loop para percorrer o vetor. 
    for (int i = 0; i < n - 1; i++) {
        //Informar que o menor por enquanto é o índice atual, no caso o primeiro índice (0)
        menor = i; 

        //Segundo loop para percorrer a partir do próximo elemento de i. 
        for (int j = i + 1; j < n; j++) {
            //Verificar se o menor atual é maior que o valor de j
            if (array[menor] > array[j]) { //Se o menor for maior que o valor de j
                //atualiza o índice
                menor = j; 
            }
        }

        //Chama o swap para realizar a troca do menor com o i. 
        swap(&array[menor], &array[i]); 
    }
}

/* Algumas observações: 
1- O primeiro for representa a posição que estamos tentando colocar em ordem, percorrendo todo o vetor até n-1, já que se por um acaso o
n = 5 => será executado o código apenas 4 vezes, porque se os 4 primeiros elementos já estiverem ordenados, o último elemento já estará 
automaticamente correto. 

2- Indicamos que o menor elemento é o elemento inicial do vetor, para servir como base de comparação, por isso "menor = i"

3- O segundo for começa em "j + 1", justamente para ele percorrer os outros elementos do vetor, e fazer a comparação para verificar se
o elemento salvo no menor por enquanto é maior que o valor salvo na frente dele, se nao for, incrementa em j++, e vai até o fim do vetor, 
caso seja maior, aquele menor será atualizado para o valor salvo em j => "menor = j"

4- Para finalizar chama-se o método swap para realizar as trocas dentro do vetor, e incrementa o i, até que o vetor esteja completamente
ordenado
*/