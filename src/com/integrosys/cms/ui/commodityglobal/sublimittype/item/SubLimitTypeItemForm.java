/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/item/SubLimitTypeItemForm.java,v 1.1 2005/10/06 06:03:37 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-19
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimittype.item.SubLimitTypeItemForm
 *      .java
 */
public class SubLimitTypeItemForm extends CommonForm implements Serializable {
	private String limitType;

	private String subLimitType;

	/**
	 * @return Returns the limitType.
	 */
	public String getLimitType() {
		return limitType;
	}

	/**
	 * @param limitType The limitType to set.
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/**
	 * @return Returns the subLimitType.
	 */
	public String getSubLimitType() {
		return subLimitType;
	}

	/**
	 * @param subLimitType The subLimitType to set.
	 */
	public void setSubLimitType(String subLimitType) {
		this.subLimitType = subLimitType;
	}

	public String[][] getMapper() {
		String[][] input = { { SLTUIConstants.AN_OB_SLT, SLTUIConstants.CN_SLT_ITEM_MAPPER } };
		return input;
	}
}
