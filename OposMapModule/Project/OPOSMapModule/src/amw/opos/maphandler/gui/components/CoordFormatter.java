package amw.opos.maphandler.gui.components;

import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.swing.text.NumberFormatter;

public class CoordFormatter extends NumberFormatter {

	private static final long serialVersionUID = 1L;

	public CoordFormatter() {
		super();
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(12);
		this.setFormat(format);
	    this.setValueClass(Double.class);
	    this.setMinimum(0.00);
	    this.setMaximum(180.0);
	    this.setAllowsInvalid(true);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    this.setCommitsOnValidEdit(true);
	}

}
