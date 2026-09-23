import java.util.Scanner; 

public class ListaEstatica {
    //Atributos da lista (tipo private)
    private int array[]; 
    private int n; 

    //Construtor da classe 
    public void inicializa(int tamanho) {
        //Inicializar lista vazia
        array = new int [tamanho]; 
        n = 0; 
    }

    //Inserir no início. 
    public void InserirInicio (int x) throws Exception {
        //Verificar se a lista não está cheia.
        if (n >= array.length) {
            throw new Exception("Full List"); 
        }

        //Se ela não estiver cheia, deslocar os elementos para a direita. 
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1]; //o lugar atual do vetor recebe o elemento anterior a ele, realizando o deslocando. 
        }
        
        //Inserir o elemento no início da lista e incrementar o contador.
        array[0] = x; 
        n++; 
    }

    //Inserir na pos
    public void InserirPos (int x, int pos) throws Exception {
        //Verificar se a lista não está cheia. 
        if (n >= array.lenth() || pos < 0 || pos > n) {
            throw new Exception ("Full List or Invalid Position"); 
        }

        //Deslocar os elementos para a direita a partir da posição. 
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1]; 
        }

        //Inserir o elemento na posição desejada e incrementar o contador
        array[pos] = x; 
        n++; 
    }

    //Inserir no fim
    public void InserirFim (int x) throws Exception {
        //Verificar se a lista tá cheia. 
        if (n >= array.length) {
            throw new Exception ("Full List")
        } 

        //Inserir elemento no fim da lista e incrementar o contador.
        array[n] = x; 
        n++; 
    }

    //Remover do inicio da lista
    public int RemoverInicio() throws Exception { 
        //verificar se a lista não está vazia
        if (n == 0) {
            throw new Exception ("Void List"); 
        }

        //Se tivet elemento, remover o da posição inicial. 
        int elementoRem = array[0]; //vai ser o do início da lista

        //Deslocar os elementos pra esquerda. 
        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1]; //o lugar atual do vetor recebe o elemento posterior a ele. 
        }  

        //decrementar o contador. 
        n--; 

        //Retornar o elemento removido. 
        return elementoRem; 
    }

    //Remover de uma pos
    public int RemoverPos(int pos) throws Exception {
        //Verificar se a lista não está vazia e se a pos é válida
        if (n == 0 || pos < 0 || pos > n) {
            throw new Exception ("Void List or Invalid Position"); 
        }

        //Remover o elemento na posição desejada. 
        int elementoRem = array[pos]; 

        //Deslocar os elementos para a esquerda a partir da pos
        for (int i = pos; i < n - 1; i++) {
            array[i] = array[i + 1]; 
        }

        //Decrementar o contador. 
        n--; 

        //Retornar o elemento removido
        return elementoRem; 
    }

    public int RemoverFim() throws Exception {
        //Verificar se a lista está vazia
        if (n == 0) {
            throw new Exception ("Void List"); 
        }

        //Remover o elemento da posição final da lista.
        int elementoRem = array[n];
        n--; //decrementar o contador.

        //Retornar o elemento removido.
        return elementoRem; 
    }

    //Mostrar a lista 
    public void mostrar() {
        //Percorrer a lista.
        for (int i = 0; i < n; i++) {
            //Imprimir o elemento naquela posição do array.
            System.out.println(array[i] + " "); 
        }

        System.out.println(); 
    }

    //Main
    public static void main(String args[]) throws Exception {
        //Criar um objeto do tipo Lista
        Lista lista = new Lista(10); 

        lista.InserirFim(10);
        lista.InserirFim(20);
        lista.InserirFim(30);

        lista.InserirInicio(5);

        lista.InserirPos(15, 2);

        lista.mostrar();

        lista.RemoverInicio();
        lista.RemoverFim();

        lista.mostrar();
    }
}