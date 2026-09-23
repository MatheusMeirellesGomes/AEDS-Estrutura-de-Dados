/*Código para mostrar como funciona uma fila estática, basicamente a FILA consiste em adicionar um elemento no fim e remover do início
FIFO = First In First Out, como se imaginassimos uma fala onde o primeiro que entra é o primeiro que sai, e as pessoas vão chegando atras, 
por isso o inserir é no início.
*/

#include <stdio.h> 
#include <stdlib.h> 

//Definir tamanho da lista
#define MAX 7

//Struct para armazenar o array[] e a o tamanho da fila
typedef struct { 
    int array[MAX]; //vetor que guarda a capacidade max da lista
    int n; //quantidade de elementos da lista
} Fila; 

//incializar fila
void inicializa (Fila *fila) {
    //Inicializar quantidade de elementos como 0
    fila->n = 0; 
}

//Procedimento para inserir no fim da fila
void inserir (Fila *fila, int elemento) {
    //Verificar se a fila está cheia
    if (fila->n < MAX) {
        //Adicionar elemento no fim da fila 
        fila->array[fila->n] = elemento; 
        fila->n++; 
    } else {
        printf("Full List"); 
    }
}

//Procedimento para remover do início da lista. 
void remover (Fila *fila) {
    //Variável para armazenar o elemento removido da fila
    int elementoRem; 

    //Verificar se a fila está vazia
    if (fila->n > 0) {
        //Elemento removido vai ser o elemento da pos inicial. 
        elementoRem = fila->array[0]; 

        //Deslocar os elementos para esquerda
        for (int i = 0; i < fila->n; i++) {
            fila->array[i] = fila->array[i + 1]; 
        }

        //Decrementar o contador, para diminuir a quantidade de elementos
        fila->n--; 
    } else {
        printf("Void List"); 
    }

    return elementoRem; 
}

//Main
int main() {
    Fila fila; 

    //Chamar método para incializar fila
    inicializa (&fila); 

    //Métodos para inserir e remover... 
}