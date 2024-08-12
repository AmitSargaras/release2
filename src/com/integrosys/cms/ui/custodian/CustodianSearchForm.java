package com.integrosys.cms.ui.custodian;

import com.integrosys.cms.ui.customer.CustomerSearchForm;

public class CustodianSearchForm extends CustomerSearchForm implements java.io.Serializable {

    private String docBarCode = "";
    
    public String getDocBarCode() {
		return docBarCode;
	}


	public void setDocBarCode(String docBarCode) {
		this.docBarCode = docBarCode;
	}


	public void reset() {
           setCustomerName("");
           setLegalID("");
           setLeIDType("");
           setIdNO("");
           setDocBarCode("");
       }
	
	public String[][] getMapper() {

		String[][] input = { { "aCustomerSearchCriteria", "com.integrosys.cms.ui.custodian.CustodianSearchMapper" },
				{ "customerSearchCriteria", "com.integrosys.cms.ui.custodian.CustodianListMapper" },

				{ "customerList", "com.integrosys.cms.ui.custodian.CustodianListMapper" },

				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }

		};

		return input;
	}


}
