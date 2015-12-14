package model;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Marking {
	
	private String tag;
	private String label;
	private ArrayList<Integer> marking;
	private int length;
	
	public Marking(ArrayList<Integer> marking){
		
		this.length = marking.size();
		this.marking = new ArrayList<>();
		
		
		for (Integer integer : marking) {
			this.marking.add(integer);
		}
	}

	public void printMarking(){
		
		for (Integer token : marking) {
			System.out.print(token+"\t");
		}
		System.out.println();

	}
	
	public void printMarkingOmega(){
		
		for (Integer token : marking) {
			if(token == 100000){
				System.out.print("w\t");
			}else{
				System.out.print(token+"\t");
			}
			
		}
		System.out.println();

	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ArrayList<Integer> getMarking() {
		return marking;
	}

	public void setMarking(ArrayList<Integer> marking) {
		this.marking = marking;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public int getMarkingPosition(int i){
		return this.marking.get(i);
	}
	
	public boolean compare(Marking m){
		
		/*System.out.println("COMPARING: ");
		this.printMarking();
		System.out.println("WITH");
		m.printMarking();*/
		
		ArrayList<Integer> mArray = m.getMarking();
		
		for (int i = 0; i < this.marking.size(); i++) {
			//System.out.println(this.marking.get(i) + " ==  " + mArray.get(i) + "   " +(this.marking.get(i).equals(mArray.get(i))));
			if(!(this.marking.get(i).equals(mArray.get(i)))){
				
				return false;
			}
		}
	
		return true;
	}
	
	public boolean equalOrGreater(Marking m){
		//check if the parameter is greater
		ArrayList<Integer> mArray = m.getMarking();
		
		
		for (int i = 0; i < this.marking.size(); i++) {
			if(mArray.get(i)  < this.marking.get(i)){
				return false;
			}
		}
		
		for (int i = 0; i < this.marking.size(); i++) {
			if(mArray.get(i) >this.marking.get(i) ){
				mArray.set(i, 1000);
			}
		}
		m.setMarking(mArray);
		return true;
	} 
	
	public int getElementFromMarking(int i){
		return this.marking.get(i);
	}

}
