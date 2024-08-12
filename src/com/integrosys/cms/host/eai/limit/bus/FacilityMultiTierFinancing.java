package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * To map the FacilityMultiTierFinancing portion of the CA001 message.
 * @author Thurein
 * 
 */
public class FacilityMultiTierFinancing implements Serializable {

	private static final long serialVersionUID = -4220046320349379373L;

	private long id;

	private Integer tierTerm;

	private Integer tierSeqNo;

	private StandardCode tierTermCode;

	private Double rate;

	private Double newPaymentAmt;

	private long facilityMasterId;

	private long refID;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String status;

	private String gppIndicator;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	public String getGppIndicator() {
		return gppIndicator;
	}

	public long getId() {
		return id;
	}

	public Double getNewPaymentAmt() {
		return newPaymentAmt;
	}

	public Double getRate() {
		return rate;
	}

	public long getRefID() {
		return refID;
	}

	public String getStatus() {
		return status;
	}

	public Integer getTierSeqNo() {
		return tierSeqNo;
	}

	public Integer getTierTerm() {
		return tierTerm;
	}

	public StandardCode getTierTermCode() {
		return tierTermCode;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	public void setGppIndicator(String gppIndicator) {
		this.gppIndicator = gppIndicator;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNewPaymentAmt(Double newPaymentAmt) {
		this.newPaymentAmt = newPaymentAmt;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public void setRefID(long refID) {
		this.refID = refID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTierSeqNo(Integer tierSeqNo) {
		this.tierSeqNo = tierSeqNo;
	}

	public void setTierTerm(Integer tierTerm) {
		this.tierTerm = tierTerm;
	}

	public void setTierTermCode(StandardCode tierTermCode) {
		this.tierTermCode = tierTermCode;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tierSeqNo == null) ? 0 : tierSeqNo.hashCode());
		result = prime * result + ((tierTerm == null) ? 0 : tierTerm.hashCode());
		result = prime * result + ((tierTermCode == null) ? 0 : tierTermCode.getStandardCodeValue().hashCode());
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
		FacilityMultiTierFinancing other = (FacilityMultiTierFinancing) obj;
		if (rate == null) {
			if (other.rate != null) {
				return false;
			}
		}
		else if (!rate.equals(other.rate)) {
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

		if (tierSeqNo == null) {
			if (other.tierSeqNo != null) {
				return false;
			}
		}
		else if (!tierSeqNo.equals(other.tierSeqNo)) {
			return false;
		}

		if (tierTerm == null) {
			if (other.tierTerm != null) {
				return false;
			}
		}
		else if (!tierTerm.equals(other.tierTerm)) {
			return false;
		}

		if (tierTermCode == null) {
			if (other.tierTermCode != null) {
				return false;
			}
		}
		else if (!tierTermCode.getStandardCodeValue().equals(other.tierTermCode.getStandardCodeValue())) {
			return false;
		}
		return true;
	}

}
