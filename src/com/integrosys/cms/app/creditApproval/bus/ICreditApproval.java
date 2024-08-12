
package com.integrosys.cms.app.creditApproval.bus;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/31 04:55:19 $ Tag: $Name: $
 * 
 */
public interface ICreditApproval extends java.io.Serializable, IValueObject {

	
	public long getId();
	
	public void setId(long id);
	
	public String getApprovalCode();
	
	public void setApprovalCode(String approvalCode);
	
	public String getApprovalName() ;
	
	public void setApprovalName(String approvalName);
	
	public BigDecimal getMaximumLimit();
	
	public void setMaximumLimit(BigDecimal maximumLimit);
	
	public BigDecimal getMinimumLimit() ;
	
	public void setMinimumLimit(BigDecimal minimumLimit) ;
	
	public String getSegmentId();
	
	public void setSegmentId(String segmentId) ;
	
	public String getEmail() ;
	
	public void setEmail(String email);
	
	public String getSenior();
	
	public void setSenior(String senior);
	
	public String getRisk() ;
	
	public void setRisk(String risk);
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime) ;
	
	public String getCreateBy();
	
	public void setCreateBy(String createBy);
	
	public Date getCreationDate() ;
	
	public void setCreationDate(Date creationDate) ;
	
	public String getLastUpdateBy() ;
	
	public void setLastUpdateBy(String lastUpdateBy);
	
	public Date getLastUpdateDate() ;
	
	public void setLastUpdateDate(Date lastUpdateDate) ;
	
	public String getDeprecated();
	
	public void setDeprecated(String deprecated) ;
	
	public String getStatus() ;
	
	public void setStatus(String status) ;
	

	public String getDeferralPowers();
	
	public void setDeferralPowers(String deferralPowers);
	
	public String getWaivingPowers();
	
	public void setWaivingPowers(String waivingPowers);
	
	public long getRegionId();
	
	public void setRegionId(long regionId);
	
	public FormFile getFileUpload();
	
	public void setFileUpload(FormFile fileUpload);
	
	public String getCpsId();

	public void setCpsId(String cpsId) ;
	
	public String getEmployeeId();

	public void setEmployeeId(String employeeId) ;
	
}
