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
public interface IPayment extends Serializable {

	public long getPaymentID();

	public void setPaymentID(long paymentID);

	public Date getReceiveDate();

	public void setReceiveDate(Date receiveDate);

	public Amount getReceiveAmount();

	public void setReceiveAmount(Amount receiveAmount);

	public String getReceiveFrom();

	public void setReceiveFrom(String receiveFrom);

	public Date getDistributeDate();

	public void setDistributeDate(Date distributeDate);

	public Amount getDistributeAmount();

	public void setDistributeAmount(Amount distributeAmount);

	public boolean getIsToFDR();

	public void setIsToFDR(boolean toFDR);

	public Amount getFDRAmount();

	public void setFDRAmount(Amount fdrAmount);

	public boolean getIsToRepo();

	public void setIsToRepo(boolean toRepo);

	public Amount getRepoAmount();

	public void setRepoAmount(Amount repoAmount);

	public boolean getIsToAPG();

	public void setIsToAPG(boolean toAPG);

	public Amount getAPGAmount();

	public void setAPGAmount(Amount apgAmount);

	public boolean getIsToTL1();

	public void setIsToTL1(boolean toTL1);

	public Amount getTL1Amount();

	public void setTL1Amount(Amount tl1Amount);

	public boolean getIsToTL2();

	public void setIsToTL2(boolean toTL2);

	public Amount getTL2Amount();

	public void setTL2Amount(Amount tl2Amount);

	public boolean getIsToTL3();

	public void setIsToTL3(boolean toTL3);

	public Amount getTL3Amount();

	public void setTL3Amount(Amount tl3Amount);

	public boolean getIsToBC();

	public void setIsToBC(boolean toBC);

	public Amount getBCAmount();

	public void setBCAmount(Amount bcAmount);

	public boolean getIsToOthers();

	public void setIsToOthers(boolean toOthers);

	public Amount getOthersAmount();

	public void setOthersAmount(Amount othersAmount);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);

	boolean getIsToCollectionAccount();

	void setIsToCollectionAccount(boolean toCollectionAccount);

	Amount getCollectionAccountAmount();

	void setCollectionAccountAmount(Amount collectionAccountAmount);
}
