package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class District {
	
	private int district_id;
	private LatLng centre;
	private int criminalita;
	
	public District(int district_id, LatLng centre, int criminalita) {
		super();
		this.district_id = district_id;
		this.centre = centre;
		this.criminalita = criminalita;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public LatLng getCentre() {
		return centre;
	}
	
	public int getCriminalita() {
		return criminalita;
	}
	@Override
	public String toString() {
		return district_id+"";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + district_id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (district_id != other.district_id)
			return false;
		return true;
	}
	
	
	

}
