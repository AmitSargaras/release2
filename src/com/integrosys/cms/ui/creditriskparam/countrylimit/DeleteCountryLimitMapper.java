package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 9, 2010
 * Time: 11:32:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteCountryLimitMapper extends AbstractCommonMapper {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap hashMap) throws MapperException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
        try {
            CountryLimitForm countryLimitForm = (CountryLimitForm) commonForm;

            if (countryLimitForm.getDeletedItemList() != null)
                return new ArrayList(Arrays.asList(countryLimitForm.getDeletedItemList()));
        }
        catch (Exception ex) {
            logger.error("Mapper exception in DeleteCountryLimitMapper", ex);
        }
        return null;
    }

}
