package com.integrosys.cms.app.ecbf.limit;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface IECBFLimitInterfaceLog{
	
	List<String> copyProperties = Arrays.asList("lineCode","documentationAmount","finalLimitReleasable","revolvingLine","capitalMarketExposure",
			"realEstateExposure", "estateType", "commEstateType","ruleID","priority", "priorityFlag","uncondiCanclCommitment","lineId",
			"borrowerCustomerID","liabilityBranch","available", "borrowerAdditionDate","freeze","remarks","panNumber","action");
	
	public long getId();
	public void setId(long id);
	
	public String getLineCode();
	public void setLineCode(String lineCode);
	
	public String getDocumentationAmount();
	public void setDocumentationAmount(String documentationAmount);

	public String getFinalLimitReleasable();
	public void setFinalLimitReleasable(String finalLimitReleasable);

	public String getRevolvingLine();
	public void setRevolvingLine(String revolvingLine);

	public String getCapitalMarketExposure();
	public void setCapitalMarketExposure(String capitalMarketExposure);

	public String getRealEstateExposure();
	public void setRealEstateExposure(String realEstateExposure);
	
	public String getEstateType();
	public void setEstateType(String estateType);
	
	public String getEstateTypeValue();
	public void setEstateTypeValue(String estateTypeValue);

	public String getCommEstateType();
	public void setCommEstateType(String commEstateType);
	
	public String getCommEstateTypeValue();
	public void setCommEstateTypeValue(String commEstateTypeValue);
	
	public String getRuleID();
	public void setRuleID(String ruleID);
	
	public String getRuleIDValue();
	public void setRuleIDValue(String ruleIDValue);

	public String getPriority();
	public void setPriority(String priority);
	
	public String getPriorityValue();
	public void setPriorityValue(String priorityValue);
	
	public String getPriorityFlag();
	public void setPriorityFlag(String priorityFlag);

	public String getUncondiCanclCommitment();
	public void setUncondiCanclCommitment(String uncondiCanclCommitment) ;
	
	public String getUncondiCanclCommitmentValue();
	public void setUncondiCanclCommitmentValue(String uncondiCanclCommitmentValue);

	public String getBorrowerCustomerID();
	public void setBorrowerCustomerID(String borrowerCustomerID);

	public String getBorrowerAdditionDate();
	public void setBorrowerAdditionDate(String borrowerAdditionDate);

	public String getFreeze();
	public void setFreeze(String freeze);

	public String getRemarks();
	public void setRemarks(String remarks);

	public String getPanNumber();
	public void setPanNumber(String panNumber);

	public String getLiabilityBranch();
	public void setLiabilityBranch(String liabilityBranch);
	
	public String getLiabilityBranchValue();
	public void setLiabilityBranchValue(String liabilityBranchValue);

	public String getAvailable();
	public void setAvailable(String available);

	public String getUdfSequences();
	public void setUdfSequences(String udfSequences);
	
	public String getUdfNames();
	public void setUdfNames(String udfNames);
	
	public String getUdfValues();
	public void setUdfValues(String udfValues);

	public String getAction();
	public void setAction(String action);
	
	public String getPartyId();
	public void setPartyId(String partyId);
	
	public String getErrorCode();
	public void setErrorCode(String errorCode);

	public Date getRequestDateTime();
	public void setRequestDateTime(Date requestDateTime);

	public Date getResponseDateTime();
	public void setResponseDateTime(Date responseDateTime);

	public String getErrorMessage();
	public void setErrorMessage(String errorMessage) ;

	public String getRequestMessage();
	public void setRequestMessage(String requestMessage);

	public String getResponseMessage();
	public void setResponseMessage(String responseMessage) ;

	public String getStatus();
	public void setStatus(String status);
	
	public String getLmtId();
	public void setLmtId(String lmtId);

	public String getLineId();
	public void setLineId(String lineId);
	
}