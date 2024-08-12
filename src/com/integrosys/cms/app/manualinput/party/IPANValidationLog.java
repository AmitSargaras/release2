package com.integrosys.cms.app.manualinput.party;

import java.io.Serializable;
import java.util.Date;

	public interface IPANValidationLog extends Serializable {
		
		public long getId();
		public void setId(long id);
		
		public String getPanNo();
		public void setPanNo(String panNo);
		
		public String getPartyID();
		public void setPartyID(String partyID);
		
		public Long getCmsMainProfileID();
		public void setCmsMainProfileID(Long cmsMainProfileID);
		
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
		
		public String getStatus();
		public void setStatus(String status);
		
		public Date getLastValidatedDate();
		public void setLastValidatedDate(Date lastValidatedDate);
		
		public String getLastValidatedBy();
		public void setLastValidatedBy(String lastValidatedBy);
		
		public char getIsPANNoValidated(); ;
		public void setIsPANNoValidated(char isPANNoValidated);
		
		public String getRequestNo();
		public void setRequestNo(String requestNo);
		
		public String getPartyNameAsPerPan();
		public void setPartyNameAsPerPan(String partyNameAsPerPan);
		
		public String getDateOfIncorporation();
		public void setDateOfIncorporation(String dateOfIncorporation);
		
}
