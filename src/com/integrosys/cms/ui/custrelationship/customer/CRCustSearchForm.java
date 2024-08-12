package com.integrosys.cms.ui.custrelationship.customer;

import com.integrosys.cms.ui.cci.CounterpartySearchForm;


public class CRCustSearchForm extends CounterpartySearchForm {

	private static final long serialVersionUID = 1L;

    public String[][] getMapper() {

        String[][] input = {
                { "DeleteCounterpartyListMapper", "com.integrosys.cms.ui.cci.DeleteCounterpartyListMapper" },
//                { "aCounterpartySearchMapper", "com.integrosys.cms.ui.cci.CounterpartySearchMapper" },
                { "counterpartySearchCriteria", "com.integrosys.cms.ui.cci.CounterpartySearchMapper" },
//                { "counterpartySearchCriteria", "com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria" },
                { "counterpartyList", "com.integrosys.cms.ui.cci.CounterpartySearchListMapper" },
                { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
                { MAPPER, MAPPER },
                {"ICCICounterpartyDetails", "com.integrosys.cms.ui.cci.CCICounterpartyDetailsMapper"},

        };

        return input;
    }
    
    public static final String MAPPER = "com.integrosys.cms.ui.custrelationship.customer.CRCustSearchMapper";
}
