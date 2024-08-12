package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.OBCustomerAddress;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.CountryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Jan 11, 2008
 * Time: 1:21:48 PM
 * To change this template use File | Settings | File Templates.
 */


public class CounterpartyUIHelper {

    public CounterpartyUIHelper() {
    }


    public static boolean isExist(ICCICounterparty[]  actual, long subProfileID) {
        boolean check = false;
        List list = new ArrayList();
        if (subProfileID == ICMSConstant.LONG_INVALID_VALUE){
            check = false;
        } else if (actual == null || actual.length == 0){
            check = false;
        } else {
            for (int index = 0; index < actual.length; index++) {
                ICCICounterparty OB = actual[index];
                list.add(OB.getSubProfileID() + "");
            }
        }
        if (list != null && !list.isEmpty()){
            if (list.contains(subProfileID + "")){
                check = true;
            }
        }
        return check;
    }


    public static String displayAddress(OBCustomerAddress address) {
        if (address == null) return "";
        String returnAddr = "";

        if (isNotEmpty(address.getAddressLine1())){
            returnAddr = returnAddr + address.getAddressLine1() + ",";
        }
        if (isNotEmpty(address.getAddressLine2())){
            returnAddr = returnAddr + address.getAddressLine2() + ",";
        }
        if (isNotEmpty(address.getCity())){
            returnAddr = returnAddr + address.getCity() + ",";
        }
        if (isNotEmpty(address.getState())){
            returnAddr = returnAddr + address.getState() + ",";
        }
        if (isNotEmpty(address.getCountryCode())){
            returnAddr = returnAddr + getCountryDesc(address.getCountryCode()) + ",";
        }
        return returnAddr;
    }

    private static String getCountryDesc(String code) {
        if (AbstractCommonMapper.isEmptyOrNull(code)){
            return "";
        } else {
            return CountryList.getInstance().getCountryName(code);
        }
    }

    public static boolean isNotEmpty(String str) {
        if (!AbstractCommonMapper.isEmptyOrNull(str)){
            return true;
        }
        return false;
    }

    /**
     * Used in Pop up window when user wants to see customer details
     *
     * @param subProfileID
     * @return HashMap
     */

    public static HashMap getCustomerProfileDetailsByGroupId(String subProfileID) {
        HashMap result = new HashMap();
        String fam = null;
        String famcode = null;
        ICMSCustomer custOB = null;
        ArrayList mainBorrowerList = new ArrayList();
        String isMainBorrowerOnly = "N";
        ILimitProfile aILimitProfile = null;
        ILimitProxy limitProxy = LimitProxyFactory.getProxy();
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
        try {
            if (subProfileID != null){
                custOB = getCustomer(subProfileID);
                if (custOB != null){
                    mainBorrowerList = custproxy.getMBlistByCBleId(custOB.getCustomerID());
                    if (mainBorrowerList == null || mainBorrowerList.size() == 0){
                        isMainBorrowerOnly = "Y";
                    }
                    if (custOB.getNonBorrowerInd()){
                        if (Long.toString(custOB.getCustomerID()) != null){
                            fam = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_NAME);
                            famcode = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_CODE);
                        }
                    }
                }
            }

            result.put("limitprofileOb", aILimitProfile);
            result.put("customerOb", custOB);
            result.put("isMainBorrowerOnly", isMainBorrowerOnly);
            result.put("fam", fam);
            result.put("famcode", famcode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * helper method used in   getCustomerProfileDetailsByGroupId
     *
     * @param sub_profile_id
     * @return ICMSCustomer
     */
    private static ICMSCustomer getCustomer(String sub_profile_id) {
        ICMSCustomer custOB = null;
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
        try {
            custOB = custproxy.getCustomer(Long.parseLong(sub_profile_id));
            if (custOB != null){
                return custOB;
            }
        } catch (Exception e) {

        }
        return custOB;

    }
}
