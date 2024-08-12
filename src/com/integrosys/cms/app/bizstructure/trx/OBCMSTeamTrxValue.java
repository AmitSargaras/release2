/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/bizstructure/trx/OBCMSTeamTrxValue.java,v 1.1 2003/07/28 09:27:17 kllee Exp $
 */
package com.integrosys.cms.app.bizstructure.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;

/**
 * This class represents the transaction object for CMS.
 * 
 * @author Alfred Lee
 */
public class OBCMSTeamTrxValue extends OBCMSTrxValue implements ITeamTrxValue {
	private ITeam _team = null;

	private ITeam _stageTeam = null;

	/**
	 * Default Constructor
	 */
	public OBCMSTeamTrxValue() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBCMSTeamTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITeamTrxValue object
	 */
	public OBCMSTeamTrxValue(ITeamTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITrxValue object
	 */
	public OBCMSTeamTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Get User
	 * 
	 * @return ITeam
	 */
	public ITeam getTeam() {
		return _team;
	}

	/**
	 * Get Staging User
	 * 
	 * @return ITeam
	 */
	public ITeam getStagingTeam() {
		return _stageTeam;
	}

	/**
	 * Set User
	 * 
	 * @param value is of type ITeam
	 */
	public void setTeam(ITeam value) {
		_team = value;
	}

	/**
	 * Set Staging User
	 * 
	 * @param value is of type ITeam
	 */
	public void setStagingTeam(ITeam value) {
		_stageTeam = value;
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
