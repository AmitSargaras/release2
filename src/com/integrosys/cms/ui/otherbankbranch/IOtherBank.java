package com.integrosys.cms.ui.otherbankbranch;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;

/**
 * Purpose : Defines methods for operating on other bank 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBank extends Serializable, IValueObject{
	
	/**
	 * @return the other Bank code
	 */
	public String getOtherBankCode();
	/**
	 * @param code the other Bank code to set
	 */
	public void setOtherBankCode(String otherBankCode);
	
	/**
	 * 
	 * @return the other Bank Name
	 */
	public String getOtherBankName();
	/**
	 * @param products name the other Bank Name to set
	 */
	public void setOtherBankName(String otherBankName);
	
	public String getBranchName() ;
	public void setBranchName(String branchName);
	public String getBranchNameAddress() ;
	public void setBranchNameAddress(String branchNameAddress) ;
	public String getiFSCCode() ;
	public void setiFSCCode(String iFSCCode);
	
	 
	
	public String getOtherBranchId() ;
	public void setOtherBranchId(String otherBranchId);
	public String getOtherBranchCode() ;
	public void setOtherBranchCode(String otherBranchCode);
	
	/**
	 * @return the address
	 */
	public String getAddress();
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) ;
	/**
	 * @return the city
	 */
	public ICity getCity() ;
	/**
	 * @param city the city to set
	 */
	public void setCity(ICity city) ;
	/**
	 * @return the state
	 */
	public IState getState() ;
	/**
	 * @param state the state to set
	 */
	public void setState(IState state) ;
	/**
	 * @return the region
	 */
	public IRegion getRegion() ;
	/**
	 * @param region the region to set
	 */
	public void setRegion(IRegion region) ;
	/**
	 * @return the country
	 */
	public ICountry getCountry() ;
	
	/**
	 * @param country the country to set
	 */
	public void setCountry(ICountry country) ;
	
	
	/**
	 * @return the contactNo
	 */
	public long getContactNo();
	
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(long contactNo);
	
	/**
	 * @return the contactMailId
	 */
	public String getContactMailId();
	
	/**
	 * @param contactMailId the contactMailId to set
	 */
	public void setContactMailId(String contactMailId);
	
	/**
	 * @return the deprecated
	 */
	public String getDeprecated();

	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated);
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id);
	
	/**
	 * @return the faxNo
	 */
	public long getFaxNo() ;
	
	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(long faxNo) ;
	
	/**
	 * @return the createBy
	 */
	public String getCreatedBy();
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreatedBy(String createdBy);
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy();
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy);
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate();
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) ;
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate();
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) ;
	
	/**
	 * @return the status
	 */
	public String getStatus();
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
	
	/**
	 * @return the versionTime
	 */
	public long getVersionTime(); 
	
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime);
	
	public FormFile getFileUpload();
	public void setFileUpload(FormFile fileUpload);
}