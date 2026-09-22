import java.util.Scanner; 

//Classe SomaDigitos
class SomaDigitos {
    //Verificar se não é fim 
    public static boolean isFim (String str) {
        //Retornar true caso a palavra se encaixe
        return str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M'; 
    }

    //Realizar a soma de dígitos, fazer usando recursividade.
    public static int somadgitos (int num) {
        //Caso base. 
        if (num == 0) {
            return 0; 
        } else {
            return (num % 10) + somadigitos(num / 10); //Pega o resto da divisão por 10 + o número dividido por 10
        }
    }

    //Main
    public static void main(String args[]) {
        //Objeto Scanner
        Scanner sc = new Scanner (System.in); 

        //Ler enquanto não chegar no fim do arquivo ou na palavra fim. 
        while (sc.hasNext()) {
            //Ler o número 
            int n = sc.nextInt(); 

            //Chamar a função para realizar a soma de digitos
            int resultado = somadigitos(n); 

            //Imprimir o resultado
            System.out.println(resultado); 
        }

        sc.close(); 
    }
}


