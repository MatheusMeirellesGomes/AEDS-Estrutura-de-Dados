#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_VEICULOS 10000
#define MAX_LINHA 1000
#define TAM 6

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

typedef struct {
    Veiculo array[TAM];
    int primeiro;
    int ultimo;
} Fila;


Data parseData(char *s) {

    Data data;

    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);

    return data;
}


Veiculo parseVeiculo(char *linha) {

    Veiculo v;

    char *parte = strtok(linha, ",");

    v.id = atoi(parte);

    parte = strtok(NULL, ",");
    strcpy(v.marca, parte);

    parte = strtok(NULL, ",");
    strcpy(v.modelo, parte);

    parte = strtok(NULL, ",");
    v.ano = atoi(parte);

    parte = strtok(NULL, ",");
    strcpy(v.categoria, parte);

    parte = strtok(NULL, ",");
    strcpy(v.combustivel, parte);

    parte = strtok(NULL, ",");
    v.cilindros = atoi(parte);

    parte = strtok(NULL, ",");
    v.cilindrada = atof(parte);

    parte = strtok(NULL, ",");
    strcpy(v.transmissao, parte);

    parte = strtok(NULL, ",");
    strcpy(v.tracao, parte);

    parte = strtok(NULL, ",");
    v.consumoCidade = atof(parte);

    parte = strtok(NULL, ",");
    v.consumoEstrada = atof(parte);

    parte = strtok(NULL, ",");
    v.co2 = atof(parte);

    parte = strtok(NULL, ",");

    if (strcmp(parte, "true") == 0) {
        v.turbo = true;
    } else {
        v.turbo = false;
    }

    parte = strtok(NULL, ",\n\r");
    v.dataRegistro = parseData(parte);

    return v;
}


int lerArquivo(Veiculo veiculos[]) {

    FILE *arquivo = fopen("/tmp/veiculos.csv", "r");

    char linha[MAX_LINHA];
    int n = 0;

    //Ignorar cabeçalho
    fgets(linha, MAX_LINHA, arquivo);

    while (fgets(linha, MAX_LINHA, arquivo) != NULL) {
        veiculos[n] = parseVeiculo(linha);
        n++;
    }

    fclose(arquivo);

    return n;
}


//Pesquisar veículo pelo id
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


//Inicializar a fila
void inicializar(Fila *fila) {
    fila->primeiro = 0;
    fila->ultimo = 0;
}


//Verificar se a fila está vazia
bool vazia(Fila *fila) {
    return fila->primeiro == fila->ultimo;
}


//Verificar se a fila está cheia
bool cheia(Fila *fila) {
    return ((fila->ultimo + 1) % TAM) == fila->primeiro;
}


//Remover do início
Veiculo remover(Fila *fila) {

    Veiculo removido = fila->array[fila->primeiro];

    fila->primeiro = (fila->primeiro + 1) % TAM;

    return removido;
}


//Inserir no fim
void inserir(Fila *fila, Veiculo veiculo) {

    //Se estiver cheia remover o primeiro
    if (cheia(fila)) {
        remover(fila);
    }

    fila->array[fila->ultimo] = veiculo;

    fila->ultimo = (fila->ultimo + 1) % TAM;
}


void mostrarCombustivel(char combustivel[]) {

    char copia[200];

    strcpy(copia, combustivel);

    char *parte = strtok(copia, ";");

    int primeiro = 1;

    printf("[");

    while (parte != NULL) {

        if (!primeiro) {
            printf(", ");
        }

        printf("%s", parte);

        primeiro = 0;

        parte = strtok(NULL, ";");
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


//Mostrar a fila do primeiro ao último
void mostrar(Fila *fila) {

    int i = fila->primeiro;

    while (i != fila->ultimo) {

        mostrarVeiculo(fila->array[i]);

        i = (i + 1) % TAM;
    }
}


int main() {

    Veiculo veiculos[MAX_VEICULOS];

    int total = lerArquivo(veiculos);

    Fila fila;

    inicializar(&fila);

    int id;

    //Primeira parte
    scanf("%d", &id);

    while (id != -1) {

        Veiculo v = pesquisar(veiculos, total, id);

        if (v.id != -1) {
            inserir(&fila, v);
        }

        scanf("%d", &id);
    }

    int quantidade;

    scanf("%d", &quantidade);

    for (int i = 0; i < quantidade; i++) {

        char comando[10];

        scanf("%s", comando);

        if (strcmp(comando, "I") == 0) {

            scanf("%d", &id);

            Veiculo v = pesquisar(veiculos, total, id);

            //Se estiver cheia mostrar o removido
            if (cheia(&fila)) {

                Veiculo removido = remover(&fila);

                printf("(R) %s %s\n",
                       removido.marca,
                       removido.modelo);
            }

            inserir(&fila, v);

        } else if (strcmp(comando, "R") == 0) {

            Veiculo removido = remover(&fila);

            printf("(R) %s %s\n",
                   removido.marca,
                   removido.modelo);
        }
    }

    mostrar(&fila);

    return 0;
}