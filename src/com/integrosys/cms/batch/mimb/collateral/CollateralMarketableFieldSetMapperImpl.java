package com.integrosys.cms.batch.mimb.collateral;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class CollateralMarketableFieldSetMapperImpl extends AbstractFieldSetMapper {

	int countrec = 0;
	public Object doMapLine(FieldSet fs) {

		ICollateralMarketable collateralMarketableItem = new OBCollateralMarketable();

		setDateFormat("ddMMyyyy");
		try {
		collateralMarketableItem.setRecordType(fs.readString(0));
		collateralMarketableItem.setSourceSecurityID(fs.readString(1));
		collateralMarketableItem.setSecurityType(fs.readString(2));
		collateralMarketableItem.setOriginatingSecurityCurrency(fs.readString(3));
		collateralMarketableItem.setCmsSecurityCurrency(fs.readString(4));
		collateralMarketableItem.setSecurityLocation(fs.readString(5));
		collateralMarketableItem.setBranchName(fs.readString(6));
		collateralMarketableItem.setBranchDescription(fs.readString(7));
		collateralMarketableItem.setSecurityCustodianType(fs.readString(8));
		collateralMarketableItem.setSecurityCustodianValue(fs.readString(9));

		if ( fs.readString(10).equals("") || fs.readString(10) == null ) {
			collateralMarketableItem.setSecurityMaturityDate(DateUtil.convertDate(fs.readString(10)));
		} else {
		  collateralMarketableItem.setSecurityMaturityDate(  fs.readDate(10, getDateFormat())  );
		}

		if ( fs.readString(11).equals("") || fs.readString(11) == null ) {
			collateralMarketableItem.setSecurityPerfectionDate(DateUtil.convertDate(fs.readString(11)));
		} else {
			collateralMarketableItem.setSecurityPerfectionDate( fs.readDate(11, getDateFormat() ) );
		}


		collateralMarketableItem.setLegalEnforceability(fs.readString(12));

		if ( fs.readString(13).equals("") || fs.readString(13) == null ) {
			collateralMarketableItem.setLegalEnforceabilityDate(DateUtil.convertDate(fs.readString(13)));
		} else {
			collateralMarketableItem.setLegalEnforceabilityDate( fs.readDate( 13, getDateFormat() ) );
		}


		collateralMarketableItem.setNatureOfCharge(fs.readString(14));
		collateralMarketableItem.setAmountOfCharge(Double.valueOf(fs.readString(15)));

		if ( fs.readString(16).equals("") || fs.readString(16) == null ) {
			collateralMarketableItem.setDateLegallyCharged(DateUtil.convertDate(fs.readString(16)));
		} else {
			collateralMarketableItem.setDateLegallyCharged(  fs.readDate(16, getDateFormat() )   );
		}

		collateralMarketableItem.setChargeType(fs.readString(17));
		collateralMarketableItem.setEquityType(fs.readString(18));
		collateralMarketableItem.setCdsNumber(fs.readString(19));
		collateralMarketableItem.setNomineeName(fs.readString(20));
		collateralMarketableItem.setRecognizeExchange(fs.readString(21));
		collateralMarketableItem.setRegisteredOwner(fs.readString(22));
		collateralMarketableItem.setNumberOfUnits(Long.parseLong(fs.readString(23)));
		collateralMarketableItem.setUnitPrice(Double.valueOf(fs.readString(24)));
		collateralMarketableItem.setStockExchange(fs.readString(25));
		collateralMarketableItem.setCountryOfStockExchange(fs.readString(26));
		collateralMarketableItem.setExchangeControlApprovalObtained(fs.readString(27));
		collateralMarketableItem.setStockCode(fs.readString(28));
		collateralMarketableItem.setBaselCompliantUnitTrustCollateral(fs.readString(29));
		collateralMarketableItem.setSecurityPledgorID(fs.readString(30));
		collateralMarketableItem.setSecurityPledgorName(fs.readString(31));
		collateralMarketableItem.setSecurityPledgorRelationship(fs.readString(32));
		collateralMarketableItem.setAaNo(fs.readString(33));
		collateralMarketableItem.setSourceLimitID(fs.readString(34));
		
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, e);
		}

		return collateralMarketableItem;
	}

}