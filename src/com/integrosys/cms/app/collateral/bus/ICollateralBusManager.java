/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralBusManager.java,v 1.31 2005/07/18 09:50:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;

/**
 * This interface defines the services of a collateral business manager.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.31 $
 * @since $Date: 2005/07/18 09:50:55 $: 2003/01/17 09:49:22 $ Tag: $Name: $
 */
public interface ICollateralBusManager extends Serializable {
	/**
	 * Get the complete collateral given its collateral id.
	 * 
	 * @param collateralID collateral id in CMS
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral getCollateral(long collateralID) throws CollateralException;

	/**
	 * Get common collateral information given its collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral object
	 * @throws CollateralException on any errors encountered
	 */
	public ICollateral getCommonCollateral(long collateralID) throws CollateralException;

	/**
	 * Search collateral based on the criteria specified.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result
	 * @throws CollateralException on errors encountered
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws CollateralException;

	/**
	 * Create a new collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral createCollateral(ICollateral collateral) throws CollateralException;

	/**
	 * Update a collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCollateral(ICollateral collateral) throws CollateralException;

	/**
	 * Update commodity pre-condition.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updatePreCondition(ICollateral collateral) throws CollateralException;

	/**
	 * Update common collateral information.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCommonCollateral(ICollateral collateral) throws CollateralException;

	/**
	 * Get the valuation given its valuation id.
	 * 
	 * @param valuationID valuation id
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation getValuation(long valuationID) throws CollateralException;

	/**
	 * Create a new valuation
	 * 
	 * @param valuation is of type IValuation
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation createValuation(IValuation valuation) throws CollateralException;

	/**
	 * Get a list of collateral subtypes belong to the type given.
	 * 
	 * @param colType of type ICollateralType
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error finding the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException;

	/**
	 * Get a list of collateral subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByGroupID(long groupID) throws CollateralException;

	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws CollateralException on error getting the collateral types
	 */
	public ICollateralType[] getAllCollateralTypes() throws CollateralException;

	/**
	 * Get a list of collateral subtypes in CMS.
	 * 
	 * @return ICollateralSubType[]
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException;

	/**
	 * Get security parameter in CMS using country ISO code and security type
	 * code.
	 * 
	 * @param countryCode country code
	 * @param typeCode security type code
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 */
	public ICollateralParameter[] getCollateralParameter(String countryCode, String typeCode)
			throws CollateralException;

	/**
	 * Get security parameter in CMS by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 */
	public ICollateralParameter[] getCollateralParameter(long groupID) throws CollateralException;

	/**
	 * Get security parameter value given the country code and security subtype
	 * code.
	 * 
	 * @param countryCode country ISO code
	 * @param subTypeCode security subtype code
	 * @return security parameter
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameter getColParamByCountryAndSubTypeCode(String countryCode, String subTypeCode)
			throws CollateralException;

	/**
	 * Creates a list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return a newly created collateral subtypes
	 * @throws CollateralException on erros creating the security subtypes
	 */
	public ICollateralSubType[] createCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException;

	/**
	 * Updates the input list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return updated list of collateral subtypes
	 * @throws CollateralException on error updating the subtypes
	 */
	public ICollateralSubType[] updateCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException;

	/**
	 * Creates a list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on errors creating the security parameters
	 */
	public ICollateralParameter[] createCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException;

	/**
	 * Updates the input list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on error updating the security parameters
	 */
	public ICollateralParameter[] updateCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException;

	/**
	 * Get the pledgor info based on the pledgor ID
	 * 
	 * @param aPledgorID of long type
	 * @return IPledgor - the pledgor info
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgor(long aPledgorID) throws CollateralException;

	/**
	 * Get the pledgor info based on the pledgor ID from SCI
	 * 
	 * @param sciPledgorID pledgor id from SCI of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException;

	/**
	 * Get a list of collateral asset lifes in CMS.
	 * 
	 * @return a list of collateral asset lifes
	 * @throws CollateralException on error finding the collateral asset lifes
	 */
	public ICollateralAssetLife[] getCollateralAssetLife() throws CollateralException;

	/**
	 * Get a list of collateral asset lifes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral asset lifes
	 * @throws CollateralException on error getting the collateral asset lifes
	 */
	public ICollateralAssetLife[] getCollateralAssetLifeByGroupID(long groupID) throws CollateralException;

	/**
	 * Creates a list of collateral asset lifes.
	 * 
	 * @param assetLifes a list of collateral asset lifes
	 * @return a newly created collateral asset lifes
	 * @throws CollateralException on erros creating the security asset lifes
	 */
	public ICollateralAssetLife[] createCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException;

	/**
	 * Updates the input list of collateral asset lifes.
	 * 
	 * @param assetLifes a list of collateral asset lifes
	 * @return updated list of collateral asset lifes
	 * @throws CollateralException on error updating the asset lifes
	 */
	public ICollateralAssetLife[] updateCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException;

	/**
	 * Gets the MF Checklist details by collateral ID.
	 * 
	 * @param collateralID collateral ID
	 * @return IMFChecklist
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklist getMFChecklistByCollateralID(long collateralID) throws CollateralException;

	/**
	 * Gets the MF Checklist details by MF Checklist ID.
	 * 
	 * @param mFChecklistID MF Checklist ID
	 * @return IMFChecklist
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException;

	/**
	 * Create the input of MF Checklist.
	 * 
	 * @param value MF Checklist details of type IMFChecklist
	 * @return IMFChecklist
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklist createMFChecklist(IMFChecklist value) throws CollateralException;

	/**
	 * Update the input of MF Checklist.
	 * 
	 * @param value MF Checklist details of type IMFChecklist
	 * @return IMFChecklist
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklist updateMFChecklist(IMFChecklist value) throws CollateralException;

}
