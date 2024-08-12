/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/OBLimitProfileTrxValue.java,v 1.1 2003/07/09 07:29:06 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class represents a Limit Profile trx value.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/09 07:29:06 $ Tag: $Name: $
 */
public class OBLimitProfileTrxValue extends OBCMSTrxValue implements ILimitProfileTrxValue {

	private static final long serialVersionUID = -4277238823848754804L;

	private ILimitProfile limitProfile = null;

	private ILimitProfile stagingLimitProfile = null;

	/**
	 * Default Constructor
	 */
	public OBLimitProfileTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_LIMIT_PROFILE);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimitProfileTrxValue
	 */
	public OBLimitProfileTrxValue(ILimitProfileTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICMSTrxValue
	 */
	public OBLimitProfileTrxValue(ICMSTrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get the Limit profile object
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getLimitProfile() {
		return limitProfile;
	}

	/**
	 * Get the staging limit profile object
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getStagingLimitProfile() {
		return stagingLimitProfile;
	}

	/**
	 * Set the limit profile object
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setLimitProfile(ILimitProfile value) {
		limitProfile = value;
	}

	/**
	 * Set the staging limit profile object
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setStagingLimitProfile(ILimitProfile value) {
		stagingLimitProfile = value;
	}
}