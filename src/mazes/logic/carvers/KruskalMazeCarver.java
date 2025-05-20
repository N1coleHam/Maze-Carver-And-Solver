package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        Collection<EdgeWithData<Room, Wall>> edges = new HashSet<>();
        for (Wall wall : walls) {
            int weight = rand.nextInt();
            edges.add(new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), weight, wall));
        }
        // Hint: you'll probably need to include something like the following:
        Set<Wall> removedWalls = new HashSet<>();

        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> minSpanTree =
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));

        if (!minSpanTree.exists()) {
            return removedWalls;
        }

        for (EdgeWithData<Room, Wall> edge: minSpanTree.edges()) {
            removedWalls.add(edge.data());
        }

        return removedWalls;
    }
}
