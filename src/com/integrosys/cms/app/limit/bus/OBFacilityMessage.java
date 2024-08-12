package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * An entity represent a message regards a corporate loan facility.
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityMessage implements Serializable {

	private static final long serialVersionUID = 1585823767720922544L;

	private Long id;

	/** CMS reference id between actual and staging copy */
	private Long cmsReferenceId;

	private Long cmsFacilityMasterId;

	private String message;

	private String messageTypeCodeCategoryCode;

	private String messageTypeCodeEntryCode;

	private String messageSeverityCodeCategoryCode;

	private String messageSeverityCodeEntryCode;

	private Date expirationDate;

	/** Sequence number sent from host system */
	private Long sequenceNumber;

	/** CMS internal status to determine whether this to be shown on the screen */
	private String status;

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
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

	public String getMessageSeverityCodeCategoryCode() {
		return messageSeverityCodeCategoryCode;
	}

	public String getMessageSeverityCodeEntryCode() {
		return messageSeverityCodeEntryCode;
	}

	public String getMessageTypeCodeCategoryCode() {
		return messageTypeCodeCategoryCode;
	}

	public String getMessageTypeCodeEntryCode() {
		return messageTypeCodeEntryCode;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
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

	public void setMessageSeverityCodeCategoryCode(String messageSeverityCodeCategoryCode) {
		this.messageSeverityCodeCategoryCode = messageSeverityCodeCategoryCode;
	}

	public void setMessageSeverityCodeEntryCode(String messageSeverityCodeEntryCode) {
		this.messageSeverityCodeEntryCode = messageSeverityCodeEntryCode;
	}

	public void setMessageTypeCodeCategoryCode(String messageTypeCodeCategoryCode) {
		this.messageTypeCodeCategoryCode = messageTypeCodeCategoryCode;
	}

	public void setMessageTypeCodeEntryCode(String messageTypeCodeEntryCode) {
		this.messageTypeCodeEntryCode = messageTypeCodeEntryCode;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((messageSeverityCodeCategoryCode == null) ? 0 : messageSeverityCodeCategoryCode.hashCode());
		result = prime * result
				+ ((messageSeverityCodeEntryCode == null) ? 0 : messageSeverityCodeEntryCode.hashCode());
		result = prime * result + ((messageTypeCodeCategoryCode == null) ? 0 : messageTypeCodeCategoryCode.hashCode());
		result = prime * result + ((messageTypeCodeEntryCode == null) ? 0 : messageTypeCodeEntryCode.hashCode());
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
		OBFacilityMessage other = (OBFacilityMessage) obj;
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
		if (messageSeverityCodeCategoryCode == null) {
			if (other.messageSeverityCodeCategoryCode != null) {
				return false;
			}
		}
		else if (!messageSeverityCodeCategoryCode.equals(other.messageSeverityCodeCategoryCode)) {
			return false;
		}
		if (messageSeverityCodeEntryCode == null) {
			if (other.messageSeverityCodeEntryCode != null) {
				return false;
			}
		}
		else if (!messageSeverityCodeEntryCode.equals(other.messageSeverityCodeEntryCode)) {
			return false;
		}
		if (messageTypeCodeCategoryCode == null) {
			if (other.messageTypeCodeCategoryCode != null) {
				return false;
			}
		}
		else if (!messageTypeCodeCategoryCode.equals(other.messageTypeCodeCategoryCode)) {
			return false;
		}
		if (messageTypeCodeEntryCode == null) {
			if (other.messageTypeCodeEntryCode != null) {
				return false;
			}
		}
		else if (!messageTypeCodeEntryCode.equals(other.messageTypeCodeEntryCode)) {
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
		StringBuffer buf = new StringBuffer("OBFacilityMessage [id=");
		buf.append(id);
		buf.append(", cmsFacilityMasterId=");
		buf.append(cmsFacilityMasterId);
		buf.append(", cmsReferenceId=");
		buf.append(cmsReferenceId);
		buf.append(", expirationDate=");
		buf.append(expirationDate);
		buf.append(", message=");
		buf.append(message);
		buf.append(", messageSeverityCodeCategoryCode=");
		buf.append(messageSeverityCodeCategoryCode);
		buf.append(", messageSeverityCodeEntryCode=");
		buf.append(messageSeverityCodeEntryCode);
		buf.append(", messageTypeCodeCategoryCode=");
		buf.append(messageTypeCodeCategoryCode);
		buf.append(", messageTypeCodeEntryCode=");
		buf.append(messageTypeCodeEntryCode);
		buf.append(", sequenceNumber=");
		buf.append(sequenceNumber);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

}