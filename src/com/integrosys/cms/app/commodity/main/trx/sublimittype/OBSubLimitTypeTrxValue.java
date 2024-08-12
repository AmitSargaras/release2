/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/OBSubLimitTypeTrxValue.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      OBSubLimitTypeTrxValue.java
 */
public class OBSubLimitTypeTrxValue extends OBCMSTrxValue implements ISubLimitTypeTrxValue {

	private ISubLimitType[] subLimitTypes = null;

	private ISubLimitType[] stagingSubLimitTypes = null;

	// Begin Construct Methods.

	public OBSubLimitTypeTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE);
	}

	// public OBSubLimitTypeTrxValue(ISubLimitType[] obj) {
	// this();
	// AccessorUtil.copyValue(obj, this);
	// }

	public OBSubLimitTypeTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	// End Construct Methods.

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue
	 * #getSubLimitTypes()
	 */
	public ISubLimitType[] getSubLimitTypes() {
		return subLimitTypes;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue
	 * #getStagingSubLimitTypes()
	 */
	public ISubLimitType[] getStagingSubLimitTypes() {
		return stagingSubLimitTypes;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue
	 * #
	 * sgetSubLimitTypes(com.integrosys.cms.app.commodity.main.bus.sublimittype.
	 * ISubLimitType[])
	 */
	public void setSubLimitTypes(ISubLimitType[] types) {
		subLimitTypes = types;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue
	 * #setStagingSubLimitTypes(com.integrosys.cms.app.commodity.main.bus.
	 * sublimittype.ISubLimitType[])
	 */
	public void setStagingSubLimitTypes(ISubLimitType[] types) {
		stagingSubLimitTypes = types;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
