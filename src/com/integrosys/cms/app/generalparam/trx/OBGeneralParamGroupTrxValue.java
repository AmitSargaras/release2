/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/OBBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.generalparam.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
/**
* @author $Author: Dattatray Thorat $
* @version $Revision: 1.4 $
* @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
*/
public class OBGeneralParamGroupTrxValue extends OBCMSTrxValue implements IGeneralParamGroupTrxValue {

	/**
	 * Get the IGeneralParamGroup busines entity
	 * 
	 * @return IGeneralParamGroup
	 */
	public IGeneralParamGroup getGeneralParamGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBGeneralParamGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBGeneralParamGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);
	}

	/**
	 * Get the staging IGeneralParamGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IGeneralParamGroup getStagingGeneralParamGroup() {
		return staging;
	}

	/**
	 * Set the IGeneralParamGroup busines entity
	 * 
	 * @param value is of type IGeneralParamGroup
	 */
	public void setGeneralParamGroup(IGeneralParamGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IGeneralParamGroup business entity
	 * 
	 * @param value is of type IGeneralParamGroup
	 */
	public void setStagingGeneralParamGroup(IGeneralParamGroup value) {
		staging = value;
	}

	private IGeneralParamGroup actual;

	private IGeneralParamGroup staging;
}
