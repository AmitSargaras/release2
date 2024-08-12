package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author Govind.Sahu
 */
public class OBRbiCategoryTrxValue extends OBCMSTrxValue implements IRbiCategoryTrxValue{

    public  OBRbiCategoryTrxValue(){}

    IRbiCategory rbiCategory ;
    IRbiCategory stagingRbiCategory ;

    public OBRbiCategoryTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the rbiCategory
	 */
	public IRbiCategory getRbiCategory() {
		return rbiCategory;
	}

	/**
	 * @param rbiCategory the rbiCategory to set
	 */
	public void setRbiCategory(IRbiCategory rbiCategory) {
		this.rbiCategory = rbiCategory;
	}

	/**
	 * @return the stagingRbiCategory
	 */
	public IRbiCategory getStagingRbiCategory() {
		return stagingRbiCategory;
	}

	/**
	 * @param stagingRbiCategory the stagingRbiCategory to set
	 */
	public void setStagingRbiCategory(IRbiCategory stagingRbiCategory) {
		this.stagingRbiCategory = stagingRbiCategory;
	}

    
   

}
