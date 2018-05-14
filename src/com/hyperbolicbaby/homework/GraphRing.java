/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyperbolicbaby.homework;

import java.util.List;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author KristianVikernes
 *
 */

public class GraphRing {

    private List<Node> nodes;
    
    private static final String DISCOVER = "DISCOVER";
    private static final String RETURN = "RETURN";
    private static final String REJECT = "REJECT";

    /**
     * Load Node list and their children/neighbors from a txt file. txt
     * structure:      *
     * 1st line -> nodeList size 2nd line to Xline -> id of each node (in this
     * implementation we use integers) XLine + 1 to last line -> Nodes
     * adjacencies (p. ej. : 2 3, node 2 is adjacent to node 3, and vice versa).
     */

    public GraphRing(String fileName) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(fileName));

        int nodeListSize = scanner.nextInt();

        nodes = Arrays.asList(new Node[nodeListSize]);

        Node dummyNode;

        // node creation
        for (int nodeListIndex = 0; nodeListIndex < nodes.size(); nodeListIndex++) {
            dummyNode = new Node(scanner.nextInt());
            nodes.set(nodeListIndex, dummyNode);
        }

        // adjacent node adder (every node only knows their neighbors' identities whom share its edge)
        while (scanner.hasNext()) {

            int v1 = (scanner.nextInt());
            int v2 = (scanner.nextInt());

            nodes.get(v1 - 1).getChildList().add(nodes.get(v2 - 1));

        }

        Node anotherDummyNode;
        Stack<Integer> stackOfNodeNeighborsID;
        //Get ids from nodes on childList to the node's stack of not visited nodes to initialize it and to know its neighbors.
        for (int indexS = 0; indexS < nodes.size(); indexS++) {

            anotherDummyNode = nodes.get(indexS);

            //System.out.println(anotherDummyNode.toString() + " FOR OF ADDING IDS TO STACK");
            stackOfNodeNeighborsID = new Stack<>();

            for (int indexR = 0; indexR < anotherDummyNode.getChildList().size(); indexR++) {
                stackOfNodeNeighborsID.add(anotherDummyNode.getChildList().get(indexR).getNodeID());
            }

            nodes.get(indexS).setIdOfNodesNotVisited(stackOfNodeNeighborsID);
            
            /*PRINTS FOR DEBUGGING*/
            
            //System.out.println(nodes.get(indexS).toString() + " END OF 1 CYCLE FOR WITH STACK OF NEIGHBOR NODES' ID ADDED");
            //nodes.get(indexS).setIdOfNodesNotVisited(nodes);
            //nodes.get(indexS).getIdOfNodesNotVisited().addAll(nodes.get(indexS).getChildList());
        }

    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public void continueExploration(int nodeID) throws InterruptedException {
        
        nodes.get(nodeID).getChildList();
        nodes.get(nodeID).sendDiscover(nodeID, DISCOVER);
    }

    public void dfsAlgorithm(Node node) throws InterruptedException {
               
        node.setVisited(true);
        
        List<Node> neighbours = node.getChildList();
        
        node.sendDiscover(neighbours.get(0).getNodeID(), DISCOVER);
        
        if (!node.getIdOfNodesNotVisited().isEmpty()) {
            node.sendDiscover(node.getIdOfNodesNotVisited().pop(), DISCOVER);
        }

        //receiveMessage() is called on the receiver node when a Message is sent. 
    }
    
    public void leaderElection(Node node) throws InterruptedException {
        
        node.setState(Node.State.AWAKE);
        node.sendCandidateID(node.getNodeID());
        
    }

}
