package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.host.stp.bus.IStpMasterTrans;
import com.integrosys.cms.host.stp.bus.IStpTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 11, 2008
 * Time: 10:18:32 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 17 Feb 2009
 *        <p/>
 *        Moving single col facility linkage deletion from create cmd to delete cmd
 */
public class StpColFacCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        boolean successDeleted;
        String appType = null;
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();

        // use new ob to set for non-deleted CollateralLimitMap
        ICollateral newObCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        newObCollateral.setCollateralLimits(SecuritySubTypeUtil.retrieveNonDeletedCollateralLimitMap(newObCollateral));

        ICollateralLimitMap[] obCollateralLimitMap = (ICollateralLimitMap[]) newObCollateral.getCurrentCollateralLimits();
        String trxType = obCollateralTrxValue.getFromState();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        if (obCollateralLimitMap != null) {
            for (int i = 0; i < obCollateralLimitMap.length; i++) {
                HashMap addHdrField = new HashMap();
                List aList = getStpTransBusManager().getColLimitMapByID(new Long(obCollateralLimitMap[i].getChargeID()));
                ArrayList iList = (ArrayList) aList.get(0);
                if (iList != null) {
                    addHdrField.put(MSG_APP_NO_FIELD, iList.get(0));
                    addHdrField.put(MSG_FAC_CODE_FIELD, iList.get(1));
                    addHdrField.put(MSG_ACC_SEQ_FIELD, iList.get(2));
                    appType = (String) iList.get(3);
                }

                if (StringUtils.isNotBlank(appType) && !appType.equals(APPLICATION_TYPE)) {
                    ctx.put(FIELD_VAL_MAP, addHdrField);;
                    ctx.put(REF_NUM, Long.toString(obCollateralLimitMap[i].getChargeID()));
                    ctx.put(STP_TRANS_MAP, stpTransMap);
                    IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                    if (obStpTrans != null) {
                        //put the OB into an array of object
                        ArrayList aObject = new ArrayList();
                        aObject.add(obCollateral);
                        aObject.add(obCollateralLimitMap[i]);
                        if (obCollateralLimitMap[i].getPledgeAmountPercentage() > 0) {
//                            double pledgeAmtPerc = obCollateralLimitMap[i].getPledgeAmountPercentage() / 100;
//                            addHdrField.put("CCDPCT", String.valueOf(pledgeAmtPerc));
                            BigDecimal bigDecimal = new BigDecimal(obCollateralLimitMap[i].getPledgeAmountPercentage()).
                                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                            addHdrField.put("CCDPCT", bigDecimal.toString());
                        }
                        if (obCollateralLimitMap[i].getDrawAmountPercentage() > 0) {
//                            double drawAmtPerc = obCollateralLimitMap[i].getDrawAmountPercentage() / 100;
                            BigDecimal bigDecimal = new BigDecimal(obCollateralLimitMap[i].getDrawAmountPercentage()).
                                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                            addHdrField.put("CCDPCD", bigDecimal.toString());
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
                    }
                }
            }
        }
        return false;
    }
}
