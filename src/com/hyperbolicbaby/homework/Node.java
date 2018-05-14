/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyperbolicbaby.homework;

import java.util.List;
import java.util.Stack;

import java.util.ArrayList;

/**
 *
 * @author KristianVikernes
 *
 */
public class Node {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private int nodeID;
    private boolean visited;
    private int fatherNodeID;
    private Stack<Integer> idOfNodesNotVisited;
    private List<Node> childList;

    private static final String DISCOVER = "DISCOVER";
    private static final String RETURN = "RETURN";
    private static final String REJECT = "REJECT";

    //Aditional attributes for the LeaderElection Algorithm    
    public enum State {
        AWAKE, ASLEEP, CANDIDATE, DEFEATED, ELECTED;
    };
    private State state;
    public int leader = 0; // 0 on Marcelín's Book, but here it will contain leader's id
    public int counter = 0;

    // Constructor for DFS
    public Node(int nodeID) {

        this.nodeID = nodeID;
        this.visited = false;
        this.idOfNodesNotVisited = new Stack<>();
        this.childList = new ArrayList<>();

    }

    // Aditional Constructor
    public Node() {

    }

    public void sendCandidateID(int candidateNodeID) throws InterruptedException {

        Thread.sleep(2000);

        if (this.getNodeID() > candidateNodeID) {
            System.out.println(ANSI_GREEN + "I'M NODE: " + this.getNodeID() + ". I SEND MY OWN ID TO NODE: " + this.getChildList().get(0).getNodeID() + ANSI_RESET);
            this.getChildList().get(0).receiveCandidateID(this.getNodeID());
            this.setState(Node.State.AWAKE);
        } else {
            System.out.println(ANSI_GREEN + "I'M NODE: " + this.getNodeID() + ". I SEND ID " + candidateNodeID + " TO NODE: " + this.getChildList().get(0).getNodeID()
                    + " BECAUSE IT'S A BETTER CANDIDATE AT THIS TIME" + ANSI_RESET);
            this.getChildList().get(0).receiveCandidateID(candidateNodeID);
            this.setState(Node.State.AWAKE);
        }

        
    }

    public void sendElectedNodeID(int candidateNodeID) throws InterruptedException {

        Thread.sleep(2000);

        this.setLeader(candidateNodeID);

        if (candidateNodeID == this.getNodeID()) {
            System.out.println(ANSI_CYAN + "I'M THE LEADER ELECTED -> ID: " + this.getNodeID() + ANSI_RESET);
            this.setState(Node.State.ELECTED);
            //System.exit(0);
        }

        this.setState(Node.State.DEFEATED);
        this.sendElectedNodeID(candidateNodeID);

    }

