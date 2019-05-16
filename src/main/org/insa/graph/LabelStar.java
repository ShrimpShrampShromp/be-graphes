package org.insa.graph;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.AbstractInputData;

public class LabelStar extends Label{
	
	double borneInf = 0;
	public LabelStar(Node N, ShortestPathData data) {
		super(N);
		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.borneInf = this.getNode().getPoint().distanceTo(data.getDestination().getPoint());
		}
		else if (data.getMode() == AbstractInputData.Mode.TIME) {
			this.borneInf = this.getNode().getPoint().distanceTo(data.getDestination().getPoint())/data.getMaximumSpeed();
		}
		
	}
	
	
}
