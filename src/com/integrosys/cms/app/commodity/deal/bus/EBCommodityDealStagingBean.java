/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDealStagingBean.java,v 1.4 2004/09/07 07:45:56 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.commodity.deal.bus.cash.EBDealCashDepositLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBCommodityTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBFinancingDocLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBHedgePriceExtensionLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBReceiptReleaseLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBSettlementLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.EBPurchaseAndSalesDetailsLocalHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging Commodity Deal entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/09/07 07:45:56 $ Tag: $Name: $
 */
public abstract class EBCommodityDealStagingBean extends EBCommodityDealBean {
	/**
	 * Get settlement local home.
	 * 
	 * @return EBSettlementLocalHome
	 */
	protected EBSettlementLocalHome getEBSettlementLocalHome() {
		EBSettlementLocalHome ejbHome = (EBSettlementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SETTLEMENT_STAGING_LOCAL_JNDI, EBSettlementLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSettlementLocalStagingHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get receipt release local home.
	 * 
	 * @return EBReceiptReleaseLocalHome
	 */
	protected EBReceiptReleaseLocalHome getEBReceiptReleaseLocalHome() {
		EBReceiptReleaseLocalHome ejbHome = (EBReceiptReleaseLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECEIPT_RELEASE_STAGING_LOCAL_JNDI, EBReceiptReleaseLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBReceiptReleaseLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get staging financing document local home.
	 * 
	 * @return EBFinancingDocLocalHome
	 */
	protected EBFinancingDocLocalHome getEBFinancingDocLocalHome() {
		EBFinancingDocLocalHome ejbHome = (EBFinancingDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FINANCING_DOC_STAGING_LOCAL_JNDI, EBFinancingDocLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFinancingDocStagingLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get staging commodity title document local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBCommodityTitleDocumentLocalHome getEBCommodityTitleDocLocalHome() {
		EBCommodityTitleDocumentLocalHome ejbHome = (EBCommodityTitleDocumentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_TITLE_DOC_STAGING_LOCAL_JNDI, EBCommodityTitleDocumentLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCommodityTitleDocumentStagingLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal cash deposit local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBDealCashDepositLocalHome getEBDealCashDepositLocalHome() {
		EBDealCashDepositLocalHome ejbHome = (EBDealCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEAL_CASH_DEPOSIT_STAGING_LOCAL_JNDI, EBDealCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBDealCashDepositStagingLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal hedged price extension local home.
	 * 
	 * @return EBHedgePriceExtensionLocalHome
	 */
	protected EBHedgePriceExtensionLocalHome getEBHedgePriceExtensionLocalHome() {
		EBHedgePriceExtensionLocalHome ejbHome = (EBHedgePriceExtensionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGE_PRICE_EXTENSION_STAGING_LOCAL_JNDI, EBHedgePriceExtensionLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgePriceExtensionStagingLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal purchase and sales details local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBPurchaseAndSalesDetailsLocalHome getEBPurchaseAndSalesDetailsLocalHome() {
		EBPurchaseAndSalesDetailsLocalHome ejbHome = (EBPurchaseAndSalesDetailsLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_PURCHASE_SALES_STAGING_LOCAL_JNDI,
						EBPurchaseAndSalesDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPurchaseAndSalesDetailsStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}