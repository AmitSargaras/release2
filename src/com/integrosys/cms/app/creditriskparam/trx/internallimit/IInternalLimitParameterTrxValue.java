/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/OBForexFeedGroupTrxValue.java,v 1.5 2003/08/06 05:42:09 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */
public interface IInternalLimitParameterTrxValue extends ICMSTrxValue {

	public List getActualILPList();

	public void setActualILPList(List actualILPList);

	public List getStagingILPList();

	public void setStagingILPList(List stagingILPList);
}
