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
	
	public double getCost() {
		return this.cost;
	}
	
	public Node getNode() {
		return this.currentNode;
	}
	
	public Node getPrevious() {
		return this.previousNode;
	}
	
	public void setMark(boolean b) {
		this.marked = b;
	}
	
	public void setPrevious(Node n) {
		this.previousNode=n;
	}
	
	public void setCost(double c) {
		this.cost=c;
	}
	
	public boolean isMarked() {
		return this.marked;
	}
	
	public void setDansTas() {
		this.dansTas = true;
	}
	
	public boolean isDansTas() {
		return this.dansTas;
	}
	
	public int compareTo(Label L) {
		return (int)(this.cost - L.getCost());
	}
}
