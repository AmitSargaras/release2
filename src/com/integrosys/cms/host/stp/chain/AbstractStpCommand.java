package com.integrosys.cms.host.stp.chain;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.IFacilityBusManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.host.mq.IMessageSenderProxy;
import com.integrosys.cms.host.stp.adapter.IMessageAdapter;
import com.integrosys.cms.host.stp.bus.*;
import com.integrosys.cms.host.stp.common.*;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA. User: Andy Wong Date: Sep 19, 2008 Time: 11:19:39
 * AM To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStpCommand implements IStpCommand, IStpConstants, IStpTransType {
    private final String noRetry = PropertyManager.getValue("stp.messageSender.retry");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IStpTransBusManager stpTransBusManager;

    private ISTPMapper stpMapper;

    private IMessageAdapter stpMsgAdapter;

    private IMessageSenderProxy stpMsgSender;

    private ICollateralBusManager actualCollateralBusManager;

    private ICollateralBusManager stagingCollateralBusManager;

    private IFacilityBusManager actualFacilityBusManager;

    private IFacilityBusManager stagingFacilityBusManager;

    private IStpErrorCodeList stpErrorCodeList;

    private SBLimitManager actualLimitBusManager;

    private SBLimitManager stagingLimitBusManager;

    private Map trxStateMap;

    private StringBuffer stpMsgBuffer;
    
    String currentThreadName;

    public Map getTrxStateMap() {
        return trxStateMap;
    }

    public void setTrxStateMap(Map trxStateMap) {
        this.trxStateMap = trxStateMap;
    }    

    public SBLimitManager getActualLimitBusManager() {
        return actualLimitBusManager;
    }

    public SBLimitManager getStagingLimitBusManager() {
        return stagingLimitBusManager;
    }

    public void setActualLimitBusManager(SBLimitManager actualLimitBusManager) {
        this.actualLimitBusManager = actualLimitBusManager;
    }

    public void setStagingLimitBusManager(SBLimitManager stagingLimitBusManager) {
        this.stagingLimitBusManager = stagingLimitBusManager;
    }

    public IStpTransBusManager getStpTransBusManager() {
        return stpTransBusManager;
    }

    public void setStpTransBusManager(IStpTransBusManager stpTransBusManager) {
        this.stpTransBusManager = stpTransBusManager;
    }

    public ISTPMapper getStpMapper() {
        return stpMapper;
    }

    public void setStpMapper(ISTPMapper stpMapper) {
        this.stpMapper = stpMapper;
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

    public ICollateralBusManager getActualCollateralBusManager() {
        return actualCollateralBusManager;
    }

    public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
        this.actualCollateralBusManager = actualCollateralBusManager;
    }

    public ICollateralBusManager getStagingCollateralBusManager() {
        return stagingCollateralBusManager;
    }

    public void setStagingCollateralBusManager(ICollateralBusManager stagingCollateralBusManager) {
        this.stagingCollateralBusManager = stagingCollateralBusManager;
    }

    public IFacilityBusManager getActualFacilityBusManager() {
        return actualFacilityBusManager;
    }

    public void setActualFacilityBusManager(IFacilityBusManager actualFacilityBusManager) {
        this.actualFacilityBusManager = actualFacilityBusManager;
    }

    public IFacilityBusManager getStagingFacilityBusManager() {
        return stagingFacilityBusManager;
    }

    public void setStagingFacilityBusManager(IFacilityBusManager stagingFacilityBusManager) {
        this.stagingFacilityBusManager = stagingFacilityBusManager;
    }

    public IStpErrorCodeList getStpErrorCodeList() {
        return stpErrorCodeList;
    }

    public void setStpErrorCodeList(IStpErrorCodeList stpErrorCodeList) {
        this.stpErrorCodeList = stpErrorCodeList;
    }

    public Map getVehColCodes() {
        String[] aColCode = PropertyManager.getValue(SIBS_VEH_COL_CODE).split(",");
        Map colCodeMap = new HashMap();
        for (int i = 0; i < aColCode.length; i++) {
            colCodeMap.put(aColCode[i], aColCode[i]);
        }
        return colCodeMap;
    }

    public IStpTrans initTransaction(Map ctx) throws Exception {
        IStpMasterTrans masterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        ICMSTrxValue trxValue = (ICMSTrxValue) ctx.get(StringUtils.equals(masterTrans.getTransactionType(),
                TRANS_TYPE_LIMIT) ? FAC_TRX_VALUE : COL_TRX_VALUE);
        Map fieldValueMap = (Map) ctx.get(FIELD_VAL_MAP);
        Map stpTransMap = (Map) ctx.get(STP_TRANS_MAP);
        Boolean isDelete = (Boolean) ctx.get(IS_DELETE);
        Long refNum = new Long(ctx.get(REF_NUM).toString());

        Date todayDate = new Date();
        String curTrxType = (String) trxStateMap.get(OPS_DESC_CREATE);
        String curOpsDesc = OPS_DESC_CREATE;

        DateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
        DateFormat timeFormat = new SimpleDateFormat(PropertyManager.getValue(STP_TIME_PATTERN));

        // Andy Wong: set mandatory MBASE fields
        fieldValueMap.put(HDR_MBASE_REF_NUM_FIELD, getStpTransBusManager().getTrxRefNum());
        fieldValueMap.put(HDR_MBASE_TRX_DATE_FIELD, dateFormat.format(todayDate));
        fieldValueMap.put(HDR_MBASE_TRX_TIME_FIELD, timeFormat.format(todayDate));
        fieldValueMap.put(HDR_MBASE_RESEND_FIELD, STP_TRX_SEND_IND);

        // Andy Wong, 5 Jan 2009: set user Id in DSP and MBASE header based on
        // trx login_id
        fieldValueMap.put(HDR_DSP_USER_ID, trxValue.getLoginId());
        fieldValueMap.put(HDR_MBASE_USER_ID, trxValue.getLoginId());

        IStpTrans obStpTrans = null;
        // preparing delete trx
        if (isDelete != null && isDelete.booleanValue()) {
            curTrxType = (String) trxStateMap.get(OPS_DESC_DELETE);
            curOpsDesc = OPS_DESC_DELETE;
            obStpTrans = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_DELETE).toString() + "&" + refNum);
            if (obStpTrans != null) {
                if (StringUtils.equals(obStpTrans.getStatus(), TRX_SUCCESS)) {
                    // bypass trx firing if DELETE had been succeed return null;
                    obStpTrans = null;
                } else if (StringUtils.equals(obStpTrans.getStatus(), TRX_SENDING)) {
                    // for existing Stp trx and request msg constructed, set resend flag
                    fieldValueMap.put(HDR_MBASE_RESEND_FIELD, STP_TRX_RESEND_IND);
                } else {
                    obStpTrans.setCreationDate(new Timestamp(todayDate.getTime()));
                    obStpTrans.setResponseCode(null);
                    obStpTrans.setRequestMsgStream(null);
                    // Andy Wong, Aug 7, 2009: apply new trx UID for FAILED message
                    obStpTrans.setTrxUID(new Long(getStpTransBusManager().getSeqNum()));
                }
            } else {
                obStpTrans = new OBStpTrans();
                obStpTrans.setCreationDate(new Timestamp(todayDate.getTime()));
                obStpTrans.setTrxUID(new Long(getStpTransBusManager().getSeqNum()));
                masterTrans.getTrxEntriesSet().add(obStpTrans);
            }
            // preparing create or update trx
        } else {
            obStpTrans = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_CREATE).toString() + "&" + refNum);
            if (obStpTrans != null && StringUtils.equals(obStpTrans.getStatus(), TRX_SUCCESS)) {
                curTrxType = trxStateMap.get(OPS_DESC_UPDATE).toString();
                curOpsDesc = OPS_DESC_UPDATE;
                obStpTrans = (OBStpTrans) stpTransMap.get(curTrxType + "&" + refNum);
            }

            if (obStpTrans == null) {
                obStpTrans = new OBStpTrans();
                obStpTrans.setCreationDate(new Timestamp(todayDate.getTime()));
                obStpTrans.setTrxUID(new Long(getStpTransBusManager().getSeqNum()));
                masterTrans.getTrxEntriesSet().add(obStpTrans);
            } else if (StringUtils.equals(obStpTrans.getStatus(), TRX_SENDING)
                    && StringUtils.equals(curOpsDesc, OPS_DESC_CREATE)) {
                // for existing Stp trx and request msg constructed, set resend flag
                fieldValueMap.put(HDR_MBASE_RESEND_FIELD, STP_TRX_RESEND_IND);
            } else {
                //reaching here indicates whole stp trx entering next cycle
                if (StringUtils.equals(obStpTrans.getStatus(), TRX_OBSOLETE) && StringUtils.equals(curOpsDesc, OPS_DESC_CREATE)) {
                    IStpTrans obsoleteUpdate = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_UPDATE).toString() + "&" + refNum);
                    if (obsoleteUpdate != null) {
                        obsoleteUpdate.setStatus(TRX_OBSOLETE);
                        obsoleteUpdate.setLastUpdateDate(new Timestamp(todayDate.getTime()));
                    }
                    IStpTrans obsoleteDelete = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_DELETE).toString() + "&" + refNum);
                    if (obsoleteDelete != null) {
                        obsoleteDelete.setStatus(TRX_OBSOLETE);
                        obsoleteDelete.setLastUpdateDate(new Timestamp(todayDate.getTime()));
                    }
                }
                obStpTrans.setCreationDate(new Timestamp(todayDate.getTime()));
                obStpTrans.setResponseCode(null);
                obStpTrans.setRequestMsgStream(null);
                obStpTrans.setTrxUID(new Long(getStpTransBusManager().getSeqNum()));
            }
        }

        // generic operation for create, update or delete trx
        if (obStpTrans != null) {
            obStpTrans.setTrxType(curTrxType);
            obStpTrans.setOpsDesc(curOpsDesc);
            obStpTrans.setMasterTrxId(masterTrans.getMasterTrxId());
            obStpTrans.setLastUpdateDate(new Timestamp(todayDate.getTime()));
            obStpTrans.setReferenceId(refNum);
            obStpTrans.setLastUpdateDate(new Timestamp(todayDate.getTime()));
            obStpTrans.setCurTrxHistoryId(trxValue.getCurrentTrxHistoryID());
            obStpTrans.setUserId(new Long(trxValue.getUID()));
            obStpTrans.setStatus(TRX_SENDING);
            fieldValueMap.put(HDR_MBASE_UID_FIELD, STP_TRX_UID_PREFIX + obStpTrans.getTrxUID());
            updateStpTrans(ctx, masterTrans);
        }

        // reset delete flag before execution
        ctx.remove(IS_DELETE);
        return obStpTrans;
    }

    /**
     * Method to send message, receive response and decipher to OBStpField List
     *
     * @param referenceID
     * @param ctx
     * @param stpList
     * @param transType
     * @return
     * @throws Exception
     */
    public List sendMessage(Long referenceID, Map ctx, List stpList, String transType) throws Exception {
        // setting the job name, which is the bean name as the thread name for the current thread
        currentThreadName = Thread.currentThread().getName();
        // Andy Wong, Oct 12, 2009: append request message content for 1 time logging
        stpMsgBuffer = new StringBuffer();
        stpMsgBuffer.append("\n------------------- Start Request -------------------\n");

        OBStpTrans hvObStpTrans = null;
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        for (Iterator iterator = obStpMasterTrans.getTrxEntriesSet().iterator(); iterator.hasNext();) {
            OBStpTrans obStpTrans = (OBStpTrans) iterator.next();
            if (obStpTrans.getTrxType().equals(transType) && obStpTrans.getReferenceId().equals(referenceID)) {
                hvObStpTrans = obStpTrans;
                break;
            }
        }

        Thread.currentThread().setName("StpMessageProcess_" + hvObStpTrans.getTrxUID());
        for (int i = 0; i < stpList.size(); i++) {
            OBStpField obStpField = new OBStpField();
            obStpField = (OBStpField) stpList.get(i);
            String listFieldID = obStpField.getFieldID();
            String listValue = obStpField.getValue();
            String listFormat = obStpField.getFormat();
            String listDecimalPoint = obStpField.getDecimalPoint();
            stpMsgBuffer.append("------------------- " + listFieldID + " = " + StringUtils.trim(listValue) + "\n");
            if (listFormat.equalsIgnoreCase("P") && StringUtils.isNotBlank(listValue)) {
                if (StringUtils.isNotBlank(listDecimalPoint) && Integer.parseInt(listDecimalPoint) > 0) {
                    try {
                        Double.parseDouble(listValue);
                    } catch (java.lang.NumberFormatException e) {
                        logger.debug(stpMsgBuffer.toString());
                        // revert original thread name
                        Thread.currentThread().setName(currentThreadName);
                        throw new StpCommonException(hvObStpTrans.getTrxId(), ERR_CODE_INVALID_PACK, listFieldID
                                + " : " + ERR_DESC_INVALID_PACK);
                    }
                } else if (StringUtils.isNotBlank(listDecimalPoint) && Integer.parseInt(listDecimalPoint) == 0) {
                    try {
                        Long.parseLong(listValue);
                    } catch (java.lang.NumberFormatException e) {
                        logger.debug(stpMsgBuffer.toString());
                        // revert original thread name
                        Thread.currentThread().setName(currentThreadName);
                        throw new StpCommonException(hvObStpTrans.getTrxId(), ERR_CODE_INVALID_PACK, listFieldID
                                + " : " + ERR_DESC_INVALID_PACK);
                    }
                }
            }
        }
        stpMsgBuffer.append("------------------- End Request -------------------\n");

        List responseMessageList = null;
        byte[] convertMsg = getStpMsgAdapter().constructMessageToByte(stpList);

        hvObStpTrans.setRequestMsgStream(convertMsg);
        updateStpTrans(ctx, obStpMasterTrans);

        if (convertMsg != null) {
            byte[] returnMsg = null;
            int count = 0;
            List returnStpFieldL = null;

            // get number of retry
            try {
                count = Integer.parseInt(noRetry);
            }
            catch (java.lang.NumberFormatException e) {
                logger.error("failed to convert no retry to int.", e);
            }

            for (int j = 0; j < count; j++) {
                try {
                    if (hvObStpTrans.getMsgCount() != null) {
                        hvObStpTrans.setMsgCount(new Integer(hvObStpTrans.getMsgCount().intValue() + 1));
                    } else {
                        hvObStpTrans.setMsgCount(new Integer(0));
                    }
                    hvObStpTrans.setLastUpdateDate(new Timestamp(new Date().getTime()));
                    // set master trx to context in case got exception
                    updateStpTrans(ctx, obStpMasterTrans);
                    returnMsg = (byte []) getStpMsgSender().sendAndReceive(hvObStpTrans.getRequestMsgStream(), hvObStpTrans.getTrxUID().toString());
                    break;
                }
                catch (UnknownHostException e) {
                    // set back the current thread name, not to confuse the name in the log
                    Thread.currentThread().setName(currentThreadName);
                    throw new StpCommonException(hvObStpTrans.getTrxId(), ERR_CODE_INVALID_HOST, ERR_DESC_INVALID_HOST);
                }
                catch (IOException e) {
                    // request timeout, retry
                    logger.info("UID = CMS" + hvObStpTrans.getTrxUID() + ", Attempt(s) = " + (hvObStpTrans.getMsgCount().intValue()+1));
                }
            }

            if (returnMsg != null) {
                if (StringUtils.equals(obStpMasterTrans.getTransactionType(), TRANS_TYPE_LIMIT)) {
                    returnStpFieldL = getStpMapper().getList(
                            StpCommandUtil.getResponseXmlTag(hvObStpTrans.getTrxType()), ISTPMapper.FACILITY_PATH);
                } else if (StringUtils.equals(obStpMasterTrans.getTransactionType(), TRANS_TYPE_COL)) {
                    returnStpFieldL = getStpMapper().getList(
                            StpCommandUtil.getResponseXmlTag(hvObStpTrans.getTrxType()), ISTPMapper.COLLATERAL_PATH);
                }
                responseMessageList = getStpMsgAdapter().decipherMessageToList(returnMsg, returnStpFieldL);
            } else {
                throw new StpCommonException(hvObStpTrans.getTrxId(), ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT);
            }
        }
        return responseMessageList;
    }

    /**
     * Method to update stp transaction value
     *
     * @param ctx
     * @param obStpMasterTrans
     * @return
     * @throws Exception
     */
    public void updateStpTrans(Map ctx, IStpMasterTrans obStpMasterTrans) throws Exception {
        ctx.put(STP_TRX_VALUE, obStpMasterTrans);
        getStpTransBusManager().updateMasterTrans(obStpMasterTrans);
    }

    /**
     * Method to prepare request message for Stp firing
     *
     * @param bizOB
     * @param transType
     * @return
     * @throws Exception
     */
    public List prepareRequest(Map ctx, Object[] bizOB, String transType, String configPath) throws Exception {
        List stpList = getStpMapper().getList(StpCommandUtil.getRequestXmlTag(transType), configPath);

        for (int i = 0; i < bizOB.length; i++) {
            stpList = getStpMapper().mapToFieldOB(bizOB[i], stpList);
        }
        return stpList;
    }

    /**
     * Method to process Stp response from SIBS handler
     *
     * @param ctx
     * @param obStpTrans
     * @param obStpFieldL
     * @return
     * @throws Exception
     */
    public boolean processResponse(Map ctx, IStpTrans obStpTrans, List obStpFieldL) throws Exception {
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = (Map) ctx.get(STP_TRANS_MAP);
        Iterator stpField = obStpFieldL.iterator();
        HashMap responseErrMap = new HashMap();
        String responseUniqueKey = null;
        Date todayDate = new Date();

        // Andy Wong, Oct 12, 2009: append request message content for 1 time logging
        stpMsgBuffer.append("------------------- Start Response -------------------\n");
        while (stpField.hasNext()) {
            OBStpField obStpField = (OBStpField) stpField.next();
            if (obStpField.getFieldID().equals(RES_FIELD_ID)) {
                obStpTrans.setResponseCode(obStpField.getValue());
            } else if (obStpField.getFieldID().equals(RES_ERR_CODE1) || obStpField.getFieldID().equals(RES_ERR_REASON1)) {
                responseErrMap.put(obStpField.getFieldID(), obStpField.getValue());
                // Added by KLYong: Error code 2 exists
                if (obStpField.getFieldID().equals(RES_ERR_CODE2) || obStpField.getFieldID().equals(RES_ERR_REASON2)) {
                    responseErrMap.put(obStpField.getFieldID(), obStpField.getValue());
                }
            } else if (obStpField.getFieldID().equals(HDR_MBASE_UID_FIELD)) {
                responseUniqueKey = obStpField.getValue();
            }
            stpMsgBuffer.append("------------------- " + obStpField.getFieldID() + " = " + obStpField.getValue() + "\n");
        }
        stpMsgBuffer.append("------------------- End Response -------------------\n");
        logger.debug(stpMsgBuffer.toString());
        // revert original thread name
        Thread.currentThread().setName(currentThreadName);

        if (obStpTrans != null && StringUtils.equals(obStpTrans.getResponseCode(), RES_REPLY_SUCCESS)
                && StringUtils.isNotBlank(responseUniqueKey)
                && StringUtils.equals(responseUniqueKey, STP_TRX_UID_PREFIX + String.valueOf(obStpTrans.getTrxUID()))) {
            obStpTrans.setStatus(TRX_SUCCESS);

            // Andy Wong, Oct 12, 2009: tag create & update trx as obsolete only after deletion successfully done in Sibs
            if(StringUtils.equals(obStpTrans.getOpsDesc(), OPS_DESC_DELETE)){
                IStpTrans obsoleteCreate = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_CREATE).toString() + "&" + obStpTrans.getReferenceId());
                if (obsoleteCreate != null) {
                    obsoleteCreate.setLastUpdateDate(new Timestamp(todayDate.getTime()));
                    obsoleteCreate.setStatus(TRX_OBSOLETE);
                }
                IStpTrans obsoleteUpdate = (OBStpTrans) stpTransMap.get(trxStateMap.get(OPS_DESC_UPDATE).toString() + "&" + obStpTrans.getReferenceId());
                if (obsoleteUpdate != null) {
                    obsoleteUpdate.setLastUpdateDate(new Timestamp(todayDate.getTime()));
                    obsoleteUpdate.setStatus(TRX_OBSOLETE);
                }
            }
            updateStpTrans(ctx, obStpMasterTrans);
            return true;
        } else {
            IStpTransError obStpTransError = new OBStpTransError();
            Set errSet = obStpTrans.getTrxErrorSet() != null ? obStpTrans.getTrxErrorSet() : new HashSet();
            for (Iterator iterError = errSet.iterator(); iterError.hasNext();) {
                IStpTransError existTransError = (IStpTransError) iterError.next();
                if (existTransError.getTrxUID().equals(obStpTrans.getTrxUID())) {
                    //we reuse existing error record when trx UID same, used for resend case
                    obStpTransError = existTransError;
                    break;
                }
            }
            obStpTransError.setTrxUID(obStpTrans.getTrxUID());
            if ((StringUtils.isNotBlank((String) responseErrMap.get(RES_ERR_CODE1))
                    || StringUtils.isNotBlank((String) responseErrMap.get(RES_ERR_REASON1)))
                    && StringUtils.isNotBlank(obStpTrans.getResponseCode())
                    && StringUtils.equals(responseUniqueKey, STP_TRX_UID_PREFIX + String.valueOf(obStpTrans.getTrxUID()))) {

                // Andy Wong, Sep 2, 2009: route transaction to PENDING_RETRY when delete message failed
                if(StringUtils.equals(obStpTrans.getOpsDesc(), OPS_DESC_DELETE)){
                    obStpMasterTrans.setStatus(MASTER_TRX_PENDING_CHECKER);
                }  else {
                    obStpMasterTrans.setStatus(MASTER_TRX_PENDING_MAKER);
                }

                obStpTrans.setStatus(TRX_FAILED);
                obStpTransError.setErrorCode((String) responseErrMap.get(RES_ERR_CODE1));
                obStpTransError.setErrorDesc((String) responseErrMap.get(RES_ERR_REASON1));
                // Added by KLYong: Append error message
                if (StringUtils.isNotBlank((String) responseErrMap.get(RES_ERR_REASON2))) {
                    // Message more than 50 characters
                    StringBuffer strErrMessage = new StringBuffer();
                    strErrMessage.append((String) responseErrMap.get(RES_ERR_REASON1)).append(" ").append(
                            (String) responseErrMap.get(RES_ERR_REASON2));
                    obStpTransError.setErrorDesc(strErrMessage.toString());
                }
            } else {
                obStpMasterTrans.setStatus(MASTER_TRX_PENDING_CHECKER);
                if (StringUtils.isBlank(obStpTrans.getResponseCode())
                        || !StringUtils.equals(responseUniqueKey, STP_TRX_UID_PREFIX + String.valueOf(obStpTrans.getTrxUID()))) {
                    obStpTrans.setResponseCode(RES_REPLY_FAILED);
                    obStpTransError.setErrorCode(ERR_CODE_BAD_RESPONSE);
                    obStpTransError.setErrorDesc(ERR_DESC_BAD_RESPONSE);
                } else {
                    obStpTrans.setResponseCode(RES_REPLY_FAILED);
                    obStpTransError.setErrorCode(ERR_CODE_TIMEOUT);
                    obStpTransError.setErrorDesc(ERR_DESC_TIMEOUT);
                }
            }
            errSet.add(obStpTransError);
            obStpTrans.setTrxErrorSet(errSet);
            updateStpTrans(ctx, obStpMasterTrans);
            return false;
        }
    }

    /**
     * Method to update collateral value
     *
     * @param obCollateralTrxValue
     * @throws Exception
     */
    public void updateColWithoutTrx(Map ctx, ICollateralTrxValue obCollateralTrxValue) throws Exception {
        if (obCollateralTrxValue != null) {
            OBCollateral stgCollateral = (OBCollateral) getStagingCollateralBusManager().updateCollateral(
                    obCollateralTrxValue.getStagingCollateral());
            obCollateralTrxValue.getStagingCollateral().setVersionTime(stgCollateral.getVersionTime());
            OBCollateral actCollateral = (OBCollateral) getActualCollateralBusManager().updateCollateral(
                    obCollateralTrxValue.getCollateral());
            obCollateralTrxValue.getCollateral().setVersionTime(actCollateral.getVersionTime());
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);
        }
    }

    /**
     * Method to update Facility value
     *
     * @param obFacilityTrxValue
     * @throws Exception
     */
    public void updateFacWithoutTrx(Map ctx, IFacilityTrxValue obFacilityTrxValue) throws Exception {
        if (obFacilityTrxValue != null) {
            getStagingFacilityBusManager().updateFacilityMaster(obFacilityTrxValue.getStagingFacilityMaster());
            getActualFacilityBusManager().updateFacilityMaster(obFacilityTrxValue.getFacilityMaster());
        }
    }

    public void updateLimitWithoutTrx(ILimit actual, ILimit stage) throws Exception {
        if (actual != null) {
            getActualLimitBusManager().updateLimit(actual);
        }
        if (stage != null) {
            getStagingLimitBusManager().updateLimit(stage);
        }
    }
}
