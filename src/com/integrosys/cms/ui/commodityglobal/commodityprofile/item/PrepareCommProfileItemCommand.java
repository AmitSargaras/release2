/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/PrepareCommProfileItemCommand.java,v 1.4 2006/03/23 09:51:32 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/23 09:51:32 $ Tag: $Name: $
 */

public class PrepareCommProfileItemCommand extends AbstractCommand implements ICMSUIConstant, CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commProfileTrxValue", "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
						SERVICE_SCOPE }, { AN_CMDT_PROF_SEARCH_OBJ, CN_CMDT_PROF_SEARCH_OBJ, SERVICE_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { FN_CMDT_CATEGORY_ID, CN_STRING, REQUEST_SCOPE },
				{ AN_PRODUCT_TYPE_ID_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_PRODUCT_TYPE_VAL_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_COUNTRY_LAB_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_COUNTRY_VAL_COLL, CN_COLLECTION, REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		// Prepare drop down list value for title document item page
		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		String cmdtCategory = getCMDTCategory(map);
		result.put(FN_CMDT_CATEGORY_ID, cmdtCategory);
		result.put(AN_PRODUCT_TYPE_ID_COLL, categoryList.getCommProductID(cmdtCategory));
		result.put(AN_PRODUCT_TYPE_VAL_COLL, categoryList.getCommProductValue(cmdtCategory));

		CountryList countryList = CountryList.getInstance();
		result.put(CMDTProfConstants.AN_COUNTRY_LAB_COLL, countryList.getCountryLabels());
		result.put(CMDTProfConstants.AN_COUNTRY_VAL_COLL, countryList.getCountryValues());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private String getCMDTCategory(HashMap map) {
		IProfileTrxValue trxValue = (IProfileTrxValue) (IProfileTrxValue) map.get("commProfileTrxValue");
		String cmdtCategory = "";
		ProfileSearchCriteria toSearchProfile = (ProfileSearchCriteria) map.get(AN_CMDT_PROF_SEARCH_OBJ);
		if (toSearchProfile != null) {
			cmdtCategory = toSearchProfile.getCategory();
		}
		if ((cmdtCategory == null) || "".equals(cmdtCategory.trim())) {
			if ((trxValue != null) && (trxValue.getStagingProfile() != null)) {
				cmdtCategory = trxValue.getStagingProfile()[0].getCategory();
			}
		}
		DefaultLogger.debug(this, " - Category : " + cmdtCategory);
		return cmdtCategory;
	}
}
