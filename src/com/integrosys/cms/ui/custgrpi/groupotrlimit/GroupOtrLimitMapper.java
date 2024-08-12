package com.integrosys.cms.ui.custgrpi.groupotrlimit;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.OBGroupOtrLimit;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: July 1, 2008
 */
public class GroupOtrLimitMapper extends AbstractCommonMapper {

    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "Inside mapFormToOB ");
        GroupOtrLimitForm aForm = (GroupOtrLimitForm) cForm;
        String event = aForm.getEvent();
        IGroupOtrLimit obj = (IGroupOtrLimit) map.get(CustGroupUIHelper.form_groupOtrLimitObj);
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        if (obj != null) {
            debug("mapFormToOB....IGroupOtrLimit is not null");
        } else {
            obj = new OBGroupOtrLimit();
            debug("mapFormToOB....IGroupOtrLimitis null");
        }

        try {
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupOtrLimitIDRef())) {
                obj.setGroupOtrLimitIDRef(Long.valueOf(aForm.getGroupOtrLimitIDRef()).longValue());
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupOtrLimitID())) {
                //obj.setGroupSubLimitID(Long.valueOf(aForm.getGroupSubLimitID()).longValue());
            }

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGrpID())) {
                obj.setGrpID(Long.valueOf(aForm.getGrpID()).longValue());
            }

            obj.setCurrencyCD(aForm.getCurrencyCD());

            //TODO TO CHECK WITH the currency
            try {
                if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLimitAmt())
                        && !AbstractCommonMapper.isEmptyOrNull(aForm.getCurrencyCD())) {
                    obj.setLimitAmt(CurrencyManager.convertToAmount(locale, aForm.getCurrencyCD(), aForm.getLimitAmt()));
                } else {
                    obj.setLimitAmt(null);
                }
            } catch (Exception e) {} 


            obj.setOtrLimitTypeCD(aForm.getOtrLimitTypeCD());
            obj.setLastReviewedDt(UIUtil.mapFormString_OBDate(locale, obj.getLastReviewedDt(), aForm.getLastReviewedDt()));

            obj.setDescription(aForm.getDesc());
            obj.setRemarks(aForm.getRemarks());

            if (CustGroupUIHelper.DELETED.equals(aForm.getStatus())) {
                obj.setStatus(CustGroupUIHelper.DELETED);
            } else {
                obj.setStatus(CustGroupUIHelper.ACTIVE);
            }

            debug("obj.getTypeCD(); = " + obj.getOtrLimitTypeCD());

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupOtrLimitMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        DefaultLogger.debug(this, "Going out of  mapFormToOB \n" + ((OBGroupOtrLimit) obj).toString() + "\n");
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
        GroupOtrLimitForm aForm = (GroupOtrLimitForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        IGroupOtrLimit iObj = (IGroupOtrLimit) obj;
        if (iObj == null) {
            debug("(IGroupOtrLimit) obj iS NULL");
        } else {
            debug("(IGroupOtrLimit) obj iS not  NULL");
        }

        try {
            aForm.setGroupOtrLimitIDRef(String.valueOf(iObj.getGroupOtrLimitIDRef()));
            //aForm.setGroupSubLimitID(String.valueOf(iObj.getGroupSubLimitID()));
            aForm.setGrpID(String.valueOf(iObj.getGrpID()));
            aForm.setCurrencyCD(iObj.getCurrencyCD());
            if (iObj.getLimitAmt() != null &&
                    iObj.getCurrencyCD() != null &&
                    iObj.getLimitAmt().getCurrencyCode() != null &&
                    iObj.getLimitAmt().getAmount() > 0) {
                aForm.setLimitAmt(UIUtil.mapAmountToString(locale, iObj.getLimitAmt()));
            }

            aForm.setOtrLimitTypeCD(iObj.getOtrLimitTypeCD());
            aForm.setLastReviewedDt(UIUtil.mapOBDate_FormString(locale, iObj.getLastReviewedDt()));
            aForm.setDesc(iObj.getDescription());
            aForm.setRemarks(iObj.getRemarks());
            if (CustGroupUIHelper.DELETED.equals(iObj.getStatus())) {
                aForm.setStatus(CustGroupUIHelper.DELETED);
            } else {
                aForm.setStatus(CustGroupUIHelper.ACTIVE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " GroupOtrLimitMapper", "error is :" + e.toString());
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
        DefaultLogger.debug(this, "GroupOtrLimitMapper,  " + msg);
    }

}
