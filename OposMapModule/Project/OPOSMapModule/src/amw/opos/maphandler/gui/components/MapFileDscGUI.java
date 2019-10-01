package amw.opos.maphandler.gui.components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.awt.Component;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;

import amw.opos.maphandler.MapFileDsc;
import amw.opos.maphandler.MapTypeEnum;

import java.awt.Color;

@SuppressWarnings("serial")
public class MapFileDscGUI extends JPanel {
	private JTextField textFieldRegion;
	private JTextField textFieldOffset;
	private JTextField textFieldZoom;
	private JTextField textFieldDate;
	
	private MapFileDsc mapFileDsc;
	private JButton btnFilename;
	private JComboBox comboBoxFileType;
	private JComboBox comboBoxMapType;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
	private BoundsEditor boundsEditor;
	private JLabel lblCenterLa;
	private JFormattedTextField formattedTextFieldCenterFi;
	private JFormattedTextField formattedTextFieldCenterLa;
	private JFormattedTextField formattedTextFieldFiMin;
	private JFormattedTextField formattedTextFieldFiMax;
	private JFormattedTextField formattedTextFieldLaMin;
	private JFormattedTextField formattedTextFieldLaMax;
	private JLabel labelLaMin;
	private JLabel lblLaMax;
	
	

	public MapFileDsc getMapFileDsc() {
		return mapFileDsc;
	}

	public void setMapFileDsc(MapFileDsc mapFileDsc) {
		this.mapFileDsc = mapFileDsc;
		getBtnFilename().setText(mapFileDsc.getFilename());
		getComboBoxFileType().setSelectedItem(mapFileDsc.getFile_type());
		getBoundsEditor().setText(mapFileDsc.printOffset());
		textFieldRegion.setText(mapFileDsc.getRegion());
		getComboBoxMapType().setSelectedItem(mapFileDsc.getMap_type());
		textFieldOffset.setText(mapFileDsc.printOffset());
		textFieldZoom.setText(Float.toString(mapFileDsc.getZoom()));
		
		if (mapFileDsc.getCenter_coord() != null) {
			formattedTextFieldCenterFi.setValue(mapFileDsc.getCenter_coord()[0]);
			formattedTextFieldCenterLa.setValue(mapFileDsc.getCenter_coord()[1]);
		}else {
			CoordFormatter f = (CoordFormatter)formattedTextFieldCenterFi.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldCenterFi.setText("");
			f.setAllowsInvalid(false);
			f = (CoordFormatter)formattedTextFieldCenterLa.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldCenterLa.setText("");
			f.setAllowsInvalid(false);
		}
		setFiFields(mapFileDsc.getFi_coord());
		setLaFields(mapFileDsc.getLa_coord());
		
	}

	private void setLaFields(double[] la_coord) {
		if (la_coord != null) {
			formattedTextFieldLaMin.setValue(la_coord[0]);
			formattedTextFieldLaMax.setValue(la_coord[1]);
		}else {
			CoordFormatter f = (CoordFormatter)formattedTextFieldLaMin.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldLaMin.setText("");
			f.setAllowsInvalid(false);
			f = (CoordFormatter)formattedTextFieldLaMax.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldLaMax.setText("");
			f.setAllowsInvalid(false);
		}
		
	}

	private void setFiFields(double[] fi_coord) {
		if (fi_coord != null) {
			formattedTextFieldFiMin.setValue(fi_coord[0]);
			formattedTextFieldFiMax.setValue(fi_coord[1]);
		}else {
			CoordFormatter f = (CoordFormatter)formattedTextFieldFiMin.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldFiMin.setText("");
			f.setAllowsInvalid(false);
			f = (CoordFormatter)formattedTextFieldFiMax.getFormatter();
			f.setAllowsInvalid(true);
			formattedTextFieldFiMax.setText("");
			f.setAllowsInvalid(false);
		}
		
	}

