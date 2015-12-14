package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.Controller;
import util.ArrayUtil;
import util.StringParser;



public class PetriNet {
	
	int inputTable [][];
	int outputTable [][];
	
	public ArrayList<Marking> markingsForReachability;
	public ArrayList<Marking> markingsForCoverability;
	
	public Controller controller;
	
	public PetriNet(int numberOfTransitions, int numberOfPlaces, Controller controller){
		
		this.markingsForReachability = new ArrayList<>();
		this.markingsForCoverability = new ArrayList<>();
		
		this.inputTable = new int [numberOfTransitions][numberOfPlaces]; 
		this.outputTable = new int [numberOfTransitions][numberOfPlaces];
		
		this.controller = controller;
		
		
	}
	
	public void printPetri(){
		for (int i = 0; i < inputTable.length; i++) {
			for (int j = 0; j < inputTable[i].length; j++) {
				System.out.print(inputTable[i][j]);
			}
			System.out.println();
		}
		
		for (int i = 0; i < outputTable.length; i++) {
			for (int j = 0; j < outputTable[i].length; j++) {
				System.out.print(outputTable[i][j]);
			}
			System.out.println();
		}
	}
	
	public void initializeInputPetriNetMatrix(ArrayList<Integer> inputs, int transitionNumber){
		
			for (int j = 0; j < inputTable[transitionNumber].length; j++) {
				inputTable[transitionNumber][j] = inputs.get(j);
			}
	}

	
	public void initializeOutputPetriNetMatrix(ArrayList<Integer> outputs, int transitionNumber){
			
			for (int j = 0; j < outputTable[transitionNumber].length; j++) {
				outputTable[transitionNumber][j] = outputs.get(j);
			}
	}
	
	public ArrayList<Integer> avaiableTransitions(Marking m){
		
		ArrayList<Integer> tokens = m.getMarking();
		ArrayList<Integer> avaiableTransitions = new ArrayList<>();
		
		for (int i = 0; i < inputTable.length; i++) {
			
			boolean fireable = true;
			for (int j = 0; j < inputTable[i].length; j++) {
				if(tokens.get(j) < inputTable[i][j]){
					fireable = false; 
					break;
				}	
			}
			
			if(fireable) avaiableTransitions.add(i);
		}
		return avaiableTransitions;
	}
	
	public Marking fireTransition(int transitionNumber, Marking m){
		
		if(m.getLength() != inputTable[0].length) {
			System.out.println("Invalid Marking");
			return null;
		}
		
		ArrayList<Integer> oldMarking = m.getMarking();
		ArrayList<Integer> newMarking = new ArrayList<>();
		
		for (int j = 0; j < inputTable[transitionNumber].length; j++) {
			newMarking.add(oldMarking.get(j) - inputTable[transitionNumber][j] + outputTable[transitionNumber][j]);
		}
		
		Marking newMarkingObject = new Marking(newMarking);
		
		return newMarkingObject;
	}
	
	public Marking fireTransitionForCoverability(int transitionNumber, Marking m){
		
		ArrayList<Integer> oldMarking = m.getMarking();
		ArrayList<Integer> newMarking = new ArrayList<>();
		
		for (int j = 0; j < inputTable[transitionNumber].length; j++) {
			if(oldMarking.get(j) >= 1000){
				newMarking.add(oldMarking.get(j));
			}else{
				newMarking.add(oldMarking.get(j) - inputTable[transitionNumber][j] + outputTable[transitionNumber][j]);
			}
			
		}
		
		Marking newMarkingObject = new Marking(newMarking);
		
		return newMarkingObject;
	}
	
	public void reachabilityTree (Marking root) throws InterruptedException{
		
		if(ArrayUtil.contains(markingsForReachability, root)){
			System.out.println("Old");
			return;
		}else{
			markingsForReachability.add(root);
		}
		
		ArrayList<Integer> avaiableTransitions = this.avaiableTransitions(root);
		root.printMarking();
		
		if(avaiableTransitions.size() == 0){
			System.out.println("Dead end");
			return;
		}else{
			for (Integer transition : avaiableTransitions) {
				
				System.out.println("Transition " + (transition+1)+ " fires");
				Marking newMarking = fireTransition(transition, root);
				Thread.sleep(100);
				reachabilityTree(newMarking);
			}
		}
	}
	
	public void printCoverability(){
		System.out.println("--------------------------------");
		for (Marking marking : this.markingsForCoverability) {
			marking.printMarking();
		}
		System.out.println("--------------------------------");
	}
	
	public void coverabilityTree (Marking root) throws InterruptedException{
		//Thread.sleep(100);
		System.out.println("------------------------------------");
		System.out.println("ROOT");
		root.printMarking();
		System.out.println("------------------------------------");
		System.out.println("STACK OF MARKINGS");
		for (Marking marking : markingsForCoverability) {
			marking.printMarking();
		}
		System.out.println("----------------END--------------------");
		if(ArrayUtil.contains(markingsForCoverability, root)){
			System.out.println("Old");
			return;
		}else{
			this.markingsForCoverability.add(root);
			if(ArrayUtil.containsForCoverability(this.markingsForCoverability, root)){
				
				//this.markingsForCoverability.add(root);
				ArrayList<Integer> avaiableTransitions = this.avaiableTransitions(root);
				root.printMarking();
				
				if(avaiableTransitions.size() == 0){
					System.out.println("Dead end");
					return;
				}else{
					
					for (Integer transition : avaiableTransitions) {
						System.out.println("Transition " + (transition+1)+ " fires");
						Marking newMarking = fireTransitionForCoverability(transition, root);
						coverabilityTree(newMarking);
					}
					
				}
				
			}else{
				
				//this.markingsForCoverability.add(root);
				ArrayList<Integer> avaiableTransitions = this.avaiableTransitions(root);
				root.printMarking();
				
				if(avaiableTransitions.size() == 0){
					System.out.println("Dead end");
					return;
				}else{
					
					for (Integer transition : avaiableTransitions) {
						System.out.println("Transition " + (transition+1)+ " fires");
						Marking newMarking = fireTransitionForCoverability(transition, root);
						coverabilityTree(newMarking);
					}
					
				}
			}
		}
	}
}

	
