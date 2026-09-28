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


//Criar célula dupla
class CelulaDupla {

    public Veiculo elemento;
    public CelulaDupla ant;
    public CelulaDupla prox;

    public CelulaDupla(Veiculo elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.prox = null;
    }
}


//Criar lista dupla
class ListaDupla {

    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    //Inicializar lista
    public ListaDupla() {

        primeiro = new CelulaDupla(null);

        ultimo = primeiro;
    }

    //Inserir no início
    public void inserirInicio(Veiculo veiculo) {

        CelulaDupla nova = new CelulaDupla(veiculo);

        nova.ant = primeiro;
        nova.prox = primeiro.prox;

        if (primeiro.prox != null) {
            primeiro.prox.ant = nova;
        } else {
            ultimo = nova;
        }

        primeiro.prox = nova;
    }

    //Inserir no fim
    public void inserirFim(Veiculo veiculo) {

        CelulaDupla nova = new CelulaDupla(veiculo);

        nova.ant = ultimo;

        ultimo.prox = nova;

        ultimo = nova;
    }

    //Inserir em uma posição
    public void inserir(Veiculo veiculo, int posicao) {

        CelulaDupla i = primeiro;

        //Percorrer até a posição anterior
        for (int j = 0; j < posicao; j++) {
            i = i.prox;
        }

        CelulaDupla nova = new CelulaDupla(veiculo);

        nova.ant = i;
        nova.prox = i.prox;

        if (i.prox != null) {
            i.prox.ant = nova;
        } else {
            ultimo = nova;
        }

        i.prox = nova;
    }

    //Remover do início
    public Veiculo removerInicio() throws Exception {

        if (primeiro == ultimo) {
            throw new Exception("Lista vazia");
        }

        CelulaDupla removida = primeiro.prox;

        Veiculo elemento = removida.elemento;

        primeiro.prox = removida.prox;

        if (removida.prox != null) {
            removida.prox.ant = primeiro;
        } else {
            ultimo = primeiro;
        }

        return elemento;
    }

    //Remover do fim
    public Veiculo removerFim() throws Exception {

        if (primeiro == ultimo) {
            throw new Exception("Lista vazia");
        }

        Veiculo elemento = ultimo.elemento;

        ultimo = ultimo.ant;

        ultimo.prox = null;

        return elemento;
    }

    //Remover de uma posição
    public Veiculo remover(int posicao) throws Exception {

        CelulaDupla i = primeiro.prox;

        //Percorrer até a posição
        for (int j = 0; j < posicao; j++) {
            i = i.prox;
        }

        Veiculo elemento = i.elemento;

        i.ant.prox = i.prox;

        if (i.prox != null) {
            i.prox.ant = i.ant;
        } else {
            ultimo = i.ant;
        }

        return elemento;
    }

    //Mostrar do primeiro ao último
    public void mostrar() {

        for (CelulaDupla i = primeiro.prox;
             i != null;
             i = i.prox) {

            System.out.println(i.elemento.format());
        }
    }
}


public class Exercicio13 {

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

        ListaDupla lista = new ListaDupla();

        int id = sc.nextInt();

        //Inserir os elementos iniciais
        while (id != -1) {

            lista.inserirFim(pesquisar(veiculos, id));

            id = sc.nextInt();
        }

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

                lista.inserir(
                    pesquisar(veiculos, id),
                    posicao
                );

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

                Veiculo removido =
                    lista.remover(posicao);

                System.out.println("(R) " +
                                   removido.getMarca() + " " +
                                   removido.getModelo());
            }
        }

        lista.mostrar();

        sc.close();
    }
}