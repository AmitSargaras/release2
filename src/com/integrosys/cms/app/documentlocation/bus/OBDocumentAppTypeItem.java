/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/OBDiaryItem.java,v 1.7 2005/09/30 11:32:23 jtan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//app

import java.util.Date;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Value object for diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public class OBDocumentAppTypeItem implements IDocumentAppTypeItem {
	private Long documentLoanId;
	private Long documentId;
	private String appType;
	private long refId = ICMSConstant.LONG_MIN_VALUE;
	private String status = ICMSConstant.STATE_ACTIVE;

	
	public Long getDocumentLoanId() {
		return documentLoanId;
	}


	public void setDocumentLoanId(Long documentLoanId) {
		this.documentLoanId = documentLoanId;
	}

	public Long getDocumentId() {
		return documentId;
	}


	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}


	public String getAppType() {
		return appType;
	}


	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());

		buf.append(" Document Id [").append(documentId).append("], ");
		buf.append(" App Type  [").append(appType).append("], ");

		return buf.toString();
	}


	public long getRefId() {
		return refId;
	}


	public void setRefId(long refId) {
		this.refId = refId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}



}