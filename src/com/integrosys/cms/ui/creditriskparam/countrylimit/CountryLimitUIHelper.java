/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimit;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryRating;
//import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.CountryLimitProxyFactory;
//import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.countrylimit.ICountryLimitTrxValue;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitUIHelper {

//    public ICountryLimitProxy getCountryLimitProxyManager() {
//        ICountryLimitProxy proxy = CountryLimitProxyFactory.getProxy();
//        return proxy;
//    }

    public ICountryLimit getCurWorkingCountryLimitItem(String fromEvent, long index, ICountryLimitTrxValue trxValue) {
        ICountryLimitParam template = null;
        if (EventConstant.EVENT_READ.equals(fromEvent)) {
            template = trxValue.getCountryLimitParam();
        } else {
            template = trxValue.getStagingCountryLimitParam();
        }
        if (template != null) {
            for (int i = 0; i < template.getCountryLimitList().length; i++) {
                ICountryLimit countryLimit = template.getCountryLimitList()[i];
                if (countryLimit.getCountryLimitID() == index) {
                    return countryLimit;
                }
            }
        }
        return null;
    }

    public ICountryRating[] getCurWorkingCountryRating(String fromEvent, ICountryLimitTrxValue trxValue) {
        ICountryLimitParam template = null;
        if (EventConstant.EVENT_READ.equals(fromEvent)) {
            template = trxValue.getCountryLimitParam();
        } else {
            template = trxValue.getStagingCountryLimitParam();
        }
        if (template != null) {
            ICountryRating[] item = template.getCountryRatingList();
            return item;
        }
        return null;
    }

    public String getCountryDesc(String code) {
        return CountryList.getInstance().getCountryName(code);
    }

    public String getRatingDesc(String code) {
        return CommonDataSingleton.getCodeCategoryLabelByValue(ICategoryEntryConstant.RATING_CAT_CODE, code);
    }

    public List getCountryList() {
        List lbValList = new ArrayList();
        List idList = (List) (CountryList.getInstance().getCountryValues());
        List valList = (List) (CountryList.getInstance().getCountryLabels());
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i).toString();
            String val = valList.get(i).toString();

            LabelValueBean lvBean = new LabelValueBean(val, id);
            lbValList.add(lvBean);

        }
        return CommonUtil.sortDropdown(lbValList);
    }

    public List getRatingList() {
        List lbValList = new ArrayList();
        HashMap map = new HashMap();

        map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICategoryEntryConstant.RATING_CAT_CODE, ICategoryEntryConstant.RATING_MOODY_ENTRY_CODE);

        Object[] keyArr = map.keySet().toArray();
        for (int i = 0; i < keyArr.length; i++) {
            Object nextKey = keyArr[i];
            LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
            lbValList.add(lvBean);
        }
        return CommonUtil.sortDropdown(lbValList);
    }

    public List getSortedCountryLimitMap(List countryLimit) {
        Map countryMap = new HashMap();

        for (int i = 0; i < countryLimit.size(); i++) {
            if (countryLimit.get(i) instanceof CompareResult) {
                CompareResult item = (CompareResult) countryLimit.get(i);
                countryMap.put(item, getCountryDesc(((ICountryLimit) item.getObj()).getCountryCode()));
            } else {
                ICountryLimit item = (ICountryLimit) countryLimit.get(i);
                countryMap.put(item, getCountryDesc(item.getCountryCode()));
            }
        }

        List mapKeys = new ArrayList(countryMap.keySet());
        List mapValues = new ArrayList(countryMap.values());
        String[] sortArray = (String[]) mapValues.toArray(new String[]{});
        countryMap.clear();
        Arrays.sort(sortArray);
        List sortList = new ArrayList();

        for (int j = 0; j < sortArray.length; j++) {
            sortList.add(mapKeys.get(mapValues.indexOf(sortArray[j])));
        }

        return sortList;
    }
}
