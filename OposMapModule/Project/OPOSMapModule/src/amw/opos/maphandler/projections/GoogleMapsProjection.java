package amw.opos.maphandler.projections;

import amw.opos.maphandler.MapFileDsc;
import amw.opos.maphandler.MapFileDsc.XYCoord;

public class GoogleMapsProjection implements Projection {

	//(half of the earth circumference's in pixels at zoom level 21)
	static double offset = 268435456; 
	static double radius = offset / Math.PI;

	@Override
	public XYPoint getXYPoint(GeoPoint geo_point, double scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeoPoint getGeoPoint(XYPoint xypoint, double scale) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// X,Y ... location in degrees
	// xcenter,ycenter ... center of the map in degrees (same value as in 
	// the google static maps URL)
	// zoomlevel (same value as in the google static maps URL)
	// xr, yr and the returned Point ... position of X,Y in pixels relativ 
	// to the center of the bitmap
	static XYPoint Adjust(double X, double Y, double xcenter, double ycenter, 
	                    int zoomlevel)
	{
	    int xr = (LToX(X) - LToX(xcenter)) >> (21 - zoomlevel);
	    int yr = (LToY(Y) - LToY(ycenter)) >> (21 - zoomlevel);
	    XYPoint p = new XYPoint(xr, yr);
	    return p;
	}

	static int LToX(double x)
	{
		int x_pos = (int)(Math.round(offset + radius * x * Math.PI / 180));
		System.out.println("x:"+x_pos);
	    return x_pos;
	}

	static int LToY(double y)
	{
		int y_pos = (int)(Math.round(offset - radius * Math.log((1 + 
                Math.sin(y * Math.PI / 180)) / (1 - Math.sin(y * 
                Math.PI / 180))) / 2));

		System.out.println("y:"+y_pos);
		
	    return y_pos;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("Start ...");
			
			MapFileDsc map = new MapFileDsc();
			double z = 14;
			

			XYCoord chicago1 = map.createInfoWindowContent(41.85, -87.64999999999998,3);
			XYCoord center1 = map.createInfoWindowContent(54.5327067,18.5430637, z);
			XYCoord basen1 = map.createInfoWindowContent(54.528274, 18.529898, z);

			System.out.println(" chicago coord:"+chicago1.x+","+chicago1.y);
			//System.out.println(" center coord:"+center1.x+","+center1.y);
			//System.out.println(" basen coord:"+basen1.x+","+basen1.y);
			//System.out.println(" basen - center:"+(basen1.x-center1.x)+","+(basen1.y-center1.y));
			
			GeoPoint center = new GeoPoint(54.5221473, 18.5442653);
			GeoPoint basen = new GeoPoint(54.528311, 18.529898);
			GeoPoint chicago = new GeoPoint(41.85, -87.64999999999998);
			int zoom_level = 14;
			
			//XYPoint xybasen = GoogleMapsProjection.Adjust(basen.lon, basen.lat, center.lon, center.lat, zoom_level);
			//System.out.println(xybasen);
			
			XYPoint xychicago = GoogleMapsProjection.Adjust(chicago.lon, chicago.lat, center.lon, center.lat, 3);
			System.out.println(xychicago);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
