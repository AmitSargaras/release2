
package com.integrosys.cms.app.holiday.bus;


import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBHoliday implements IHoliday {
	
	/**
	 * constructor
	 */
	public OBHoliday() {
		
	}
    private long id;
	
	private long versionTime;
	private String description;
	private Date startDate;
	private Date endDate;
	private String isRecurrent;
	
	
	private String status;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	private FormFile fileUpload;

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIsRecurrent() {
		return isRecurrent;
	}
	public void setIsRecurrent(String isRecurrent) {
		this.isRecurrent = isRecurrent;
	}
	public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	
	
	
	
	


	
	

//	public String toString() {
//		StringBuffer buf = new StringBuffer(getClass().getName());
//		buf.append("@").append(System.identityHashCode(this));
//
//		buf.append(" CIF [").append(customerReference).append("], ");
//		buf.append("Customer Name [").append(customerName).append("], ");
//		buf.append("Short Description [").append(description).append("], ");
//		buf.append("Expiry Date [").append(expiryDate).append("], ");
//		buf.append("Reminder Date [").append(dueDate).append("], ");
//		buf.append("Access Country [").append(allowedCountry).append("]");
//
//		return buf.toString();
//	}
}