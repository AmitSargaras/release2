package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.OBSpecificChargeVehicle;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:28:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpColTradeInDeletionCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
        String colSubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);
        String sourceSecType = obCollateralTrxValue.getCollateral().getSourceSecuritySubType();
        String colCode = PropertyManager.getValue(SIBS_VEH_COL_CODE);
        String[] aColCode = colCode.split(",");
        HashMap colCodeMap = new HashMap();
        for (int i = 0; i < aColCode.length; i++) {
            colCodeMap.put(aColCode[i], aColCode[i]);
        }
        double tradeInAmt = 0.0;

        if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH) || colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
            if (colCodeMap.containsKey(sourceSecType)) {
                if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                    OBSpecificChargeVehicle obSpecificChargeVehicle = (OBSpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                    tradeInAmt = obSpecificChargeVehicle.getDptradein() != null ? obSpecificChargeVehicle.getDptradein().getAmount() : 0;
                } else if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                    OBSpecificChargePlant obSpecificChargePlant = (OBSpecificChargePlant) obCollateralTrxValue.getCollateral();
                    tradeInAmt = obSpecificChargePlant.getDptradein() != null ? obSpecificChargePlant.getDptradein().getAmount() : 0;
                }

                if (tradeInAmt > 0) {
                    HashMap addHdrField = new HashMap();
                    ctx.put(FIELD_VAL_MAP, addHdrField);
                    ctx.put(REF_NUM, Long.toString(obCollateralTrxValue.getCollateral().getCollateralID()));
                    ctx.put(STP_TRANS_MAP, stpTransMap);
                    ctx.put(IS_DELETE, new Boolean(true));
                    OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                    if (obStpTrans != null) {
                        //put the OB into an array of object
                        ArrayList aObject = new ArrayList();
                        OBCollateral obCollateral = (OBCollateral) obCollateralTrxValue.getCollateral();
                        aObject.add(obCollateral);
                        aObject.add(addHdrField);

                        //map to field OB
                        List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.COLLATERAL_PATH);

                        // construct msg, send msg and decipher msg
                        stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

                        //map object to biz OB and update STP transaction
                        boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                        if (!successUpd) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