    //Executed on receiver Node
    public void receiveCandidateID(int candidateNodeID) throws InterruptedException {

        Thread.sleep(2000);

        if (candidateNodeID > this.getNodeID()) {
            System.out.println("I'M NODE: " + this.getNodeID() + ". THE CANDIDATE TO LEADER AT THIS TIME IS: " + candidateNodeID + " AND HAS A HIGHER ID THAN MINE.");
            this.setLeader(candidateNodeID);
            this.sendCandidateID(candidateNodeID);
        } else if (candidateNodeID < this.getNodeID() && this.getState() != State.AWAKE) {
            System.out.println(ANSI_BLUE + "I'M NODE: " + this.getNodeID() + ". THE CANDIDATE HAS AN ID LESS OR EQUAL THAN MINE, SO I'M THE CANDIDATE NOW");
            this.sendCandidateID(this.getNodeID());
        } else if (candidateNodeID == this.getNodeID()) {
            this.setLeader(candidateNodeID);
            System.out.println(ANSI_RED + "I'M NODE: " + this.getNodeID() + " AND I'M THE LEADER!!!!. I'LL SEND MY ID TO THE OTHER NODES.");
            this.sendElectedNodeID(this.getNodeID());

        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getFatherNodeID() {
        return fatherNodeID;
    }

    public void setFatherNodeID(int fatherNodeID) {
        this.fatherNodeID = fatherNodeID;
    }

    public Stack<Integer> getIdOfNodesNotVisited() {
        return idOfNodesNotVisited;
    }

    public void setIdOfNodesNotVisited(Stack<Integer> idOfNodesNotVisited) {
        this.idOfNodesNotVisited = idOfNodesNotVisited;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    //--
    public void sendDiscover(int nodeID, String DISCOVER) throws InterruptedException {

        Thread.sleep(2000);

        System.out.println(ANSI_GREEN + "I'M NODE: " + this.getNodeID() + ". I SEND 'DISCOVER' TO THE NODE WITH ID: " + nodeID + ANSI_RESET);

        for (int i = 0; i < this.getChildList().size(); i++) {
            if (this.getChildList().get(i).getNodeID() == nodeID) {
                this.getChildList().get(i).receiveMessage(this.getNodeID(), DISCOVER);
            }
        }

    }

    public void sendReturn(int nodeID, String RETURN) throws InterruptedException {

        Thread.sleep(1000);

        System.out.println("I'M NODE: " + this.getNodeID() + ". I HAVE NO NEIGHBOURS TO VISIT.");
        System.out.println("I'M NODE: " + this.getNodeID() + ". I SEND 'RETURN' TO THE NODE WITH ID: " + nodeID + ".\n");
        receiveMessage(nodeID, RETURN);

    }

    public void sendReject(int nodeID, String REJECT) throws InterruptedException {

        Thread.sleep(1000);

        System.err.println("I'M NODE: " + this.getNodeID() + ". I'VE BEEN VISITED.");
        System.err.println("I'M NODE: " + this.getNodeID() + ". I SEND 'REJECT' TO THE NODE WITH ID: " + nodeID);
        receiveMessage(nodeID, REJECT);

    }

    public void receiveMessage(int nodeID, String messageReceived) throws InterruptedException {

        Thread.sleep(1000);

        switch (messageReceived) {
            case DISCOVER:
                System.out.println(ANSI_BLUE + "\nI'M NODE: " + this.getNodeID() + ". I RECEIVE DISCOVER FROM NODE: " + nodeID + ANSI_RESET);

                if (this.isVisited()) {
                    this.sendReject(nodeID, REJECT);
                } else {
                    System.out.println(ANSI_BLUE + "I'M NODE: " + this.getNodeID() + ". I'VE NOT BEEN VISITED YET. I ADD NODE: [" + nodeID + "] AS MY FATHER" + ANSI_RESET);
                    this.setVisited(true);
                    this.setFatherNodeID(nodeID);

                    if (!this.getIdOfNodesNotVisited().isEmpty()) {
                        System.out.println("I'M NODE: " + this.getNodeID() + ". I'VE NEIGHBOURS. I CONTINUE EXPLORING.\n");
                        int idNextNode = this.getIdOfNodesNotVisited().pop();
                        this.sendDiscover(idNextNode, DISCOVER);
                        //Experimental
                        this.sendReturn(nodeID, RETURN);
                    }

                }

                break;

            case RETURN:
                System.out.println("I'M NODE: " + nodeID + ". I RECEIVE RETURN FROM NODE: " + this.getNodeID());

                if (!this.getIdOfNodesNotVisited().isEmpty()) {

                    System.out.println("I'M NODE: " + this.getNodeID() + ". I'VE NEIGHBOURS TO VISIT. I CONTINUE EXPLORING.\n");
                    int nodeToVisit = this.getIdOfNodesNotVisited().pop();
                    sendDiscover(nodeToVisit, DISCOVER);
                }

                break;

            case REJECT:
                System.err.println("I'M NODE: " + nodeID + ". I RECEIVE REJECT FROM NODE: " + this.getNodeID() + "\n");

                break;
        }

    }

    @Override
    public String toString() {

        if (this.getFatherNodeID() == 0) {
            return "Node Attributes:\n" + "nodeID: " + this.getNodeID()
                    + " [I'M THE TREE'S ROOT]"
                    + "\nvisited: " + this.isVisited();
        } else if (this.getLeader() != 0) {
            return "Node Attributes:\n" + "nodeID: " + this.getNodeID()
                    + "\nNode State: " + this.getState()
                    + "\nLeader Node: " + this.getLeader();
        } else {
            return "Node Attributes:\n" + "nodeID: " + this.getNodeID()
                    + "\nvisited: " + this.isVisited()
                    + "\nfatherNode: " + this.getFatherNodeID();
        }

    }

}
