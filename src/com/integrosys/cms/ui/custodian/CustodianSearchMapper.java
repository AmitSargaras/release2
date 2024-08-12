package com.integrosys.cms.ui.custodian;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.ui.limit.facility.relationship.CustomerSearchMapper;

public class CustodianSearchMapper extends CustomerSearchMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},});
    }

    public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {

        CustodianSearchForm aForm = (CustodianSearchForm) arg0;
        CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
        if (aForm != null) {
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setLegalID(aForm.getLegalID());
            cSearch.setLeIDType(aForm.getLeIDType());
            cSearch.setIdNO(aForm.getIdNO());
            cSearch.setDocBarCode(aForm.getDocBarCode());
        }

        cSearch.setNItems(10);
		cSearch.setStartIndex(0);
		return cSearch;
    }

}
