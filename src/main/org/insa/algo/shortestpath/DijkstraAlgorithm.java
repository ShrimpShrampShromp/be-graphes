package org.insa.algo.shortestpath;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.Label;
import org.insa.algo.utils.BinaryHeap; 


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected ShortestPathSolution doRun() {
    	
    	//retrieve graph
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        int tailleGraphe = graph.size();
       
        //Tableau de labels pour les modifier
        Label[] labels = new Label[tailleGraphe];
        
        //Tas de labels pour dérouler l'algo 
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        //Premier et dernier noeud
        Node startingNode = data.getOrigin();
        Node destNode = data.getDestination();        
        
        //initialisation du sommet du tas
        Label startingLabel = createNewLabel(startingNode, data);
        Label destLabel = createNewLabel(destNode, data);
        startingLabel.setCost(0);
        tas.insert(startingLabel);
        labels[startingNode.getId()] = startingLabel;
        labels[destNode.getId()] = destLabel;
        //initialize arc array for sortest path
        Arc[] predecessorArcs = new Arc[tailleGraphe];
        
        //actual algorithm
        boolean fin = false;
        boolean impossible = false;
        Label currentLabel, nextLabel;
        
        //Observer function: the algorithm is at first Node
        notifyOriginProcessed(startingNode);
        
        while (!tas.isEmpty() && !fin) {
        	
        	//Definition of the current label for later use
        	currentLabel = tas.deleteMin();
        	
        	//Observer function : Node is visited for the first time
    		notifyNodeReached(currentLabel.getNode());
    		
    		//We mark the label as we're visiting it
        	currentLabel.setMark(true);
    		
        	//if we arrived at the last Node, exit the loop
    		if (currentLabel.getNode() == destNode) {
    			fin = true;
    		}
    		
        	for (Arc arc : currentLabel.getNode().getSuccessors()) {
        		//Small complementary test to check if we're allowed to take this path
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		
        		//Definition of the current following Node & associated Label for later use
        		Node followingNode = arc.getDestination();
        		int nextId = followingNode.getId();
        		nextLabel = labels[nextId];
        		
        		if (nextLabel == null) {
        			//Notify the observers : we've reached a new Node
        			notifyNodeReached(arc.getDestination());
        			
        			//Creating a new Label for the table
					nextLabel = createNewLabel(followingNode, data);
					labels[nextLabel.getNode().getId()] = nextLabel;
        		}
        		
        		if (!nextLabel.isMarked()) {
        			
        			//Creating the cost variable
        			double nextLabelCost = nextLabel.getCost();
        			double currentLabelCost = currentLabel.getCost();
            		double cost = Math.min(nextLabelCost, currentLabelCost + (float)data.getCost(arc));
            		
            		//Check wether we have to update the cost or not
        			if(cost != nextLabelCost || (nextLabelCost==Float.POSITIVE_INFINITY)){
        				
        				//Update the cost
        				nextLabel.setCost(cost);
        				nextLabel.setPrevious(currentLabel.getNode());
        				
						//If the next label is already in the label BH, remove it
						if(nextLabel.isDansTas()) {
							tas.remove(nextLabel);
						}
						
						//If it is not, put it in
						else {
							nextLabel.setDansTas();
						}
						
						tas.insert(nextLabel);
						
						//Update the arc list for the final solution
						predecessorArcs[arc.getDestination().getId()] = arc;
					}
        		}   		
        	}
        }
        ShortestPathSolution solution = null;

        // Destination has no predecessor, the solution is infeasible...
        if (labels[destNode.getId()].getPrevious() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        // Destination non reachable, the solution is infeasible...
        else if (impossible == true) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[destNode.getId()];
            
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }
            
            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            Path solutionPath = new Path(graph, arcs);
            solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
            
            //Test the solution with the use of the path class' methods
            
        }    
        return solution;
    }
    
    //Create and return a new Label associated to the Node. This method is redefined in the AStarAlgorithm class.
    protected Label createNewLabel(Node n, ShortestPathData data) {
    	Label l = new Label(n);
    	return l;
    }
    
}
