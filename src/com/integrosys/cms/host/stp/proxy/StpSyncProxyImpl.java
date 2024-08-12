package com.integrosys.cms.host.stp.proxy;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.host.mq.IMessageSenderProxy;
import com.integrosys.cms.host.stp.adapter.IMessageAdapter;
import com.integrosys.cms.host.stp.bus.IStpTransBusManager;
import com.integrosys.cms.host.stp.bus.OBStpField;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.host.stp.mapper.STPMapper;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Sep 3, 2008
 * Time: 11:57:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpSyncProxyImpl implements IStpSyncProxy, IStpTransType, IStpConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IStpTransBusManager stpTransBusManager;
    private IMessageAdapter stpMsgAdapter;
    private IMessageSenderProxy stpMsgSender;
    private ISTPMapper stpMapper;

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public IMessageAdapter getStpMsgAdapter() {
        return stpMsgAdapter;
    }

    public void setStpMsgAdapter(IMessageAdapter stpMsgAdapter) {
        this.stpMsgAdapter = stpMsgAdapter;
    }

    public IMessageSenderProxy getStpMsgSender() {
        return stpMsgSender;
    }

    public void setStpMsgSender(IMessageSenderProxy stpMsgSender) {
        this.stpMsgSender = stpMsgSender;
    }

    public ISTPMapper getStpMapper() {
        return stpMapper;
    }

    public void setStpMapper(ISTPMapper stpMapper) {
        this.stpMapper = stpMapper;
    }

    /**
     * singleton instance
     */
    private static StpSyncProxyImpl singleton = new StpSyncProxyImpl();

    /**
     * Gets singleton
     *
     * @return singleton
     */
    public static StpSyncProxyImpl getInstance() {
        return singleton;
    }

    //** Modified by KLYong - Stp inquiry and detail listing

    /**
     * Submiting task of transaction firing request to sibs to retrieve message response
     *
     * @param trxType - transaction type
     * @param object  - object (hashmap, arraylist or biz object)
     * @return message response
     * @throws Exception exception
     */
    public Object submitTask(String trxType, Object[] object) throws Exception {
        try {
            if (StringUtils.equals(trxType, TRX_TYPE_INQUIRE_CIF)) {
                return getCifSearchByCustomerID(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_SEARCH_FD_ACCT_LIST)) {
                return getFDAccountSearchByGroup(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_ACC_VERIFY)) {
                return getAccountVerification(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_FAC_COL_CHARGE_LIST)) {
                return getFacilityCollateralChargeListing(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_FAC_COL_CHARGE_INQUIRY)) {
                return getFacilityCollateralChargeInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_COL_LINK_FAC_SUBFILE)) {
                return getCollateralLinkFacilitySubfile(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_COL_LINK_FAC_INQUIRY)) {
                return getCollateralLinkFacilityInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_REL_SUBFILE_INQUIRY)) {
                return getFacRelSubfileInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_REL_DETAIL_INQUIRY)) {
                return getFacRelDetailInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_BNM_DETAIL_INQUIRY)) {
                return getFacBnmDetailInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_OFFICER_SUBFILE_INQUIRY)) {
                return getFacOfficialSubfileInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_INSURE_DETAIL_INQUIRY)) {
                return getFacInsuranceInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_MASTER_SUBFILE_INQUIRY)) {
                return getFacMasterSubfileInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_LS_FAC_MASTER_DETAIL_INQUIRY)) {
                return getFacMasterDetailInquiry(trxType, object);
            } else if (StringUtils.equals(trxType, TRX_TYPE_CIF_BLACKLIST_INQUIRY)
//                    || StringUtils.equals(trxType, TRX_TYPE_FAC_ISLAMIC_RENTAL_RENEWAL_INQUIRY)
//                    || StringUtils.equals(trxType, TRX_TYPE_FAC_ISLAMIC_SEC_DEPOSIT_INQUIRY)
//                    || StringUtils.equals(trxType, TRX_TYPE_FAC_ISLAMIC_MASTER_INQUIRY)
                    ) {
                return getCifBlackListInquiry(trxType, object);
            }
        } catch (UnknownHostException e) {
            throw new StpCommonException(ERR_CODE_INVALID_HOST, ERR_DESC_INVALID_HOST, e);
        } catch (IOException e) {
            throw new StpCommonException(ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT, e);
        }
        return null;
    }

    private String stpMoreRecord;

    public void setStpMoreRecord(String stpMoreRecord) {
        this.stpMoreRecord = stpMoreRecord;
    }

    public String getStpMoreRecord() {
        return this.stpMoreRecord;
    }

    //CIF search by customer id listing

    public ArrayList getCifSearchByCustomerID(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //FD account search by group listing

    public ArrayList getFDAccountSearchByGroup(String trxType, Object[] object) throws Exception {
        ArrayList stpArrayList = new ArrayList();
        HashMap stpHashMap = new HashMap();
        ICommonUser iCommonUser = new OBCommonUser();

        for (int i = 0; i < object.length; i++) { //Get the hashmap and the user id for re-firing purpose if found more record
            if (object[i] instanceof HashMap) {
                stpHashMap = (HashMap) object[i];
            } else if (object[i] instanceof ICommonUser) {
                iCommonUser = (ICommonUser) object[i];
            }
        }
        ArrayList bizList = getArrayListMsg(trxType, object);

        while (StringUtils.equals(getStpMoreRecord(), "Y")) { //Check the message response for more record indicator
            int lastindex = bizList.lastIndexOf(new OBCashDeposit());
            OBCashDeposit obCashDep = (OBCashDeposit) bizList.get(lastindex);

            stpArrayList.add(stpHashMap);
            HashMap moreHashMap = new HashMap();
            moreHashMap.put(HDR_DSP_MORE_SEARCH_IND, "1");
            moreHashMap.put(HDR_MBASE_MORE_SEARCH_IND, "Y");
            stpArrayList.add(moreHashMap);
            stpArrayList.add(iCommonUser);
            stpArrayList.add(obCashDep);

            bizList.addAll(getArrayListMsg(trxType, stpArrayList.toArray()));
        }
        return bizList;
    }

    //Account verification

    public Object[] getAccountVerification(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //Facility's Collateral Charge listing

    public Object getFacilityCollateralChargeListing(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //Facility's Collateral Charge inquiry

    public Object getFacilityCollateralChargeInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //Collateral Linkage To Facility Subfile

    public Object getCollateralLinkFacilitySubfile(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //Collateral Linkage To Facility Inquiry

    public Object getCollateralLinkFacilityInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //LS Fac Rel. Subfile Inquiry Program

    public Object getFacRelSubfileInquiry(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //LS Fac Rel. Detail Inquiry Program

    public Object getFacRelDetailInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //LS Fac BNM. Detail Inquiry Program

    public Object getFacBnmDetailInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //LS Fac Official Subfile Inquiry Program

    public Object getFacOfficialSubfileInquiry(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //LS Fac Ins. Detail Inquiry Program

    public Object getFacInsuranceInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //LS Fac Subfile Inquiry

    public Object getFacMasterSubfileInquiry(String trxType, Object[] object) throws Exception {
        return getArrayListMsg(trxType, object);
    }

    //LS Fac Detail Inquiry

    public Object getFacMasterDetailInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //CIF and Blacklist Inquiry
    //AW, 15 June 2009: customize CIF detail inquiry transaction to return stp response map

    public Object getCifBlackListInquiry(String trxType, Object[] object) throws Exception {
        HashMap headerMap = getMessageHeader();
        byte[] convertByteMsg = getRequestMsg(trxType, ArrayUtils.add(object, headerMap), ISTPMapper.OTHER_PATH);
        byte[] returnByteMsg = (byte[]) getStpMsgSender().sendAndReceive(convertByteMsg, headerMap.get(MBASE_UNQKEY).toString()); //Sending byte through message sender

        if (returnByteMsg != null) {
            Object[] returnOB = new Object[2];
            List returnMsgList = getStpMapper().getList(StpCommandUtil.getResponseXmlTag(trxType), ISTPMapper.OTHER_PATH); //Get the response xml definition list
            returnMsgList = getStpMsgAdapter().decipherMessageToList(returnByteMsg, returnMsgList); //Decipher message to list of stp object
            Map responseMap = StpCommandUtil.getResponseMsgMap(returnMsgList);

            returnOB[0] = getStpMapper().mapToBizOB(object[1], returnMsgList); //Get the biz object
            returnOB[1] = responseMap;
            if (!StringUtils.equals(responseMap.get(RES_FIELD_ID).toString(), RES_REPLY_SUCCESS)) {
                if (StringUtils.isNotBlank(responseMap.get(RES_ERR_REASON1).toString())) {
                    throw new StpCommonException(ERR_CODE_BIZ_GENERIC, responseMap.get(RES_ERR_REASON1).toString(), null);
                } else {
                    throw new StpCommonException(ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT, null);
                }
            }
            return returnOB;
        }
        return null;
    }

    //363 - Facility Islamic Rental Renewel Inquiry

    public Object getFacIslamicRentRenewelInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //364 - Facility Islamic Security Deposit Inquiry

    public Object getFacIslamicSecDepositInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    //366 - Facility Islamic Master Inquiry

    public Object getFacIslamicMasterInquiry(String trxType, Object[] object) throws Exception {
        return getObjectMsg(trxType, object);
    }

    /**
     * Retrieve business object containing all the response messages
     *
     * @param trxType - request/response id
     * @param object  - object (business object)
     * @return business object with values
     * @throws Exception exception
     */
    public Object[] getObjectMsg(String trxType, Object[] object) throws Exception {
        Object[] bizOB = new Object[object.length];
        String path = STPMapper.OTHER_PATH;

        HashMap headerMap = getMessageHeader();
        byte[] convertByteMsg = getRequestMsg(trxType, ArrayUtils.add(object, headerMap), ISTPMapper.OTHER_PATH);

        if (convertByteMsg != null) {
            byte[] returnByteMsg = (byte[]) getStpMsgSender().sendAndReceive(convertByteMsg, headerMap.get(MBASE_UNQKEY).toString()); //Sending byte through message sender

            if (returnByteMsg != null) {
                List returnMsgList = getStpMapper().getList(StpCommandUtil.getResponseXmlTag(trxType), path); //Get the response xml definition list
                returnMsgList = getStpMsgAdapter().decipherMessageToList(returnByteMsg, returnMsgList); //Decipher message to list of stp object

                for (int i = 0; i < object.length; i++)
                    bizOB[i] = getStpMapper().mapToBizOB(object[i], returnMsgList); //Get the biz object

                StringBuffer stpMsgBuffer = new StringBuffer();
                stpMsgBuffer.append("------------------- Start Response -------------------\n");
                for (Iterator iterator = returnMsgList.iterator(); iterator.hasNext();) {
                    OBStpField field = (OBStpField) iterator.next();
                    stpMsgBuffer.append("------------------- " + field.getFieldID() + " = " + StringUtils.trim(field.getValue()) + "\n");
                }
                stpMsgBuffer.append("------------------- End Response -------------------\n");
                logger.debug(stpMsgBuffer.toString());

                Map responseMap = StpCommandUtil.getResponseMsgMap(returnMsgList);
                if (!StringUtils.equals(responseMap.get(RES_FIELD_ID).toString(), RES_REPLY_SUCCESS)) {
                    if (StringUtils.isNotBlank(responseMap.get(RES_ERR_REASON1).toString())) {
                        throw new StpCommonException(ERR_CODE_BIZ_GENERIC, responseMap.get(RES_ERR_REASON1).toString(), null);
                    } else {
                        throw new StpCommonException(ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT, null);
                    }
                }
            }
        }
        return bizOB;
    }

    /**
     * Retrieves list of response business objects messages
     *
     * @param trxType - request/response id
     * @param object  - object (business object)
     * @return business object with values
     * @throws Exception exception
     */
    public ArrayList getArrayListMsg(String trxType, Object[] object) throws Exception {
        List bizOBList = null;
        String path = STPMapper.OTHER_PATH;

        HashMap headerMap = getMessageHeader();
        byte[] convertByteMsg = getRequestMsg(trxType, ArrayUtils.add(object, headerMap), path);
        if (convertByteMsg != null) {
            byte[] returnByteMsg = (byte[]) getStpMsgSender().sendAndReceive(convertByteMsg, headerMap.get(MBASE_UNQKEY).toString());  //Sending byte through message sender

            if (returnByteMsg != null) {
                List returnMsgList = getStpMapper().getList(StpCommandUtil.getResponseXmlTag(trxType), path); //Get the response xml definition list
                returnMsgList = getStpMsgAdapter().decipherMessageToList(returnByteMsg, returnMsgList); //Decipher message to list of stp object'
                bizOBList = getStpMapper().mapToBizOBList(returnMsgList); //Get the biz object into a list

                StringBuffer stpMsgBuffer = new StringBuffer();
                stpMsgBuffer.append("------------------- Start Response -------------------\n");
                for (Iterator iterator = returnMsgList.iterator(); iterator.hasNext();) {
                    OBStpField field = (OBStpField) iterator.next();
                    stpMsgBuffer.append("------------------- " + field.getFieldID() + " = " + StringUtils.trim(field.getValue()) + "\n");
                }
                stpMsgBuffer.append("------------------- End Response -------------------\n");
                logger.debug(stpMsgBuffer.toString());

                Map responseMap = StpCommandUtil.getResponseMsgMap(returnMsgList);
                setStpMoreRecord(responseMap.get(HDR_MBASE_MORE_SEARCH_IND).toString()); //Get more record indicator
                if (!StringUtils.equals(responseMap.get(RES_FIELD_ID).toString(), RES_REPLY_SUCCESS)) {
                    if (StringUtils.isNotBlank(responseMap.get(RES_ERR_REASON1).toString())) {
                        throw new StpCommonException(ERR_CODE_BIZ_GENERIC, responseMap.get(RES_ERR_REASON1).toString(), null);
                    } else {
                        throw new StpCommonException(ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT, null);
                    }
                }
            }
        }
        return (ArrayList) bizOBList;
    }

    /**
     * Get Request message
     *
     * @param trxType - transaction type code
     * @param object  - business search object
     * @param path    - collateral, facility, other stp path
     * @return byte array message
     * @throws StpCommonException common exception
     */
    public byte[] getRequestMsg(String trxType, Object[] object, String path) throws Exception {
        List stpList = getStpMapper().getList(StpCommandUtil.getRequestXmlTag(trxType), path); //Get the request xml definition list

        for (int i = 0; i < object.length; i++)
            stpList = getStpMapper().mapToFieldOB(object[i], stpList); //Map field to list of stp object

        StringBuffer stpMsgBuffer = new StringBuffer();
        // Andy Wong, Oct 12, 2009: append request message content for 1 time logging
        stpMsgBuffer.append("\n------------------- Start Request -------------------\n");
        for (Iterator iterator = stpList.iterator(); iterator.hasNext();) {
            OBStpField field = (OBStpField) iterator.next();
            stpMsgBuffer.append("------------------- " + field.getFieldID() + " = " + StringUtils.trim(field.getValue()) + "\n");
        }
        stpMsgBuffer.append("------------------- End Request -------------------\n");
        logger.debug(stpMsgBuffer.toString());
        return getStpMsgAdapter().constructMessageToByte(stpList); //Convert message to byte[]
    }

    /**
     * Get message header
     *
     * @return hashmap containing the message header
     */
    protected HashMap getMessageHeader() {
        Date curDate = new Date();
        HashMap map = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
        SimpleDateFormat timeFormat = new SimpleDateFormat(PropertyManager.getValue(STP_TIME_PATTERN));

        map.put(MBASE_REFNUM, getStpTransBusManager().getTrxRefNum());
        map.put(MBASE_TRXDATE, dateFormat.format(curDate));
        map.put(MBASE_TRXTIME, timeFormat.format(curDate));
        map.put(MBASE_UNQKEY, STP_TRX_UID_PREFIX + getStpTransBusManager().getSeqNum());
        return map;
    }
}
