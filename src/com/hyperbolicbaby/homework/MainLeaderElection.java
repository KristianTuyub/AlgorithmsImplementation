/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyperbolicbaby.homework;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author KristianVikernes
 *
 */

public class MainLeaderElection {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indicate the file name: ");
        String fileName = scanner.nextLine() + ".txt";
        
        GraphRing graph = new GraphRing(fileName);
        
        /* For Cycle to get all Node's attributes [DEBUGGING]
        for (int index = 0; index < graph.getNodes().size(); index++) {
            System.out.println(graph.getNodes().get(index).toString());
        }*/
        System.out.println("");
        
        System.out.println("LEADER ELECTION ALGORITHM (LCR) STARTS");
        graph.leaderElection(graph.getNodes().get(0));

        for (int i = 0; i < 45; i++) {
            System.out.print("_");
        }

        System.out.print("\nALL NODES HAVE BEEN VISITED.\nLEADER ELECTION ALGORITHM (LCR) EXECUTION ENDS\n");

        for (int i = 0; i < 45; i++) {
            System.out.print("_");
        }

        System.out.println("\nFinal RING'S Config");
        
        for (int i = 0; i < graph.getNodes().size(); i++) {
            if (i == 0) {
                continue;
            }
            
            graph.getNodes().get(i).setLeader(graph.getNodes().get(graph.getNodes().size()).getNodeID());
        }
        
        for (int i = 0; i < graph.getNodes().size(); i++) {
            System.out.println(graph.getNodes().get(i).toString());
        }
    }
}
