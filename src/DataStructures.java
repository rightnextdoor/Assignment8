import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

public class DataStructures {
	
	public static void main(String[] args) {
		
		flightData("flights.txt");
		welcome();
		planner();
	}
	
	private static void flightData(String file) {
		city = new ArrayList<String>();
		flight = new HashMap<String,ArrayList<String>>();
		
		try {
			BufferedReader rd = new BufferedReader(new FileReader(file));
			while (true) {
				String line = rd.readLine();
				
				if (line == null) break;
				if (line.length() != 0) {
					
					int space = line.indexOf("->");
					String startCity = line.substring(0, space).trim();
					String endCity = line.substring(space + 2).trim();
					addCity(startCity);
					addCity(endCity);
					travel(startCity).add(endCity);
				}
			}
			rd.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
	
	private static void addCity(String name) {
		if (!city.contains(name)) {
			city.add(name);
			flight.put(name, new ArrayList<String>());
		}
	}
	
	public static void welcome() {
		System.out.println("Welcome to Flight Planner! \n"
							+ "Here's a list of all the cities in our database: \n"); 
							getCities(city);
		
	}
	
	public static void getCities(ArrayList<String> city) {
		for (int i = 0; i < city.size(); i++) {
			String cities = city.get(i);
			System.out.println(" " + cities);
		}
	}
	
	public static void planner() {
		ArrayList<String> trip = new ArrayList<String>();
		
		System.out.println("Let's plan a round-trip route! ");
		System.out.print("Enter the starting city: ");
		Scanner input = new Scanner(System.in);
		String startCity = input.nextLine();
		trip.add(startCity);
		String nextCity = startCity;
		
		while(true) {
			String city = getNextCity(nextCity);
			trip.add(city);
			if (city.equals(startCity)) break;
			nextCity = city;
		}
		
		printTrip(trip);
	}
	
	private static String getNextCity(String city) {
		ArrayList<String> travel = travel(city);
		String nextCity = null;
		while(true) {
			System.out.println("From " + city + " you can fly directly to:");
			getCities(travel);
			System.out.print("Where do you want to go from " + city + "? ");
			Scanner input = new Scanner(System.in);
			nextCity = input.nextLine();
			if (travel.contains(nextCity)) break;
			System.out.println("You can't get to that city by a direct flight.");
		}
		return nextCity;
	}
	
	private static void printTrip(ArrayList<String> trip) {
		System.out.println("The route you've chosen is: ");
		for (int i = 0; i < trip.size(); i++) {
			if (i > 0) 
				System.out.print(" -> ");
			System.out.print(trip.get(i));
		}
		System.out.println();
	}
	
	
	

	
	private static ArrayList<String> travel(String city){
		return flight.get(city);
	}
	
	private static ArrayList<String> city;
	private static HashMap<String,ArrayList<String>> flight;

}
