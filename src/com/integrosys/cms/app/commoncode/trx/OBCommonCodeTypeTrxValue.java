package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBCommonCodeTypeTrxValue extends OBCMSTrxValue implements ICommonCodeTypeTrxValue {

	private ICommonCodeType commonCodeType = null;

	private ICommonCodeType stageCommonCodeType = null;

	private ICommonCodeType[] commonCodeTypeList = null;

	/**
	 * Default Constructor
	 */
	public OBCommonCodeTypeTrxValue() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBCommonCodeTypeTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the IBusinessParameterGroupTrxValue object
	 */
	public OBCommonCodeTypeTrxValue(ICommonCodeTypeTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITrxValue object
	 */
	public OBCommonCodeTypeTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Get User
	 * 
	 * @return IBusinessParameterGroup
	 */
	public ICommonCodeType getCommonCodeType() {
		return commonCodeType;
	}

	/**
	 * Get Staging User
	 * 
	 * @return IBusinessParameterGroup
	 */
	public ICommonCodeType getStagingCommonCodeType() {
		return stageCommonCodeType;
	}

	/**
	 * Get Common Code Type List
	 * 
	 * @return IBusinessParameterGroup
	 */
	public ICommonCodeType[] getCommonCodeTypeList() {
		return commonCodeTypeList;
	}

	/**
	 * Set User
	 * 
	 * @param value is of type IBusinessParameterGroup
	 */
	public void setCommonCodeType(ICommonCodeType value) {
		commonCodeType = value;
	}

	/**
	 * Set Staging User
	 * 
	 * @param value is of type IBusinessParameterGroup
	 */
	public void setStagingCommonCodeType(ICommonCodeType value) {
		stageCommonCodeType = value;
	}

	/**
	 * Set Common Code Type List
	 * 
	 * @param value is of type IBusinessParameterGroup
	 */
	public void setCommonCodeTypeList(ICommonCodeType[] value) {
		commonCodeTypeList = value;
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
