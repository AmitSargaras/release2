package com.integrosys.cms.ui.collateral;

import java.util.Map;

import org.apache.struts.action.ActionErrors;

public interface CollateralStpValidator {
	public static final String COL_OB = "collateral";
    public static final String COL_TRX_VALUE = "collateralTrxValue";
    public static final String TRX_STATUS = "status";
    public static final String ERRORS = "errors";
    
    /**
	 * To validate the collateral fields, to check whether the collateral is
	 * ready to be stp to the host
	 * 
	 * @param context consists of all variables needed for validator
	 * @return true if the collateral is ready to be stp, else false
	 */
	public boolean validate(Map context);
	
	public ActionErrors validateAndAccumulate(Map context);

}
