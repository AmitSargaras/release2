/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/SBCommodityMaintenanceProxy.java,v 1.16 2006/10/12 03:07:57 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

// java
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
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
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 3:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SBCommodityMaintenanceProxy extends EJBObject {

	// TitleDocument methods
	public ITitleDocumentTrxValue getTitleDocumentTrxValue(ITrxContext trxContext) throws CommodityException,
			RemoteException;

	public ITitleDocumentTrxValue getTitleDocumentByTrxID(ITrxContext trxContext, String trxID)
			throws CommodityException, RemoteException;

	public ITitleDocument[] getAllTitleDocuments() throws CommodityException, RemoteException;

	public ITitleDocument[] getAllTitleDocuments(String documentType) throws CommodityException, RemoteException;

	public ITitleDocument getTitleDocumentByTitleDocumentID(long titleDocumentID) throws CommodityException,
			RemoteException;

	public ITitleDocumentTrxValue makerCreateTitleDocument(ITrxContext trxContext, ITitleDocument[] value)
			throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerApproveCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerRejectCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerResubmitCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerCloseCreateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerApproveUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerRejectUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerResubmitUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerCloseUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerApproveDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue checkerRejectDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerResubmitDeleteTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException, RemoteException;

	public ITitleDocumentTrxValue makerCloseDeleteTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Maker saves title document types.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity title document transaction value
	 * @param types a list of ITitleDocument objects
	 * @return saved commodity title document types
	 * @throws CommodityException on error saving the transaction
	 * @throws RemoteException on error during remote method call
	 */
	public ITitleDocumentTrxValue makerSaveTitleDocument(ITrxContext ctx, ITitleDocumentTrxValue trxVal,
			ITitleDocument[] types) throws CommodityException, RemoteException;

	// Warehouse methods
	public IWarehouseTrxValue getWarehouseTrxValue(ITrxContext trxContext, String countryCode)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue getWarehouseByTrxID(ITrxContext trxContext, String trxID) throws CommodityException,
			RemoteException;

	public IWarehouse[] getAllWarehouses() throws CommodityException, RemoteException;

	public IWarehouse getWarehouseByWarehouseID(long warehouseID) throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerCreateWarehouse(ITrxContext trxContext, IWarehouse[] value)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerApproveCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerRejectCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerResubmitCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerCloseCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerApproveUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerRejectUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerResubmitUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerCloseUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerApproveDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue checkerRejectDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerResubmitDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IWarehouseTrxValue makerCloseDeleteWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Maker saves commodity warehouse.
	 * 
	 * @param ctx transaction context
	 * @param trxVal warehouse transaction value
	 * @param warehouses a list of IWarehouse objects
	 * @return saved warehouses
	 * @throws CommodityException on error saving the transaction
	 * @throws RemoteException on error during remote method call
	 */
	public IWarehouseTrxValue makerSaveWarehouse(ITrxContext ctx, IWarehouseTrxValue trxVal, IWarehouse[] warehouses)
			throws CommodityException, RemoteException;

	// Begin Sub-Limit Type related methods.
	public ISubLimitType[] getAllSubLimitTypes() throws CommodityException, RemoteException;

	public ISubLimitTypeTrxValue operateSubLimitType(ITrxContext trxContext, ISubLimitTypeTrxValue sltTrxValue,
			String action) throws CommodityException, RemoteException;

	// End Sub-Limit Type related methods.

	// Profile methods
	public SearchResult searchProfile(ITrxContext trxContext, CommodityMainInfoSearchCriteria criteria)
			throws CommodityException, RemoteException;

	public IProfileTrxValue getProfileTrxValue(ITrxContext trxContext, ProfileSearchCriteria criteria)
			throws CommodityException, RemoteException;

	public IProfileTrxValue getProfileByTrxID(ITrxContext trxContext, String trxID) throws CommodityException,
			RemoteException;

	public IProfileTrxValue getProfilesByGroupID(ITrxContext trxContext, long groupID) throws CommodityException,
			RemoteException;

	public IProfile[] getAllProfiles() throws CommodityException, RemoteException;

	public IProfile getProfileByProfileID(long profileID) throws CommodityException, RemoteException;

	public ISupplier getSupplierBySupplierID(long supplierID) throws CommodityException, RemoteException;

	public IProfileTrxValue makerCreateProfile(ITrxContext trxContext, IProfile[] value) throws CommodityException,
			RemoteException;

	public IProfileTrxValue checkerApproveCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue checkerRejectCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerResubmitCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerCloseCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue checkerApproveUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue checkerRejectUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerResubmitUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerCloseUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue checkerApproveDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue checkerRejectDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerResubmitDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerCloseDeleteProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException, RemoteException;

	public IProfileTrxValue makerSaveProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException, RemoteException;

	// ///// commodity price //////////
	/**
	 * Get commodity price transaction value based on commodity category and
	 * product type code.
	 * 
	 * @param ctx transaction context
	 * @param catCode commodity category code
	 * @param prodTypeCode commodity product type code
	 * @return commodity price transaction value
	 * @throws CommodityException on error getting the transaction value
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxContext ctx, String catCode, String prodTypeCode,
			String ricType) throws CommodityException, RemoteException;

	/**
	 * Get commodity price transaction value given its transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity price transaction value
	 * @throws CommodityException on error getting the transaction value
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxContext ctx, String trxID, String ricType)
			throws CommodityException, RemoteException;

	/**
	 * Maker closes commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return closed commodity prices
	 * @throws CommodityException on error closing the transaction
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue makerCloseCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException, RemoteException;

	/**
	 * Maker updates commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal transaction value of commodity price
	 * @param prices a list of commodity prices to be created
	 * @return newly updated commodity price contained in transaction value
	 * @throws CommodityException on error updating commodity price
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue makerUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			ICommodityPrice[] prices, String ricType) throws CommodityException, RemoteException;

	/**
	 * Maker saves commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @param prices a list of ICommodityPrice objects
	 * @return saved commodity prices
	 * @throws CommodityException on error saving the transaction
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue makerSaveCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			ICommodityPrice[] prices, String ricType) throws CommodityException, RemoteException;

	/**
	 * System updates commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal transaction value
	 * @return updated commodity price transaction value
	 * @throws CommodityException on error updating the transaction
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue systemUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException, RemoteException;

	/**
	 * Checker approve commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return approved commodity price transaction value
	 * @throws CommodityException on error approving the updated commodity price
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue checkerApproveUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException, RemoteException;

	/**
	 * Checker reject commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return rejected commodity price transaction value
	 * @throws CommodityException on error rejecting the prices
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPriceTrxValue checkerRejectUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException, RemoteException;

	// Unit of Measure
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
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String catCode, String prodTypeCode)
			throws CommodityException, RemoteException;

	/**
	 * Maker updates unit of measure approved by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return unit of measure transaction value
	 * @throws CommodityException on encountering error getting the transaction
	 *         value
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String trxID) throws CommodityException,
			RemoteException;

	/**
	 * Maker updates unit of measure approved by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return updated unit of measure transaction value
	 * @throws CommodityException on encountering error updating the unit of
	 *         measures
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue makerUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Checker approves unit of measure updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return approved unit of measure transaction value
	 * @throws CommodityException on encountering error approving the unit of
	 *         measures
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue checkerApproveUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Checker rejects unit of measure updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return rejected unit of measure transaction value
	 * @throws CommodityException on encountering error rejecting the unit of
	 *         measures
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue checkerRejectUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Maker resubmits unit of measure rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return resubmitted unit of measure transaction value
	 * @throws CommodityException on encountering error resubmitting the unit of
	 *         measures
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue makerResubmitUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException, RemoteException;

	/**
	 * Maker closes request to update unit of measure rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return closed unit of measure transaction value
	 * @throws CommodityException on encountering error closing the request
	 * @throws RemoteException on error during remote method call
	 */
	public IUnitofMeasureTrxValue makerCloseUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException, RemoteException;

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
			IUnitofMeasure[] uoms) throws CommodityException, RemoteException;

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
	 * @throws RemoteException on error during remote method call
	 */
	public UOMWrapper[] getUnitofMeasure(long profileID) throws CommodityException, RemoteException;

	/**
	 * Gets the latest price from commodity price feed.
	 * 
	 * @param profileID id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws CommodityException
	 */
	public Amount getLatestPrice(long profileID) throws CommodityException, RemoteException;

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
	public Date getLatestPriceFirstUpdateDate(long profileID) throws CommodityException, RemoteException;

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
	public Date getMarketPriceFirstUpdateDate(long profileID) throws CommodityException, RemoteException;

	public boolean isRICTypeTransferable(long profileID) throws CommodityException, RemoteException;
}
