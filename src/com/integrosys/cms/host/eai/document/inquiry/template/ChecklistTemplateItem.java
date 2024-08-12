package com.integrosys.cms.host.eai.document.inquiry.template;

import java.util.Date;

import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class ChecklistTemplateItem implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private String docCode;

	private String mandatoryInd;

	private String description;

	private Date expiryDate;

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getMandatoryInd() {
		return mandatoryInd;
	}

	public void setMandatoryInd(String mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(this.expiryDate);
	}

	public Date getJDOExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expiryDate);
	}

	public void setJDOExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
