package com.deezzex;

public class GraphHelper {
    private final int[] parentNodes;
    private final int[] rankOfNodes;

    public GraphHelper(int n) {
        parentNodes = new int[n];
        rankOfNodes = new int[n];
        for (int i = 0; i < n; i++) {
            parentNodes[i] = i;
            rankOfNodes[i] = 0;
        }
    }

    public int getNode(int u) {
        while (u != parentNodes[u]) {
            u = parentNodes[u];
        }
        return u;
    }

    public void mergeNodes(int u, int v) {
        int uParent = getNode(u);
        int vParent = getNode(v);
        if (uParent == vParent) {
            return;
        }

        if (rankOfNodes[uParent] < rankOfNodes[vParent]) {
            parentNodes[uParent] = vParent;
        } else if (rankOfNodes[uParent] > rankOfNodes[vParent]) {
            parentNodes[vParent] = uParent;
        } else {
            parentNodes[vParent] = uParent;
            rankOfNodes[uParent]++;
        }
    }
}
