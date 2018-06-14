import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TesteArquivo {
    public static boolean debug = true;


  public static void main(String[] args ) throws IOException {





    try {
        FileReader arq = new FileReader(args[0]);
        BufferedReader lerArq = new BufferedReader(arq);

        String[] linha = lerArq.readLine().split(" "); // lê a primeira linha
        int lin = Integer.parseInt(linha[0]);
        int col = Integer. parseInt(linha[1]);

        char[][] lab = new char[lin][col];

        for (int i =0; i<lin; i++) {
            lab[i] = lerArq.readLine().toCharArray();
        }

        int qntItens = Integer.parseInt(lerArq.readLine());

        int[][] itens = new int[qntItens][4];
        for (int i =0; i<qntItens; i++) {
            linha = lerArq.readLine().split(" ");
            for (int j =0; j<itens[i].length; j++) {
                itens[i][j] = Integer.parseInt(linha[j]);
            }

        }

        int capMochi = Integer.parseInt(lerArq.readLine());

        int[][] iniFim = new int[2][2];
        for (int i =0; i<2; i++) {
            linha = lerArq.readLine().split(" ");
            for (int j =0; j<2; j++) {
                iniFim[i][j] = Integer.parseInt(linha[j]);
            }
        }

        for (int i =0; i<2; i++) {
            for (int j =0; j<2; j++) {
                System.out.print(iniFim[i][j]);
            }
            System.out.println();
        }
      arq.close();
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }

    System.out.println();
  }
}
