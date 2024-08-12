package com.integrosys.cms.batch.sibs.customer;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Method used to map data obtained from a file into an object.
 */
public class CustomerUpdateFieldSetMapper extends AbstractFieldSetMapper {
	public Object doMapLine(FieldSet fs) {
		OBCustomerUpdate obCU001 = new OBCustomerUpdate();
		obCU001.setRecordType(fs.readString(0));
		obCU001.setCustomerID(fs.readString(1));
		obCU001.setCustomerName(fs.readString(2));
		obCU001.setCustomerShortName(fs.readString(3));
		obCU001.setLegalConstitution(fs.readString(4));
		obCU001.setLegalRegCountry(fs.readString(5));

		if (!CommonUtil.isEmpty(fs.readString(6))) {
			obCU001.setIncorporatedDate(fs.readDate(6, getDateFormat()));
		}

        obCU001.setCustomerType(fs.readString(7));

		if (!CommonUtil.isEmpty(fs.readString(8))) {
			obCU001.setCustomerRelationshipStartDate(fs.readDate(8, getDateFormat()));
		}

        obCU001.setIsicCode(fs.readString(9));
		obCU001.setIdNo(fs.readString(10));
		obCU001.setIdType(fs.readString(11));
		obCU001.setSecIdNo(fs.readString(12));
		obCU001.setSecIdType(fs.readString(13));
		obCU001.setAddrType(fs.readString(14));
		obCU001.setAddr1(fs.readString(15));
		obCU001.setAddr2(fs.readString(16));
		obCU001.setAddr3(fs.readString(17));
		obCU001.setAddr4(fs.readString(18));
		obCU001.setPostCode(fs.readString(19));
		obCU001.setResCountry(fs.readString(20));
		obCU001.setLanguage(fs.readString(21));
		obCU001.setSecAddrType(fs.readString(22));
		obCU001.setSecAddr1(fs.readString(23));
		obCU001.setSecAddr2(fs.readString(24));
		obCU001.setSecAddr3(fs.readString(25));
		obCU001.setSecAddr4(fs.readString(26));
		obCU001.setSecPostcode(fs.readString(27));
		obCU001.setEndLineIndicator(fs.readString(28));

		return obCU001;
	}
}
