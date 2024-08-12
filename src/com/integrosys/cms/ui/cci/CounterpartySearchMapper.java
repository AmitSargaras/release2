package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;

import java.util.HashMap;

public class CounterpartySearchMapper extends AbstractCommonMapper {

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},});
    }

    public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {

        CounterpartySearchForm aForm = (CounterpartySearchForm) arg0;
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
        }

        String event = aForm.getEvent();

        if ("add".equals(event)) {
            String[] customers = aForm.getCustomerID();
            //Map map = new HashMap();
            //map.put("customers", customers);
            return customers;
        }

        return cSearch;
    }


    

    public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
        CounterpartySearchForm aForm = (CounterpartySearchForm) arg0;
        CounterpartySearchCriteria cSearch = (CounterpartySearchCriteria) arg1;
        if (aForm == null || cSearch == null) {
            return aForm;
        }
        aForm.setGroupCCINo(cSearch.getGroupCCINo());
        aForm.setCustomerName(cSearch.getCustomerName());
        aForm.setLegalID(cSearch.getLegalID());
        aForm.setLeIDType(cSearch.getLeIDType());
        aForm.setIdNO(cSearch.getIdNO());
         if (cSearch.getCustomerSeach()){
             aForm.setCustomerSeach("true");
            }
        return aForm;
    }

}
