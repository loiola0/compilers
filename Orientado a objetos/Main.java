package org.victorloiola.faculdade;

public class Main {
    public static void main(String[] args) {
        Percurso percurso = new Percurso(10);

        percurso.imprimirCidades();
        
        CaixeiroViajante.vizinhoMaisProximo(percurso);
        
        bs2(percurso);
    }

    public static void forcaBruta(Percurso percurso, int[] pos, int l, int r, float[] minDist, float currentDist, int[] caminho) {
        if (l == r) {
            currentDist += percurso.distancias[pos[r]][pos[0]];
            if (currentDist < minDist[0]) {
                minDist[0] = currentDist;
                System.arraycopy(pos, 0, caminho, 0, percurso.cidades.size());
                caminho[percurso.cidades.size()] = pos[0];
            }
        } else {
            for (int i = l; i <= r; i++) {
                int temp = pos[l];
                pos[l] = pos[i];
                pos[i] = temp;

                forcaBruta(percurso, pos, l + 1, r, minDist, currentDist + percurso.distancias[pos[l - 1]][pos[l]], caminho);

                temp = pos[l];
                pos[l] = pos[i];
                pos[i] = temp;
            }
        }
    }

    public static void bs2(Percurso percurso) {
        long startTime = System.currentTimeMillis();

        int[] pos = new int[percurso.cidades.size()];
        for (int i = 0; i < percurso.cidades.size(); i++) {
            pos[i] = i;
        }

        float[] minDist = {Float.MAX_VALUE};
        int[] caminho = new int[percurso.cidades.size() + 1];

        forcaBruta(percurso, pos, 1, percurso.cidades.size() - 1, minDist, percurso.distancias[0][pos[1]], caminho);

        long endTime = System.currentTimeMillis();
        double cpuTimeUsed = (endTime - startTime) / 1000.0;

        System.out.print("Melhor caminho pela Força Bruta: ");
        for (int i = 0; i < percurso.cidades.size(); i++) {
            System.out.print(percurso.cidades.get(caminho[i]).getNome() + " -> ");
        }
        System.out.println(percurso.cidades.get(caminho[0]).getNome());
        System.out.printf("Distância total: %.2f\n", minDist[0]);

        //salvarArquivoMelhorCaminho(percurso, "cidadesForcaBruta.csv", caminho);

        System.out.printf("O algoritmo de força bruta foi executado em %.6f segundos.\n", cpuTimeUsed);
    }
}