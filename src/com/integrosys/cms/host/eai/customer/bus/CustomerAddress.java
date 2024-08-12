package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class CustomerAddress implements java.io.Serializable {

	private long cmsId;

	private long cmsCIFId;

	private String CIFId;

	private long addressId;

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String address5;

	private String postCode;

	private String country;

	private StandardCode addressType;

	private String state;

	private String city;

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public long getCmsCIFId() {
		return cmsCIFId;
	}

	public void setCmsCIFId(long cmsCIFId) {
		this.cmsCIFId = cmsCIFId;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public StandardCode getAddressType() {
		if (addressType != null && addressType.getStandardCodeNumber() == null) {
			addressType.setStandardCodeNumber("4");
		}

		return addressType;
	}

	public void setAddressType(StandardCode addressType) {
		this.addressType = addressType;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((CIFId == null) ? 0 : CIFId.hashCode());
		result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((address3 == null) ? 0 : address3.hashCode());
		result = prime * result + ((address4 == null) ? 0 : address4.hashCode());
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		result = prime * result + (int) (cmsCIFId ^ (cmsCIFId >>> 32));
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());

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

		CustomerAddress other = (CustomerAddress) obj;
		if (CIFId == null) {
			if (other.CIFId != null) {
				return false;
			}
		}
		else if (!CIFId.equals(other.CIFId)) {
			return false;
		}

		if (address1 == null) {
			if (other.address1 != null) {
				return false;
			}
		}
		else if (!address1.equals(other.address1)) {
			return false;
		}

		if (address2 == null) {
			if (other.address2 != null) {
				return false;
			}
		}
		else if (!address2.equals(other.address2)) {
			return false;
		}

		if (address3 == null) {
			if (other.address3 != null) {
				return false;
			}
		}
		else if (!address3.equals(other.address3)) {
			return false;
		}

		if (address4 == null) {
			if (other.address4 != null) {
				return false;
			}
		}
		else if (!address4.equals(other.address4)) {
			return false;
		}

		if (addressType == null) {
			if (other.addressType != null) {
				return false;
			}
		}
		else if (!addressType.equals(other.addressType)) {
			return false;
		}

		if (cmsCIFId != other.cmsCIFId) {
			return false;
		}

		if (country == null) {
			if (other.country != null) {
				return false;
			}
		}
		else if (!country.equals(other.country)) {
			return false;
		}

		if (postCode == null) {
			if (other.postCode != null) {
				return false;
			}
		}
		else if (!postCode.equals(other.postCode)) {
			return false;
		}

		return true;
	}

	public String toString() {
		return getClass().getName() + " CIF Id [" + CIFId + "] Address1 [" + address1 + "] Address2 [" + address2
				+ "] Address3 [" + address3 + "] Address4 [" + address4 + "] Address Type [" + addressType
				+ "] country [" + country + "] post code [" + postCode + "]";
	}

}
