package com.integrosys.cms.app.contractfinancing.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IContractFinancingSummary extends Serializable {

	public long getLimitProfileID();

	public void setLimitProfileID(long limitProfileID);

	public long getLimitID();

	public void setLimitID(long limitID);

	public String getSourceLimit();

	public void setSourceLimit(String sourceLimit);

	public String getProductDescription();

	public void setProductDescription(String productDescription);

	public long getContractID();

	public void setContractID(long contractID);

	public String getContractNumber();

	public void setContractNumber(String contractNumber);

	public Date getContractDate();

	public void setContractDate(Date contractDate);

	public Date getExpiryDate();

	public void setExpiryDate(Date expiryDate);

	public Date getExtendedDate();

	public void setExtendedDate(Date extendedDate);

	public Amount getContractAmount();

	public void setContractAmount(Amount contractAmount);

	public float getFinancePercent();

	public void setFinancePercent(float financePercent);

	public Amount getFinancedAmount();

}
