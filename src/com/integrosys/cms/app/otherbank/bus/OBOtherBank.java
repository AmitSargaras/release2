package com.integrosys.cms.app.otherbank.bus;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * Purpose: Used for defining attributes for Other Bank
 * 
 * @author Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 16:07:56 +0800 (Fri, 18 Feb 2011) 
 */

public class OBOtherBank implements IOtherBank{
	
	
	private static final long serialVersionUID = 1L;
	
	private String otherBankCode ;
	private String otherBankName ;
	private long versionTime ;
    private String deprecated ;
    private String address;
    private ICity city;
    private IState state;
    private IRegion region;
    private ICountry country;
    private long contactNo;
    private String contactMailId;
    private long faxNo;
	private String status;
	private long id;
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String branchName;
	private String branchNameAddress;
	
	private String iFSCCode;
	private String otherBranchId  ;
	private String otherBranchCode;
	private FormFile fileUpload;
     
	/**
	 * @return the createBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createBy the createBy to set
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
	 * @return the otherBankCode
	 */
	public String getOtherBankCode() {
		return otherBankCode;
	}
	/**
	 * @param otherBankCode the otherBankCode to set
	 */
	public void setOtherBankCode(String otherBankCode) {
		this.otherBankCode = otherBankCode;
	}
	/**
	 * @return the otherBankName
	 */
	public String getOtherBankName() {
		return otherBankName;
	}
	/**
	 * @param otherBankName the otherBankName to set
	 */
	public void setOtherBankName(String otherBankName) {
		this.otherBankName = otherBankName;
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
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchNameAddress() {
		return branchNameAddress;
	}
	public void setBranchNameAddress(String branchNameAddress) {
		this.branchNameAddress = branchNameAddress;
	}
	public String getiFSCCode() {
		return iFSCCode;
	}
	public void setiFSCCode(String iFSCCode) {
		this.iFSCCode = iFSCCode;
	}
	public String getOtherBranchId() {
		return otherBranchId;
	}
	public void setOtherBranchId(String otherBranchId) {
		this.otherBranchId = otherBranchId;
	}
	public String getOtherBranchCode() {
		return otherBranchCode;
	}
	public void setOtherBranchCode(String otherBranchCode) {
		this.otherBranchCode = otherBranchCode;
	}
	
	
	
	
}
