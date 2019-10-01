package amw.opos.maphandler.projections;

public class GeoPoint {
	public double lat;
	public double lon;
	
	public GeoPoint(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "GeoPoint [lat=" + lat + ", lon=" + lon + "]";
	}
	
}
