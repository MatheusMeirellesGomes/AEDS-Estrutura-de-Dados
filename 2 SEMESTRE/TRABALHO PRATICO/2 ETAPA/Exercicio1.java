import java.io.*;
import java.util.Scanner;

class Data {

    //Atributos da data
    private int ano;
    private int mes;
    private int dia;

    //Construtor da data
    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    //Métodos get
    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    //Método para transformar String em Data
    public static Data parseData(String s) {
        String[] partes = s.split("-");

        int ano = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        return new Data(ano, mes, dia);
    }

    //Método para formatar a data
    public String format() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}


class Veiculo {

    //Atributos do veículo
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    //Construtor do veículo
    public Veiculo(int id, String marca, String modelo, int ano,
                   String categoria, String[] combustivel, int cilindros,
                   double cilindrada, String transmissao, String tracao,
                   double consumoCidade, double consumoEstrada, double co2,
                   boolean turbo, Data dataRegistro) {

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.transmissao = transmissao;
        this.tracao = tracao;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
    }

    //Métodos get
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public String[] getCombustivel() {
        return combustivel;
    }

    public int getCilindros() {
        return cilindros;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public String getTransmissao() {
        return transmissao;
    }

    public String getTracao() {
        return tracao;
    }

    public double getConsumoCidade() {
        return consumoCidade;
    }

    public double getConsumoEstrada() {
        return consumoEstrada;
    }

    public double getCo2() {
        return co2;
    }

    public boolean getTurbo() {
        return turbo;
    }

    public Data getDataRegistro() {
        return dataRegistro;
    }

    //Método para transformar a linha do arquivo em veículo
    public static Veiculo parseVeiculo(String linha) {

        //Separar os atributos
        String[] partes = linha.split(",", -1);

        int id = Integer.parseInt(partes[0]);
        String marca = partes[1];
        String modelo = partes[2];
        int ano = Integer.parseInt(partes[3]);
        String categoria = partes[4];

        //Separar os combustíveis
        String[] combustivel = partes[5].split(";");

        int cilindros = Integer.parseInt(partes[6]);
        double cilindrada = Double.parseDouble(partes[7]);
        String transmissao = partes[8];
        String tracao = partes[9];
        double consumoCidade = Double.parseDouble(partes[10]);
        double consumoEstrada = Double.parseDouble(partes[11]);
        double co2 = Double.parseDouble(partes[12]);
        boolean turbo = Boolean.parseBoolean(partes[13]);

        //Transformar a String em data
        Data dataRegistro = Data.parseData(partes[14]);

        //Retornar o veículo criado
        return new Veiculo(id, marca, modelo, ano, categoria,
                combustivel, cilindros, cilindrada, transmissao,
                tracao, consumoCidade, consumoEstrada, co2,
                turbo, dataRegistro);
    }

    //Método para formatar o veículo
    public String format() {

        //Montar os combustíveis
        String combustiveis = "";

        for (int i = 0; i < combustivel.length; i++) {
            combustiveis += combustivel[i];

            if (i < combustivel.length - 1) {
                combustiveis += ", ";
            }
        }

        //Retornar o veículo formatado
        return "[" + id +
                " ## " + marca +
                " ## " + modelo +
                " ## " + ano +
                " ## " + categoria +
                " ## [" + combustiveis + "]" +
                " ## " + cilindros +
                " ## " + cilindrada +
                " ## " + transmissao +
                " ## " + tracao +
                " ## " + consumoCidade +
                " ## " + consumoEstrada +
                " ## " + co2 +
                " ## " + turbo +
                " ## " + dataRegistro.format() + "]";
    }
}


class LeitorCsv {

    //Método para ler o arquivo CSV
    public static Veiculo[] ler(String caminhoArquivo) throws Exception {

        //Abrir o arquivo
        BufferedReader arquivo =
                new BufferedReader(new FileReader(caminhoArquivo));

        //Criar o vetor de veículos
        Veiculo[] veiculos = new Veiculo[10000];

        int n = 0;

        //Ignorar o cabeçalho
        arquivo.readLine();

        String linha;

        //Ler todas as linhas do arquivo
        while ((linha = arquivo.readLine()) != null) {

            if (linha.length() > 0) {
                veiculos[n] = Veiculo.parseVeiculo(linha);
                n++;
            }
        }

        arquivo.close();

        //Criar um vetor com o tamanho utilizado
        Veiculo[] resposta = new Veiculo[n];

        //Copiar os veículos
        for (int i = 0; i < n; i++) {
            resposta[i] = veiculos[i];
        }

        return resposta;
    }
}


public class Exercicio1 {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        //Ler os veículos do arquivo
        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        //Ler o primeiro id
        int id = sc.nextInt();

        //Continuar enquanto o id for diferente de -1
        while (id != -1) {

            //Fazer a pesquisa sequencial
            for (int i = 0; i < veiculos.length; i++) {

                //Verificar se encontrou o veículo
                if (veiculos[i].getId() == id) {

                    //Mostrar o veículo
                    System.out.println(veiculos[i].format());

                    break;
                }
            }

            //Ler o próximo id
            id = sc.nextInt();
        }

        sc.close();
    }
}