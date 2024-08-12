/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/IBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.trx;

import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IGeneralParamGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IBondFeedGroup busines entity
	 * 
	 */
	public IGeneralParamGroup getGeneralParamGroup();

	/**
	 * Get the staging IGeneralParamGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IGeneralParamGroup getStagingGeneralParamGroup();

	/**
	 * Set the IGeneralParamGroup busines entity
	 * 
	 * @param value is of type IGeneralParamGroup
	 */
	public void setGeneralParamGroup(IGeneralParamGroup value);

	/**
	 * Set the staging IGeneralParamGroup business entity
	 * 
	 * @param value is of type IGeneralParamGroup
	 */
	public void setStagingGeneralParamGroup(IGeneralParamGroup value);
}