package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.ui.cci.CounterpartyCCIAction;

import java.util.HashMap;
import java.util.List;

public class CustGrpIdentifierSearchListMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public CustGrpIdentifierSearchListMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},});
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values
     * and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     * @throws com.integrosys.base.uiinfra.exception.MapperException
     *          on errors
     */

    public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
        DefaultLogger.debug(this, "Inside mapForm to ob ");
        List list = (List) inputs.get("CustGrpIdentifierSearchListMapper");

        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) cForm;
        String event = aForm.getEvent();

        if (list != null && !list.isEmpty()) {
//            System.out.println("List.size() = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                OBCustomerSearchResult col = (OBCustomerSearchResult) list.get(i);
//                System.out.println(" CustGrpIdentifierSearchListMapper >>>>>>>>> INSIDE MAPPER  = " + col.toString());
            }
        }


        if (!CounterpartyCCIAction.EVENT_PREPARE.equals(event)) {
            DefaultLogger.debug(this, aForm.getNumItems() + "");
            DefaultLogger.debug(this, aForm.getStartIndex() + "");
            CounterpartySearchCriteria cSearch = new CounterpartySearchCriteria();
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setAll(aForm.getAll());
            if ("2".equals(aForm.getGobutton())) {
                cSearch.setLegalID(aForm.getLegalID());
                cSearch.setLeIDType(aForm.getLeIDType());
            } else if ("3".equals(aForm.getGobutton())) {
                cSearch.setIdNO(aForm.getIdNO());
            } else if ("4".equals(aForm.getGobutton())) {
                cSearch.setGroupCCINo((aForm.getGrpID()));
            } else if ("5".equals(aForm.getGobutton())) {
                cSearch.setGroupCCINo((aForm.getGroupName()));
            } else {
                DefaultLogger.debug(this, "Empty criteria !");
            }
            DefaultLogger.debug(this, "PAGIIIING");

            String nItemsStr = PropertyManager.getValue("customer.pagination.nitems");
            int nItems = 20;
            if (null != nItemsStr) {
                try {
                    nItems = Integer.parseInt(nItemsStr);
                } catch (NumberFormatException e) {
                    nItems = 20;
                }
            }
            if (aForm.getNumItems() > 10) {
                cSearch.setNItems(aForm.getNumItems());
            } else {
                cSearch.setNItems(nItems);
            }

            cSearch.setStartIndex(aForm.getStartIndex());
            DefaultLogger.debug(this, "Going out of mapForm to ob ");
            DefaultLogger.debug(this, cSearch.getNItems() + "");
            DefaultLogger.debug(this, cSearch.getStartIndex() + "");
            return cSearch;
        } else {
            return null;
        }
    }

    /**
     * This method is used to map data from OB to the form and to return the
     * form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     * @throws com.integrosys.base.uiinfra.exception.MapperException
     *          on errors
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOb to form ");
        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) cForm;
        if (obj != null) {
            SearchResult sr = (SearchResult) obj;
            List v = (List) sr.getResultList();
//            System.out.println("Collection SearchResult = " + v.size());

            aForm.setCurrentIndex(sr.getCurrentIndex());
            aForm.setNumItems(sr.getNItems());
            // aForm.setStartIndex(sr.getStartIndex());
            DefaultLogger.debug(this, "Before putting list result");
            aForm.setSearchResult(sr.getResultList());
        } else {
//            System.out.println("obj  is null");
            aForm.setSearchResult(null);
            aForm.setCurrentIndex(0);
            aForm.setStartIndex(0);
            aForm.setNumItems(0);
        }
        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }

}
