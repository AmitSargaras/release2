/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ILimitProfileTrxValue.java,v 1.1 2003/07/09 07:29:06 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a Limit Profile trx value.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/09 07:29:06 $ Tag: $Name: $
 */
public interface ILimitProfileTrxValue extends ICMSTrxValue {
	/**
	 * Get the Limit object
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getLimitProfile();

	/**
	 * Get the staging limit Profile object
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getStagingLimitProfile();

	/**
	 * Set the limit Profile object
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setLimitProfile(ILimitProfile value);

	/**
	 * Set the staging limit Profile object
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setStagingLimitProfile(ILimitProfile value);
}