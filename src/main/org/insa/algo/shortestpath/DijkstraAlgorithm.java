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
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
        int tailleGraphe = graph.size();
        
        boolean fin = false;
        
        List<Label> labels = new ArrayList<Label>();
        List<Node> predecessors = graph.getNodes();
        BinaryHeap<Node> tas = new BinaryHeap<Node>();
        Node startingNode = data.getOrigin();
        
        for (int i = 0; i<tailleGraphe; i++) {
        	Node n = graph.getNodes().get(i);
        	labels.set(n.getId(), new Label(n));
        }
        
        //initialisation du sommet du tas
        labels.get(startingNode.getId()).setCost(0);
        
        tas.insert(startingNode);
        Label startingLabel = new Label(startingNode);
        Label tempLabel = startingLabel;
        Node tempNode = startingLabel.getNode();
        
        while (!tempLabel.isMarked() && !fin) {
        	tempLabel = labels.get(tas.findMin().getId());
        	tempLabel.setMark(true);
        	tempNode = tempLabel.getNode();
        	for (Arc arc : tempNode.getSuccessors()) {
        		Label Successor = labels.get(arc.getDestination().getId());
        		if (!Successor.isMarked()) {
        			double cout = Math.min(Successor.getCost(), tempLabel.getCost() + arc.getLength());
        			if (cout != Successor.getCost()) {
            			Successor.setCost(cout);
            			tas.insert(Successor.getNode());
            			tempLabel.setPrevious(arc.getOrigin());
        			}
        		}
        	}
        	//c'est pas exactement ça mais on fait la même fin que sur l'algo bellmanfordalgo
        	predecessors.add(tempLabel.getNode());
        }
        
        return solution;
    }
    
    
}
