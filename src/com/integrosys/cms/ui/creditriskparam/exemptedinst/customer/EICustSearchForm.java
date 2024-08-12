package com.integrosys.cms.ui.creditriskparam.exemptedinst.customer;

import com.integrosys.cms.ui.cci.CounterpartySearchForm;

public class EICustSearchForm extends CounterpartySearchForm {

	private static final long serialVersionUID = 1L;

    private String[] customerID ;
    private String[] chkDeletes ;
    private String customerSearch = "";
   
	public String[] getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String[] customerID) {
        this.customerID = customerID;
    }

    public String[] getChkDeletes() {
        return chkDeletes;
    }

    public void setChkDeletes(String[] chkDeletes) {
        this.chkDeletes = chkDeletes;
    }
    
    public String[][] getMapper() {

        String[][] input = {
                { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
                { MAPPER, MAPPER },
				{ "aCustomerSearchCriteria", "com.integrosys.cms.ui.cci.CounterpartySearchMapper" },
				{ "customerSearchCriteria", "com.integrosys.cms.ui.cci.CounterpartySearchListMapper" },
				{ "customerList", "com.integrosys.cms.ui.customer.CustomerListMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }

        };

        return input;
    }
    
    public static final String MAPPER = "com.integrosys.cms.ui.creditriskparam.exemptedinst.customer.EICustSearchMapper";

	public String getCustomerSearch() {
		return customerSearch;
	}

	public void setCustomerSearch(String customerSearch) {
		this.customerSearch = customerSearch;
	}
}
