import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(
                new File("entrada/inputCodeForces.txt")
        ).useLocale(Locale.US);

        int n = scanner.nextInt();
        int m = scanner.nextInt();


        EdgeWeightedDigraph G = new EdgeWeightedDigraph(n + 1);

        for (int i = 0; i < m; i++) {

            int a = scanner.nextInt();
            int b = scanner.nextInt();
            double peso = scanner.nextDouble();


            G.addEdge(new DirectedEdge(a, b, peso));
            G.addEdge(new DirectedEdge(b, a, peso));
        }

        Dijkstra dijkstra = new Dijkstra(G);

        List<Integer> caminho = dijkstra.caminho(1, n);

            for (int v : caminho) {
                System.out.print(v + " ");
            }


        scanner.close();
    }
}