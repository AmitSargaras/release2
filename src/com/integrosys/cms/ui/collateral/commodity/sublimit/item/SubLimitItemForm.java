/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/SubLimitItemForm.java,v 1.1 2005/10/14 06:31:32 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14 Tag :
 *        com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitForm.java
 */
public class SubLimitItemForm extends CommonForm implements Serializable {
	private String subLimitType;

	private String subLimitCCY;

	private String subLimitAmount;

	private String activeAmount;

	private String innerFlag;

	public String getSubLimitAmount() {
		return subLimitAmount;
	}

	public void setSubLimitAmount(String subLimitAmount) {
		this.subLimitAmount = subLimitAmount;
	}

	public String getSubLimitCCY() {
		return subLimitCCY;
	}

	public void setSubLimitCCY(String subLimitCCY) {
		this.subLimitCCY = subLimitCCY;
	}

	public String getSubLimitType() {
		return subLimitType;
	}

	public void setSubLimitType(String subLimitType) {
		this.subLimitType = subLimitType;
	}

	public String getActiveAmount() {
		return activeAmount;
	}

	public void setActiveAmount(String activeAmount) {
		this.activeAmount = activeAmount;
	}

	public String getInnerFlag() {
		return innerFlag;
	}

	public void setInnerFlag(String innerFlag) {
		this.innerFlag = innerFlag;
	}

	public String[][] getMapper() {
		String[][] input = { { SLUIConstants.AN_OB_SL, SLUIConstants.CN_SLI_MAP } };
		return input;
	}
}