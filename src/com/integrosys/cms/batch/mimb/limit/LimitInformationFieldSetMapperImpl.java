package com.integrosys.cms.batch.mimb.limit;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class LimitInformationFieldSetMapperImpl extends AbstractFieldSetMapper {

	public Object doMapLine(FieldSet fs) {
		ILimitInformation limitInformationItem = new OBLimitInformation();
		limitInformationItem.setRecordType(fs.readString(0));
		limitInformationItem.setAaNo(fs.readString(1));
		limitInformationItem.setSourceLimitID(fs.readString(2));
		limitInformationItem.setLimitBookingLocationCode(fs.readString(3));
		limitInformationItem.setLimitBookingLocationDesc(fs.readString(4));
		limitInformationItem.setProductTypeCode(fs.readString(5));
		limitInformationItem.setProductDescription(fs.readString(6));
		limitInformationItem.setApprovedLimit(Double.valueOf(fs.readString(7)));
		limitInformationItem.setDrawingLimit(Double.valueOf(fs.readString(8)));
		limitInformationItem.setOutstandingBalanceSign(fs.readString(9));
		limitInformationItem.setOutstandingBalance(Double.valueOf(fs.readString(10)));
		limitInformationItem.setLimitStatus(fs.readString(11));

		return limitInformationItem;
	}

}
