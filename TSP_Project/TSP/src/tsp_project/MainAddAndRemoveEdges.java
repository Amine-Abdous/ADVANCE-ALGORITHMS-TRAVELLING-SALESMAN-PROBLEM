/**
 * 
 */
package tsp_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainAddAndRemoveEdges {
	private static Scanner sc;
	static int database = Constants.use_database;
	
	
	public static void main (String[] args) {
		/* Distances matrix initialization */
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
				Constants.dis_matrix[i][j] = -1;
			}
		}
		
		/* Setting the array of cities by giving each city a name */
		int nb = 0;
		for(int i=0; i < Constants.number_of_cities; i++) {
			String city_name = "C" + nb;
			nb++;
			Constants.cities[i] = new City(city_name);
		}
		
		if (database == 1) {
			List<Integer> rows = new ArrayList<Integer>();
			try {
				File f = new File(Constants.database);
				sc = new Scanner(f);
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] details = line.split("\\s+");
					for(String s: details) {
						rows.add(Integer.parseInt(s));
					}
				}
			
				for (int i=0; i < Constants.number_of_cities; i++) {
					for (int j=0; j < Constants.number_of_cities; j++) {
						Constants.dis_matrix[i][j] = rows.get(j + (i * Constants.number_of_cities));
					}
				}
			}
			catch (FileNotFoundException e) {
				System.out.println("The file was not found, random variables are gonna be used instead !");
				database = 0;
	            e.printStackTrace();
			}
//			System.out.println(Constants.number_of_cities);
//			for (int i=0; i < cities.size(); i++) {
//				System.out.println(cities.get(i).getName() + " " + cities.get(i).getX() + " " + cities.get(i).getY());
//			}
		}
		if (database != 1) {
			/* Setting the matrix of distances by giving random distances between cities */
			for (int i=0; i < Constants.number_of_cities; i++) {
				for (int j=0; j < Constants.number_of_cities; j++) {
					if (i == j) {
						continue;
					}
					else if (Constants.dis_matrix[i][j] != -1) {
						continue;
					}
					/* Symetric matrix of distances */
//					else {
//						Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
//						Constants.dis_matrix[j][i] = Constants.dis_matrix[i][j];
//					}
					/* Non symmetric matrix of distances */
					else {
						Constants.dis_matrix[i][j] = Constants.cities[i].distance(Constants.cities[j]);
					}
				}
			}
//			Constants.dis_matrix[0][1] = 3;
//			Constants.dis_matrix[0][2] = 4;
//			Constants.dis_matrix[0][3] = 2;
//			Constants.dis_matrix[0][4] = 7;
//			Constants.dis_matrix[1][2] = 4;
//			Constants.dis_matrix[1][3] = 6;
//			Constants.dis_matrix[1][4] = 3;
//			Constants.dis_matrix[2][3] = 5;
//			Constants.dis_matrix[2][4] = 8;
//			Constants.dis_matrix[3][4] = 6;
//	
//			Constants.dis_matrix[0][0] = -1;
//			Constants.dis_matrix[1][1] = -1;
//			Constants.dis_matrix[2][2] = -1;
//			Constants.dis_matrix[3][3] = -1;
//			Constants.dis_matrix[4][4] = -1;
//			
//			Constants.dis_matrix[1][0] = 3;
//			Constants.dis_matrix[2][0] = 4;
//			Constants.dis_matrix[3][0] = 2;
//			Constants.dis_matrix[4][0] = 7;
//			Constants.dis_matrix[2][1] = 4;
//			Constants.dis_matrix[3][1] = 6;
//			Constants.dis_matrix[4][1] = 3;
//			Constants.dis_matrix[3][2] = 5;
//			Constants.dis_matrix[4][2] = 8;
//			Constants.dis_matrix[4][3] = 6;
		}
		
		/* Printing the matrix of distances */
		System.out.println("Matrix representing the cities and the distances between them :");
		String str = "|\t";
		String str2 = "\t";
		for(int k=0; k < Constants.number_of_cities; k++) {
			str2 += " " + Constants.cities[k].getName() + "\t";
		}
		System.out.println(str2);
		
		for (int i=0; i < Constants.number_of_cities; i++) {
			for (int j=0; j < Constants.number_of_cities; j++) {
		        str += Constants.dis_matrix[i][j] + "\t";
			}
			System.out.print(Constants.cities[i].getName());
			System.out.println(str + "|");
	        str = "|\t";
		}
		System.out.println();
		
		/* Computing the candidate paths */
		Path path = new Path();
		path.cityPermutation(path.convertPathToString(Constants.cities));
		path.convertStringToPath(Constants.candidate_paths);
		
		/* Add and Remove Edges Algo */
		double debut = System.currentTimeMillis();
		System.out.println("Applying the \"Add and Remove Edges\" algorithm :");
		AddAndRemoveEdges c = new AddAndRemoveEdges();
		c.addAndRemoveEdges();
		System.out.println();
		System.out.println("Execution time : " + (System.currentTimeMillis() - debut) + " ms");
	}
}
