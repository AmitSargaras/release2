/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/IDealCashDeposit.java,v 1.3 2005/10/10 01:30:27 pooja Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author $Author: pooja $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/10 01:30:27 $ Tag: $Name: $
 */
public interface IDealCashDeposit extends Serializable {
	public long getCashDepositID();

	public void setCashDepositID(long cashDepositID);

	public String getDepositType();

	public void setDepositType(String type);

	public String getReferenceNo();

	public void setReferenceNo(String referenceNo);

	public Amount getAmount();

	public void setAmount(Amount amount);

	public String getLocationCountryCode();

	public void setLocationCountryCode(String locationCountryCode);

	public Date getMaturityDate();

	public void setMaturityDate(Date maturityDate);

	public Date getLastUpdatedDate();

	public void setLastUpdatedDate(Date lastUpdatedDate);

	public long getCommonReferenceID();

	public void setCommonReferenceID(long commonReferenceID);

	public String getStatus();

	public void setStatus(String status);
}