	/**
	 * Create the panel.
	 */
	public MapFileDscGUI() {
		setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {70, 123};
		gridBagLayout.rowHeights = new int[] {25, 25, 25, 25, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("filename:");
		lblNewLabel.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(5, 2, 1, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		btnFilename = new JButton("filename");
		btnFilename.setMargin(new Insets(2, 5, 2, 5));
		btnFilename.setPreferredSize(new Dimension(120, 23));
		btnFilename.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnFilename = new GridBagConstraints();
		gbc_btnFilename.weightx = 1.0;
		gbc_btnFilename.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilename.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnFilename.insets = new Insets(5, 1, 1, 2);
		gbc_btnFilename.gridx = 1;
		gbc_btnFilename.gridy = 0;
		add(btnFilename, gbc_btnFilename);
		
		JLabel lblFileType = new JLabel("file type:");
		lblFileType.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblFileType = new GridBagConstraints();
		gbc_lblFileType.anchor = GridBagConstraints.EAST;
		gbc_lblFileType.insets = new Insets(1, 1, 1, 5);
		gbc_lblFileType.gridx = 0;
		gbc_lblFileType.gridy = 1;
		add(lblFileType, gbc_lblFileType);
		
		comboBoxFileType = new JComboBox();
		comboBoxFileType.setPreferredSize(new Dimension(120, 22));
		comboBoxFileType.setModel(new DefaultComboBoxModel(new String[] {"PNG", "JPG"}));
		GridBagConstraints gbc_comboBoxFileType = new GridBagConstraints();
		gbc_comboBoxFileType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxFileType.anchor = GridBagConstraints.WEST;
		gbc_comboBoxFileType.insets = new Insets(1, 1, 1, 2);
		gbc_comboBoxFileType.gridx = 1;
		gbc_comboBoxFileType.gridy = 1;
		add(comboBoxFileType, gbc_comboBoxFileType);
		
		JLabel lblRegion = new JLabel("region:");
		lblRegion.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblRegion = new GridBagConstraints();
		gbc_lblRegion.anchor = GridBagConstraints.EAST;
		gbc_lblRegion.insets = new Insets(1, 1, 1, 5);
		gbc_lblRegion.gridx = 0;
		gbc_lblRegion.gridy = 2;
		add(lblRegion, gbc_lblRegion);
		
		textFieldRegion = new JTextField();
		textFieldRegion.setColumns(13);
		GridBagConstraints gbc_textFieldRegion = new GridBagConstraints();
		gbc_textFieldRegion.weightx = 1.0;
		gbc_textFieldRegion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRegion.anchor = GridBagConstraints.WEST;
		gbc_textFieldRegion.insets = new Insets(1, 1, 1, 2);
		gbc_textFieldRegion.gridx = 1;
		gbc_textFieldRegion.gridy = 2;
		add(textFieldRegion, gbc_textFieldRegion);
		
		JLabel lblMapType = new JLabel("map type:");
		lblMapType.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblMapType = new GridBagConstraints();
		gbc_lblMapType.anchor = GridBagConstraints.EAST;
		gbc_lblMapType.insets = new Insets(1, 1, 1, 5);
		gbc_lblMapType.gridx = 0;
		gbc_lblMapType.gridy = 3;
		add(lblMapType, gbc_lblMapType);
		
		comboBoxMapType = new JComboBox();
		comboBoxMapType.setPreferredSize(new Dimension(120, 22));
		comboBoxMapType.setModel(new DefaultComboBoxModel(MapTypeEnum.values()));
		GridBagConstraints gbc_comboBoxMapType = new GridBagConstraints();
		gbc_comboBoxMapType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMapType.anchor = GridBagConstraints.WEST;
		gbc_comboBoxMapType.insets = new Insets(1, 1, 1, 2);
		gbc_comboBoxMapType.gridx = 1;
		gbc_comboBoxMapType.gridy = 3;
		add(comboBoxMapType, gbc_comboBoxMapType);
		
		JLabel lblOffset = new JLabel("offset:");
		lblOffset.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblOffset = new GridBagConstraints();
		gbc_lblOffset.anchor = GridBagConstraints.EAST;
		gbc_lblOffset.insets = new Insets(1, 1, 1, 5);
		gbc_lblOffset.gridx = 0;
		gbc_lblOffset.gridy = 4;
		add(lblOffset, gbc_lblOffset);
		
		boundsEditor = new BoundsEditor();
		GridBagConstraints gbc_boundsEditor = new GridBagConstraints();
		gbc_boundsEditor.insets = new Insets(1, 1, 1, 2);
		gbc_boundsEditor.fill = GridBagConstraints.HORIZONTAL;
		gbc_boundsEditor.gridx = 1;
		gbc_boundsEditor.gridy = 4;
		add(boundsEditor, gbc_boundsEditor);
		
		JLabel lblCenterCoord = new JLabel("Center Fi:");
		lblCenterCoord.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblCenterCoord = new GridBagConstraints();
		gbc_lblCenterCoord.anchor = GridBagConstraints.EAST;
		gbc_lblCenterCoord.insets = new Insets(1, 1, 1, 5);
		gbc_lblCenterCoord.gridx = 0;
		gbc_lblCenterCoord.gridy = 5;
		add(lblCenterCoord, gbc_lblCenterCoord);
		
		formattedTextFieldCenterFi = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldCenterFi.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldCenterFi = new GridBagConstraints();
		gbc_formattedTextFieldCenterFi.insets = new Insets(1, 1, 1, 2);
		gbc_formattedTextFieldCenterFi.fill = GridBagConstraints.BOTH;
		gbc_formattedTextFieldCenterFi.gridx = 1;
		gbc_formattedTextFieldCenterFi.gridy = 5;
		add(formattedTextFieldCenterFi, gbc_formattedTextFieldCenterFi);
		
		lblCenterLa = new JLabel("Center La:");
		lblCenterLa.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblCenterLa = new GridBagConstraints();
		gbc_lblCenterLa.anchor = GridBagConstraints.EAST;
		gbc_lblCenterLa.insets = new Insets(1, 1, 1, 5);
		gbc_lblCenterLa.gridx = 0;
		gbc_lblCenterLa.gridy = 6;
		add(lblCenterLa, gbc_lblCenterLa);
		
		formattedTextFieldCenterLa = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldCenterLa.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldCenterLa = new GridBagConstraints();
		gbc_formattedTextFieldCenterLa.insets = new Insets(1, 1, 1, 2);
		gbc_formattedTextFieldCenterLa.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldCenterLa.gridx = 1;
		gbc_formattedTextFieldCenterLa.gridy = 6;
		add(formattedTextFieldCenterLa, gbc_formattedTextFieldCenterLa);
		
		JLabel lblZoom = new JLabel("zoom:");
		lblZoom.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblZoom = new GridBagConstraints();
		gbc_lblZoom.anchor = GridBagConstraints.EAST;
		gbc_lblZoom.insets = new Insets(1, 1, 1, 5);
		gbc_lblZoom.gridx = 0;
		gbc_lblZoom.gridy = 7;
		add(lblZoom, gbc_lblZoom);
		
		textFieldZoom = new JTextField();
		textFieldZoom.setColumns(10);
		GridBagConstraints gbc_textFieldZoom = new GridBagConstraints();
		gbc_textFieldZoom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldZoom.anchor = GridBagConstraints.WEST;
		gbc_textFieldZoom.insets = new Insets(1, 1, 1, 2);
		gbc_textFieldZoom.gridx = 1;
		gbc_textFieldZoom.gridy = 7;
		add(textFieldZoom, gbc_textFieldZoom);
		
		JLabel lblMinFi = new JLabel("Fi Min.:");
		lblMinFi.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblMinFi = new GridBagConstraints();
		gbc_lblMinFi.anchor = GridBagConstraints.EAST;
		gbc_lblMinFi.insets = new Insets(1, 1, 1, 5);
		gbc_lblMinFi.gridx = 0;
		gbc_lblMinFi.gridy = 8;
		add(lblMinFi, gbc_lblMinFi);
		
		formattedTextFieldFiMin = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldFiMin.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldFiMin = new GridBagConstraints();
		gbc_formattedTextFieldFiMin.insets = new Insets(1, 1, 1, 2);
		gbc_formattedTextFieldFiMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldFiMin.gridx = 1;
		gbc_formattedTextFieldFiMin.gridy = 8;
		add(formattedTextFieldFiMin, gbc_formattedTextFieldFiMin);
		
		JLabel lblMaxFi = new JLabel("Fi Max.:");
		lblMaxFi.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblMaxFi = new GridBagConstraints();
		gbc_lblMaxFi.anchor = GridBagConstraints.EAST;
		gbc_lblMaxFi.insets = new Insets(1, 1, 1, 5);
		gbc_lblMaxFi.gridx = 0;
		gbc_lblMaxFi.gridy = 9;
		add(lblMaxFi, gbc_lblMaxFi);
		
		formattedTextFieldFiMax = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldFiMax.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldFiMax = new GridBagConstraints();
		gbc_formattedTextFieldFiMax.insets = new Insets(1,1, 1, 2);
		gbc_formattedTextFieldFiMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldFiMax.gridx = 1;
		gbc_formattedTextFieldFiMax.gridy = 9;
		add(formattedTextFieldFiMax, gbc_formattedTextFieldFiMax);
		
		labelLaMin = new JLabel("La Min.:");
		labelLaMin.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_labelLaMin = new GridBagConstraints();
		gbc_labelLaMin.insets = new Insets(0, 0, 5, 5);
		gbc_labelLaMin.anchor = GridBagConstraints.EAST;
		gbc_labelLaMin.gridx = 0;
		gbc_labelLaMin.gridy = 10;
		add(labelLaMin, gbc_labelLaMin);
		
		formattedTextFieldLaMin = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldLaMin.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldLaMin = new GridBagConstraints();
		gbc_formattedTextFieldLaMin.insets = new Insets(1,1, 1, 2);
		gbc_formattedTextFieldLaMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldLaMin.gridx = 1;
		gbc_formattedTextFieldLaMin.gridy = 10;
		add(formattedTextFieldLaMin, gbc_formattedTextFieldLaMin);
		
		lblLaMax = new JLabel("La Max.:");
		lblLaMax.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblLaMax = new GridBagConstraints();
		gbc_lblLaMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblLaMax.anchor = GridBagConstraints.EAST;
		gbc_lblLaMax.gridx = 0;
		gbc_lblLaMax.gridy = 11;
		add(lblLaMax, gbc_lblLaMax);
		
		formattedTextFieldLaMax = new JFormattedTextField(new CoordFormatter());
		formattedTextFieldLaMax.setColumns(3);
		GridBagConstraints gbc_formattedTextFieldLaMax = new GridBagConstraints();
		gbc_formattedTextFieldLaMax.insets = new Insets(1,1, 1, 2);
		gbc_formattedTextFieldLaMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldLaMax.gridx = 1;
		gbc_formattedTextFieldLaMax.gridy = 11;
		add(formattedTextFieldLaMax, gbc_formattedTextFieldLaMax);
		
		JLabel lblDate = new JLabel("date:");
		lblDate.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(1, 1, 1, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 12;
		add(lblDate, gbc_lblDate);
		
		textFieldDate = new JTextField();
		textFieldDate.setColumns(10);
		GridBagConstraints gbc_textFieldDate = new GridBagConstraints();
		gbc_textFieldDate.insets = new Insets(1, 1, 1, 2);
		gbc_textFieldDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDate.anchor = GridBagConstraints.WEST;
		gbc_textFieldDate.gridx = 1;
		gbc_textFieldDate.gridy = 12;
		add(textFieldDate, gbc_textFieldDate);
		
		JLabel labelValidDate = new JLabel("valid date:");
		labelValidDate.setPreferredSize(new Dimension(70, 25));
		GridBagConstraints gbc_labelValidDate = new GridBagConstraints();
		gbc_labelValidDate.anchor = GridBagConstraints.EAST;
		gbc_labelValidDate.insets = new Insets(1, 1, 1, 5);
		gbc_labelValidDate.gridx = 0;
		gbc_labelValidDate.gridy = 13;
		add(labelValidDate, gbc_labelValidDate);
		
		textFieldOffset = new JTextField();
		textFieldOffset.setColumns(10);
		GridBagConstraints gbc_textFieldOffset = new GridBagConstraints();
		gbc_textFieldOffset.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldOffset.anchor = GridBagConstraints.WEST;
		gbc_textFieldOffset.insets = new Insets(1, 1, 1, 2);
		gbc_textFieldOffset.gridx = 1;
		gbc_textFieldOffset.gridy = 13;
		add(textFieldOffset, gbc_textFieldOffset);

	}

	public JButton getBtnFilename() {
		return btnFilename;
	}
	public JComboBox getComboBoxFileType() {
		return comboBoxFileType;
	}
	public JComboBox getComboBoxMapType() {
		return comboBoxMapType;
	}
	public BoundsEditor getBoundsEditor() {
		return boundsEditor;
	}
	public JTextField getTextFieldRegion() {
		return textFieldRegion;
	}
	public JFormattedTextField getFormattedTextFieldCenterFi() {
		return formattedTextFieldCenterFi;
	}
	public JFormattedTextField getFormattedTextFieldCenterLa() {
		return formattedTextFieldCenterLa;
	}
}
