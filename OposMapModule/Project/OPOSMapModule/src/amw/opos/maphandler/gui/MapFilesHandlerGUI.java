package amw.opos.maphandler.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import amw.opos.maphandler.MapFileDsc;
import amw.opos.maphandler.MapsHandler;
import amw.opos.maphandler.gui.components.MapFileDscEditGUI;
import amw.opos.maphandler.gui.components.MapFileDscGUI;
import amw.opos.maphandler.gui.components.MapsFilesListModel;
import amw.opos.maphandler.gui.utils.ComponentResizeEndListener;
import amw.opos.maphandler.gui.utils.GuiUtils;
import sun.security.action.GetLongAction;

import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MapFilesHandlerGUI {

	private JFrame frmMapFilesHandler;
	private final JToolBar toolBar = new JToolBar();
	private JMenuItem mntmOpen;
	private MapsHandler mapsHandler = null;
	private JList<MapFileDsc> listFiles;
	private MapsFilesListModel listModel;
	private MapFileDscGUI mapFileDscGUI;
	private JPanel panelMapProperties;
	private JButton btnDelete;
	private JLabel lblMapPreview;
	
	private String preview_map_filename=null;
	
	

	public MapsHandler getMapsHandler() {
		return mapsHandler;
	}

	public void setMapsHandler(MapsHandler mapsHandler) {
		this.mapsHandler = mapsHandler;
		frmMapFilesHandler.setTitle(frmMapFilesHandler.getTitle()+"(file: "+mapsHandler.getFilename()+" )");
		listModel = new MapsFilesListModel(mapsHandler);
		getListFiles().setModel(listModel);
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapFilesHandlerGUI window = new MapFilesHandlerGUI();
					GuiUtils.displayCenter(window.frmMapFilesHandler);
					window.frmMapFilesHandler.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapFilesHandlerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMapFilesHandler = new JFrame();
		frmMapFilesHandler.setTitle("Map Files Handler");
		frmMapFilesHandler.setBounds(100, 100, 1000, 750);
		frmMapFilesHandler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmMapFilesHandler.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File"); 
		menuBar.add(mnFile);
		
		mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openMapHandlerFile();
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		mnFile.add(mntmOpen);
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveMapHandlerFile();
			}
		});
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		mnFile.add(mntmSave);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExport = new JMenuItem("Export selected");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportSelected();
			}
		});
		mntmExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
		mnFile.add(mntmExport);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMapFilesHandler.dispose();
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mntmExit.setActionCommand("");
		mnFile.add(mntmExit);
		frmMapFilesHandler.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JPanel JPanelMain = new JPanel();
		frmMapFilesHandler.getContentPane().add(JPanelMain, BorderLayout.CENTER);
		JPanelMain.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPaneMap = new JSplitPane();
		JPanelMain.add(splitPaneMap, BorderLayout.CENTER);
		
		JPanel panelLeft = new JPanel();
		splitPaneMap.setLeftComponent(panelLeft);
		panelLeft.setLayout(new BorderLayout(0, 20));
		
		JPanel panelMapsList = new JPanel();
		panelMapsList.setMinimumSize(new Dimension(10, 150));
		panelMapsList.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelLeft.add(panelMapsList, BorderLayout.CENTER);
		panelMapsList.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneFiles = new JScrollPane();
		panelMapsList.add(scrollPaneFiles);
		scrollPaneFiles.setBorder(new TitledBorder(null, "Map lists", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		listFiles = new JList<MapFileDsc>();
		listFiles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
		        if (e.getValueIsAdjusting()) {
		        	listFilesSelectionChanged();
		        }
			}
		});
		scrollPaneFiles.setViewportView(listFiles);
		
		JPanel panel_1 = new JPanel();
		panelMapsList.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Add map");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMapFile();
			}
		});
		panel_1.add(btnNewButton);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteMap();
			}
		});
		btnDelete.setEnabled(false);
		panel_1.add(btnDelete);
		
		panelMapProperties = new JPanel();
		panelMapProperties.setAlignmentY(Component.TOP_ALIGNMENT);
		panelMapProperties.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelLeft.add(panelMapProperties, BorderLayout.SOUTH);
		panelMapProperties.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Map properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMapProperties.setLayout(new BorderLayout(0, 0));
		
		mapFileDscGUI = new MapFileDscGUI();
		panelMapProperties.add(mapFileDscGUI, BorderLayout.CENTER);
		
		JPanel panelMapPreview = new JPanel();
		panelMapPreview.setBorder(new TitledBorder(null, "Map preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPaneMap.setRightComponent(panelMapPreview);
		panelMapPreview.setLayout(new BorderLayout(0, 0));
		
		lblMapPreview = new JLabel();
		lblMapPreview.addComponentListener(new ComponentResizeEndListener() {
		    @Override
		    public void resizeTimedOut() {
		        redrawPreview();
		    }
		});
		panelMapPreview.add(lblMapPreview, BorderLayout.CENTER);
		splitPaneMap.setDividerLocation(220);
	}

	protected void redrawPreview() {
		if (preview_map_filename != null) {
			int w = getLblMapPreview().getWidth();
			int h = getLblMapPreview().getHeight();
			getLblMapPreview().setIcon(new ImageIcon(
					new ImageIcon(preview_map_filename).getImage().getScaledInstance(w, w, Image.SCALE_DEFAULT)));
			System.out.println("PREVIEW for " + preview_map_filename);
		}
	}

	protected void exportSelected() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//user must select a file not folder
		fileChooser.setMultiSelectionEnabled(false);//disabled selection of multiple files
		fileChooser.setDialogTitle("Export maps");
		GuiUtils.displayCenter(fileChooser, this.frmMapFilesHandler);
		int wyn = fileChooser.showSaveDialog(frmMapFilesHandler);
		
	}

	protected void deleteMap() {
		Object o = getListFiles().getSelectedValue();
		if (o != null) {
			int option = JOptionPane.showConfirmDialog(this.frmMapFilesHandler, "Delete selected map?", "Delete confirmation", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				MapFileDsc selected = (MapFileDsc)o;
				getMapsHandler().removeFileDsc(selected);
				deleteFile(selected, new File(getMapsHandler().getFilename()));
				
				listModel.contentsChanged();
				//getMapFileDscGUI().clear();
				
				getBtnDelete().setEnabled(false);
			}
			
		}else {
			getBtnDelete().setEnabled(false);
		}
		
	}

	protected void saveMapHandlerFile() {
		getMapsHandler().store();
		
	}

	protected void addMapFile() {
		MapFileDscEditGUI gui = new MapFileDscEditGUI();
		GuiUtils.displayCenter(gui, this.frmMapFilesHandler);
		gui.setCreate(true);
		gui.setVisible(true);
		if (gui.isApproved()) {
			if (gui.getMapFileDsc() != null) {
				getMapsHandler().addFileDsc(gui.getMapFileDsc());
				copyFile(gui.getSelectedFile(), new File(getMapsHandler().getFilename()));
				
				listModel.contentsChanged();
			}
		}
		
	}

	private void copyFile(File sourceFile, File dscFile) {
		Path source_path = Paths.get(sourceFile.getAbsolutePath());
		Path dsc_path = Paths.get(dscFile.getParent()+"\\"+source_path.getFileName().toString());
		
		try {
			Path wyn = Files.copy(source_path, dsc_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void deleteFile(MapFileDsc sourceFile, File dscFile) {
		
		try {
			Path path = Paths.get(dscFile.getParent()+"\\"+sourceFile.getFilename());
			boolean result = Files.deleteIfExists(path);

			System.out.println("DELETING:"+path.toString()+":"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	protected void listFilesSelectionChanged() {
		Object o = getListFiles().getSelectedValue();
		if (o != null) {
			MapFileDsc selected = (MapFileDsc)o;
			getMapFileDscGUI().setMapFileDsc(selected);
			previewMap(selected);
			getBtnDelete().setEnabled(true);
		}else {
			getBtnDelete().setEnabled(false);
		}
		
	}

	private void previewMap(MapFileDsc selected) {
		preview_map_filename = new File(getMapsHandler().getFilename()).getParent()+"\\"+selected.getFilename();
		//getLblMapPreview().setIcon(new ImageIcon(filename));
		redrawPreview();
	}

	protected void openMapHandlerFile() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
		FileFilter ff = new FileNameExtensionFilter("pliki definicji map", "json");
		fileChooser.addChoosableFileFilter(ff);//ff added to filechooser
		fileChooser.setFileFilter(ff);//st ff as default selection
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//user must select a file not folder
		fileChooser.setMultiSelectionEnabled(false);//disabled selection of multiple files
		GuiUtils.displayCenter(fileChooser, this.frmMapFilesHandler);
		int wyn = fileChooser.showOpenDialog(frmMapFilesHandler);
		if (wyn == JFileChooser.APPROVE_OPTION) {
			MapsHandler handler = MapsHandler.read(fileChooser.getSelectedFile().getAbsolutePath());
			setMapsHandler(handler);
		}
		
	}
	
	public JList getListFiles() {
		return listFiles;
	}
	
	public MapFileDscGUI getMapFileDscGUI() {
		return mapFileDscGUI;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JLabel getLblMapPreview() {
		return lblMapPreview;
	}
}
