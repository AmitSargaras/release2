package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author Govind.Sahu
 */
 
public interface IRbiCategoryTrxValue  extends ICMSTrxValue {
    
    /**
	 * @return the rbiCategory
	 */
	public IRbiCategory getRbiCategory();

	/**
	 * @param rbiCategory the rbiCategory to set
	 */
	public void setRbiCategory(IRbiCategory rbiCategory) ;
	/**
	 * @return the stagingRbiCategory
	 */
	public IRbiCategory getStagingRbiCategory() ;
	/**
	 * @param stagingRbiCategory the stagingRbiCategory to set
	 */
	public void setStagingRbiCategory(IRbiCategory stagingRbiCategory);
}
