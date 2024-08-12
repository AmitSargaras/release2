/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IStock.java,v 1.5 2005/05/19 12:19:46 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents Stocks of the Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/05/19 12:19:46 $ Tag: $Name: $
 */
public interface IStock extends IGeneralChargeSubType {

	public static final String STOCK_TYPE_RAW_MATERIALS = "Raw Materials";

	public static final String STOCK_TYPE_FINISHED_GOODS = "Finished Goods";

	public static final String STOCK_TYPE_GOODS_IN_TRANSIT = "Goods-in-Transit";

	public static final String STOCK_TYPE_WORK_IN_PROGRESS = "Work-in-Progress";

	public static final String STOCK_TYPE_STORES_AND_SPARES = "Stores & Spares";

	public static final String STOCK_TYPE_OTHER_MERCHANDISE = "Other Merchandise";

	public long getAssetGCStockID();

	public void setAssetGCStockID(long assetGCStockID);

	/**
	 * Get stockID.
	 * 
	 * @return String
	 */
	public String getStockID();

	/**
	 * Set stockID.
	 * 
	 * @param stockID is of type String
	 */
	public void setStockID(String stockID);

	public Amount getRecoverableAmount();

	public void setRecoverableAmount(Amount recoverableAmt);

	public Amount getCreditorAmt();

	public void setCreditorAmt(Amount creditorAmt);

	public Amount getFinishGoodsAmt();

	public void setFinishGoodsAmt(Amount finishGoodsAmt);

	public double getFinishGoodsMargin();

	public void setFinishGoodsMargin(double finishGoodsMargin);

	public Amount getGoodsTransitAmt();

	public void setGoodsTransitAmt(Amount goodsTransitAmt);

	public double getGoodsTransitMargin();

	public void setGoodsTransitMargin(double goodsTransitMargin);

	public boolean getPhysicalInspectionDone();

	public void setPhysicalInspectionDone(boolean physicalInspectionDone);

	public Date getLastPhysicalInspectDate();

	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	public Date getNextPhysicalInspectDate();

	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	public Amount getOtherMerchandiseAmt();

	public void setOtherMerchandiseAmt(Amount otherMerchandiseAmt);

	public double getOtherMerchandiseMargin();

	public void setOtherMerchandiseMargin(double otherMerchandiseMargin);

	public int getPhysicalInspectionFreq();

	public void setPhysicalInspectionFreq(int physicalInspectionFreq);

	public String getPhysicalInspectionFreqUnit();

	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	public Amount getRawMaterialAmt();

	public void setRawMaterialAmt(Amount rawMaterialAmt);

	public double getRawMaterialMargin();

	public void setRawMaterialMargin(double rawMaterialMargin);

	public Amount getStoresSparesAmt();

	public void setStoresSparesAmt(Amount storesSparesAmt);

	public double getStoresSparesMargin();

	public void setStoresSparesMargin(double storesSparesMargin);

	public Amount getWorkProgressAmt();

	public void setWorkProgressAmt(Amount workProgressAmt);

	public double getWorkProgressMargin();

	public void setWorkProgressMargin(double workProgressMargin);

	public String[] getStockType();

}