package com.integrosys.cms.host.eai.document.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Document CheckList Item
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CheckListItem implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private long cmsDocItemID = ICMSConstant.LONG_INVALID_VALUE;;

	private long checklistID;

	private String docCode;

	private long docNo = ICMSConstant.LONG_INVALID_VALUE;

	private char mandatoryInd;

	private String description;

	private String status;

	private Date expiryDate;

	private Date receivedDate;

	private Date waivedDate;

	private char isDeleted;

	private boolean isPreApprove;

	private String updateStatusIndicator;

	private String changeIndicator;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getChecklistID() {
		return checklistID;
	}

	public long getCmsDocItemID() {
		return cmsDocItemID;
	}

	public String getDescription() {
		return description;
	}

	public String getDocCode() {
		return docCode;
	}

	public long getDocNo() {
		return docNo;
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(expiryDate);
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public Date getJDOExpiryDate() {
		return expiryDate;
	}

	public Date getJDOReceivedDate() {
		return receivedDate;
	}

	public Date getJDOWaivedDate() {
		return waivedDate;
	}

	public char getMandatoryInd() {
		return mandatoryInd;
	}

	public String getReceivedDate() {
		return MessageDate.getInstance().getString(receivedDate);
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getWaivedDate() {
		return MessageDate.getInstance().getString(waivedDate);
	}

	public boolean isInsertItem() {
		if ("Y".equalsIgnoreCase(getChangeIndicator()) && "I".equalsIgnoreCase(getUpdateStatusIndicator()))
			return true;
		else
			return false;
	}

	public boolean isPreApprove() {
		return isPreApprove;
	}

	public boolean isUpdateItem() {
		if ("Y".equalsIgnoreCase(getChangeIndicator()) && "U".equalsIgnoreCase(getUpdateStatusIndicator()))
			return true;
		else
			return false;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setChecklistID(long checklistID) {
		this.checklistID = checklistID;
	}

	public void setCmsDocItemID(long cmsDocItemID) {
		this.cmsDocItemID = cmsDocItemID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public void setDocNo(long docNo) {
		this.docNo = docNo;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expiryDate);
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setJDOExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setJDOReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setJDOWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}

	public void setMandatoryInd(char mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}

	public void setPreApprove(boolean isPreApprove) {
		this.isPreApprove = isPreApprove;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = MessageDate.getInstance().getDate(receivedDate);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setWaivedDate(String waivedDate) {
		this.waivedDate = MessageDate.getInstance().getDate(waivedDate);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + (int) (checklistID ^ (checklistID >>> 32));
		result = prime * result + (int) (cmsDocItemID ^ (cmsDocItemID >>> 32));
		result = prime * result + ((docCode == null) ? 0 : docCode.hashCode());
		result = prime * result + (int) (docNo ^ (docNo >>> 32));

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		CheckListItem other = (CheckListItem) obj;
		if (checklistID != other.checklistID) {
			return false;
		}

		if (cmsDocItemID != other.cmsDocItemID) {
			return false;
		}

		if (docCode == null) {
			if (other.docCode != null) {
				return false;
			}
		}
		else if (!docCode.equals(other.docCode)) {
			return false;
		}

		if (docNo != other.docNo) {
			return false;
		}

		return true;
	}
}
