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
        String[] partes = s.split("-");
        return new Data(Integer.parseInt(partes[0]),
                        Integer.parseInt(partes[1]),
                        Integer.parseInt(partes[2]));
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

        BufferedReader arquivo = new BufferedReader(new FileReader(caminho));

        Veiculo[] veiculos = new Veiculo[10000];
        int n = 0;

        //Ignorar cabeçalho
        arquivo.readLine();

        String linha;

        while ((linha = arquivo.readLine()) != null) {
            if (linha.length() > 0) {
                veiculos[n] = Veiculo.parseVeiculo(linha);
                n++;
            }
        }

        arquivo.close();

        Veiculo[] resposta = new Veiculo[n];

        for (int i = 0; i < n; i++) {
            resposta[i] = veiculos[i];
        }

        return resposta;
    }
}

class Lista {

    private Veiculo[] array;
    private int n;

    //Inicializar a lista
    public Lista(int tamanho) {
        array = new Veiculo[tamanho];
        n = 0;
    }

    //Inserir no início
    public void inserirInicio(Veiculo veiculo) throws Exception {

        if (n >= array.length) {
            throw new Exception("Lista cheia");
        }

        //Deslocar para a direita
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = veiculo;
        n++;
    }

    //Inserir em uma posição
    public void inserir(Veiculo veiculo, int posicao) throws Exception {

        if (n >= array.length || posicao < 0 || posicao >= n) {
            throw new Exception("Erro");
        }

        //Deslocar para a direita
        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }

        array[posicao] = veiculo;
        n++;
    }

    //Inserir no fim
    public void inserirFim(Veiculo veiculo) throws Exception {

        if (n >= array.length) {
            throw new Exception("Lista cheia");
        }

        array[n] = veiculo;
        n++;
    }

    //Remover do início
    public Veiculo removerInicio() throws Exception {

        if (n == 0) {
            throw new Exception("Lista vazia");
        }

        Veiculo removido = array[0];

        //Deslocar para a esquerda
        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        n--;

        return removido;
    }

    //Remover de uma posição
    public Veiculo remover(int posicao) throws Exception {

        if (n == 0 || posicao < 0 || posicao >= n) {
            throw new Exception("Erro");
        }

        Veiculo removido = array[posicao];

        //Deslocar para a esquerda
        for (int i = posicao; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        n--;

        return removido;
    }

    //Remover do fim
    public Veiculo removerFim() throws Exception {

        if (n == 0) {
            throw new Exception("Lista vazia");
        }

        Veiculo removido = array[n - 1];
        n--;

        return removido;
    }

    //Mostrar a lista
    public void mostrar() {

        for (int i = 0; i < n; i++) {
            System.out.println(array[i].format());
        }
    }
}

public class Exercicio9 {

    //Pesquisar veículo pelo id
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

        Veiculo[] veiculos = LeitorCsv.ler("/tmp/veiculos.csv");

        Lista lista = new Lista(10000);

        //Primeira parte da entrada
        int id = sc.nextInt();

        while (id != -1) {

            Veiculo veiculo = pesquisar(veiculos, id);

            if (veiculo != null) {
                lista.inserirFim(veiculo);
            }

            id = sc.nextInt();
        }

        //Quantidade de comandos
        int quantidade = sc.nextInt();

        for (int i = 0; i < quantidade; i++) {

            String comando = sc.next();

            if (comando.equals("II")) {

                id = sc.nextInt();

                lista.inserirInicio(pesquisar(veiculos, id));

            } else if (comando.equals("IF")) {

                id = sc.nextInt();

                lista.inserirFim(pesquisar(veiculos, id));

            } else if (comando.equals("I*")) {

                int posicao = sc.nextInt();
                id = sc.nextInt();

                lista.inserir(pesquisar(veiculos, id), posicao);

            } else if (comando.equals("RI")) {

                Veiculo removido = lista.removerInicio();

                System.out.println("(R) " +
                                   removido.getMarca() + " " +
                                   removido.getModelo());

            } else if (comando.equals("RF")) {

                Veiculo removido = lista.removerFim();

                System.out.println("(R) " +
                                   removido.getMarca() + " " +
                                   removido.getModelo());

            } else if (comando.equals("R*")) {

                int posicao = sc.nextInt();

                Veiculo removido = lista.remover(posicao);

                System.out.println("(R) " +
                                   removido.getMarca() + " " +
                                   removido.getModelo());
            }
        }

        lista.mostrar();

        sc.close();
    }
}