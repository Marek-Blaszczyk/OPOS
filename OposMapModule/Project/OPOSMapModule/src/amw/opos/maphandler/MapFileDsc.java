package amw.opos.maphandler;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MapFileDsc {
	public class LatLng{
		
		public LatLng(double lat, double lon) {
			super();
			this.lat = lat;
			this.lon = lon;
		}
		
		double lat;
		double lon;
	}
	public class XYCoord{
		public XYCoord( long x, long y){
			this( (double)x,(double) y);
		}
		public XYCoord( double x, double y){
			this.x = x;
			this.y = y;
		}
		public double x;
		public double y;
	}
	
	public static final String FILE_TYPE_JPEG = "JPG";
	public static final String FILE_TYPE_PNG = "PNG";

	
	private String filename;
	private long id= 0L;
	private String file_type;
	private String region;
	private MapTypeEnum map_type;
	private int[] offset = {0,0,0,0};
	private double[] Center_coord = null;
	private double[] fi_coord = null;
	private double[] la_coord = null;
	
	private float zoom = -1f;
	private String map_date;
	private String valid_date;
		
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
		id = filename.hashCode();
	}
	
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	public MapTypeEnum getMap_type() {
		return map_type;
	}
	public void setMap_type(MapTypeEnum map_type) {
		this.map_type = map_type;
	}
	public int[] getOffset() {
		return offset;
	}
	public void setOffset(int[] offset) {
		this.offset = offset;
	}
	public double[] getFi_coord() {
		return fi_coord;
	}
	public void setFi_coord(double[] coord) {
		fi_coord = coord;
	}
	public double[] getLa_coord() {
		return la_coord;
	}
	public void setLa_coord(double[] coord) {
		la_coord = coord;
	}
	public double[] getCenter_coord() {
		return Center_coord;
	}
	public void setCenter_coord(double[] center_coord) {
		Center_coord = center_coord;
	}
	public float getZoom() {
		return zoom;
	}
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	public String getMap_date() {
		return map_date;
	}
	public void setMap_date(String map_date) {
		this.map_date = map_date;
	}
	public String getValid_date() {
		return valid_date;
	}
	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	
	public long getId() {
		return id;
	}
	private void setId(long id) {
		this.id = id;
	}
	private void resetId() {
		if ((getFilename()!=null) && (getFilename().length()<2))
			setId(getFilename().hashCode());
		else
			setId(0L);
	}
	public void printString() {
		System.out.println("Filename:"+getFilename());
		System.out.println("file_type:"+getFile_type());
		
	}
	
	public String printOffset() {
		return getOffset()[0]+","+getOffset()[1]+","+getOffset()[2]+","+getOffset()[3];
	}
	
	public static String printCoord(float[] coord) {
		if (coord != null)
			return '('+Float.toString(coord[0])+','+Float.toString(coord[1])+')';
		return "null";
	}
	
	public String toString() {
		return getFilename()+" ("+getRegion()+")";
	}
	
	public static long TILE_SIZE = 256;
	public XYCoord createInfoWindowContent(double lat, double lon, double zoom) {
		
		//18525.14847025366;//
        double scale = Math.pow(2, zoom); // scale = 1 << zoom;
        System.out.println("Scale (10.23:"+Math.pow(2, 10.23));
        System.out.println("Scale (14:"+Math.pow(2, 14));

        XYCoord worldCoordinate = project(new LatLng(lat, lon));

        XYCoord pixelCoordinate = new XYCoord(
            Math.floor(worldCoordinate.x * scale),
            Math.floor(worldCoordinate.y * scale));

        XYCoord tileCoordinate = new XYCoord(
            Math.floor(worldCoordinate.x * scale / TILE_SIZE),
            Math.floor(worldCoordinate.y * scale / TILE_SIZE));

        System.out.println("New coocrdinates:\n LatLng: " + lat+","+lon+
          "\nZoom level: " + zoom+
          "\nWorld Coordinate: " + worldCoordinate.x+","+worldCoordinate.y+
          "\nPixel Coordinate: " + pixelCoordinate.x+","+pixelCoordinate.y+
          "\nPixel Coordinate2: " + worldCoordinate.x*18525.14847025366+","+worldCoordinate.y*18525.14847025366+
          "\nTile Coordinate: " + tileCoordinate.x+","+tileCoordinate.y);
        
        return pixelCoordinate;
      }

      // The mapping between latitude, longitude and pixels is defined by the web
      // mercator projection.
      public XYCoord project(LatLng latLng) {
        double siny = Math.sin(latLng.lat * Math.PI / 180);

        // Truncating to 0.9999 effectively limits latitude to 89.189. This is
        // about a third of a tile past the edge of the world tile.
        siny = Math.min(Math.max(siny, -0.9999), 0.9999);

        return new XYCoord(
            TILE_SIZE * (0.5 + latLng.lon / 360),
            TILE_SIZE * (0.5 - Math.log((1 + siny) / (1 - siny)) / (4 * Math.PI)));
      }
	
	public static void main(String[] args) {
		// Center
		//	18.5442653
		//	54.5221473
		// 3000, 2000
		
		// Basen
		//	18525.14847025366
		//	54.528311, 
		//	18.529898
		//	3930,2266
		

/*Chicago, IL
LatLng: (41.85, -87.64999999999998)
Zoom level: 3
World Coordinate: (65.67111111111113, 95.17492654697409)
Pixel Coordinate: (525, 761)
Tile Coordinate: (2, 2)
		*/
		
		MapFileDsc map = new MapFileDsc();
		double z = 14;
		XYCoord chicago = map.createInfoWindowContent(41.85, -87.64999999999998,3);

		XYCoord center = map.createInfoWindowContent(54.5327067,18.5430637, z);
		XYCoord basen = map.createInfoWindowContent(54.528274, 18.529898, z);
		
		System.out.println(" center coord:"+center.x+","+center.y);
		System.out.println(" basen coord:"+basen.x+","+basen.y);
		System.out.println(" basen - center:"+(basen.x-center.x)+","+(basen.y-center.y));
		System.out.println(" spr basen: 3930 2266 delta:"+(723-basen.x)+","+(558-basen.y));
	}

}
