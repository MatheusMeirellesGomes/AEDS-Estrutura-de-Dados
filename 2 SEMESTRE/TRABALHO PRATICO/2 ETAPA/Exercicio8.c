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


//Procedimento para ordenar por seleção
void selecao(Veiculo array[], int n) {

    //Percorrer todos os elementos
    for (int i = 0; i < n - 1; i++) {

        //Considerar o elemento atual como o menor
        int menor = i;

        //Procurar o menor modelo
        for (int j = i + 1; j < n; j++) {

            if (strcmp(array[j].modelo, array[menor].modelo) < 0) {
                menor = j;
            }
        }

        //Trocar os elementos
        Veiculo temp = array[i];
        array[i] = array[menor];
        array[menor] = temp;
    }
}


//Função para fazer a pesquisa binária
bool pesquisaBinaria(Veiculo array[], int n, char modelo[]) {

    int esq = 0;
    int dir = n - 1;

    //Continuar enquanto existir uma parte para pesquisar
    while (esq <= dir) {

        //Encontrar o meio
        int meio = (esq + dir) / 2;

        //Comparar o modelo procurado com o modelo do meio
        int comparacao = strcmp(modelo, array[meio].modelo);

        //Verificar se encontrou
        if (comparacao == 0) {
            return true;
        }

        //Pesquisar na metade esquerda
        if (comparacao < 0) {
            dir = meio - 1;
        }

        //Pesquisar na metade direita
        else {
            esq = meio + 1;
        }
    }

    return false;
}


int main() {

    //Criar vetor com todos os veículos
    Veiculo veiculos[MAX_VEICULOS];

    //Ler todos os veículos do arquivo
    int quantidadeVeiculos = lerArquivo(veiculos);

    //Criar vetor para os veículos escolhidos
    Veiculo selecionados[MAX_VEICULOS];

    int n = 0;
    int id;

    //Ler o primeiro id
    scanf("%d", &id);

    //Ler a primeira parte da entrada
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

    //Ordenar os veículos pelo modelo
    selecao(selecionados, n);

    //Limpar o ENTER que ficou na entrada
    getchar();

    char modelo[200];

    //Ler o primeiro modelo
    fgets(modelo, sizeof(modelo), stdin);

    //Remover a quebra de linha
    modelo[strcspn(modelo, "\r\n")] = '\0';

    //Ler os modelos até encontrar FIM
    while (strcmp(modelo, "FIM") != 0) {

        //Fazer a pesquisa binária
        if (pesquisaBinaria(selecionados, n, modelo)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        //Ler o próximo modelo
        fgets(modelo, sizeof(modelo), stdin);

        //Remover a quebra de linha
        modelo[strcspn(modelo, "\r\n")] = '\0';
    }

    return 0;
}