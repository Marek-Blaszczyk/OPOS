package amw.opos.maphandler.projections;

public interface Projection {
	XYPoint getXYPoint(GeoPoint geo_point, double scale);
	GeoPoint getGeoPoint(XYPoint xypoint, double scale);
}
