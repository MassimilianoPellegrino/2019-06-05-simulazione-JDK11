package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo(2014);
		model.simula(2015, 2, 25, 5);
		
		
		/*for(District d: model.getGrafo().vertexSet()) {
			model.getAdiacenti(d);
			break;
		}*/
			
	}

}
