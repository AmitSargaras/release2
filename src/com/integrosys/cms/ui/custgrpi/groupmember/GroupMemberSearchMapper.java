package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;

import java.util.HashMap;

public class GroupMemberSearchMapper extends AbstractCommonMapper {


    public Object mapFormToOB(CommonForm arg0, HashMap arg1) throws MapperException {

        DefaultLogger.debug(this, "Inside mapFormToOB to ob ");
        GroupMemberForm aForm = (GroupMemberForm) arg0;
        GroupMemberSearchCriteria cSearch = new GroupMemberSearchCriteria();
        if (aForm != null) {

            cSearch.setSearchType(aForm.getSearchType());
            cSearch.setGrpNo(aForm.getSearchGroupNo());
            cSearch.setGrpID(aForm.getSearchGroupID());
            cSearch.setGroupName(aForm.getSearchGroupName());

            cSearch.setAll(aForm.getAll());
            cSearch.setCustomerName(aForm.getCustomerName());
            cSearch.setLegalID(aForm.getLegalID());
            cSearch.setSourceType(aForm.getSourceType());
            cSearch.setLeIDType(aForm.getLeIDType());
            cSearch.setIdNO(aForm.getIdNO());
            if (aForm.getCustomerSeach() != null && aForm.getCustomerSeach().equals("true")) {
                cSearch.setCustomerSeach(true);
            }
        }
        DefaultLogger.debug(this, "Going out of mapFormToOB to GroupMemberSearchCriteria " + cSearch.toString());
        return cSearch;
    }


    public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
        DefaultLogger.debug(this, "inside mapOBToForm to form ");
        GroupMemberForm aForm = (GroupMemberForm) arg0;
        GroupMemberSearchCriteria cSearch = (GroupMemberSearchCriteria) arg1;
        if (aForm == null || cSearch == null) {
            return aForm;
        }


         aForm.setSearchType(cSearch.getSearchType());

         aForm.setSearchGroupNo(cSearch.getGrpNo());
         aForm.setSearchGroupID(cSearch.getGrpID());
         aForm.setSearchGroupName(cSearch.getGroupName());


        aForm.setCustomerName(cSearch.getCustomerName());
        aForm.setLegalID(cSearch.getLegalID());
        aForm.setSourceType(cSearch.getSourceType());
        aForm.setLeIDType(cSearch.getLeIDType());
        aForm.setIdNO(cSearch.getIdNO());
        if (cSearch.getCustomerSeach()) {
            aForm.setCustomerSeach("true");
        }
        DefaultLogger.debug(this, "Going out of mapOBToForm to GroupMemberForm " + aForm.toString());
        return aForm;
    }

}
