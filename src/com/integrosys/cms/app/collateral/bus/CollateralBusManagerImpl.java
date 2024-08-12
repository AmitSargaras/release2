/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralBusManagerImpl.java,v 1.35 2005/07/18 09:52:41 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.exception.ExceptionUtil;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ICollateralManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2005/07/18 09:52:41 $ Tag: $Name: $
 */
public class CollateralBusManagerImpl extends AbstractCollateralBusManager {

	private static final long serialVersionUID = -7376453897451759717L;

	/**
	 * Get the collateral given its collateral id.
	 * 
	 * @param collateralID collateral id in CMS
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral getCollateral(long collateralID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			ICollateral col = theEjb.getCollateral(collateralID);
			if (CollateralDetailFactory.canCollateralMaintainMultipleCharge(col)) {
				ILimitCharge[] charges = col.getLimitCharges();
				col.setLimitCharges(filterChargeMap(charges));
			}
			return col;
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Helper method to filter out charge map.
	 * 
	 * @param charges of type ILimitCharge[]
	 * @return a list of charges with update limit map
	 */
	private ILimitCharge[] filterChargeMap(ILimitCharge[] charges) {
		if (charges != null) {
			for (int i = 0; i < charges.length; i++) {
				ICollateralLimitMap[] maps = charges[i].getLimitMaps();
				if (maps != null) {
					ArrayList mapList = new ArrayList();
					for (int j = 0; j < maps.length; j++) {
						if ((maps[j].getSCILimitID() != null) && (maps[j].getSCILimitID().length() > 0)) {
							mapList.add(maps[j]);
						}
					}
					charges[i].setLimitMaps((ILimitChargeMap[]) mapList.toArray(new OBLimitChargeMap[0]));
				}
			}
		}
		return charges;
	}

