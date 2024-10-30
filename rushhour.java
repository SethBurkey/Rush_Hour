import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;

public class rushhour {
	/**
	 * @param args
	 * This program reads the input from a file and saves each car/truck's data in a hashmap of custom Vehicle Objects
	 * Then it builds a 6x6 board using a 36 character string assigning each vehicle a unique letter of the alphabet 
	 * Next it enqueues the starting board to the queue and starts a BFS while loop. While the queue is not empty
	 * and the red car has not reached the exit, find and enqueue adjacencies. The adjacencies were the resulting
	 * board of any possible move. Additionally, I stored each adjacency and the move in hashmaps using the
	 * current board as the key for both and the move and parent board as the values. Lastly, it uses a while loop
	 * to print the result. While the current board did not equal the starting board, add it to a stack and get the 
	 * move and parent board. Each iteration increments a move counter. Lastly, we printed the moves counter and 
	 * each move in reverse order off the stack.
	 */
	public static void main(String[] args){
		try {
			File file = new File("input.txt");
			Scanner sc = new Scanner(file);
			int numOfVehicles = Integer.parseInt(sc.nextLine());
			HashMap<Character, Vehicle> vehicles = new HashMap<>();
			for(int i = 0; i < numOfVehicles; ++i){
				boolean car = false;
				if(sc.nextLine().equals("car")){
					car = true;
				}
				String color = sc.nextLine();
				boolean horizontal = false;
				if(sc.nextLine().equals("h")){
					horizontal = true;
				}
				int row = Integer.parseInt(sc.nextLine());
				int col = Integer.parseInt(sc.nextLine());
				char designator = 'A';
				designator += i;
				Vehicle vehicle = new Vehicle(car, color, horizontal, row, col);
				vehicles.put(designator, vehicle);
			}
			sc.close();
			StringBuilder strBoard = new StringBuilder("-".repeat(36));
			char c = 'A';
			for (Map.Entry<Character,Vehicle> vehicle : vehicles.entrySet()) {
				int index1 = vehicle.getValue().pos;
				strBoard.setCharAt(index1, c);
				if(vehicle.getValue().car){
					if(vehicle.getValue().horizontal){
						int index2 = index1 + 1;
						strBoard.setCharAt(index2, c);
					}
					else {
						int index2 = index1 + 6;
						strBoard.setCharAt(index2, c);
					}
				}
				else {
					if(vehicle.getValue().horizontal){
						int index2 = index1 + 1;
						int index3 = index2 + 1;
						strBoard.setCharAt(index2, c);
						strBoard.setCharAt(index3, c);
					}
					else {
						int index2 = index1 + 6;
						int index3 = index2 + 6;
						strBoard.setCharAt(index2, c);
						strBoard.setCharAt(index3, c);
					}
				}
				c++;
			}
			HashMap<String, String> parent = new HashMap<>();
			HashMap<String, String> moves = new HashMap<>();
			Queue<String> q = new LinkedList<>();
			q.add(strBoard.toString());
			StringBuilder currBoard = strBoard;
			while(!q.isEmpty()){
				currBoard = new StringBuilder(q.remove()); //dequeue
				if (currBoard.charAt(17) == 'A'){ //check if done
					break;
				}
				HashMap<Character, Boolean> vfound = new HashMap<>(); //recalculate the vehicle positions
				for(int i = 0; i < currBoard.length(); ++i){
					if(currBoard.charAt(i) != '-'){
						if(!vfound.containsKey(currBoard.charAt(i))){
								vfound.put(currBoard.charAt(i), true);
								vehicles.get(currBoard.charAt(i)).pos = i;
						}
					}
				}
				for (Map.Entry<Character, Vehicle> vehicle : vehicles.entrySet()){ //check adjacencies
					int pos = vehicle.getValue().pos;
					if(vehicle.getValue().car){
						if(vehicle.getValue().horizontal){ //horizontal car
							int tempPos = pos;
							int dist = 1;
							while(tempPos%6 > 0){ //left adjacencies
								if(currBoard.charAt(tempPos-1) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos, tempPos - 1);
									swap(tempBoard, pos + 1, tempPos);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " car moved " + dist + " to the left.";
										moves.put(tempBoard.toString(), move);
									}
									--tempPos;
									++dist;
								}
								else{
									break;
								}
							}
							tempPos = pos;
							dist = 1;
							while((tempPos+2)%6 > 0){ //right adjacencies
								if(currBoard.charAt(tempPos+2) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos + 1 , tempPos + 2);
									swap(tempBoard, pos, tempPos + 1);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " car moved " + dist + " to the right.";
										moves.put(tempBoard.toString(), move);
									}
									++tempPos;
									++dist;
								}
								else{
									break;
								}
							}
						}
						else{ //vertical car
							int tempPos = pos;
							int dist = 1;
							while(tempPos > 5){ //above adjacencies
								if(currBoard.charAt(tempPos-6) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos, tempPos - 6);
									swap(tempBoard, pos + 6, tempPos);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " car moved " + dist + " upward.";
										moves.put(tempBoard.toString(), move);
									}
									tempPos -= 6;
									++dist;
								}
								else{
									break;
								}
							}
							tempPos = pos;
							dist = 1;
							while(tempPos < 24){ //below adjacencies
								if(currBoard.charAt(tempPos+12) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos + 6, tempPos + 12);
									swap(tempBoard, pos, tempPos + 6);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " car moved " + dist + " downward.";
										moves.put(tempBoard.toString(), move);
									}
									tempPos += 6;
									++dist;
								}
								else{
									break;
								}
							}
						}
					} 
					else {
						if(vehicle.getValue().horizontal){ //horizontal truck
							int tempPos = pos;
							int dist = 1;
							while(tempPos%6 > 0){ //left adjacencies
								if(currBoard.charAt(tempPos-1) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos, tempPos - 1);
									swap(tempBoard, pos + 1, tempPos);
									swap(tempBoard, pos + 2, tempPos + 1);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " truck moved " + dist + " to the left.";
										moves.put(tempBoard.toString(), move);
									}
									--tempPos;
									++dist;
								}
								else{
									break;
								}
							}
							tempPos = pos;
							dist = 1;
							while((tempPos+3)%6 > 0){ //right adjacencies
								if(currBoard.charAt(tempPos+3) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos + 2, tempPos + 3);
									swap(tempBoard, pos + 1, tempPos + 2);
									swap(tempBoard, pos, tempPos + 1);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " truck moved " + dist + " to the right.";
										moves.put(tempBoard.toString(), move);
									}
									++tempPos;
									++dist;
								}
								else{
									break;
								}
							}
						}
						else{ // vertical truck
							int tempPos = pos;
							int dist = 1;
							while(tempPos > 5){ //above adjacencies
								if(currBoard.charAt(tempPos-6) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos, tempPos - 6);
									swap(tempBoard, pos + 6, tempPos);
									swap(tempBoard, pos + 12, tempPos + 6);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " truck moved " + dist + " upward.";
										moves.put(tempBoard.toString(), move);
									}
									tempPos -= 6;
									++dist;
								}
								else{
									break;
								}
							}
							tempPos = pos;
							dist = 1;
							while(tempPos < 18){ //below adjacencies
								if(currBoard.charAt(tempPos+18) == '-'){
									StringBuilder tempBoard = new StringBuilder(currBoard);
									swap(tempBoard, pos + 12, tempPos + 18);
									swap(tempBoard, pos + 6, tempPos + 12);
									swap(tempBoard, pos, tempPos + 6);
									if(!parent.containsKey(tempBoard.toString())){
										q.add(tempBoard.toString());
										parent.put(tempBoard.toString(), currBoard.toString());
										String move = vehicle.getValue().color + " truck moved " + dist + " downward.";
										moves.put(tempBoard.toString(), move);
									}
									tempPos += 6;
									++dist;
								}
								else{
									break;
								}
							}
						}
					}
				}
			}
			int numOfMoves = 0;
			Stack<String> output = new Stack<String>();
			while(!currBoard.toString().equals(strBoard.toString())){
				output.add(moves.get(currBoard.toString()));
				currBoard = new StringBuilder(parent.get(currBoard.toString()));
				++numOfMoves;
			}
			if(numOfMoves != 1){
				System.out.println(numOfMoves + " moves:");
			}
			else{
				System.out.println("1 move:");
			}
			for(int i = 0; i < numOfMoves; ++i){
				System.out.println(output.pop());
			}
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	static void swap(StringBuilder str, int index1, int index2){
		char temp = str.charAt(index1);
		str.setCharAt(index1, str.charAt(index2));
		str.setCharAt(index2, temp);
	}
}