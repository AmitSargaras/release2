package com.integrosys.cms.ui.custrelationship.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.ui.cci.CounterpartySearchMapper;

public class CRCustSearchMapper extends CounterpartySearchMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},});
    }
    
    public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {
        CRCustSearchForm aForm = (CRCustSearchForm) arg0;
        CounterpartySearchCriteria cSearch = new CounterpartySearchCriteria();
        if (aForm != null) {
            cSearch.setGroupCCINo(aForm.getGroupCCINo());
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setLegalID(aForm.getLegalID());
            cSearch.setLeIDType(aForm.getLeIDType());
            cSearch.setIdNO(aForm.getIdNO());
            if (aForm.getCustomerSeach() !=  null && aForm.getCustomerSeach().equals("true")){
               cSearch.setCustomerSeach(true) ;
            }
            
            String event = aForm.getEvent();
            
            if (event.equals(CRCustomerSearchAction.EVENT_CUST_ADD_SELECT) || 
            	event.equals(CRCustomerSearchAction.EVENT_CUST_ADD_SH_SELECT)) {
                String[] customers = aForm.getCustomerID();
                String[] selectCustomers = aForm.getSelectCustomerID();
                
                List selectedCustList = new ArrayList();
                
                if (selectCustomers!=null)
                	selectedCustList.add(selectCustomers);                	
                else
                	selectedCustList.add(customers);
                	
                
                return selectedCustList;
            }
        }
        return cSearch;
    }
}
