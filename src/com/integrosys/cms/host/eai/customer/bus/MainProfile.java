package com.integrosys.cms.host.eai.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @author Thurein
 * @since 1.0
 */
public class MainProfile implements java.io.Serializable {

	private String FIIndicator;

	private String sciSearchId;

	private String CIFId;

	private long cmsId;

	private boolean hasLeId;

	private java.lang.String customerNameShort;

	private java.lang.String customerNameLong;

	private StandardCode customerClass;

	private String domicileCountry;

	private String incorporatedCountry;

	private java.lang.String incorporationNumber;

	private Date incorporationDate;

	private StandardCode customerType;

	private Date customerStartDate;

	private StandardCode customerSegment;

	private StandardCode baselCustomerSegment;

	private StandardCode baselCustomerSubSegment;

	private String blacklisted;

	private StandardCode natureOfBiz;

	private String accountOfficerID;

	private String accountOfficerName;

	private java.util.Vector idInfo;

	private java.util.Vector address;

	private String bizGroup = "";

	private long TFA;

	private StandardCode incomeGroup;

	private StandardCode bizSector;

	private StandardCode idType;

	private String idNo = "";

	private String oldIdNo = "";

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String postcode;

	private String country;

	private StandardCode countryPermanent;

	private StandardCode language;

	private String officeAddress1;

	private String officeAddress2;

	private String officeAddress3;

	private String officeAddress4;

	private String officePostcode;

	private String source;

	private Character updateStatusIndicator;

	private Character changeIndicator;

	private long subProfilePrimaryKey;

	private CustomerIdInfo idInfo1;

	private CustomerIdInfo idInfo2;

	private CustomerIdInfo idInfo3;

	private StandardCode customerBranch;
	
	private String leiCode;
	
	private Date leiExpDate;

	public long getSubProfilePrimaryKey() {
		return subProfilePrimaryKey;
	}

