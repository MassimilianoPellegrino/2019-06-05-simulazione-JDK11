/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.District;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	txtResult.clear();
    	int anno;
    	
    	try {
    		anno = boxAnno.getValue();
    		model.creaGrafo(anno);
    		for(District d: model.getGrafo().vertexSet()) {
    			txtResult.appendText("Adiacenti a "+d+"\n");
    			for(DefaultWeightedEdge e: model.getAdiacenti(d))
    				txtResult.appendText(String.format("\t"+Graphs.getOppositeVertex(model.getGrafo(), e, d)+" distante %.2f Km\n", model.getGrafo().getEdgeWeight(e)));
    		}
    		
    	}catch(NullPointerException e) {
    		this.txtResult.appendText("Scegliere un anno");
    	}

    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	int anno;
    	int mese;
    	int giorno;
    	int n;
    	try {
    		anno = boxAnno.getValue();
    		mese = boxMese.getValue();
    		giorno = boxGiorno.getValue();
    		n = Integer.parseInt(this.txtN.getText());
    		
    		if(n<1 || n>10)
    			throw new NumberFormatException();
    		
    		int eventiMalgestiti = model.simula(anno, mese, giorno, giorno);
    		
    		this.txtResult.appendText("Simulazione completata; numero di eventi mal gestiti: "+eventiMalgestiti);
    	}catch(NullPointerException e) {
    		this.txtResult.appendText("Scegliere anno mese e giorno");
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero di agenti valido");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int i=2014; i<=2017; i++)
    		boxAnno.getItems().add(i);
    	for(int i=1; i<=12; i++)
    		boxMese.getItems().add(i);
    	for(int i=1; i<=31; i++)
    		boxGiorno.getItems().add(i);
    }
}
