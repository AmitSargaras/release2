/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ICollateralParameterTrxValue.java,v 1.3 2003/08/13 14:40:04 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual collateral parameters and staging collateral parameters for
 * transaction usage.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/13 14:40:04 $ Tag: $Name: $
 */
public interface ICollateralParameterTrxValue extends ICMSTrxValue {
	/**
	 * Get actual security parameter contained in this transaction.
	 * 
	 * @return objects of type ICollateralParameter
	 */
	public ICollateralParameter[] getCollateralParameters();

	/**
	 * Set actual security parameter to this transaction.
	 * 
	 * @param colParam of type ICollateralParameter[]
	 */
	public void setCollateralParameters(ICollateralParameter[] colParam);

	/**
	 * Get staging security parameter contained in this transaction.
	 * 
	 * @return objects of type ICollateralParameter
	 */
	public ICollateralParameter[] getStagingCollateralParameters();

	/**
	 * Set staging security parameter to this transaction.
	 * 
	 * @param stagingColParam of type ICollateralParameter[]
	 */
	public void setStagingCollateralParameters(ICollateralParameter[] stagingColParam);

	/**
	 * Get country code.
	 * 
	 * @return String
	 */
	public String getCountryCode();

	/**
	 * Set country code.
	 * 
	 * @param countryCode of type String
	 */
	public void setCountryCode(String countryCode);

	/**
	 * Set security type code.
	 * 
	 * @return String
	 */
	public String getCollateralTypeCode();

	/**
	 * Set security type code.
	 * 
	 * @param collateralTypeCode of type String
	 */
	public void setCollateralTypeCode(String collateralTypeCode);
}
