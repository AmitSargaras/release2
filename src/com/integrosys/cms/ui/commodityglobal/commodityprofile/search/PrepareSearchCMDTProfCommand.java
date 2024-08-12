package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfHelper;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-9
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.list.
 *      PrepareSearchCommand.java
 */
public class PrepareSearchCMDTProfCommand extends AbstractCommand implements ICMSUIConstant, CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return new String[0][];
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { AN_CATEGORY_ID_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_CATEGORY_VAL_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_PRICE_TYPE_ID_COLL, CN_COLLECTION, REQUEST_SCOPE },
				{ AN_PRICE_TYPE_VAL_COLL, CN_COLLECTION, REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Prepare drop down list values");

		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		resultMap.put(AN_CATEGORY_ID_COLL, categoryList.getCommCategoryID());
		resultMap.put(AN_CATEGORY_VAL_COLL, categoryList.getCommCategoryValue());
		Collection priceIdColl = CMDTProfHelper.getPriceTypeIDCollection();
		Collection priceValColl = CMDTProfHelper.getPriceTypeDescCollection();
		resultMap.put(AN_PRICE_TYPE_ID_COLL, priceIdColl);
		resultMap.put(AN_PRICE_TYPE_VAL_COLL, priceValColl);

		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
