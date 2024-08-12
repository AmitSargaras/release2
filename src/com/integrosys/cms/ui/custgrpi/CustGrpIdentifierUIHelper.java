package com.integrosys.cms.ui.custgrpi;

import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.proxy.ICustGrpIdentifierProxy;
import com.integrosys.cms.app.custgrpi.proxy.CustGrpIdentifierProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.IInternalCreditRatingProxy;
//import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.InternalCreditRatingProxyFactory;
//import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.InternalCreditRatingBusManagerFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.*;

import org.apache.commons.lang.StringUtils;


/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Jan 11, 2008
 * Time: 1:21:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustGrpIdentifierUIHelper {

    public CustGrpIdentifierUIHelper() {
    }

    public static boolean isExist(ICustGrpIdentifier[]  actual, String lmpLeID) {

        return false;
    }

    public static boolean isNotEmpty(String str) {
        if (StringUtils.isNotEmpty(str)){
            return true;
        }
        return false;
    }

    public static Map getRelationValue() {

        Map map = new HashMap();
        List relationValue = new ArrayList();
        List relationLabel = new ArrayList();

        relationValue.add("Director");
        relationValue.add("Holding Company");
        relationValue.add("Proxy");
        relationValue.add("Shareholder");
        relationValue.add("Subsidiary");

        CommonCodeList commonCode = CommonCodeList .getInstance(null, null, ICMSUIConstant.GENT_REL, null);
        Collection values = commonCode.getCommonCodeValues();
        Collection labels = commonCode.getCommonCodeLabels();
        if (values == null){
            values = new ArrayList();
        }
        if (labels == null){
            labels = new ArrayList();
        }
        map.put("relationCodes", values);
        map.put("relationLabel", labels);

        map.put("relationCodes1", relationValue);
        map.put("relationLabel1", relationValue);


        return map;
    }

    public static String getCodeDesc(String code, String codeType) {
        String codeDesc = "-";
        if (AbstractCommonMapper.isEmptyOrNull(code)){
            return codeDesc;
        } else {
            codeDesc = CommonDataSingleton.getCodeCategoryLabelByValue(codeType, code.trim());
        }
        if (codeDesc == null || codeDesc.equals("")){
            codeDesc = "-";
        }
        return codeDesc;
    }

    public static String getCountryCodeDesc(String grpCountry) {

        CountryList cList = CountryList.getInstance();
        String codeDesc = "-";
        if (AbstractCommonMapper.isEmptyOrNull(grpCountry)){
            return codeDesc;
        } else {
            codeDesc = cList.getCountryName(grpCountry.trim());
        }
        if (codeDesc == null || codeDesc.equals("")){
            codeDesc = "-";
        }
        return codeDesc;
    }

    public static String getGroupAccountMgrName(long mgid) {
        String codeDesc = "-";
        if (mgid == ICMSConstant.LONG_INVALID_VALUE || mgid == 0){
            return codeDesc;
        }
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        Map map = new HashMap();
        Collection labels = null;
        Collection values = null;
        map.put("teamType", ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER + "");
        try {
            map = proxy.getGroupAccountMgrCodes(map);
            if (map != null && !map.isEmpty()){
                labels = (List) map.get("labels");
                values = (List) map.get("values");
                if (values != null){
                    List list = (List) values;
                    List list1 = (List) labels;
                    for (int i = 0; i < list.size(); i++) {
                        String userid = (String) list.get(i);
                        if (userid != null && userid.equals(mgid + "")){
                            codeDesc = (String) list1.get(i);
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
        }

        return codeDesc;
    }

    /**
     * Method to get credit rating limit amount from SC
     * @param rating
     * @return
     */
    public static Amount getGroupLimit(String intLmt, String rating) {
        ICustGrpIdentifierProxy proxy = CustGrpIdentifierProxyFactory.getProxy();
        Amount amount = new Amount();
        try {
           amount = proxy.getGroupLimit(intLmt, rating);
        } catch (Exception e) {
        }

        return amount;
    }

    /**
     * helper method to check the type of User
     * @param user
     * @param userTeam
     * @return  boolean
     */
    public static boolean isGEMSMAKER( ITeam userTeam, ICommonUser user) {
        for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {
            for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
                if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user.getUserID()){
                    if (userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID() == ICMSConstant.TEAM_TYPE_GEMS_MAKER){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ICMSCustomer getCustomer(long customerID) {
        ICustomerProxy proxy = CustomerProxyFactory.getProxy();
        ICMSCustomer customer = null;
        try {
            customer = proxy.getCustomer(customerID);
        } catch (CustomerException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
