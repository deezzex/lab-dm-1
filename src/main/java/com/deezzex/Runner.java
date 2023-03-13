package com.deezzex;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Runner {
    private static final String PATH_TO_FILE = "C:\\Users\\pshti\\IdeaProjects\\lab-dm-1\\src\\main\\resources\\matrix.txt";

    public static void main(String[] args) throws FileNotFoundException {
        int[][] matrix = getInputMatrix();

        printInputMatrix(matrix);

        MutableValueGraph<Integer, Integer> graph = getGraph(matrix);

        AlgorithmBoruvka algorithmBoruvka = new AlgorithmBoruvka(graph);

        MutableValueGraph<Integer, Integer> mst = algorithmBoruvka.findMst();

        System.out.println("Загальна вага остового дерева: " + algorithmBoruvka.getTotalWeight());
        System.out.println("Ребра остового дерева: " + mst.edges());
    }

    private static MutableValueGraph<Integer, Integer> getGraph(int[][] matrix) {
        MutableValueGraph<Integer, Integer> graph = ValueGraphBuilder.undirected().build();

        int x = 0;
        int y = 0;

        Map<String, Integer> cells = new HashMap<>();

        for (int[] curRow : matrix) {
            for (int curCol : curRow){
                if (curCol == 0) {
                    x++;
                    continue;
                }

                String cellId = "[" + y + ";" + x + "]";
                String inverseCellId = "[" + x + ";" + y + "]";

                if (!cells.containsKey(inverseCellId)){
                    cells.put(cellId, curCol);
                    graph.putEdgeValue(x, y, curCol);
                }

                x++;
            }
            y++;
            x = 0;
        }

        printEdges(cells);
        return graph;
    }

    private static void printEdges(Map<String, Integer> cells) {
        System.out.println("Ненульові ребра графу: ");
        for (Map.Entry<String, Integer> entry : cells.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();
    }

    private static void printInputMatrix(int[][] matrix) {
        System.out.println("Вхідна матриця: ");
        for (int[] curRow : matrix) {
            for (int curCol : curRow) System.out.printf("%d ", curCol);
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] getInputMatrix() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(PATH_TO_FILE)));
        boolean firstLine = true;
        int N = 0;
        int[][] matrix = new int[0][];

        while(sc.hasNextLine()) {
            if (firstLine){
                String[] line = sc.nextLine().trim().split(" ");
                N = Integer.parseInt(line[0]);
                firstLine = false;
            }

            matrix = new int[N][N];

            for (int i=0; i<matrix.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }

        return matrix;
    }
}