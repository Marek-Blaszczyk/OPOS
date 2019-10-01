/*
 * MapFileDscHandler.cpp
 *
 *  Created on: 29 paü 2018
 *      Author: blaszczyk
 */

#include "MapFileDscHandler.h"

#include <math.h>
#include <string.h>
#include <cstdio>
#include <fstream>
#include <iostream>
#include <sstream>

#include "gason.h"

using namespace std;

namespace oposbom {

const string FILE_TYPE_JPEG = "JPG";
const string FILE_TYPE_PNG = "PNG";

const string JSON_FILENAME = "filename";
const string JSON_FILES = "files";
const string JSON_FILE_TYPE = "file_type";
const string JSON_REGION = "region";
const string JSON_MAP_TYPE = "map_type";
const string JSON_OFFSET = "offset";
const string JSON_FI_COORD = "fi_coord";
const string JSON_LA_COORD = "la_coord";
const string JSON_ID = "id";
const string JSON_ZOOM = "zoom";
const string JSON_CENTER_COORD = "center_coord";
const string JSON_MAP_DATE = "map_date";
const string JSON_VALID_DATE = "valid_date";

const int GOOGLE_TILE_SIZE = 256;

MapFileDscHandler::MapFileDscHandler() {
	// TODO Auto-generated constructor stub

}

MapFileDscHandler::~MapFileDscHandler() {
	// TODO Auto-generated destructor stub
}

void parseJson(JsonValue o, MapFileDscHandler *handler, bool initialized) {

	switch (o.getTag()) {
	case JSON_NUMBER:
		printf("Json number:%g\n", o.toNumber());
		break;
	case JSON_STRING:
		printf("Json string: %s \n", o.toString());
		break;
	case JSON_ARRAY:
		printf("Json ARRAY[");
		for (auto i : o) {
			parseJson(i->value, NULL, false);
		}
		printf("]ENDARRAY1");
		break;
	case JSON_OBJECT:
		for (auto i : o) {
			printf("Json object %s = |\n", i->key);
			parseJson(i->value, handler, false);
		}
		break;
	case JSON_TRUE:
		fprintf(stdout, "true");
		break;
	case JSON_FALSE:
		fprintf(stdout, "false");
		break;
	case JSON_NULL:
		printf("null\n");
		break;
	default:
		printf("default \n");
		break;
	}
}

MapTypeEnum getMapTypeEnum(string map_type) {
	if (map_type.compare("ROADMAP") == 0)
		return ROADMAP;
	else if (map_type.compare("SATELLITE") == 0)
		return SATELLITE;
	else if (map_type.compare("HYBRID") == 0)
		return HYBRID;
	else if (map_type.compare("TERRAIN") == 0)
		return TERRAIN;
	else
		return ROADMAP;
}

string getMapTypeName(MapTypeEnum map_type) {
	if (map_type == ROADMAP)
		return MAP_TYPE_ROADMAP;
	else if (map_type == SATELLITE)
		return MAP_TYPE_SATELLITE;
	else if (map_type == HYBRID)
		return MAP_TYPE_HYBRID;
	else if (map_type == TERRAIN)
		return MAP_TYPE_TERRAIN;
	else
		return MAP_TYPE_ROADMAP;
}

void fillMapDsc(MapFileDsc *mapa, string key, string value) {
#ifdef DEBUG
	cout << "Setting " << mapa->getFilename() << " " << key << " to " << value
	<< endl;
#endif
	if (key.compare(JSON_FILENAME) == 0)
		mapa->setFilename(value);
	else if (key.compare(JSON_FILE_TYPE) == 0) {
		mapa->setFileType(string(value));
	} else if (key.compare(JSON_REGION) == 0)
		mapa->setRegionName(value);
	else if (key.compare(JSON_MAP_TYPE) == 0)
		mapa->setMapType(getMapTypeEnum(value));
	else if (key.compare(JSON_VALID_DATE) == 0)
		mapa->setValidDate(value);
	else if (key.compare(JSON_MAP_DATE) == 0)
		mapa->setMapDate(value);
	else
		cerr << "fillMapDsc string Key:" << key << " unsupported." << endl;
}

void fillMapDsc(MapFileDsc *mapa, string key, JsonValue o) {
#ifdef DEBUG
	cout << "Setting " << mapa->getFilename() << " " << key << " to " << o.toNumber()
	<< " (" << o.ival << ")" << endl;
#endif
	if (key.compare(JSON_ID) == 0) {
		int id = round(o.toNumber()); //o.ival;
		mapa->setId(id);
	} else if (key.compare(JSON_ZOOM) == 0)
		mapa->setZoom(o.fval);
	else
		cerr << "fillMapDsc number Key:" << key << " unsupported." << endl;
}

void parseOffsetJson(JsonValue o, MapFileDsc *mapa, int idx) {
#ifdef DEBUG
	cout << "parseOffsetJson Start" << endl;
#endif
	switch (o.getTag()) {
	case JSON_NUMBER:
		mapa->setOffset(idx, o.toNumber());
#ifdef DEBUG
		cout << "mapa " << mapa->getFilename() << " offset[" << idx << "]="
		<< o.toNumber() << endl;
#endif
		break;
	case JSON_ARRAY:
#ifdef DEBUG
		printf("Offset ARRAY[");
#endif
		for (auto i : o) {
			parseOffsetJson(i->value, mapa, idx++);
		}
#ifdef DEBUG
		printf("]ENDARRAY1");
#endif
		break;
	default:
		cerr << "Offset default." << endl;
		break;
	}
}

void parseFiCoordJson(JsonValue o, MapFileDsc *mapa, int idx) {
#ifdef DEBUG
	cout << "parseFiCoordJson Start" << endl;
#endif
	switch (o.getTag()) {
	case JSON_NUMBER:
		mapa->setGeoRegion(idx * 2, o.toNumber());
#ifdef DEBUG
		cout << "mapa " << mapa->getFilename() << " GeoRegion[" << (idx * 2)
		<< "]=" << o.toNumber() << endl;
#endif
		break;
	case JSON_ARRAY:
#ifdef DEBUG
		printf("FiCoord ARRAY[");
#endif
		for (auto i : o) {
			parseFiCoordJson(i->value, mapa, idx++);
		}
#ifdef DEBUG
		printf("]ENDARRAY1");
#endif
		break;
	default:
		cerr << "FiCoord default." << endl;
		break;
	}
}
void parseLaCoordJson(JsonValue o, MapFileDsc *mapa, int idx) {
#ifdef DEBUG
	cout << "parseLaCoordJson Start" << endl;
#endif
	switch (o.getTag()) {
	case JSON_NUMBER:
		mapa->setGeoRegion(idx * 2 + 1, o.toNumber());
#ifdef DEBUG
		cout << "mapa " << mapa->getFilename() << " GeoRegion[" << (idx * 2 + 1)
		<< "]=" << o.toNumber() << endl;
#endif
		break;
	case JSON_ARRAY:
#ifdef DEBUG
		printf("LaCoord ARRAY[");
#endif
		for (auto i : o) {
			parseLaCoordJson(i->value, mapa, idx++);
		}
#ifdef DEBUG
		printf("]ENDARRAY1");
#endif
		break;
	default:
		cerr << "LaCoord default." << endl;
		break;
	}
}

void parseCenterCoordJson(JsonValue o, MapFileDsc *mapa, int idx) {
#ifdef DEBUG
	cout << "parseCenterCoordJson Start" << endl;
#endif
	switch (o.getTag()) {
	case JSON_NUMBER:
		mapa->setCenterCoord(idx, o.toNumber());
#ifdef DEBUG
		cout << "mapa " << mapa->getFilename() << " CenterCoord[" << idx
		<< "]=" << o.toNumber() << endl;
#endif
		break;
	case JSON_ARRAY:
#ifdef DEBUG
		printf("LaCoord ARRAY[");
#endif
		for (auto i : o) {
			parseCenterCoordJson(i->value, mapa, idx++);
		}
#ifdef DEBUG
		printf("]ENDARRAY1");
#endif
		break;
	default:
		cerr << "LaCoord default." << endl;
		break;
	}
}
void parseMapJson(JsonValue o, MapFileDsc *mapa, string key) {
	switch (o.getTag()) {
	case JSON_NUMBER:
#ifdef DEBUG
		cout << "Map number:" << o.toNumber() << " " << o.ival << endl;
#endif
		fillMapDsc(mapa, key, o);
		break;
	case JSON_STRING:
#ifdef DEBUG
		printf("Map string: %s \n", o.toString());
#endif
		fillMapDsc(mapa, key, o.toString());
		break;
	case JSON_ARRAY:
#ifdef DEBUG
		printf("Map ARRAY1[");
#endif
		for (auto i : o) {
			parseJson(i->value, NULL, false);
		}
#ifdef DEBUG
		printf("]ENDARRAY1");
#endif
		break;
	case JSON_OBJECT:
#ifdef DEBUG
		cout << "parseMapJson Object" << endl;
#endif
		for (auto i : o) {
			string object_name;
#ifdef DEBUG
			cout << "Map object " << i->key << endl;
#endif
			if (strcmp(JSON_OFFSET.c_str(), i->key) == 0)
				parseOffsetJson(i->value, mapa, 0);
			else if (strcmp(JSON_FI_COORD.c_str(), i->key) == 0)
				parseFiCoordJson(i->value, mapa, 0);
			else if (strcmp(JSON_LA_COORD.c_str(), i->key) == 0)
				parseLaCoordJson(i->value, mapa, 0);
			else if (strcmp(JSON_CENTER_COORD.c_str(), i->key) == 0)
				parseCenterCoordJson(i->value, mapa, 0);
			else {
				object_name.append(i->key);
				parseMapJson(i->value, mapa, object_name);
			}

		}
		break;
	default:
		cerr << "parseMapJson default." << endl;
		break;
	}
}

void parseMapsJson(JsonValue o, MapFileDscHandler *handler) {
	switch (o.getTag()) {
	case JSON_ARRAY:
		for (auto i : o) {
			MapFileDsc *map = new MapFileDsc();
			handler->addMapFileDsc(map);
			parseMapJson(i->value, map, "");
		}
		break;
	case JSON_OBJECT:
		for (auto i : o) {
#ifdef DEBUG
			cout << "parseMapsJson Object:" << i->key << endl;
#endif
			if (strcmp(JSON_FILES.c_str(), i->key) == 0)
				parseMapsJson(i->value, handler);
			else
				cerr << "Unknown Maps object:" << i->key << endl;
		}
		break;
	default:
		cerr << "default Maps:" << o.toNode()->key << endl;
		break;
	}
}

void parseHandlerJson(JsonValue o, MapFileDscHandler *handler,
		bool initialized) {

	switch (o.getTag()) {
	case JSON_STRING:
#ifdef DEBUG
		printf("Handler string: %s \n", o.toString());
#endif
		if ((handler != NULL) && (initialized)) {
			string filename;
			filename.append(o.toString());
			handler->setFilename(filename);
#ifdef DEBUG
			printf("FILENAME:%s\n", handler->getFilename().c_str());
#endif
		} else
			cerr << "Unknown JSON_STRING:" << o.toString() << endl;
		/*parseMapsJson(o.toNode()->value, handler);*/
		break;
	case JSON_OBJECT:
		for (auto i : o) {
#ifdef DEBUG
			printf("Handler object %s = |\n", i->key);
#endif
			if (strcmp(JSON_FILENAME.c_str(), i->key) == 0)
				parseHandlerJson(i->value, handler, true);
			else if (strcmp(JSON_FILES.c_str(), i->key) == 0)
				parseMapsJson(i->value, handler);
			else
				cerr << "!!! Unknown object:" << i->key << endl;
		}
		break;
	default:
		cerr << "!!! default !!!" << endl;
		break;
	}
}

void printMapFileHandler(MapFileDscHandler *mapHandler) {
	cout << "File:" << mapHandler->getFilename() << endl;
	cout << "Files:" << mapHandler->getFiles().size() << endl;
	for (auto val : mapHandler->getFiles()) {
		cout << "\t file_type:" << val->getFileType() << endl;
		cout << "\t filename:" << val->getFilename() << endl;
		cout << "\t id:" << val->getId() << endl;
		cout << "\t map type:" << getMapTypeName(val->getMapType()) << endl;
		cout << "\t center[" << val->getCenterCoord()[0] << ","
				<< val->getCenterCoord()[1] << "]" << endl;
		cout << "\t region[" << val->getGeoRegion()[0] << ","
				<< val->getGeoRegion()[1] << "," << val->getGeoRegion()[2]
				<< "," << val->getGeoRegion()[3] << "]" << endl;
		cout << "\t offset[" << val->getOffset()[0] << ","
				<< val->getOffset()[1] << "," << val->getOffset()[2] << ","
				<< val->getOffset()[3] << "]" << endl;
		cout << "\t region:" << val->getRegionName() << endl;
		cout << "\t zoom:" << val->getZoom() << endl;
		cout << "\t valid:" << val->getValidDate() << endl;
		cout << "\t date:" << val->getMapDate() << endl;
		cout << "--------------------------------------" << endl;
	}
}

MapFileDscHandler MapFileDscHandler::readFromJsonFile(string filename) {
	ifstream t(filename);
	stringstream buffer;
	buffer << t.rdbuf();
	int n = buffer.str().length();

	char char_array[n + 1];

	// copying the contents of the
	// string to char array
	strcpy(char_array, buffer.str().c_str());

	char *endptr;
	JsonValue value;
	JsonAllocator allocator;
	int status = jsonParse(char_array, &endptr, &value, allocator);
	if (status != JSON_OK) {
		fprintf(stderr, "%s at %zd\n", jsonStrError(status),
				endptr - buffer.str().c_str());
		return MapFileDscHandler(filename);
	}
	printf("+++++++++++++++++++++++++\n");
	MapFileDscHandler handler;
	parseHandlerJson(value, &handler, NULL);
	printf("...............................\n");
	printMapFileHandler(&handler);
	return handler;
}

void MapFileDscHandler::addMapFileDsc(MapFileDsc* mapFileDsc) {
	getFiles().push_back(mapFileDsc);
}

vector<MapFileDsc*>& MapFileDscHandler::getMapsList(double fi_min,
		double la_min, double fi_max, double la_max) {

	vector<MapFileDsc*> *list = new vector<MapFileDsc*>;
	for (auto map : getFiles()){
		if (map->checkRegion(fi_min, la_min, fi_max, la_max))
			list->push_back(map);
	}
	return *list;
}

MapPoint* MapFileDscHandler::getPoint(int map_id, double fi, double la) {
}

GeoPoint* MapFileDscHandler::getGeoCoord(int map_id, int x, int y) {
}

MapPoint* MapFileDscHandler::getMapCoord(int map_id, double fi, double la) {
	int x_max = 7512, y_max = 6024;
	double fi_center = 54.0595545, la_center = 14.1804651;
	double zoom = 10.23;
	int tile = 256;

	double siny = sin(fi * M_PI / 180);

	// Truncating to 0.9999 effectively limits latitude to 89.189. This is
	// about a third of a tile past the edge of the world tile.
	siny = min(max(siny, -0.9999), 0.9999);
	int x = GOOGLE_TILE_SIZE * (0.5 + la / 360);
	int y = GOOGLE_TILE_SIZE
			* (0.5 - log((1 + siny) / (1 - siny)) / (4 * M_PI));

	MapPoint point = new MapPoint();
	point.x = x;
	point.y = y;

	return point;
}


} /* namespace oposbom */

