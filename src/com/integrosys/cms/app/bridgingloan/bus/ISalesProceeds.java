package com.integrosys.cms.app.bridgingloan.bus;

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
public interface ISalesProceeds extends Serializable {

	public long getProceedsID();

	public void setProceedsID(long proceedsID);

	public Date getProceedsDate();

	public void setProceedsDate(Date proceedsDate);

	public String getPurpose();

	public void setPurpose(String purpose);

	public float getPurposePercent();

	public void setPurposePercent(float purposePercent);

	public String getBankName();

	public void setBankName(String bankName);

	public String getChequeNo();

	public void setChequeNo(String chequeNo);

	public Amount getReceiveAmount();

	public void setReceiveAmount(Amount receiveAmount);

	public String getStatus();

	public void setStatus(String status);

	public String getRemarks();

	public void setRemarks(String remarks);

	public Date getDistributeDate();

	public void setDistributeDate(Date distributeDate);

	public Amount getDistributeAmount();

	public void setDistributeAmount(Amount distributeAmount);

	public boolean getIsToTL1();

	public void setIsToTL1(boolean tlInd);

	public Amount getTL1Amount();

	public void setTL1Amount(Amount tL1Amount);

	public boolean getIsToOD();

	public void setIsToOD(boolean isToOD);

	public Amount getOdAmount();

	public void setOdAmount(Amount odAmount);

	public boolean getIsToFDR();

	public void setIsToFDR(boolean fdrInd);

	public Amount getFdrAmount();

	public void setFdrAmount(Amount fdrAmount);

	public boolean getIsToHDA();

	public void setIsToHDA(boolean hdaInd);

	public Amount getHdaAmount();

	public void setHdaAmount(Amount hdaAmount);

	public boolean getIsToOthers();

	public void setIsToOthers(boolean othersInd);

	public String getOthersAccount();

	public void setOthersAccount(String account);

	public Amount getOthersAmount();

	public void setOthersAmount(Amount othersAmount);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);

}
