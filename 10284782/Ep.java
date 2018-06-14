/*********************************************************************************************
**			Nome: Leonardo Soares Santos				   N USP: 10284782     				**
**			Trabalho Para a Disciplina Introducao a Analise de Algoritmos					**
**			Discente: Flavio Coutinho					Segundo Semestre/2017				**
**			Tema: Labirinto inteligente														**
*********************************************************************************************/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Ep {
    public static boolean debug = true;
    public static List<Caminho> caminhos = new ArrayList<Caminho>();
    public static List<int[]> indiceIten3 = new ArrayList<int[]>();

    public static boolean verifica(char[][] lab, int lin, int col, int[][] mcam){
        if(lin<0 || col<0 || lin>=lab.length||col>=lab[0].length) return false; //Verifica se a próxima coordenada está dentro do labirinto
        if(lab[lin][col] == 'X') return false; //Verifica se a próxima casa é valída
        if(mcam[lin][col] != 0) return false;
        return true;
    }
    public static int[] temItem(int lin,int col, int[][] itens){
        int[] temp = new int[2];
        for(int i = 0; i<itens.length; i++){
            if (lin == itens[i][0] && col == itens[i][1]){
                temp[0] = i;
                temp[1] = itens[i][2];
                return temp;
            }
        }
        temp[0] = -1;
        temp[1] = 0;
        return temp;
    }
    public static boolean passeio(char[][] lab, int lin, int col, int passo, int[][] mcam,int linf, int colf, Stack<Passo> pilha,  int[][] itens){
        int[] cy = {-1,1,0,0};
        int[] cx = {0,0,-1,1};
        if(!verifica(lab, lin, col, mcam)) return false;
        passo++;
        mcam[lin][col] = passo;
        Passo novo = new Passo();
        novo.coordY = lin;
        novo.coordX = col;
        int[] temp =  temItem(lin, col, itens);
        novo.indiceItem = temp[0];
        novo.valorItem = temp[1];
        pilha.push(novo);
        if(lin == linf && col==colf) {
            Caminho caminhoAtual= new Caminho();
            caminhoAtual.passos = passo;
            int valor = 0;
            int itensCami = 0;
            int peso = 0;
            while(pilha.size()>1){
                Passo passoAtual = pilha.pop();
                valor += passoAtual.valorItem;
                int[] help = new int[2];
                help[0] = passoAtual.coordX;
                help[1] = passoAtual.coordY;
                caminhoAtual.setMapa(help);
                if(passoAtual.indiceItem != -1){
                    itensCami++;
                    peso += itens[passoAtual.indiceItem][3];
                    int[] aux = new int[4];
                    for (int j = 0; j<aux.length; j++) {
                        aux[j] = itens[passoAtual.indiceItem][j];
                    }
                    caminhoAtual.setItens(aux);
                }

            }
            caminhoAtual.peso = peso;
            caminhoAtual.itens = itensCami;
            caminhoAtual.valor = valor;

            caminhos.add(caminhoAtual);
            mcam[lin][col] = 0;
            return true;
        }
        for (int i = 0; i<cx.length; i++) {
            int proxLin = lin+cy[i];
            int proxCol = col+cx[i];
            passeio(lab, proxLin, proxCol, passo, mcam, linf, colf, pilha, itens);

        }
        mcam[lin][col] = 0;

        return false;
    }
    public static void imprime(int[][] mcam, char[][] lab){
        for (int i = 0; i<lab.length; i++) {
            for (int j = 0; j<lab[0].length; j++) {
                System.out.print("|"+lab[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("Caminho ");
        for (int i = 0; i<mcam.length; i++) {
            for (int j = 0; j<mcam[0].length; j++) {
                System.out.print("|"+mcam[i][j]);
            }
            System.out.println("|");
        }

        System.out.println("");
    }
    public static void criterio1(){
        Caminho min = caminhos.get(0);

            for (int i = 1; i<caminhos.size(); i++) {
                Caminho atual = caminhos.get(i);
                if(min.passos> atual.passos){
                    min = atual;
                }
            }
        apresenta(min);

    }
    public static void criterio2(){
        Caminho max = caminhos.get(0);

        for (int i = 1; i<caminhos.size(); i++) {
            Caminho atual = caminhos.get(i);
            if(max.valor < atual.valor){
                max = atual;
            }
        }
        apresenta(max);
    }
    public static void criarOpcao(Caminho atual, int n, int capacidade, int[] aux){
        if (n <= 0) {
            int peso = 0;
            for (int i=0; i<aux.length; i++) {
                int [] help = atual.indiceItens.get(aux[i]);
                peso += help[3];
            }

            if (peso <= capacidade) {
                for (int i = 0; i<aux.length; i++) {
                    for (int j = i+1; j<aux.length; j++) {
                        if(aux[i] == aux[j]) return;
                    }
                }
                indiceIten3.add(aux);
            }
            return;

        }

        for (int i =0; i<atual.indiceItens.size(); i++) {
            aux[n-1] = i;
            criarOpcao(atual, n-1, capacidade, aux);
        }
    }
    public static Caminho escolherOpcao(Caminho atual){
        int valor, peso;
        int[] aux = indiceIten3.get(0);
        int[] bestOption = new int[aux.length + 2];

        for (int i = 0; i<indiceIten3.size(); i++) {
            aux = indiceIten3.get(i);
            valor = 0;
            peso = 0;
            for (int j = 0; j<aux.length; j++) {
                int[] help = atual.indiceItens.get(aux[j]);
                peso += help[3];
                valor += help[2];
            }
            if(i == 0) {
                for (int j = 0; j<aux.length; j++) {
                    bestOption[i] = aux[i];
                }
                bestOption[bestOption.length-1] = peso;
                bestOption[bestOption.length-2] = valor;
            }
            else if(valor > bestOption[bestOption.length-2]){
                for (int j = 0; j<aux.length; j++) {
                    bestOption[i] = aux[i];
                }
                bestOption[bestOption.length-1] = peso;
                bestOption[bestOption.length-2] = valor;
            }
        }

        Caminho opcaoCaminho = new Caminho();
        opcaoCaminho.passos = atual.passos;
        opcaoCaminho.coordenadasMapa = atual.coordenadasMapa;

        for (int i = 0; i<aux.length; i++) {
            aux[i] = bestOption[i];
        }

        opcaoCaminho.indiceItens.add(aux);
        opcaoCaminho.itens = aux.length;
        opcaoCaminho.valor = bestOption[bestOption.length-2];
        opcaoCaminho.peso = bestOption[bestOption.length-1];
        return opcaoCaminho;
    }
    public static Caminho escolherMelhorOpcao(List<Caminho> opcoes){
        Caminho melhorCaminho = opcoes.get(0);
        for (int i = 1; i<opcoes.size(); i++) {
            Caminho aux = opcoes.get(i);
            if(melhorCaminho.valor < aux.valor){
                melhorCaminho = aux;
            }
        }

        return melhorCaminho;
    }
    public static Caminho melhorOpcao(Caminho atual, int capacidade){
        if (atual.peso <= capacidade) return atual;

        List<Caminho> opcoes = new ArrayList<Caminho>();

        int[] melhorItem =  {0, 0, 0, 0};
        for (int i = 0; i<atual.indiceItens.size(); i++) {
            int[] aux = atual.indiceItens.get(i);
            if(aux[3] <= capacidade && aux[3] > melhorItem[3]){
                melhorItem = aux;

            }
        }
        if (melhorItem[3] <= capacidade){
            Caminho opcaoCaminho = new Caminho();
            opcaoCaminho.passos = atual.passos;
            opcaoCaminho.coordenadasMapa = atual.coordenadasMapa;
            opcaoCaminho.indiceItens.add(melhorItem);
            opcaoCaminho.itens = 1;
            opcaoCaminho.valor = melhorItem[2];
            opcaoCaminho.peso = melhorItem[3];
            opcoes.add(opcaoCaminho);
        }
        for (int i = 2; i<atual.indiceItens.size(); i++) {
            int[] aux = new int[atual.indiceItens.size()-(i-1)];
            criarOpcao(atual, capacidade, i, aux);
            if(!indiceIten3.isEmpty()){
                opcoes.add(escolherOpcao(atual));
                for (int j = 0; j<indiceIten3.size(); j++) {
                    indiceIten3.remove(i);
                }
            }
        }

        return escolherMelhorOpcao(opcoes);
    }
    public static void criterio3(int capacidade){
        Caminho atual = new Caminho();
        List<Caminho> caminhos3 = new ArrayList<Caminho>();
        for (int i=0; i<caminhos.size(); i++) {
            atual = caminhos.get(i);

            if (atual.itens > 0) {
                if(atual.peso<=capacidade)caminhos3.add(atual);
                caminhos3.add(melhorOpcao(atual, capacidade));
            }
        }
        apresenta(escolherMelhorOpcao(caminhos3));

    }
    public static void apresenta(Caminho caminho){
        System.out.println(caminho.passos);
        for (int i = caminho.coordenadasMapa.size() - 1; i>=0; i--) {
            int[] aux = caminho.coordenadasMapa.get(i);
            System.out.println(aux[1]+ " "+ aux[0]);
        }
        System.out.print(caminho.itens+ " ");
        System.out.print(caminho.valor+ " ");
        System.out.println(caminho.peso);
        for (int i = caminho.indiceItens.size() - 1; i>=0; i--) {
            int[] aux = caminho.indiceItens.get(i);
            System.out.println(aux[0]+ " "+ aux[1]);
        }
    }
  public static void main(String[] args ) throws IOException {

    try {
        FileReader arq = new FileReader(args[0]); // Criar arquivo dentro do programa
        BufferedReader lerArq = new BufferedReader(arq); //Ler Arquivo

        String[] linha = lerArq.readLine().split(" "); // lê a primeira linha
        int lin = Integer.parseInt(linha[0]); //Pega a qnt de linhas do labirinto
        int col = Integer. parseInt(linha[1]); //Pega a qnt de colunas do labirinto

        char[][] lab = new char[lin][col]; //Cria Labirinto

        for (int i =0; i<lin; i++) {
            lab[i] = lerArq.readLine().toCharArray(); //Preenche Labirinto
        }

        int qntItens = Integer.parseInt(lerArq.readLine()); //Le quantidade de itens

        int[][] itens = new int[qntItens][4]; // Cria matrizpara armazenar as informações de cada item
        for (int i =0; i<qntItens; i++) {
            linha = lerArq.readLine().split(" "); //Le informações de cada item
            for (int j =0; j<itens[i].length; j++) {
                itens[i][j] = Integer.parseInt(linha[j]); //Guarda as informações na matriz
            }

        }

        int capMochi = Integer.parseInt(lerArq.readLine()); //Le a capacidade da mochila

        int[][] iniFim = new int[2][2]; //MAtriz para armazenar o ponto de inico e o ponto final do labirinto
        for (int i =0; i<2; i++) {
            linha = lerArq.readLine().split(" ");
            for (int j =0; j<2; j++) {
                iniFim[i][j] = Integer.parseInt(linha[j]);
            }
        }

        int criterio = Integer.parseInt(args[1]); //Le qual saida o usúario quer
        Stack<Passo> pilha = new Stack<Passo>();
        int[][] mcam= new int[lab.length][lab[0].length];
        passeio(lab, iniFim[0][0], iniFim[0][1], 0, mcam, iniFim[1][0], iniFim[1][1], pilha, itens);
        if(criterio == 1)criterio1();
        else if(criterio == 2)criterio2();
        else if(criterio == 3)criterio3(capMochi);

      arq.close();
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }

    System.out.println();
  }
}
