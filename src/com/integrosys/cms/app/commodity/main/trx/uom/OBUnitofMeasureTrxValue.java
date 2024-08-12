/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/OBUnitofMeasureTrxValue.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public class OBUnitofMeasureTrxValue extends OBCMSTrxValue implements IUnitofMeasureTrxValue {

	private IUnitofMeasure[] values = null;

	private IUnitofMeasure[] stagingValues = null;

	private String categoryCode;

	private String productTypeCode;

	public OBUnitofMeasureTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_UOM);
	}

	public OBUnitofMeasureTrxValue(ICMSTrxValue trxValue) {
		this();
		AccessorUtil.copyValue(trxValue, this);
	}

	public OBUnitofMeasureTrxValue(IUnitofMeasure[] objs) {
		this();
		AccessorUtil.copyValue(objs, this);
	}

	public OBUnitofMeasureTrxValue(ITrxValue[] objs) {
		this();
		AccessorUtil.copyValue(objs, this);
	}

	public IUnitofMeasure[] getUnitofMeasure() {
		return values;
	}

	public IUnitofMeasure[] getStagingUnitofMeasure() {
		return stagingValues;
	}

	public void setUnitofMeasure(IUnitofMeasure[] values) {
		this.values = values;
	}

	public void setStagingUnitofMeasure(IUnitofMeasure[] values) {
		this.stagingValues = values;
	}

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Set commodity category code.
	 * 
	 * @param commodityCategoryCode of type String
	 */
	public void setCategoryCode(String commodityCategoryCode) {
		this.categoryCode = commodityCategoryCode;
	}

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getProductTypeCode() {
		return productTypeCode;
	}

	/**
	 * Set commodity product type code.
	 * 
	 * @param commodityProdTypeCode of type String
	 */
	public void setProductTypeCode(String commodityProdTypeCode) {
		this.productTypeCode = commodityProdTypeCode;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
