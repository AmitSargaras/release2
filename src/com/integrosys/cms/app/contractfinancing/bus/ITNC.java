package com.integrosys.cms.app.contractfinancing.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ITNC extends Serializable {

	public long getTncID();

	public void setTncID(long tncID);

	// public long getContractID();
	//
	// public void setContractID(long contractID);

	public String getTerms();

	public void setTerms(String terms);

	public String getTermsOthers();

	public void setTermsOthers(String termsOthers);

	public Date getTncDate();

	public void setTncDate(Date tncDate);

	public String getConditions();

	public void setConditions(String conditions);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);

	String getStatus();

	void setStatus(String status);
}
