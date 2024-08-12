/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/UnitofMeasureSearchCriteria.java,v 1.2 2004/06/04 04:53:24 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:24 $ Tag: $Name: $
 */
public class UnitofMeasureSearchCriteria extends CommodityMainInfoSearchCriteria {

	private String _categoryCode;

	private String _productTypeCode;

	public UnitofMeasureSearchCriteria() {
		super(ICommodityMainInfo.INFO_TYPE_UOM);
	}

	public String getCategoryCode() {
		return _categoryCode;
	}

	public void setCategory(String categoryCode) {
		this._categoryCode = categoryCode;
	}

	public String getProductTypeCode() {
		return _productTypeCode;
	}

	public void setProductTypeCode(String pdtTypeCode) {
		this._productTypeCode = pdtTypeCode;
	}
}
