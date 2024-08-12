/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/SBCommodityMainInfoManager.java,v 1.6 2006/03/03 05:30:30 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

//java
import java.rmi.RemoteException;
import java.util.Map;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 3:06:06 PM To change this template use File | Settings | File Templates.
 */
public interface SBCommodityMainInfoManager extends EJBObject {

	// MaiInfo
	public long getInfoGroupID(String infoType) throws CommodityException, RemoteException;

	public ICommodityMainInfo getCommodityMainInfoByTrxID(String trxID, String infoType) throws CommodityException,
			RemoteException;

	public ICommodityMainInfo getCommodityMainInfoByID(long infoID, String infoType) throws CommodityException,
			RemoteException;

	public ICommodityMainInfo[] getCommodityMainInfosByGroupID(String groupID, String infoType)
			throws CommodityException, RemoteException;

	public ICommodityMainInfo[] getAll(CommodityMainInfoSearchCriteria criteria) throws CommodityException,
			RemoteException;

	public SearchResult searchCommodityMainInfos(CommodityMainInfoSearchCriteria criteria) throws CommodityException,
			RemoteException;

	public ICommodityMainInfo createInfo(ICommodityMainInfo titleDocument) throws CommodityException, RemoteException;

	public ICommodityMainInfo[] createInfo(ICommodityMainInfo[] titleDocument) throws CommodityException,
			RemoteException;

	// public ICommodityMainInfo updateInfo(ICommodityMainInfo titleDocument)
	// throws CommodityException, RemoteException;
	public ICommodityMainInfo[] updateInfo(ICommodityMainInfo[] titleDocument) throws CommodityException,
			RemoteException;

	public ICommodityMainInfo deleteInfo(ICommodityMainInfo titleDocument) throws CommodityException, RemoteException;

	public ICommodityMainInfo[] deleteInfo(ICommodityMainInfo[] titleDocument) throws CommodityException,
			RemoteException;

	/**
	 * Get commodity price given it category and product.
	 * 
	 * @param catCode commodity category code
	 * @param prodCode product type code
	 * @return a list of commodity profile objects
	 * @throws CommodityException on error getting the commodity price
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPrice[] getCommodityPrice(String catCode, String prodCode, String ricType)
			throws CommodityException, RemoteException;

	/**
	 * Create commodity price history.
	 * 
	 * @param prices of type ICommodityPrice[]
	 * @return ICommodityPrice[]
	 * @throws CommodityException on error creating history of commodity prices
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityPrice[] createCommodityPriceHistory(ICommodityPrice[] prices) throws CommodityException,
			RemoteException;

	public long getWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException, RemoteException;

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException, RemoteException;

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
	public UOMWrapper[] getUnitofMeasureByProfileID(long profileID) throws CommodityException, RemoteException;

	/**
	 * Gets unit of measure given the unit of measure id.
	 * 
	 * @return unit of measure wrapper
	 * @throws CommodityException on error getting the unit of measure.
	 * @throws RemoteException on error during remote method call
	 */
	public UOMWrapper getUnitofMeasureByID(long uomID) throws CommodityException, RemoteException;

	/**
	 * Gets supplier given the supplier id.
	 * 
	 * @param supplierID
	 * @return ISupplier
	 * @throws CommodityException on error getting supplier.
	 * @throws RemoteException on error during remote method call*
	 */
	public ISupplier getSupplierByID(long supplierID) throws CommodityException, RemoteException;

	/**
	 * Gets buyer given the buyer id.
	 * 
	 * @param supplierID
	 * @return IBuyer
	 * @throws CommodityException on error getting buyer
	 * @throws RemoteException on error during remote method call*
	 */
	public IBuyer getBuyerByID(long supplierID) throws CommodityException, RemoteException;
}
