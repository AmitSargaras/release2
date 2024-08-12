/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/ITitleDocumentTrxValue.java,v 1.3 2004/08/17 06:52:30 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: wltan $
 * @version $
 * @since $Date: 2004/08/17 06:52:30 $ Tag: $Name: $
 */
public interface ITitleDocumentTrxValue extends ICMSTrxValue {
	public ITitleDocument[] getTitleDocument();

	public ITitleDocument[] getStagingTitleDocument();

	public void setTitleDocument(ITitleDocument[] value);

	public void setStagingTitleDocument(ITitleDocument[] value);
}
