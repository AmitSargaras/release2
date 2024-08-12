package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.OBGroupMember;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.common.UIUtil;

import java.util.HashMap;
import java.util.Locale;

public class GroupMemberMapper extends AbstractCommonMapper {


    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "Inside mapFormToOB ");
        GroupMemberForm aForm = (GroupMemberForm) cForm;
        String event = aForm.getEvent();
        IGroupMember obj = (IGroupMember) map.get(CustGroupUIHelper.form_groupmemberObj);
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        if (obj != null) {
            Debug("mapFormToOB....IGroupSubLimit is not null");
        } else {
            obj = new OBGroupMember();
            Debug("mapFormToOB....IGroupSubLimitis null");
        }

        try {
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupMemberID())) {
                obj.setGroupMemberID(Long.valueOf(aForm.getGroupMemberID()).longValue());
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupMemberIDRef())) {
                obj.setGroupMemberIDRef(Long.valueOf(aForm.getGroupMemberIDRef()).longValue());
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGrpID())) {
                obj.setGrpID(Long.valueOf(aForm.getGrpID()).longValue());
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEntityID())) {
                obj.setEntityID(Long.valueOf(aForm.getEntityID()).longValue());
            }

            obj.setEntityType(aForm.getEntityType());

            obj.setRelationName(aForm.getRelationName());
            obj.setPercentOwned(UIUtil.mapFormString_OBDouble(aForm.getRelationValue()));

            obj.setMembersCreditRating(aForm.getMembersCreditRating());
            obj.setEntityLmt(aForm.getEntityLmt());

            if (CustGroupUIHelper.DELETED.equals(aForm.getStatus())) {
                obj.setStatus(CustGroupUIHelper.DELETED);
            } else {
                obj.setStatus(CustGroupUIHelper.ACTIVE);
            }


        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupMemberMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        Debug("Going out of  mapFormToOB \n" + ((OBGroupMember) obj).toString() + "\n");

        DefaultLogger.debug(this, "Going out of map  to OB");
        return obj;
    }


    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object iobj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOBToForm ");
        GroupMemberForm aForm = (GroupMemberForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        IGroupMember obj = (IGroupMember) iobj;
        Debug(" mapOBToForm");
        if (obj == null) {
            obj = new OBGroupMember();
            Debug("(IGroupMember) obj iS NULL");
        } else {
            Debug("(IGroupMember) obj iS not  NULL");
        }

        try {
            aForm.setGroupMemberID(String.valueOf(obj.getGroupMemberID()));
            aForm.setGroupMemberIDRef(String.valueOf(obj.getGroupMemberIDRef()));
            aForm.setGrpID(String.valueOf(obj.getGrpID()));

            aForm.setEntityID(String.valueOf(obj.getEntityID()));
            aForm.setEntityName(obj.getEntityName());
            aForm.setEntityType(obj.getEntityType());
            aForm.setLmpLeID(obj.getLmpLeID());
            aForm.setIdNO(obj.getIdNO());

            aForm.setSourceID(obj.getSourceID());

            aForm.setRelBorMemberName(obj.getRelBorMemberName());

            aForm.setRelationName(obj.getRelationName());
            aForm.setRelationValue(obj.getPercentOwned() + "");

            aForm.setMembersCreditRating(obj.getMembersCreditRating());
            aForm.setEntityLmt(obj.getEntityLmt());

            if (CustGroupUIHelper.DELETED.equals(obj.getStatus())) {
                aForm.setStatus(CustGroupUIHelper.DELETED);
            } else {
                aForm.setStatus(CustGroupUIHelper.ACTIVE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupMemberMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        Debug("Going out of  mapOBToForm \n " + aForm.toString() + "\n");

        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
        });
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"GroupMemberMapper,  " + msg);
    }

}
