package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:04:03 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 17 Feb 2009
 *
 * Moving single insurance deletion from create cmd to delete cmd
 */
public class StpColInsCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        IInsurancePolicy[] obInsurancePolicy = (IInsurancePolicy[]) obCollateral.getInsurancePolicies();
        String trxType = obCollateralTrxValue.getFromState();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        if (obInsurancePolicy != null) {
            for (int i = 0; i < obInsurancePolicy.length; i++) {
                HashMap addHdrField = new HashMap();
                ctx.put(FIELD_VAL_MAP, addHdrField);
                ctx.put(REF_NUM, Long.toString(obInsurancePolicy[i].getInsurancePolicyID()));
                ctx.put(STP_TRANS_MAP, stpTransMap);
                IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                if (obStpTrans != null) {
                    //put the OB into an array of object
                    ArrayList aObject = new ArrayList();
                    aObject.add(obCollateral);
                    aObject.add(obInsurancePolicy[i]);

                    ICollateralSubType obCollateralSubType = (ICollateralSubType) obCollateralTrxValue.getCollateral().getCollateralSubType();
                    String securitySubType = obCollateralTrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
                    //Andy Wong, 26 Feb 2009: map collateral desc to make and model for VEH collateral
                    if(getVehColCodes().containsKey(obCollateralTrxValue.getCollateral().getSourceSecuritySubType())){
                        if(StringUtils.equals(securitySubType, ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
                            ISpecificChargeVehicle obSpecificChargeVehicle = (ISpecificChargeVehicle) obCollateralTrxValue.getCollateral();
                            addHdrField.put(MSG_COL_DESC, CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargeVehicle.getBrand())
                                    + " " + CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargeVehicle.getModelNo()));

                        } else if (StringUtils.equals(securitySubType, ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
                            ISpecificChargePlant obSpecificChargePlant = (ISpecificChargePlant) obCollateralTrxValue.getCollateral();
                            addHdrField.put(MSG_COL_DESC, CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.VEHICLE_BRAND, obSpecificChargePlant.getBrand())
                                    + " " + CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.ASSET_MODEL_TYPE, obSpecificChargePlant.getModelNo()));
                        }
                    } else if (obCollateralSubType != null) {
                        aObject.add(obCollateralSubType);
                    }
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
                    updateColWithoutTrx(ctx, obCollateralTrxValue);
                }
            }
        }
        return false;
    }

    public boolean processResponse(Map ctx, IStpTrans obStpTrans, List obStpFieldL) throws Exception {
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        int arrayInd = -1;

        // map stp object list to biz OB
        if (obStpFieldL != null) {
            ICollateral actObCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
            IInsurancePolicy[] actObInsurancePolicy = (IInsurancePolicy[]) actObCollateral.getInsurancePolicies();
            for (int i = 0; i < actObInsurancePolicy.length; i++) {
                if (obStpTrans.getReferenceId().equals(new Long(actObInsurancePolicy[i].getInsurancePolicyID()))) {
                    actObInsurancePolicy[i] = (IInsurancePolicy) getStpMapper().mapToBizOB(actObInsurancePolicy[i], obStpFieldL);
                    arrayInd = i;
                }
            }
            actObCollateral.setInsurancePolicies(actObInsurancePolicy);
            obCollateralTrxValue.setCollateral(actObCollateral);
            ICollateral stgObCollateral = (ICollateral) obCollateralTrxValue.getStagingCollateral();
            IInsurancePolicy[] stgObInsurancePolicy = (IInsurancePolicy[]) stgObCollateral.getInsurancePolicies();
            if (arrayInd > -1) {
                stgObInsurancePolicy[arrayInd] = (IInsurancePolicy) getStpMapper().mapToBizOB(stgObInsurancePolicy[arrayInd], obStpFieldL);
                stgObCollateral.setInsurancePolicies(stgObInsurancePolicy);
            }
            obCollateralTrxValue.setStagingCollateral(stgObCollateral);
        }
        ctx.put(COL_TRX_VALUE, obCollateralTrxValue);

        return super.processResponse(ctx, obStpTrans, obStpFieldL);
    }
}
