/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianSearchCriteria.java,v 1.12 2005/10/10 02:50:16 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.checklist.bus.CheckListCustodianStatus;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the custodian
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2005/10/10 02:50:16 $ Tag: $Name: $
 */
public class CustodianSearchCriteria extends SearchCriteria {
	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docType = null;

	private String docSubType = null;

	private String[] trxStatus = null;

	private String[] cpcCustodianStatus = null;

	private String[] cpcCustodianStatusExclude = null;

	private CheckListCustodianStatus[] checkListCustodianStatus = null; // added
																		// to
																		// support
																		// filtering
																		// by
																		// cpc,
																		// cpc
																		// cust
																		// &
																		// cust
																		// statuses

	private String[] countryCodes = null; // bernard - added to support
											// filtering by country codes

	private String[] orgCodes = null; // bernard - added to support filtering by
										// org codes

	private boolean isPrintLodgement = false;
    private boolean isPrintReversal = false;
	private boolean isPrintWithdrawal = false;

	private boolean forMemoPrinting = false;

	private Boolean isInVault = null; // bernard - added to indicate if document
										// is in vault, not in vault or doesn't
										// matter (3 states)

	private boolean isDocItemNarrationRequired = false; // CR130 - load item
														// narration only when
														// required

	// TODO: add condition to track AND CMS_CHECKLIST_ITEM.CPC_CUST_STATUS IS
	// NOT NULL

	/**
	 * Default Constructor
	 */
	public CustodianSearchCriteria() {
	}

	/**
	 * Getter Methods
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getSubProfileID() {
		return this.subProfileID;
	}

	public long getPledgorID() {
		return this.pledgorID;
	}

	public long getCollateralID() {
		return this.collateralID;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public String getDocType() {
		return this.docType;
	}

	public String getDocSubType() {
		return this.docSubType;
	}

	public String[] getTrxStatus() {
		return trxStatus;
	}

	public String[] getCPCCustodianStatus() {
		return cpcCustodianStatus;
	}

	public String[] getCPCCustodianStatusExclude() {
		return cpcCustodianStatusExclude;
	}

	public CheckListCustodianStatus[] getCheckListCustodianStatus() {
		return checkListCustodianStatus;
	}

	public String[] getCountryCodes() {
		return countryCodes;
	}

	public String[] getOrganisationCodes() {
		return orgCodes;
	}
    public boolean getIsPrintReversal() {
        return this.isPrintReversal;
    }

	public boolean getIsPrintLodgement() {
		return this.isPrintLodgement;
	}

	public boolean getIsPrintWithdrawal() {
		return this.isPrintWithdrawal;
	}

	public boolean getForMemoPrinting() {
		return this.forMemoPrinting;
	}

	public Boolean getIsInVaultInd() {
		return this.isInVault;
	}

	public boolean getIsDocItemNarrationRequired() {
		return this.isDocItemNarrationRequired;
	}

	/**
	 * Setter Methods
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	public void setPledgorID(long aPledgorID) {
		this.pledgorID = aPledgorID;
	}

	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	public void setDocType(String aDocType) {
		this.docType = aDocType;
	}

	public void setDocSubType(String aDocSubType) {
		this.docSubType = aDocSubType;
	}

	public void setTrxStatus(String[] trxStatus) {
		this.trxStatus = trxStatus;
	}

	public void setCPCCustodianStatus(String[] cpcCustodianStatus) {
		this.cpcCustodianStatus = cpcCustodianStatus;
	}

	public void setCPCCustodianStatusExclude(String[] cpcCustodianStatusExclude) {
		this.cpcCustodianStatusExclude = cpcCustodianStatusExclude;
	}

	public void setCheckListCustodianStatus(CheckListCustodianStatus[] checkListCustodianStatus) {
		this.checkListCustodianStatus = checkListCustodianStatus;
	}

	public void setCountryCodes(String[] countryCodes) {
		this.countryCodes = countryCodes;
	}

	public void setOrganisationCodes(String[] orgCodes) {
		this.orgCodes = orgCodes;
	}

    public void setIsPrintReversal(boolean isPrintReversal) {
            this.isPrintReversal = isPrintReversal;
        }

    public void setIsPrintLodgement(boolean isPrintLodgement) {
		this.isPrintLodgement = isPrintLodgement;
	}

	public void setIsPrintWithdrawal(boolean isPrintWithdrawal) {
		this.isPrintWithdrawal = isPrintWithdrawal;
	}

	public void setForMemoPrinting(boolean aForMemoPrintingInd) {
		this.forMemoPrinting = aForMemoPrintingInd;
	}

	public void setIsDocItemNarrationRequired(boolean isNarrationReq) {
		this.isDocItemNarrationRequired = isNarrationReq;
	}

	public void setIsInVaultInd(Boolean isInVault) {
		this.isInVault = isInVault;
	}

}
