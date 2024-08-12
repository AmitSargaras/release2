package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class ProductTypeLimitForm extends TrxContextForm implements Serializable {
	 
	private String prodTypeDesc = "";
	private String prodTypeRefCode = "";
	
	private static final long serialVersionUID = 1L;
	
    public String getProdTypeDesc() {
		return prodTypeDesc;
	}

	public void setProdTypeDesc(String prodTypeDesc) {
		this.prodTypeDesc = prodTypeDesc;
	}

	public String getProdTypeRefCode() {
		return prodTypeRefCode;
	}

	public void setProdTypeRefCode(String prodTypeRefCode) {
		this.prodTypeRefCode = prodTypeRefCode;
	}

	public String[][] getMapper() {
        String[][] input =
        {
            {"productTypeLimitForm", "com.integrosys.cms.ui.creditriskparam.producttypelimit.ProductTypeLimitMapper"},
        };
        return input;
    }	
}
