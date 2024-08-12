/**
 *
 */
package com.integrosys.cms.batch.feeds.vehicle;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

/**
 * @author gp loh
 * @date 06oct08 1008hr
 *
 */
public class VehicleFieldSetMapper extends AbstractFieldSetMapper {

	public Object doMapLine(FieldSet fs) {
		// TODO Auto-generated method stub
		OBVehicle obv = new OBVehicle();
		obv.setMakeOfVehicle(fs.readString(0));
		obv.setModelOfVehicle(fs.readString(1));
		obv.setYearOfVehicle(fs.readInt(2));

		if ( fs.readString(3) == null || fs.readString(3).trim().equals("") ) {
			// use for check later in file loader
			obv.setValuationFSV( -9999999999.99 );
		} else {
			obv.setValuationFSV( fs.readDouble(3) );
		}

		return obv;
	}

}
