package amw.opos.maphandler.gui.components;

import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class BoundsEditor extends JPanel {
	
	public final String boundsPattern = "\\d+,\\d+,\\d+,\\d+";
	private int top, left, down, right;
	
	private JFormattedTextField formattedTextFieldTop;
	private JFormattedTextField formattedTextFieldLeft;
	private JFormattedTextField formattedTextFieldDown;
	private JFormattedTextField formattedTextFieldRight;

	/**
	 * Create the panel.
	 */
	public BoundsEditor() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		formattedTextFieldTop = new JFormattedTextField(new IntegerFormatter());
		formattedTextFieldTop.setText("0");
		formattedTextFieldTop.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldTop.setColumns(1);
		GridBagConstraints gbc_formattedTextFieldTop = new GridBagConstraints();
		gbc_formattedTextFieldTop.weighty = 1.0;
		gbc_formattedTextFieldTop.fill = GridBagConstraints.BOTH;
		gbc_formattedTextFieldTop.weightx = 1.0;
		gbc_formattedTextFieldTop.insets = new Insets(0, 0, 0, 0);
		gbc_formattedTextFieldTop.gridx = 0;
		gbc_formattedTextFieldTop.gridy = 0;
		add(formattedTextFieldTop, gbc_formattedTextFieldTop);
		
		JLabel label = new JLabel(",");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.SOUTHWEST;
		gbc_label.insets = new Insets(0, 0, 0, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		formattedTextFieldLeft = new JFormattedTextField(new IntegerFormatter());
		formattedTextFieldLeft.setText("0");
		formattedTextFieldLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldLeft.setColumns(1);
		GridBagConstraints gbc_formattedTextFieldLeft = new GridBagConstraints();
		gbc_formattedTextFieldLeft.fill = GridBagConstraints.BOTH;
		gbc_formattedTextFieldLeft.weighty = 1.0;
		gbc_formattedTextFieldLeft.weightx = 1.0;
		gbc_formattedTextFieldLeft.insets = new Insets(0, 0, 0, 0);
		gbc_formattedTextFieldLeft.gridx = 2;
		gbc_formattedTextFieldLeft.gridy = 0;
		add(formattedTextFieldLeft, gbc_formattedTextFieldLeft);
		
		JLabel label_1 = new JLabel(",");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_label_1.insets = new Insets(0, 0, 0, 0);
		gbc_label_1.gridx = 3;
		gbc_label_1.gridy = 0;
		add(label_1, gbc_label_1);
		
		formattedTextFieldDown = new JFormattedTextField(new IntegerFormatter());
		formattedTextFieldDown.setText("0");
		formattedTextFieldDown.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldDown.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldDown = new GridBagConstraints();
		gbc_formattedTextFieldDown.fill = GridBagConstraints.BOTH;
		gbc_formattedTextFieldDown.weighty = 1.0;
		gbc_formattedTextFieldDown.weightx = 1.0;
		gbc_formattedTextFieldDown.insets = new Insets(0, 0, 0, 0);
		gbc_formattedTextFieldDown.gridx = 4;
		gbc_formattedTextFieldDown.gridy = 0;
		add(formattedTextFieldDown, gbc_formattedTextFieldDown);
		
		JLabel label_2 = new JLabel(",");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.SOUTHWEST;
		gbc_label_2.insets = new Insets(0, 0, 0, 0);
		gbc_label_2.gridx = 5;
		gbc_label_2.gridy = 0;
		add(label_2, gbc_label_2);
		
		formattedTextFieldRight = new JFormattedTextField(new IntegerFormatter());
		formattedTextFieldRight.setText("0");
		formattedTextFieldRight.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldRight.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldRight = new GridBagConstraints();
		gbc_formattedTextFieldRight.insets = new Insets(0, 0, 0, 0);
		gbc_formattedTextFieldRight.fill = GridBagConstraints.BOTH;
		gbc_formattedTextFieldRight.weighty = 1.0;
		gbc_formattedTextFieldRight.weightx = 1.0;
		gbc_formattedTextFieldRight.gridx = 6;
		gbc_formattedTextFieldRight.gridy = 0;
		add(formattedTextFieldRight, gbc_formattedTextFieldRight);

	}
	
	protected JFormattedTextField getFormattedTextFieldTop() {
		return formattedTextFieldTop;
	}
	protected JFormattedTextField getFormattedTextFieldLeft() {
		return formattedTextFieldLeft;
	}
	protected JFormattedTextField getFormattedTextFieldDown() {
		return formattedTextFieldDown;
	}
	protected JFormattedTextField getFormattedTextFieldRight() {
		return formattedTextFieldRight;
	}
	

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setText(String text) {
		if (text.matches(boundsPattern)){
			int start = 0;
			int delim = text.indexOf(",");
			top = Integer.parseInt(text.substring(0, delim));
			start = delim+1;
			delim = text.indexOf(",", start);
			left = Integer.parseInt(text.substring(start, delim));
			start = delim+1;
			delim = text.indexOf(",", start);
			down = Integer.parseInt(text.substring(start, delim));
			start = delim+1;
			right = Integer.parseInt(text.substring(start));
	
			getFormattedTextFieldTop().setText(""+top);
			getFormattedTextFieldLeft().setText(""+left);
			getFormattedTextFieldDown().setText(""+down);
			getFormattedTextFieldRight().setText(""+right);
		}
	}

	public String getText() {
		return getFormattedTextFieldTop().getText()+','+
		getFormattedTextFieldLeft().getText()+','+
		getFormattedTextFieldDown().getText()+','+
		getFormattedTextFieldRight().getText();
	}

	public void setValues(int[] offset) {
		getFormattedTextFieldTop().setText(""+offset[0]);
		getFormattedTextFieldLeft().setText(""+offset[1]);
		getFormattedTextFieldDown().setText(""+offset[2]);
		getFormattedTextFieldRight().setText(""+offset[3]);
		
		IntegerFormatter f = (IntegerFormatter)getFormattedTextFieldTop().getFormatter();
		f.setMaximum(offset[2]);
		f = (IntegerFormatter)getFormattedTextFieldDown().getFormatter();
		f.setMaximum(offset[2]);
		
		f = (IntegerFormatter)getFormattedTextFieldLeft().getFormatter();
		f.setMaximum(offset[3]);
		f = (IntegerFormatter)getFormattedTextFieldRight().getFormatter();
		f.setMaximum(offset[3]);
	}
	
	public int[] getValues() {
		top = ((Integer)getFormattedTextFieldTop().getValue()).intValue();
		left = ((Integer)getFormattedTextFieldLeft().getValue()).intValue();
		down = ((Integer)getFormattedTextFieldDown().getValue()).intValue();
		right = ((Integer)getFormattedTextFieldRight().getValue()).intValue();
		
		int[] values = {top, left, down, right};
		return values;
	}
}
