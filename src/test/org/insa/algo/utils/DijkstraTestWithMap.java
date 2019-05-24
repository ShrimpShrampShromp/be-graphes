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
	// typeEvaluation : 0 = temps, 1 = distance
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
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { // On est hors du graphe. / Sommets inexistants
				System.out.println("ERROR : Invalid parameters");
				
			} else {
				ArcInspector arcInspectorDijkstra;
				
				if (typeEvaluation == 0) { //Temps
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
					
					// Recuperation des solutions de Bellman et Dijkstra pour comparer 
					ShortestPathSolution solution = D.run();
					ShortestPathSolution expected = B.run();
	
					
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.println("NO SOLUTION");
						System.out.println("(infinite)");
					}
					// Un plus court chemin trouve 
					else {
						double costSolution;
						double costExpected;
						if(typeEvaluation == 0) { //Temps
							//Calcul du cout de la solution 
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


	@Test
	/* Verifie que le temps du chemin le plus rapide est inferieur au temps du chemin le plus court */
	/* Et verifie que la distance du chemin le plus rapide est superieur a la distance du chemin le plus court */
	public void testScenarioSansOracle(String mapName, int origine, int destination) throws Exception {

		double costFastestSolutionInTime = Double.POSITIVE_INFINITY;
		double costFastestSolutionInDistance = Double.POSITIVE_INFINITY;
		double costShortestSolutionInTime = Double.POSITIVE_INFINITY;
		double costShortestSolutionInDistance = Double.POSITIVE_INFINITY;

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { // On est hors du graphe. / Sommets inexistants
			System.out.println("ERROR : invalid parameters");
			
		} else {
			System.out.println("ORIGIN : " + origine);
			System.out.println("DESTINATION : " + destination);
			
			if(origine==destination) {
				System.out.println("ORIGIN AND DESTINATION ARE THE SAME");
				System.out.println("All costs are set to 0.");
				
			} else {
		
				/** Recherche du chemin le plus rapide **/
				ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		
				ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		
				/* Recuperation de la solution de Dijkstra */
				ShortestPathSolution solution = D.run();
		
				/* Pas de chemin trouve */
				if (solution.getPath() == null) {
					System.out.println("NO PATH WAS FOUND");
				}
				/* Un plus court chemin trouve */
				else {
					/* Calcul du cout de la solution (en temps et en distance) */
					costFastestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costFastestSolutionInDistance = solution.getPath().getLength();
				}
		
		
				/** Recherche du chemin le plus court **/
		
				arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		
				data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				D = new DijkstraAlgorithm(data);
		
				/* Recuperation de la solution de Dijkstra */
				solution = D.run();
	
				/* Pas de chemin trouve */
				if (solution.getPath() == null) {
					System.out.println("NO PATH WAS FOUND");
				}
				/* Un plus court chemin trouve */
				else {				
					/* Calcul du cout de la solution (en temps et en distance) */
					costShortestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costShortestSolutionInDistance = solution.getPath().getLength();
				}
			
			
				/* Verifie que le temps du chemin le plus rapide est inferieur au temps du chemin le plus court */
				assertTrue(costFastestSolutionInTime <= costShortestSolutionInTime);
				System.out.println("SHORTEST PATH'S COST : " + costFastestSolutionInTime);
				System.out.println("FASTEST PATH'S COST  : " + costShortestSolutionInTime);
		
				/* Et verifie que la distance du chemin le plus rapide est superieur a la distance du chemin le plus court */
				assertTrue(costFastestSolutionInDistance >= costShortestSolutionInDistance);
				System.out.println("SHORTEST PATH'S COST : " + costFastestSolutionInDistance);
				System.out.println("FASTEST PATH'S COST  : " + costShortestSolutionInDistance);
	
			}
		}
		System.out.println();
		System.out.println();
	}
}