#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_VEICULOS 10000
#define MAX_LINHA 1000

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int id;
    char marca[100];
    char modelo[100];
    int ano;
    char categoria[100];
    char combustivel[200];
    int cilindros;
    double cilindrada;
    char transmissao[100];
    char tracao[100];
    double consumoCidade;
    double consumoEstrada;
    double co2;
    bool turbo;
    Data dataRegistro;
} Veiculo;


//Criar a célula da lista
typedef struct Celula {
    Veiculo elemento;
    struct Celula *prox;
} Celula;


//Criar a lista
typedef struct {
    Celula *primeiro;
    Celula *ultimo;
} Lista;


Data parseData(char *s) {

    Data data;

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);

    return data;
}


Veiculo parseVeiculo(char *linha) {

    Veiculo v;

    char *p = strtok(linha, ",");

    v.id = atoi(p);

    p = strtok(NULL, ",");
    strcpy(v.marca, p);

    p = strtok(NULL, ",");
    strcpy(v.modelo, p);

    p = strtok(NULL, ",");
    v.ano = atoi(p);

    p = strtok(NULL, ",");
    strcpy(v.categoria, p);

    p = strtok(NULL, ",");
    strcpy(v.combustivel, p);

    p = strtok(NULL, ",");
    v.cilindros = atoi(p);

    p = strtok(NULL, ",");
    v.cilindrada = atof(p);

    p = strtok(NULL, ",");
    strcpy(v.transmissao, p);

    p = strtok(NULL, ",");
    strcpy(v.tracao, p);

    p = strtok(NULL, ",");
    v.consumoCidade = atof(p);

    p = strtok(NULL, ",");
    v.consumoEstrada = atof(p);

    p = strtok(NULL, ",");
    v.co2 = atof(p);

    p = strtok(NULL, ",");
    v.turbo = strcmp(p, "true") == 0;

    p = strtok(NULL, ",\n\r");
    v.dataRegistro = parseData(p);

    return v;
}


int lerArquivo(Veiculo veiculos[]) {

    FILE *arquivo = fopen("/tmp/veiculos.csv", "r");

    char linha[MAX_LINHA];

    int n = 0;

    fgets(linha, MAX_LINHA, arquivo);

    while (fgets(linha, MAX_LINHA, arquivo) != NULL) {
        veiculos[n++] = parseVeiculo(linha);
    }

    fclose(arquivo);

    return n;
}


Veiculo pesquisar(Veiculo veiculos[], int n, int id) {

    for (int i = 0; i < n; i++) {

        if (veiculos[i].id == id) {
            return veiculos[i];
        }
    }

    Veiculo vazio;
    vazio.id = -1;

    return vazio;
}


//Criar nova célula
Celula *novaCelula(Veiculo elemento) {

    Celula *nova = (Celula*) malloc(sizeof(Celula));

    nova->elemento = elemento;
    nova->prox = NULL;

    return nova;
}


//Inicializar lista
void inicializar(Lista *lista) {

    lista->primeiro = (Celula*) malloc(sizeof(Celula));

    lista->primeiro->prox = NULL;

    lista->ultimo = lista->primeiro;
}


//Inserir no início
void inserirInicio(Lista *lista, Veiculo elemento) {

    Celula *nova = novaCelula(elemento);

    nova->prox = lista->primeiro->prox;

    lista->primeiro->prox = nova;

    if (lista->ultimo == lista->primeiro) {
        lista->ultimo = nova;
    }
}


//Inserir no fim
void inserirFim(Lista *lista, Veiculo elemento) {

    lista->ultimo->prox = novaCelula(elemento);

    lista->ultimo = lista->ultimo->prox;
}


//Inserir em uma posição
void inserir(Lista *lista, Veiculo elemento, int posicao) {

    Celula *i = lista->primeiro;

    //Percorrer até a posição anterior
    for (int j = 0; j < posicao; j++) {
        i = i->prox;
    }

    Celula *nova = novaCelula(elemento);

    nova->prox = i->prox;

    i->prox = nova;

    if (nova->prox == NULL) {
        lista->ultimo = nova;
    }
}


