package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;

import java.util.HashMap;
import java.util.List;

public class GroupMemberSearchListMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public GroupMemberSearchListMapper() {
        DefaultLogger.debug(this, "Inside constructor");
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
        List list = (List) inputs.get("GroupMemberSearchListMapper");

        GroupMemberForm aForm = (GroupMemberForm) cForm;
        String event = aForm.getEvent();
        DefaultLogger.debug(this,"GroupMemberSearchListMapper event = " + event);
        if (list != null && !list.isEmpty()) {
            //System.out.println("List.size() = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                OBCustomerSearchResult col = (OBCustomerSearchResult) list.get(i);
                //System.out.println(" CounterpartyListMapper >>>>>>>>> INSIDE MAPPER  = " + col.toString() );
            }
        }


        if (!EVENT_PREPARE.equals(event)) {

            DefaultLogger.debug(this, aForm.getNumItems() + "");
            DefaultLogger.debug(this, aForm.getStartIndex() + "");

            GroupMemberSearchCriteria cSearch = new GroupMemberSearchCriteria();
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setAll(aForm.getAll());

            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setLegalID(aForm.getLegalID());
            cSearch.setLeIDType(aForm.getLeIDType());
            cSearch.setIdNO(aForm.getIdNO());
            if (aForm.getCustomerSeach() !=  null && aForm.getCustomerSeach().equals("true")){
               cSearch.setCustomerSeach(true) ;
            }
//            if ("1".equals(aForm.getGobutton())) {
//                cSearch.setLegalID(aForm.getLegalID());
//                cSearch.setIdNO(aForm.getIdNO());
//                cSearch.setSourceType(aForm.getSourceType());
//            } else if ("2".equals(aForm.getGobutton())) {
//                cSearch.setLegalID(aForm.getLegalID());
//                cSearch.setSourceType(aForm.getSourceType());
//            } else if ("3".equals(aForm.getGobutton())) {
//                cSearch.setIdNO(aForm.getIdNO());
//            } else if ("4".equals(aForm.getGobutton())) {
//            } else {
//                DefaultLogger.debug(this, "Empty criteria !");
//            }
            DefaultLogger.debug(this, "PAGIIIING");

            String nItemsStr = PropertyManager .getValue("customer.pagination.nitems");
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
        DefaultLogger.debug(this, "inside mapOb to form  FOR  putting list result ");
        GroupMemberForm aForm = (GroupMemberForm) cForm;
        if (obj != null) {
            SearchResult sr = (SearchResult) obj;
            aForm.setCurrentIndex(sr.getCurrentIndex());
            aForm.setNumItems(sr.getNItems());
            DefaultLogger.debug(this, "Before putting list result");
            aForm.setSearchResult(sr.getResultList());
        } else {
            aForm.setSearchResult(null);
            aForm.setCurrentIndex(0);
            aForm.setStartIndex(0);
            aForm.setNumItems(0);
        }
        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }

}
