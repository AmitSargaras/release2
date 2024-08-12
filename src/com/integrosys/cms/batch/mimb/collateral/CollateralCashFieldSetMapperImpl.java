package com.integrosys.cms.batch.mimb.collateral;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class CollateralCashFieldSetMapperImpl extends AbstractFieldSetMapper {

	public Object doMapLine(FieldSet fs) {
		ICash cashItem = new OBCash();
		setDateFormat("ddMMyyyy");
		cashItem.setRecordType(fs.readString(0));
		cashItem.setSourceSecurityID(fs.readString(1));
		cashItem.setSecurityType(fs.readString(2));
		cashItem.setSecuritySubType(fs.readString(3));
		cashItem.setOriginatingSecurityCurrency(fs.readString(4));
		cashItem.setCmsSecurityCurrency(fs.readString(5));
		cashItem.setSecurityLocation(fs.readString(6));
		cashItem.setBranchName(fs.readString(7));
		cashItem.setBranchDescription(fs.readString(8));
		cashItem.setSecurityCustodianType(fs.readString(9));
		cashItem.setSecurityCustodianValue(fs.readString(10));

		if ( fs.readString(11).equals("") || fs.readString(11) == null) {
			cashItem.setSecurityPerfectionDate(DateUtil.convertDate(fs.readString(11)));
		} else {
			cashItem.setSecurityPerfectionDate( fs.readDate(11, getDateFormat() ) );
		}

		cashItem.setLegalEnforceability(fs.readString(12));

		if ( fs.readString(13).equals("") || fs.readString(13) == null) {
			cashItem.setLegalEnforceabilityDate(DateUtil.convertDate(fs.readString(13)));
		} else {
			cashItem.setLegalEnforceabilityDate( fs.readDate(13, getDateFormat() ));
		}

		cashItem.setExchangeControlApprovalObtained(fs.readString(14));
		cashItem.setDepositOrSourceReferenceNumber(fs.readString(15));
		cashItem.setDepositCurrency(fs.readString(16));
		cashItem.setDepositAmount(Double.valueOf(fs.readString(17)));
		cashItem.setSecurityPledgorID(fs.readString(18));
		cashItem.setSecurityPledgorName(fs.readString(19));
		cashItem.setSecurityPledgorRelationship(fs.readString(20));
		cashItem.setAaNo(fs.readString(21));
		cashItem.setSourceLimitID(fs.readString(22));

		return cashItem;
	}

}