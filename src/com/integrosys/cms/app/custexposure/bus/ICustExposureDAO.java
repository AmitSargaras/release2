package com.integrosys.cms.app.custexposure.bus;

import java.util.Map;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Customer exposure data access object interface 
 * @author skchai
 *
 */
public interface ICustExposureDAO {

	/**
	 * Get dependent exposure 
	 * @param criteria
	 * @return
	 * @throws SearchDAOException
	 */
	public Map getCustExposureRecords(CustExposureSearchCriteria criteria)
			throws SearchDAOException;

	/**
	 * Get limit profile id by given sub profile id
	 * @param subProfileID
	 * @return
	 * @throws SearchDAOException
	 * TODO : should migrate to Limit module
	 */
	public List getlimitProfileIDBySubProfileID(long subProfileID)
			throws SearchDAOException;

	/**
	 * Get CCI No with the given sub profile Id
	 * @param subProfileId
	 * @return
	 * TODO : should migrate to CCI module
	 */
	public long getCCINoBySubProfileId(long subProfileId);

	/**
	 * Getting the bank entity with the given branch code
	 * @param orgCode
	 * @return
	 */
	public String getBankEntityByOrgCode(String orgCode);
	
    /**
	 * Retrieve the latest OMV per collateral
	 * @param collateralId
	 * @param sourceType
	 * @return
	 * @throws Exception
	 */
	public Amount retrieveLatestValuationByCollateralId(String collateralId) throws Exception;
	
	/**
     * getContingentLiabilitiesResults that belongs to the customer
     * @param criteria
     * @param dbUtil
     * @param rs
     * @return   map
     */
    public Map getContingentLiabilitiesResults(CustExposureSearchCriteria criteria);

}
