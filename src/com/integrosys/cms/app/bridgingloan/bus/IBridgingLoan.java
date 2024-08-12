package com.integrosys.cms.app.bridgingloan.bus;

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
public interface IBridgingLoan extends IValueObject, Serializable {

	public long getProjectID();

	public void setProjectID(long projectID);

	public long getLimitProfileID();

	public void setLimitProfileID(long limitProfileID);

	public long getLimitID();

	public void setLimitID(long limitID);

	public String getSourceLimit();

	public void setSourceLimit(String sourceLimit);

	public String getProductDescription();

	public void setProductDescription(String productDescription);

	public String getProjectNumber();

	public void setProjectNumber(String projectNumber);

	public Date getContractDate();

	public void setContractDate(Date contractDate);

	public Amount getContractAmount();

	public void setContractAmount(Amount contractAmount);

	public float getFinancePercent();

	public void setFinancePercent(float financePercent);

	public Amount getFinancedAmount();

	public String getCollectionAccount();

	public void setCollectionAccount(String collectionAccount);

	public String getHdaAccount();

	public void setHdaAccount(String hdaAccount);

	public String getProjectAccount();

	public void setProjectAccount(String projectAccount);

	public String getCurrentAccount();

	public void setCurrentAccount(String currentAccount);

	public Date getFullSettlementDate();

	public void setFullSettlementDate(Date fullSettlementDate);

	public Date getFullSettlementContractDate();

	public void setFullSettlementContractDate(Date fullSettlementContractDate);

	public int getNoOfTypes();

	public void setNoOfTypes(int noOfTypes);

	public int getNoOfUnits();

	public void setNoOfUnits(int noOfUnits);

	public Date getExpectedStartDate();

	public void setExpectedStartDate(Date expectedStartDate);

	public Date getExpectedCompletionDate();

	public void setExpectedCompletionDate(Date expectedCompletionDate);

	public Date getActualStartDate();

	public void setActualStartDate(Date actualStartDate);

	public Date getActualCompletionDate();

	public void setActualCompletionDate(Date actualCompletionDate);

	public Date getAvailabilityExpiryDate();

	public void setAvailabilityExpiryDate(Date availabilityExpiryDate);

	public String getBlRemarks();

	public void setBlRemarks(String remarks);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeletedInd);

	public IPropertyType[] getPropertyTypeList();

	public void setPropertyTypeList(IPropertyType[] propertyTypeList);

	public IProjectSchedule[] getProjectScheduleList();

	public void setProjectScheduleList(IProjectSchedule[] projectScheduleList);

	public IDisbursement[] getDisbursementList();

	public void setDisbursementList(IDisbursement[] disbursementList);

	public ISettlement[] getSettlementList();

	public void setSettlementList(ISettlement[] settlementList);

	public IBuildUp[] getBuildUpList();

	public void setBuildUpList(IBuildUp[] buildUpList);

	public IFDR[] getFdrList();

	public void setFdrList(IFDR[] fdrList);
}