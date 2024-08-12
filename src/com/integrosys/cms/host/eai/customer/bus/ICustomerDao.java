package com.integrosys.cms.host.eai.customer.bus;

import java.util.List;

import com.integrosys.cms.host.eai.core.IPersistentDao;

/**
 * ORM based DAO to deal with objects of customer modules.
 * 
 * @author Chong Jun Yong
 * @since 13.08.2008
 */
public interface ICustomerDao extends IPersistentDao {
	/**
	 * Search the customers based on the ID Info provided
	 * 
	 * @param idInfo the customer ID info to be used to search customer
	 * @return list of customer match the criteria or empty list if there is
	 *         none record
	 */
	public List searchCustomerByCustomerIdInfo(CustomerIdInfo idInfo);

	/**
	 * <p>
	 * Search the customer based on the ID Info, short customer name, cif source
	 * and cif id
	 * 
	 * <p>
	 * ID info must be provided, short customer name or cif source or cif id,
	 * either one must be provided.
	 * 
	 * @param idInfo the customer ID info to be used to search customer
	 * @param shortCustomerName customer short name
	 * @param cifSource customer source
	 * @param cifId customer cif number
	 * @return list of customer match the criteria or empty list if there is
	 *         none record
	 */
	public List searchCustomerByCustomerIdInfoAndShortNameAndCifSource(CustomerIdInfo idInfo, String shortCustomerName,
			String cifSource, String cifId);

	/**
	 * 
	 * @param cifNO String
	 * @param cifSource String
	 * @return MainProfile match the given cifNo and cifSource
	 */
	public MainProfile searchMainProfileByCIFAndCIFSource(String cifNO, String cifSource);

}
