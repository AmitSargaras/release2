/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBDocument.java,v 1.3 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.IDocument;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;

/**
 * This class implements the IDocument
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBDocument implements IDocument {
	private long documentID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String documentType = null;

	private IDocumentItem[] documentItemList = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the document ID
	 * @return long - the document ID
	 */
	public long getDocumentID() {
		return this.documentID;
	}

	/**
	 * Get the document Type
	 * @return String - the document type
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * Get the document item list
	 * @return IDocumentItem[] - the list of document items
	 */
	public IDocumentItem[] getDocumentItemList() {
		return this.documentItemList;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the document ID
	 * @param aDocumentID - long
	 */
	public void setDocumentID(long aDocumentID) {
		this.documentID = aDocumentID;
	}

	/**
	 * Set the document type
	 * @param aDocumentType - String
	 */
	public void setDocumentType(String aDocumentType) {
		this.documentType = aDocumentType;
	}

	/**
	 * Set the document item list
	 * @param anIDocumentItemList - IDocumentItem[]
	 */
	public void setDocumentItemList(IDocumentItem[] anIDocumentItemList) {
		this.documentItemList = anIDocumentItemList;
	}

	/**
	 * Set the version time
	 * @param aVersionTime - long
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
