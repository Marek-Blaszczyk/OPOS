package amw.opos.maphandler;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MapTypeEnum {
	ROADMAP(0,"ROADMAP"),		//0 - (normal, default 2D map)
	SATELLITE(1, "SATELLITE"),	//	1 - SATELLITE (photographic map)
	HYBRID(2, "HYBRID"),		// 2 - HYBRID (photographic map + roads and city names)
	TERRAIN(3, "TERRAIN"); 		//3 - TERRAIN
	
	private Integer map_type;
    private String map_type_name;
 
    // standard constructors
    private MapTypeEnum(Integer map_type, String map_type_name) {
		this.map_type = map_type;
		this.map_type_name = map_type_name;
	}
 
    @JsonValue
    public String getName() {
        return map_type_name;
    }

	public Integer getMap_type() {
		return map_type;
	}

	

}
