package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;

import java.util.HashMap;
import java.util.Locale;


public class CCICounterpartyDetailsMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */

    public CCICounterpartyDetailsMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {"prev_event", "java.lang.String", REQUEST_SCOPE},
        });
    }


    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "Inside Map Form to OB ");
        CounterpartySearchForm aForm = (CounterpartySearchForm) cForm;
        String event = aForm.getEvent();
        ICCICounterpartyDetails temp = (ICCICounterpartyDetails) map.get("ICCICounterpartyDetails");
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        DefaultLogger.debug(this, "Going out of  Map Form to OB ");
        return temp;
    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOb to form ");
        CounterpartySearchForm aForm = (CounterpartySearchForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        ICCICounterpartyDetailsTrxValue temp = (ICCICounterpartyDetailsTrxValue) map.get("ICCICounterpartyDetailsTrxValue");

        if (obj != null) {
            ICCICounterpartyDetails details = (ICCICounterpartyDetails) obj;
            aForm.setICCICounterpartyDetails(details);
            aForm.setGroupCCINo(details.getGroupCCINo()+"");
        }
        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }





}
