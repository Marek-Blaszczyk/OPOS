package amw.opos.maphandler.gui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import amw.opos.maphandler.MapFileDsc;
import amw.opos.maphandler.MapTypeEnum;
import amw.opos.maphandler.MapsHandler;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class MapFileDscEditGUI extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private MapFileDsc mapFileDsc = null;
	private MapFileDscGUI mapFileDscGUI;
	private boolean approved = false;
	private boolean create = true;
	private File selectedFile;
	
	public File getSelectedFile() {
		return selectedFile;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setCreate(boolean create) {
		this.create = create;
		if (create)
			setTitle("Nowa mapa");
		else
			setTitle("Edycja mapy");
	}

	public MapFileDsc getMapFileDsc() {
		return mapFileDsc;
	}

	public void setMapFileDsc(MapFileDsc mapFileDsc) {
		this.mapFileDsc = mapFileDsc;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MapFileDscEditGUI dialog = new MapFileDscEditGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MapFileDscEditGUI() {
		setModal(true);
		setBounds(100, 100, 347, 559);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						performOK();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			mapFileDscGUI = new MapFileDscGUI();
			mapFileDscGUI.getBtnFilename().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					loadMapFile();
				}
			});
			getContentPane().add(mapFileDscGUI, BorderLayout.CENTER);
		}
	}

	protected void performOK() {
		if (create)
			mapFileDsc = new MapFileDsc();
		if (mapFileDsc != null) {
		mapFileDsc.setFilename(getMapFileDscPanel().getBtnFilename().getText());
		mapFileDsc.setFile_type(getMapFileDscPanel().getComboBoxFileType().getSelectedItem().toString());
		mapFileDsc.setRegion(getMapFileDscPanel().getTextFieldRegion().getText());
		MapTypeEnum mapType = MapTypeEnum.valueOf(getMapFileDscPanel().getComboBoxMapType().getSelectedItem().toString());
		mapFileDsc.setMap_type(mapType);
		mapFileDsc.setOffset(getMapFileDscPanel().getBoundsEditor().getValues());
		Double fi = (Double)getMapFileDscPanel().getFormattedTextFieldCenterFi().getValue();
		Double la = (Double)getMapFileDscPanel().getFormattedTextFieldCenterLa().getValue();
		mapFileDsc.setCenter_coord(new double[] {fi.floatValue(), la.floatValue()});
		
		approved = true;
		}
		
		this.dispose();
		
	}

	protected void loadMapFile() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		FileFilter ff = new FileNameExtensionFilter("pliki map JPEG oraz PNG", "jpg", "jpeg", "png");
		fileChooser.addChoosableFileFilter(ff);//ff added to filechooser
		fileChooser.setFileFilter(ff);//st ff as default selection
		int wyn = fileChooser.showOpenDialog(this);
		if (wyn == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			String filename = selectedFile.getName();
			getMapFileDscPanel().getBtnFilename().setText(filename);
			String ext;
			if(filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
		        ext = filename.substring(filename.lastIndexOf(".")+1);
		    else ext = "";
			if (ext.equalsIgnoreCase("PNG") || ext.equalsIgnoreCase("JPG"))
				getMapFileDscPanel().getComboBoxFileType().setSelectedItem(ext.toUpperCase());
			//read and print image properties
			readImageProperties(selectedFile);
		}
		
	}

	private void readImageProperties(File imageFile) {
		try(ImageInputStream in = ImageIO.createImageInputStream(imageFile)){
		    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
		    if (readers.hasNext()) {
		        ImageReader reader = readers.next();
		        try {
		            reader.setInput(in);
		            int offset[] = {0,0,reader.getWidth(0),reader.getHeight(0)};
		            //getMapFileDsc().setOffset(offset);
		            getMapFileDscPanel().getBoundsEditor().setValues(offset);
				} finally {
		            reader.dispose();
		        }
		    }
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
	}


	public MapFileDscGUI getMapFileDscPanel() {
		return mapFileDscGUI;
	}
}
