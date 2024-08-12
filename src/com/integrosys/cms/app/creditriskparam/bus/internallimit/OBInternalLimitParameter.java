/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/OBForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/13 08:41:24 $ Tag: $Name: $
 * 
 */
public class OBInternalLimitParameter implements IInternalLimitParameter {

	private static final long serialVersionUID = 1L;
	
	private long id = ICMSConstant.LONG_INVALID_VALUE;
	
	private String descriptionCode;
	
	private String capitalFundAmountCurrencyCode;
	
	private double capitalFundAmount;
	
	private String totalLoanAdvanceAmountCurrencyCode;
	
	private double totalLoanAdvanceAmount;
	
	private double gp5LimitPercentage;
	
	private double internalLimitPercentage;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;
	
	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	
	public OBInternalLimitParameter() {
        super();
    }
	
	public OBInternalLimitParameter(IInternalLimitParameter obj) {
		this();
        AccessorUtil.copyValue (obj, this);
	}

	public double getCapitalFundAmount() {
		return capitalFundAmount;
	}

	public void setCapitalFundAmount(double capitalFundAmount) {
		this.capitalFundAmount = capitalFundAmount;
	}

	public double getInternalLimitPercentage() {
		return internalLimitPercentage;
	}

	public void setInternalLimitPercentage(double internalLimitPercentage) {
		this.internalLimitPercentage = internalLimitPercentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCapitalFundAmountCurrencyCode() {
		return capitalFundAmountCurrencyCode;
	}

	public void setCapitalFundAmountCurrencyCode(String capitalFundAmountCurrencyCode) {
		this.capitalFundAmountCurrencyCode = capitalFundAmountCurrencyCode;
	}

	public String getDescriptionCode() {
		return descriptionCode;
	}

	public void setDescriptionCode(String descriptionCode) {
		this.descriptionCode = descriptionCode;
	}

	public double getGp5LimitPercentage() {
		return gp5LimitPercentage;
	}

	public void setGp5LimitPercentage(double gp5LimitPercentage) {
		this.gp5LimitPercentage = gp5LimitPercentage;
	}

	public double getTotalLoanAdvanceAmount() {
		return totalLoanAdvanceAmount;
	}

	public void setTotalLoanAdvanceAmount(double totalLoanAdvanceAmount) {
		this.totalLoanAdvanceAmount = totalLoanAdvanceAmount;
	}

	public String getTotalLoanAdvanceAmountCurrencyCode() {
		return totalLoanAdvanceAmountCurrencyCode;
	}

	public void setTotalLoanAdvanceAmountCurrencyCode(String totalLoanAdvanceAmountCurrencyCode) {
		this.totalLoanAdvanceAmountCurrencyCode = totalLoanAdvanceAmountCurrencyCode;
	}

	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

    public IInternalLimitParameter getValue() throws InternalLimitException {
		try {
			IInternalLimitParameter ilParam = new OBInternalLimitParameter();
			AccessorUtil.copyValue(this, ilParam);
			return ilParam;
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}
}