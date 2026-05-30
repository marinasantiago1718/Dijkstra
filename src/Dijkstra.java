import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    private EdgeWeightedDigraph G;

    public Dijkstra(EdgeWeightedDigraph G) {
        this.G = G;
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());
    }

    public List<Integer> caminho(int origem, int destino) {


        for(int i = 1; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        distTo[origem] = 0.0;
        pq.insert(origem, distTo[origem]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            
            for(DirectedEdge e : G.adj(v)) {
                int atual = e.from();
                int novo = e.to();

                if(distTo[novo] > distTo[atual] + e.weight()) {
                    distTo[novo] = distTo[atual] + e.weight();
                    edgeTo[novo] = e;

                    if(pq.contains(novo)) {
                        pq.changeKey(novo, distTo[novo]);
                    } else {
                        pq.insert(novo, distTo[novo]);
                    }
                }
                
            }
        }


        if (distTo[destino] == Double.POSITIVE_INFINITY) {
            return new ArrayList<>();
        }

        List<Integer> caminho = new ArrayList<>();

        int atual = destino;

        while (atual != origem) {

            caminho.add(atual);

            atual = edgeTo[atual].from();
        }

        caminho.add(origem);

        Collections.reverse(caminho);

        return caminho;
    }
     
}
