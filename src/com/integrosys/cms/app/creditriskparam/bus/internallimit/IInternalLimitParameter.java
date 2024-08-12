/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedEntry.java,v 1.6 2003/08/13 08:41:24 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.internallimit;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IInternalLimitParameter extends java.io.Serializable,IValueObject {
	
	public double getCapitalFundAmount();

	public void setCapitalFundAmount(double capitalFundAmount);

	public double getInternalLimitPercentage();

	public void setInternalLimitPercentage(double internalLimitPercentage);

	public String getStatus();

	public void setStatus(String status);

	public long getGroupID();

	public void setGroupID(long groupID);

	public long getId();

	public void setId(long id);
	
	public String getCapitalFundAmountCurrencyCode();
	
	public void setCapitalFundAmountCurrencyCode(String capitalFundAmountCurrencyCode);
	
	public String getDescriptionCode();
	
	public void setDescriptionCode(String descriptionCode);
	
	public double getGp5LimitPercentage();
	
	public void setGp5LimitPercentage(double gp5LimitPercentage);
	
	public double getTotalLoanAdvanceAmount();
	
	public void setTotalLoanAdvanceAmount(double totalLoanAdvanceAmount);
	
	public String getTotalLoanAdvanceAmountCurrencyCode();
	
	public void setTotalLoanAdvanceAmountCurrencyCode(String totalLoanAdvanceAmountCurrencyCode);
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);
	
	public long getCommonRef();

	public void setCommonRef(long commonRef);

    public IInternalLimitParameter getValue() throws InternalLimitException;
}
