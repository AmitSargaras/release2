/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.INPLInfo;
import com.integrosys.cms.app.liquidation.bus.LiquidationDAO;
import com.integrosys.cms.app.liquidation.bus.LiquidationDAOFactory;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.liquidation.trx.LiquidationTrxControllerFactory;
import com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxParameter;
import com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * This class implements the services that are available in CMS with respect to
 * the liquidation life cycle.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractLiquidationProxy implements ILiquidationProxy {
	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @param Liqs a list of liquidation objects
	 * @return transaction value
	 */
	private ILiquidationTrxValue constructTrxValue(ITrxContext ctx, ICMSTrxValue trxValue, ILiquidation Liqs) {
		ILiquidationTrxValue liquidationTrxValue = null;
		if (trxValue != null) {
			liquidationTrxValue = new OBLiquidationTrxValue(trxValue);
		}
		else {
			liquidationTrxValue = new OBLiquidationTrxValue();
		}
		liquidationTrxValue.setStagingLiquidation(Liqs);
		liquidationTrxValue.setLegalID(Liqs.getSecurityID());
		liquidationTrxValue.setLegalName(Liqs.getSecurityType());
		liquidationTrxValue = constructTrxValue(ctx, liquidationTrxValue);

		return liquidationTrxValue;
	}

	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @return transaction value
	 */
	private ILiquidationTrxValue constructTrxValue(ITrxContext ctx, ILiquidationTrxValue trxValue) {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_LIQUIDATION);
		return trxValue;
	}

	/**
	 * Helper method to operate transactions.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws LiquidationException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws LiquidationException {
		if (trxVal == null) {
			throw new LiquidationException("ILiquidationTrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof ILiquidationTrxValue) {
				controller = (new LiquidationTrxControllerFactory()).getController(trxVal, param);
			}
			else {
				controller = (new LiquidationTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new LiquidationException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (TransactionException e) {
			rollback();
			throw new LiquidationException("failed to operate the liquidation workflow", e);
		}
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws LiquidationException on errors encountered
	 */
	protected abstract void rollback() throws LiquidationException;

	public Collection getNPLInfo(long collateralID) throws LiquidationException {
		LiquidationDAO dao = LiquidationDAOFactory.getDAO();
		Collection nPLList = dao.getNPLInfo(collateralID);

		if (nPLList != null) {
			for (Iterator nPLIterator = nPLList.iterator(); nPLIterator.hasNext();) {
				INPLInfo npl = (INPLInfo) nPLIterator.next();
				Collection facilityList = dao.getFacilityTypeInfo(npl.getAccountNo());

				if (facilityList != null) {
					List data = new ArrayList();
					for (Iterator facIterator = facilityList.iterator(); facIterator.hasNext();) {
						List list = (List) facIterator.next();
						String facilityNo = (String) list.get(0);
						String facilityValue = (String) list.get(1);

						DefaultLogger.debug(this, "*** facilityNo : " + facilityNo);
						DefaultLogger.debug(this, "*** facilityValue : " + facilityValue);

						String facilityType = CommonCodeList.getInstance(facilityNo).getCommonCodeLabel(facilityValue);
						data.add(facilityType);
					}
					npl.setFacilityTypeList(data);
				}
			}
		}

		return nPLList;
	}

	public ILiquidationTrxValue getLiquidationTrxValue(ITrxContext ctx, long collateralID) throws LiquidationException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_LIQUIDATION);
		OBLiquidationTrxValue trxValue = new OBLiquidationTrxValue();

		trxValue.setLegalName(String.valueOf(collateralID));
		trxValue.setLegalID(String.valueOf(collateralID));
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	public ILiquidationTrxValue getLiquidationTrxValueByTrxRefID(ITrxContext ctx, String collateralID)
			throws LiquidationException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_LIQUIDATION);
		OBLiquidationTrxValue trxValue = new OBLiquidationTrxValue();

		String collateralTrxID = "";

		try {
			LiquidationDAO dao = LiquidationDAOFactory.getDAO();
			if ((collateralID != null) && !collateralID.trim().equals("")) {
				collateralTrxID = dao.getTrxIDByColID(collateralID, ICMSConstant.INSTANCE_COLLATERAL);
			}
		}
		catch (Exception e) {
			DefaultLogger.warn(this,
					"no transaction created yet for the collateral id [" + collateralID + "], ignored", e);
		}

		if ((collateralTrxID != null) && !collateralTrxID.trim().equals("")) {
			trxValue.setTrxReferenceID(collateralTrxID);
		}

		trxValue.setLegalName(String.valueOf(collateralID));
		trxValue.setLegalID(String.valueOf(collateralID));

		if ((collateralTrxID != null) && !collateralTrxID.trim().equals("")) {
			String trxID = null;
			try {
				LiquidationDAO dao = LiquidationDAOFactory.getDAO();

				trxID = dao.getTrxIDByTrxRefID(collateralTrxID, ICMSConstant.INSTANCE_LIQUIDATION);
			}
			catch (Exception e) {
				// no transaction yet.
			}
			if ((trxID != null) && !trxID.trim().equals("")) {
				return getLiquidationTrxValueByTrxID(ctx, trxID);
			}
		}

		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	public ILiquidationTrxValue getLiquidationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws LiquidationException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_LIQUIDATION_BY_TRXID);
		OBLiquidationTrxValue trxValue = new OBLiquidationTrxValue();
		trxValue.setTransactionID(trxID);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	public ILiquidationTrxValue makerUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation Liqs)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_LIQUIDATION);
		trxVal.setStagingLiquidation(Liqs);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal, Liqs), param);
	}

	public ILiquidationTrxValue makerSaveLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation Liqs)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_LIQUIDATION);
		trxVal.setStagingLiquidation(Liqs);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	public ILiquidationTrxValue makerCancelUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_LIQUIDATION);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	public ILiquidationTrxValue makerCloseUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_LIQUIDATION);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	public ILiquidationTrxValue checkerApproveUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_LIQUIDATION);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	public ILiquidationTrxValue checkerRejectUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		OBLiquidationTrxParameter param = new OBLiquidationTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_LIQUIDATION);
		return (ILiquidationTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

}