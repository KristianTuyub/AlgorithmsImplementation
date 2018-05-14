/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hyperbolicbaby.homework;

import java.util.Scanner;

/**
 *
 * @author KristianVikernes
 *
 */

public class MainDFS {
    
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indicate the file name: ");
        String fileName = scanner.nextLine() + ".txt";
        
        Graph graph = new Graph(fileName);
        
        /* For Cycle to get all Node's attributes [DEBUGGING]
        for (int index = 0; index < graph.getNodes().size(); index++) {
            System.out.println(graph.getNodes().get(index).toString());
        }*/
        
        System.out.println("");
        
        graph.dfsAlgorithm(graph.getNodes().get(0));
        
        for (int i = 0; i < 45; i++) {
            System.out.print("_");
        }
        
        System.out.print("\nALL NODES HAVE BEEN VISITED.\nDFS ALGORITHM EXECUTION ENDS\n");
        
        for (int i = 0; i < 45; i++) {
            System.out.print("_");
        }
        
        System.out.println("\nFinal Config - Spanning Tree");
        
        for (int i = 0; i < graph.getNodes().size(); i++) {
            System.out.println(graph.getNodes().get(i).toString());
        }
        
        
    }
}
