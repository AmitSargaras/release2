package com.integrosys.cms.host.eai.security.bus;

import java.util.Vector;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class Pledgor implements java.io.Serializable {

	private static final long serialVersionUID = -8911189475247505659L;

	private long cmsId;

	private long CMSPledgorId;

	private String pledgorId; // CIF Id

	private String CIF;

	private CustomerIdInfo idInfo;

	private StandardCode relationship;

	private String pledgorCIFSource;

	private String pledgorLegalName;

	private String incorporatedCountry;

	private String incorporationNumber;

	private StandardCode idType;

	private String updateStatusIndicator;

	private String changeIndicator;

	private Vector creditGrade;

	private String pledgorRelTypeNumber = "34";

	private String pledgorRelTypeValue;

	private String originatingCountry;

	private String originatingOrganisation;

	private StandardCode legalType;

	private String sourceID;

	/**
	 * Default constructor.
	 */
	public Pledgor() {
		super();
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getCIF() {
		return CIF;
	}

	public long getCmsId() {
		return cmsId;
	}

	public long getCMSPledgorId() {
		return CMSPledgorId;
	}

	public Vector getCreditGrade() {
		return creditGrade;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public CustomerIdInfo getIdInfo() {
		return idInfo;
	}

	public StandardCode getIdType() {
		return idType;
	}

	public String getIncorporatedCountry() {
		return incorporatedCountry;
	}

	public String getIncorporationNumber() {
		return incorporationNumber;
	}

	public String getPledgorCIFSource() {
		return pledgorCIFSource;
	}

	public String getPledgorId() {
		return pledgorId;
	}

	public String getPledgorLegalName() {
		return pledgorLegalName;
	}

	public String getPledgorRelTypeNumber() {
		return pledgorRelTypeNumber;
	}

	public String getPledgorRelTypeValue() {
		return pledgorRelTypeValue;
	}

	public StandardCode getRelationship() {
		return relationship;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCIF(String cif) {
		CIF = cif;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public void setCMSPledgorId(long pledgorId) {
		CMSPledgorId = pledgorId;
	}

	public void setCreditGrade(Vector creditGrade) {
		this.creditGrade = creditGrade;
	}

	public void setIdInfo(CustomerIdInfo idInfo) {
		this.idInfo = idInfo;
	}

	public void setIdType(StandardCode idType) {
		this.idType = idType;
	}

	public void setIncorporatedCountry(String incorporatedCountry) {
		this.incorporatedCountry = incorporatedCountry;
	}

	public void setIncorporationNumber(String incorporationNumber) {
		this.incorporationNumber = incorporationNumber;
	}

	public void setPledgorCIFSource(String pledgorCIFSource) {
		this.pledgorCIFSource = pledgorCIFSource;
	}

	public void setPledgorId(String pledgorId) {
		this.pledgorId = pledgorId;
	}

	public void setPledgorLegalName(String pledgorLegalName) {
		this.pledgorLegalName = pledgorLegalName;
	}

	public void setPledgorRelTypeNumber(String pledgorRelTypeNumber) {
		this.pledgorRelTypeNumber = pledgorRelTypeNumber;
	}

	public void setPledgorRelTypeValue(String pledgorRelTypeValue) {
		this.pledgorRelTypeValue = pledgorRelTypeValue;
	}

	public void setRelationship(StandardCode relationship) {
		this.relationship = relationship;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	public String getOriginatingOrganisation() {
		return originatingOrganisation;
	}

	public void setOriginatingOrganisation(String originatingOrganisation) {
		this.originatingOrganisation = originatingOrganisation;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public StandardCode getLegalType() {
		return legalType;
	}

	public void setLegalType(StandardCode legalType) {
		this.legalType = legalType;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(this.hashCode());
		buf.append(" CIF # [").append(CIF).append("], ");
		buf.append(" ID Info [").append(idInfo).append("], ");
		buf.append(" CIF Source [").append(pledgorCIFSource).append("], ");
		buf.append(" Legal Name [").append(pledgorLegalName).append("]");

		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CIF == null) ? 0 : CIF.hashCode());
		result = prime * result + ((idInfo == null) ? 0 : idInfo.hashCode());
		result = prime * result + ((pledgorCIFSource == null) ? 0 : pledgorCIFSource.hashCode());
		result = prime * result + ((pledgorLegalName == null) ? 0 : pledgorLegalName.hashCode());
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
		Pledgor other = (Pledgor) obj;
		if (CIF == null) {
			if (other.CIF != null) {
				return false;
			}
		}
		else if (!CIF.equals(other.CIF)) {
			return false;
		}
		if (idInfo == null) {
			if (other.idInfo != null) {
				return false;
			}
		}
		else if (!idInfo.equals(other.idInfo)) {
			return false;
		}

		if (pledgorCIFSource == null) {
			if (other.pledgorCIFSource != null) {
				return false;
			}
		}
		else if (!pledgorCIFSource.equals(other.pledgorCIFSource)) {
			return false;
		}
		if (pledgorLegalName == null) {
			if (other.pledgorLegalName != null) {
				return false;
			}
		}
		else if (!pledgorLegalName.equals(other.pledgorLegalName)) {
			return false;
		}
		return true;
	}
}
