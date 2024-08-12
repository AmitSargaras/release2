/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ICustodianTrxValue.java,v 1.5 2005/04/08 06:30:56 wltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface defines the list of attributes pertaining to the custodian
 * transaction object
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/04/08 06:30:56 $ Tag: $Name: $
 */
public interface ICustodianTrxValue extends ICMSTrxValue {
	public ICustodianDoc getStagingCustodianDoc();

	public ICustodianDoc getCustodianDoc();

	public void setStagingCustodianDoc(ICustodianDoc anICustodianDoc);

	public void setCustodianDoc(ICustodianDoc anICustodianDoc);
}
