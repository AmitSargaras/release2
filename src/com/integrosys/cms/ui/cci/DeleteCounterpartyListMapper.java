package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DeleteCounterpartyListMapper extends AbstractCommonMapper {

    public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {
        DefaultLogger.debug(this, "entering mapOBToForm(...).");
        CounterpartySearchForm form = (CounterpartySearchForm) aForm;
        String event = form.getEvent();
        DefaultLogger.debug(this, "event is " + event);
        if (CounterpartyCCIAction.EVENT_REMOVE.equals(event)) {
            form.setChkDeletes(new String[0]);
        }
        return form;
    }


    public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {
        DefaultLogger.debug(this, "entering mapFormToOB(...).");
        CounterpartySearchForm form = (CounterpartySearchForm) aForm;
        String event = form.getEvent();
        if (CounterpartyCCIAction.EVENT_REMOVE.equals(event)) {
            String[] chkDeletes = form.getChkDeletes();
            List returnList = new ArrayList();
            returnList.add(chkDeletes);
            return returnList;
        }
        return null;
    }


    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the mapFormToOB method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    
    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"offset", "java.lang.Integer", SERVICE_SCOPE},
                {"length", "java.lang.Integer", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}};
    }


    /**
     * Helper method to return true if integer is one of the array elements in
     * the integer array.
     *
     * @param target
     * @param arr
     * @return boolean
     */
    public static boolean inArray(int target, int[] arr) {

        if (arr == null) {
            return false;
        }

        for (int i = 0; i < arr.length; i++) {
            if (target == arr[i]) {
                return true;
            }
        }

        return false;
    }
}
