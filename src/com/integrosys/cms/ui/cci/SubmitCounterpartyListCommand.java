package com.integrosys.cms.ui.cci;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.HashMap;

/**
 * This class implements command
 */
public class SubmitCounterpartyListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {"ICCICounterparty", "com.integrosys.cms.app.cci.bus.ICCICounterparty", FORM_SCOPE},
                {"session.customerlist", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {"session.ICCICounterpartyDetails", "com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails", SERVICE_SCOPE},
                {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
            };
    }


    /**
     * Defines a two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", REQUEST_SCOPE},
        };
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute ");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        ICCICounterpartyDetailsTrxValue resultValue = null;


        try {
            ICCICounterpartyDetails stagingDetails = (ICCICounterpartyDetails) inputMap.get("session.ICCICounterpartyDetails");
            ICCICounterpartyDetailsTrxValue value = (ICCICounterpartyDetailsTrxValue) inputMap.get("ICCICounterpartyDetailsTrxValue");
            ITrxContext trxContext = (ITrxContext) inputMap.get("theOBTrxContext");

            if (value == null) {
                value = new OBCCICounterpartyDetailsTrxValue();
            }

            value.setStagingCCICounterpartyDetails(stagingDetails);
            trxContext.setLimitProfile(null);
            trxContext.setTrxCountryOrigin(getTrxCountryOrigin(inputMap));
            trxContext.setCustomer(getCustomerType(ICategoryEntryConstant.COMMON_CODE_LE_ID_TYPE_GCIF, value));

            ICCICustomerProxy proxy = CCICustomerProxyFactory.getProxy();
            if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
                resultValue = proxy.makerSubmitICCICustomer(trxContext, value, value.getStagingCCICounterpartyDetails());
            } else {
                resultValue = proxy.makerSubmitICCICustomer(trxContext, value, value.getStagingCCICounterpartyDetails());
            }

            resultMap.put("request.ITrxValue", resultValue);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        DefaultLogger.debug(this, "Existing doExecute ");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }

    private ICMSCustomer getCustomerType(String CustomerType, ICCICounterpartyDetailsTrxValue value) throws Exception {
       // System.out.println("getCustomerType CustomerType  = " + CustomerType);
        ICMSCustomer aICMSCustomer = null;
        if (value == null) {
            return aICMSCustomer;
        }
        ICCICounterpartyDetails stagingDetails = value.getStagingCCICounterpartyDetails();
        if (stagingDetails == null) {
            return aICMSCustomer;
        }

        ICCICounterparty[]  counterparty = stagingDetails.getICCICounterparty();
        if (counterparty != null && counterparty.length > 0) {
            for (int i = 0; i < counterparty.length; i++) {
               // System.out.println("counterparty[i].getSourceID() = " + counterparty[i].getSourceID());
                if (!counterparty[i].getDeletedInd() && CustomerType.equals(counterparty[i].getSourceID())) {
                    return getCustomer(counterparty[i].getSubProfileID());
                }
            }
        }
        return aICMSCustomer;

    }

    public ICMSCustomer getCustomer(long customerID) throws Exception {
        ICustomerProxy proxy = CustomerProxyFactory.getProxy();
        ICMSCustomer aICMSCustomer = null;
        if (customerID == ICMSConstant.LONG_INVALID_VALUE) {
            return aICMSCustomer ;
        }
        try {
            aICMSCustomer = proxy.getCustomer(customerID);
            if (aICMSCustomer != null){
           //System.out.println("getCustomer CustomerName = " + aICMSCustomer.getCustomerName());
            }else{
           // System.out.println("getCustomer CustomerName = null");
            }
        } catch (Exception e) {

        }
        return aICMSCustomer;

    }

    public ICMSCustomer createCMSCustomer(ICCICounterparty counterparty) {
        ICMSCustomer ob = new OBCMSCustomer();
        ob.setCustomerName(counterparty.getLegalName());
        ob.setCustomerID(counterparty.getSubProfileID());
       // System.out.println("ob.getCustomerName() = " + ob.getCustomerName());
        return ob;
    }


    private String getTrxCountryOrigin (HashMap inputMap){
        String country =null;
        boolean isGEMSAMMAKER = false;
        ICommonUser user = (ICommonUser)inputMap.get(IGlobalConstant.USER);
        ITeam userTeam = (ITeam)inputMap.get(IGlobalConstant.USER_TEAM);
        TOP_LOOP: for(int i=0;i<userTeam.getTeamMemberships().length;i++){//parse team membership to validate user first
            for(int j=0; j<userTeam.getTeamMemberships()[i].getTeamMembers().length;j++){  //parse team memebers to get the team member first..
                if(userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID()==user.getUserID()){
                    if(userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID()== ICMSConstant.TEAM_TYPE_GEMS_AM_MAKER){
                        isGEMSAMMAKER = true;
                        country = user.getCountry() ;
                        break TOP_LOOP;
                    }
                }
            }
        }
        return country;
    }

}
