/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBCustodianDocSearchResult.java,v 1.14 2006/08/30 12:09:22 jzhai Exp $
 */
package com.integrosys.cms.app.generateli.bus;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Implementation class for the ICustodianDocSearchResult interface
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/08/30 12:09:22 $ Tag: $Name: $
 */
public class OBLiDoc implements ILiDoc {

	private Date firstGenDate;
	private Date lastGenDate;
	private Date generatedDate;
	private String generatedBy;
	private String liTemplateName;
	private Long checklistId;
	private String fileLocation;
	private Long id;

	public Date getFirstGenDate() {
		return firstGenDate;
	}



	public void setFirstGenDate(Date firstGenDate) {
		this.firstGenDate = firstGenDate;
	}



	public Date getLastGenDate() {
		return lastGenDate;
	}



	public void setLastGenDate(Date lastGenDate) {
		this.lastGenDate = lastGenDate;
	}



	public String getGeneratedBy() {
		return generatedBy;
	}



	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}



	public String getLiTemplateName() {
		return liTemplateName;
	}



	public void setLiTemplateName(String liTemplateName) {
		this.liTemplateName = liTemplateName;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}



	public Date getGeneratedDate() {
		return generatedDate;
	}



	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}



	public Long getChecklistId() {
		return checklistId;
	}



	public void setChecklistId(Long checklistId) {
		this.checklistId = checklistId;
	}



	public String getFileLocation() {
		return fileLocation;
	}



	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}