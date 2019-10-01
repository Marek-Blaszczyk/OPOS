package amw.opos.maphandler.gui.components;

import javax.swing.AbstractListModel;

import amw.opos.maphandler.MapFileDsc;
import amw.opos.maphandler.MapsHandler;

public class MapsFilesListModel extends AbstractListModel<MapFileDsc> {
	
	private static final long serialVersionUID = 1L;
	
	
	private MapsHandler mapsHandler;
	

	public MapsFilesListModel(MapsHandler mapsHandler) {
		super();
		this.mapsHandler = mapsHandler;
	}
	

	public MapsHandler getMapsHandler() {
		return mapsHandler;
	}


	public void setMapsHandler(MapsHandler mapsHandler) {
		this.mapsHandler = mapsHandler;
	}


	@Override
	public MapFileDsc getElementAt(int idx) {
		if (idx < getMapsHandler().getFiles().size())
			return getMapsHandler().getFiles().get(idx);
		return null;
	}

	@Override
	public int getSize() {
		return getMapsHandler().getFiles().size();
	}
	
	public void contentsChanged() {
		fireContentsChanged(this, getSize()-1, getSize());
	}

}
