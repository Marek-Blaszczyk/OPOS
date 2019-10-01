/*
 * MapFileDsc.cpp
 *
 *  Created on: 29 paü 2018
 *      Author: blaszczyk
 */

#include "MapFileDsc.h"

namespace oposbom {

MapFileDsc::MapFileDsc() {
	// TODO Auto-generated constructor stub

}

MapFileDsc::~MapFileDsc() {
	// TODO Auto-generated destructor stub
}

bool MapFileDsc::checkRegion(double fi_min, double la_min,
		double fi_max, double la_max) {
	if ((fi_min <= getGeoRegion()[2]) && (fi_max >= getGeoRegion()[0])){
		if ((la_min <= getGeoRegion()[3]) && (la_max >= getGeoRegion()[1]))
			return true;
	}
	return false;
}

} /* namespace oposbom */


