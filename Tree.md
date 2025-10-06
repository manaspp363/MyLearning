# 🌳 Tree Data Structure in Java

Trees are **non-linear data structures** where data is organized hierarchically. Unlike arrays or linked lists, tree nodes are not stored sequentially but rather across multiple levels.

## 📁 Real-World Analogies
- File system folders
- HTML or YAML tag structures
- Organizational charts

## 🌱 Basic Operations
- **Create** – Initialize a tree
- **Insert** – Add data to the tree
- **Search** – Find specific data
- **Traversal**
  - Depth-First Search (DFS)
  - Breadth-First Search (BFS)

## 🧠 Java Implementation

```java
import java.io.*;
import java.util.*;

class GFG {

    public static void printParents(int node, Vector<Vector<Integer>> adj, int parent) {
        if (parent == 0)
            System.out.println(node + "->Root");
        else
            System.out.println(node + "->" + parent);

        for (int i = 0; i < adj.get(node).size(); i++)
            if (adj.get(node).get(i) != parent)
                printParents(adj.get(node).get(i), adj, node);
    }

    public static void printChildren(int Root, Vector<Vector<Integer>> adj) {
        Queue<Integer> q = new LinkedList<>();
        q.add(Root);
        int vis[] = new int[adj.size()];
        Arrays.fill(vis, 0);

        while (!q.isEmpty()) {
            int node = q.poll();
            vis[node] = 1;
            System.out.print(node + "-> ");

            for (int i = 0; i < adj.get(node).size(); i++) {
                if (vis[adj.get(node).get(i)] == 0) {
                    System.out.print(adj.get(node).get(i) + " ");
                    q.add(adj.get(node).get(i));
                }
            }
            System.out.println();
        }
    }

    public static void printLeafNodes(int Root, Vector<Vector<Integer>> adj) {
        for (int i = 1; i < adj.size(); i++)
            if (adj.get(i).size() == 1 && i != Root)
                System.out.print(i + " ");
        System.out.println();
    }

    public static void printDegrees(int Root, Vector<Vector<Integer>> adj) {
        for (int i = 1; i < adj.size(); i++) {
            System.out.print(i + ": ");
            if (i == Root)
                System.out.println(adj.get(i).size());
            else
                System.out.println(adj.get(i).size() - 1);
        }
    }

    public static void main(String[] args) {
        int N = 7, Root = 1;
        Vector<Vector<Integer>> adj = new Vector<>();
        for (int i = 0; i < N + 1; i++) {
            adj.add(new Vector<>());
        }

        adj.get(1).add(2); adj.get(2).add(1);
        adj.get(1).add(3); adj.get(3).add(1);
        adj.get(1).add(4); adj.get(4).add(1);
        adj.get(2).add(5); adj.get(5).add(2);
        adj.get(2).add(6); adj.get(6).add(2);
        adj.get(4).add(7); adj.get(7).add(4);

        System.out.println("The parents of each node are:");
        printParents(Root, adj, 0);

        System.out.println("The children of each node are:");
        printChildren(Root, adj);

        System.out.println("The leaf nodes of the tree are:");
        printLeafNodes(Root, adj);

        System.out.println("The degrees of each node are:");
        printDegrees(Root, adj);
    }
}
