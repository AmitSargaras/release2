package com.integrosys.cms.ui.collateral.pledgor;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.customer.CustomerMessageBody;
import com.integrosys.cms.host.eai.customer.MainProfileDetails;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.service.MessageSender;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.StpSyncProxyImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class SavePledgorCommand extends AbstractCommand {
    private final Logger logger = LoggerFactory.getLogger(SavePledgorCommand.class);
    private final PropertyUtil propUtil = PropertyUtil.getInstance("/stp.properties");
    private final NumberFormat formatter = new DecimalFormat("#"); //Format to Integer/Numerical only

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"pledgorData", "java.util.List", FORM_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE}});
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
                {"subtype", "java.lang.String", REQUEST_SCOPE},});
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        boolean stpMode = propUtil.getBoolean(IStpConstants.STP_SWITCH_MODE, false);

        try {
            ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
            String subtype = (String) map.get("subtype");
            result.put("subtype", subtype);

            ICollateral iCol = trxValue.getStagingCollateral();
            // ICollateralPledgor[] pledgor = iCol.getPledgors();

            //Modified by KLYong: Stp Cif and Blacklist Inquiry
            ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
            List pledgorList = (List) map.get("pledgorData");
            List tempPledgorList = new ArrayList();

            if (pledgorList != null && pledgorList.size() > 0) {
                if (iCol.getPledgors() != null && iCol.getPledgors().length > 0) {
                    ICollateralPledgor[] existPledgors = iCol.getPledgors(); //Previous list
                    ICollateralPledgor[] newPledgors = (ICollateralPledgor[]) pledgorList.toArray(new ICollateralPledgor[0]); //New pledgor list

                    List duplicateCifNo = new ArrayList();
                    boolean isDuplicateCifNo = false;

                    for (int i = 0; i < existPledgors.length; i++) {
                        for (int j = 0; j < newPledgors.length; j++) {
                            if (StringUtils.equals(existPledgors[i].getLegalID(), newPledgors[j].getLegalID())) {
                                isDuplicateCifNo = true; //If found existing cif no
                                duplicateCifNo.add(existPledgors[i].getLegalID()); //To be displayed on screen which cif no was fail
                            }
                        }
                    }

                    tempPledgorList.addAll(Arrays.asList(existPledgors));
                    if (isDuplicateCifNo) { //Thrown exception on screen if duplicate cif no
                        String strDuplicateCifNo = "";
                        for (Iterator iter = duplicateCifNo.iterator(); iter.hasNext();) {
                            strDuplicateCifNo += "[" + iter.next() + "] ";
                        }
                        exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, "Duplication found in existing pledgor - CIF NUMBER " + strDuplicateCifNo));
                    } else {
                        for (int i = 0; i < newPledgors.length; i++) {
                            if (stpMode) { //If stp mode true, proceed to stp firing
                                try {
                                    ICollateralPledgor newPledgor = retrieveStpPledgorList(user, newPledgors[i]); //Update pledgor list with additional pledgor detail
                                    tempPledgorList.add(newPledgor);
                                } catch (StpCommonException e) {
                                    tempPledgorList.clear();
                                    tempPledgorList.addAll(Arrays.asList(existPledgors));
                                    exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc() + " - CIF NUMBER [" + newPledgors[i].getLegalID() + "]"));
                                    break;
                                }
                            } else {
                                tempPledgorList.add(newPledgors[i]);
                            }
                        }
                    }
                } else { //If list was empty
                    ICollateralPledgor[] newPledgors = (ICollateralPledgor[]) pledgorList.toArray(new ICollateralPledgor[0]);
                    for (int i = 0; i < newPledgors.length; i++) {
                        if (stpMode) {
                            try {
                                ICollateralPledgor newPledgor = retrieveStpPledgorList(user, newPledgors[i]); //Update pledgor list with additional pledgor detail
                                tempPledgorList.add(newPledgor);
                            } catch (StpCommonException e) {
                                tempPledgorList.clear();
                                exceptionMap.put("stpError", new ActionMessage(IStpConstants.ERR_STP_INQUIRY, e.getErrorDesc() + " - CIF NUMBER [" + newPledgors[i].getLegalID() + "]"));
                                break;
                            }
                        } else {
                            tempPledgorList.add(newPledgors[i]);
                        }
                    }
                }

                Collections.sort(tempPledgorList, new Comparator() {
                    public int compare(Object a, Object b) {
                        ICollateralPledgor pledgor1 = (ICollateralPledgor) a;
                        ICollateralPledgor pledgor2 = (ICollateralPledgor) b;
                        return pledgor1.getPledgorName().compareTo(pledgor2.getPledgorName());
                    }
                });
                iCol.setPledgors((ICollateralPledgor[]) tempPledgorList.toArray(new ICollateralPledgor[0]));
                trxValue.setStagingCollateral(iCol);
            }
            result.put("serviceColObj", trxValue);
        }
        catch (Exception e) {
            DefaultLogger.error(this, "got exception in doExecute", e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    /**
     * STP Message firing to retrive more pledgor detail
     *
     * @param user    - user stored in the session
     * @param pledgor - pledgor added
     * @return pledgor with additional detail updated
     * @throws Exception stp common exception
     */
    public ICollateralPledgor retrieveStpPledgorList(ICommonUser user, ICollateralPledgor pledgor) throws Exception {
        ArrayList stpArrlist = new ArrayList();

        stpArrlist.add(user);
        stpArrlist.add(pledgor); //Pass in object for stp firing

        StpSyncProxyImpl stpProxy = StpSyncProxyImpl.getInstance();
        Object[] responseMsg = (Object[]) stpProxy.submitTask(IStpTransType.TRX_TYPE_CIF_BLACKLIST_INQUIRY, stpArrlist.toArray());

        if (responseMsg != null && responseMsg.length > 0) {
            ICollateralPledgor outPledgor = (ICollateralPledgor) responseMsg[0];
            //Andy Wong: default source id, status and legal type category
            outPledgor.setSourceID(ICMSConstant.SOURCE_SYSTEM_CMS);
            outPledgor.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);
            outPledgor.setLegalIDSource("SIBS");
            outPledgor.setSCIPledgorMapStatus(ICMSConstant.HOST_STATUS_INSERT);
            outPledgor.setPledgorStatus(ICMSConstant.HOST_STATUS_INSERT);
            outPledgor.setLegalTypeID(56);
            Map responseMessageMap = (Map) responseMsg[1];

            //AW, 10 July 2009: only persist main/sub profile when not exists
            ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
            long customerId = customerDAO.searchCustomerByCIFNumber(outPledgor.getLegalID(), "SIBS");
            if(customerId < 0){
                logger.debug("Pledgor CIF not found in main/sub profile table. CIF : " + outPledgor.getLegalID());
                //AW, 15 June 2009: fire CU001 EAI to local, persist pledgor main/sub profile into DB
                EAIMessage message = new EAIMessage();
                message.setMsgHeader(formulateRequestHeader());
                message.setMsgBody(formulateRequestBody(responseMessageMap));

                MessageSender localMessageSender = (MessageSender) BeanHouse.get("localMessageSender");
                String acknowledge = (String) localMessageSender.sendAndReceive(message);
                if (acknowledge != null) {
                    String responseCode = EAIHeaderHelper.getHeaderValue(acknowledge, "ResponseCode");
                    String responseDesc = EAIHeaderHelper.getHeaderValue(acknowledge, "ResponseMessage");
                    logger.debug("Pledgor fire CU001 \n " + acknowledge);
                    if (!"0".equals(responseCode)) {
                        logger.error("Response retrieved from Origination for Pledgor, CIF Number [" + outPledgor.getLegalID() + "] is bad [" + acknowledge + "]");
                        throw new StpCommonException(IStpConstants.ERR_CODE_BIZ_GENERIC, responseDesc, null);
                    }
                }
            }
            return outPledgor;
        }
        return null;
    }

    /**
     * Formulate CU001 request header
     *
     * @return EAIHeader object
     * @author Andy Wong
     */
    private EAIHeader formulateRequestHeader() {
        EAIHeader responseHeader = new EAIHeader();
        responseHeader.setMessageId(IEAIHeaderConstant.CUSTOMER_CU001);
        responseHeader.setMessageType(IEAIHeaderConstant.MESSAGE_TYPE_CUSTOMER);
        responseHeader.setPublishType(IEAIHeaderConstant.PUB_TYPE_NORMAL);
        responseHeader.setMessageRefNum(String.valueOf(Math.abs(new Random().nextInt())));
        responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));
        responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);

        return responseHeader;
    }

    /**
     * Formulate CU001 request body
     *
     * @param responseMessageMap
     * @return EAIBody object
     * @author Andy Wong
     */
    private EAIBody formulateRequestBody(Map responseMessageMap) {
        CustomerMessageBody customerMessageBody = new CustomerMessageBody();
        MainProfileDetails mainProfileDetails = new MainProfileDetails();
        MainProfile mainProfile = new MainProfile();

        mainProfile.setSource("SIBS");
        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFCIFN")))
            mainProfile.setCIFId(formatter.format(Long.parseLong(responseMessageMap.get("CFCIFN") + "")));
        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFFSNM")))
            mainProfile.setCustomerNameShort(responseMessageMap.get("CFFSNM") + "");
        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFNA1")))
            mainProfile.setCustomerNameLong(responseMessageMap.get("CFNA1") + "");
        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFNA1A")))
            mainProfile.setCustomerNameLong(mainProfile.getCustomerNameLong() + " " + responseMessageMap.get("CFNA1A"));

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFCLAS"))) {
            StandardCode customerClass = new StandardCode();
            customerClass.setStandardCodeNumber("56");
            customerClass.setStandardCodeValue(responseMessageMap.get("CFCLAS") + "");
            mainProfile.setCustomerClass(customerClass);
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFCITZ")))
            mainProfile.setDomicileCountry(responseMessageMap.get("CFCITZ") + "");

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFCOPR"))) {
            StandardCode customerType = new StandardCode();
            customerType.setStandardCodeNumber("8");
            if (StringUtils.equals((String) responseMessageMap.get("CFCOPR"), "Y")) {
                customerType.setStandardCodeValue("77");
            } else {
                if (StringUtils.equals((String) responseMessageMap.get("CFRACE"), "FRN"))
                    customerType.setStandardCodeValue("95");
                else
                    customerType.setStandardCodeValue("78");
            }
            mainProfile.setCustomerType(customerType);
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("D8CBSTR6"))) {
            String customerStartDate = responseMessageMap.get("D8CBSTR6") + "";
            mainProfile.setCustomerStartDate(StringUtils.substring(customerStartDate, 4, 7) + StringUtils.substring(customerStartDate, 2, 3) + StringUtils.substring(customerStartDate, 0, 1));
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFBUST"))) {
            StandardCode natureOfBiz = new StandardCode();
            natureOfBiz.setStandardCodeNumber("17");
            natureOfBiz.setStandardCodeValue(responseMessageMap.get("CFBUST") + "");
            mainProfile.setNatureOfBiz(natureOfBiz);
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFOFFR")))
            mainProfile.setAccountOfficerID(responseMessageMap.get("CFOFFR") + "");

        Vector customerIdInfoList = new Vector();
        CustomerIdInfo customerIdInfo;
        StandardCode idType;

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFSSNO"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFIDCN"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFSSCD"))) {
            customerIdInfo = new CustomerIdInfo();
            customerIdInfo.setCountryIssued(responseMessageMap.get("CFIDCN") + "");
            customerIdInfo.setIdNumber(responseMessageMap.get("CFSSNO") + "");
            idType = new StandardCode();
            idType.setStandardCodeNumber("ID_TYPE");
            idType.setStandardCodeValue(responseMessageMap.get("CFSSCD") + "");
            customerIdInfo.setIdType(idType);

            customerIdInfoList.add(customerIdInfo);
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFSSNO2"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFIDCN2"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFSSCD2"))) {
            customerIdInfo = new CustomerIdInfo();
            customerIdInfo.setCountryIssued(responseMessageMap.get("CFIDCN2") + "");
            customerIdInfo.setIdNumber(responseMessageMap.get("CFSSNO2") + "");
            idType = new StandardCode();
            idType.setStandardCodeNumber("ID_TYPE");
            idType.setStandardCodeValue(responseMessageMap.get("CFSSCD2") + "");
            customerIdInfo.setIdType(idType);

            customerIdInfoList.add(customerIdInfo);
        }

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFSSNO3"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFIDCN3"))
                && StringUtils.isNotBlank((String) responseMessageMap.get("CFSSCD3"))) {
            customerIdInfo = new CustomerIdInfo();
            customerIdInfo.setCountryIssued(responseMessageMap.get("CFIDCN3") + "");
            customerIdInfo.setIdNumber(responseMessageMap.get("CFSSNO3") + "");
            idType = new StandardCode();
            idType.setStandardCodeNumber("ID_TYPE");
            idType.setStandardCodeValue(responseMessageMap.get("CFSSCD3") + "");
            customerIdInfo.setIdType(idType);

            customerIdInfoList.add(customerIdInfo);
        }
        mainProfile.setIdInfo(customerIdInfoList);

        if (StringUtils.isNotBlank((String) responseMessageMap.get("CFBRNN"))) {
            StandardCode customerBranch = new StandardCode();
            customerBranch.setStandardCodeNumber("40");
            customerBranch.setStandardCodeValue(formatter.format(Long.parseLong(responseMessageMap.get("CFBRNN") + "")));
            mainProfile.setCustomerBranch(customerBranch);
        }

        mainProfileDetails.setMainProfile(mainProfile);
        customerMessageBody.setMainProfileDetails(mainProfileDetails);

        return customerMessageBody;
    }
}
