/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/AbstractCommodityMaintenanceProxy.java,v 1.29 2006/10/26 02:27:06 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import java.util.Date;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.price.CommodityPriceDAO;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.proxy.sublimittype.SubLimitTypeProxyHelper;
import com.integrosys.cms.app.commodity.main.trx.price.CommodityPriceTrxControllerFactory;
import com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue;
import com.integrosys.cms.app.commodity.main.trx.price.OBCommodityPriceTrxValue;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.app.commodity.main.trx.profile.OBProfileTrxValue;
import com.integrosys.cms.app.commodity.main.trx.profile.ProfileTrxControllerFactory;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue;
import com.integrosys.cms.app.commodity.main.trx.titledocument.OBTitleDocumentTrxValue;
import com.integrosys.cms.app.commodity.main.trx.titledocument.TitleDocumentTrxControllerFactory;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;
import com.integrosys.cms.app.commodity.main.trx.uom.OBUnitofMeasureTrxValue;
import com.integrosys.cms.app.commodity.main.trx.uom.UnitofMeasureTrxControllerFactory;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;
import com.integrosys.cms.app.commodity.main.trx.warehouse.OBWarehouseTrxValue;
import com.integrosys.cms.app.commodity.main.trx.warehouse.WarehouseTrxControllerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 30, 2004 Time: 10:07:33 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCommodityMaintenanceProxy implements ICommodityMaintenanceProxy {

	/**
	 * Gets the unit of measure transaction value given the commodity category
	 * and product type code.
	 * 
	 * @param ctx transaction context
	 * @param catCode commodity category code
	 * @param prodTypeCode commodity product type code
	 * @return unit of measure transaction value
	 * @throws CommodityException on encountering error getting the transaction
	 *         value
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String catCode, String prodTypeCode)
			throws CommodityException {
		OBUnitofMeasureTrxValue trxValue = new OBUnitofMeasureTrxValue();
		trxValue.setCategoryCode(catCode);
		trxValue.setProductTypeCode(prodTypeCode);
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD);
	}

	/**
	 * Gets the unit of measure transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return unit of measure transaction value
	 * @throws CommodityException on encountering error getting the transaction
	 *         value
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String trxID) throws CommodityException {
		OBUnitofMeasureTrxValue trxValue = new OBUnitofMeasureTrxValue();
		trxValue.setTransactionID(trxID);
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID);
	}

	/**
	 * Maker updates unit of measure approved by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return updated unit of measure transaction value
	 * @throws CommodityException on encountering error updating the unit of
	 *         measures
	 */
	public IUnitofMeasureTrxValue makerUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
			action = ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
		}
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue), action);
	}

	/**
	 * Checker approves unit of measure updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return approved unit of measure transaction value
	 * @throws CommodityException on encountering error approving the unit of
	 *         measures
	 */
	public IUnitofMeasureTrxValue checkerApproveUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
			action = ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN;
		}
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue), action);
	}

	/**
	 * Checker rejects unit of measure updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return rejected unit of measure transaction value
	 * @throws CommodityException on encountering error rejecting the unit of
	 *         measures
	 */
	public IUnitofMeasureTrxValue checkerRejectUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
			action = ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN;
		}
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue), action);
	}

	/**
	 * Maker resubmits unit of measure rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return resubmitted unit of measure transaction value
	 * @throws CommodityException on encountering error resubmitting the unit of
	 *         measures
	 */
	public IUnitofMeasureTrxValue makerResubmitUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
			action = ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN;
		}
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue), action);
	}

	/**
	 * Maker closes request to update unit of measure rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return closed unit of measure transaction value
	 * @throws CommodityException on encountering error closing the request
	 */
	public IUnitofMeasureTrxValue makerCloseUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
			action = ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN;
		}
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxValue), action);
	}

	/**
	 * Maker saves unit of measure.
	 * 
	 * @param ctx transaction context
	 * @param trxVal unit of measure transaction value
	 * @param uoms a list of IUnitofMeasure objects
	 * @return saved unit of measure
	 * @throws CommodityException on error saving the transaction
	 */
	public IUnitofMeasureTrxValue makerSaveUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxVal,
			IUnitofMeasure[] uoms) throws CommodityException {
		trxVal.setStagingUnitofMeasure(uoms);
		return (IUnitofMeasureTrxValue) operate(constructTrxValue(ctx, trxVal),
				ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
	}

	/**
	 * Helper method to construct the TrxParam object and call operate.
	 * 
	 * @param trxVal
	 * @param paramStr
	 * @return
	 * @throws CommodityException
	 */
	private ITrxValue operate(ITrxValue trxVal, String paramStr) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(paramStr);
		return operate(trxVal, param);
	}

	// Pricing
	/**
	 * Get commodity price transaction value based on commodity category and
	 * product type code.
	 * 
	 * @param ctx transaction context
	 * @param catCode commodity category code
	 * @param prodTypeCode commodity product type code
	 * @return commodity price transaction value
	 * @throws CommodityException on error getting the transaction value
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxContext ctx, String catCode, String prodTypeCode,
			String ricType) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD);
		OBCommodityPriceTrxValue trxValue = new OBCommodityPriceTrxValue();
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		trxValue.setCommodityCategoryCode(catCode);
		trxValue.setCommodityProdTypeCode(prodTypeCode);
		trxValue.setCommodityRICType(ricType);
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * Get commodity price transaction value given its transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity price transaction value
	 * @throws CommodityException on error getting the transaction value
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxContext ctx, String trxID, String ricType)
			throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID);
		OBCommodityPriceTrxValue trxValue = new OBCommodityPriceTrxValue();
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		trxValue.setTransactionID(trxID);
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * Maker closes commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return closed commodity prices
	 * @throws CommodityException on error closing the transaction
	 */
	public ICommodityPriceTrxValue makerCloseCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getCommodityPrice() == null) {
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN);
		}
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Maker updates commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal transaction value of commodity price
	 * @param prices a list of commodity prices to be created
	 * @return newly updated commodity price contained in transaction value
	 * @throws CommodityException on error updating commodity price
	 */
	public ICommodityPriceTrxValue makerUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			ICommodityPrice[] prices, String ricType) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ((trxVal.getTransactionID() == null) || (trxVal.getCommodityPrice() == null)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN);
		}
		// Set speciall transaction type for non-RIC
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		trxVal.setStagingCommodityPrice(prices);
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Maker saves commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @param prices a list of ICommodityPrice objects
	 * @return saved commodity prices
	 * @throws CommodityException on error saving the transaction
	 */
	public ICommodityPriceTrxValue makerSaveCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			ICommodityPrice[] prices, String ricType) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
		trxVal.setStagingCommodityPrice(prices);
		// Set speciall transaction type for non-RIC
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * System updates commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal transaction value
	 * @return updated commodity price transaction value
	 * @throws CommodityException on error updating the transaction
	 */
	public ICommodityPriceTrxValue systemUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_COMMODITY_MAIN);
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Checker approve commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return approved commodity price transaction value
	 * @throws CommodityException on error approving the updated commodity price
	 */
	public ICommodityPriceTrxValue checkerApproveUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getCommodityPrice() == null) {
			param.setAction(ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN);
		}
		else {
			param.setAction(ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN);
		}
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Checker reject commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return rejected commodity price transaction value
	 * @throws CommodityException on error rejecting the prices
	 */
	public ICommodityPriceTrxValue checkerRejectUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getCommodityPrice() == null) {
			param.setAction(ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN);
		}
		else {
			param.setAction(ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN);
		}
		if (IProfile.RIC_TYPE_NON.equals(ricType)) {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC);
		}
		else {
			trxVal.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
		}
		return (ICommodityPriceTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @return transaction value
	 */
	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		((ICMSTrxValue) trxValue).setTrxContext(ctx);
		return trxValue;
	}

	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws CommodityException {
		if (trxVal == null) {
			throw new CommodityException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;
			if (trxVal instanceof ICommodityPriceTrxValue) {
				controller = (new CommodityPriceTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof IUnitofMeasureTrxValue) {
				controller = (new UnitofMeasureTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof IWarehouseTrxValue) {
				controller = (new WarehouseTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof IProfileTrxValue) {
				controller = (new ProfileTrxControllerFactory()).getController(trxVal, param);
			}
			else if (trxVal instanceof ITitleDocumentTrxValue) {
				controller = (new TitleDocumentTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new CommodityException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (CommodityException e) {
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			rollback();
			throw new CommodityException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new CommodityException("Exception caught! " + e.toString(), e);
		}
	}

	// TitleDocument
	public ITitleDocumentTrxValue getTitleDocumentTrxValue(ITrxContext ctx) throws CommodityException {
		OBTitleDocumentTrxValue trxValue = new OBTitleDocumentTrxValue();
		return (ITitleDocumentTrxValue) operate(constructTrxValue(ctx, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID);
	}

	public ITitleDocumentTrxValue getTitleDocumentByTrxID(ITrxContext trxContext, String trxID)
			throws CommodityException {
		OBTitleDocumentTrxValue trxValue = new OBTitleDocumentTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_TITLEDOC);
		return operate(trxContext, trxValue, ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID);
	}

	public ITitleDocument[] getAllTitleDocuments() throws CommodityException {
		TitleDocumentSearchCriteria criteria = new TitleDocumentSearchCriteria();
		criteria.setIncludeDeleted(false);
		return (ITitleDocument[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
	}

	public ITitleDocument[] getAllTitleDocuments(String documentType) throws CommodityException {
		TitleDocumentSearchCriteria criteria = new TitleDocumentSearchCriteria();
		criteria.setIncludeDeleted(false);
		criteria.setType(documentType);
		return (ITitleDocument[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
	}

	public ITitleDocument getTitleDocumentByTitleDocumentID(long titleDocumentID) throws CommodityException {
		TitleDocumentSearchCriteria criteria = new TitleDocumentSearchCriteria();
		criteria.setSearchBy(CommodityMainInfoSearchCriteria.SEARCH_BY_ID);
		criteria.setInfoID(new Long(titleDocumentID));
		ITitleDocument[] titleDocuments = (ITitleDocument[]) CommodityMainInfoManagerFactory.getManager().getAll(
				criteria);
		return ((titleDocuments != null) && (titleDocuments.length > 0) ? titleDocuments[0] : null);
	}

	/*
	 * public SearchResult searchTitleDocument(ITrxContext trxContext,
	 * CommodityMainInfoSearchCriteria criteria) throws CommodityException {
	 * throw new CommodityException (" AbstractCommodityMaintenanceProxy :
	 * Method not implemented "); }
	 */

	public ITitleDocumentTrxValue makerCreateTitleDocument(ITrxContext trxContext, ITitleDocument[] value)
			throws CommodityException {
		return operate(trxContext, value, ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerApproveCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerRejectCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerResubmitCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerCloseCreateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerApproveUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerRejectUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerResubmitUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerCloseUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerApproveDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue checkerRejectDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerResubmitDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN);
	}

	public ITitleDocumentTrxValue makerCloseDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN);
	}

	/**
	 * Maker saves commodity title document types.
	 * 
	 * @param ctx transaction context
	 * @param trxVal title document type transaction value
	 * @param types a list of ITitleDocument objects
	 * @return saved title document types
	 * @throws CommodityException on error saving the transaction
	 */
	public ITitleDocumentTrxValue makerSaveTitleDocument(ITrxContext ctx, ITitleDocumentTrxValue trxVal,
			ITitleDocument[] types) throws CommodityException {
		trxVal.setStagingTitleDocument(types);
		return (ITitleDocumentTrxValue) operate(ctx, trxVal, ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
	}

	private ITitleDocumentTrxValue operate(ITrxContext trxContext, ITitleDocument[] value, String action)
			throws CommodityException {

		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (value == null) {
			throw new CommodityException("The IDocumentItem to be created is null!!!");
		}
		ITitleDocumentTrxValue trxValue = formulateTrxValue(trxContext, value);

		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);

	}

	private ITitleDocumentTrxValue operate(ITrxContext trxContext, ITitleDocumentTrxValue inTrxValue, String action)
			throws CommodityException {
		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (inTrxValue == null) {
			throw new CommodityException("The IDocumentItem to be created is null!!!");
		}
		ITitleDocumentTrxValue trxValue = formulateTrxValue(trxContext, inTrxValue);
		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);
	}

	private ITitleDocumentTrxValue operate(ITitleDocumentTrxValue trxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CommodityException {
		ICMSTrxResult result = operateAnyAbstract_TitleDocument(trxValue, anOBCMSTrxParameter);
		return (ITitleDocumentTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the document item transactions.
	 * 
	 * @param trxValue - ITitleDocumentTrxValue
	 * @param trxParam - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	private ICMSTrxResult operateAnyAbstract_TitleDocument(ICMSTrxValue trxValue, OBCMSTrxParameter trxParam)
			throws CommodityException {
		try {

			ITrxController controller = null;
			if (trxValue.getTransactionType().equals(ICMSConstant.INSTANCE_COMMODITY_MAIN_TITLEDOC)) {
				DefaultLogger.debug(this, "$$$ debug: A1");
				controller = (new TitleDocumentTrxControllerFactory()).getController(trxValue, trxParam);
				DefaultLogger.debug(this, "$$$ debug: A2");
			}
			else {
				throw new CommodityException(" From Proxy : TransactionType '" + trxValue.getTransactionType()
						+ "' is invalid. Unable to proceed. Please pass a valid transaction type.");
			}

			DefaultLogger.debug(this, "$$$ debug: A3" + controller);
			if (controller == null) {
				throw new CommodityException("ITrxController is null!!!");
			}
			DefaultLogger.debug(this, "$$$ debug: A4");
			ITrxResult result = controller.operate(trxValue, trxParam);
			DefaultLogger.debug(this, "$$$ debug: A5");
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			rollback();
			throw new CommodityException(ex.toString());
		}
	}

	private ITitleDocumentTrxValue formulateTrxValue(ITrxContext trxContext, ITitleDocument[] value) {
		return formulateTrxValue(trxContext, null, value);
	}

	private ITitleDocumentTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ITitleDocument[] anIDocumentItem) {
		ITitleDocumentTrxValue docItemTrxValue = null;
		if (anICMSTrxValue != null) {
			docItemTrxValue = new OBTitleDocumentTrxValue(anICMSTrxValue);
		}
		else {
			docItemTrxValue = new OBTitleDocumentTrxValue();
		}
		docItemTrxValue.setStagingTitleDocument(anIDocumentItem);
		docItemTrxValue = formulateTrxValue(anITrxContext, (OBTitleDocumentTrxValue) docItemTrxValue);
		return docItemTrxValue;
	}

	private ITitleDocumentTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ITitleDocumentTrxValue anITitleDocumentTrxValue) {
		anITitleDocumentTrxValue.setTrxContext(anITrxContext);
		anITitleDocumentTrxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_TITLEDOC);
		return anITitleDocumentTrxValue;
	}

	// Warehouse methods

	// Warehouse
	public IWarehouseTrxValue getWarehouseTrxValue(ITrxContext trxContext, String countryCode)
			throws CommodityException {
		OBWarehouseTrxValue trxValue = new OBWarehouseTrxValue();
		trxValue.setCountryCode(countryCode);
		IWarehouseTrxValue aTrxValue = (IWarehouseTrxValue) operate(constructTrxValue(trxContext, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_COUNTRY);

		return aTrxValue;
	}

	public IWarehouseTrxValue getWarehouseByTrxID(ITrxContext trxContext, String trxID) throws CommodityException {
		OBWarehouseTrxValue trxValue = new OBWarehouseTrxValue();
		trxValue.setTransactionID(trxID);

		IWarehouseTrxValue aTrxValue = (IWarehouseTrxValue) operate(constructTrxValue(trxContext, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID);

		return aTrxValue;
	}

	public IWarehouse[] getAllWarehouses() throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
		criteria.setIncludeDeleted(false);
		return (IWarehouse[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
	}

	public IWarehouse getWarehouseByWarehouseID(long warehouseID) throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
		criteria.setSearchBy(CommodityMainInfoSearchCriteria.SEARCH_BY_ID);
		criteria.setInfoID(new Long(warehouseID));
		IWarehouse[] warehouses = (IWarehouse[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
		return ((warehouses != null) && (warehouses.length > 0) ? warehouses[0] : null);
	}

	public SearchResult searchWarehouse(ITrxContext trxContext, CommodityMainInfoSearchCriteria criteria)
			throws CommodityException {
		throw new CommodityException(" AbstractCommodityMaintenanceProxy : Method not implemented ");
	}

	public IWarehouseTrxValue makerCreateWarehouse(ITrxContext trxContext, IWarehouse[] value)
			throws CommodityException {
		return operate(trxContext, value, ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue checkerApproveCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue checkerRejectCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerResubmitCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerCloseCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {

		String action = null;
		if ((trxValue.getWarehouse() != null) && (trxValue.getWarehouse().length > 0)) {
			action = ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
		}
		else {
			if (trxValue.getStatus().equals(ICMSConstant.STATE_DRAFT)) {
				action = ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
			}
			else {
				action = ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
			}
		}
		return (IWarehouseTrxValue) operate(constructTrxValue(trxContext, trxValue), action);
	}

	public IWarehouseTrxValue checkerApproveUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {

		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue checkerRejectUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerResubmitUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerCloseUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue checkerApproveDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue checkerRejectDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerResubmitDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN);
	}

	public IWarehouseTrxValue makerCloseDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN);
	}

	/**
	 * Maker saves commodity warehouse.
	 * 
	 * @param ctx transaction context
	 * @param trxVal warehouse transaction value
	 * @param warehouses a list of IWarehouse objects
	 * @return saved warehouses
	 * @throws CommodityException on error saving the transaction
	 */
	public IWarehouseTrxValue makerSaveWarehouse(ITrxContext ctx, IWarehouseTrxValue trxVal, IWarehouse[] warehouses)
			throws CommodityException {
		trxVal.setStagingWarehouse(warehouses);
		return (IWarehouseTrxValue) operate(ctx, trxVal, ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
	}

	private IWarehouseTrxValue operate(ITrxContext trxContext, IWarehouse[] value, String action)
			throws CommodityException {

		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (value == null) {
			throw new CommodityException("The IWarehouse to be created is null!!!");
		}
		IWarehouseTrxValue trxValue = formulateTrxValue(trxContext, value);

		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);

	}

	private IWarehouseTrxValue operate(ITrxContext trxContext, IWarehouseTrxValue inTrxValue, String action)
			throws CommodityException {
		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (inTrxValue == null) {
			throw new CommodityException("The IWarehouse to be created is null!!!");
		}
		IWarehouseTrxValue trxValue = formulateTrxValue(trxContext, inTrxValue);
		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);
	}

	private IWarehouseTrxValue operate(IWarehouseTrxValue trxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CommodityException {
		ICMSTrxResult result = operateAnyAbstract_Warehouse(trxValue, anOBCMSTrxParameter);
		return (IWarehouseTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the warehouse transactions.
	 * 
	 * @param trxValue - IWarehouseTrxValue
	 * @param trxParam - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	private ICMSTrxResult operateAnyAbstract_Warehouse(ICMSTrxValue trxValue, OBCMSTrxParameter trxParam)
			throws CommodityException {
		try {
			IWarehouse[] act = null;
			IWarehouse[] stag = null;

			act = ((IWarehouseTrxValue) trxValue).getWarehouse();
			stag = ((IWarehouseTrxValue) trxValue).getStagingWarehouse();

			ITrxController controller = null;
			if (trxValue.getTransactionType().equals(ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE)) {
				DefaultLogger.debug(this, "$$$ debug: A1");
				controller = (new WarehouseTrxControllerFactory()).getController(trxValue, trxParam);
				DefaultLogger.debug(this, "$$$ debug: A2");
			}
			else {
				throw new CommodityException(" From Proxy : TransactionType '" + trxValue.getTransactionType()
						+ "' is invalid. Unable to proceed. Please pass a valid transaction type.");
			}

			DefaultLogger.debug(this, "$$$ debug: A3" + controller);
			if (controller == null) {
				throw new CommodityException("ITrxController is null!!!");
			}
			DefaultLogger.debug(this, "$$$ debug: A4");
			ITrxResult result = controller.operate(trxValue, trxParam);
			DefaultLogger.debug(this, "$$$ debug: A5");

			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			rollback();
			throw new CommodityException(ex.toString());
		}
	}

	private IWarehouseTrxValue formulateTrxValue(ITrxContext trxContext, IWarehouse[] value) {
		return formulateTrxValue(trxContext, null, value);
	}

	private IWarehouseTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IWarehouse[] anIWarehouse) {
		IWarehouseTrxValue warehouseTrxValue = null;
		if (anICMSTrxValue != null) {
			warehouseTrxValue = new OBWarehouseTrxValue(anICMSTrxValue);
		}
		else {
			warehouseTrxValue = new OBWarehouseTrxValue();
		}
		warehouseTrxValue.setStagingWarehouse(anIWarehouse);
		warehouseTrxValue = formulateTrxValue(anITrxContext, (OBWarehouseTrxValue) warehouseTrxValue);
		return warehouseTrxValue;
	}

	private IWarehouseTrxValue formulateTrxValue(ITrxContext anITrxContext, IWarehouseTrxValue anIWarehouseTrxValue) {
		anIWarehouseTrxValue.setTrxContext(anITrxContext);
		anIWarehouseTrxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE);
		return anIWarehouseTrxValue;
	}

	// Begin Sub-Limit Type related methods.
	public ISubLimitType[] getAllSubLimitTypes() throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE);
		criteria.setIncludeDeleted(false);
		return (ISubLimitType[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
	}

	public ISubLimitTypeTrxValue operateSubLimitType(ITrxContext trxContext, ISubLimitTypeTrxValue sltTrxValue,
			String action) throws CommodityException {
		try {
			return new SubLimitTypeProxyHelper().operate(trxContext, sltTrxValue, action);
		}
		catch (TransactionException e) {
			this.rollback();
			throw new CommodityException(e);
		}
	}

	// End Sub-Limit Type related methods.

	public IProfileTrxValue getProfileTrxValue(ITrxContext trxContext, ProfileSearchCriteria criteria)
			throws CommodityException {
		OBProfileTrxValue trxValue = new OBProfileTrxValue();
		trxValue.setSearchCriteria(criteria);
		IProfileTrxValue aTrxValue = (IProfileTrxValue) operate(constructTrxValue(trxContext, trxValue),
				ICMSConstant.ACTION_READ_COMMODITY_MAIN_PROFILE_GROUP);

		return aTrxValue;
	}

	public IProfileTrxValue getProfileByTrxID(ITrxContext trxContext, String trxID) throws CommodityException {
		OBProfileTrxValue trxValue = new OBProfileTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE);
		DefaultLogger.debug(this, "$$$Debug::: 1  byTRXID");
		return operate(trxContext, trxValue, ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID);
	}

	public IProfileTrxValue getProfilesByGroupID(ITrxContext trxContext, long groupID) throws CommodityException {
		OBProfile value = new OBProfile();
		value.setGroupID(groupID);

		OBProfile[] values = new OBProfile[] { value };
		IProfileTrxValue trxValue = formulateTrxValue(trxContext, values);

		trxValue.setReferenceID(groupID + "");
		// throw new CommodityException("Method not implemented ");
		return operate(trxContext, trxValue, ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID);
	}

	public IProfile[] getAllProfiles() throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_PROFILE);
		criteria.setIncludeDeleted(false);
		return (IProfile[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);

	}

	public IProfile getProfileByProfileID(long profileID) throws CommodityException {
		CommodityMainInfoSearchCriteria criteria = new CommodityMainInfoSearchCriteria(
				ICommodityMainInfo.INFO_TYPE_PROFILE);
		criteria.setSearchBy(CommodityMainInfoSearchCriteria.SEARCH_BY_ID);
		criteria.setInfoID(new Long(profileID));
		IProfile[] profiles = (IProfile[]) CommodityMainInfoManagerFactory.getManager().getAll(criteria);
		return ((profiles != null) && (profiles.length > 0) ? profiles[0] : null);
	}

	public ISupplier getSupplierBySupplierID(long supplierID) throws CommodityException {
		return CommodityMainInfoManagerFactory.getManager().getSupplierByID(supplierID);
	}

	public SearchResult searchProfile(ITrxContext trxContext, CommodityMainInfoSearchCriteria criteria)
			throws CommodityException {
		throw new CommodityException(" AbstractCommodityMaintenanceProxy : Method not implemented ");
	}

	public IProfileTrxValue makerCreateProfile(ITrxContext trxContext, IProfile[] value) throws CommodityException {
		return operate(trxContext, value, ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN);
	}

	public IProfileTrxValue checkerApproveCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN);
	}

	public IProfileTrxValue checkerRejectCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {

		DefaultLogger.debug(this, "$$$Proxy : 1 trxValue=" + trxValue);

		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerResubmitCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {

		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerCloseCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {

		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		String action = null;
		if (ICMSConstant.STATE_ND.equals(trxValue.getStatus())) {
			action = ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
		}
		else {
			action = ICMSConstant.ACTION_MAKER_UPDATE_COMMODITY_MAIN;
		}
		DefaultLogger.debug(this, "action : " + action);
		DefaultLogger.debug(this, "status : " + trxValue.getStatus());

		return operate(trxContext, trxValue, action);
	}

	public IProfileTrxValue checkerApproveUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN);
	}

	public IProfileTrxValue checkerRejectUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerResubmitUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerCloseUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_COMMODITY_MAIN);
	}

	public IProfileTrxValue checkerApproveDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN);
	}

	public IProfileTrxValue checkerRejectDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerResubmitDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerCloseDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN);
	}

	public IProfileTrxValue makerSaveProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		return operate(trxContext, trxValue, ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN);
	}

	private IProfileTrxValue operate(ITrxContext trxContext, IProfile[] value, String action) throws CommodityException {

		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (value == null) {
			throw new CommodityException("The IDocumentItem to be created is null!!!");
		}
		IProfileTrxValue trxValue = formulateTrxValue(trxContext, value);

		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);

	}

	private IProfileTrxValue operate(ITrxContext trxContext, IProfileTrxValue inTrxValue, String action)
			throws CommodityException {
		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (inTrxValue == null) {
			throw new CommodityException("The IDocumentItem to be created is null!!!");
		}

		IProfileTrxValue trxValue = formulateTrxValue(trxContext, inTrxValue);
		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);
	}

	private IProfileTrxValue operate(IProfileTrxValue trxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CommodityException {
		ICMSTrxResult result = operateAnyAbstract_Profile(trxValue, anOBCMSTrxParameter);
		return (IProfileTrxValue) result.getTrxValue();
	}

	/**
	 * Helper method to perform the document item transactions.
	 * 
	 * @param trxValue - IProfileTrxValue
	 * @param trxParam - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	private ICMSTrxResult operateAnyAbstract_Profile(ICMSTrxValue trxValue, OBCMSTrxParameter trxParam)
			throws CommodityException {
		try {

			ITrxController controller = null;
			if (trxValue.getTransactionType().equals(ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE)) {
				DefaultLogger.debug(this, "$$$ debug: A1");
				controller = (new ProfileTrxControllerFactory()).getController(trxValue, trxParam);
				DefaultLogger.debug(this, "$$$ debug: A2");
			}
			else {
				throw new CommodityException(" From Proxy : TransactionType '" + trxValue.getTransactionType()
						+ "' is invalid. Unable to proceed. Please pass a valid transaction type.");
			}

			DefaultLogger.debug(this, "$$$ debug: A3" + controller);
			if (controller == null) {
				throw new CommodityException("ITrxController is null!!!");
			}
			DefaultLogger.debug(this, "$$$ debug: A4");
			ITrxResult result = controller.operate(trxValue, trxParam);
			DefaultLogger.debug(this, "$$$ debug: A5");
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			rollback();
			throw new CommodityException(ex.toString());
		}
	}

	private IProfileTrxValue formulateTrxValue(ITrxContext trxContext, IProfile[] value) {
		return formulateTrxValue(trxContext, null, value);
	}

	private IProfileTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IProfile[] anIDocumentItem) {
		IProfileTrxValue docItemTrxValue = null;
		if (anICMSTrxValue != null) {
			docItemTrxValue = new OBProfileTrxValue(anICMSTrxValue);
		}
		else {
			docItemTrxValue = new OBProfileTrxValue();
		}
		docItemTrxValue.setStagingProfile(anIDocumentItem);
		docItemTrxValue = formulateTrxValue(anITrxContext, (OBProfileTrxValue) docItemTrxValue);
		return docItemTrxValue;
	}

	private IProfileTrxValue formulateTrxValue(ITrxContext anITrxContext, IProfileTrxValue anIProfileTrxValue) {
		anIProfileTrxValue.setTrxContext(anITrxContext);
		anIProfileTrxValue.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PROFILE);
		return anIProfileTrxValue;
	}

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException {
		return CommodityMainInfoManagerFactory.getManager().getProductSubTypesByCategoryAndProduct(commodityCode,
				productType);
	}

	/**
	 * Gets all unit of measure that can be used for a commodity identified by
	 * the given commodity profile id. Includes the common unit of measure (e.g.
	 * kg, lbs) and the unit of measure set up for the given commodity profile
	 * (e.g. a small bag).
	 * 
	 * @param profileID - id of a commodity profile
	 * @return a list of unit of measure wrapper that can be used for the
	 *         commodity
	 * @return a zero-length list if there are no unit of measure found
	 * @throws CommodityException on error getting the set of unit of measure
	 */
	public UOMWrapper[] getUnitofMeasure(long profileID) throws CommodityException {
		if (profileID == ICMSConstant.LONG_INVALID_VALUE) {
			return (UOMWrapper[]) UOMWrapperFactory.getInstance().getCommonUOM().toArray(new UOMWrapper[0]);
		}
		return CommodityMainInfoManagerFactory.getManager().getUnitofMeasureByProfileID(profileID);
	}

	/**
	 * Gets the latest price from commodity price feed.
	 * 
	 * @param profileID id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws CommodityException
	 */
	public Amount getLatestPrice(long profileID) throws CommodityException {

		try {
			if (profileID != ICMSConstant.LONG_INVALID_VALUE) {
				return new CommodityPriceDAO().getCurrentPrice(profileID);
			}
			return null;
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}

	}

	/**
	 * The date of when the price is first updated (to current price) is
	 * retreived. Currently, its only meant to be used in conjunction with
	 * getLatestPrice() (see above).
	 * 
	 * E.g: Date | Price ---------------- 10/11 | 12.00 11/11 | 11.00 12/11 |
	 * 11.00 13/11 | 12.00 14/11 | 12.00 15/11 | 12.00 <-- assuming this is
	 * today
	 * 
	 * The date retrieved will be 13/11, since that is when it was first updated
	 * to this current price. (Currently when the price stays the same, it is
	 * assumed that there is NO update.)
	 * 
	 * @param profileID id of a commodity profile
	 * @return first update date of current price
	 * @throws CommodityException when exception occurs
	 */
	public Date getLatestPriceFirstUpdateDate(long profileID) throws CommodityException {
		try {
			if (profileID != ICMSConstant.LONG_INVALID_VALUE) {
				return new CommodityPriceDAO().getCurrentPriceFirstUpdateDate(profileID);
			}
			return null;
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}
	}

	/**
	 * See getLatestPriceFirstUpdateDate(). Its the same except it retrieves the
	 * date when the price is first updated (to current closed price). It
	 * returns null if current close price does not match the last updated close
	 * price in the staging price table (This scenario will occur when the batch
	 * job runs since the batch job does not update the staging table).
	 * 
	 * See CommodityPriceDAO.getClosePriceFirstUpdateDate() for more detailed
	 * explanation.
	 * 
	 * @param profileID id of a commodity profile
	 * @return first update date of current price
	 * @throws CommodityException when exception occurs
	 */
	public Date getMarketPriceFirstUpdateDate(long profileID) throws CommodityException {
		try {
			if (profileID != ICMSConstant.LONG_INVALID_VALUE) {
				return new CommodityPriceDAO().getClosePriceFirstUpdateDate(profileID);
			}
			return null;
		}
		catch (SearchDAOException e) {
			e.printStackTrace();
			rollback();
			throw new CommodityException(e);
		}
	}

	protected abstract void rollback() throws CommodityException;

	public boolean isRICTypeTransferable(long profileID) throws CommodityException {
		try {
			return new CommodityPriceDAO().isRICTypeTransferable(profileID);
		}
		catch (SearchDAOException e) {
			throw new CommodityException(e);
		}
	}
}
