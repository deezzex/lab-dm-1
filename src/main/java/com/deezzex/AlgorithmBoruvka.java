package com.deezzex;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlgorithmBoruvka {
    @Getter
    private int totalWeight;
    private final MutableValueGraph<Integer, Integer> inputGraph;

    public MutableValueGraph<Integer, Integer> findMst(){
        MutableValueGraph<Integer, Integer> mst = ValueGraphBuilder
                .undirected()
                .build();

        int size = inputGraph.nodes().size();

        GraphHelper uf = new GraphHelper(size);

        for (int t = 1; t < size && mst.edges().size() < size - 1; t = t + t) {

            EndpointPair<Integer>[] closestEdgeArray = new EndpointPair[size];

            for (EndpointPair<Integer> edge : inputGraph.edges()) {
                int u = edge.nodeU();
                int v = edge.nodeV();
                int uParent = uf.getNode(u);
                int vParent = uf.getNode(v);
                if (uParent == vParent) {
                    continue;
                }

                int weight = inputGraph.edgeValueOrDefault(u, v, 0);

                if (closestEdgeArray[uParent] == null) {
                    closestEdgeArray[uParent] = edge;
                }
                if (closestEdgeArray[vParent] == null) {
                    closestEdgeArray[vParent] = edge;
                }

                int uParentWeight = inputGraph.edgeValueOrDefault(closestEdgeArray[uParent].nodeU(), closestEdgeArray[uParent].nodeV(), 0);
                int vParentWeight = inputGraph.edgeValueOrDefault(closestEdgeArray[vParent].nodeU(), closestEdgeArray[vParent].nodeV(), 0);

                if (weight < uParentWeight) {
                    closestEdgeArray[uParent] = edge;
                }
                if (weight < vParentWeight) {
                    closestEdgeArray[vParent] = edge;
                }
            }

            for (int i = 0; i < size; i++) {
                EndpointPair<Integer> edge = closestEdgeArray[i];
                if (edge != null) {
                    int u = edge.nodeU();
                    int v = edge.nodeV();
                    int weight = inputGraph.edgeValueOrDefault(u, v, 0);

                    if (uf.getNode(u) != uf.getNode(v)) {
                        mst.putEdgeValue(u, v, weight);
                        totalWeight += weight;
                        uf.mergeNodes(u, v);
                    }
                }
            }
        }

        return mst;
    }
}