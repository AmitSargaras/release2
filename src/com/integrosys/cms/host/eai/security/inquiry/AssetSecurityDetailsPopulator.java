package com.integrosys.cms.host.eai.security.inquiry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.ChequeDetail;

/**
 * <p>
 * Security Subtype details populater for Asset Based Security.
 * <p>
 * Special requirement for cheque, vehicle, plant&equipment, which require
 * indirect logic to construct the indiviual object.
 * @author Chong Jun Yong
 * 
 */
public class AssetSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		AssetSecurity asset = (AssetSecurity) securitySubtypeObject;
		asset.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setAssetDetail(asset);

		Map parameters = new HashMap();
		if (ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE.equals(securityMsgBody.getSecurityDetail().getSecuritySubType()
				.getStandardCodeValue())) {
			parameters.put("collateralId", new Long(securityMsgBody.getSecurityDetail().getCMSSecurityId()));
			List chequeDetailList = getSecurityDao().retrieveObjectsListByParameters(parameters, ChequeDetail.class);

			Vector chequeDetails = new Vector(chequeDetailList);
			securityMsgBody.setChequeDetail(chequeDetails);
		}
		else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(securityMsgBody.getSecurityDetail()
				.getSecuritySubType().getStandardCodeValue())) {
			if (securityMsgBody.getAssetDetail().getSpecificChargeDetail() != null && asset.getVehicleDetail() != null) {
				securityMsgBody.getAssetDetail().getSpecificChargeDetail().setDownPaymentCash(
						asset.getVehicleDetail().getDownPaymentCash());
				securityMsgBody.getAssetDetail().getSpecificChargeDetail().setDownPaymentTradeIn(
						asset.getVehicleDetail().getDownPaymentTradeIn());
			}
		}
		else if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT.equals(securityMsgBody.getSecurityDetail()
				.getSecuritySubType().getStandardCodeValue())) {
			if (securityMsgBody.getAssetDetail().getSpecificChargeDetail() != null
					&& asset.getPlantEquipDetail() != null) {
				securityMsgBody.getAssetDetail().getSpecificChargeDetail().setDownPaymentCash(
						asset.getPlantEquipDetail().getDownPaymentCash());
				securityMsgBody.getAssetDetail().getSpecificChargeDetail().setDownPaymentTradeIn(
						asset.getPlantEquipDetail().getDownPaymentTradeIn());
			}
		}

		populateValuationDetails(securityMsgBody);

		populateInsurancePolicyDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return AssetSecurity.class;
	}

}
