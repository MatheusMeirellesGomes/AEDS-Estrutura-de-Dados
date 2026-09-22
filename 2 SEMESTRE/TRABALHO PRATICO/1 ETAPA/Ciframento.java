//Questão 1 do tp

import java.util.Scanner; 

class Ciframento {
    //Método booleano para retornar FIM quando for encontrada
    public static boolean isFim (String str) { //passando string como parâmetro. 
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M'; 
    }

    //Método para cifrar a palavra
    public static String ciframento (String str) {
        //Armazenar letra por letra a string cifrada em uma variável
        String cifrada = ""; 

        //Loop para percorrer a strig inteira 
        for (int i = 0; i < str.length(; ); i++) {
            //Precisa-se pegar o caracter na posição inicial
            char caracter = str.charAt(i); 

            //O novo caracter vai ser o caracter atual + 3, que é a tabela ascII 
            char novoCaracter = (char) (caracter + 3); //Transformando a soma que da inteiro para char. 
            
            //Adicionar o novo caracter cifrado na string cifrada
            cifrada += novoCaracter; 
        }

        //Retornar a string cifrada
        return cifrada; 
    }

    //Main 
    public static void main(String args[]) {
        //Objeto scanner 
        Scanner sc = new Scanner(System.in); 

        //Criar variável para ler a palavra
        String linha; 
        linha = sc.hasNext(); 

        //Ler enquanto a linha do arquivo não for FIM 
        while (!isFim(linha)) {
            //Variável para armazenar a linha cifrada
            String linhaCifrada = Ciframento(linha); 

            //Imprimir a linha
            System.out.println(linhaCifrada); 

            //Ler a próxima linha
            linha = sc.nextLine(); 
        }

        //Fechar scanner 
        sc.close(); 
    }
}