package org.insa.graph;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.AbstractInputData;

public class LabelStar extends Label implements Comparable<Label>{
	
	double borneInf = 0;
	public LabelStar(Node N, ShortestPathData data) {
		super(N);
		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.borneInf = (float)Point.distance(N.getPoint(),data.getDestination().getPoint());
		}
		else if (data.getMode() == AbstractInputData.Mode.TIME) {
			int maxSpeed = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.borneInf = (float)Point.distance(N.getPoint(),data.getDestination().getPoint())/(maxSpeed*1000.0f/3600.0f);
			if (this.borneInf == -1) {
				this.borneInf = 20;
			}
		}
		
	}
	
	@Override
	//Redefine the getCost method, in order to guarantee our new ordering condition in the binary heap
	public double getTotalCost() {
		return this.borneInf + this.cost;
	}
	
}
