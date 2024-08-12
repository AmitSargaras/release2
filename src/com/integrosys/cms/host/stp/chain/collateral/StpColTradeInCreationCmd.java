package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.OBSpecificChargeVehicle;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.OBStpField;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:04:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class StpColTradeInCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap addHdrField = new HashMap();
        OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
        String colSubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
        String sourceSecType = obCollateralTrxValue.getCollateral().getSourceSecuritySubType();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        String colCode = PropertyManager.getValue(SIBS_VEH_COL_CODE);
        String[] aColCode = colCode.split(",");
        HashMap colCodeMap = new HashMap();
        for (int i = 0; i < aColCode.length; i++) {
            colCodeMap.put(aColCode[i], aColCode[i]);
        }
        double tradeInAmt = 0.0;
        OBTradeInInfo[] obTradeInInfo = null;

        if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH) || colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
            if (colCodeMap.containsKey(sourceSecType)) {
                if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                    OBSpecificChargeVehicle obSpecificChargeVehicle = (OBSpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                    tradeInAmt = obSpecificChargeVehicle.getDptradein() != null ? obSpecificChargeVehicle.getDptradein().getAmount() : 0;
                    obTradeInInfo = (OBTradeInInfo[]) obSpecificChargeVehicle.getTradeInInfo();
                } else if (colSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                    OBSpecificChargePlant obSpecificChargePlant = (OBSpecificChargePlant) obCollateralTrxValue.getCollateral();
                    tradeInAmt = obSpecificChargePlant.getDptradein() != null ? obSpecificChargePlant.getDptradein().getAmount() : 0;
                    obTradeInInfo = (OBTradeInInfo[]) obSpecificChargePlant.getTradeInInfo();
                }

                if (tradeInAmt > 0) {
                    ctx.put(FIELD_VAL_MAP, addHdrField);
                    ctx.put(REF_NUM, Long.toString(obCollateralTrxValue.getCollateral().getCollateralID()));
                    ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
                    OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                    if (obStpTrans != null) {
                        //put the OB into an array of object
                        ArrayList aObject = new ArrayList();
                        aObject.add(obCollateralTrxValue.getCollateral());
                        aObject.add(obTradeInInfo[0]);
                        //addHdrField = preProcess(addHdrField, obCollateralTrxValue);
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
                            updateColWithoutTrx(ctx, obCollateralTrxValue);
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

    public HashMap preProcess(HashMap xmlHashMap, OBCollateralTrxValue obCollateralTrxValue) {
        String secType = obCollateralTrxValue.getCollateral().getCollateralType().getTypeCode();
        String secSubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
        NumberFormat formatter = new DecimalFormat("#");
        String strValue = null;
        OBTradeInInfo[] obTradeInInfo = null;
        if (secType.equals(ICMSConstant.SECURITY_TYPE_ASSET) && secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
            OBSpecificChargeVehicle obSpecificChargeVehicle = (OBSpecificChargeVehicle) obCollateralTrxValue.getCollateral();
            obTradeInInfo = (OBTradeInInfo[]) obSpecificChargeVehicle.getTradeInInfo();
        } else
        if (secType.equals(ICMSConstant.SECURITY_TYPE_ASSET) && secSubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
            OBSpecificChargePlant obSpecificChargePlant = (OBSpecificChargePlant) obCollateralTrxValue.getCollateral();
            obTradeInInfo = (OBTradeInInfo[]) obSpecificChargePlant.getTradeInInfo();
        }
        if (obTradeInInfo[0].getTradeInValue() != null) {
            if (obTradeInInfo[0].getTradeInValue().getAmount() > 0) {
                strValue = formatter.format(obTradeInInfo[0].getTradeInValue().getAmount());
                xmlHashMap.put(MSG_TRADE_IN_FIELD, strValue);
            }
        }
        if (obTradeInInfo[0].getTradeInDeposit() != null) {
            if (obTradeInInfo[0].getTradeInDeposit().getAmount() > 0) {
                strValue = formatter.format(obTradeInInfo[0].getTradeInDeposit().getAmount());
                xmlHashMap.put(MSG_TRADE_DEP_FIELD, strValue);
            }
        }
        return xmlHashMap;
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);
        if (successStp) {
            OBCollateralTrxValue obCollateralTrxValue = (OBCollateralTrxValue) ctx.get(COL_TRX_VALUE);
            String securityType = obCollateralTrxValue.getStagingCollateral().getCollateralType().getTypeCode();
            String securitySubType = obCollateralTrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode();
            Amount dpAmount = new Amount();
            if (obStpFieldL != null) {
                Iterator stpField = obStpFieldL.iterator();
                while (stpField.hasNext()) {
                    OBStpField obStpField = (OBStpField) stpField.next();
                    if (obStpField.getFieldID().equals(MSG_TRADE_DEP_FIELD)) {
                        dpAmount = new Amount(Double.parseDouble(obStpField.getValue()), obCollateralTrxValue.getCollateral().getCurrencyCode());
                    }
                }
                if (securityType.equals(ICMSConstant.SECURITY_TYPE_ASSET)) {
                    if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                        OBSpecificChargePlant stgSpecificChargePlant = (OBSpecificChargePlant) obCollateralTrxValue.getStagingCollateral();
                        stgSpecificChargePlant.setDptradein(dpAmount);
                        obCollateralTrxValue.setStagingCollateral(stgSpecificChargePlant);
                        OBSpecificChargePlant actSpecificChargePlant = (OBSpecificChargePlant) obCollateralTrxValue.getCollateral();
                        actSpecificChargePlant.setDptradein(dpAmount);
                        obCollateralTrxValue.setCollateral(actSpecificChargePlant);
                    } else if (securitySubType.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                        OBSpecificChargeVehicle stgSpecificChargeVehicle = (OBSpecificChargeVehicle) obCollateralTrxValue.getStagingCollateral();
                        stgSpecificChargeVehicle.setDptradein(dpAmount);
                        obCollateralTrxValue.setStagingCollateral(stgSpecificChargeVehicle);
                        OBSpecificChargeVehicle actSpecificChargeVehicle = (OBSpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                        actSpecificChargeVehicle.setDptradein(dpAmount);
                        obCollateralTrxValue.setCollateral(actSpecificChargeVehicle);
                    }
                }
            }
            ctx.put(COL_TRX_VALUE, obCollateralTrxValue);
        }

        return successStp;
    }
}
