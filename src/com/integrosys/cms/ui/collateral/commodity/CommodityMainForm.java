/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainForm.java,v 1.4 2005/07/15 06:19:30 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/15 06:19:30 $ Tag: $Name: $
 */

public class CommodityMainForm extends TrxContextForm implements Serializable {
	private String[] collateralPool;

	private String[] specificTrans;

	private String[] cash;

	private String[] cashReq;

	private String[] limitIDList;

	private String[] deleteAppCommodity;

	private String[] deleteContract;

	private String[] deleteHedgeContract;

	private String[] deleteLoanAgency;

	private String checkCMTmaker = "";

	private String preConditions = "";

	private String preCondLastUpdatedBy = "";

	private String preCondUpdatedDate = "";

	public String[] getCollateralPool() {
		return this.collateralPool;
	}

	public void setCollateralPool(String[] collateralPool) {
		this.collateralPool = collateralPool;
	}

	public String[] getSpecificTrans() {
		return this.specificTrans;
	}

	public void setSpecificTrans(String[] specificTrans) {
		this.specificTrans = specificTrans;
	}

	public String[] getCash() {
		return this.cash;
	}

	public void setCash(String[] cash) {
		this.cash = cash;
	}

	public String[] getCashReq() {
		return this.cashReq;
	}

	public void setCashReq(String[] cashReq) {
		this.cashReq = cashReq;
	}

	public String[] getLimitIDList() {
		return limitIDList;
	}

	public void setLimitIDList(String[] limitIDList) {
		this.limitIDList = limitIDList;
	}

	public String[] getDeleteAppCommodity() {
		return this.deleteAppCommodity;
	}

	public void setDeleteAppCommodity(String[] deleteAppCommodity) {
		this.deleteAppCommodity = deleteAppCommodity;
	}

	public String[] getDeleteContract() {
		return this.deleteContract;
	}

	public void setDeleteContract(String[] deleteContract) {
		this.deleteContract = deleteContract;
	}

	public String[] getDeleteHedgeContract() {
		return deleteHedgeContract;
	}

	public void setDeleteHedgeContract(String[] deleteHedgeContract) {
		this.deleteHedgeContract = deleteHedgeContract;
	}

	public String[] getDeleteLoanAgency() {
		return deleteLoanAgency;
	}

	public void setDeleteLoanAgency(String[] deleteLoanAgency) {
		this.deleteLoanAgency = deleteLoanAgency;
	}

	public String getCheckCMTmaker() {
		return checkCMTmaker;
	}

	public void setCheckCMTmaker(String checkCMTmaker) {
		this.checkCMTmaker = checkCMTmaker;
	}

	public String getPreConditions() {
		return this.preConditions;
	}

	public void setPreConditions(String preConditions) {
		this.preConditions = preConditions;
	}

	public String getPreCondLastUpdatedBy() {
		return this.preCondLastUpdatedBy;
	}

	public void setPreCondLastUpdatedBy(String preCondLastUpdatedBy) {
		this.preCondLastUpdatedBy = preCondLastUpdatedBy;
	}

	public String getPreCondUpdatedDate() {
		return this.preCondUpdatedDate;
	}

	public void setPreCondUpdatedDate(String preCondUpdatedDate) {
		this.preCondUpdatedDate = preCondUpdatedDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "commodityMainObj", "com.integrosys.cms.ui.collateral.commodity.CommodityMainMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}
}
