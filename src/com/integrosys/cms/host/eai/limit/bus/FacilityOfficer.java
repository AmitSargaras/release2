package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

public class FacilityOfficer implements Serializable {

	private static final long serialVersionUID = 8163541761473917258L;

	private long id;

	private long facilityMasterId;

	private long cmsOfficerId;

	private Long cmsReferenceId;

	private StandardCode relationshipCode;

	private StandardCode officer;

	private StandardCode officerType;

	private String status;

	private String updateStatusIndicator;

	private String changeIndicator;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getCMSOfficerId() {
		return cmsOfficerId;
	}

	public Long getCmsReferenceId() {
		return this.cmsReferenceId;
	}

	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	public long getId() {
		return id;
	}

	public StandardCode getOfficer() {
		return officer;
	}

	public StandardCode getOfficerType() {
		return officerType;
	}

	public StandardCode getRelationshipCode() {
		return relationshipCode;
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

	public void setCMSOfficerId(long CMSOfficerId) {
		this.cmsOfficerId = CMSOfficerId;
	}

	public void setCmsReferenceId(Long cmsReferenceId) {
		this.cmsReferenceId = cmsReferenceId;
	}

	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOfficer(StandardCode officer) {
		this.officer = officer;
	}

	public void setOfficerType(StandardCode officerType) {
		this.officerType = officerType;
	}

	public void setRelationshipCode(StandardCode relationshipCode) {
		this.relationshipCode = relationshipCode;
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
		result = prime * result + ((officer == null) ? 0 : officer.hashCode());
		result = prime * result + ((officerType == null) ? 0 : officerType.hashCode());
		result = prime * result + ((relationshipCode == null) ? 0 : relationshipCode.hashCode());
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
		FacilityOfficer other = (FacilityOfficer) obj;
		if (officer == null) {
			if (other.officer != null) {
				return false;
			}
		}
		else if (!officer.equals(other.officer)) {
			return false;
		}
		if (officerType == null) {
			if (other.officerType != null) {
				return false;
			}
		}
		else if (!officerType.equals(other.officerType)) {
			return false;
		}
		if (relationshipCode == null) {
			if (other.relationshipCode != null) {
				return false;
			}
		}
		else if (!relationshipCode.equals(other.relationshipCode)) {
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
		StringBuffer buf = new StringBuffer("FacilityOfficer [");
		buf.append("officer=");
		buf.append(officer);
		buf.append(", officerType=");
		buf.append(officerType);
		buf.append(", relationshipCode=");
		buf.append(relationshipCode);
		buf.append(", facilityMasterId=");
		buf.append(facilityMasterId);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}
}
