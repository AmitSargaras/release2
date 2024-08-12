/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/ApprovedCommMapper.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBApprovedCommodityType;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:08 $ Tag: $Name: $
 */

public class ApprovedCommMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		ApprovedCommForm aForm = (ApprovedCommForm) cForm;

		int indexID = Integer.parseInt((String) inputs.get("indexID"));
		OBApprovedCommodityType obToChange = null;

		if (indexID == -1) {
			obToChange = new OBApprovedCommodityType();
		}
		else {
			try {
				int secIndexID = Integer.parseInt((String) inputs.get("secIndexID"));
				HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
				ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
				obToChange = (OBApprovedCommodityType) AccessorUtil.deepClone(col[secIndexID]
						.getApprovedCommodityTypes()[indexID]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		IProfile profile = obToChange.getProfile();
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		if (!isEmptyOrNull(aForm.getProductSubType())) {
			try {
				DefaultLogger.debug(this, "profile id: " + aForm.getProductSubType());
				profile = proxy.getProfileByProfileID(Long.parseLong(aForm.getProductSubType()));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		DefaultLogger.debug(this, "profile is: " + profile);
		obToChange.setProfile(profile);

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ApprovedCommForm aForm = (ApprovedCommForm) cForm;
		HashMap approvedCommMap = (HashMap) obj;
		IApprovedCommodityType approvedCommObj = (IApprovedCommodityType) approvedCommMap.get("obj");
		IProfile profile = approvedCommObj.getProfile();
		DefaultLogger.debug(this, "profile is : " + profile);
		aForm.setSecurityID((String) approvedCommMap.get("securityID"));
		aForm.setSecuritySubType(profile.getCategory());
		aForm.setProductType(profile.getProductType());
		if (aForm.getEvent().equals(ApprovedCommAction.EVENT_READ)) {
			aForm.setProductSubType(profile.getProductSubType());
		}
		else {
			aForm.setProductSubType(String.valueOf(profile.getProfileID()));
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
		// {"serviceApprovedComm",
		// "com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType"
		// , SERVICE_SCOPE},
		});
	}
}
