package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Agente {
	
	private int id;
	private District location;
	private boolean free;
	private LocalDateTime end;
	
	public Agente(int id, District location, boolean free) {
		super();
		this.id = id;
		this.location = location;
		this.free = free;
	}

	public District getLocation() {
		return location;
	}

	public void setLocation(District location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	
	
	
	

}
