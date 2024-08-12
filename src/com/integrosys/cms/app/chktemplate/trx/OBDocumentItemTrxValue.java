/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OBDocumentItemTrxValue.java,v 1.1 2003/06/27 10:44:38 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;

/**
 * This class provides the implementation for the IDocumentItemTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:44:38 $ Tag: $Name: $
 */
public class OBDocumentItemTrxValue extends OBCMSTrxValue implements IDocumentItemTrxValue {
	private IDocumentItem documentItem = null;

	private IDocumentItem stagingDocumentItem = null;

	/**
	 * Default Constructor
	 */
	public OBDocumentItemTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBDocumentItemTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the document item business entity
	 * @return IDocumentItem - the document item
	 */
	public IDocumentItem getDocumentItem() {
		return this.documentItem;
	}

	/**
	 * Get the staging document item business entity
	 * @return IDocumentItem - the staging document item
	 */
	public IDocumentItem getStagingDocumentItem() {
		return this.stagingDocumentItem;
	}

	/**
	 * Set the document item business entity
	 * @param anIDocumentItem - IDocumentItem
	 */
	public void setDocumentItem(IDocumentItem anIDocumentItem) {
		this.documentItem = anIDocumentItem;
	}

	/**
	 * Set the staging document item business entity
	 * @param anIDocumentItem - IDocumentItem
	 */
	public void setStagingDocumentItem(IDocumentItem anIDocumentItem) {
		this.stagingDocumentItem = anIDocumentItem;
	}
}