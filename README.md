
## Nome do Problema
C. Dijkstra?

## Link do Problema
[https://codeforces.com/problemset/problem/20/C](https://codeforces.com/problemset/problem/20/C)

---

## Integrantes do Grupo
- João Miguel Drumond
- Marina Maia
- Tales Pimentel 

---

## Linguagem Utilizada
- Java

---

# Como Executar a Solução
Nesse exemplo, a entrada do repositório está sendo usada diretamente como input no Scanner presente na Main. Assim, basta executar a classe principal. A saída será impressa no console.

---

# Entrada de Exemplo
A entrada utilizada é a mesma fornecida como exemplo no enunciado do CodeForces.

```text
5 6
1 2 2
2 5 5
2 3 4
1 4 1
4 3 3
3 5 1

```

# Saída Esperada

```text
1 4 3 5 
```

---

# Explicação da Modelagem
O problema consiste em aplicar Dijkstra em um grafo não direcionado de pesos não negativos de tal forma que, com vértices enumerados de 1 a n, o algoritmo deve percorrer
de 1 até n e achar o menor caminho existente.
Para solucionar, foram implementadas lógicas ensinadas em sala de aula: o Dijkstra deve operar em um grafo direcionado.
Assim, o grafo foi modelado substituindo as arestas não direcionadas por dois arcos direcionados e opostos, garantindo que o algoritmo 
consiga enxergar o caminho em ambos os sentidos.
Ou seja, se na entrada existir uma conexão A → B, o algoritmo ajusta para que seja criada uma aresta oposta (A ← B).

---

# Algoritmo Utilizado
Foi utilizado o algoritmo de Dijkstra para encontrar o caminho mínimo entre o vértice 1 e o vértice n.
## Funcionamento

1. Inicializar as distâncias entre vértices como infinito.
2. Inserir o vértice de origem na fila de prioridade com a sua distância igual a 0.
3. Remover repetidamente o vértice de menor distância da fila.
4. Relaxar todas as arestas adjacentes a esse vértice.
5. Quando uma distância menor é encontrada, atualizar a distância e registrar a aresta correspondente.
6. Atualizar a prioridade do vértice na fila.
7. Ao final da execução, utilizar o vetor de arestas para reconstruir o caminho mínimo do vértice n até o vértice 1.
8. Inverter a sequência obtida para apresentar o caminho na ordem correta: origem → destino.

---

# Estruturas de Dados Utilizadas
Para a implementação, foram utilizadas as classes da biblioteca Algs4 em Java, adaptadas para o contexto do problema.
Embora o enunciado forneça um grafo não direcionado, a solução foi implementada utilizando uma estrutura de grafo direcionado.
Para preservar o comportamento original, cada aresta da entrada foi inserida em ambas as direções.

## DirectedEdge
Classe responsável por representar uma aresta direcionada do grafo:
- vértice de origem;
- vértice de destino;
- peso da aresta.
Como o problema descreve um grafo não direcionado, cada conexão (u,v) foi convertida em duas arestas direcionadas:
- (u → v)
- (v → u)
com o mesmo peso.

## EdgeWeightedDigraph
Estrutura responsável por representar o grafo ponderado direcionado através de listas de adjacência.
Responsável por:
- armazenar os vértices do grafo;
- armazenar as arestas de saída de cada vértice;
- fornecer acesso eficiente aos vértices adjacentes.
Operações principais:
- addEdge() → adiciona uma aresta ao grafo;
- adj(v) → retorna todas as arestas que saem do vértice v.

## IndexMinPQ
Fila de prioridade mínima indexada utilizada pelo algoritmo de Dijkstra.
Responsável por:
- armazenar os vértices candidatos a serem processados;
- selecionar sempre o vértice com menor distância conhecida;
- atualizar prioridades quando um caminho mais curto é encontrado.
Operações principais:
- insert() → O(log V)
- delMin() → O(log V)
- changeKey() → O(log V)
- contains() → O(1)

---

# Análise de Complexidade

## Complexidade de Tempo

O algoritmo de Dijkstra percorre todos os vértices e relaxa todas as arestas do grafo.
Durante a execução:
- cada vértice pode ser inserido ou ter sua prioridade atualizada na fila de prioridade;
- cada remoção do menor elemento da fila custa O(log V);
- cada atualização de prioridade também custa O(log V);
- cada aresta é analisada durante o processo de relaxamento.

Assim, a complexidade total é:
O((V+E)logV)
onde:
- V é o número de vértices;
- E é o número de arestas.

Como o problema original é não direcionado e cada aresta foi armazenada em ambas as direções, o número de arestas efetivamente armazenadas no grafo é aproximadamente 2E, mas isso não altera a ordem assintótica da complexidade.

## Complexidade de Espaço

As principais estruturas utilizadas são:
- lista de adjacência do grafo;
- fila de prioridade;
- vetor distTo;
- vetor edgeTo.
- 
O consumo de memória é composto por:
- O(V) para distTo;
- O(V) para edgeTo;
- O(V) para a fila de prioridade;
- O(V + E) para o armazenamento do grafo.
Portanto, a complexidade de espaço é:
O(V+E)

---
# Variação de MST

Não foi utilizada nenhuma variação específica.
Foi aplicada a versão clássica do algoritmo de Dijkstra para grafos direcionados com fila de prioridade mínima indexada (IndexMinPQ).

---
# Casos Especiais

- Caso não exista caminho de 1 até n.
- Múltiplos caminhos mínimos.

---
# Comprovação de Accepted



