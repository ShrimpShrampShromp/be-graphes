package org.insa.base;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;


import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathAlgorithm;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.*;



public class PerfTest {
	
	private String nom_carte;
	
	private int iters;
	
	private int max_id ;
	
	private Graph graph;
	
	File[] files;
	
	public PerfTest(Graph graph, int iters){
		this.nom_carte = graph.getMapName();
		this.iters = iters;
		max_id = graph.size();
		this.graph = graph;
	}


	public void generateTests() {
    	BufferedWriter distanceWriter = null;
    	BufferedWriter timeWriter = null;
        try {
            //create files
            File distanceFile = new File("./tests/" + nom_carte + "_distance" + iters + "-test_data.txt");
            File timeFile = new File("./tests/" + nom_carte + "_temps-" + iters + "-test_data.txt");
            // This will output the full path where the file will be written to...

            distanceWriter = new BufferedWriter(new FileWriter(distanceFile));
            timeWriter = new BufferedWriter(new FileWriter(timeFile));
            
            distanceWriter.write(nom_carte + "\n0\n" + iters);
            //là on génère tous les duos de points
            timeWriter.write(nom_carte + "\n1\n" + iters);
            //et bam on remet les duos de points. on peut peut etre le faire en même temps?
            for (int i=0; i < 2 *iters; i++) { //on fait deux fois plus de duos de points au cas où il y aurait des chemins impossibles plus tard
            	int rand1 =(int) (max_id * Math.random() + 1);
            	int rand2 =(int) (max_id * Math.random() + 1);
            	distanceWriter.newLine();
            	distanceWriter.write(rand1 + " " + rand2);
            	timeWriter.newLine();
            	timeWriter.write(rand1 + " " + rand2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                distanceWriter.close();
                timeWriter.close();
            } catch (Exception e) {
            }
        }
    }
	
	public void runTests(char tested_algo) {
		File Directory = new File("./tests");
		files = Directory.listFiles();
		String map;
		int type,i;
		int samples;
		int Ori,Dest;
		String[] nodePairs;
		
		BufferedReader buffreader = null;
		BufferedWriter bw1 = null;
		FileOutputStream fos1 = null;
		for(File f : files) {
			try{
				buffreader = new BufferedReader(new FileReader(f.getPath()));
				map = buffreader.readLine().toLowerCase();
				type = Integer.parseInt(buffreader.readLine());
				System.out.println("type : " + type);
				samples = Integer.parseInt(buffreader.readLine());
				i=0;
				
				System.out.println("creation des fichiers de resultats");
				String fpathR1 = f.getName().substring(0,f.getName().length()-4) + ".txt";
				File fR1 = new File("./ResTest/"+ tested_algo + fpathR1);
				fR1.createNewFile();
				fos1 = new FileOutputStream("./ResTest/"+ tested_algo + fpathR1);
				bw1 = new BufferedWriter(new OutputStreamWriter(fos1));
				String pathmap = "/home/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/"+map+".mapgr";
				GraphReader reader =new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathmap))));
				
				while(i<samples) {
					nodePairs = buffreader.readLine().split(" ");
					System.out.println(nodePairs[0] + " " + nodePairs[1]);
					Ori = Integer.parseInt(nodePairs[0]);
					Dest = Integer.parseInt(nodePairs[1]);
					ShortestPathData data = new ShortestPathData(graph,graph.getNodes().get(Ori),graph.getNodes().get(Dest),ArcInspectorFactory.getAllFilters().get(type*2));
					ShortestPathAlgorithm algo;
					
					switch(tested_algo) {
					case 'b' :
						algo = new BellmanFordAlgorithm(data);
						break;
					case 'd' :
						algo = new DijkstraAlgorithm(data);
						break;
					case 'a' :
						algo = new AStarAlgorithm(data);
						break;
					default :
						System.out.println("erreur d'arg : algos -> b, d ou a");
						throw new IOException();
					}
					long deb = System.currentTimeMillis();
					ShortestPathSolution sol = algo.doRun();
					long fin = System.currentTimeMillis();
					long duree = fin - deb;
					if(sol.isFeasible()) {
						bw1.write(sol.getPath().size() + " " + duree + " " + sol.getInputData().getVisitedNodes());
						bw1.newLine();
						i++;
					}
					else {}
				}
				
	        } catch (Exception e) {
	            e.printStackTrace();
	        }        try {
	            // Close regardless of what happens...
	            buffreader.close();
	            bw1.close();
	            fos1.close();
	            
	        } catch (Exception e) {
	    }
		}

			
	}
	
}