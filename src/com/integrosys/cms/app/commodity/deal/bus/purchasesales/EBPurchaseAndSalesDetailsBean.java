/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/purchasesales/EBPurchaseAndSalesDetailsBean.java,v 1.9 2004/08/12 07:26:15 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.purchasesales;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.9 $
 * @since $Date: 2004/08/12 07:26:15 $ Tag: $Name: $
 */
public abstract class EBPurchaseAndSalesDetailsBean implements IPurchaseAndSalesDetails, EntityBean {
	private static final String[] EXCLUDE_METHOD = new String[] { "getDetailsID", "setDetailsID" };

	private EntityContext context;

	public EBPurchaseAndSalesDetailsBean() {
	}

	public void setEntityContext(EntityContext context) throws EJBException {
		this.context = context;
	}

	public void unsetEntityContext() throws EJBException {
		context = null;
	}

	public Long ejbCreate(IPurchaseAndSalesDetails details) throws CreateException {
		try {
			String newDetailsPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_DEAL_CASH, true);
			AccessorUtil.copyValue(details, this, EXCLUDE_METHOD);
			setEBDetailsID(new Long(newDetailsPK));

			if (details.getCommonReferenceID() == ICMSConstant.LONG_INVALID_VALUE) {
				setEBCommonReferenceID(getEBDetailsID());
			}
			else {
				// else maintain this reference id.
				setCommonReferenceID(details.getCommonReferenceID());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}

		// return null;
	}

	public void ejbPostCreate(IPurchaseAndSalesDetails details) throws CreateException {
	}

	public void ejbRemove() throws RemoveException, EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbLoad() throws EJBException {
	}

	public void ejbStore() throws EJBException {
	}

	public IPurchaseAndSalesDetails getValue() {
		OBPurchaseAndSalesDetails settlement = new OBPurchaseAndSalesDetails();
		AccessorUtil.copyValue(this, settlement);
		return settlement;
	}

