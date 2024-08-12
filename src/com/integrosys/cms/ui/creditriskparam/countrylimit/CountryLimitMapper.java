/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.*;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitMapper extends AbstractCommonMapper {

    private CountryLimitUIHelper helper = new CountryLimitUIHelper();
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
      */

    public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
            throws MapperException {
        try {
            String event = (String) (inputs.get("event"));
            ICountryLimitTrxValue countryLimitTrxObj = (ICountryLimitTrxValue) (inputs.get("countryLimitTrxObj"));

            ICountryLimitParam ctryLimitParam = (ICountryLimitParam) obj;
            CountryLimitForm ctryLimitForm = (CountryLimitForm) commonForm;

            if (!EventConstant.EVENT_PROCESS.equals(event) &&
                    !EventConstant.EVENT_PROCESS_RETURN.equals(event)) {
                if (ctryLimitParam.getCountryLimitList() != null) {
                    ctryLimitForm.setCountryLimitItemList(helper.getSortedCountryLimitMap(Arrays.asList(ctryLimitParam.getCountryLimitList())));
                } else {
                    ctryLimitForm.setCountryLimitItemList(new ArrayList());
                }

                if (ctryLimitParam.getCountryRatingList() != null) {
                    ctryLimitForm.setCountryRatingList(Arrays.asList(ctryLimitParam.getCountryRatingList()));
                } else {
                    ctryLimitForm.setCountryRatingList(new ArrayList());
                }

            } else {
                renderCompareItem(countryLimitTrxObj.getCountryLimitParam(),
                        countryLimitTrxObj.getStagingCountryLimitParam(),
                        ctryLimitForm);
            }

            ctryLimitForm.setDeletedItemList(new String[0]);

            return ctryLimitForm;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

    private void renderCompareItem(ICountryLimitParam origLimitParam, ICountryLimitParam stagingLimitParam, CountryLimitForm ctryLimitForm) throws Exception {
        ICountryLimit[] oldLmtRef = null;
        ICountryLimit[] newLmtRef = null;

        if (origLimitParam != null) {
            oldLmtRef = (ICountryLimit[]) helper.getSortedCountryLimitMap(Arrays.asList(origLimitParam.getCountryLimitList())).toArray(new OBCountryLimit[0]);
        }
        if (stagingLimitParam != null) {
            newLmtRef = (ICountryLimit[]) helper.getSortedCountryLimitMap(Arrays.asList(stagingLimitParam.getCountryLimitList())).toArray(new OBCountryLimit[0]);
        }
        if (oldLmtRef == null) {
            oldLmtRef = new ICountryLimit[0];
        }

        if (newLmtRef == null) {
            newLmtRef = new ICountryLimit[0];
        }

        List compareList = CompareOBUtil.compOBArray(newLmtRef, oldLmtRef);

        ctryLimitForm.setCountryLimitItemList(compareList);

        ICountryRating[] oldRatingRef = null;
        ICountryRating[] newRatingRef = null;
        if (origLimitParam != null) {
            oldRatingRef = origLimitParam.getCountryRatingList();
        }
        if (stagingLimitParam != null) {
            newRatingRef = stagingLimitParam.getCountryRatingList();
        }
        if (oldRatingRef == null) {
            oldRatingRef = new ICountryRating[0];
        }

        if (newRatingRef == null) {
            newRatingRef = new ICountryRating[0];
        }

        List compareRatingList = CompareOBUtil.compOBArray(newRatingRef, oldRatingRef);

        ctryLimitForm.setCountryRatingList(compareRatingList);
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
      */

    public Object mapFormToOB(CommonForm commonForm, HashMap inputs)
            throws MapperException {
        // TODO Auto-generated method stub
        String event = (String) (inputs.get("event"));

        try {
            ICountryLimitParam ctryLimitParam = null;
            if (EventConstant.EVENT_CREATE.equals(event)) {
                ctryLimitParam = new OBCountryLimitParam();
            } else {
                ICountryLimitTrxValue trxValue = (ICountryLimitTrxValue) (inputs.get("countryLimitTrxObj"));
                ctryLimitParam = trxValue.getStagingCountryLimitParam();
            }
            if (ctryLimitParam == null) {
                ctryLimitParam = new OBCountryLimitParam();
            }
            return ctryLimitParam;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }
}
