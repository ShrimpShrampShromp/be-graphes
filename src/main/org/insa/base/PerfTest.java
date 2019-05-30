package org.insa.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.BinaryPathReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;


public class PerfTest {
	
	private String nom_carte;
	
	private int iters;
	
	public PerfTest(String string, int iters){
		this.nom_carte = string.toString();
		this.iters = iters;
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
            distanceWriter.write(nom_carte + "\n0 \n" + iters);
            //là on génère tous les duos de points
            
            timeWriter = new BufferedWriter(new FileWriter(timeFile));
            timeWriter.write(nom_carte + "\n 1 \n" + iters);
            //et bam on remet les duos de points. on peut peut etre le faire en même temps?
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
}