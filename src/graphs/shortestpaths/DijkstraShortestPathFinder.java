package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> edgeTo = new HashMap<>();

        if (start.equals(end)) {
            return edgeTo;
        }

        // aux data structures
        Map<V, Double> distances = new HashMap<>();
        ExtrinsicMinPQ<V> minPQ = createMinPQ();
        Set<V> visited = new HashSet<>();

        // populate
        distances.put(start, 0.0);
        minPQ.add(start, 0.0);

        while (!minPQ.isEmpty()) {
            V curr = minPQ.removeMin();
            visited.add(curr);
            if (curr.equals(end)) {
                break;
            }

            for (E edge : graph.outgoingEdgesFrom(curr)) {
                V neighbor = edge.to();
                if (!visited.contains(neighbor)) {
                    double newDist = distances.get(curr) + edge.weight();
                    if (!distances.containsKey(neighbor)) {
                        distances.put(neighbor, Double.POSITIVE_INFINITY);
                    }
                    double oldDist = distances.get(neighbor);

                    if (newDist < oldDist) {
                        distances.put(neighbor, newDist);
                        edgeTo.put(neighbor, edge);

                        if (!minPQ.contains(neighbor)) {
                            minPQ.add(neighbor, newDist);
                        } else {
                            minPQ.changePriority(neighbor, newDist);
                        }
                    }
                }
            }
        }

        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (start.equals(end)) {
            return new ShortestPath.SingleVertex<>(start);
        } else if (spt.isEmpty() || !spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }

        List<E> edges = new ArrayList<>();
        V curr = end;
        while (!curr.equals(start)) {
            E edge = spt.get(curr);
            edges.add(edge);
            curr = edge.from();
        }

        Collections.reverse(edges);
        return new ShortestPath.Success<>(edges);
    }

}
