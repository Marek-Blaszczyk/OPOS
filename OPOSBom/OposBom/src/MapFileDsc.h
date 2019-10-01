/*
 * MapFileDsc.h
 *
 * The class represents information about one map file.
 *
 *  Created on: 29 paü 2018
 *      Author: blaszczyk
 */

#ifndef MAPFILEDSC_H_
#define MAPFILEDSC_H_

#include <string>

using namespace std;

namespace oposbom {

/**
 * Map types.
 */
enum MapTypeEnum {
	ROADMAP = 0,	// 0 - (normal, default 2D map)
	SATELLITE = 1,	// 1 - SATELLITE (photographic map)
	HYBRID = 2,		// 2 - HYBRID (photographic map + roads and city names)
	TERRAIN = 3		//!< TERRAIN
};

const string MAP_TYPE_ROADMAP = "ROADMAP";
const string MAP_TYPE_SATELLITE = "SATELLITE";
const string MAP_TYPE_HYBRID = "HYBRID";
const string MAP_TYPE_TERRAIN = "TERRAIN";

class MapFileDscHandler;

/**
 * The class represents information about one map file.
 */
class MapFileDsc {

	friend class MapFileDscHandler;

private:

	/**
	 * Map filename.
	 */
	string filename;

	/**
	 * Unique map ID.
	 */
	int id = 0L;

	/**
	 * File type (format) e.g. PNG, JPEG, ...
	 */
	string file_type;

	/**
	 * Map region name (e.g. "Baltic Sea", "GdaÒsk Bay", ...)
	 */
	string region_name;

	/**
	 * Map type ROADMAP, SATTELITE, ...
	 */
	MapTypeEnum map_type = ROADMAP;

	/**
	 * Map offset. The margins graphic file contains map data. (xstart, ystart, xend, yend)
	 */
	int offset[4] = { 0, 0, 0, 0 };

	/**
	 * Geographical coordinates [Fi, La] in degree of the center map point.
	 */
	double center_coord[2];

	/**
	 * Geographical coordinates (in degree) of the region on map [Fi_min, La_min, Fi_max, La_max]
	 */
	double geo_region[4] = { 0.0, 0.0, 0.0, 0.0};	//fi_min, la_min, fi_max, la_max

	/**
	 * The map scale.
	 */
	float zoom = -1;

	/**
	 * Date map was created/generated (UNIX date format: seconds from 1 Jan 1970).
	 */
	string map_date;

	/**
	 * Date the map is valid (UNIX date format: seconds from 1 Jan 1970).
	 */
	string valid_date;

public:

	/**
	 * Default constructor.
	 */
	MapFileDsc();

	/**
	 * Default destructor.
	 */
	virtual ~MapFileDsc();

	/**
	 * Center point geographical coordinates.
	 * @return array of geographical coordinates [Fi_degree, La_degree]
	 */
	const double* getCenterCoord() const {
		return center_coord;
	}

	/**
	 * Sets center point coordinates.
	 * @param fi Fi coordinate (degree)
	 * @param la La coordinate (degree)
	 */
	void setCenterCoord(double fi, double la) {
		center_coord[0] = fi;
		center_coord[1] = la;
	}

	/**
	 * Sets specified geographical coordinate of the center point.
	 * @param idx 0: Fi coordinate, 1: La coordinate
	 * @param val geographical coordinate in degrees.
	 */
	void setCenterCoord(int idx, double val) {
		center_coord[idx] = val;
	}

	/**
	 * Returns geographical coordinates of the map region.
	 * @return array of coordinates in degree [Fi_min, La_min, Fi_max, La_max]
	 */
	const double* getGeoRegion() const {
		return geo_region;
	}

	/**
	 * Sets geographical coordinates of the map region.
	 * @param fi_min Minimal Fi in degree
	 * @param la_min Minimal La in degree
	 * @param fi_max Maximal Fi in degree
	 * @param la_max Maximal La in degree
	 */
	void setGeoRegion(double fi_min, double la_min, double fi_max,
			double la_max) {
		geo_region[0] = fi_min;
		geo_region[2] = fi_max;

		geo_region[1] = la_min;
		geo_region[3] = la_max;
	}

