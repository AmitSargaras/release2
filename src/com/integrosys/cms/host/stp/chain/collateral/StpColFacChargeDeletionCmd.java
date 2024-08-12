package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.ILimitChargeMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 11, 2008
 * Time: 10:25:24 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 17 Feb 2009
 *        <p/>
 *        Moving single charge deletion from create cmd to delete cmd
 */
public class StpColFacChargeDeletionCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        String appType = null;
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        ILimitCharge[] obLimitCharge = (ILimitCharge[]) obCollateral.getLimitCharges();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        String secType = obCollateralTrxValue.getCollateral().getCollateralType().getTypeCode();

        //Andy Wong: only Stp when it is ASSET BASED, PROPERTY or OTHERS collateral
        if ((secType.equals(ICMSConstant.SECURITY_TYPE_ASSET)
                || secType.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)
                || secType.equals(ICMSConstant.SECURITY_TYPE_OTHERS))
                ) {

            // entire collateral to be deleted
            if (StringUtils.equals(obCollateralTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
                for (int i = 0; i < obLimitCharge.length; i++) {
                    ICollateralLimitMap[] obCollateralLimitMap = (ICollateralLimitMap[]) obLimitCharge[i].getLimitMaps();
                    if (obCollateralLimitMap.length > 0) {
                        for (int j = 0; j < obCollateralLimitMap.length; j++) {

                            HashMap addHdrField = new HashMap();
                            ILimitChargeMap obLimitChargeMap = (ILimitChargeMap) obCollateralLimitMap[j];
                            List aList = getStpTransBusManager().getLimitChargeMapByID(new Long(obLimitChargeMap.getLimitChargeMapID()));
                            ArrayList iList = (ArrayList) aList.get(0);
                            if (iList != null) {
                                addHdrField.put(MSG_APP_NO_FIELD, iList.get(0));
                                addHdrField.put(MSG_FAC_CODE_FIELD, iList.get(1));
                                addHdrField.put(MSG_ACC_SEQ_FIELD, iList.get(2));
                                appType = (String) iList.get(3);
                            }

                            if (StringUtils.isNotBlank(appType) && !appType.equals(APPLICATION_TYPE)) {
                                ctx.put(FIELD_VAL_MAP, addHdrField);
                                ctx.put(REF_NUM, Long.toString(obLimitChargeMap.getLimitChargeMapID()));
                                ctx.put(STP_TRANS_MAP, stpTransMap);
                                ctx.put(IS_DELETE, new Boolean(true));
                                IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                                if (obStpTrans != null) {
                                    //put the OB into an array of object
                                    ArrayList aObject = new ArrayList();
                                    aObject.add(obCollateral);
                                    aObject.add(obLimitCharge[i]);
                                    aObject.add(addHdrField);

                                    //map to field OB
                                    List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

                                    // construct msg, send msg and decipher msg
                                    stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

                                    //put required OB in context to be used in subsequent command
                                    ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                                    ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

                                    //map object to biz OB and update STP transaction
                                    boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                                    if (!successUpd) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //delete during maintenance
                if (!deletedColFacCharge(ctx)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deletedColFacCharge(Map ctx) throws Exception {
        IStpTrans[] existObStpTrans = null;
        boolean deletedIns = false;
        int count = 0;
        HashMap addHdrField = new HashMap();

        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        ILimitCharge[] obLimitCharge = (ILimitCharge[]) obCollateral.getLimitCharges();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        if (obStpMasterTrans.getTrxEntriesSet().size() > 0) {
            existObStpTrans = new IStpTrans[obStpMasterTrans.getTrxEntriesSet().size()];
            Iterator iSet = obStpMasterTrans.getTrxEntriesSet().iterator();
            while (iSet.hasNext()) {
                existObStpTrans[count] = (IStpTrans) iSet.next();
                count++;
            }
        }

        if (existObStpTrans != null) {
            for (int k = 0; k < existObStpTrans.length; k++) {
                // Andy Wong, Aug 7, 2009: not to fire delete message when previous create message failed
                if (existObStpTrans[k].getTrxType().equals(TRX_TYPE_COL_FAC_CHARGE_CREATE) && TRX_SUCCESS.equals(existObStpTrans[k].getStatus())) {
                    if (!ArrayUtils.isEmpty(obLimitCharge)) {
                        for (int i = 0; i < obLimitCharge.length; i++) {
                            ICollateralLimitMap[] obCollateralLimitMap = (ICollateralLimitMap[]) obLimitCharge[i].getLimitMaps();
                            for (int j = 0; j < obCollateralLimitMap.length; j++) {
                                ILimitChargeMap obLimitChargeMap = (ILimitChargeMap) obCollateralLimitMap[j];
                                if (existObStpTrans[k].getReferenceId().equals(new Long(obLimitChargeMap.getLimitChargeMapID()))) {
                                    deletedIns = false;
                                    break;
                                } else {
                                    deletedIns = true;
                                }
                            }
                            if (!deletedIns) {
                                break;
                            }
                        }
                    } else {
                        deletedIns = true;
                    }

                    if (deletedIns) {
                        ctx.put(FIELD_VAL_MAP, addHdrField);
                        ctx.put(REF_NUM, existObStpTrans[k].getReferenceId().toString());
                        ctx.put(STP_TRANS_MAP, stpTransMap);
                        ctx.put(IS_DELETE, new Boolean(true));
                        IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                        if (obStpTrans != null) {
                            // to do fire deleted record to sibs to delete the insurance policy
                            List aList = getStpTransBusManager().getLimitChargeMapByID(existObStpTrans[k].getReferenceId());
                            ArrayList iList = (ArrayList) aList.get(0);
                            if (iList != null) {
                                addHdrField.put(MSG_APP_NO_FIELD, iList.get(0));
                                addHdrField.put(MSG_FAC_CODE_FIELD, iList.get(1));
                                addHdrField.put(MSG_ACC_SEQ_FIELD, iList.get(2));
                                addHdrField.put(MSG_CHARGE_SEC_RANK, iList.get(4));
                            }

                            //put the OB into an array of object
                            ArrayList aObject = new ArrayList();
                            aObject.add(obCollateral);
                            aObject.add(addHdrField);

                            //map to field OB
                            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

                            // construct msg, send msg and decipher msg
                            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

                            //put required OB in context to be used in subsequent command
                            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

                            if (processResponse(ctx, obStpTrans, stpList)) {
                                continue;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
