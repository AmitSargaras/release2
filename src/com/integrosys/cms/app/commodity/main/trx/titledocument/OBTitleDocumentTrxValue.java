/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/OBTitleDocumentTrxValue.java,v 1.3 2004/08/17 06:52:30 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: wltan $
 * @version $
 * @since $Date: 2004/08/17 06:52:30 $ Tag: $Name: $
 */
public class OBTitleDocumentTrxValue extends OBCMSTrxValue implements ITitleDocumentTrxValue {

	private ITitleDocument[] titleDocument = null;

	private ITitleDocument[] stagingTitleDocument = null;

	public OBTitleDocumentTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_TITLEDOC);
	}

	public OBTitleDocumentTrxValue(ITitleDocument[] obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public OBTitleDocumentTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public ITitleDocument[] getTitleDocument() {
		return titleDocument;
	}

	public void setTitleDocument(ITitleDocument[] titleDocument) {
		this.titleDocument = titleDocument;
	}

	public ITitleDocument[] getStagingTitleDocument() {
		return stagingTitleDocument;
	}

	public void setStagingTitleDocument(ITitleDocument[] stagingTitleDocument) {
		this.stagingTitleDocument = stagingTitleDocument;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
