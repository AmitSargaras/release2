package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class SubProfile implements java.io.Serializable {
	private String FIIndicator;

	private long cmsId;

	private String cifId;

	private long cmsMainProfileId;

	private long subProfileId = 1;

	private String subProfileName;

	private String originatingCountry;

	private String originatingOrganisation;

	private char nonBorrowerIndicator;
	
	private String  domicileCountry;

	private Character updateStatusIndicator;

	private Character changeIndicator;

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public String getCifId() {
		return cifId;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public long getCmsMainProfileId() {
		return cmsMainProfileId;
	}

	public void setCmsMainProfileId(long cmsMainProfileId) {
		this.cmsMainProfileId = cmsMainProfileId;
	}

	public long getSubProfileId() {
		return subProfileId;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public String getSubProfileName() {
		return subProfileName;
	}

	public void setSubProfileName(String subProfileName) {
		this.subProfileName = subProfileName;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	public String getOriginatingOrganisation() {
		if ((originatingOrganisation == null) || "".equals(originatingOrganisation)) {
			return "--";
		}
		return originatingOrganisation;
	}

	public void setOriginatingOrganisation(String originatingOrganisation) {
		this.originatingOrganisation = originatingOrganisation;
	}

	public char getNonBorrowerIndicator() {
		if ("".equals(String.valueOf(nonBorrowerIndicator).trim())) {
			nonBorrowerIndicator = ICMSConstant.TRUE_VALUE.charAt(0);
		}
		return nonBorrowerIndicator;
	}

	public void setNonBorrowerIndicator(char nonBorrowerIndicator) {
		this.nonBorrowerIndicator = nonBorrowerIndicator;
	}

	public Character getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(Character updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public Character getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(Character changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	/**
	 * @return the fIIndicator
	 */
	public String getFIIndicator() {
		return FIIndicator;
	}

	/**
	 * @param indicator the fIIndicator to set
	 */
	public void setFIIndicator(String indicator) {
		FIIndicator = indicator;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((cifId == null) ? 0 : cifId.hashCode());
		result = prime * result + (int) (cmsMainProfileId ^ (cmsMainProfileId >>> 32));
		result = prime * result + ((originatingCountry == null) ? 0 : originatingCountry.hashCode());
		result = prime * result + ((originatingOrganisation == null) ? 0 : originatingOrganisation.hashCode());
		result = prime * result + (int) (subProfileId ^ (subProfileId >>> 32));

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

		SubProfile other = (SubProfile) obj;
		if (cifId == null) {
			if (other.cifId != null) {
				return false;
			}
		}
		else if (!cifId.equals(other.cifId)) {
			return false;
		}

		if (cmsMainProfileId != other.cmsMainProfileId) {
			return false;
		}

		if (originatingCountry == null) {
			if (other.originatingCountry != null) {
				return false;
			}
		}
		else if (!originatingCountry.equals(other.originatingCountry)) {
			return false;
		}

		if (originatingOrganisation == null) {
			if (other.originatingOrganisation != null) {
				return false;
			}
		}
		else if (!originatingOrganisation.equals(other.originatingOrganisation)) {
			return false;
		}

		if (subProfileId != other.subProfileId) {
			return false;
		}

		return true;
	}

	public String getDomicileCountry() {
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public String toString() {
		return getClass().getName() + " CIF Id [" + cifId + "] originating country [" + originatingCountry + "]";
	}
}
