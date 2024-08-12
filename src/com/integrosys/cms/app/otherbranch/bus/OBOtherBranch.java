package com.integrosys.cms.app.otherbranch.bus;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Purpose: Used for defining attributes for Other Branch
 * 
 * @author Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 16:07:56 +0800 (Fri, 18 Feb 2011) 
 */

public class OBOtherBranch implements IOtherBranch {
	
	
	private static final long serialVersionUID = 1L;
	
	private IOtherBank otherBankCode;
	private String otherBranchCode;
	private String otherBranchName;
	private long versionTime ;
    private String deprecated;
    private String address;
    private ICity city;
    private IState state;
    private IRegion region;
    private ICountry country;
    private long contactNo;
    private String contactMailId;
    private String revisedContactMailId;
	private String status;
	private long id;
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private long faxNo;
	private long rbiCode;
	
	private FormFile fileUpload;
	
    
	
	public String getRevisedContactMailId() {
		return revisedContactMailId;
	}

	public void setRevisedContactMailId(String revisedContactMailId) {
		this.revisedContactMailId = revisedContactMailId;
	}

	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
     
	
	/**
	 * @return the otherBankCode
	 */
	public IOtherBank getOtherBankCode() {
		return otherBankCode;
	}
	/**
	 * @param otherBankCode the otherBankCode to set
	 */
	public void setOtherBankCode(IOtherBank otherBankCode) {
		this.otherBankCode = otherBankCode;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	/**
	 * @return the id
	 */

	/**
	 * @return the faxNo
	 */
	public long getFaxNo() {
		return faxNo;
	}
	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(long faxNo) {
		this.faxNo = faxNo;
	}
	/**
	 * @return the rbiCode
	 */
	public long getRbiCode() {
		return rbiCode;
	}
	/**
	 * @param rbiCode the rbiCode to set
	 */
	public void setRbiCode(long rbiCode) {
		this.rbiCode = rbiCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the contactNo
	 */
	public long getContactNo() {
		return contactNo;
	}
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	/**
	 * @return the contactMailId
	 */
	public String getContactMailId() {
		return contactMailId;
	}
	/**
	 * @param contactMailId the contactMailId to set
	 */
	public void setContactMailId(String contactMailId) {
		this.contactMailId = contactMailId;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the city
	 */
	public ICity getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(ICity city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public IState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(IState state) {
		this.state = state;
	}
	/**
	 * @return the region
	 */
	public IRegion getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(IRegion region) {
		this.region = region;
	}
	/**
	 * @return the country
	 */
	public ICountry getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(ICountry country) {
		this.country = country;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the otherBranchCode
	 */
	public String getOtherBranchCode() {
		return otherBranchCode;
	}
	/**
	 * @param otherBranchCode the otherBranchCode to set
	 */
	public void setOtherBranchCode(String otherBranchCode) {
		this.otherBranchCode = otherBranchCode;
	}
	/**
	 * @return the otherBranchName
	 */
	public String getOtherBranchName() {
		return otherBranchName;
	}
	/**
	 * @param otherBranchName the otherBranchName to set
	 */
	public void setOtherBranchName(String otherBranchName) {
		this.otherBranchName = otherBranchName;
	}
	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	/**
	 * @return the deprecated
	 */
	public String getDeprecated() {
		return deprecated;
	}
	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
