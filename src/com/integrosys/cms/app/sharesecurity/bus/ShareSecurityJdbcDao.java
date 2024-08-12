package com.integrosys.cms.app.sharesecurity.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * JDBC based DAO to be used by the share security module.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ShareSecurityJdbcDao {

	/**
	 * Retrieve the list of shared collateral having security id, type providing
	 * the cms collateral key.
	 * 
	 * @param collateralId cms collateral id
	 * @return collateral security id and type
	 * @throws SearchDAOException if there is any error encountered in the jdbc
	 */
	public List getSharedSecNameForCollateral(Long collateralId) throws SearchDAOException;

	/**
	 * Retrieve the shared collateral having security id, type providing the cms
	 * collateral key list, key is the cms collateral key <b>(in string)</b>,
	 * value is the security id and type.
	 * 
	 * @param collateralIdList list of cms collateral id
	 * @return collateral security id and type backed by the cms collateral id
	 * @throws SearchDAOException if there is any error encountered in the jdbc
	 */
	public Map getSharedSecNameForCollaterals(List collateralIdList) throws SearchDAOException;

	/**
	 * Create the shared security instance
	 * 
	 * @param shareSecurity the shared security instance
	 * @throws SearchDAOException if there is any error encountered in the jdbc
	 */
	public void createSharedSecurity(IShareSecurity shareSecurity) throws SearchDAOException;

}
