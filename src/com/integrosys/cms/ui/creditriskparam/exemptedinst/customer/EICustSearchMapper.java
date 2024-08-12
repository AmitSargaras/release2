package com.integrosys.cms.ui.creditriskparam.exemptedinst.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.ui.customer.CustomerSearchMapper;

public class EICustSearchMapper extends CustomerSearchMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},});
    }
    
    public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {
        EICustSearchForm aForm = (EICustSearchForm) arg0;
        CounterpartySearchCriteria cSearch = new CounterpartySearchCriteria();
        
		if (aForm != null) {
			cSearch.setCustomerName(aForm.getCustomerName());
			cSearch.setLegalID(aForm.getLegalID());
			cSearch.setLeIDType(aForm.getLeIDType());
			cSearch.setIdNO(aForm.getIdNO());
		}
		
        String event = aForm.getEvent();
        
        if (event.equals(EICustomerSearchAction.EVENT_CUST_ADD_SELECT)) {
            String[] customers = aForm.getCustomerID();
            List selectedCustList = new ArrayList();
            selectedCustList.add(customers);
            return selectedCustList;
        }
        
		return cSearch;
		
    }
    
	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2)
	throws MapperException {
		EICustSearchForm aForm = (EICustSearchForm) arg0;
		CounterpartySearchCriteria cSearch = (CounterpartySearchCriteria) arg1;
		if (aForm == null || cSearch == null) {
			return aForm;
		}
		aForm.setCustomerName(cSearch.getCustomerName());
		aForm.setLegalID(cSearch.getLegalID());
		aForm.setLeIDType(cSearch.getLeIDType());
		aForm.setIdNO(cSearch.getIdNO());
		return aForm;
	}
}
