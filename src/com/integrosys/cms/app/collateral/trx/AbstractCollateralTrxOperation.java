/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/AbstractCollateralTrxOperation.java,v 1.25 2006/10/10 08:04:36 jzhan Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.sharesecurity.bus.OBShareSecurity;
import com.integrosys.cms.app.sharesecurity.bus.ShareSecurityJdbcDao;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;

/**
 * Abstract class that contain methods that is common among the set of
 * collateral trx operations.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2006/10/10 08:04:36 $ Tag: $Name: $
 */
public abstract class AbstractCollateralTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private static final long serialVersionUID = -5494252274529767381L;

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	private ShareSecurityJdbcDao shareSecurityJdbcDao;

	/**
	 * @return the actualCollateralBusManager
	 */
	public ICollateralBusManager getActualCollateralBusManager() {
		return actualCollateralBusManager;
	}

	/**
	 * @return the stagingCollateralBusManager
	 */
	public ICollateralBusManager getStagingCollateralBusManager() {
		return stagingCollateralBusManager;
	}

	/**
	 * @param actualCollateralBusManager the actualCollateralBusManager to set
	 */
	public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
		this.actualCollateralBusManager = actualCollateralBusManager;
	}

	/**
	 * @param stagingCollateralBusManager the stagingCollateralBusManager to set
	 */
	public void setStagingCollateralBusManager(ICollateralBusManager stagingCollateralBusManager) {
		this.stagingCollateralBusManager = stagingCollateralBusManager;
	}

	public void setShareSecurityJdbcDao(ShareSecurityJdbcDao shareSecurityJdbcDao) {
		this.shareSecurityJdbcDao = shareSecurityJdbcDao;
	}

	public ShareSecurityJdbcDao getShareSecurityJdbcDao() {
		return shareSecurityJdbcDao;
	}

	public abstract ITrxResult performProcess(ITrxValue value) throws TrxOperationException;

	/**
	 * Helper method to cast a generic trx value object to a collateral specific
	 * transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return collateral specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICollateralTrxValue
	 */
	protected ICollateralTrxValue getCollateralTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICollateralTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICollateralTrxValue: " + e.toString());
		}
	}

	/**
	 * Create actual collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors creating the collateral
	 */
	protected ICollateralTrxValue createActualCollateral(ICollateralTrxValue value) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "===============================105-6-1=========create============= ");
			ICollateral col = value.getStagingCollateral();
			DefaultLogger.debug(this, "===============================105-6-2=========create============= "+col);
			col = getActualCollateralBusManager().createCollateral(col);
			DefaultLogger.debug(this, "===============================105-6-3=========create============= "+col.getCollateralID());
			IShareSecurity shareSec = new OBShareSecurity();
			shareSec.setCmsCollateralId(col.getCollateralID());
			shareSec.setSourceSecurityId(col.getSCISecurityID());
			shareSec.setStatus(ICMSConstant.STATE_ACTIVE);
			shareSec.setSourceId(col.getSourceId());
			shareSec.setLastUpdatedTime(new Date());
			DefaultLogger.debug(this, "===============================105-6-4=========create============= "+shareSec);
			getShareSecurityJdbcDao().createSharedSecurity(shareSec);
			DefaultLogger.debug(this, "===============================105-6-5=========create============= ");
			value.setCollateral(col);
			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to create actual collateral using staging collateral, collateral id ["
							+ value.getStagingReferenceID() + "]", e);
		}
	}

	/**
	 * Create staging collatral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralTrxValue createStagingCollateral(ICollateralTrxValue value) throws TrxOperationException {
		try {
			ICollateral col = value.getStagingCollateral();
			if (value.getCollateral() != null) {
				col.setCollateralID(value.getCollateral().getCollateralID());
			}
			
			//java.util.List secApportionment = col.getSecApportionment();
			col = getStagingCollateralBusManager().createCollateral(col);
			//col.setSecApportionment(secApportionment);

			if (value.getCollateral() != null) {
				if ((col.getPledgors() == null) || (col.getPledgors().length == 0)) {
					col.setPledgors(value.getCollateral().getPledgors());
				}
			}
			
			/*if(col instanceof IMarketableCollateral){
				IMarketableEquity[] equityList = ((IMarketableCollateral) value.getStagingCollateral()).getEquityList();
				if(equityList != null){
					((IMarketableCollateral) col).getEquityList()[0].getValuationUnitPrice();
					((IMarketableCollateral) col).setEquityList(equityList);
				}
			}*/
			
			value.setStagingCollateral(col);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to create staging collateral using staging collateral, collateral id ["
							+ value.getStagingReferenceID() + "]", e);
		}
	}

	/**
	 * Update staging collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateStagingCollateral(ICollateralTrxValue value) throws TrxOperationException {
		try {
			ICollateral staging = value.getStagingCollateral();

			staging = getStagingCollateralBusManager().updateCollateral(staging);

			value.setStagingCollateral(staging);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to update staging collateral using staging collateral, collateral id ["
							+ value.getStagingReferenceID() + "]", e);
		}
	}

	/**
	 * Update staging common collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateStagingCommonCollateral(ICollateralTrxValue value) throws TrxOperationException {
		try {
			ICollateral staging = value.getStagingCollateral();

			staging = getStagingCollateralBusManager().updateCommonCollateral(staging);

			value.setStagingCollateral(staging);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to update staging common collateral using staging collateral, collateral id ["
							+ value.getStagingReferenceID() + "]", e);
		}
	}

	/**
	 * Update actual collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateActualCollateral(ICollateralTrxValue value) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "===============================105-4-1=========update============= ");
			ICollateral actual = value.getCollateral();
			ICollateral staging = value.getStagingCollateral(); // update from

			long stageColID = staging.getCollateralID();
			long stageVersion = staging.getVersionTime();
			String stageStatus = staging.getStatus();
			DefaultLogger.debug(this, "===============================105-4-4=========update=====stageColID======== "+stageColID);
			DefaultLogger.debug(this, "===============================105-4-5=========update=====actual.getCollateralID()======== "+actual.getCollateralID());
			staging.setCollateralID(actual.getCollateralID()); // but maintain
			// actual's pk
			staging.setVersionTime(actual.getVersionTime()); // and actual's
			// version time
			// staging.setStatus (value.getToState());

			actual = getActualCollateralBusManager().updateCollateral(staging);

			value.setCollateral(actual); // set into actual

			// set back the staging collateral id for transaction ref id
			value.getStagingCollateral().setCollateralID(stageColID);
			value.getStagingCollateral().setVersionTime(stageVersion);
			value.getStagingCollateral().setStatus(stageStatus);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("Failed to update actual collateral, collateral id ["
					+ value.getReferenceID() + "],  using staging collateral, collateral id ["
					+ value.getStagingReferenceID() + "]", e);
		}
	}

	/**
	 * Update actual commodity pre-condition record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateActualPreCondition(ICollateralTrxValue value) throws TrxOperationException {
		try {
			value.setCollateral(getActualCollateralBusManager().updatePreCondition(value.getCollateral()));
			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("Failed to update commodity pre condition, collateral id ["
					+ value.getReferenceID() + "]", e);
		}
	}

	/**
	 * Update actual collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateActualfromActualCollateral(ICollateralTrxValue value)
			throws TrxOperationException {
		try {
			ICollateral actual = value.getCollateral();

			actual = getActualCollateralBusManager().updateCollateral(actual);

			value.setCollateral(actual);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to update actual collateral using actual collateral, collateral id ["
							+ value.getReferenceID() + "]", e);
		}
	}

	/**
	 * Update actual common collateral record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected ICollateralTrxValue updateActualfromActualCommonCollateral(ICollateralTrxValue value)
			throws TrxOperationException {
		try {
			ICollateral actual = value.getCollateral();

			actual = getActualCollateralBusManager().updateCommonCollateral(actual);

			value.setCollateral(actual);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException(
					"Failed to update actual common collateral using actual collateral, collateral id ["
							+ value.getReferenceID() + "]", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralTrxValue createTransaction(ICollateralTrxValue value) throws TrxOperationException {
		value = prepareTrxValue(value);
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBCollateralTrxValue newValue = new OBCollateralTrxValue(tempValue);
		newValue.setCollateral(value.getCollateral());
		newValue.setStagingCollateral(value.getStagingCollateral());
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICollateralTrxValue updateTransaction(ICollateralTrxValue value) throws TrxOperationException {
		DefaultLogger.debug(this, "===============================105-8-1=========create============= ");
		
		value = prepareTrxValue(value);
		DefaultLogger.debug(this, "===============================105-8-2=========create============= "+value.getTransactionID());
		ICMSTrxValue tempValue = super.updateTransaction(value);
		DefaultLogger.debug(this, "===============================105-8-3=========create============= ");
		OBCollateralTrxValue newValue = new OBCollateralTrxValue(tempValue);
		
		newValue.setCollateral(value.getCollateral());	
		newValue.setStagingCollateral(value.getStagingCollateral());
		DefaultLogger.debug(this, "===============================105-8-4=========create============= ");
		
		return newValue;

	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICollateralTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICollateralTrxValue value) {
		OBCollateralTrxResult result = new OBCollateralTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ICollateralTrxValue
	 * @return collateral transaction value
	 */
	private ICollateralTrxValue prepareTrxValue(ICollateralTrxValue value) {
		if (value != null) {
			ICollateral actual = value.getCollateral();
			ICollateral staging = value.getStagingCollateral();

			String actualCollateralId = (actual != null) ? String.valueOf(actual.getCollateralID()) : null;
			String stagingCollateralId = (staging != null)
					&& (staging.getCollateralID() != ICMSConstant.LONG_MIN_VALUE) ? String.valueOf(staging
					.getCollateralID()) : null;

			value.setReferenceID(actualCollateralId);
			value.setStagingReferenceID(stagingCollateralId);
		}
		return value;
	}

}