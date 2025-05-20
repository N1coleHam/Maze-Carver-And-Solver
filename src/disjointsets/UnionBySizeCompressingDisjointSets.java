package disjointsets;

import java.util.ArrayList;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */
    private List<T> sets;

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        sets = new ArrayList<>();
    }

    @Override
    public void makeSet(T item) {
        sets.add(item);
        pointers.add(-1);
    }

    @Override
    public int findSet(T item) {
        if (!sets.contains(item)) {
            throw new IllegalArgumentException(item + " is not in any set.");
        }

        int index = sets.indexOf(item);
        List<Integer> path = new ArrayList<>();

        // have to keep on going through the set until it reaches the index that is a negative number
        while (pointers.get(index) >= 0) {
            path.add(index);
            index = pointers.get(index);
        }

        // perform path compression on all nodes
        for (int node : path) {
            pointers.set(node, index);
        }

        return index;
    }

    @Override
    public boolean union(T item1, T item2) {
        int index1 = findSet(item1);
        int index2 = findSet(item2);

        // Same parent index, in the same set
        if (index1 == index2) {
            return false;
        }

        int size1 = pointers.get(index1);
        int size2 = pointers.get(index2);
        if (size1 < size2 || size1 == size2) {
            // Size 1 is bigger than size 2
            pointers.set(index1, size1 + size2);
            pointers.set(index2, index1);
        } else {
            // Size 2 is bigger than size 1
            pointers.set(index2, size2 + size1);
            pointers.set(index1, index2);
        }

        return true;
    }
}
