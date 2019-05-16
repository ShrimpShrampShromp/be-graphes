package org.insa.algo.shortestpath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        
        
        final int tailleGraphe = graph.size();
       
       //Tableau de labels pour les modifier, tas de labels pour dérouler l'algo 
        List<Node> predecessors = graph.getNodes();
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        Node startingNode = data.getOrigin();
        Node destNode = data.getDestination();
        
        //initialize label array
        
        for (int i = 0; i<tailleGraphe; i++) {
        	Node n = graph.getNodes().get(i);
        	labels.add(i, new Label(n));
        	tas.insert(new Label(n));
        }
        
        //initialisation du sommet du tas
        Label startingLabel = new Label(startingNode);
        startingLabel.setCost(0);
        tas.insert(startingLabel);
        
        //actual algorithm
        
        boolean fin = false;
        Label currentLabel, nextLabel;
        
        //Observer function
        notifyOriginProcessed(startingNode);
        
        while (!tas.isEmpty() && !fin) {
        	
        	currentLabel = tas.deleteMin();
        	currentLabel.setMark(true);
        	
        	for (Arc arc : currentLabel.getNode().getSuccessors()) {
        		//Petit test supplémentaire pour vérifier si l'on est autorisé à prendre le dit arc
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		
        		int nextId = arc.getDestination().getId();
        		nextLabel = labels.get(nextId);
        		if (nextLabel.isMarked()) {
        			continue;
        		}
        		
        		//Observer function : Node is visited for the first time
        		notifyNodeReached(arc.getDestination());
        		
        		//Creating the cost variable
        		double cout = Math.min(nextLabel.getCost(), currentLabel.getCost() + arc.getLength());
        		
        		//Check wether we have to update the cost or not
        		if (cout != nextLabel.getCost()) {
            			nextLabel.setCost(cout);
            			nextLabel.setPrevious(arc.getOrigin());
            			labels.set(nextId, nextLabel);
            			tas.insert(nextLabel);
        		}
        		
        		//if we arrived at the last Node, exit the loop
        		if (currentLabel.getNode() == destNode) {
        			//Notify the observers : we're at the end
        			notifyDestinationReached(destNode);
        			fin = true;
        		}
        	}
        }
        ShortestPathSolution solution = null;

        // Destination has no predecessor, the solution is infeasible...
        if (labels.get(destNode.getId()).getPrevious() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        
        //faut reprendre à partir d'ici : c'est la fin de bellman ford faut juste un peu modifier
        
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }    
        return solution;
    }
    
    
}
