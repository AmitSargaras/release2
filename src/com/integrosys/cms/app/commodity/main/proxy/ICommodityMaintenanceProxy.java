package com.integrosys.cms.app.commodity.main.proxy;

import java.util.Date;
import java.util.Map;

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
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 11:08:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ICommodityMaintenanceProxy {

	// TitleDocument methods
	public ITitleDocumentTrxValue getTitleDocumentTrxValue(ITrxContext trxContext) throws CommodityException;

	public ITitleDocumentTrxValue getTitleDocumentByTrxID(ITrxContext trxContext, String trxID)
			throws CommodityException;

	public ITitleDocument[] getAllTitleDocuments() throws CommodityException;

	public ITitleDocument[] getAllTitleDocuments(String documentType) throws CommodityException;

	public ITitleDocument getTitleDocumentByTitleDocumentID(long titleDocumentID) throws CommodityException;

	public ITitleDocumentTrxValue makerCreateTitleDocument(ITrxContext trxContext, ITitleDocument[] value)
			throws CommodityException;

	public ITitleDocumentTrxValue checkerApproveCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue checkerRejectCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue makerResubmitCreateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue makerCloseCreateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException;

	public ITitleDocumentTrxValue makerUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException;

	public ITitleDocumentTrxValue checkerApproveUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue checkerRejectUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue makerResubmitUpdateTitleDocument(ITrxContext trxContext,
			ITitleDocumentTrxValue trxValue) throws CommodityException;

	public ITitleDocumentTrxValue makerCloseUpdateTitleDocument(ITrxContext trxContext, ITitleDocumentTrxValue trxValue)
			throws CommodityException;

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
			ITitleDocument[] types) throws CommodityException;

	// Warehouse methods
	public IWarehouseTrxValue getWarehouseTrxValue(ITrxContext trxContext, String countryCode)
			throws CommodityException;

	public IWarehouseTrxValue getWarehouseByTrxID(ITrxContext trxContext, String trxID) throws CommodityException;

	public IWarehouse[] getAllWarehouses() throws CommodityException;

	public IWarehouse getWarehouseByWarehouseID(long warehouseID) throws CommodityException;

	public IWarehouseTrxValue makerCreateWarehouse(ITrxContext trxContext, IWarehouse[] value)
			throws CommodityException;

	public IWarehouseTrxValue checkerApproveCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue checkerRejectCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue makerResubmitCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue makerCloseCreateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue makerUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue checkerApproveUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue checkerRejectUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue makerResubmitUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

	public IWarehouseTrxValue makerCloseUpdateWarehouse(ITrxContext trxContext, IWarehouseTrxValue trxValue)
			throws CommodityException;

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
			throws CommodityException;

	// Begin Sub-Limit Type related methods.
	public ISubLimitType[] getAllSubLimitTypes() throws CommodityException;

	public ISubLimitTypeTrxValue operateSubLimitType(ITrxContext trxContext, ISubLimitTypeTrxValue sltTrxValue,
			String action) throws CommodityException;

	// End Sub-Limit Type related methods.

	// Profile methods
	public SearchResult searchProfile(ITrxContext trxContext, CommodityMainInfoSearchCriteria criteria)
			throws CommodityException;

	public IProfileTrxValue getProfileTrxValue(ITrxContext trxContext, ProfileSearchCriteria criteria)
			throws CommodityException;

	public IProfileTrxValue getProfileByTrxID(ITrxContext trxContext, String trxID) throws CommodityException;

	public IProfileTrxValue getProfilesByGroupID(ITrxContext trxContext, long groupID) throws CommodityException;

	public IProfile getProfileByProfileID(long profileID) throws CommodityException;

	public ISupplier getSupplierBySupplierID(long supplierID) throws CommodityException;

	public IProfileTrxValue makerCreateProfile(ITrxContext trxContext, IProfile[] value) throws CommodityException;

	public IProfileTrxValue checkerApproveCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue checkerRejectCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerResubmitCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerCloseCreateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue checkerApproveUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue checkerRejectUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerResubmitUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerCloseUpdateProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	public IProfileTrxValue makerSaveProfile(ITrxContext trxContext, IProfileTrxValue trxValue)
			throws CommodityException;

	/**
	 * gets all product subtypes from profiles created
	 */
	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException;

	// ///////// commodity price //////////

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
			String ricType) throws CommodityException;

	/**
	 * Get commodity price transaction value given its transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity price transaction value
	 * @throws CommodityException on error getting the transaction value
	 */
	public ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxContext ctx, String trxID, String ricType)
			throws CommodityException;

	/**
	 * Maker closes commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return closed commodity prices
	 * @throws CommodityException on error closing the transaction
	 */
	public ICommodityPriceTrxValue makerCloseCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException;

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
			ICommodityPrice[] prices, String ricType) throws CommodityException;

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
			ICommodityPrice[] prices, String ricType) throws CommodityException;

	/**
	 * System updates commodity prices.
	 * 
	 * @param ctx transaction context
	 * @param trxVal transaction value
	 * @return updated commodity price transaction value
	 * @throws CommodityException on error updating the transaction
	 */
	public ICommodityPriceTrxValue systemUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal)
			throws CommodityException;

	/**
	 * Checker approves commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return approved commodity price transaction value
	 * @throws CommodityException on error approving the updated commodity price
	 */
	public ICommodityPriceTrxValue checkerApproveUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException;

	/**
	 * Checker reject commodity prices updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity price transaction value
	 * @return rejected commodity price transaction value
	 * @throws CommodityException on encountering error rejecting the prices
	 */
	public ICommodityPriceTrxValue checkerRejectUpdateCommodityPrice(ITrxContext ctx, ICommodityPriceTrxValue trxVal,
			String ricType) throws CommodityException;

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
			throws CommodityException;

	/**
	 * Maker updates unit of measure approved by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return unit of measure transaction value
	 * @throws CommodityException on encountering error getting the transaction
	 *         value
	 */
	public IUnitofMeasureTrxValue getUnitofMeasureTrxValue(ITrxContext ctx, String trxID) throws CommodityException;

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
			throws CommodityException;

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
			throws CommodityException;

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
			throws CommodityException;

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
			throws CommodityException;

	/**
	 * Maker closes request to update unit of measure rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue unit of measure transaction value
	 * @return closed unit of measure transaction value
	 * @throws CommodityException on encountering error closing the request
	 */
	public IUnitofMeasureTrxValue makerCloseUpdateUnitofMeasure(ITrxContext ctx, IUnitofMeasureTrxValue trxValue)
			throws CommodityException;

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
			IUnitofMeasure[] uoms) throws CommodityException;

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
	public UOMWrapper[] getUnitofMeasure(long profileID) throws CommodityException;

	/**
	 * Gets the latest price from commodity price feed.
	 * 
	 * @param profileID id of a commodity profile
	 * @return Amount object containing the latest price from the commodity
	 *         price feed and its currency
	 * @throws CommodityException
	 */
	public Amount getLatestPrice(long profileID) throws CommodityException;

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
	public Date getLatestPriceFirstUpdateDate(long profileID) throws CommodityException;

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
	public Date getMarketPriceFirstUpdateDate(long profileID) throws CommodityException;

	public boolean isRICTypeTransferable(long profileID) throws CommodityException;
}