	/**
	 * Sets specified coordinate of the map region
	 * @param idx 0:Fi_min, 1:La_min, 2:Fi_max, 3:La_max
	 * @param val coordinate valu in degree
	 */
	void setGeoRegion(int idx, double val) {
		geo_region[idx] = val;
	}

	/**
	 * Gets map file type (e.g. PNG, JPEG, ...)
	 * @return file type extension
	 */
	const string& getFileType() const {
		return file_type;
	}

	/**
	 * Sets map file type.
	 * @param fileType file type name e.g. PNG, JPEG, ...
	 */
	void setFileType(const string& fileType) {
		file_type = fileType;
	}

	/**
	 * The name of the map file.
	 * @return map file name.
	 */
	const string& getFilename() const {
		return filename;
	}

	/**
	 * Sets map file name.
	 * @param filename
	 */
	void setFilename(const string& filename) {
		this->filename = filename;
	}

	/**
	 * Gets map unique ID.
	 * @return map ID.
	 */
	int getId() const {
		return id;
	}

	/**
	 * Sets map ID. The ID is calculated as hash code of the filename
	 * and is set when filename is set.
	 * @param id
	 */
	void setId(int id = 0L) {
		this->id = id;
	}

	/**
	 * Gets the date map was created/generated.
	 * @return Date in UNIX format: seconds since 1 Jan 1970.
	 */
	const string& getMapDate() const {
		return map_date;
	}

	/**
	 * Sets the date map was created/generated.
	 * @param mapDate Date in UNIX format: seconds since 1 Jan 1970.
	 */
	void setMapDate(const string& mapDate) {
		map_date = mapDate;
	}

	/**
	 * Gets type of the map.
	 * @return Map type enumeration.
	 */
	MapTypeEnum getMapType() const {
		return map_type;
	}

	/**
	 * Sets map type.
	 * @param mapType enumeration of map type.
	 */
	void setMapType(MapTypeEnum mapType = ROADMAP) {
		map_type = mapType;
	}

	/**
	 * Gets map offset. Offset is the margin map data presents on image.
	 * @return array [x_min, y_min, x_max, y_max]
	 */
	const int* getOffset() const {
		return offset;
	}

	/**
	 * Sets map offset (map data area).
	 * @param x0 X coordinate of left upper corner of map data.
	 * @param y0 Y coordinate of left upper corner of map data.
	 * @param x1 X coordinate of right down  corner of map data.
	 * @param y1 Y coordinate of right down  corner of map data.
	 */
	void setOffset(int x0, int y0, int x1, int y1) {
		offset[0] = x0;
		offset[1] = y0;
		offset[2] = x1;
		offset[3] = y1;
	}

	/**
	 * Sets specified margin coordinate.
	 * @param idx 0: x0, 1:y0, 2:x1, 3:y1
	 * @param val coordinate value
	 */
	void setOffset(int idx, int val) {
		offset[idx] = val;
	}

	/**
	 * Gets the name of map region.
	 * @return map region name.
	 */
	const string& getRegionName() const {
		return region_name;
	}

	/**
	 * Sets map region name.
	 * @param region map region name.
	 */
	void setRegionName(const string& region) {
		this->region_name = region;
	}

	/**
	 * Gets the date map is valid.
	 * @return date in UNIX form (seconds since 1 Jan 1970)
	 */
	const string& getValidDate() const {
		return valid_date;
	}

	/**
	 * Sets the date map is valid.
	 * @param validDate date in UNIX form (seconds since 1 Jan 1970)
	 */
	void setValidDate(const string& validDate) {
		valid_date = validDate;
	}

	/**
	 * Gets the map scale.
	 * @return map scale
	 */
	float getZoom() const {
		return zoom;
	}

	/**
	 * Sets map scale.
	 * @param zoom
	 */
	void setZoom(float zoom = -1) {
		this->zoom = zoom;
	}

	bool checkRegion(double fi_min, double la_min, double fi_max, double la_max);
};

} /* namespace oposbom */

#endif /* MAPFILEDSC_H_ */
