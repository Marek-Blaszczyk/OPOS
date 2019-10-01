package amw.opos.maphandler;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapsHandler {
	
	private String filename;
	
	private List<MapFileDsc> files = new ArrayList<MapFileDsc>();
	
	
	public MapsHandler() {
		super();
	}

	public MapsHandler(String filename) {
		super();
		this.filename = filename;
	}
	
	public static MapsHandler read(String filename) {
		
		ObjectMapper mapper = new ObjectMapper();

		//JSON from file to Object
		try {
			MapsHandler handler = mapper.readValue(new File(filename), MapsHandler.class);
			handler.setFilename(filename);
			return handler;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<MapFileDsc> getFiles() {
		return files;
	}

	public void setFiles(List<MapFileDsc> files) {
		this.files = files;
	}

	public void addFileDsc(MapFileDsc file_dsc) {
		files.add(file_dsc);
		
	}
	
	public void printString() {
		System.out.println("filename:"+getFilename());
		System.out.println("files:"+getFiles().size());
		for (Iterator iterator = files.iterator(); iterator.hasNext();) {
			MapFileDsc mapFileDsc = (MapFileDsc) iterator.next();
			mapFileDsc.printString();
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("Start ...");
			
			MapsHandler handler = new MapsHandler("MAPS//maps.json");
			
			MapFileDsc file_dsc = new MapFileDsc();
			file_dsc.setFilename("MAPS//mapa1.png");
			file_dsc.setFile_type(MapFileDsc.FILE_TYPE_PNG);
			file_dsc.setRegion("Baltic/Gdansk Bay");
			file_dsc.setMap_type(MapTypeEnum.ROADMAP);
			file_dsc.setOffset(new int[] {10,10,10,10});
			file_dsc.setFi_coord(new double[] {54.827330517840025f, 54.34989086222425f});
			file_dsc.setLa_coord(new double[] {18.423335447054797f, 19.6540613538441f});
			
			handler.addFileDsc(file_dsc);
			
			file_dsc.setFilename("MAPS//mapa2.png");
			file_dsc.setFile_type(MapFileDsc.FILE_TYPE_PNG);
			file_dsc.setRegion("Baltic/Gdansk Bay");
			file_dsc.setMap_type(MapTypeEnum.SATELLITE);
			file_dsc.setOffset(new int[] {20,20,20,20});
			file_dsc.setFi_coord(new double[] {54.827330517840025f, 54.34989086222425f});
			file_dsc.setLa_coord(new double[] {18.423335447054797f, 19.6540613538441f});
			handler.addFileDsc(file_dsc);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
					  .writeValueAsString(handler);
			
			System.out.println("JSON output:");
			System.out.println(jsonResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void store() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
					  .writeValueAsString(this);
			FileWriter writer = new FileWriter(filename);
			writer.write(jsonResult);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void removeFileDsc(MapFileDsc deleted) {
		files.remove(deleted);
		
	}


}
