package com.integrosys.cms.ui.manualinput.customer;

import java.io.Serializable;
import java.util.Date;

	public interface IIfsccodeWsLog extends Serializable {
		
		public long getId();
		public void setId(long id);
		public String getPartyId();
		public void setPartyId(String partyId);
		
		public String getBankName();
		public void setBankName(String bankName);
	
		public String getBranchName();
		public void setBranchName(String branchName);
		
		public String getIfscCode();
		public void setIfscCode(String ifscCode);
		
		public String getRequestMessage();
		public void setRequestMessage(String requestMessage);
		
		public String getResponseMessage();
		public void setResponseMessage(String responseMessage);
		
		public Date getRequestDateTime();
		public void setRequestDateTime(Date requestDateTime);
		
		public Date getResponseDateTime();
		public void setResponseDateTime(Date responseDateTime);
		
		public String getResponseCode();
		public void setResponseCode(String responseCode);
		
		public String getErrorMessage();
		public void setErrorMessage(String errorMessage);
		
	
		
}