	public void setSubProfilePrimaryKey(long subProfilePrimaryKey) {
		this.subProfilePrimaryKey = subProfilePrimaryKey;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public boolean isHasLeId() {
		return hasLeId;
	}

	public void setHasLeId(boolean hasLeId) {
		this.hasLeId = hasLeId;
	}

	public String getCustomerNameLong() {
		return customerNameLong;
	}

	public void setCustomerNameLong(String customerNameLong) {
		this.customerNameLong = (customerNameLong == null ? null : customerNameLong.toUpperCase());
	}

	public String getCustomerNameShort() {
		return customerNameShort;
	}

	public void setCustomerNameShort(String customerNameShort) {
		this.customerNameShort = (customerNameShort == null ? null : customerNameShort.toUpperCase());
	}

	public StandardCode getCustomerClass() {
		if ((customerClass != null) && (customerClass.getStandardCodeNumber() == null)) {
			customerClass.setStandardCodeNumber("56");
		}
		return customerClass;
	}

	public void setCustomerClass(StandardCode customerClass) {
		this.customerClass = customerClass;
	}

	public String getIncorporatedCountry() {
		// Get incorporate country from properties and map to Country ISO
		if ((incorporatedCountry != null) && (incorporatedCountry.length() > 2)) {
			incorporatedCountry = PropertyManager.getValue(IEaiConstant.GCIF_COUNTRY_PREFIX
					+ incorporatedCountry.trim());
		}
		return incorporatedCountry;
	}

	public void setIncorporatedCountry(String incorporatedCountry) {
		this.incorporatedCountry = incorporatedCountry;
	}

	public String getIncorporationNumber() {
		return incorporationNumber;
	}

	public void setIncorporationNumber(String incorporationNumber) {
		this.incorporationNumber = incorporationNumber;
	}

	public String getIncorporationDate() {
		return MessageDate.getInstance().getString(this.incorporationDate);
	}

	public Date getJDOIncorporationDate() {
		return this.incorporationDate;
	}

	public void setIncorporationDate(String incorporationDate) {
		this.incorporationDate = MessageDate.getInstance().getDate(incorporationDate);
	}

	public void setJDOIncorporationDate(Date incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public StandardCode getCustomerType() {
		if (customerType != null && customerType.getStandardCodeNumber() == null) {
			customerType.setStandardCodeNumber("8");
		}
		return customerType;
	}

	public void setCustomerType(StandardCode customerType) {
		this.customerType = customerType;
	}

	public String getCustomerStartDate() {
		return MessageDate.getInstance().getString(this.customerStartDate);
	}

	public Date getJDOCustomerStartDate() {
		return this.customerStartDate;
	}

	public void setCustomerStartDate(String customerStartDate) {
		this.customerStartDate = MessageDate.getInstance().getDate(customerStartDate);
	}

	public void setJDOCustomerStartDate(Date customerStartDate) {
		this.customerStartDate = customerStartDate;
	}

	public StandardCode getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(StandardCode customerSegment) {
		this.customerSegment = customerSegment;
	}

	public StandardCode getBaselCustomerSegment() {
		return baselCustomerSegment;
	}

	public void setBaselCustomerSegment(StandardCode baselCustomerSegment) {
		this.baselCustomerSegment = baselCustomerSegment;
	}

	public StandardCode getBaselCustomerSubSegment() {
		return baselCustomerSubSegment;
	}

	public void setBaselCustomerSubSegment(StandardCode baselCustomerSubSegment) {
		this.baselCustomerSubSegment = baselCustomerSubSegment;
	}

	public String getBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(String blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getBizGroup() {
		return bizGroup;
	}

	public void setBizGroup(String bizGroup) {
		this.bizGroup = bizGroup;
	}

	public long getTFA() {
		return TFA;
	}

	public void setTFA(long TFA) {
		this.TFA = TFA;
	}

	public StandardCode getIncomeGroup() {
		return incomeGroup;
	}

	public void setIncomeGroup(StandardCode incomeGroup) {
		this.incomeGroup = incomeGroup;
	}

	public StandardCode getBizSector() {
		return bizSector;
	}

	public void setBizSector(StandardCode bizSector) {
		this.bizSector = bizSector;
	}

	public StandardCode getIdType() {
		return idType;
	}

	public void setIdType(StandardCode idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getOldIdNo() {
		return oldIdNo;
	}

	public void setOldIdNo(String oldIdNo) {
		this.oldIdNo = oldIdNo;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getDomicileCountry() {
		if (domicileCountry == null) {
			domicileCountry = "MY";
		}
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public StandardCode getNatureOfBiz() {
		if (natureOfBiz != null && natureOfBiz.getStandardCodeNumber() == null) {
			natureOfBiz.setStandardCodeNumber("17");
		}
		return natureOfBiz;
	}

	public void setNatureOfBiz(StandardCode natureOfBiz) {
		this.natureOfBiz = natureOfBiz;
	}

	public java.util.Vector getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(java.util.Vector idInfo) {
		this.idInfo = idInfo;
	}

	public java.util.Vector getAddress() {
		return address;
	}

	public void setAddress(java.util.Vector address) {
		this.address = address;
	}

	public String getCountry() {
		// Get country from properties and map to Country ISO
		if ((country != null) && (country.length() > 2)) {
			country = PropertyManager.getValue(IEaiConstant.GCIF_COUNTRY_PREFIX + country.trim());
		}
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public StandardCode getCountryPermanent() {
		// Get country from properties and map to Country ISO
		if (countryPermanent != null) {
			String countryCode = countryPermanent.getStandardCodeValue();
			if ((countryCode != null) && (countryCode.length() > 2)) {
				countryCode = PropertyManager.getValue(IEaiConstant.GCIF_COUNTRY_PREFIX + countryCode.trim());
				countryPermanent.setStandardCodeValue(countryCode);
			}
		}
		return countryPermanent;
	}

	public void setCountryPermanent(StandardCode countryPermanent) {
		this.countryPermanent = countryPermanent;
	}

	public StandardCode getLanguage() {
		return language;
	}

	public void setLanguage(StandardCode language) {
		this.language = language;
	}

	public String getOfficeAddress1() {
		return officeAddress1;
	}

	public void setOfficeAddress1(String officeAddress1) {
		this.officeAddress1 = officeAddress1;
	}

	public String getOfficeAddress2() {
		return officeAddress2;
	}

	public void setOfficeAddress2(String officeAddress2) {
		this.officeAddress2 = officeAddress2;
	}

	public String getOfficeAddress3() {
		return officeAddress3;
	}

	public void setOfficeAddress3(String officeAddress3) {
		this.officeAddress3 = officeAddress3;
	}

	public String getOfficeAddress4() {
		return officeAddress4;
	}

	public void setOfficeAddress4(String officeAddress4) {
		this.officeAddress4 = officeAddress4;
	}

	public String getOfficePostcode() {
		return officePostcode;
	}

	public void setOfficePostcode(String officePostcode) {
		this.officePostcode = officePostcode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public final String getSciSearchId() {
		return sciSearchId;
	}

	public final void setSciSearchId(String sciSearchId) {
		this.sciSearchId = sciSearchId;
	}

	public CustomerIdInfo getIdInfo1() {
		if (idInfo1 != null && idInfo1.getIdType() != null && idInfo1.getIdType().getStandardCodeNumber() == null) {
			idInfo1.getIdType().setStandardCodeNumber("ID_TYPE");
		}
		return idInfo1;
	}

	public void setIdInfo1(CustomerIdInfo idInfo1) {
		this.idInfo1 = idInfo1;
	}

	public CustomerIdInfo getIdInfo2() {
		return idInfo2;
	}

	public void setIdInfo2(CustomerIdInfo idInfo2) {
		this.idInfo2 = idInfo2;
	}

	public CustomerIdInfo getIdInfo3() {
		return idInfo3;
	}

	public void setIdInfo3(CustomerIdInfo idInfo3) {
		this.idInfo3 = idInfo3;
	}

	/**
	 * @return the fIIndicator
	 */
	public final String getFIIndicator() {
		return FIIndicator;
	}

	/**
	 * @param indicator the fIIndicator to set
	 */
	public final void setFIIndicator(String indicator) {
		FIIndicator = indicator;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((CIFId == null) ? 0 : CIFId.hashCode());
		result = prime * result + ((customerNameLong == null) ? 0 : customerNameLong.hashCode());
		result = prime * result + ((customerNameShort == null) ? 0 : customerNameShort.hashCode());
		result = prime * result + ((incorporatedCountry == null) ? 0 : incorporatedCountry.hashCode());
		result = prime * result + ((incorporationDate == null) ? 0 : incorporationDate.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());

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

		MainProfile other = (MainProfile) obj;
		if (CIFId == null) {
			if (other.CIFId != null) {
				return false;
			}
		}
		else if (!CIFId.equals(other.CIFId)) {
			return false;
		}

		if (customerNameLong == null) {
			if (other.customerNameLong != null) {
				return false;
			}
		}
		else if (!customerNameLong.equals(other.customerNameLong)) {
			return false;
		}

		if (customerNameShort == null) {
			if (other.customerNameShort != null) {
				return false;
			}
		}
		else if (!customerNameShort.equals(other.customerNameShort)) {
			return false;
		}

		if (incorporatedCountry == null) {
			if (other.incorporatedCountry != null) {
				return false;
			}
		}
		else if (!incorporatedCountry.equals(other.incorporatedCountry)) {
			return false;
		}

		if (incorporationDate == null) {
			if (other.incorporationDate != null) {
				return false;
			}
		}
		else if (!incorporationDate.equals(other.incorporationDate)) {
			return false;
		}

		if (source == null) {
			if (other.source != null) {
				return false;
			}
		}
		else if (!source.equals(other.source)) {
			return false;
		}

		return true;
	}

	public String getAccountOfficerID() {
		return accountOfficerID;
	}

	public void setAccountOfficerID(String accountOfficerID) {
		this.accountOfficerID = accountOfficerID;
	}

	public String getAccountOfficerName() {
		return accountOfficerName;
	}

	public void setAccountOfficerName(String accountOfficerName) {
		this.accountOfficerName = accountOfficerName;
	}

	public StandardCode getCustomerBranch() {
		if (this.customerBranch.getStandardCodeNumber() == null) {
			this.customerBranch.setStandardCodeNumber("40");
		}
		return this.customerBranch;
	}

	public void setCustomerBranch(StandardCode customerBranch) {
		this.customerBranch = customerBranch;
	}
	
	public String getLeiCode() {
		return leiCode;
	}

	public void setLeiCode(String leiCode) {
		this.leiCode = leiCode;
	}

	public Date getLeiExpDate() {
		return leiExpDate;
	}

	public void setLeiExpDate(Date leiExpDate) {
		this.leiExpDate = leiExpDate;
	}

	public String toString() {
		return "MainProfile : CIF Id [" + CIFId + "] source [" + source + "] Customer Name (Long) [" + customerNameLong
				+ "] Incorporation Country [" + incorporatedCountry + "] Incorporation Date [" + incorporationDate
				+ "]";
	}
}
