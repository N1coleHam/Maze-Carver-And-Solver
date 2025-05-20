package graphs.minspantrees;

import disjointsets.DisjointSets;
//import disjointsets.QuickFindDisjointSets;
import disjointsets.UnionBySizeCompressingDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        // return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        // initialize each vertex to be its own component
        DisjointSets<V> disjointSets = createDisjointSets();
        Collection<V> allVertices = graph.allVertices();
        for (V vertex : allVertices) {
            disjointSets.makeSet(vertex);
        }

        // for each edge (u,v) in sorted order
        List<E> kruskalEdges = new ArrayList<>();

        for (E edge : edges) {
            // if (u and v are in different components, add (u,v) to the MST
            V from = edge.from();
            V to = edge.to();

            // find disjoint set together
            int fromSet = disjointSets.findSet(from);
            int toSet = disjointSets.findSet(to);
            if (fromSet != toSet) {
                kruskalEdges.add(edge);
                disjointSets.union(from, to);
            }
        }

        // make tree failure if there is less than v - 1 edges
        if (kruskalEdges.size() < allVertices.size() - 1) {
            return new MinimumSpanningTree.Failure<>();
        }

        return new MinimumSpanningTree.Success<>(kruskalEdges);
    }
}
