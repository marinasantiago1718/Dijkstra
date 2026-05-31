import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
        FastReader scanner;

        if (args.length > 0) {
            try {
                scanner = new FastReader(args[0]);
            } catch (IOException e) {
                System.err.println("Erro ao abrir o arquivo: " + args[0]);
                return;
            }
        } else {
            scanner = new FastReader();
        }
        String nStr = scanner.next();
        if (nStr == null) return;
        
        int n = Integer.parseInt(nStr);
        int m = scanner.nextInt();

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(n + 1);

        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            long peso = scanner.nextLong();

            G.addEdge(new DirectedEdge(a, b, peso));
            G.addEdge(new DirectedEdge(b, a, peso));
        }

        Dijkstra dijkstra = new Dijkstra(G);
        List<Integer> caminho = dijkstra.caminho(1, n);

        if (caminho.isEmpty()) {
            System.out.println("-1");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int v : caminho) {
                sb.append(v).append(" ");
            }
            System.out.println(sb.toString().trim());
        }

        scanner.close();
    }

static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String fileName) throws IOException {
            br = new BufferedReader(new FileReader(fileName));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String line = br.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        void close() {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    
    private int n;                

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

class Dijkstra {
    private DirectedEdge[] edgeTo;
    private long[] distTo;
    private IndexMinPQ pq;
    private EdgeWeightedDigraph G;

    public Dijkstra(EdgeWeightedDigraph G) {
        this.G = G;
        edgeTo = new DirectedEdge[G.V()];
        distTo = new long[G.V()];
        pq = new IndexMinPQ(G.V());
    }

    public List<Integer> caminho(int origem, int destino) {
        for(int i = 1; i < distTo.length; i++) {
            distTo[i] = Long.MAX_VALUE;
        }

        distTo[origem] = 0;
        pq.insert(origem, distTo[origem]);
        
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            
            for(DirectedEdge e : G.adj(v)) {
                int atual = e.from();
                int novo = e.to();

                if(distTo[atual] != Long.MAX_VALUE && distTo[novo] > distTo[atual] + e.weight()) {
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

        if (distTo[destino] == Long.MAX_VALUE) {
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

class DirectedEdge {
    private final int v;
    private final int w;
    private final long weight;

    public DirectedEdge(int v, int w, long weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() { return v; }
    public int to() { return w; }
    public long weight() { return weight; }
}

class EdgeWeightedDigraph {
    private final int V;                
    private int E;                      
    private Bag<DirectedEdge>[] adj;    

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    public int V() { return V; }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
}
class IndexMinPQ {
    private int maxN;        
    private int n;           
    private int[] pq;        
    private int[] qp;        
    private long[] keys;      

    public IndexMinPQ(int maxN) {
        this.maxN = maxN;
        n = 0;
        keys = new long[maxN + 1];    
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() { return n == 0; }

    public boolean contains(int i) { return qp[i] != -1; }

    public void insert(int i, long key) {
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public int delMin() {
        int min = pq[1];
        exch(1, n--);
        sink(1);
        qp[min] = -1;        
        pq[n+1] = -1;        
        return min;
    }

    public void changeKey(int i, long key) {
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]] > keys[pq[j]];
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}