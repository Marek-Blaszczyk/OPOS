package amw.opos.maphandler.gui.components;

import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.text.NumberFormatter;

public class IntegerFormatter extends NumberFormatter {

	public IntegerFormatter() {
		super();
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		this.setFormat(format);
	    this.setValueClass(Integer.class);
	    this.setMinimum(0);
	    this.setMaximum(Integer.MAX_VALUE);
	    this.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    this.setCommitsOnValidEdit(true);
	}
	

}
