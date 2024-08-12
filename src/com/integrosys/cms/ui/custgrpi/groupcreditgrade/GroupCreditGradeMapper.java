package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.OBGroupCreditGrade;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.custgrpi.groupcreditgrade.GroupCreditGradeForm;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

import java.util.HashMap;
import java.util.Locale;

public class GroupCreditGradeMapper extends AbstractCommonMapper {


    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "Inside mapFormToOB ");
        GroupCreditGradeForm aForm = (GroupCreditGradeForm) cForm;
        String event = aForm.getEvent();
        IGroupCreditGrade obj = (IGroupCreditGrade) map.get(CustGroupUIHelper.form_groupCreditGradeObj);
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        if (obj != null) {
            debug("mapFormToOB....ICustGrpIdentifier is not null");
        } else {
            obj = new OBGroupCreditGrade();
            debug("mapFormToOB....ICustGrpIdentifier is null");
        }

        try {
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupCreditGradeIDRef())) {
                obj.setGroupCreditGradeIDRef(Long.valueOf(aForm.getGroupCreditGradeIDRef()).longValue());
            }
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupCreditGradeID())) {
                obj.setGroupCreditGradeID(Long.valueOf(aForm.getGroupCreditGradeID()).longValue());
            }

            obj.setRatingCD(aForm.getRatingCD());
            obj.setTypeCD(aForm.getTypeCD());
            obj.setRatingDt(UIUtil.mapFormString_OBDate(locale, obj.getRatingDt(), aForm.getRatingDt()));
            obj.setExpectedTrendRating(aForm.getExpectedTrendRating());
            obj.setReason(aForm.getReason());

            if (CustGroupUIHelper.DELETED.equals(aForm.getStatus())) {
                obj.setStatus(CustGroupUIHelper.DELETED);
            } else {
                obj.setStatus(CustGroupUIHelper.ACTIVE);
            }

            debug("obj.getTypeCD(); = " + obj.getTypeCD());

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupCreditGradeMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        DefaultLogger.debug(this, "Going out of  mapFormToOB \n" + ((OBGroupCreditGrade) obj).toString() + "\n");
        return obj;
    }


    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOBToForm ");
        GroupCreditGradeForm aForm = (GroupCreditGradeForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        IGroupCreditGrade iObj = (IGroupCreditGrade) obj;
//        System.out.println(">>>>>>>>>>>>Jitu GroupCreditGradeMapper mapOBToForm");
        if (iObj == null) {
            debug("(IGroupCreditGrade) obj iS NULL");
        } else {

        }
        debug("(IGroupCreditGrade) obj iS not  NULL");

        try {
            aForm.setGroupCreditGradeID(String.valueOf(iObj.getGroupCreditGradeID()));
            aForm.setGroupCreditGradeIDRef(String.valueOf(iObj.getGroupCreditGradeIDRef()));
            aForm.setRatingCD(iObj.getRatingCD());
            aForm.setTypeCD(iObj.getTypeCD());
            aForm.setRatingDt(UIUtil.mapOBDate_FormString(locale, iObj.getRatingDt()));
            aForm.setExpectedTrendRating(iObj.getExpectedTrendRating());
            if ("true".equals(iObj.getStatus())) {
                aForm.setStatus("true");
            } else {
                aForm.setStatus("false");
            }

            aForm.setReason(iObj.getReason());

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupCreditGradeMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        DefaultLogger.debug(this, "Going out of  mapOBToForm \n " + aForm.toString() + "\n");

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


    private void debug(String msg) {
    	DefaultLogger.debug(this,"GroupCreditGradeMapper,  " + msg);
    }

}
