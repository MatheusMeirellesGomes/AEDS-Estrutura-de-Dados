import java.io.*;
import java.util.Scanner;

class Data {

    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public static Data parseData(String s) {

        String[] p = s.split("-");

        return new Data(
            Integer.parseInt(p[0]),
            Integer.parseInt(p[1]),
            Integer.parseInt(p[2])
        );
    }

    public String format() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}


class Veiculo {

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

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public static Veiculo parseVeiculo(String linha) {

        String[] p = linha.split(",", -1);

        return new Veiculo(
            Integer.parseInt(p[0]),
            p[1],
            p[2],
            Integer.parseInt(p[3]),
            p[4],
            p[5].split(";"),
            Integer.parseInt(p[6]),
            Double.parseDouble(p[7]),
            p[8],
            p[9],
            Double.parseDouble(p[10]),
            Double.parseDouble(p[11]),
            Double.parseDouble(p[12]),
            Boolean.parseBoolean(p[13]),
            Data.parseData(p[14])
        );
    }

    public String format() {

        String combustiveis = "";

        for (int i = 0; i < combustivel.length; i++) {

            combustiveis += combustivel[i];

            if (i < combustivel.length - 1) {
                combustiveis += ", ";
            }
        }

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

    public static Veiculo[] ler(String caminho) throws Exception {

        BufferedReader arquivo =
            new BufferedReader(new FileReader(caminho));

        Veiculo[] array = new Veiculo[10000];

        int n = 0;

        //Ignorar cabeçalho
        arquivo.readLine();

        String linha;

        while ((linha = arquivo.readLine()) != null) {

            if (linha.length() > 0) {
                array[n++] = Veiculo.parseVeiculo(linha);
            }
        }

        arquivo.close();

        Veiculo[] resposta = new Veiculo[n];

        for (int i = 0; i < n; i++) {
            resposta[i] = array[i];
        }

        return resposta;
    }
}


//Criar célula
class Celula {

    public Veiculo elemento;
    public Celula prox;

    public Celula(Veiculo elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}


//Criar pilha
class Pilha {

    private Celula topo;

    //Inicializar pilha vazia
    public Pilha() {
        topo = null;
    }

    //Inserir no topo
    public void inserir(Veiculo veiculo) {

        Celula nova = new Celula(veiculo);

        nova.prox = topo;

        topo = nova;
    }

    //Remover do topo
    public Veiculo remover() throws Exception {

        if (topo == null) {
            throw new Exception("Pilha vazia");
        }

        Veiculo removido = topo.elemento;

        topo = topo.prox;

        return removido;
    }

    //Mostrar a partir do topo
    public void mostrar() {

        for (Celula i = topo; i != null; i = i.prox) {
            System.out.println(i.elemento.format());
        }
    }
}


public class Exercicio12 {

    public static Veiculo pesquisar(Veiculo[] veiculos, int id) {

        for (int i = 0; i < veiculos.length; i++) {

            if (veiculos[i].getId() == id) {
                return veiculos[i];
            }
        }

        return null;
    }


    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        Veiculo[] veiculos =
            LeitorCsv.ler("/tmp/veiculos.csv");

        Pilha pilha = new Pilha();

        int id = sc.nextInt();

        //Inserir os veículos iniciais
        while (id != -1) {

            pilha.inserir(pesquisar(veiculos, id));

            id = sc.nextInt();
        }

        int quantidade = sc.nextInt();

        for (int i = 0; i < quantidade; i++) {

            String comando = sc.next();

            if (comando.equals("I")) {

                id = sc.nextInt();

                pilha.inserir(pesquisar(veiculos, id));

            } else if (comando.equals("R")) {

                Veiculo removido = pilha.remover();

                System.out.println("(R) " +
                                   removido.getMarca() + " " +
                                   removido.getModelo());
            }
        }

        //Mostrar do topo para baixo
        pilha.mostrar();

        sc.close();
    }
}