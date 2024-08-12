/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralParameterDAO.java,v 1.3 2003/08/19 10:42:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * DAO for security parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/19 10:42:24 $ Tag: $Name: $
 */
public interface ICollateralParameterDAO {
	/**
	 * Get security parameter based on the country code and security type code.
	 * 
	 * @param country country code
	 * @param colType security type code
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameters
	 */
	public ICollateralParameter[] getCollateralParameters(String country, String colType) throws SearchDAOException;

	/**
	 * Get security parameter based on the country code and security type code.
	 * 
	 * @param groupID security parameter group id
	 * @return a list of security parameters
	 * @throws SearchDAOException on error searching the security parameters
	 */
	public ICollateralParameter[] getCollateralParameters(long groupID) throws SearchDAOException;

	/**
	 * Get CRP value given the country code and security subtype code.
	 * 
	 * @param countryCode country code
	 * @param subTypeCode security subtype code
	 * @return crp value
	 * @throws SearchDAOException on error getting the crp value
	 */
	public double getCRP(String countryCode, String subTypeCode) throws SearchDAOException;
}
