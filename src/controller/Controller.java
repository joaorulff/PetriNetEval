package controller;



import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Marking;
import model.PetriNet;
import util.StringParser;

public class Controller {
	
	private PetriNet currentPetriNet;
	
	@FXML private VBox resultVbox;
	@FXML private HBox markingHbox;
	
	@FXML private TextField numberOfPlacesTextField;
	@FXML private TextField numberOfTransitionsTextField;
	
	
	@FXML private GridPane placesAndTransitionsGridPane;
	
	
	
	public void generatePetriNet() throws InterruptedException{
		
		for (int i = 0; i < Integer.parseInt(this.numberOfTransitionsTextField.getText()); i++) {
			
			Label inputLabel = new Label("I(" + (i+1) +"):");
			TextField InputTextField = new TextField();
			
			Label outputLabel = new Label("O(" + (i+1) +"):");
			TextField OutputTextField = new TextField();
			
			this.placesAndTransitionsGridPane.add(inputLabel, 0, i);
			this.placesAndTransitionsGridPane.add(InputTextField, 1, i);
			this.placesAndTransitionsGridPane.add(outputLabel, 2, i);
			this.placesAndTransitionsGridPane.add(OutputTextField, 3, i);
		}
			
			Button evaluateButton = new Button("Evaluate");
			evaluateButton.setOnAction(e -> evaluateButtonClicked());
			this.placesAndTransitionsGridPane.add(evaluateButton, 3 , Integer.parseInt(this.numberOfTransitionsTextField.getText())+1);
			
			Label initialMarkingLabel = new Label("Initial Marking");
			TextField initialMarkingTextField = new TextField();
			this.markingHbox.getChildren().addAll(initialMarkingLabel, initialMarkingTextField);
		
			this.currentPetriNet = new PetriNet(Integer.parseInt(this.numberOfTransitionsTextField.getText()), Integer.parseInt(this.numberOfPlacesTextField.getText()), this);
	}
	
	
	
	public void newEvaluation(){
		this.placesAndTransitionsGridPane.getChildren().clear();
		this.markingHbox.getChildren().clear();
		this.resultVbox.getChildren().clear();
		this.currentPetriNet = null;
	}
	
	public void evaluateButtonClicked(){
		
		for(int currentRow = 1; currentRow <= this.placesAndTransitionsGridPane.getChildren().size()/4; currentRow++){
			
			TextField currentInputTexfield = (TextField)(this.placesAndTransitionsGridPane.getChildren().get((currentRow*4)-3));
			TextField currentOutputTexfield = (TextField)(this.placesAndTransitionsGridPane.getChildren().get((currentRow*4)-1));
			
			if(StringParser.parseInputStringFormat(currentInputTexfield.getText(), Integer.parseInt(this.numberOfPlacesTextField.getText()))){
				
				this.currentPetriNet.initializeInputPetriNetMatrix(StringParser.parseInputString(currentInputTexfield.getText()
																, Integer.parseInt(this.numberOfPlacesTextField.getText()))
																, currentRow-1);
				
				this.currentPetriNet.initializeOutputPetriNetMatrix(StringParser.parseInputString(currentOutputTexfield.getText()
																, Integer.parseInt(this.numberOfPlacesTextField.getText()))
																, currentRow-1);
				
			}else{
				//POPUP WINDOW!
				System.out.println("NOT OK");
			}	
		}
		
		TextField initialMarking = (TextField)this.markingHbox.getChildren().get(1);
		
		if(StringParser.parseInputStringFormat(initialMarking.getText(), Integer.parseInt(this.numberOfPlacesTextField.getText()))){
			
			ArrayList<Integer> markingValues = StringParser.parseInputString(initialMarking.getText(), Integer.parseInt(this.numberOfPlacesTextField.getText()));
			Marking marking = new Marking(markingValues);
			try {
				//this.currentPetriNet.reachabilityTree(marking);
				this.currentPetriNet.coverabilityTree(marking);
				//this.currentPetriNet.printCoverability();
				this.displayResult(this.currentPetriNet.markingsForCoverability);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("MARKING NOT OK");
		}
		
	}
	
	
	public void displayResult(ArrayList<Marking> results){
		
		for (Marking marking : results) {
			String label = "< ";
			for (int i = 0; i < marking.getLength(); i++) {
				int temp = marking.getElementFromMarking(i);
				if(temp == 1000){
					label += " " + "w"+ " ";
				}else{
					label += " " + temp+ " ";
				}
				
			}
			label += " >";
			this.resultVbox.getChildren().add(new Label(label));
		}
		
	}

}
