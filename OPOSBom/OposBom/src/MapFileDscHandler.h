/*
 * MapFileDscHandler.h
 *
 *  Created on: 29 paü 2018
 *      Author: blaszczyk
 */

#ifndef MAPFILEDSCHANDLER_H_
#define MAPFILEDSCHANDLER_H_

#include <string>
#include <vector>

#include "MapFileDsc.h"

using namespace std;

namespace oposbom {

/**
 * Struct for geographical points in degree.
 */
struct GeoPoint{
	/** Fi coordinates in degree */
	double fi;

	/**
	 * La coordinate in degree.
	 */
	double la;
};
/**
 * Struct for map point coordinates.
 */
struct PosXY{
	/**
	 * X coordinate (column)
	 */
	int x;

	/**
	 * Y coordinate (row)
	 */
	int y;
};

/**
 * struct for map point.
 */
struct MapPoint{
	/**
	 * X coordinate (column) of the point
	 */
	int x;

	/**
	 * Y coordinate (row) of the point
	 */
	int y;

	/**
	 * Map point attribute (actually color)
	 */
	unsigned long attrib;
};

/**
 * Class for handling maps descriptions.
 * It also delivers method for reading information from JSON file.
 */
class MapFileDscHandler {

private:
	/**
	 * The name of the file where information of maps is stored (JSON file).
	 */
	string filename;

	/**
	 * Vector of maps descriptions.
	 */
	vector<MapFileDsc*> files;

public:
	/**
	 * Standard constructor;
	 */
	MapFileDscHandler();

	/**
	 * Creates new MapFileDscHandler with specified filename.
	 * @param filename where all information is stored.
	 */
	MapFileDscHandler(string filename){
		this->filename = filename;
	}

	/**
	 * Standard destructor.
	 */
	virtual ~MapFileDscHandler();


	/**
	 * Reads maps information from JSON file.
	 * @param filename JSON file with maps information.
	 * @return new MapFileDscHandler object with maps information.
	 */
	static MapFileDscHandler readFromJsonFile(string filename);

	/**
	 * Adds new map file description to the handler.
	 * @param mapFileDsc object to be added.
	 */
	void addMapFileDsc(MapFileDsc *mapFileDsc);

	/**
	 * Standard accessor for filename field.
	 * @return
	 */
	const string& getFilename() const {
		return filename;
	}

	/**
	 * Sets filename with maps info.
	 * @param filename
	 */
	void setFilename(string filename) {
		this->filename = filename;
	}

	/**
	 * Returns vector of maps description.
	 * @return vector of maps descriptions.
	 */
	vector<MapFileDsc*>& getFiles() {
		return files;
	}

	/**
	 * Checks for maps containing specified region.
	 * @param fi_min Minimal Fi (in degree) coordinates for region.
	 * @param la_min Minimal La (in degree) coordinates for region.
	 * @param fi_max Maximum Fi (in degree) coordinates for region.
	 * @param la_max Maximum La (in degree) coordinates for region.
	 * @return vector of MapFileDsc describing maps containing at least a part of specified region.
	 * If no one map contains this region, empty vector is returned.
	 */
	vector<MapFileDsc*>& getMapsList(double fi_min, double la_min, double fi_max, double la_max);

	/**
	 * Gets map point for specified geographical location.
	 * @param map_id Map ID for point.
	 * @param fi Fi coordinates in degree.
	 * @param la La coordinates in degree
	 * @return pointer to the structure of MapPoint describing the map pixel
	 * or NULL if point is out of the map.
	 */
	MapPoint* getPoint(int map_id, double fi, double la);

	/**
	 * Calculates geographical coordinates for specified point of the map.
	 * @param map_id Map ID
	 * @param x X coordinates (column)
	 * @param y Y coordinates (row)
	 * @return geographical point or NULL.
	 */
	GeoPoint* getGeoCoord(int map_id, int x, int y);

	/**
	 * Calculates map point for specified geographical coordinates.
	 * @param map_id Map ID
	 * @param fi Fi coordinates in degree.
	 * @param la La coordinates in degree.
	 * @return MapPoint pointer or NULL.
	 */
	MapPoint* getMapCoord(int map_id, double fi, double la);

};

} /* namespace oposbom */

#endif /* MAPFILEDSCHANDLER_H_ */
