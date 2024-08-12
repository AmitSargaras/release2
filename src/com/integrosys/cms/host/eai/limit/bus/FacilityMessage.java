package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * An entity represent a message regards a corporate loan facility.
 * @author Chong Jun Yong
 * 
 */
public class FacilityMessage implements Serializable {

	private static final long serialVersionUID = -5476790037217592904L;

	private Long id;

	/** CMS PK passed back from LOS system */
	private Long cmsFacilityMessageId;

	/** CMS reference id between actual and staging copy */
	private Long cmsReferenceId;

	private Long cmsFacilityMasterId;

	private String message;

	private StandardCode messageTypeCode;

	private StandardCode messageSeverityCode;

	private Date expirationDate;

	/** Sequence number sent from host system */
	private Long sequenceNumber;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	private String updateStatusIndicator;

	private String changeIndicator;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public Long getCmsFacilityMessageId() {
		return cmsFacilityMessageId;
	}

	public Long getCmsReferenceId() {
		return cmsReferenceId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public StandardCode getMessageSeverityCode() {
		return messageSeverityCode;
	}

	public StandardCode getMessageTypeCode() {
		return messageTypeCode;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public void setCmsFacilityMessageId(Long cmsFacilityMessageId) {
		this.cmsFacilityMessageId = cmsFacilityMessageId;
	}

	public void setCmsReferenceId(Long cmsReferenceId) {
		this.cmsReferenceId = cmsReferenceId;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessageSeverityCode(StandardCode messageSeverityCode) {
		this.messageSeverityCode = messageSeverityCode;
	}

	public void setMessageTypeCode(StandardCode messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageSeverityCode == null) ? 0 : messageSeverityCode.hashCode());
		result = prime * result + ((messageTypeCode == null) ? 0 : messageTypeCode.hashCode());
		result = prime * result + ((sequenceNumber == null) ? 0 : sequenceNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		FacilityMessage other = (FacilityMessage) obj;
		if (expirationDate == null) {
			if (other.expirationDate != null) {
				return false;
			}
		}
		else if (!expirationDate.equals(other.expirationDate)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		}
		else if (!message.equals(other.message)) {
			return false;
		}
		if (messageSeverityCode == null) {
			if (other.messageSeverityCode != null) {
				return false;
			}
		}
		else if (!messageSeverityCode.equals(other.messageSeverityCode)) {
			return false;
		}
		if (messageTypeCode == null) {
			if (other.messageTypeCode != null) {
				return false;
			}
		}
		else if (!messageTypeCode.equals(other.messageTypeCode)) {
			return false;
		}
		if (sequenceNumber == null) {
			if (other.sequenceNumber != null) {
				return false;
			}
		}
		else if (!sequenceNumber.equals(other.sequenceNumber)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Facility Message [");
		buf.append("Id=").append(id);
		buf.append(", CMS facility message id=").append(cmsFacilityMessageId);
		buf.append(", Message=").append(message);
		buf.append(", Message Type=").append(messageTypeCode);
		buf.append(", Message Severity=").append(messageSeverityCode);
		buf.append(", Expiration Date=").append(expirationDate);
		buf.append("]");
		return buf.toString();
	}
}
