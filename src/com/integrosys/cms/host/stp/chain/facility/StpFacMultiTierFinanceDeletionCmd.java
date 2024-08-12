package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sai Heng
 * Date: Sep 4, 2008
 * Time: 12:26:21 PM
 * To change this template use File | Settings | File Templates.
 *
 * @author Andy Wong
 * @since 19 Sept 2008
 */
public class StpFacMultiTierFinanceDeletionCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        ctx.put(FIELD_VAL_MAP, fieldValMap);
        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

        // entire facility to be deleted
        if (StringUtils.equals(obFacilityTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
            for (Iterator iterator = obFacilityMaster.getFacilityMultiTierFinancingSet().iterator(); iterator.hasNext();) {
                OBFacilityMultiTierFinancing obFacilityMultiTierFinancing = (OBFacilityMultiTierFinancing) iterator.next();
                ctx.put(REF_NUM, obFacilityMultiTierFinancing.getCmsRefId().toString());
                ctx.put(STP_TRANS_MAP, stpTransMap);
                ctx.put(IS_DELETE, new Boolean(true));
                OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                // StpTrans OB will be null if delete trx had been succeed
                if (obStpTrans != null) {
                    //put the OB into an array of object
                    ArrayList aObject = new ArrayList();
                    aObject.add(obLimit);
                    aObject.add(fieldValMap);
                    aObject.add(obFacilityMultiTierFinancing);

                    //map to field OB
                    List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.FACILITY_PATH);
                    // construct msg, send msg and decipher msg
                    stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());
                    //put required OB in context to be used in subsequent command
                    ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                    ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);

                    //map object to biz OB and update STP transaction
                    boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                    if (!successUpd) {
                        return true;
                    }
                }
            }
        } else {
            //delete obsolete transaction
            //Filter out deleted facility multitier financing
            obFacilityMaster.setFacilityMultiTierFinancingSet(FacilityUtil.filterDeletedMultiTierFin(obFacilityMaster.getFacilityMultiTierFinancingSet()));
            for (Iterator iterator = stpTransMap.values().iterator(); iterator.hasNext();) {
                OBStpTrans obStpTrans = (OBStpTrans) iterator.next();
                boolean deleteInd = true;
                // Andy Wong, Aug 7, 2009: not to fire delete message when previous create message failed
                if (StringUtils.equals(obStpTrans.getTrxType(), TRX_TYPE_FAC_MULTI_TIER_FINANCE_CREATE) && TRX_SUCCESS.equals(obStpTrans.getStatus())) {
                    if (obFacilityMaster.getFacilityMultiTierFinancingSet() != null && obFacilityMaster.getFacilityMultiTierFinancingSet().size() > 0) {
                        for (Iterator iterator1 = obFacilityMaster.getFacilityMultiTierFinancingSet().iterator(); iterator1.hasNext();) {
                            IFacilityMultiTierFinancing tierFinancing = (IFacilityMultiTierFinancing) iterator1.next();
                            if (obStpTrans.getReferenceId().equals(tierFinancing.getCmsRefId())) {
                                deleteInd = false;
                                break;
                            }
                        }
                    }

                    if (deleteInd) {
                        ctx.put(REF_NUM, obStpTrans.getReferenceId().toString());
                        ctx.put(STP_TRANS_MAP, stpTransMap);
                        ctx.put(IS_DELETE, new Boolean(true));
                        obStpTrans = (OBStpTrans) initTransaction(ctx);

                        // StpTrans OB will be null if delete trx had been succeed
                        if (obStpTrans != null) {
                            List aList = getStpTransBusManager().getFacilityMultiTierFinanceByID(obStpTrans.getReferenceId());
                            String tierSeq = (String) aList.get(0);
                            fieldValMap.put("AMSEQN", tierSeq);

                            //put the OB into an array of object
                            ArrayList aObject = new ArrayList();
                            aObject.add(obLimit);
                            aObject.add(fieldValMap);

                            //map to field OB
                            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.FACILITY_PATH);
                            // construct msg, send msg and decipher msg
                            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());
                            //put required OB in context to be used in subsequent command
                            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                            ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);

                            //map object to biz OB and update STP transaction
                            boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                            if (!successUpd) {
                                return true;
                            }
//                    updateFacWithoutTrx(ctx, obFacilityTrxValue);
                        }
                        ctx.remove(IS_DELETE);  // reset IS_DELETE
                    }
                }
            }

        }
        return false;
    }
}