	public void setValue(IPurchaseAndSalesDetails value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	/***** Not to be used, here to fulfill IPurchaseAndSalesDetails contract *****/
	public long getDetailsID() {
		return getEBDetailsID().longValue();
	}

	public void setDetailsID(long detailsID) {
		setEBDetailsID((detailsID == ICMSConstant.LONG_INVALID_VALUE) ? null : new Long(detailsID));
	}

	public IPurchaseDetails getPurchaseDetails() {
		IPurchaseDetails purchaseDetails = new OBPurchaseDetails();
		purchaseDetails.setBankName(getPurchaseBankName());
		purchaseDetails.setIsClaimAllowed(ICMSConstant.TRUE_VALUE.equals(getPurchaseIsClaimAllowed()) ? true : false);
		purchaseDetails.setExpiryDate(getPurchaseExpiryDate());
		purchaseDetails.setNoDaysClaimed((getPurchaseNoDaysClaimed() == null) ? 0 : getPurchaseNoDaysClaimed()
				.intValue());
		purchaseDetails.setOtherSupplierName(getOtherSupplierName());
		purchaseDetails.setPaymentMode(getPurchasePaymentMode());
		purchaseDetails.setReferenceNo(getPurchaseReferenceNo());
		purchaseDetails.setRemarks(getPurchaseRemarks());
		purchaseDetails.setShipDate(getPurchaseShipDate());
		purchaseDetails.setShipmentDestination(getPurchaseShipmentDestination());
		purchaseDetails.setShipmentSource(getPurchaseShipmentSource());
		purchaseDetails.setSupplier((getSupplierID() == null) ? null : getSupplier(getSupplierID().longValue()));
		purchaseDetails.setTransportationDocumentNo(getPurchaseTransportationDocumentNo());

		Quantity qty = ((getPurchaseQuantity() == null) || (getPurchaseQuantityUOMID() == null)) ? null : new Quantity(
				getPurchaseQuantity(), UOMWrapperFactory.getInstance().valueOf(getPurchaseQuantityUOMID().toString()));
		purchaseDetails.setQuantity(qty);

		Amount unitPrice = ((getPurchaseUnitPrice() == null) || (getPurchaseUnitPriceCcyCode() == null)) ? null
				: new Amount(getPurchaseUnitPrice(), new CurrencyCode(getPurchaseUnitPriceCcyCode()));
		purchaseDetails.setUnitPrice(unitPrice);

		return purchaseDetails;
	}

	public void setPurchaseDetails(IPurchaseDetails purchaseDetails) {
		setPurchaseBankName((purchaseDetails == null) ? null : purchaseDetails.getBankName());
		setPurchaseExpiryDate((purchaseDetails == null) ? null : purchaseDetails.getExpiryDate());
		setPurchaseIsClaimAllowed((purchaseDetails == null) ? null
				: ((purchaseDetails.getIsClaimAllowed()) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE));
		setPurchaseNoDaysClaimed((purchaseDetails == null) ? null : new Integer(purchaseDetails.getNoDaysClaimed()));
		setPurchasePaymentMode((purchaseDetails == null) ? null : purchaseDetails.getPaymentMode());
		setPurchaseReferenceNo((purchaseDetails == null) ? null : purchaseDetails.getReferenceNo());
		setPurchaseRemarks((purchaseDetails == null) ? null : purchaseDetails.getRemarks());
		setPurchaseShipDate((purchaseDetails == null) ? null : purchaseDetails.getShipDate());
		setPurchaseShipmentDestination((purchaseDetails == null) ? null : purchaseDetails.getShipmentDestination());
		setPurchaseShipmentSource((purchaseDetails == null) ? null : purchaseDetails.getShipmentSource());
		setPurchaseTransportationDocumentNo((purchaseDetails == null) ? null : purchaseDetails
				.getTransportationDocumentNo());
		setSupplierID((purchaseDetails == null) ? null : ((purchaseDetails.getSupplier() == null) ? null : new Long(
				purchaseDetails.getSupplier().getSupplierID())));
		setOtherSupplierName((purchaseDetails == null) ? null : purchaseDetails.getOtherSupplierName());

		this.setPurchaseQuantity((purchaseDetails == null) ? null : ((purchaseDetails.getQuantity() == null) ? null
				: purchaseDetails.getQuantity().getQuantity()));
		this.setPurchaseQuantityUOMID((purchaseDetails == null) ? null
				: ((purchaseDetails.getQuantity() == null) ? null : purchaseDetails.getQuantity().getUnitofMeasure()
						.getID()));

		this.setPurchaseUnitPrice((purchaseDetails == null) ? null : ((purchaseDetails.getUnitPrice() == null) ? null
				: purchaseDetails.getUnitPrice().getAmountAsBigDecimal()));
		this.setPurchaseUnitPriceCcyCode((purchaseDetails == null) ? null
				: ((purchaseDetails.getUnitPrice() == null) ? null : purchaseDetails.getUnitPrice().getCurrencyCode()));
	}

	public ISalesDetails getSalesDetails() {
		ISalesDetails salesDetails = new OBSalesDetails();
		salesDetails.setBankName(getSalesBankName());
		salesDetails.setBuyer((getBuyerID() == null) ? null : getBuyer(getBuyerID().longValue()));
		salesDetails.setIsClaimAllowed(ICMSConstant.TRUE_VALUE.equals(getSalesIsClaimAllowed()) ? true : false);
		salesDetails.setExpiryDate(getSalesExpiryDate());
		salesDetails.setNoDaysClaimed((getSalesNoDaysClaimed() == null) ? 0 : getSalesNoDaysClaimed().intValue());
		salesDetails.setOtherBuyerName(getOtherBuyerName());
		salesDetails.setPaymentMode(getSalesPaymentMode());
		Quantity qty = ((getSalesQuantity() == null) || (getSalesQuantityUOMID() == null)) ? null : new Quantity(
				getSalesQuantity(), UOMWrapperFactory.getInstance().valueOf(getSalesQuantityUOMID().toString()));
		salesDetails.setQuantity(qty);
		salesDetails.setReferenceNo(getSalesReferenceNo());
		salesDetails.setRemarks(getSalesRemarks());
		salesDetails.setShipDate(getSalesShipDate());
		salesDetails.setShipmentDestination(getSalesShipmentDestination());
		salesDetails.setShipmentSource(getSalesShipmentSource());
		salesDetails.setTransportationDocumentNo(getSalesTransportationDocumentNo());
		Amount unitPrice = ((getSalesUnitPrice() == null) || (getSalesUnitPriceCcyCode() == null)) ? null : new Amount(
				getSalesUnitPrice(), new CurrencyCode(getSalesUnitPriceCcyCode()));
		salesDetails.setUnitPrice(unitPrice);
		return salesDetails;
	}

	public void setSalesDetails(ISalesDetails salesDetails) {
		this.setSalesBankName((salesDetails == null) ? null : salesDetails.getBankName());
		this.setSalesExpiryDate((salesDetails == null) ? null : salesDetails.getExpiryDate());
		this.setSalesIsClaimAllowed((salesDetails == null) ? null
				: ((salesDetails.getIsClaimAllowed()) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE));
		this.setSalesNoDaysClaimed((salesDetails == null) ? null : new Integer(salesDetails.getNoDaysClaimed()));
		this.setSalesPaymentMode((salesDetails == null) ? null : salesDetails.getPaymentMode());
		this.setSalesQuantity((salesDetails == null) ? null : ((salesDetails.getQuantity() == null) ? null
				: salesDetails.getQuantity().getQuantity()));
		this.setSalesQuantityUOMID((salesDetails == null) ? null : ((salesDetails.getQuantity() == null) ? null
				: salesDetails.getQuantity().getUnitofMeasure().getID()));
		this.setSalesReferenceNo((salesDetails == null) ? null : salesDetails.getReferenceNo());
		this.setSalesRemarks((salesDetails == null) ? null : salesDetails.getRemarks());
		this.setSalesShipDate((salesDetails == null) ? null : salesDetails.getShipDate());
		this.setSalesShipmentDestination((salesDetails == null) ? null : salesDetails.getShipmentDestination());
		this.setSalesShipmentSource((salesDetails == null) ? null : salesDetails.getShipmentSource());
		this.setSalesTransportationDocumentNo((salesDetails == null) ? null : salesDetails
				.getTransportationDocumentNo());
		this.setSalesUnitPrice((salesDetails == null) ? null : ((salesDetails.getUnitPrice() == null) ? null
				: salesDetails.getUnitPrice().getAmountAsBigDecimal()));
		this.setSalesUnitPriceCcyCode((salesDetails == null) ? null : ((salesDetails.getUnitPrice() == null) ? null
				: salesDetails.getUnitPrice().getCurrencyCode()));
		this.setBuyerID((salesDetails == null) ? null : ((salesDetails.getBuyer() == null) ? null : new Long(
				salesDetails.getBuyer().getBuyerID())));
		this.setOtherBuyerName((salesDetails == null) ? null : salesDetails.getOtherBuyerName());
	}

	public long getCommonReferenceID() {
		return getEBCommonReferenceID().longValue();
	}

	public void setCommonReferenceID(long refID) {
		setEBCommonReferenceID(new Long(refID));
	}

	public abstract Long getEBDetailsID();

	public abstract void setEBDetailsID(Long detailsID);

	public abstract String getPurchaseReferenceNo();

	public abstract void setPurchaseReferenceNo(String purchaseReferenceNo);

	public abstract Date getPurchaseShipDate();

	public abstract void setPurchaseShipDate(Date purchaseShipDate);

	public abstract Date getPurchaseExpiryDate();

	public abstract void setPurchaseExpiryDate(Date purchaseExpiryDate);

	public abstract String getPurchaseShipmentSource();

	public abstract void setPurchaseShipmentSource(String purchaseShipmentSource);

	public abstract String getPurchaseShipmentDestination();

	public abstract void setPurchaseShipmentDestination(String purchaseShipmentDestination);

	public abstract String getPurchaseTransportationDocumentNo();

	public abstract void setPurchaseTransportationDocumentNo(String purchaseTransportationDocumentNo);

	public abstract String getPurchasePaymentMode();

	public abstract void setPurchasePaymentMode(String purchasePaymentMode);

	public abstract String getPurchaseBankName();

	public abstract void setPurchaseBankName(String purchaseBankName);

	public abstract String getPurchaseIsClaimAllowed();

	public abstract void setPurchaseIsClaimAllowed(String purchaseIsClaimAllowed);

	public abstract Integer getPurchaseNoDaysClaimed();

	public abstract void setPurchaseNoDaysClaimed(Integer purchaseNoDaysClaimed);

	public abstract String getPurchaseRemarks();

	public abstract void setPurchaseRemarks(String purchaseRemarks);

	public abstract BigDecimal getPurchaseQuantity();

	public abstract void setPurchaseQuantity(BigDecimal purchaseQuantity);

	public abstract String getPurchaseQuantityUOMID();

	public abstract void setPurchaseQuantityUOMID(String purchaseQuantityUOMID);

	public abstract BigDecimal getPurchaseUnitPrice();

	public abstract void setPurchaseUnitPrice(BigDecimal purchaseUnitPrice);

	public abstract String getPurchaseUnitPriceCcyCode();

	public abstract void setPurchaseUnitPriceCcyCode(String purchaseUnitPriceCcyCode);

	public abstract String getSalesReferenceNo();

	public abstract void setSalesReferenceNo(String salesReferenceNo);

	public abstract BigDecimal getSalesQuantity();

	public abstract void setSalesQuantity(BigDecimal salesQuantity);

	public abstract String getSalesQuantityUOMID();

	public abstract void setSalesQuantityUOMID(String salesQuantityUOMID);

	public abstract BigDecimal getSalesUnitPrice();

	public abstract void setSalesUnitPrice(BigDecimal salesUnitPrice);

	public abstract String getSalesUnitPriceCcyCode();

	public abstract void setSalesUnitPriceCcyCode(String salesUnitPriceCcyCode);

	public abstract Date getSalesShipDate();

	public abstract void setSalesShipDate(Date salesShipDate);

	public abstract Date getSalesExpiryDate();

	public abstract void setSalesExpiryDate(Date salesExpiryDate);

	public abstract String getSalesShipmentSource();

	public abstract void setSalesShipmentSource(String salesShipmentSource);

	public abstract String getSalesShipmentDestination();

	public abstract void setSalesShipmentDestination(String salesShipmentDestination);

	public abstract String getSalesTransportationDocumentNo();

	public abstract void setSalesTransportationDocumentNo(String salesTransportationDocumentNo);

	public abstract String getSalesPaymentMode();

	public abstract void setSalesPaymentMode(String salesPaymentMode);

	public abstract String getSalesBankName();

	public abstract void setSalesBankName(String salesBankName);

	public abstract String getSalesIsClaimAllowed();

	public abstract void setSalesIsClaimAllowed(String salesIsClaimAllowed);

	public abstract Integer getSalesNoDaysClaimed();

	public abstract void setSalesNoDaysClaimed(Integer salesNoDaysClaimed);

	public abstract String getSalesRemarks();

	public abstract void setSalesRemarks(String salesRemarks);

	public abstract Long getBuyerID();

	public abstract void setBuyerID(Long buyerID);

	public abstract Long getSupplierID();

	public abstract void setSupplierID(Long supplierID);

	public abstract String getOtherBuyerName();

	public abstract void setOtherBuyerName(String otherBuyerName);

	public abstract String getOtherSupplierName();

	public abstract void setOtherSupplierName(String otherSupplierName);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonReferenceID);

