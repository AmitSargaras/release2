/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/CommodityMaintenanceProxyImpl.java,v 1.16 2006/10/12 03:07:57 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue;
import com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;
import com.integrosys.cms.app.commodity.main.trx.warehouse.IWarehouseTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 30, 2004 Time:
 * 10:16:48 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMaintenanceProxyImpl implements ICommodityMaintenanceProxy {

	// TitleDocument methods
	public ITitleDocumentTrxValue getTitleDocumentTrxValue(ITrxContext trxContext) throws CommodityException {
		try {
			return getProxy().getTitleDocumentTrxValue(trxContext);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue getTitleDocumentByTrxID(ITrxContext trxContext, String trxID)
			throws CommodityException {
		try {
			return getProxy().getTitleDocumentByTrxID(trxContext, trxID); // To
			// change
			// body
			// of
			// implemented
			// methods
			// use
			// File
			// |
			// Settings
			// |
			// File
			// Templates.
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocument[] getAllTitleDocuments() throws CommodityException {
		try {
			return getProxy().getAllTitleDocuments();
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocument[] getAllTitleDocuments(String documentType) throws CommodityException {
		try {
			return getProxy().getAllTitleDocuments(documentType);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocument getTitleDocumentByTitleDocumentID(long titleDocumentID) throws CommodityException {
		try {
			return getProxy().getTitleDocumentByTitleDocumentID(titleDocumentID);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerCreateTitleDocument(ITrxContext trxContext, ITitleDocument[] value)
			throws CommodityException {
		try {
			return getProxy().makerCreateTitleDocument(trxContext, value);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerApproveCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerApproveCreateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerRejectCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerRejectCreateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerResubmitCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().makerResubmitCreateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerCloseCreateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseCreateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerUpdateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerApproveUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerApproveUpdateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerRejectUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerRejectUpdateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerResubmitUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().makerResubmitUpdateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerCloseUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseUpdateTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerDeleteTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerApproveDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerApproveDeleteTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue checkerRejectDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().checkerRejectDeleteTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerResubmitDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException {
		try {
			return getProxy().makerResubmitDeleteTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ITitleDocumentTrxValue makerCloseDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseDeleteTitleDocument(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	/**
	 * Maker saves title document types.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity title document transaction value
	 * @param types a list of ITitleDocument objects
	 * @return saved commodity title document types
	 * @throws CommodityException on error saving the transaction
	 */
	public ITitleDocumentTrxValue makerSaveTitleDocument(ITrxContext ctx, ITitleDocumentTrxValue trxVal,
			ITitleDocument[] types) throws CommodityException {
		try {
			return getProxy().makerSaveTitleDocument(ctx, trxVal, types);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException at makerSaveTitleDocument:" + e.toString());
		}
	}

	// Warehouse methods
	public IWarehouseTrxValue getWarehouseTrxValue(ITrxContext trxContext, String countryCode)
			throws CommodityException {
		try {
			return getProxy().getWarehouseTrxValue(trxContext, countryCode);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue getWarehouseByTrxID(ITrxContext trxContext, String trxID) throws CommodityException {
		try {
			return getProxy().getWarehouseByTrxID(trxContext, trxID); // To
			// change
			// body
			// of
			// implemented
			// methods
			// use
			// File
			// |
			// Settings
			// |
			// File
			// Templates.
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouse[] getAllWarehouses() throws CommodityException {
		try {
			return getProxy().getAllWarehouses();
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouse getWarehouseByWarehouseID(long warehouseID) throws CommodityException {
		try {
			return getProxy().getWarehouseByWarehouseID(warehouseID);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerCreateWarehouse(ITrxContext trxContext, IWarehouse[] value)
			throws CommodityException {
		try {
			return getProxy().makerCreateWarehouse(trxContext, value);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerApproveCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveCreateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerRejectCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectCreateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerResubmitCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitCreateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerCloseCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseCreateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerUpdateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerApproveUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveUpdateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerRejectUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectUpdateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerResubmitUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitUpdateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerCloseUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseUpdateWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerDeleteWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerApproveDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveDeleteWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue checkerRejectDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectDeleteWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerResubmitDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitDeleteWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IWarehouseTrxValue makerCloseDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseDeleteWarehouse(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
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
		try {
			return getProxy().makerSaveWarehouse(ctx, trxVal, warehouses);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at makerSaveWarehouse", e);
		}
	}

	// Begin Sub-Limit Type related methods.
	public ISubLimitType[] getAllSubLimitTypes() throws CommodityException {
		try {
			return getProxy().getAllSubLimitTypes();
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ISubLimitTypeTrxValue operateSubLimitType(ITrxContext trxContext, ISubLimitTypeTrxValue sltTrxValue,
			String action) throws CommodityException {
		try {
			return getProxy().operateSubLimitType(trxContext, sltTrxValue, action);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	// End Sub-Limit Type related methods.

	// Profile methods
	public SearchResult searchProfile(ITrxContext trxContext, CommodityMainInfoSearchCriteria criteria)
			throws CommodityException {
		try {
			return getProxy().searchProfile(trxContext, criteria);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue getProfileTrxValue(ITrxContext trxContext, ProfileSearchCriteria criteria)
			throws CommodityException {
		try {
			return getProxy().getProfileTrxValue(trxContext, criteria);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue getProfileByTrxID(ITrxContext trxContext, String trxID) throws CommodityException {
		try {
			return getProxy().getProfileByTrxID(trxContext, trxID); // To change
			// body of
			// implemented
			// methods
			// use File
			// |
			// Settings
			// | File
			// Templates.
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue getProfilesByGroupID(ITrxContext trxContext, long groupID) throws CommodityException {
		try {
			return getProxy().getProfilesByGroupID(trxContext, groupID); // To
			// change
			// body
			// of
			// implemented
			// methods
			// use
			// File
			// |
			// Settings
			// |
			// File
			// Templates.
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfile[] getAllProfiles() throws CommodityException {
		try {
			return getProxy().getAllProfiles();
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfile getProfileByProfileID(long profileID) throws CommodityException {
		try {
			return getProxy().getProfileByProfileID(profileID);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ISupplier getSupplierBySupplierID(long supplierID) throws CommodityException {
		try {
			return getProxy().getSupplierBySupplierID(supplierID);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerCreateProfile(ITrxContext trxContext, IProfile[] value) throws CommodityException {
		try {
			return getProxy().makerCreateProfile(trxContext, value);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerApproveCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveCreateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerRejectCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectCreateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerResubmitCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitCreateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerCloseCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseCreateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerUpdateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerApproveUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveUpdateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerRejectUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectUpdateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerResubmitUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitUpdateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerCloseUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseUpdateProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerDeleteProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerApproveDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveDeleteProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue checkerRejectDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectDeleteProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerResubmitDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitDeleteProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerCloseDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseDeleteProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException {
		try {
			return getProxy().getProductSubTypesByCategoryAndProduct(commodityCode, productType);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public IProfileTrxValue makerSaveProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerSaveProfile(trxContext, trxValue);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

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
		try {
			return getProxy().getCommodityPriceTrxValue(ctx, catCode, prodTypeCode, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException caught at getCommodityPriceTrxValue:" + e.toString());
		}
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
		try {
			return getProxy().getCommodityPriceTrxValue(ctx, trxID, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException caught at getCommodityPriceTrxValue by trx id:"
					+ e.toString());
		}
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
		try {
			return getProxy().makerCloseCommodityPrice(ctx, trxVal);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException at makerCloseCommodityPrice:" + e.toString());
		}
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
		try {
			return getProxy().makerUpdateCommodityPrice(ctx, trxVal, prices, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException caught at makerUpdateCommodityPrice:" + e.toString());
		}
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
		try {
			return getProxy().makerSaveCommodityPrice(ctx, trxVal, prices, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException at makerSaveCommodityPrice:" + e.toString());
		}
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
		try {
			return getProxy().systemUpdateCommodityPrice(ctx, trxVal);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException at systemUpdateCommodityPrice:" + e.toString());
		}
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
		try {
			return getProxy().checkerApproveUpdateCommodityPrice(ctx, trxVal, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException caught at checkerApproveUpdateCommodityPrice:" + e.toString());
		}
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
		try {
			return getProxy().checkerRejectUpdateCommodityPrice(ctx, trxVal, ricType);
		}
		catch (RemoteException e) {
			throw new CommodityException("RemoteException at checkerRejectUpdateCommodityPrice:" + e.toString());
		}
	}

	/**
	 * Get unit of measure transaction value based on commodity category and
	 * product type code.
	 * 
	 * @param ctx - transaction context
	 * @param catCode - code denoting the commodity category
	 * @param pdtTypeCode - code denoting the product type
	 * @return the unit of measure transaction value
	 * @throws CommodityException upon encountering error when retrieving
	 *         transaction value
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String catCode, String pdtTypeCode)
			throws CommodityException {
		try {
			return getProxy().getUnitofMeasureTrxValue(ctx, catCode, pdtTypeCode);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getUnitofMeasureTrxValue by catcode and pdtcode", e);
		}
	}

	/**
	 * Get unit of measure transaction value based on transaction id.
	 * 
	 * @param ctx - transaction context
	 * @param trxID - transaction ID
	 * @return the unit of measure transaction value
	 * @throws CommodityException upon encountering error when retrieving
	 *         transaction value
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String trxID) throws CommodityException {
		try {
			return getProxy().getUnitofMeasureTrxValue(ctx, trxID);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getUnitofMeasureTrxValue by trxid", e);
		}
	}

	/**
	 * Maker updates the unit of measure transaction value.
	 * 
	 * @param ctx - transaction context
	 * @param trxValue - unit of measure transaction value
	 * @return the updated transaction value
	 * @throws CommodityException upon encountering error when updating
	 *         transaction value
	 */
	public IUnitofMeasureTrxValue makerUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerUpdateUnitofMeasure(ctx, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at makerUpdateUnitofMeasure", e);
		}
	}

	/**
	 * Checker approves the unit of measure transaction value.
	 * 
	 * @param ctx - transaction context
	 * @param trxValue - unit of measure transaction value
	 * @return the approved transaction value
	 * @throws CommodityException upon encountering error approving the
	 *         transaction value
	 */
	public IUnitofMeasureTrxValue checkerApproveUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerApproveUpdateUnitofMeasure(ctx, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at checkerApproveUpdateUnitofMeasure", e);
		}
	}

	/**
	 * Checker rejects the unit of measure transaction value.
	 * 
	 * @param ctx - transaction context
	 * @param trxValue - unit of measure transaction value
	 * @return the rejected transaction value
	 * @throws CommodityException upon encountering error rejecting transaction
	 *         value
	 */
	public IUnitofMeasureTrxValue checkerRejectUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().checkerRejectUpdateUnitofMeasure(ctx, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at checkerRejectUpdateUnitofMeasure", e);
		}
	}

	/**
	 * Maker resubmits the unit of measure transaction value.
	 * 
	 * @param ctx - transaction context
	 * @param trxValue - unit of measure transaction value
	 * @return the resubmitted transaction value
	 * @throws CommodityException upon encountering error resubmitting
	 *         transaction value
	 */
	public IUnitofMeasureTrxValue makerResubmitUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerResubmitUpdateUnitofMeasure(ctx, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at makerResubmitUpdateUnitofMeasure", e);
		}
	}

	/**
	 * Maker updates the unit of measure transaction value.
	 * 
	 * @param ctx - transaction context
	 * @param trxValue - unit of measure transaction value
	 * @return the closed transaction value
	 * @throws CommodityException
	 */
	public IUnitofMeasureTrxValue makerCloseUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException {
		try {
			return getProxy().makerCloseUpdateUnitofMeasure(ctx, trxValue);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at makerCloseUpdateUnitofMeasure", e);
		}
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
		try {
			return getProxy().makerSaveUnitofMeasure(ctx, trxVal, uoms);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at makerSaveUnitofMeasure", e);
		}
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
		try {
			return getProxy().getUnitofMeasure(profileID);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getUnitofMeasureByProfileID", e);
		}
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
			return getProxy().getLatestPrice(profileID);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getLatestPrice", e);
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
	 * @param profileID
	 * @return first update date of current price
	 * @throws CommodityException
	 */
	public Date getLatestPriceFirstUpdateDate(long profileID) throws CommodityException {
		try {
			return getProxy().getLatestPriceFirstUpdateDate(profileID);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getLatestPriceFirstUpdateDate", e);
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
			return getProxy().getMarketPriceFirstUpdateDate(profileID);
		}
		catch (RemoteException e) {
			throw new CommodityException("Exception at getMarketPriceFirstUpdateDate", e);
		}
	}

	public boolean isRICTypeTransferable(long profileID) throws CommodityException {
		try {
			return getProxy().isRICTypeTransferable(profileID);
		}
		catch (Exception e) {
			throw new CommodityException(e);
		}
	}

	public SBCommodityMaintenanceProxy getProxy() {
		if (_proxy == null) {
			initProxy();
		}
		return _proxy;
	}

	protected SBCommodityMaintenanceProxy _proxy;

	private void initProxy() {
		_proxy = (SBCommodityMaintenanceProxy) BeanController.getEJB(JNDIConstants.SB_COMMODITY_MAINTENANCE_PROXY_HOME,
				JNDIConstants.SB_COMMODITY_MAINTENANCE_PROXY_HOME_PATH);
	}

}
