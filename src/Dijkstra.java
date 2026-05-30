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

    public int[] caminho(int origem) {

        distTo[origem] = 0.0;
        for(int i = 1; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }

        pq.insert(origem, distTo[origem]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            
            for(DirectedEdge e : G.adj(v)) {
                int atual = e.from();
                int novo = e.to();

                if(distTo[atual] > distTo[novo] + e.weight()) {
                    distTo[atual] = distTo[novo] + e.weight();
                    edgeTo[atual] = e;    

                    if(pq.contains(atual)) {
                        pq.changeKey(atual, distTo[atual]);
                    } else {
                        pq.insert(atual, distTo[atual]);
                    }
                }
                
            }
        }

        int[] menorCaminho = new int[edgeTo.length + 1];
        for(int i = 0; i <= menorCaminho.length; i++) {
            menorCaminho[i] = edgeTo[i].from();
        }

        return menorCaminho;
    }
     
}
