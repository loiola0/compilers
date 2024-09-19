package org.victorloiola.faculdade;

import java.util.*;

public class Percurso {
    public static List<Cidade> cidades = new ArrayList<Cidade>();
    public static float[][] distancias = new float[100][100];
    
    public Percurso(int numeroDeCidades){
        char nomeCidade = 'A';
        for (int i = 0; i < numeroDeCidades; i++) {
            cidades.add(new Cidade(nomeCidade));
            nomeCidade++;
        }
        for (int i = 0; i < numeroDeCidades; i++) {
            for (int j = 0; j < numeroDeCidades; j++) {
                this.distancias[i][j] = (float) cidades.get(i).calcularDistancia(cidades.get(j));
            }
        }
    }
    
    public void imprimirCidades(){
        for (Cidade cidade : cidades) {
            System.out.println(cidade.getNome() +"-" + "("+cidade.getX()+","+cidade.getY()+")");
        }
    }
}
