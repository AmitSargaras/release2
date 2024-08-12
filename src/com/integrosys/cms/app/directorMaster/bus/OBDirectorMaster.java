package com.integrosys.cms.app.directorMaster.bus;
import java.util.Date;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public class OBDirectorMaster implements IDirectorMaster {
	
	/**
	 * constructor
	 */
	public OBDirectorMaster() {
		
	}
    private long id;
	private String dinNo;
	
	public String getDinNo() {
		return dinNo;
		}
	public void setDinNo(String dinNo) {
		this.dinNo = dinNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirectorCode() {
		return directorCode;
	}
	public void setDirectorCode(String directorCode) {
		this.directorCode = directorCode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	private String name;
	private String directorCode;
	private String action;
	
	private long versionTime;
	
	private String status;
	
	private String deprecated;

	private Date creationDate;
	
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
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

	
}