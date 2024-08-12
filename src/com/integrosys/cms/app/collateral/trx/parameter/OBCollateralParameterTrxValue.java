/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralParameterTrxValue.java,v 1.3 2003/08/13 14:40:45 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging collateral parameters for transaction usage.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/13 14:40:45 $ Tag: $Name: $
 */
public class OBCollateralParameterTrxValue extends OBCMSTrxValue implements ICollateralParameterTrxValue {
	private ICollateralParameter[] actual;

	private ICollateralParameter[] staging;

	private String countryCode;

	private String collateralTypeCode;

	/**
	 * Default Constructor
	 */
	public OBCollateralParameterTrxValue() {
		super();
		setTransactionType(ICMSConstant.INSTANCE_COL_PARAMETER);
	}

	/**
	 * Construct an object from its interface.
	 * 
	 * @param obj is of type ICollateralParametersTrxValue
	 */
	public OBCollateralParameterTrxValue(ICollateralParameterTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBCollateralParameterTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get actual security parameter contained in this transaction.
	 * 
	 * @return objects of type ICollateralParameter
	 */
	public ICollateralParameter[] getCollateralParameters() {
		return actual;
	}

	/**
	 * Set actual security parameter to this transaction.
	 * 
	 * @param colParam of type ICollateralParameter[]
	 */
	public void setCollateralParameters(ICollateralParameter[] colParam) {
		actual = colParam;
	}

	/**
	 * Get staging security parameter contained in this transaction.
	 * 
	 * @return objects of type ICollateralParameter
	 */
	public ICollateralParameter[] getStagingCollateralParameters() {
		return staging;
	}

	/**
	 * Set staging security parameter to this transaction.
	 * 
	 * @param stagingColParam of type ICollateralParameter[]
	 */
	public void setStagingCollateralParameters(ICollateralParameter[] stagingColParam) {
		staging = stagingColParam;
	}

	/**
	 * Get country code.
	 * 
	 * @return String
	 */
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * Set country code.
	 * 
	 * @param countryCode of type String
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Set security type code.
	 * 
	 * @return String
	 */
	public String getCollateralTypeCode() {
		return collateralTypeCode;
	}

	/**
	 * Set security type code.
	 * 
	 * @param collateralTypeCode of type String
	 */
	public void setCollateralTypeCode(String collateralTypeCode) {
		this.collateralTypeCode = collateralTypeCode;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}