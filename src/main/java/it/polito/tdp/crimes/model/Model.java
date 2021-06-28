package it.polito.tdp.crimes.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private Graph<District, DefaultWeightedEdge> grafo;
	private EventsDao dao = new EventsDao();
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		List<District> vertici = dao.getVertici(anno);
				
		for(District d1: vertici)
			for(District d2: vertici)
				if(!d1.equals(d2))
					Graphs.addEdgeWithVertices(grafo, d1, d2, LatLngTool.distance(d1.getCentre(), d2.getCentre(), LengthUnit.KILOMETER));
		
		/*for(DefaultWeightedEdge e: grafo.edgeSet())
			System.out.println(e+" "+grafo.getEdgeWeight(e));*/
		
	}
	
	public List<DefaultWeightedEdge> getAdiacenti(District district){
		List<DefaultWeightedEdge> result = new ArrayList<>();
		
		for(DefaultWeightedEdge e: grafo.incomingEdgesOf(district))
			result.add(e);
		
		result.sort(new Comparator<DefaultWeightedEdge>() {

			@Override
			public int compare(DefaultWeightedEdge o1, DefaultWeightedEdge o2) {
				// TODO Auto-generated method stub
				return (int) (grafo.getEdgeWeight(o1)-grafo.getEdgeWeight(o2));
			}
		});
		
		/*for(DefaultWeightedEdge e: result)
			System.out.println(grafo.getEdgeWeight(e));*/
		
		return result;
		
	}
	
	public int simula(int anno, int mese, int giorno, int n) {
		
		int result = 0;
		PriorityQueue<Event> queue = new PriorityQueue<Event>();
		List<Agente> agenti = new ArrayList<>();
		
		for(int i=1; i<=n; i++)
			agenti.add(new Agente(i, getPartenza(), true));
		
		queue.addAll(dao.listDailyEvents(anno, mese, giorno));
		
		Event e;
		
		while((e = queue.poll()) != null) {
			
			//System.out.println(e.getReported_date());
			
			for(Agente a: agenti) {
				if(!a.isFree()) {
					if(a.getEnd().compareTo(e.getReported_date())<=0) {
						//System.out.println(a.getEnd()+" "+e.getReported_date());
						a.setFree(true);
						a.setEnd(null);
					}
				}
			}
			
			int district = e.getDistrict_id();
			
			District distretto = null;
			Agente agente = null;
			
			for(District d: grafo.vertexSet())
				if(d.getDistrict_id() == district)
					distretto = d;
			
			for(Agente a: agenti)
				if(a.getLocation().equals(distretto))
					agente = a;
			
			if(agente == null) {
				for(DefaultWeightedEdge edge: getAdiacenti(distretto)) {
					for(Agente a: agenti) {
						if(Graphs.getOppositeVertex(grafo, edge, distretto).equals(a.getLocation())) {
							agente = a;
							break;
						}
					}
					if(agente != null)
						break;
				}
			}
			
			if(agente!=null) {
				
				double time = 0;
				
				if(agente.getLocation().getDistrict_id()!=e.getDistrict_id()) {
					time = grafo.getEdgeWeight(grafo.getEdge(agente.getLocation(), distretto));
					//System.out.println(time);
				}
				
				if(time>=15)
					result++;
				
				if(e.getOffense_category_id().equals("all-other-crimes")) {
					if(Math.random()<0.5)
						time+=60;
					else
						time+=120;
				}else
					time+=120;
				
				agente.setEnd(e.getReported_date().plusMinutes((long) time));
				agente.setLocation(distretto);
				agente.setFree(false);
			
			}
		}
		
		
		return result;
		
	}
	
	public District getPartenza() {
		
		int min = 1000000000;
		District dist = null;
		
		for(District d: grafo.vertexSet()) {
			if(d.getCriminalita()<min) {
				min = d.getCriminalita();
				dist = d;
			}
		}
		
		return dist;
	}

	public Graph<District, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	
	
}
