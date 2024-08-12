/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/AbstractCommodityMainInfoManager.java,v 1.9 2005/10/06 03:52:16 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.sql.SQLException;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.price.EBCommodityPriceLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.EBBuyerLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.EBProfileLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.EBSupplierLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitTypeLocalHome;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.main.bus.uom.EBUnitofMeasureLocalHome;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 3:15:08 PM To change this template use File | Settings | File Templates.
 */

public abstract class AbstractCommodityMainInfoManager implements ICommodityMainInfoManager {

	// MaiInfo
	public long getInfoGroupID(String infoType) throws CommodityException {
		try {
			return (new CommodityMainInfoDAO()).getGroupIDForType(infoType);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public ICommodityMainInfo getCommodityMainInfoByTrxID(String trxID, String infoType) throws CommodityException {
		return getCommodityMainInfoByTrxIDPersist(trxID, infoType);
	}

	public ICommodityMainInfo getCommodityMainInfoByID(long theID, String infoType) throws CommodityException {
		return getCommodityMainInfoByIDPersist(theID, infoType);
	}

	public ICommodityMainInfo[] getCommodityMainInfosByGroupID(String groupID, String infoType)
			throws CommodityException {
		return getCommodityMainInfosByGroupIDPersist(groupID, infoType);
	}

	public ICommodityMainInfo[] getAll(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		return getAllPersist(criteria);
	}

	public SearchResult searchCommodityMainInfos(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		return searchCommodityMainInfosPersist(criteria);
	}

	public ICommodityMainInfo createInfo(ICommodityMainInfo info) throws CommodityException {
		return createInfoPersist(info);
	}

	public ICommodityMainInfo[] createInfo(ICommodityMainInfo[] info) throws CommodityException {
		return createInfoPersist(info);
	}

	// public ICommodityMainInfo updateInfo(ICommodityMainInfo info) throws
	// CommodityException {
	// return updateInfoPersist(info);
	// }

	public ICommodityMainInfo[] updateInfo(ICommodityMainInfo[] info) throws CommodityException {
		return updateInfoPersist(info);
	}

	public ICommodityMainInfo deleteInfo(ICommodityMainInfo info) throws CommodityException {
		return deleteInfo(info);
	}

	public ICommodityMainInfo[] deleteInfo(ICommodityMainInfo[] info) throws CommodityException {
		return deleteInfo(info);
	}

	/**
	 * Gets supplier given the supplier id.
	 * 
	 * @param supplierID
	 * @return ISupplier
	 * @throws CommodityException on error getting supplier.
	 */
	public ISupplier getSupplierByID(long supplierID) throws CommodityException {
		return getSupplierByIDPersist(supplierID);
	}

	/**
	 * Gets buyer given the buyer id.
	 * 
	 * @param buyerID
	 * @return IBuyer
	 * @throws CommodityException on error getting buyer
	 */
	public IBuyer getBuyerByID(long buyerID) throws CommodityException {
		return getBuyerByIDPersist(buyerID);
	}

	// MaiInfo
	public abstract ICommodityMainInfo getCommodityMainInfoByTrxIDPersist(String trxID, String infoType)
			throws CommodityException;

	public abstract ICommodityMainInfo getCommodityMainInfoByIDPersist(long titleDocumentID, String infoType)
			throws CommodityException;

	public abstract ICommodityMainInfo[] getCommodityMainInfosByGroupIDPersist(String groupID, String infoType)
			throws CommodityException;

	public abstract ICommodityMainInfo[] getAllPersist(CommodityMainInfoSearchCriteria criteria)
			throws CommodityException;

	public abstract SearchResult searchCommodityMainInfosPersist(CommodityMainInfoSearchCriteria criteria)
			throws CommodityException;

	public abstract ICommodityMainInfo createInfoPersist(ICommodityMainInfo titleDocument) throws CommodityException;

	public abstract ICommodityMainInfo[] createInfoPersist(ICommodityMainInfo[] titleDocument)
			throws CommodityException;

	// public abstract ICommodityMainInfo updateInfoPersist(ICommodityMainInfo
	// titleDocument) throws CommodityException;
	public abstract ICommodityMainInfo[] updateInfoPersist(ICommodityMainInfo[] titleDocument)
			throws CommodityException;

	public abstract ICommodityMainInfo deleteInfoPersist(ICommodityMainInfo titleDocument) throws CommodityException;

	public abstract ICommodityMainInfo[] deleteInfoPersist(ICommodityMainInfo[] titleDocument)
			throws CommodityException;

	public abstract ISupplier getSupplierByIDPersist(long supplierID) throws CommodityException;

	public abstract IBuyer getBuyerByIDPersist(long buyerID) throws CommodityException;

	public long getWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException {
		try {
			return (new CommodityMainInfoDAO()).getGroupIDForWarehouseByCountry(countryCode);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public long getStagingWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException {
		try {
			return (new CommodityMainInfoDAO()).getGroupIDForStagingWarehouseByCountry(countryCode);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	public Map getProductSubTypesByCategoryAndProduct(String commodityCode, String productType)
			throws CommodityException {
		try {
			return new CommodityMainInfoDAO().getAllProductSubTypesByCategoryAndProductType(commodityCode, productType);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}

	protected EBTitleDocumentLocalHome _getTitleDocumentLocalHome() {
		if (_titleDocumentLocalHome == null) {
			_titleDocumentLocalHome = (EBTitleDocumentLocalHome) BeanController.getEJBLocalHome(
					JNDIConstants.EB_TITLE_DOCUMENT_LOCAL_BEAN, EBTitleDocumentLocalHome.class.getName());
		}
		return _titleDocumentLocalHome;
	}

	protected EBWarehouseLocalHome _getWarehouseLocalHome() {
		if (_warehouseLocalHome == null) {
			_warehouseLocalHome = (EBWarehouseLocalHome) BeanController.getEJBLocalHome(
					JNDIConstants.EB_WAREHOUSE_LOCAL_BEAN, EBWarehouseLocalHome.class.getName());
		}
		return _warehouseLocalHome;
	}

	// Begin Sub-Limit Type related methods.
	protected EBSubLimitTypeLocalHome _getSubLimitTypeLocalHome() {
		EBSubLimitTypeLocalHome subLimitTypeLocalHome = (EBSubLimitTypeLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_SLT_LOCAL_BEAN, EBSubLimitTypeLocalHome.class.getName());
		return subLimitTypeLocalHome;
	}

	// End Sub-Limit Type related methods.

	protected EBProfileLocalHome _getProfileLocalHome() {
		EBProfileLocalHome profileLocalHome = (EBProfileLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_PROFILE_LOCAL_BEAN, EBProfileLocalHome.class.getName());
		return profileLocalHome;
	}

	protected EBBuyerLocalHome _getBuyerLocalHome() {
		return (EBBuyerLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_BUYER_LOCAL_BEAN,
				EBBuyerLocalHome.class.getName());
	}

	protected EBSupplierLocalHome _getSupplierLocalHome() {
		return (EBSupplierLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_SUPPLIER_LOCAL_BEAN,
				EBSupplierLocalHome.class.getName());
	}

	/**
	 * Get local home of commodity price bean.
	 * 
	 * @return EBCommodityPriceLocalHome
	 */
	protected EBCommodityPriceLocalHome getCommodityPriceLocalHome() {
		if (_commodityPriceLocalHome == null) {
			_commodityPriceLocalHome = (EBCommodityPriceLocalHome) BeanController.getEJBLocalHome(
					ICMSJNDIConstant.EB_COMMODITY_PRICE_LOCAL_JNDI, EBCommodityPriceLocalHome.class.getName());
		}
		return _commodityPriceLocalHome;
	}

	/**
	 * Get local home of commodity price history bean.
	 * 
	 * @return EBCommodityPriceLocalHome
	 */
	protected EBCommodityPriceLocalHome getCommodityPriceHistoryLocalHome() {
		if (_commodityPriceHistoryLocalHome == null) {
			_commodityPriceHistoryLocalHome = (EBCommodityPriceLocalHome) BeanController.getEJBLocalHome(
					ICMSJNDIConstant.EB_COMMODITY_PRICE_HISTORY_LOCAL_JNDI, EBCommodityPriceLocalHome.class.getName());
		}
		return _commodityPriceHistoryLocalHome;
	}

	/**
	 * Get local home of unit of measure bean.
	 * 
	 * @return EBCommodityPriceLocalHome
	 */
	protected EBUnitofMeasureLocalHome getUnitofMeasureLocalHome() {
		EBUnitofMeasureLocalHome unitofMeasureLocalHome = (EBUnitofMeasureLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_UOM_LOCAL_JNDI, EBUnitofMeasureLocalHome.class.getName());
		return unitofMeasureLocalHome;
	}

	protected EBTitleDocumentLocalHome _titleDocumentLocalHome = null;

	protected EBWarehouseLocalHome _warehouseLocalHome = null;

	protected EBSupplierLocalHome _supplierLocalHome = null;

	protected EBCommodityPriceLocalHome _commodityPriceLocalHome = null;

	protected EBCommodityPriceLocalHome _commodityPriceHistoryLocalHome = null;

	protected boolean isStaging = false;
}