	/**
	 * Get common collateral information given its collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral object
	 * @throws CollateralException on any errors encountered
	 */
	public ICollateral getCommonCollateral(long collateralID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			ICollateral col = theEjb.getCommonCollateral(collateralID);
			if (CollateralDetailFactory.canCollateralMaintainMultipleCharge(col)) {
				ILimitCharge[] charges = col.getLimitCharges();
				col.setLimitCharges(filterChargeMap(charges));
			}
			return col;
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Search collateral based on the criteria specified.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result
	 * @throws CollateralException on errors encountered
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.searchCollateral(criteria);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Create a new collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral createCollateral(ICollateral collateral) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createCollateral(collateral);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Update a collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCollateral(ICollateral collateral) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCollateral(collateral);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Update commodity pre-condition.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updatePreCondition(ICollateral collateral) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updatePreCondition(collateral);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Update common collateral information.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCommonCollateral(ICollateral collateral) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCommonCollateral(collateral);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get the valuation given its valuation id.
	 * 
	 * @param valuationID valuation id
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation getValuation(long valuationID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getValuation(valuationID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Create a new valuation
	 * 
	 * @param valuation is of type IValuation
	 * @return valuation
	 * @throws CollateralException on errors encountered
	 */
	public IValuation createValuation(IValuation valuation) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createValuation(valuation);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get a list of collateral subtypes belong to the type given.
	 * 
	 * @param colType of type ICollateralType
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error finding the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByType(ICollateralType colType) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralSubTypeByType(colType);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get a list of collateral subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral subtypes
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypeByGroupID(long groupID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralSubTypeByGroupID(groupID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

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
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralParameter(countryCode, typeCode);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get security parameter in CMS by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of security parameters
	 * @throws CollateralException on error getting the security parameters
	 */
	public ICollateralParameter[] getCollateralParameter(long groupID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralParameter(groupID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

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
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getColParamByCountryAndSubTypeCode(countryCode, subTypeCode);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Creates a list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return a newly created collateral subtypes
	 * @throws CollateralException on erros creating the security subtypes
	 */
	public ICollateralSubType[] createCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createCollateralSubTypes(subTypes);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Updates the input list of collateral subtypes.
	 * 
	 * @param subTypes a list of collateral subtypes
	 * @return updated list of collateral subtypes
	 * @throws CollateralException on error updating the subtypes
	 */
	public ICollateralSubType[] updateCollateralSubTypes(ICollateralSubType[] subTypes) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCollateralSubTypes(subTypes);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Creates a list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on errors creating the security parameters
	 */
	public ICollateralParameter[] createCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createCollateralParameters(colParams);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Updates the input list of collateral parameters.
	 * 
	 * @param colParams a list of security parameters
	 * @return a list of security parameters
	 * @throws CollateralException on error updating the security parameters
	 */
	public ICollateralParameter[] updateCollateralParameters(ICollateralParameter[] colParams)
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCollateralParameters(colParams);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get a list of collateral subtypes in CMS.
	 * 
	 * @return ICollateralSubType[]
	 * @throws CollateralException on error getting the collateral subtypes
	 */
	public ICollateralSubType[] getAllCollateralSubTypes() throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getAllCollateralSubTypes();
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get all collateral types avaiable in CMS.
	 * 
	 * @return a list of collateral types
	 * @throws CollateralException on error getting the collateral types
	 */
	public ICollateralType[] getAllCollateralTypes() throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getAllCollateralTypes();
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get the pledgor info based on the pledgor ID
	 * 
	 * @param aPledgorID of long type
	 * @return IPledgor - the pledgor info
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgor(long aPledgorID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getPledgor(aPledgorID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get the pledgor info based on the pledgor ID from SCI
	 * 
	 * @param sciPledgorID pledgor id from SCI of long type
	 * @return pledgor information
	 * @throws CollateralException on errors getting the pledgor info
	 */
	public IPledgor getPledgorBySCIPledgorID(long sciPledgorID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();
		try {
			return theEjb.getPledgorBySCIPledgorID(sciPledgorID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get a list of collateral asset lifes.
	 * 
	 * @return a list of collateral asset lifes
	 * @throws CollateralException on error finding the collateral asset lifes
	 */
	public ICollateralAssetLife[] getCollateralAssetLife() throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralAssetLife();
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Get a list of collateral asset lifes by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of collateral asset lifes
	 * @throws CollateralException on error getting the collateral asset lifes
	 */
	public ICollateralAssetLife[] getCollateralAssetLifeByGroupID(long groupID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getCollateralAssetLifeByGroupID(groupID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Creates a list of collateral asset lifes.
	 * 
	 * @param assetLifes a list of collateral asset lifes
	 * @return a newly created collateral asset lifes
	 * @throws CollateralException on erros creating the security asset lifes
	 */
	public ICollateralAssetLife[] createCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createCollateralAssetLifes(assetLifes);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * Updates the input list of collateral asset lifes.
	 * 
	 * @param assetLifes a list of collateral asset lifes
	 * @return updated list of collateral subtypes
	 * @throws CollateralException on error updating the asset lifes
	 */
	public ICollateralAssetLife[] updateCollateralAssetLifes(ICollateralAssetLife[] assetLifes)
			throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCollateralAssetLifes(assetLifes);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklistByCollateralID
	 */
	public IMFChecklist getMFChecklistByCollateralID(long collateralID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getMFChecklistByCollateralID(collateralID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#getMFChecklist
	 */
	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.getMFChecklist(mFChecklistID);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#createMFChecklist
	 */
	public IMFChecklist createMFChecklist(IMFChecklist value) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.createMFChecklist(value);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.ICollateralBusManager#updateMFChecklist
	 */
	public IMFChecklist updateMFChecklist(IMFChecklist value) throws CollateralException {
		SBCollateralBusManager theEjb = getBusManager();

		try {
			return theEjb.updateMFChecklist(value);
		}
		catch (RemoteException e) {
			ExceptionUtil.handleRemoteException(e);
			return null;
		}
	}

	/**
	 * helper method to get an ejb object to collateral business manager session
	 * bean.
	 * 
	 * @return collateral manager ejb object
	 * @throws CollateralException on errors encountered
	 */
	protected SBCollateralBusManager getBusManager() throws CollateralException {
		SBCollateralBusManager theEjb = (SBCollateralBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI, SBCollateralBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new CollateralException("Failed to find CollateralBusManager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI + "]");
		}

		return theEjb;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CollateralException on errors encountered
	 */
	protected void rollback() throws CollateralException {
	}

}