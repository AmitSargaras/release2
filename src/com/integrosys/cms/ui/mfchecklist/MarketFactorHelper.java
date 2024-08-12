package com.integrosys.cms.ui.mfchecklist;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public abstract class MarketFactorHelper {

	private static final Logger logger = LoggerFactory.getLogger(MarketFactorHelper.class);

	public static List getMarketFactorTemplateList(ICommonMFTemplate[] mfTemplate, IMFChecklist curTemplate) {
		String curTemplateID = "";

		if (curTemplate != null) {
			curTemplateID = String.valueOf(curTemplate.getMFTemplateID());
		}

		List labelValueBeanList = new ArrayList();
		try {
			boolean found = false;
			for (int i = 0; i < mfTemplate.length; i++) {
				ICommonMFTemplate commonMarketFactorTemplate = mfTemplate[i];
				LabelValueBean lvBean = new LabelValueBean(commonMarketFactorTemplate.getMFTemplateName(), String
						.valueOf(commonMarketFactorTemplate.getMFTemplateID()));
				labelValueBeanList.add(lvBean);

				if (curTemplateID.equals(String.valueOf(commonMarketFactorTemplate.getMFTemplateID()))) {
					found = true;
				}

			}
			if ((curTemplate != null) && !found) {
				LabelValueBean lvBean = new LabelValueBean(curTemplate.getMFTemplateName(), String.valueOf(curTemplate
						.getMFTemplateID()));
				labelValueBeanList.add(lvBean);

			}
		}
		catch (Exception ex) {
			logger.warn("error encounter when preparing label value bean, continue to process", ex);
		}

		return CommonUtil.sortDropdown(labelValueBeanList);
	}
}