//Remover do início
Veiculo removerInicio(Lista *lista) {

    Celula *tmp = lista->primeiro->prox;

    Veiculo removido = tmp->elemento;

    lista->primeiro->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }

    free(tmp);

    return removido;
}


//Remover do fim
Veiculo removerFim(Lista *lista) {

    Celula *i = lista->primeiro;

    while (i->prox != lista->ultimo) {
        i = i->prox;
    }

    Veiculo removido = lista->ultimo->elemento;

    free(lista->ultimo);

    lista->ultimo = i;

    lista->ultimo->prox = NULL;

    return removido;
}


//Remover de uma posição
Veiculo remover(Lista *lista, int posicao) {

    Celula *i = lista->primeiro;

    for (int j = 0; j < posicao; j++) {
        i = i->prox;
    }

    Celula *tmp = i->prox;

    Veiculo removido = tmp->elemento;

    i->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = i;
    }

    free(tmp);

    return removido;
}


void mostrarCombustivel(char combustivel[]) {

    char copia[200];

    strcpy(copia, combustivel);

    char *p = strtok(copia, ";");

    int primeiro = 1;

    printf("[");

    while (p != NULL) {

        if (!primeiro) {
            printf(", ");
        }

        printf("%s", p);

        primeiro = 0;

        p = strtok(NULL, ";");
    }

    printf("]");
}


void mostrarVeiculo(Veiculo v) {

    printf("[%d ## %s ## %s ## %d ## %s ## ",
           v.id, v.marca, v.modelo, v.ano, v.categoria);

    mostrarCombustivel(v.combustivel);

    printf(" ## %d ## %.1lf ## %s ## %s ## %.1lf ## %.1lf ## %.1lf ## %s ## %02d/%02d/%04d]\n",
           v.cilindros,
           v.cilindrada,
           v.transmissao,
           v.tracao,
           v.consumoCidade,
           v.consumoEstrada,
           v.co2,
           v.turbo ? "true" : "false",
           v.dataRegistro.dia,
           v.dataRegistro.mes,
           v.dataRegistro.ano);
}


void mostrar(Lista *lista) {

    Celula *i = lista->primeiro->prox;

    while (i != NULL) {

        mostrarVeiculo(i->elemento);

        i = i->prox;
    }
}


int main() {

    Veiculo veiculos[MAX_VEICULOS];

    int total = lerArquivo(veiculos);

    Lista lista;

    inicializar(&lista);

    int id;

    scanf("%d", &id);

    while (id != -1) {

        Veiculo v = pesquisar(veiculos, total, id);

        inserirFim(&lista, v);

        scanf("%d", &id);
    }

    int quantidade;

    scanf("%d", &quantidade);

    for (int i = 0; i < quantidade; i++) {

        char comando[10];

        scanf("%s", comando);

        if (strcmp(comando, "II") == 0) {

            scanf("%d", &id);

            inserirInicio(&lista, pesquisar(veiculos, total, id));

        } else if (strcmp(comando, "IF") == 0) {

            scanf("%d", &id);

            inserirFim(&lista, pesquisar(veiculos, total, id));

        } else if (strcmp(comando, "I*") == 0) {

            int posicao;

            scanf("%d %d", &posicao, &id);

            inserir(&lista,
                    pesquisar(veiculos, total, id),
                    posicao);

        } else if (strcmp(comando, "RI") == 0) {

            Veiculo r = removerInicio(&lista);

            printf("(R) %s %s\n", r.marca, r.modelo);

        } else if (strcmp(comando, "RF") == 0) {

            Veiculo r = removerFim(&lista);

            printf("(R) %s %s\n", r.marca, r.modelo);

        } else if (strcmp(comando, "R*") == 0) {

            int posicao;

            scanf("%d", &posicao);

            Veiculo r = remover(&lista, posicao);

            printf("(R) %s %s\n", r.marca, r.modelo);
        }
    }

    mostrar(&lista);

    return 0;
}