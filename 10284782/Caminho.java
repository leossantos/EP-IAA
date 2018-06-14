/*********************************************************************************************
**			Nome: Leonardo Soares Santos				   N USP: 10284782     				**
**			Trabalho Para a Disciplina Introducao a Analise de Algoritmos					**
**			Discente: Flavio Coutinho					Segundo Semestre/2017				**
**			Tema: Labirinto inteligente														**
*********************************************************************************************/


import java.util.*;
public class Caminho{
    int passos;
    List<int[]> coordenadasMapa = new ArrayList<int[]>();
    List<int[]> indiceItens = new ArrayList<int[]>();
    int itens;
    int valor;
    int peso;

    public void setMapa(int[] coord){
        coordenadasMapa.add(coord);
    }

    public void setItens(int[] info){
        indiceItens.add(info);
    }
}
