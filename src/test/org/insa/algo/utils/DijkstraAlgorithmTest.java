package org.insa.algo.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;

import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {

	// Small graph use for tests
	private static Graph graph;

	// List of nodes
	private static Node[] nodes;

	// List of arcs in the graph, a2b is the arc from node A (0) to B (1).
	@SuppressWarnings("unused")
	private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;

	@BeforeClass
	public static void initAll() throws IOException {

		// Define roads
		RoadInformation RoadInfo = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, null);

		// Create nodes
		nodes = new Node[6];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node(i, null);
		}

		// Add arcs...
		a2b = Node.linkNodes(nodes[0], nodes[1], 7, RoadInfo, null);
		a2c = Node.linkNodes(nodes[0], nodes[2], 8, RoadInfo, null);
		b2d = Node.linkNodes(nodes[1], nodes[3], 4, RoadInfo, null);
		b2e = Node.linkNodes(nodes[1], nodes[4], 1, RoadInfo, null);
		b2f = Node.linkNodes(nodes[1], nodes[5], 5, RoadInfo, null);
		c2a = Node.linkNodes(nodes[2], nodes[0], 7, RoadInfo, null);
		c2b = Node.linkNodes(nodes[2], nodes[1], 2, RoadInfo, null);
		c2f = Node.linkNodes(nodes[2], nodes[5], 2, RoadInfo, null);
		e2c = Node.linkNodes(nodes[4], nodes[2], 2, RoadInfo, null);
		e2d = Node.linkNodes(nodes[4], nodes[3], 2, RoadInfo, null);
		e2f = Node.linkNodes(nodes[4], nodes[5], 3, RoadInfo, null);
		f2e = Node.linkNodes(nodes[5], nodes[4], 3, RoadInfo, null);

		// Initialize the graph
		graph = new Graph("ID", "", Arrays.asList(nodes), null);

	}

	@Test
	public void testDoRun() {
		System.out.println("               VALIDITY TESTS WITH SIMPLE GRAPHS               ");

		for (int i=0;  i < nodes.length; ++i) {

			//Starting point display
			System.out.print("x"+(nodes[i].getId()+1) + ":");

			for (int j=0;  j < nodes.length; ++j) {

				if(nodes[i]==nodes[j]) {
					System.out.print("     -    ");
				}
				else{

					ArcInspector arcInspectorDijkstra = new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[i],nodes[j], arcInspectorDijkstra);

					BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);

					// Getting the Bellman & Dijkstra solutions for comparison
					ShortestPathSolution solution = Dijkstra.run();
					ShortestPathSolution expected = Bellman.run();

					// No Path has been found
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.print("infinite  ");
					}
					// A shortest path has been found
					else {

						// Total cost of the said path
						float costSolution = solution.getPath().getLength();
						float costExpected = expected.getPath().getLength();
						assertEquals(costExpected, costSolution, 0);

						List<Arc> arcs = solution.getPath().getArcs();
						Node originOfLastArc = arcs.get(arcs.size()-1).getOrigin();

						// Displays the cost and the Node preceding the destination(coût, sommet père du Dest) */
						System.out.print("("+costSolution+ ", x" + (originOfLastArc.getId()+1) + ") ");
					}
					
				}

			}
			System.out.println();
		}
		System.out.println();
	}

	@Test
	public void testDoScenarioDistanceBretagne() throws Exception {
		
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		String mapName = "D:\\Cours\\Cartes\\bretagne.mapgr";
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("---------------TESTS MADE WITH ORACLE---------------");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("               CURRENT MAP : BRETAGNE");
		System.out.println("               SHORTEST DISTANCE");
		System.out.println();
		
		System.out.println("|... NO PATH ...|");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName,1,origine,destination);    
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 143909;
		destination = 75535;
		test.testScenario(mapName,1,origine,destination);    	
	
		System.out.println("|... NON CONNECTED PATH ...|");
		origine = 126449;
		destination = 29331;
		test.testScenario(mapName,1,origine,destination); 
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...     NO ORIGIN      ...|");
		origine = -1;
		destination = 75535;
		test.testScenario(mapName,1,origine,destination);    	

		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...   NO DESTINATION   ...|");
		origine = 143909;
		destination = 1000000000;
		test.testScenario(mapName,1,origine,destination);    	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...      NOTHING       ...|");
		origine = -1;
		destination = 1000000000;
		test.testScenario(mapName,1,origine,destination);    	
	}

	
	@Test
	public void testDoScenarioTempsBretagne() throws Exception {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		String mapName = "D:\\Cours\\Cartes\\bretagne.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("               CURRENT MAP : BRETAGNE");
		System.out.println("               SHORTEST TIME");
		System.out.println();
		
		System.out.println("|... NO PATH ...|");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName,0,origine,destination);    
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 143909;
		destination = 75535;
		test.testScenario(mapName,0,origine,destination);    	
	
		System.out.println("|... NON CONNECTED PATH ...|");
		origine = 126449;
		destination = 29331;
		test.testScenario(mapName,0,origine,destination); 
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...     NO ORIGIN      ...|");
		origine = -1;
		destination = 75535;
		test.testScenario(mapName,0,origine,destination);    	

		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...   NO DESTINATION   ...|");
		origine = 143909;
		destination = 1000000000;
		test.testScenario(mapName,0,origine,destination);    	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...      NOTHING       ...|");
		origine = -1;
		destination = 1000000000;
		test.testScenario(mapName,0,origine,destination);      	
	}

	@Test
	public void testDoScenarioDistanceINSA() throws Exception {

		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "D:\\Cours\\Cartes\\insa.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("               CURRENT MAP : INSA");
		System.out.println("               SHORTEST DISTANCE");
		System.out.println();
		
		System.out.println("|... NO PATH ...|");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName,1,origine,destination);    
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 1303;
		destination = 112;
		test.testScenario(mapName,1,origine,destination);    	
	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...     NO ORIGIN      ...|");
		origine = -1;
		destination = 112;
		test.testScenario(mapName,1,origine,destination);    	

		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...   NO DESTINATION   ...|");
		origine = 1303;
		destination = 2000;
		test.testScenario(mapName,1,origine,destination);    	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...      NOTHING       ...|");
		origine = -1;
		destination = 2000;
		test.testScenario(mapName,1,origine,destination);    
	}

	@Test
	public void testDoScenarioTempsINSA() throws Exception {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "D:\\Cours\\Cartes\\insa.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("               CURRENT MAP : INSA");
		System.out.println("               SHORTEST TIME");
		System.out.println();
		
		System.out.println("|... NO PATH ...|");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName,0,origine,destination);    
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 1303;
		destination = 112;
		test.testScenario(mapName,0,origine,destination);    	
	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...     NO ORIGIN      ...|");
		origine = -1;
		destination = 112;
		test.testScenario(mapName,0,origine,destination);    	

		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...   NO DESTINATION   ...|");
		origine = 1303;
		destination = 2000;
		test.testScenario(mapName,0,origine,destination);    	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...      NOTHING       ...|");
		origine = -1;
		destination = 2000;
		test.testScenario(mapName,0,origine,destination);    
	}
	
	@Test
	public void testDoScenarioDistanceCarreDense() throws Exception {

		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		String mapName = "D:\\Cours\\Cartes\\carre-dense.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("               CURRENT MAP : CARRE-DENSE");
		System.out.println("               SHORTEST DISTANCE");
		System.out.println();
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 189230;
		destination = 219989;
		test.testScenario(mapName,1,origine,destination);    		
	}

	@Test
	public void testDoScenarioTempsCarreDense() throws Exception {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
		String mapName = "D:\\Cours\\Cartes\\carre-dense.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("               CURRENT MAP : CARRE-DENSE");
		System.out.println("               SHORTEST TIME");
		System.out.println();
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 189230;
		destination = 219989;
		test.testScenario(mapName,0,origine,destination); 			
	}

	@Test
	public void testDoScenarioMinTempsDistBretagne() throws Exception {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
		String mapName = "D:\\Cours\\Cartes\\bretagne.mapgr";
		
		DijkstraTestWithMap test = new  DijkstraTestWithMap();
		int origine;
		int destination;
		
		System.out.println("---------------TESTS MADE WITHOUT ORACLE---------------");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("               CURRENT MAP : BRETAGNE");
		System.out.println();

		System.out.println("|... NO PATH ...|");
		origine = 0 ;
		destination = 0;
		test.testScenarioSansOracle(mapName,origine,destination);    
		
		System.out.println("|... SIMPLE PATH ...|");
		origine = 143909;
		destination = 75535;
		test.testScenarioSansOracle(mapName,origine,destination);    	
	
		System.out.println("|... NON CONNECTED PATH ...|");
		origine = 126449;
		destination = 29331;
		test.testScenarioSansOracle(mapName,origine,destination);  
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...     NO ORIGIN      ...|");
		origine = -1;
		destination = 75535;
		test.testScenarioSansOracle(mapName,origine,destination);     	

		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...   NO DESTINATION   ...|");
		origine = 143909;
		destination = 1000000000;
		test.testScenarioSansOracle(mapName,origine,destination);    	
		
		System.out.println("|... NON EXISTING NODES ...|");
		System.out.println("|...      NOTHING       ...|");
		origine = -1;
		destination = 1000000000;
		test.testScenarioSansOracle(mapName,origine,destination); 
	}
}