package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * An entity represents a reduction of facility, for corporate loan.
 * @author Chong Jun Yong
 * 
 */
public class FacilityReduction implements Serializable {

	private static final long serialVersionUID = 2145923137631633018L;

	private Long id;

	private Long cmsReferenceId;

	private Long cmsFacilityMasterId;

	private String status;

	private Integer incrementalNumber;

	private Double amountApplied;

	private Double incrementalLimit;

	private Double originalLimit;

	private Date applicationDate;

	private Date dateOfferAccepted;

	private Date dateApproved;

	private StandardCode approvedBy;

	private StandardCode cancelRejectCode;

	private Date cancelRejectDate;

	private String facilityStatusCategoryCode;

	private String facilityStatus;

	private String solicitorName;

	private StandardCode requestReason;

	private StandardCode documentationStatus;

	private StandardCode lawyerCode;

	private String updateStatusIndicator;

	private String changeIndicator;

	public Double getAmountApplied() {
		return amountApplied;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public StandardCode getApprovedBy() {
		return approvedBy;
	}

	public StandardCode getCancelRejectCode() {
		return cancelRejectCode;
	}

	public Date getCancelRejectDate() {
		return cancelRejectDate;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Long getCmsFacilityMasterId() {
		return cmsFacilityMasterId;
	}

	public Long getCmsReferenceId() {
		return cmsReferenceId;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public Date getDateOfferAccepted() {
		return dateOfferAccepted;
	}

	public StandardCode getDocumentationStatus() {
		return documentationStatus;
	}

	public String getFacilityStatusCategoryCode() {
		return facilityStatusCategoryCode;
	}

	public String getFacilityStatus() {
		return facilityStatus;
	}

	public Long getId() {
		return id;
	}

	public Double getIncrementalLimit() {
		return incrementalLimit;
	}

	public Integer getIncrementalNumber() {
		return incrementalNumber;
	}

	public StandardCode getLawyerCode() {
		return lawyerCode;
	}

	public Double getOriginalLimit() {
		return originalLimit;
	}

	public StandardCode getRequestReason() {
		return requestReason;
	}

	public String getSolicitorName() {
		return solicitorName;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAmountApplied(Double amountApplied) {
		this.amountApplied = amountApplied;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setApprovedBy(StandardCode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setCancelRejectCode(StandardCode cancelRejectCode) {
		this.cancelRejectCode = cancelRejectCode;
	}

	public void setCancelRejectDate(Date cancelRejectDate) {
		this.cancelRejectDate = cancelRejectDate;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCmsFacilityMasterId(Long cmsFacilityMasterId) {
		this.cmsFacilityMasterId = cmsFacilityMasterId;
	}

	public void setCmsReferenceId(Long cmsReferenceId) {
		this.cmsReferenceId = cmsReferenceId;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public void setDateOfferAccepted(Date dateOfferAccepted) {
		this.dateOfferAccepted = dateOfferAccepted;
	}

	public void setDocumentationStatus(StandardCode documentationStatus) {
		this.documentationStatus = documentationStatus;
	}

	public void setFacilityStatusCategoryCode(String facilityStatusCategoryCode) {
		this.facilityStatusCategoryCode = facilityStatusCategoryCode;
	}

	public void setFacilityStatus(String facilityStatus) {
		this.facilityStatus = facilityStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIncrementalLimit(Double incrementalLimit) {
		this.incrementalLimit = incrementalLimit;
	}

	public void setIncrementalNumber(Integer incrementalNumber) {
		this.incrementalNumber = incrementalNumber;
	}

	public void setLawyerCode(StandardCode lawyerCode) {
		this.lawyerCode = lawyerCode;
	}

	public void setOriginalLimit(Double originalLimit) {
		this.originalLimit = originalLimit;
	}

	public void setRequestReason(StandardCode requestReason) {
		this.requestReason = requestReason;
	}

	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
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
		result = prime * result + ((applicationDate == null) ? 0 : applicationDate.hashCode());
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
		FacilityReduction other = (FacilityReduction) obj;
		if (applicationDate == null) {
			if (other.applicationDate != null) {
				return false;
			}
		}
		else if (!applicationDate.equals(other.applicationDate)) {
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
		StringBuffer buf = new StringBuffer("FacilityReduction [");
		buf.append("applicationDate=");
		buf.append(applicationDate);
		buf.append(", incrementalNumber=");
		buf.append(incrementalNumber);
		buf.append(", incrementalLimit=");
		buf.append(incrementalLimit);
		buf.append(", originalLimit=");
		buf.append(originalLimit);
		buf.append(", amountApplied=");
		buf.append(amountApplied);
		buf.append(", requestReason=");
		buf.append(requestReason);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

}