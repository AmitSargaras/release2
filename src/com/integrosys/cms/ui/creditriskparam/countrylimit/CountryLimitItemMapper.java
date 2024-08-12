/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimit;

import java.util.HashMap;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitItemMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"countryLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue", SERVICE_SCOPE},
        });
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapOBToForm(com.integrosys.base.uiinfra.common.CommonForm, java.lang.Object, java.util.HashMap)
      */

    public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs)
            throws MapperException {
        try {
            ICountryLimit item = (ICountryLimit) obj;
            CountryLimitItemForm itemForm = (CountryLimitItemForm) commonForm;

            itemForm.setCountry(item.getCountryCode());
            itemForm.setCountryRating(item.getCountryRatingCode());

            return itemForm;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

    /* (non-Javadoc)
      * @see com.integrosys.base.uiinfra.common.IMapper#mapFormToOB(com.integrosys.base.uiinfra.common.CommonForm, java.util.HashMap)
      */

    public Object mapFormToOB(CommonForm commonForm, HashMap inputs)
            throws MapperException {
        // TODO Auto-generated method stub
        try {
            CountryLimitItemForm itemForm = (CountryLimitItemForm) commonForm;
            ICountryLimit item = new OBCountryLimit();
            if (item != null) {
                item.setCountryCode(itemForm.getCountry());
                item.setCountryRatingCode(itemForm.getCountryRating());
                item.setStatus(ICMSConstant.STATE_ACTIVE);
            }
            return item;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }

}
