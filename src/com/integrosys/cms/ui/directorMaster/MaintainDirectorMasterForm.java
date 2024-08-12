package com.integrosys.cms.ui.directorMaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;


/**
 * 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */


public class MaintainDirectorMasterForm extends TrxContextForm implements Serializable {

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
	private String creationDate;
	private String lastUpdateDate;
	private String createBy;
	private String lastUpdateBy;
	private String versionTime;
	private String status;
	private String deprecated;
	private String id;
  
   
	public String[][] getMapper() {
		String[][] input = {  { "directorMasterObj", DIRECTOR_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String DIRECTOR_MASTER_MAPPER = "com.integrosys.cms.ui.directorMaster.DirectorMasterMapper";

	public static final String DIRECTOR_MASTER_LIST_MAPPER = "com.integrosys.cms.ui.directorMaster.DirectorMasterListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
	

}
