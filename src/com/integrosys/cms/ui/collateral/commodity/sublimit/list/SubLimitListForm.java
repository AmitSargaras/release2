/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/SubLimitListForm.java,v 1.1 2005/10/14 06:31:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14
 * @Tag :
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitListForm.java
 */
public class SubLimitListForm extends CommonForm implements Serializable {
	private String[] collaterPoolChk;

	private String[] specificTrxChk;

	private String[] cashReqQtyChk;

	private String[] cashReqQty;

	private String[] chkDeletes;

	private String[] limitIDArray;

	public String[] getChkDeletes() {
		return chkDeletes;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String[] getCashReqQty() {
		return cashReqQty;
	}

	public void setCashReqQty(String[] cashReqQty) {
		this.cashReqQty = cashReqQty;
	}

	public String[] getCashReqQtyChk() {
		return cashReqQtyChk;
	}

	public void setCashReqQtyChk(String[] cashReqQtyChk) {
		this.cashReqQtyChk = cashReqQtyChk;
	}

	public String[] getCollaterPoolChk() {
		return collaterPoolChk;
	}

	public void setCollaterPoolChk(String[] collaterPoolChk) {
		this.collaterPoolChk = collaterPoolChk;
	}

	public String[] getSpecificTrxChk() {
		return specificTrxChk;
	}

	public void setSpecificTrxChk(String[] specificTrxChk) {
		this.specificTrxChk = specificTrxChk;
	}

	public String[] getLimitIDArray() {
		return limitIDArray;
	}

	public void setLimitIDArray(String[] limitIDArray) {
		this.limitIDArray = limitIDArray;
	}

	public String[][] getMapper() {
		String[][] input = { { SLUIConstants.AN_DEL_SLT_MAP, SLUIConstants.CN_SL_DEL_MAPPER },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_SL_LIST_MAPPER } };
		return input;
	}
}