
# 🚀 Trabalho Final de Compiladores

## 📝 Descrição do Projeto

Este repositório contém a implementação do **Problema do Caixeiro Viajante** (TSP - Traveling Salesman Problem) em três paradigmas de programação diferentes, como parte do trabalho final da disciplina de Compiladores. Foram utilizados os seguintes paradigmas e linguagens:

- Paradigma **Imperativo**: Implementação em **C**.
- Paradigma **Orientado a Objetos**: Implementação em **Java**.
- Paradigma **Funcional**: Implementação em **Haskell**.

Os algoritmos implementados para resolver o problema do TSP são:

1. **Força Bruta**: Explora todas as possíveis permutações para encontrar a solução ótima.
2. **Vizinho Mais Próximo**: Uma heurística que busca o caminho mais curto selecionando a cidade mais próxima a cada passo.

## 📚 O Problema do Caixeiro Viajante

O **TSP** é um problema clássico da teoria da computação e otimização, onde dado um conjunto de cidades e as distâncias entre cada par de cidades, o objetivo é encontrar o menor percurso possível que visite todas as cidades exatamente uma vez e retorne à cidade de origem. O problema é **NP-difícil**, o que significa que não existe um algoritmo conhecido que possa resolvê-lo de forma eficiente em todos os casos.

### Algoritmos Implementados

- **Força Bruta**: Este algoritmo verifica todas as permutações possíveis das cidades e escolhe o caminho de menor distância. Embora garanta a solução ótima, é computacionalmente inviável para grandes conjuntos de cidades devido ao crescimento exponencial do número de permutações.
  
- **Vizinho Mais Próximo**: Um algoritmo heurístico que começa em uma cidade aleatória e escolhe a cidade mais próxima que ainda não foi visitada. É uma abordagem rápida, mas não garante o resultado ótimo.

## 🛠 Estrutura do Projeto

- `Imperativo/`: Contém a implementação do algoritmo em C.
- `Orientado a objetos/`: Contém a implementação em Java.
- `Funcional/`: Contém a implementação em Haskell.

Cada pasta inclui:
- Código-fonte do algoritmo de força bruta e vizinho mais próximo.
- Instruções específicas de compilação e execução.


## 📊 Comparação dos Paradigmas

Este projeto também permite comparar como diferentes paradigmas de programação tratam o mesmo problema. Cada abordagem oferece vantagens e desvantagens no que se refere a:

- **Simplicidade e clareza do código**
- **Desempenho**
- **Abstração e modularidade**

## 🏅 Créditos

Este projeto foi desenvolvido como trabalho final da disciplina de **Compiladores** por Henrique Sousa e Victor Loiola (https://github.com/loiola0).

---

**Divirta-se explorando o mundo dos paradigmas de programação e a complexidade do TSP!** 🎯
