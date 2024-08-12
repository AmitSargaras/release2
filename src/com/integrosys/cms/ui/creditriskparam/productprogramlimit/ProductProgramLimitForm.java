package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Author: Priya
 * Date: Oct 5, 2008
 */
public class ProductProgramLimitForm extends TrxContextForm implements Serializable {

    private String prodProgramDesc = "";
    private String prodProgramRefCode = "";
    private String limitAmt;
    
    private static final long serialVersionUID = 1L;
    
    private String[] deleteItems;
    
    
    public String[][] getMapper() {
        String[][] input =
        {
            {"productProgramLimitForm", "com.integrosys.cms.ui.creditriskparam.productprogramlimit.ProductProgramLimitMapper"},
            {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
        };
        return input;
    }


	public String getProdProgramDesc() {
		return prodProgramDesc;
	}


	public void setProdProgramDesc(String prodProgramDesc) {
		this.prodProgramDesc = prodProgramDesc;
	}


	public String getProdProgramRefCode() {
		return prodProgramRefCode;
	}


	public void setProdProgramRefCode(String prodProgramRefCode) {
		this.prodProgramRefCode = prodProgramRefCode;
	}


	public String getLimitAmt() {
		return limitAmt;
	}


	public void setLimitAmt(String limitAmt) {
		this.limitAmt = limitAmt;
	}


	public String[] getDeleteItems() {
		return deleteItems;
	}


	public void setDeleteItems(String[] deleteItems) {
		this.deleteItems = deleteItems;
	}

}
