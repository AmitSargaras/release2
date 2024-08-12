/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/ISubLimitTypeTrxValue.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitTypeTrxValue
 *      .java
 */
public interface ISubLimitTypeTrxValue extends ICMSTrxValue {

	public ISubLimitType[] getSubLimitTypes();

	public ISubLimitType[] getStagingSubLimitTypes();

	public void setSubLimitTypes(ISubLimitType[] types);

	public void setStagingSubLimitTypes(ISubLimitType[] types);
}
