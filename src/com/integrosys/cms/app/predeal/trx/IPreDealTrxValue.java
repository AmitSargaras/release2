/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * IPreDealTrxValue
 *
 * Created on 6:07:57 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 6:07:57 PM
 */
public interface IPreDealTrxValue extends ICMSTrxValue {

	IPreDeal getPreDeal();

	IPreDeal getStagingPreDeal();

	void setPreDeal(IPreDeal actualData);

	void setStagingPreDeal(IPreDeal stagingData);

}
