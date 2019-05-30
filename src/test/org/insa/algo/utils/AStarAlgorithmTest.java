package org.insa.algo.utils;

import org.junit.Test;

public class AStarAlgorithmTest {

		@Test
		public void testDoScenarioDistanceBretagne() throws Exception {
			
			//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr";
			String mapName = "E:\\cartes\\bretagne.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\bretagne.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
			String mapName = "E:\\cartes\\bretagne.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\bretagne.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
			String mapName = "E:\\cartes\\insa.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\insa.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
			String mapName = "E:\\cartes\\insa.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\insa.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
			String mapName = "E:\\cartes\\carre-dense.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\carre-dense.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
			String mapName = "E:\\cartes\\carre-dense.mapgr";
			//String mapName = "D:\\Cours\\Cartes\\carre-dense.mapgr";
			
			AStarTestWithMap test = new  AStarTestWithMap();
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
}
