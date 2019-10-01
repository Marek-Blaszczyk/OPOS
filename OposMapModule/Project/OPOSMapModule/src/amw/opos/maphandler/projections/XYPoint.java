package amw.opos.maphandler.projections;

public class XYPoint {
	public int x;
	public int y;
	public XYPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "XYPoint [x=" + x + ", y=" + y + "]";
	}

	
}
