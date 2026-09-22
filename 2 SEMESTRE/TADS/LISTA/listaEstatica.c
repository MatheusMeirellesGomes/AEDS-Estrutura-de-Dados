#include <stdio.h> 
#include <stdlib.h> 
#define MAX 7

//Criar o strucut para lista
typedef struct {
    int n; //tamanho da lista
    int array[MAX]; //onde vai ficar armazenado cada elemento
}Lista; 

//Inicialziar lista
void inicializa (Lista *lista) {
    lista->n = 0; //indicar que a lista está vazia.
}

//Procedimento para inserir no início da lista
void inserirInicio (Lista *lista, int elemento) {
    //Verificar se a lista não está cheia. 
    if (lista->n < MAX) { //Se a lista não estiver cheia
        //Deslocar todos os elementos para a direita
        for (int i = lista->n; i > 0; i--) {
            lista->array[i] = lista->array[i - 1]; //o próximo lugar do vetor recebe o elemento da posicao anterior. 
        }

        //Inserir o elemento no início da lista
        lista->array[0] = elemento; 
        lista->n++; 
    } else {
        printf("Lista Cheia"); 
    }
}

//Procedimento para inserir na posição. 
void inserirPos (Lista *lista, int elemento, int pos) {
    //Verificar se a pos é válida e a lista não está vazia 
    if (pos >= 0 && pos < lista->n && lista->n < MAX) {
        //Deslocar todos os elementos para a direita. 
        for (int i = lista->n; i > pos; i--) {
            lista->array[i] = lista->array[i - 1]; //O próxmo lugar no vetor recebe o elemento da pos anterior. 
        }

        //Inserir o elemento na pos desejada
        lista->array[pos] = elemento; 
        lista->n++; 
    } else {
        printf("Lista Cheia ou Pos inválida"); 
    }
}

//Procedimento para inserir no fim da lista. 
void inserirFim (Lista *lista, int elemento) {
    //Verificar se a lista não está cheia. 
    if (lista->n < MAX) {
        lista->array[lista->n] = elemento; 
        lista->n++; 
    } else {
        printf("Lista Cheia"); 
    }
}

//Função para remover um elemento do início. 
int removerInicio (Lista *lista) {
    //Criar variável para armazenar o elemento removido. 
    int elementoRemovido; 

    //Verificar se a lista não está vazia
    if (lista->n > 0) {
        //O elemento removido vai ser o elemento da posição inicial da lista. 
        elementoRemovido = lista->array[0]; 

        //Deslocar todos os elementos para a esquerda. 
        for (int i = 0; i < lista->n - 1; i++) {
            lista->array[i] = lista->array[i + 1]; //a pos atual vai receber o elemento da posição na frente dele, fazendo assim a movimentaçõa para a esquerda.
        }

        lista->n--; //decrementar o contador; 
    } else {
        printf("Lista Vazia"); 
    }

    //Retornar o elemento removido. 
    return elementoRemovido; 
}

//Função para remover o elemento na posição desejada. 
int removerPos (Lista *lista, int pos) {
    //Variável para armazenar o elemento removido. 
    int elementoRemovido; 

    //Verificar se a posição é válida e se a lista não está vazia. 
    if (pos >= 0 && pos < lista->n && lista->n > 0) {
        //Remover o elemento da pos desejada
        elementoRemovido = lista->array[pos]; 

        //Deslocar todos os elementos para a esquerda do vetor. 
        for (int i = pos; i < lista->n - 1; i++) {
            lista->array[i] = lista->array[i + 1]; 
        }

        lista->n--; //Decrementar o n, para a quantidade atual de elementos. 
    } else {
        printf("Lista Cheia"); 
    }

    return elementoRemovido; 
}

//Função para remover o elemento no fim da lista. 
int removerFim (Lista *lista) {
    //Variável para guardar o elemento removido. 
    int elementoRemovido; 

    //Verificar se a lista não está vazia. 
    if (lista->n > 0) {
        elementoRemovido = lista->array[lista->n - 1]; 
        lista->n--; 
    } else {
        printf("Lista Vazia"); 
    }

    return elementoRemovido; 
}

//Procedimento para mostrar a lista
void mostrar(Lista *lista) {
    printf("\nLista:"); 

    //Verificar se a lista nao esta vazia.
    if (lista->n > 0) {
        //Percorrer a lista completa
        for (int i = 0; i < lista->n; i++) {
            //Printar cada elemento da lista.
            printf("%d ", lista->array[i]); 
        }
    }
}

int main() {
    //Criar uma lista do tipo Lista
    Lista lista; 

    //Inicialziar a lista
    inicializa(&lista); 

    inserirFim(&lista, 10);
    inserirFim(&lista, 20);
    inserirFim(&lista, 30);

    inserirInicio(&lista, 5);

    inserirPos(&lista, 15, 2);

    mostrar(&lista);

    removerInicio(&lista);
    removerFim(&lista);

    mostrar(&lista);
}