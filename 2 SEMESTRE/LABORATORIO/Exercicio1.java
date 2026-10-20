
/*Dada uma coleção de intervalos: [1,3] [2,6] [8,10] [11,15]
combine todos os intervalos que se sobrepõem para que a saída fique: [1,6] [8,10] [11,15] */

import java.util.Scanner;

class Exercicio1 {
    public static void main(String args[]) {
        //Scanner para leitura de dados
        Scanner sc = new Scanner(System.in);

        //Variável para contar a quantidade de intervalos
        int n = sc.nextInt();

        //Criar uma matriz para armazenar em linhas e colunas
        int intervalos[][] = new int[n][2]; //Vai receber a quantidade de linhas 'n' e duas colunas.

        //Percorrer os vetores das linhas e colunas para fazer a leitura
        for (int i = 0; i < n; i++) {
            intervalos[i][0] = sc.nextInt(); //Vai ler a 1 coluna, que indica o início do intervalo.
            intervalos[i][1] = sc.nextInt(); //Vai ler a 2 coluna, que indica o fim do intervalo.
        }

        //Verificar se existe algum intervalo.
        if (n > 0) {
            int inicio = intervalos[0][0];
            int fim = intervalos[0][1];

            //Percorrer os próximos intervalos
            for (int i = 1; i < n; i++) { //i começa em 1 agora, a partir do próximo intervalo.

                //Verificar se há sobreposições
                if (intervalos[i][0] <= fim) { //Se o início do próximo intervalo for menor ou igual ao fim do intervalo salvo.

                    //Verificar se o fim do próximo intervalo é maior que o fim atual
                    if (intervalos[i][1] > fim) {
                        fim = intervalos[i][1]; //Atualiza apenas o fim, para ser o novo fim.
                    }

                } else {
                    //Mostrar o intervalo anterior antes de atualizar
                    System.out.println("[" + inicio + "," + fim + "]");

                    //Atualizar o início e fim do novo intervalo
                    inicio = intervalos[i][0];
                    fim = intervalos[i][1];
                }
            }

            //Mostrar o intervalo final correto.
            System.out.println("[" + inicio + "," + fim + "]");
        }

        sc.close();
    }
}
