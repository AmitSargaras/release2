/**
 * CommonCodeEntry.java
 *
 * Created on January 29, 2007, 4:24 PM
 *
 * Purpose: The OB object the stores an individual entry in the COMMON_CODE_CATEGORY_ENTRY table
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBCommonCodeEntry implements ICommonCodeEntry {
	private static final long serialVersionUID = 1L;

	private long entryId = ICMSConstant.LONG_INVALID_VALUE;

	private String entryCode;

	private String entryName;

	private boolean activeStatus;

	private String entrySource;

	private String country;

	private Integer groupId;

	private String categoryCode;

	private long categoryCodeId = ICMSConstant.LONG_INVALID_VALUE;

	private String refEntryCode;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	private String status;

	private Long stageId;

	private Long stageRefId;

	private char updateFlag = 'Z';

    private String isDel;
    
    private String cpsId;
    
    private String operationName;
    
    /**
     * Added by @author anil.pandey on @createdOn Sep 23, 2014 1:45:20 PM for handling CC entry audit.
     */
    private Date creationDate;
	private Date lastUpdateDate;
	
	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getStageRefId() {
		return stageRefId;
	}

	public void setStageRefId(Long stageRefId) {
		this.stageRefId = stageRefId;
	}

	public char getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(char updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getEntrySource() {
		return entrySource;
	}

	public void setEntrySource(String entrySource) {
		this.entrySource = entrySource;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public long getCategoryCodeId() {
		return categoryCodeId;
	}

	public void setCategoryCodeId(long categoryCodeId) {
		this.categoryCodeId = categoryCodeId;
	}

	public String getRefEntryCode() {
		return refEntryCode;
	}

	public void setRefEntryCode(String refEntryCode) {
		this.refEntryCode = refEntryCode;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveStatusStr() {
		return (getActiveStatus()) ? "1" : "0";
	}

	public void setActiveStatusStr(String statusStr) {
		setActiveStatus("1".equals(statusStr));
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OBCommonCodeEntry that = (OBCommonCodeEntry) o;

		if (activeStatus != that.activeStatus)
			return false;
		if (categoryCode != null ? !categoryCode.equals(that.categoryCode) : that.categoryCode != null)
			return false;
		if (country != null ? !country.equals(that.country) : that.country != null)
			return false;
		if (entryCode != null ? !entryCode.equals(that.entryCode) : that.entryCode != null)
			return false;
		if (entrySource != null ? !entrySource.equals(that.entrySource) : that.entrySource != null)
			return false;
		if (refEntryCode != null ? !refEntryCode.equals(that.refEntryCode) : that.refEntryCode != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (entryCode != null ? entryCode.hashCode() : 0);
		result = 31 * result + (activeStatus ? 1 : 0);
		result = 31 * result + (entrySource != null ? entrySource.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (categoryCode != null ? categoryCode.hashCode() : 0);
		result = 31 * result + (refEntryCode != null ? refEntryCode.hashCode() : 0);
		return result;
	}

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }
    
    public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
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

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
