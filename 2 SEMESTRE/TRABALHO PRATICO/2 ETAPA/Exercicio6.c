#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_VEICULOS 10000
#define MAX_LINHA 1000

//Criar a struct para a data
typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

//Criar a struct para o veículo
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


//Função para transformar uma String em Data
Data parseData(char *s) {

    Data data;

    //Separar ano, mês e dia
    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);

    return data;
}


//Função para transformar uma linha do CSV em veículo
Veiculo parseVeiculo(char *linha) {

    Veiculo veiculo;

    //Separar os atributos da linha
    char *parte = strtok(linha, ",");

    //Ler o id
    veiculo.id = atoi(parte);

    //Ler a marca
    parte = strtok(NULL, ",");
    strcpy(veiculo.marca, parte);

    //Ler o modelo
    parte = strtok(NULL, ",");
    strcpy(veiculo.modelo, parte);

    //Ler o ano
    parte = strtok(NULL, ",");
    veiculo.ano = atoi(parte);

    //Ler a categoria
    parte = strtok(NULL, ",");
    strcpy(veiculo.categoria, parte);

    //Ler o combustível
    parte = strtok(NULL, ",");
    strcpy(veiculo.combustivel, parte);

    //Ler os cilindros
    parte = strtok(NULL, ",");
    veiculo.cilindros = atoi(parte);

    //Ler a cilindrada
    parte = strtok(NULL, ",");
    veiculo.cilindrada = atof(parte);

    //Ler a transmissão
    parte = strtok(NULL, ",");
    strcpy(veiculo.transmissao, parte);

    //Ler a tração
    parte = strtok(NULL, ",");
    strcpy(veiculo.tracao, parte);

    //Ler o consumo da cidade
    parte = strtok(NULL, ",");
    veiculo.consumoCidade = atof(parte);

    //Ler o consumo da estrada
    parte = strtok(NULL, ",");
    veiculo.consumoEstrada = atof(parte);

    //Ler o CO2
    parte = strtok(NULL, ",");
    veiculo.co2 = atof(parte);

    //Ler o turbo
    parte = strtok(NULL, ",");

    if (strcmp(parte, "true") == 0) {
        veiculo.turbo = true;
    } else {
        veiculo.turbo = false;
    }

    //Ler a data
    parte = strtok(NULL, ",\n\r");
    veiculo.dataRegistro = parseData(parte);

    return veiculo;
}


//Função para ler todos os veículos do arquivo
int lerArquivo(Veiculo veiculos[]) {

    //Abrir o arquivo
    FILE *arquivo = fopen("/tmp/veiculos.csv", "r");

    char linha[MAX_LINHA];
    int n = 0;

    //Ignorar o cabeçalho
    fgets(linha, MAX_LINHA, arquivo);

    //Ler todas as linhas
    while (fgets(linha, MAX_LINHA, arquivo) != NULL) {

        //Transformar a linha em veículo
        veiculos[n] = parseVeiculo(linha);

        n++;
    }

    //Fechar o arquivo
    fclose(arquivo);

    return n;
}


//Procedimento para mostrar os combustíveis
void mostrarCombustivel(char combustivel[]) {

    char copia[200];

    //Copiar os combustíveis
    strcpy(copia, combustivel);

    //Separar os combustíveis
    char *parte = strtok(copia, ";");

    int primeiro = 1;

    printf("[");

    while (parte != NULL) {

        if (primeiro == 0) {
            printf(", ");
        }

        printf("%s", parte);

        primeiro = 0;

        parte = strtok(NULL, ";");
    }

    printf("]");
}


//Procedimento para mostrar um veículo
void mostrar(Veiculo veiculo) {

    printf("[%d ## %s ## %s ## %d ## %s ## ",
           veiculo.id,
           veiculo.marca,
           veiculo.modelo,
           veiculo.ano,
           veiculo.categoria);

    //Mostrar os combustíveis
    mostrarCombustivel(veiculo.combustivel);

    printf(" ## %d ## %.1lf ## %s ## %s ## %.1lf ## %.1lf ## %.1lf ## %s ## %02d/%02d/%04d]\n",
           veiculo.cilindros,
           veiculo.cilindrada,
           veiculo.transmissao,
           veiculo.tracao,
           veiculo.consumoCidade,
           veiculo.consumoEstrada,
           veiculo.co2,
           veiculo.turbo ? "true" : "false",
           veiculo.dataRegistro.dia,
           veiculo.dataRegistro.mes,
           veiculo.dataRegistro.ano);
}


//Função para encontrar o maior ano
int getMaior(Veiculo array[], int n) {

    //Considerar o primeiro ano como maior
    int maior = array[0].ano;

    //Percorrer os elementos
    for (int i = 1; i < n; i++) {

        if (array[i].ano > maior) {
            maior = array[i].ano;
        }
    }

    return maior;
}


//Procedimento para ordenar um dígito do ano
void countingSort(Veiculo array[], int n, int exp) {

    //Criar vetor ordenado
    Veiculo ordenado[n];

    //Criar vetor de contagem
    int count[10];

    //Inicializar o vetor de contagem
    for (int i = 0; i < 10; i++) {
        count[i] = 0;
    }

    //Contar a quantidade de cada dígito
    for (int i = 0; i < n; i++) {

        int digito = (array[i].ano / exp) % 10;

        count[digito]++;
    }

    //Fazer o acumulado
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    //Colocar os elementos nas posições corretas
    for (int i = n - 1; i >= 0; i--) {

        int digito = (array[i].ano / exp) % 10;

        ordenado[count[digito] - 1] = array[i];

        count[digito]--;
    }

    //Copiar para o vetor original
    for (int i = 0; i < n; i++) {
        array[i] = ordenado[i];
    }
}


//Procedimento para ordenar usando Radixsort
void radixsort(Veiculo array[], int n) {

    //Verificar se existem elementos
    if (n > 0) {

        //Encontrar o maior ano
        int maior = getMaior(array, n);

        //Ordenar cada dígito
        for (int exp = 1; maior / exp > 0; exp *= 10) {
            countingSort(array, n, exp);
        }
    }
}


int main() {

    //Criar vetor com todos os veículos
    Veiculo veiculos[MAX_VEICULOS];

    //Ler o arquivo
    int quantidadeVeiculos = lerArquivo(veiculos);

    //Criar vetor para os veículos escolhidos
    Veiculo selecionados[MAX_VEICULOS];

    int n = 0;
    int id;

    //Ler o primeiro id
    scanf("%d", &id);

    //Continuar enquanto o id for diferente de -1
    while (id != -1) {

        //Fazer a pesquisa sequencial pelo id
        for (int i = 0; i < quantidadeVeiculos; i++) {

            //Verificar se encontrou o veículo
            if (veiculos[i].id == id) {

                //Adicionar o veículo no vetor
                selecionados[n] = veiculos[i];

                n++;

                break;
            }
        }

        //Ler o próximo id
        scanf("%d", &id);
    }

    //Ordenar os veículos pelo ano
    radixsort(selecionados, n);

    //Mostrar os veículos ordenados
    for (int i = 0; i < n; i++) {
        mostrar(selecionados[i]);
    }

    return 0;
}