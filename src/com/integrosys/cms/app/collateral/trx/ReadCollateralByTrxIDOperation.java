/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadCollateralByTrxIDOperation.java,v 1.4 2003/08/19 09:07:47 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

import java.util.List;

/**
 * The operation is to read collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/19 09:07:47 $ Tag: $Name: $
 */
public class ReadCollateralByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	private static final long serialVersionUID = 741410283480344430L;

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	private List onlineValuationTypeList;

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

	/**
	 * Get the list of security types that are applicable for online valuation
	 * @return
	 */
	public List getOnlineValuationTypeList() {
		return onlineValuationTypeList;
	}

	/**
	 * Set the list of security types that are applicable for online valuation
	 * @param onlineValuationTypeList
	 */
	public void setOnlineValuationTypeList(List onlineValuationTypeList) {
		this.onlineValuationTypeList = onlineValuationTypeList;
	}

	/**
	 * Default Constructor
	 */
	public ReadCollateralByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COL_BY_TRXID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
			OBCollateralTrxValue colTrxValue = new OBCollateralTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = colTrxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);
			ICollateralPledgor[] stagePledgor = null;

			if (stagingRef != null) {
				ICollateral col = getStagingCollateralBusManager().getCollateral(Long.parseLong(stagingRef));
				colTrxValue.setStagingCollateral(col);
				stagePledgor = col.getPledgors();
			}

			if (actualRef != null) {
				ICollateral col = getActualCollateralBusManager().getCollateral(Long.parseLong(actualRef));
				colTrxValue.setCollateral(col);
				if ((colTrxValue.getStagingCollateral() != null)
						&& ((stagePledgor == null) || (stagePledgor.length == 0))) {
					colTrxValue.getStagingCollateral().setPledgors(col.getPledgors());
				}

				if (colTrxValue.getStagingCollateral() != null) {
					// only override the staging's source valuation if the
					// collateral type does not perform
					// online valuation. If there is online valuation, then the
					// valuation that is latest
					// (from actual or staging) should be retain.
					if (!(getOnlineValuationTypeList().contains(col.getCollateralType().getTypeCode()))) {
						colTrxValue.getStagingCollateral().setSourceValuation(col.getSourceValuation());
					}
					else { // online valuation sec types
						IValuation actualVal = col.getSourceValuation();
						IValuation stageVal = colTrxValue.getStagingCollateral().getSourceValuation();

						if ((actualVal != null && (stageVal == null || stageVal.getValuationDate() == null))
								|| (actualVal != null && stageVal != null && actualVal.getValuationDate() != null
										&& stageVal.getValuationDate() != null && actualVal.getValuationDate().after(
										stageVal.getValuationDate()))) {
							colTrxValue.getStagingCollateral().setSourceValuation(col.getSourceValuation());
						}
					}
					colTrxValue.getStagingCollateral().setValuationFromLOS(col.getValuationFromLOS());
					//colTrxValue.getStagingCollateral().setCollateralLimits(col.getCollateralLimits());

					if (colTrxValue.getStagingCollateral().getSecSystemName() == null
							|| colTrxValue.getStagingCollateral().getSecSystemName().isEmpty()) {
						OBCollateral staging = (OBCollateral) colTrxValue.getStagingCollateral();
						staging.setSecSystemName(col.getSecSystemName());
						colTrxValue.setStagingCollateral(staging);
					}
				}

				// Updating computed MF score in staging collateral because this
				// field is not stored in staging table
				if ((new OBPropertyCollateral()).getClass().isInstance(col)) {
					IPropertyCollateral propCol = (OBPropertyCollateral) col;
					if (colTrxValue.getStagingCollateral() != null) {
						((IPropertyCollateral) colTrxValue.getStagingCollateral()).setComputedMFScore(propCol
								.getComputedMFScore());
					}
				}
				// Updating the staging collateral with equity detail
				// only if it is subclass of OBMarketableCollateral
				DefaultLogger.debug(this, "Updating the staging collateral with equity detail");
				if ((new OBMarketableCollateral()).getClass().isInstance(col)) {
					IMarketableCollateral marCol = (OBMarketableCollateral) col;
					IMarketableCollateral marStagingCol = (OBMarketableCollateral) colTrxValue.getStagingCollateral();

					if (marCol.getEquityList() != null) {
						IMarketableEquity[] equityArray = (IMarketableEquity[]) marCol.getEquityList();
						for (int i = 0; i < equityArray.length; i++) {
							if (equityArray[i].getEquityDetailArray() != null) {
								if (marStagingCol.getEquityList() != null) {
									for (int j = 0; j < marStagingCol.getEquityList().length; j++) {
										if (equityArray[i].getRefID() == marStagingCol.getEquityList()[j].getRefID()) {
											marStagingCol.getEquityList()[j].setEquityDetailArray(equityArray[i]
													.getEquityDetailArray());
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			return colTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}