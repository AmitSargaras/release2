/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;

import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.common.UIUtil;

import java.util.*;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author:  $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */
public class ExemptFacilityMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public ExemptFacilityMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},
                {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
                {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            }
         );

    }
    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "******************** Inside Map Form to OB ");
        String event = (String)map.get(ICommonEventConstant.EVENT);
        Locale locale = (Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        DefaultLogger.debug(this, "map="+map);
		DefaultLogger.debug(this, "cForm="+cForm);

        IExemptFacilityGroupTrxValue trxVal = (IExemptFacilityGroupTrxValue)map.get("exemptFacilityTrxValue");
        DefaultLogger.debug(this,"trxVal  = $$$$$$$$ "+ trxVal);
        ExemptFacilityForm aForm = (ExemptFacilityForm) cForm;
        
        if (ExemptFacilityAction.EVENT_VIEW.equals(event)) {

            OBExemptFacility obExemptFacility = new OBExemptFacility();

            DefaultLogger.debug(this, "ExemptFacilitymapper.java ....after obExemptFacility.set");
            DefaultLogger.debug(this, "obExemptFacility="+obExemptFacility);

            return obExemptFacility;

        }
        else if(ExemptFacilityAction.EV_ADD.equals(event) || ExemptFacilityAction.EV_EDIT.equals(event)){
            OBExemptFacility obExemptFacility = new OBExemptFacility();
            obExemptFacility.setFacilityCode(aForm.getFacilityCode());
            if (aForm.getFacilityStatusConditionalPerc() != null && !aForm.getFacilityStatusConditionalPerc().trim().equals("")){
                try {
                    obExemptFacility.setFacilityStatusConditionalPerc(MapperUtil.mapStringToDouble(aForm.getFacilityStatusConditionalPerc(),locale));
                } catch (Exception e) {
                    throw new MapperException(e.toString());
                }
            }
            obExemptFacility.setFacilityStatusExempted(aForm.getFacilityStatusExempted());
            //obExemptFacility.setRemarks(aForm.getRemarks());
            return obExemptFacility;
        }
        
      
				
        return null;

    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        try {
            DefaultLogger.debug(this, "******************** inside mapOb to form");
            ExemptFacilityForm aForm = (ExemptFacilityForm) cForm;
			DefaultLogger.debug(this, "obj="+obj);
            String event = (String)map.get(ICommonEventConstant.EVENT);
            Locale locale = (Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

            OBExemptFacility fac = (OBExemptFacility)obj;
            if (ExemptFacilityAction.EVENT_VIEW.equals(event) ||
                    ExemptFacilityAction.EV_PREPARE_EDIT.equals(event)) {
                aForm.setExemptFacilityID(fac.getExemptFacilityID());
                aForm.setFacilityCode(fac.getFacilityCode());
                try{
                    aForm.setFacilityStatusConditionalPerc(MapperUtil.mapDoubleToString(fac.getFacilityStatusConditionalPerc(),2,locale));
                } catch (Exception e) {
                    throw new MapperException(e.toString());
                }
                aForm.setFacilityStatusExempted(fac.getFacilityStatusExempted());
                //aForm.setRemarks(fac.getRemarks());

            }
            DefaultLogger.debug(this, "Going out of mapOb to form ");
            return aForm;
        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.error(this, "error in ExemptFacilityMapper is" + e);
        }
        return null;
    }
}

