package org.insa.algo.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.algo.shortestpath.*;

import org.junit.Test;

public class DijkstraTestWithMap {

	@Test
	// MODE : 0 = TIME, 1 = LENGTH
	public void testScenario(String mapName, int typeEvaluation, int origine, int destination) throws Exception {
		//public void testScenario(String mapName, int typeEvaluation, Node origine, Node destination) throws Exception {

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (typeEvaluation!=0 && typeEvaluation!=1) {
			System.out.println("Invalid argument");
		} else {
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { 
				System.out.println("ERROR : Invalid parameters");
				
			} else {
				ArcInspector arcInspectorDijkstra;
				
				if (typeEvaluation == 0) {
					System.out.println("MODE : TIME");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
				} else {
					System.out.println("MODE : DIISTANCE");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
				}
				
				System.out.println("ORIGIN : " + origine);
				System.out.println("DESTINATION : " + destination);
				
				if(origine==destination) {
					System.out.println("ORIGIN AND DESTINATION ARE THE SAME");
					System.out.println("SOLUTION'S COST : 0");
					
				} else {			
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);
					
					// Getting the Bellman & Dijkstra solutions for comparison
					ShortestPathSolution solution = D.run();
					ShortestPathSolution expected = B.run();
	
					
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.println("NO SOLUTION");
						System.out.println("(infinite)");
					}
					// Shortest path was found
					else {
						double costSolution;
						double costExpected;
						if(typeEvaluation == 0) {
							//Computing the solution's cost
							costSolution = solution.getPath().getMinimumTravelTime();
							costExpected = expected.getPath().getMinimumTravelTime();
						} else {
							costSolution = solution.getPath().getLength();
							costExpected = expected.getPath().getLength();
						}
						assertEquals(costExpected, costSolution, 0.001);
						System.out.println("SOLUTION COST: " + costSolution);
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}
}