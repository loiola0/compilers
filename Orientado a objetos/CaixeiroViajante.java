package org.victorloiola.faculdade;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CaixeiroViajante {

    public static void vizinhoMaisProximo(Percurso percurso) {
        long startTime = System.nanoTime();
        
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        int[] visitado = new int[percurso.cidades.size()];
        int[] caminho = new int[percurso.cidades.size()];

        float distanciaTotal = 0;
        int atual = 0;

        visitado[atual] = 1;
        caminho[0] = atual;

        for (int i = 1; i < percurso.cidades.size(); i++) {
            int proximo = -1;
            float menorDistancia = Float.MAX_VALUE;

            for (int j = 0; j < percurso.cidades.size(); j++) {
                if (visitado[j] == 0 && percurso.distancias[atual][j] < menorDistancia) {
                    menorDistancia = percurso.distancias[atual][j];
                    proximo = j;
                }
            }

            caminho[i] = proximo;
            visitado[proximo] = 1;
            distanciaTotal += menorDistancia;
            atual = proximo;
        }

        distanciaTotal += percurso.distancias[atual][caminho[0]];
        
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        long memoryUsed = memoryAfter - memoryBefore;

        long endTime = System.nanoTime();
        double cpuTimeUsed = (endTime - startTime) / 1_000_000_000.0;

        System.out.print("Caminho pelo vizinho mais próxima: ");
        for (int i = 0; i < percurso.cidades.size(); i++) {
            System.out.print(percurso.cidades.get(caminho[i]).getNome() + " -> ");
        }

        System.out.println(percurso.cidades.get(caminho[0]).getNome());
        System.out.println("Distancia total: " + distanciaTotal);

        salvarArquivoMelhorCaminho(percurso, "cidadesVizinhoProximo.csv", caminho);

        System.out.printf("O algoritmo do vizinho mais próximo foi executado em %.6f segundos.\n", cpuTimeUsed);
        
        System.out.printf("Memória utilizada: %d bytes\n", memoryUsed);
    }

    

    public static void salvarArquivoMelhorCaminho(Percurso percurso, String nomeArquivo, int[] caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Cidade,X,Y\n");

            for (int i = 0; i < percurso.cidades.size(); i++) {
                Cidade cidade = percurso.cidades.get(i);
                writer.write(String.format("%s,%.2f,%.2f\n", cidade.getNome(), cidade.getX(), cidade.getY()));
            }
            
            writer.write("\nCaminho Percorrido\n");
            
            for (int i = 0; i < caminho.length; i++) {
                Cidade cidade = percurso.cidades.get(caminho[i]);
                writer.write(cidade.getNome() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo para escrita: " + e.getMessage());
        }
    }
}
