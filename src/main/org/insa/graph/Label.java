package org.insa.graph;

public class Label implements Comparable<Label>{
	private Node currentNode;
	private boolean marked;
	protected double cost;
	private Node previousNode;
	private boolean dansTas;
	
	public Label(Node N) {
		this.currentNode = N;
		this.marked = false;
		this.previousNode = null;
		this.cost = Double.POSITIVE_INFINITY;
		this.dansTas = false;
	}
	
	//Returns the cost of the current label
	public double getCost() {
		return this.cost;
	}
	
	//Returns the total cost of the current Label
	public double getTotalCost() {
		return this.cost;
	}
	
	//Returns the Node to which the current label is associated
	public Node getNode() {
		return this.currentNode;
	}
	
	//Returns the previous Node, if it exists
	public Node getPrevious() {
		return this.previousNode;
	}
	
	//Marks the current label
	public void setMark(boolean b) {
		this.marked = b;
	}
	
	//Sets the previous Node
	public void setPrevious(Node n) {
		this.previousNode=n;
	}
	
	//Sets the cost of the Label
	public void setCost(double c) {
		this.cost=c;
	}
	
	//Returns true if the label is marked
	public boolean isMarked() {
		return this.marked;
	}
	
	//Purely symbolic way to put the label in the binary Heap, useful for the algorithms
	public void setDansTas() {
		this.dansTas = true;
	}
	
	//Returns true if the Label is in the binary heap
	public boolean isDansTas() {
		return this.dansTas;
	}
	
	/*This method is used in the binary heap class
	 * The only thing it needs to return is 1 or -1, depending on which cost is higher
	 */
	public int compareTo(Label L) {
		int retour = 0;
		if (this.getTotalCost() > L.getTotalCost()) retour = 1;
		else if (this.getTotalCost() == L.getTotalCost()) {
			if (this.cost > L.cost) retour = 1;
			else if (this.cost < L.cost) retour = -1;
		}
		else if (this.getTotalCost() < L.getTotalCost()) retour = -1;
		return retour;
	}
}