	/**
	 * Get purchase and sales details local home.
	 * 
	 * @return EBPurchaseAndSalesDetailsLocalHome
	 */
	protected EBPurchaseAndSalesDetailsLocalHome getLocalHome() {
		EBPurchaseAndSalesDetailsLocalHome ejbHome = (EBPurchaseAndSalesDetailsLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_PURCHASE_SALES_LOCAL_JNDI,
						EBPurchaseAndSalesDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPurchaseAndSalesDetailsLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to get ISupplier with a supplierID
	 * 
	 * @param supplierID
	 * @return ISupplier
	 */
	private ISupplier getSupplier(long supplierID) {
		try {
			if (supplierID != ICMSConstant.LONG_INVALID_VALUE) {
				return CommodityMainInfoManagerFactory.getManager().getSupplierByID(supplierID);
			}
		}
		catch (CommodityException e) {
			DefaultLogger
					.error(this, "Error while getting ISupplier for purchase and sales details - " + supplierID, e);
		}
		return null;
	}

	/**
	 * Helper method to get IBuyer with a buyerID
	 * 
	 * @param buyerID
	 * @return IBuyer
	 */
	private IBuyer getBuyer(long buyerID) {
		try {
			if (buyerID != ICMSConstant.LONG_INVALID_VALUE) {
				return CommodityMainInfoManagerFactory.getManager().getBuyerByID(buyerID);
			}
		}
		catch (CommodityException e) {
			DefaultLogger.error(this, "Error while getting IBuyer for purchase and sales details - " + buyerID, e);
		}
		return null;
	}
}
