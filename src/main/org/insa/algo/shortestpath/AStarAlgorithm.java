package org.insa.algo.shortestpath;

import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected Label createNewLabel(Node n, ShortestPathData data) {
    	LabelStar l = new LabelStar(n, data);
    	return l;
    }
}
