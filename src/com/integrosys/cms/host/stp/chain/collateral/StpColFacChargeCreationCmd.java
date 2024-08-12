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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 11, 2008
 * Time: 10:21:50 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 17 Feb 2009
 *
 * Moving single charge deletion from create cmd to delete cmd
 */
public class StpColFacChargeCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        String appType = null;
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ILimitCharge[] actLimitCharge = (ILimitCharge[]) obCollateralTrxValue.getCollateral().getLimitCharges();
        ILimitCharge[] stgLimitCharge = (ILimitCharge[]) obCollateralTrxValue.getStagingCollateral().getLimitCharges();
        String trxType = obCollateralTrxValue.getFromState();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);
        String secType = obCollateralTrxValue.getCollateral().getCollateralType().getTypeCode();

        //Andy Wong: only Stp when it is ASSET BASED, PROPERTY or OTHERS collateral
        if (secType.equals(ICMSConstant.SECURITY_TYPE_ASSET)
                || secType.equals(ICMSConstant.SECURITY_TYPE_PROPERTY)
                || secType.equals(ICMSConstant.SECURITY_TYPE_OTHERS)) {

            if (!ArrayUtils.isEmpty(actLimitCharge)) {
                for (int i = 0; i < actLimitCharge.length; i++) {
                    ICollateralLimitMap[] obCollateralLimitMap = (ICollateralLimitMap[]) actLimitCharge[i].getLimitMaps();
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
                            IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                            if (obStpTrans != null) {
                                //put the OB into an array of object
                                ArrayList aObject = new ArrayList();
                                aObject.add(actLimitCharge[i]);
                                aObject.add(obCollateralTrxValue.getCollateral());
                                aObject.add(addHdrField);

                                //map to field OB
                                List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

                                // construct msg, send msg and decipher msg
                                stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

                                //put required OB in context to be used in subsequent command
                                ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                                ctx.put(COL_TRX_VALUE, obCollateralTrxValue);
                                ctx.put(ACTUAL_BIZ_OB, actLimitCharge[i]);
                                ctx.put(STAGE_BIZ_OB, stgLimitCharge[i]);

                                //map object to biz OB and update STP transaction
                                boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                                if (!successUpd) {
                                    return true;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Process response purposely to update Sibs collateral id, used to determine security rabk field editable
     * @param ctx
     * @param obStpTrans
     * @param obStpFieldL
     * @return
     * @throws Exception
     */
    public boolean processResponse(Map ctx, IStpTrans obStpTrans, List obStpFieldL) throws Exception {
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);
        if (successStp) {
            ICollateralTrxValue collateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
            ILimitCharge actLimitCharge = (ILimitCharge) ctx.get(ACTUAL_BIZ_OB);
            ILimitCharge stgLimitCharge = (ILimitCharge) ctx.get(STAGE_BIZ_OB);

            if(actLimitCharge!=null && stgLimitCharge!=null){
                actLimitCharge.setHostCollateralId(collateralTrxValue.getCollateral().getSCISecurityID());
                stgLimitCharge.setHostCollateralId(collateralTrxValue.getCollateral().getSCISecurityID());
                updateColWithoutTrx(ctx, collateralTrxValue);
            }

            // remove the OB from context
            ctx.remove(ACTUAL_BIZ_OB);
            ctx.remove(STAGE_BIZ_OB);
        }
        return successStp;
    }
}
