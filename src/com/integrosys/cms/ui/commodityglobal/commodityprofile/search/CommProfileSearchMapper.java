package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-9
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.search.
 *      CommProfileSearchMapper.java
 */
public class CommProfileSearchMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
		DefaultLogger.debug(this, " - mapOBToForm");
		CommProfileSearchForm aForm = (CommProfileSearchForm) arg0;
		return aForm;
	}

	public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {
		DefaultLogger.debug(this, " - mapFormToOB");
		CommProfileSearchForm aForm = (CommProfileSearchForm) arg0;
		ProfileSearchCriteria criteria = new ProfileSearchCriteria();
		criteria.setCategory(trimString(aForm.getCommodityCategory()));
		criteria.setPriceType(trimString(aForm.getPriceType()));
		criteria.setNonRICCode(trimString(aForm.getNonRICCode()));
		criteria.setProductSubType(trimString(aForm.getCommoditySubType()));
		return criteria;
	}

	private String trimString(String orginStr) {
		if (orginStr == null) {
			return null;
		}
		else {
			return orginStr.trim();
		}
	}
}