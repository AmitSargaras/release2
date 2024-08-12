/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/SBCollateralBusManager.java,v 1.32 2006/07/27 04:36:39 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;

/**
 * Remote interface to the collateral business manager session bean.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.32 $
 * @since $Date: 2006/07/27 04:36:39 $: 2003/01/17 09:49:22 $ Tag: $Name: $
 */
public interface SBCollateralBusManager extends EJBObject {
	/**
	 * Get the complete collateral given its collateral id.
	 * 
	 * @param collateralID collateral id in CMS
	 * @return collateral
	 * @throws CollateralException on errors getting the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral getCollateral(long collateralID) throws CollateralException, RemoteException;

	/**
	 * Get common collateral information given its collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral object
	 * @throws CollateralException on errors getting the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral getCommonCollateral(long collateralID) throws CollateralException, RemoteException;

	/**
	 * Search collateral based on the criteria specified.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws CollateralException, RemoteException;

	/**
	 * Create a new collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral createCollateral(ICollateral collateral) throws CollateralException, RemoteException;

	/**
	 * Update a collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors updating the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral updateCollateral(ICollateral collateral) throws CollateralException, RemoteException;

	/**
	 * Update commodity pre-condition.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public ICollateral updatePreCondition(ICollateral collateral) throws CollateralException, RemoteException;

	/**
	 * Update common collateral information.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral updateCommonCollateral(ICollateral collateral) throws CollateralException, RemoteException;

	/**
	 * Get the valuation given its valuation id.
	 * 
	 * @param valuationID valuation id
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public IValuation getValuation(long valuationID) throws CollateralException, RemoteException;

	/**
	 * Create a new valuation
	 * 
	 * @param valuation is of type IValuation
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public IValuation createValuation(IValuation valuation) throws CollateralException, RemoteException;

	/**
	 * Get a list of collateral subtypes belong to the type given.
	 * 
	 * @param colType of type ICollateralType
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error finding the collateral subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException,
			RemoteException;

	/**
	 * Get a list of collateral subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error getting the collateral subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] getCollateralSubTypeByGroupID(long groupID) throws CollateralException, RemoteException;

	/**
	 * Get a list of collateral subtypes in CMS.
	 * 
	 * @return ICollateralSubType[]
	 * @throws CollateralException on error getting the collateral subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException, RemoteException;

	/**
	 * Get security parameter in CMS using country ISO code and security type
	 * code.
	 * 
	 * @param countryCode country code
	 * @param typeCode security type code
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] getCollateralParameter(String countryCode, String typeCode)
			throws CollateralException, RemoteException;

	/**
	 * Get security parameter in CMS by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] getCollateralParameter(long groupID) throws CollateralException, RemoteException;

	/**
	 * Get security parameter value given the country code and security subtype
	 * code.
	 * 
	 * @param countryCode country ISO code
	 * @param subTypeCode security subtype code
	 * @return security parameter
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter getColParamByCountryAndSubTypeCode(String countryCode, String subTypeCode)
			throws CollateralException, RemoteException;

	/**
	 * Creates a list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return a newly created collateral subtypes
	 * @throws CollateralException on erros creating the security subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] createCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException,
			RemoteException;

	/**
	 * Creates a list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on errors creating the security parameters
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] createCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException, RemoteException;

	/**
	 * Updates the input list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on error updating the security parameters
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralParameter[] updateCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException, RemoteException;

	/**
	 * Updates the input list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return updated list of collateral subtypes
	 * @throws CollateralException on error updating the subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] updateCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException,
			RemoteException;

	/**
	 * Get all collateral types avaiable in CMS.
	 * 
	 * @return a list of collateral types
	 * @throws CollateralException on error getting the collateral types
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralType[] getAllCollateralTypes() throws CollateralException, RemoteException;

	/**
	 * Get the pledgor info based on the pledgor ID
	 * 
	 * @param aPledgorID of long type
	 * @return IPledgor - the pledgor info
	 * @throws CollateralException on errors getting the pledgor info
	 * @throws RemoteException on error during remote method call
	 */
	public IPledgor getPledgor(long aPledgorID) throws CollateralException, RemoteException;

	/**
	 * Get the pledgor info based on the pledgor ID from SCI
	 * 
	 * @param sciPledgorID pledgor id from SCI of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 * @throws RemoteException on error during remote method call
	 */
	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException, RemoteException;

	public List getLimitDetailForNewApportionment(String collateralId) throws CollateralException, RemoteException;

	public boolean checkSecurityRequireApportion(String collateralId) throws CollateralException, RemoteException;

	/**
	 * Get a list of collateral assetlife.
	 * 
	 * @return a list of collateral assetlife
	 * @throws CollateralException on error finding the collateral assetlife
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralAssetLife[] getCollateralAssetLife() throws CollateralException, RemoteException;

	/**
	 * Get a list of collateral assetlife by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral assetlife
	 * @throws CollateralException on error getting the collateral assetlife
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralAssetLife[] getCollateralAssetLifeByGroupID(long groupID) throws CollateralException,
			RemoteException;

	/**
	 * Creates a list of collateral assetlife.
	 * 
	 * @param assetLife a list of collateral assetlife
	 * @return a newly created collateral assetlife
	 * @throws CollateralException on erros creating the security assetlife
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralAssetLife[] createCollateralAssetLifes(ICollateralAssetLife[] assetLife)
			throws CollateralException, RemoteException;

	/**
	 * Updates the input list of collateral assetlife.
	 * 
	 * @param assetLife a list of collateral assetlife
	 * @return updated list of collateral assetlife
	 * @throws CollateralException on error updating the assetlife
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralAssetLife[] updateCollateralAssetLifes(ICollateralAssetLife[] assetLife)
			throws CollateralException, RemoteException;

	public ICollateral createCollateralWithPledgor(ICollateral collateral) throws CollateralException, RemoteException;

	public Long getCmsCollateralId(String securityId, String source) throws CollateralException, RemoteException;

	public Collection getCmsCollateralIdList(String securityId, String source) throws CollateralException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklistByCollateralID
	 */
	public IMFChecklist getMFChecklistByCollateralID(long collateralID) throws CollateralException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklist
	 */
	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#createMFChecklist
	 */
	public IMFChecklist createMFChecklist(IMFChecklist value) throws CollateralException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#updateMFChecklist
	 */
	public IMFChecklist updateMFChecklist(IMFChecklist value) throws CollateralException, RemoteException;

	/**
	 * Create the limit charge map if charge details is found and it is not link
	 * to the limit when security is link to limit from manual input.
	 * 
	 * @param lmtChargeMap limit charge map details of type ILimitChargeMap
	 * @return ICollateral
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateral createLimitChargeMap(ILimitChargeMap lmtChargeMap) throws CollateralException, RemoteException;

	/**
	 * Remove the limit charge map if charge details is found and it is link to
	 * the limit when security is unlink from limit from manual input.
	 * 
	 * @param lmtChargeMap limit charge map details of type ILimitChargeMap
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public void removeLimitChargeMap(ILimitChargeMap lmtChargeMap) throws CollateralException, RemoteException;

	/**
	 * Gets the collateral limit map details by collateral ID and cms limit ID
	 * 
	 * @param collateralID collateral ID
	 * @param cmsLimitID cms limit ID
	 * @return ICollateralLimitMap
	 * @throws CollateralException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralLimitMap getCollateralLimitMap(long collateralID, long cmsLimitID) throws CollateralException,
			RemoteException;

}