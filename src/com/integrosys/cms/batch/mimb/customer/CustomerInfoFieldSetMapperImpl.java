package com.integrosys.cms.batch.mimb.customer;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class CustomerInfoFieldSetMapperImpl extends AbstractFieldSetMapper {

	public Object doMapLine(FieldSet fs) {
		ICustomerInfo customerInfoItem = new OBCustomerInfo();
		setDateFormat("ddMMyyyy");
		customerInfoItem.setRecordType(fs.readString(0));
		customerInfoItem.setAaNo(fs.readString(1));
		customerInfoItem.setCifNo(fs.readString(2));
		customerInfoItem.setLegalName(fs.readString(3));
		customerInfoItem.setCustomerName(fs.readString(4));
		customerInfoItem.setIdType(fs.readString(5));
		customerInfoItem.setIdNo(fs.readString(6));

		if ( fs.readString(7).equals("") || fs.readString(7) == null ) {
			customerInfoItem.setRelationshipStartDate(DateUtil.convertDate(fs.readString(7)));
		} else {
			customerInfoItem.setRelationshipStartDate( fs.readDate(7, getDateFormat() ) );
		}

		customerInfoItem.setCustomerTypeCode(fs.readString(8));
		customerInfoItem.setCustomerTypeDes(fs.readString(9));
		customerInfoItem.setCustomerLegalCons(fs.readString(10));
		customerInfoItem.setCustomerLegalConsDes(fs.readString(11));
		customerInfoItem.setAddressType(fs.readString(12));
		customerInfoItem.setAddress1(fs.readString(13));
		customerInfoItem.setAddress2(fs.readString(14));
		customerInfoItem.setAddress3(fs.readString(15));
		customerInfoItem.setAddress4(fs.readString(16));
		customerInfoItem.setCountry(fs.readString(17));

		return customerInfoItem;
	}

}
