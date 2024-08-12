package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBFacilityIncremental;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.IStpTransType;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Facility incremental reduction delete Stp message
 *
 * @author Andy Wong
 * @since Sep 29, 2009
 */
public class StpFacIncrementReductionDeletionCmd extends AbstractStpCommand implements IStpTransType, IStpConstants {
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        ctx.put(FIELD_VAL_MAP, fieldValMap);
        ILimit limit = getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());
        ILimitProfile limitProfile = getActualLimitBusManager().getLimitProfile(limit.getLimitProfileID());

        //only stp when application type is corporate
        if (limitProfile.getApplicationType().equals(ICMSConstant.APPLICATION_TYPE_CO)
                || limitProfile.getApplicationType().equals(ICMSConstant.APPLICATION_TYPE_CO_HP)) {
            // entire facility to be deleted
            if (StringUtils.equals(obFacilityTrxValue.getStatus(), ICMSConstant.STATE_LOADING_DELETE)) {
                for (Iterator iterator = obFacilityMaster.getFacilityIncrementals().iterator(); iterator.hasNext();) {
                    OBFacilityIncremental facilityIncremental = (OBFacilityIncremental) iterator.next();
                    ctx.put(REF_NUM, facilityIncremental.getCmsReferenceId());
                    ctx.put(STP_TRANS_MAP, stpTransMap);
                    ctx.put(IS_DELETE, new Boolean(true));
                    OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                    // StpTrans OB will be null if delete trx had been succeed
                    if (obStpTrans != null) {
                        //put the OB into an array of object
                        ArrayList aObject = new ArrayList();
                        aObject.add(limit);
                        aObject.add(fieldValMap);
                        aObject.add(facilityIncremental);

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
                //Filter out deleted facility incremental
//            obFacilityMaster.setFacilityRelationshipSet(FacilityUtil.filterDeletedRelationship(obFacilityMaster.getFacilityRelationshipSet()));
                for (Iterator iterator = stpTransMap.values().iterator(); iterator.hasNext();) {
                    OBStpTrans obStpTrans = (OBStpTrans) iterator.next();
                    boolean deleteInd = true;
                    // Andy Wong, Aug 7, 2009: not to fire delete message when previous create message failed
                    if (StringUtils.equals(obStpTrans.getTrxType(), TRX_TYPE_FAC_INCREMENT_REDUCT_CREATE) && TRX_SUCCESS.equals(obStpTrans.getStatus())) {
                        if (obFacilityMaster.getFacilityIncrementals() != null && obFacilityMaster.getFacilityIncrementals().size() > 0) {
                            for (Iterator iterator1 = obFacilityMaster.getFacilityIncrementals().iterator(); iterator1.hasNext();) {
                                OBFacilityIncremental facilityIncremental = (OBFacilityIncremental) iterator1.next();
                                if (obStpTrans.getReferenceId().equals(facilityIncremental.getCmsReferenceId())) {
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
                                //put the OB into an array of object
                                ArrayList aObject = new ArrayList();
                                aObject.add(limit);
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
                            }
                            ctx.remove(IS_DELETE);  // reset IS_DELETE
                        }
                    }
                }
            }
        }
        return false;
    }
}