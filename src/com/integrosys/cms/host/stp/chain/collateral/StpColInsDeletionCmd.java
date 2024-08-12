package com.integrosys.cms.host.stp.chain.collateral;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
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

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 5, 2008
 * Time: 12:28:24 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 17 Feb 2009
 *        <p/>
 *        Moving single insurance deletion from create cmd to delete cmd
 */
public class StpColInsDeletionCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        IInsurancePolicy[] obInsurancePolicy = (IInsurancePolicy[]) obCollateral.getInsurancePolicies();
        IStpMasterTrans obStpMasterTrans = (IStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        // entire collateral to be deleted
        if (StringUtils.equals(obCollateralTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
            if (obInsurancePolicy.length > 0) {
                for (int i = 0; i < obInsurancePolicy.length; i++) {
                    HashMap addHdrField = new HashMap();
                    ctx.put(FIELD_VAL_MAP, addHdrField);
                    ctx.put(REF_NUM, Long.toString(obInsurancePolicy[i].getInsurancePolicyID()));
                    ctx.put(STP_TRANS_MAP, stpTransMap);
                    ctx.put(IS_DELETE, new Boolean(true));
                    IStpTrans obStpTrans = (IStpTrans) initTransaction(ctx);

                    if (obStpTrans != null) {
                        //put the OB into an array of object
                        ArrayList aObject = new ArrayList();
                        aObject.add(obInsurancePolicy[i]);
                        aObject.add(obCollateral);
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
        } else {
            // insurance deletion during maintenance
            if (!deletedInsPolicy(ctx)) {
                return true;
            }
        }
        return false;
    }

    public boolean deletedInsPolicy(Map ctx) throws Exception {
        IStpTrans[] existObStpTrans = null;
        boolean deletedIns = false;
        int count = 0;
        HashMap addHdrField = new HashMap();

        ICollateralTrxValue obCollateralTrxValue = (ICollateralTrxValue) ctx.get(COL_TRX_VALUE);
        ICollateral obCollateral = (ICollateral) obCollateralTrxValue.getCollateral();
        IInsurancePolicy[] obInsurancePolicy = (IInsurancePolicy[]) obCollateral.getInsurancePolicies();
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
                if (existObStpTrans[k].getTrxType().equals(TRX_TYPE_COL_INSURE_CREATE) && TRX_SUCCESS.equals(existObStpTrans[k].getStatus())) {
                    if (!ArrayUtils.isEmpty(obInsurancePolicy)) {
                        for (int i = 0; i < obInsurancePolicy.length; i++) {
                            if (existObStpTrans[k].getReferenceId().equals(new Long(obInsurancePolicy[i].getInsurancePolicyID()))) {
                                deletedIns = false;
                                break;
                            } else {
                                deletedIns = true;
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
                            List aList = getStpTransBusManager().getInsPolicySequenceByInsPolicyID(existObStpTrans[k].getReferenceId());
                            String insPolicySeq = (String) aList.get(0);
                            addHdrField.put(MSG_INS_POLICY_SEQ_FIELD, insPolicySeq);

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
