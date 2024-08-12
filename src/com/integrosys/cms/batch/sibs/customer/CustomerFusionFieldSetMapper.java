package com.integrosys.cms.batch.sibs.customer;

import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

/**
* Method used to map data obtained from a file into an object.
* for customer ID fusion
*/
public class CustomerFusionFieldSetMapper implements FieldSetMapper {

	public Object mapLine(FieldSet fset) {

		OBCustomerFusion obCU003 = new OBCustomerFusion();
		if ( fset.readString(0).equalsIgnoreCase("D") ) {
			obCU003.setRecordType( fset.readString(0) );
			obCU003.setCustomerID( fset.readString(1) );
			obCU003.setCustomerNewID( fset.readString(2) );

            // new added 15oct08 1626hr, to handle end line ind 'T'
			obCU003.setEndLineIndicator( fset.readString(3) );


        }
		return obCU003;
  }
}
