package com.integrosys.cms.app.contractfinancing.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IContractFinancing extends IValueObject, Serializable {

	public long getContractID();

	public void setContractID(long contractID);

	public long getLimitProfileID();

	public void setLimitProfileID(long limitProfileID);

	public long getLimitID();

	public void setLimitID(long limitID);

	public String getSourceLimit();

	public void setSourceLimit(String sourceLimit);

	public String getProductDescription();

	public void setProductDescription(String productDescription);

	public String getContractNumber();

	public void setContractNumber(String contractNumber);

	public Date getContractDate();

	public void setContractDate(Date contractDate);

	public String getAwarderType();

	public void setAwarderType(String awarderType);

	public String getAwarderTypeOthers();

	public void setAwarderTypeOthers(String awarderTypeOthers);

	public String getAwarderName();

	public void setAwarderName(String awarderName);

	public String getContractType();

	public void setContractType(String contractType);

	public String getContractTypeOthers();

	public void setContractTypeOthers(String contractTypeOthers);

	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getExpiryDate();

	public void setExpiryDate(Date expiryDate);

	public Date getExtendedDate();

	public void setExtendedDate(Date extendedDate);

	public Amount getContractAmount();

	public void setContractAmount(Amount contractAmount);

	public Amount getActualFinanceAmount();

	public void setActualFinanceAmount(Amount actualFinanceAmount);

	public float getFinancePercent();

	public void setFinancePercent(float financePercent);

	public Amount getFinancedAmount();

	// public void setFinancedAmount(Amount financedAmount);

	public Amount getProjectedProfit();

	public void setProjectedProfit(Amount projectedProfit);

	public String getCollectionAccount();

	public void setCollectionAccount(String collectionAccount);

	public Date getFacilityExpiryDate();

	public void setFacilityExpiryDate(Date facilityExpiryDate);

	public String getProjectAccount();

	public void setProjectAccount(String projectAccount);

	public String getSinkingFundInd();

	public void setSinkingFundInd(String sinkingFundInd);

	public String getSinkingFundParty();

	public void setSinkingFundParty(String sinkingFundParty);

	public float getBuildUpFDR();

	public void setBuildUpFDR(float buildUpFDR);

	public String getContractDescription();

	public void setContractDescription(String contractDescription);

	public String getRemark();

	public void setRemark(String remark);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);

	public IContractFacilityType[] getFacilityTypeList();

	public void setFacilityTypeList(IContractFacilityType[] facilityTypeList);

	public IAdvance[] getAdvanceList();

	public void setAdvanceList(IAdvance[] advanceList);

	public ITNC[] getTncList();

	public void setTncList(ITNC[] tncList);

	public IFDR[] getFdrList();

	public void setFdrList(IFDR[] fdrList);

}
