/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/SBCommodityMainInfoManagerStagingBean.java,v 1.13 2005/10/06 03:49:23 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import javax.ejb.SessionBean;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.price.EBCommodityPriceLocalHome;
import com.integrosys.cms.app.commodity.main.bus.profile.EBProfileLocalHome;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitTypeLocalHome;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.main.bus.uom.EBUnitofMeasureLocalHome;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 30, 2004 Time:
 * 9:31:43 AM To change this template use File | Settings | File Templates.
 */
public class SBCommodityMainInfoManagerStagingBean extends SBCommodityMainInfoManagerBean implements SessionBean {

	public SBCommodityMainInfoManagerStagingBean() {
		isStaging = true;
		_titleDocumentLocalHome = null;
		_warehouseLocalHome = null;
		_commodityPriceLocalHome = null;
	}

	protected EBTitleDocumentLocalHome _getTitleDocumentLocalHome() {
		if (_titleDocumentLocalHome == null) {
			_titleDocumentLocalHome = (EBTitleDocumentLocalHome) BeanController.getEJBLocalHome(
					JNDIConstants.EB_TITLE_DOCUMENT_STAGING_LOCAL_BEAN, EBTitleDocumentLocalHome.class.getName());
		}
		return _titleDocumentLocalHome;
	}

	protected EBWarehouseLocalHome _getWarehouseLocalHome() {
		if (_warehouseLocalHome == null) {
			_warehouseLocalHome = (EBWarehouseLocalHome) BeanController.getEJBLocalHome(
					JNDIConstants.EB_WAREHOUSE_STAGING_LOCAL_BEAN, EBWarehouseLocalHome.class.getName());
		}
		return _warehouseLocalHome;
	}

	protected EBProfileLocalHome _getProfileLocalHome() {
		EBProfileLocalHome profileLocalHome = (EBProfileLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_PROFILE_STAGING_LOCAL_BEAN, EBProfileLocalHome.class.getName());
		return profileLocalHome;
	}

	/**
	 * Get local home of staging commodity price bean.
	 * 
	 * @return EBCommodityPriceLocalHome
	 */
	protected EBCommodityPriceLocalHome getCommodityPriceLocalHome() {
		if (_commodityPriceLocalHome == null) {
			_commodityPriceLocalHome = (EBCommodityPriceLocalHome) BeanController.getEJBLocalHome(
					ICMSJNDIConstant.EB_COMMODITY_PRICE_STAGING_LOCAL_JNDI, EBCommodityPriceLocalHome.class.getName());
		}
		return _commodityPriceLocalHome;
	}

	/**
	 * Get local home of staging commodity UOM.
	 * 
	 * @return EBCommodityPriceLocalHome
	 */
	protected EBUnitofMeasureLocalHome getUnitofMeasureLocalHome() {
		EBUnitofMeasureLocalHome unitofMeasureLocalHome = (EBUnitofMeasureLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_UOM_STAGING_LOCAL_JNDI, EBUnitofMeasureLocalHome.class.getName());
		return unitofMeasureLocalHome;
	}

	protected long _generateTitleDocumentPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_TITLE_DOCUMENT_STAGING_SEQ);
	}

	protected long _generateWarehousePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_WAREHOUSE_STAGING_SEQ);
	}

	protected long _generateProfilePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_STAGING_SEQ);
	}

	// Begin - SubLimitType related methods.
	protected EBSubLimitTypeLocalHome _getSubLimitTypeLocalHome() {
		DefaultLogger.debug(this, "_getSubLimitTypeLocalHome - Staging.");
		EBSubLimitTypeLocalHome subLimitTypeLocalHome = (EBSubLimitTypeLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_SLT_STAGING_LOCAL_BEAN, EBSubLimitTypeLocalHome.class.getName());
		isStaging = true;
		return subLimitTypeLocalHome;
	}

	protected long _generateSubLimitTypePK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_SLT_STAGING_SEQ);
	}
	// End - SubLimitType related methods.
}
