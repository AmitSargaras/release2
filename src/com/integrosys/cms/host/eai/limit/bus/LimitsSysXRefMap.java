package com.integrosys.cms.host.eai.limit.bus;

/**
 * <p>
 * Entity to represent the mapping between a account and a limit/facility.
 * <p>
 * This mean it is many-to-many mapping between account and limit/facility.
 * 
 * @author Chong Jun Yong
 */
public class LimitsSysXRefMap implements java.io.Serializable {

	private static final long serialVersionUID = -7784788159382972694L;

	private Long cmsId;

	private Long cmsLimitId;

	private Long cmsSystemXRefId;

	private Long referenceId;

	private String cifId;

	private Long subProfileId;

	private String status;

	public String getCifId() {
		return cifId;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public Long getCmsLimitId() {
		return cmsLimitId;
	}

	public Long getCmsSystemXRefId() {
		return cmsSystemXRefId;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public String getStatus() {
		return status;
	}

	public Long getSubProfileId() {
		return subProfileId;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public void setCmsLimitId(Long cmsLimitId) {
		this.cmsLimitId = cmsLimitId;
	}

	public void setCmsSystemXRefId(Long cmsSystemXRefId) {
		this.cmsSystemXRefId = cmsSystemXRefId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubProfileId(Long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmsLimitId == null) ? 0 : cmsLimitId.hashCode());
		result = prime * result + ((cmsSystemXRefId == null) ? 0 : cmsSystemXRefId.hashCode());
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
		LimitsSysXRefMap other = (LimitsSysXRefMap) obj;
		if (cmsLimitId == null) {
			if (other.cmsLimitId != null) {
				return false;
			}
		}
		else if (!cmsLimitId.equals(other.cmsLimitId)) {
			return false;
		}
		if (cmsSystemXRefId == null) {
			if (other.cmsSystemXRefId != null) {
				return false;
			}
		}
		else if (!cmsSystemXRefId.equals(other.cmsSystemXRefId)) {
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
		StringBuffer buf = new StringBuffer("Limit - Account Map : ");
		buf.append("CMS Limit Id [").append(this.cmsLimitId).append("], ");
		buf.append("CMS SystemXRef Id [").append(this.cmsSystemXRefId).append("], ");
		buf.append("Status [").append(this.status).append("]");

		return buf.toString();
	}
}
