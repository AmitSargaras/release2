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
 * Purpose : Defines methods for operating on other bank branch
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBranch extends Serializable, IValueObject{
	/**
	 * @return the other bank
	 */
	public IOtherBank getOtherBankCode();
	
	/**
	 * @param otherBank the otherBank to set
	 */
	public void setOtherBankCode(IOtherBank otherBank);
	
	/**
	 * @return the other branch code
	 */
	public String getOtherBranchCode();
	/**
	 * @param code the other branch code to set
	 */
	public void setOtherBranchCode(String otherBranchCode);
	
	/**
	 * 
	 * @return the other Bank Name
	 */
	public String getOtherBranchName();
	/**
	 * @param products name the other Bank Name to set
	 */
	public void setOtherBranchName(String otherBranchName);
	
	
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
	
	
	public long getContactNo();
	
	public void setContactNo(long contactNo);
	
	public String getContactMailId();
	
	public void setContactMailId(String contactMailId);
	
	public String getRevisedContactMailId();
	
	public void setRevisedContactMailId(String revisedContactMailId);
	
	/**
	 * @return the deprecated
	 */
	public String getDeprecated();

	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated);
	
	public long getId();

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
	 * @return the rbiCode
	 */
	public long getRbiCode() ;
	/**
	 * @param rbiCode the rbiCode to set
	 */
	public void setRbiCode(long rbiCode) ;

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
	 * @return the createBy
	 */
	public String getCreatedBy();
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreatedBy(String createdBy);
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