package amw.opos.maphandler.projections;

public class GMapsProjection implements Projection {

	public static int TILE_SIZE = 256;

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
	
	public static XYPoint createInfoWindowContent(GeoPoint latLng, float scale) {
      //double scale = Math.pow(2, zoom);
		double zoom = Math.log(scale)/Math.log(2);

      GeoPoint worldCoordinate = project(latLng);

      GeoPoint pixelCoordinate = new GeoPoint(
          Math.floor(worldCoordinate.lat * scale),
          Math.floor(worldCoordinate.lon * scale));

      GeoPoint tileCoordinate = new GeoPoint(
          Math.floor(worldCoordinate.lat * scale / TILE_SIZE),
          Math.floor(worldCoordinate.lon * scale / TILE_SIZE));

      System.out.println("LatLng: " + latLng);
      System.out.println("Zoom level: " + zoom + " scale:" + scale);
      System.out.println("World Coordinate: " + worldCoordinate);
      System.out.println("Pixel Coordinate: " + pixelCoordinate);
      System.out.println("Tile Coordinate: " + tileCoordinate+'\n');
      
      return new XYPoint((int)Math.floor(pixelCoordinate.lon), (int)Math.floor(pixelCoordinate.lat));
    }

    // The mapping between latitude, longitude and pixels is defined by the web
    // mercator projection.
    public static GeoPoint project(GeoPoint latLng) {
      double siny = Math.sin(latLng.lat * Math.PI / 180);

      // Truncating to 0.9999 effectively limits latitude to 89.189. This is
      // about a third of a tile past the edge of the world tile.
      siny = Math.min(Math.max(siny, -0.9999), 0.9999);

      return new GeoPoint(
          TILE_SIZE * (0.5 + latLng.lon / 360),
          TILE_SIZE * (0.5 - Math.log((1 + siny) / (1 - siny)) / (4 * Math.PI)));
    }
	
	public static void main(String[] args) {
		try {
			System.out.println("Start ...");
			
			float z = 14;
			float scale = 18520.0f;
			
			XYPoint chicago1 = createInfoWindowContent(new GeoPoint(41.85, -87.64999999999998),3.0f);
			XYPoint center1 = createInfoWindowContent(new GeoPoint(54.5327067,18.5430637), scale);
			XYPoint basen1 = createInfoWindowContent(new GeoPoint(54.528274, 18.529898), scale);

			System.out.println(" chicago coord:"+chicago1);
			System.out.println(" center coord:"+center1);
			System.out.println(" basen coord:"+basen1);
			System.out.println(" basen - center:"+(basen1.x-center1.x)+","+(basen1.y-center1.y));
			System.out.println(" spr: "+(1920/2-729)+","+(929/2-596));
			//729.746
						
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
