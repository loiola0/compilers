#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <float.h>
#include <time.h>

#define MAX 100

typedef struct {
    char nome;
    float x;
    float y;
} Cidade;

typedef struct {
    Cidade cidades[MAX];
    int numeroDeCidades;
    float adjMatriz[MAX][MAX];
} Grafo;

float calcularDistancia(Cidade a, Cidade b) {
    return sqrtf(powf(b.x - a.x, 2) + powf(b.y - a.y, 2));
}

Grafo inicializarGrafo(int numeroDeCidades) {
    Grafo grafo;
    grafo.numeroDeCidades = numeroDeCidades;

    for (int i = 0; i < grafo.numeroDeCidades; i++) {
        grafo.cidades[i].nome = 'A' + i;
        grafo.cidades[i].x = (float)rand() / (float)RAND_MAX * 100;
        grafo.cidades[i].y = (float)rand() / (float)RAND_MAX * 100;
    }

    for (int i = 0; i < numeroDeCidades; i++) {
        for (int j = 0; j < numeroDeCidades; j++) {
            grafo.adjMatriz[i][j] = calcularDistancia(grafo.cidades[i], grafo.cidades[j]);
        }
    }

    return grafo;
}

void mostrarGrafo(Grafo grafo) {
    printf("Cidades e suas coordenadas:\n");
    for (int i = 0; i < grafo.numeroDeCidades; i++) {
        printf("Cidade %c: (%.2f, %.2f)\n", grafo.cidades[i].nome, grafo.cidades[i].x, grafo.cidades[i].y);
    }
}

void salvarArquivoMelhorCaminho(Grafo grafo, char nomeArquivo[], int caminho[MAX+1]) {
    FILE *arquivo = fopen(nomeArquivo, "w");

    if(arquivo == NULL) {
        printf("Erro ao abrir o arquivo para escrita!\n");
        return;
    }

    fprintf(arquivo, "Cidade,X,Y\n");
    for(int i = 0; i < grafo.numeroDeCidades; i++) {
        fprintf(arquivo, "%c,%.2f,%.2f\n", grafo.cidades[i].nome, grafo.cidades[i].x, grafo.cidades[i].y);
    }

    fprintf(arquivo, "\nCaminho Percorrido\n");
    for(int i = 0; i < grafo.numeroDeCidades; i++) {
        fprintf(arquivo, "%c\n", grafo.cidades[caminho[i]].nome);
    }

    fclose(arquivo);
}

void vizinhoMaisProximo(Grafo grafo) {
    clock_t start, end;
    double cpu_time_used;

    start = clock();

    int visitado[MAX] = {0};
    int caminho[MAX];
    float totalDistancia = 0.0;

    int atual = 0;
    visitado[atual] = 1;
    caminho[0] = atual;

    for (int i = 1; i < grafo.numeroDeCidades; i++) {
        int proximo = -1;
        float menorDistancia = FLT_MAX;

        for (int j = 0; j < grafo.numeroDeCidades; j++) {
            if (!visitado[j] && grafo.adjMatriz[atual][j] < menorDistancia) {
                menorDistancia = grafo.adjMatriz[atual][j];
                proximo = j;
            }
        }

        caminho[i] = proximo;
        visitado[proximo] = 1;
        totalDistancia += menorDistancia;
        atual = proximo;
    }

    totalDistancia += grafo.adjMatriz[atual][caminho[0]];

    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;


    printf("Caminho pelo Vizinho Mais Próximo: ");
    for (int i = 0; i < grafo.numeroDeCidades; i++) {
        printf("%c -> ", grafo.cidades[caminho[i]].nome);
    }
    printf("%c\n", grafo.cidades[caminho[0]].nome);
    printf("Distância total: %.2f\n", totalDistancia);

    salvarArquivoMelhorCaminho(grafo, "cidadesVizinhoMaisProximo.csv", caminho);

    printf("O algortimo do vizinho mais próximo foi executado em %f segundos.", cpu_time_used);
}

void forcaBruta(Grafo grafo, int pos[], int l, int r, float *minDist, float currentDist, int caminho[]) {
    if (l == r) {
        currentDist += grafo.adjMatriz[pos[r]][pos[0]];
        if (currentDist < *minDist) {
            *minDist = currentDist;
            for (int i = 0; i < grafo.numeroDeCidades; i++) {
                caminho[i] = pos[i];
            }
            caminho[grafo.numeroDeCidades] = pos[0];
        }
    } else {
        for (int i = l; i <= r; i++) {
            // Swap
            int temp = pos[l];
            pos[l] = pos[i];
            pos[i] = temp;

            // Recur
            forcaBruta(grafo, pos, l + 1, r, minDist, currentDist + grafo.adjMatriz[pos[l - 1]][pos[l]], caminho);

            // Swap back
            temp = pos[l];
            pos[l] = pos[i];
            pos[i] = temp;
        }
    }
}

void calcularForcaBruta(Grafo grafo) {
    clock_t start, end;
    double cpu_time_used;

    start = clock();

    int pos[MAX];
    for (int i = 0; i < grafo.numeroDeCidades; i++) {
        pos[i] = i;
    }

    float minDist = FLT_MAX;
    int caminho[MAX + 1];

    forcaBruta(grafo, pos, 1, grafo.numeroDeCidades - 1, &minDist, grafo.adjMatriz[0][pos[1]], caminho);

    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;

    printf("Melhor caminho pela Força Bruta: ");
    for (int i = 0; i < grafo.numeroDeCidades; i++) {
        printf("%c -> ", grafo.cidades[caminho[i]].nome);
    }
    printf("%c\n", grafo.cidades[caminho[0]].nome);
    printf("Distância total: %.2f\n", minDist);


    printf("O algortimo de força bruta foi executado em %f segundos.", cpu_time_used);

    salvarArquivoMelhorCaminho(grafo, "cidadesForcaBruta.csv", caminho);
}

int main() {
    srand(time(NULL));

    int numeroDeCidades = 10;

    Grafo grafo = inicializarGrafo(numeroDeCidades);

    mostrarGrafo(grafo);

    printf("\nAlgoritmo Vizinho Mais Próximo:\n");
    vizinhoMaisProximo(grafo);

    printf("\nAlgoritmo Força Bruta:\n");
    calcularForcaBruta(grafo);

    return 0;
}
