/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoManagerImpl.java,v 1.7 2006/03/03 05:30:30 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.rmi.RemoteException;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 3:45:56 PM To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoManagerImpl implements ICommodityMainInfoManager {
	// MaiInfo
	public long getInfoGroupID(String infoType) throws CommodityException {
		try {
			return getManager().getInfoGroupID(infoType);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public ICommodityMainInfo getCommodityMainInfoByTrxID(String trxID, String infoType) throws CommodityException {
		try {
			return getManager().getCommodityMainInfoByTrxID(trxID, infoType);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo getCommodityMainInfoByID(long infoID, String infoType) throws CommodityException {
		try {
			return getManager().getCommodityMainInfoByID(infoID, infoType);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo[] getCommodityMainInfosByGroupID(String groupID, String infoType)
			throws CommodityException {
		try {
			return getManager().getCommodityMainInfosByGroupID(groupID, infoType);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo[] getAll(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		try {
			return getManager().getAll(criteria);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}

	}

	public SearchResult searchCommodityMainInfos(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		try {
			return getManager().searchCommodityMainInfos(criteria);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo createInfo(ICommodityMainInfo info) throws CommodityException {
		try {
			return getManager().createInfo(info);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo[] createInfo(ICommodityMainInfo[] info) throws CommodityException {
		try {
			return getManager().createInfo(info);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	/*
	 * public ICommodityMainInfo updateInfo(ICommodityMainInfo info) throws
	 * CommodityException { try { return getManager().updateInfo( info ); }
	 * catch (RemoteException e) { e.printStackTrace();
	 * DefaultLogger.debug(this, e.toString()); return null; } }
	 */

	public ICommodityMainInfo[] updateInfo(ICommodityMainInfo[] info) throws CommodityException {
		try {
			return getManager().updateInfo(info);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo deleteInfo(ICommodityMainInfo info) throws CommodityException {
		try {
			return getManager().deleteInfo(info);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	public ICommodityMainInfo[] deleteInfo(ICommodityMainInfo[] info) throws CommodityException {
		try {
			return getManager().deleteInfo(info);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	/**
	 * Get commodity price given it category and product.
	 * 
	 * @param catCode commodity category code
	 * @param prodCode product type code
	 * @return a list of commodity profile objects
	 * @throws CommodityException on error getting the commodity price
	 */
	public ICommodityPrice[] getCommodityPrice(String catCode, String prodCode, String ricType)
			throws CommodityException {
		try {
			return getManager().getCommodityPrice(catCode, prodCode, ricType);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("RemoteException caught" + e.toString());
		}
	}

	/**
	 * Create commodity price history.
	 * 
	 * @param prices of type ICommodityPrice[]
	 * @return ICommodityPrice[]
	 * @throws CommodityException on error creating history of commodity prices
	 */
	public ICommodityPrice[] createCommodityPriceHistory(ICommodityPrice[] prices) throws CommodityException {
		try {
			return getManager().createCommodityPriceHistory(prices);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException("RemoteException caught" + e.toString());
		}
	}

	public long getWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException {
		try {
			return getManager().getWarehouseGroupIDByCountryCode(countryCode);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
		}
	}

	public long getStagingWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException {
		return -1;
	}

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException {
		try {
			return getManager().getProductSubTypesByCategoryAndProduct(commodityCode, productType);
		}
		catch (RemoteException e) {
			throw new CommodityException(e);
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
	public UOMWrapper[] getUnitofMeasureByProfileID(long profileID) throws CommodityException {
		try {
			return getManager().getUnitofMeasureByProfileID(profileID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * Gets unit of measure given the unit of measure id.
	 * 
	 * @return unit of measure wrapper
	 * @throws CommodityException on error getting the unit of measure.
	 */
	public UOMWrapper getUnitofMeasureByID(long uomID) throws CommodityException {
		try {
			return getManager().getUnitofMeasureByID(uomID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	public ISupplier getSupplierByID(long supplierID) throws CommodityException {
		try {
			return getManager().getSupplierByID(supplierID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	public IBuyer getBuyerByID(long buyerID) throws CommodityException {
		try {
			return getManager().getBuyerByID(buyerID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityException(e);
		}
	}

	/**
	 * helper method to get an ejb object to collateral business manager session
	 * bean.
	 * 
	 * @return collateral manager ejb object
	 * @throws CommodityException on errors encountered
	 */
	protected SBCommodityMainInfoManager getManager() throws CommodityException {
		SBCommodityMainInfoManager theEjb = (SBCommodityMainInfoManager) BeanController.getEJB(
				JNDIConstants.SB_COMMODITY_MAIN_INFO_MGR_HOME, JNDIConstants.SB_COMMODITY_MAIN_INFO_MGR_HOME_PATH);

		if (theEjb == null) {
			throw new CommodityException("SBCommodityMainInfoManager is not deployed. Unable to find name in JNDI!");
		}

		return theEjb;
	}

}
