/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/trx/OBUserTrxValue.java,v 1.1 2003/07/23 12:38:46 kllee Exp $
 */
package com.integrosys.cms.app.user.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * This class represents the transaction object for CMS.
 * 
 * @author Alfred Lee
 */
public class OBUserTrxValue extends OBCMSTrxValue implements ICommonUserTrxValue {
	private ICommonUser _user = null;

	private ICommonUser _stageUser = null;

	/**
	 * Default Constructor
	 */
	public OBUserTrxValue() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBUserTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICommonUserTrxValue object
	 */
	public OBUserTrxValue(ICommonUserTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITrxValue object
	 */
	public OBUserTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Get User
	 * 
	 * @return ICommonUser
	 */
	public ICommonUser getUser() {
		return _user;
	}

	/**
	 * Get Staging User
	 * 
	 * @return ICommonUser
	 */
	public ICommonUser getStagingUser() {
		return _stageUser;
	}

	/**
	 * Set User
	 * 
	 * @param value is of type ICommonUser
	 */
	public void setUser(ICommonUser user) {
		_user = user;
	}

	/**
	 * Set Staging User
	 * 
	 * @param value is of type ICommonUser
	 */
	public void setStagingUser(ICommonUser user) {
		_stageUser = user;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
