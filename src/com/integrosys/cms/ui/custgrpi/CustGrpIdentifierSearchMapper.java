package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;

import java.util.HashMap;

public class CustGrpIdentifierSearchMapper extends AbstractCommonMapper {

    public Object mapFormToOB(CommonForm arg0, HashMap arg1)
            throws MapperException {
        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) arg0;
        CustGrpIdentifierSearchCriteria cSearch = new CustGrpIdentifierSearchCriteria();
        if (aForm != null) {
            cSearch.setGrpNo(aForm.getGrpNo() != null ? aForm.getGrpNo().trim() : aForm.getGrpNo());
            cSearch.setGrpID(aForm.getGrpID() != null ? aForm.getGrpID().trim() : aForm.getGrpID());
            cSearch.setGroupName(aForm.getGroupName());
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setLegalID(aForm.getLegalID());
            cSearch.setLeIDType(aForm.getLeIDType());
            cSearch.setIdNO(aForm.getIdNO());
            cSearch.setSearchType(aForm.getSearchType());
            if (aForm.getCustomerSeach() != null && aForm.getCustomerSeach().equals("true")) {
                cSearch.setCustomerSeach(true);
            }
        }
        return cSearch;
    }

    public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2)
            throws MapperException {
        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) arg0;
        CustGrpIdentifierSearchCriteria cSearch = (CustGrpIdentifierSearchCriteria) arg1;
        if (aForm == null || cSearch == null) {
            return aForm;
        }
        aForm.setGrpNo(cSearch.getGrpNo());
        aForm.setGrpID(cSearch.getGrpID());
        aForm.setGroupName(cSearch.getGroupName());
        aForm.setCustomerName(cSearch.getCustomerName());
        aForm.setLegalID(cSearch.getLegalID());
        aForm.setLeIDType(cSearch.getLeIDType());
        aForm.setIdNO(cSearch.getIdNO());
        aForm.setSearchType(cSearch.getSearchType());

        if (cSearch.getCustomerSeach()) {
            aForm.setCustomerSeach("true");
        } else {
            aForm.setCustomerSeach("false");
        }
        return aForm;
    }

}
