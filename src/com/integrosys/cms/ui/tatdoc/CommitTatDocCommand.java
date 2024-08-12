package com.integrosys.cms.ui.tatdoc;

import java.util.*;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.MaintainTatDurationUtil;
import com.integrosys.cms.app.tatdoc.bus.TatDocReplicationUtils;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatduration.bus.ITatParamItem;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class CommitTatDocCommand extends TatDocCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
//				{ TatDocForm.MAPPER, "com.integrosys.cms.app.tatdoc.bus.OBTatDoc", FORM_SCOPE },
                {TatDocForm.MAPPER, "com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack", FORM_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
//				{"tatParamStageTrackingString", "java.lang.String", REQUEST_SCOPE},
//                {"tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE},
//                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
        };
    }

    public String[][] getResultDescriptor() {
        return new String[][]{
//                {"request.ITrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", REQUEST_SCOPE},
//                {"tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE},
        };
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

//		ITatDoc tatDoc = (ITatDoc) map.get(TatDocForm.MAPPER);
        OBTatLimitTrack mapperTrackOB = (OBTatLimitTrack) map.get(TatDocForm.MAPPER);
//        ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
//        ITatDocTrxValue trxValue = (ITatDocTrxValue) map.get("tatDocTrxValue");

        ITatDocProxy proxy = getTatDocProxy();

        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

        //this might coming from the store table [CMS_TAT_TRACK or from template for first time]
        OBTatLimitTrack trackOB = proxy.getTatStageTrackingListByLimitProfileId(limit.getLimitProfileID(), limit.getApplicationType());

        HashMap tatTrackingStageMap = new HashMap();
        Set stageListSet = trackOB.getStageListSet();
        for (Iterator itr = stageListSet.iterator(); itr.hasNext();) {
            OBTatLimitTrackStage trackStageOB = (OBTatLimitTrackStage) itr.next();
            tatTrackingStageMap.put(trackStageOB.getTatParamItemId() + "", trackStageOB);
        }

        long activeSequence = 0;

        String preDisbursementRemarks = mapperTrackOB.getPreDisbursementRemarks();
        String disbursementRemarks = mapperTrackOB.getDisbursementRemarks();
        String postDisbursementRemarks = mapperTrackOB.getPostDisbursementRemarks();

        trackOB.setPreDisbursementRemarks(preDisbursementRemarks);
        trackOB.setDisbursementRemarks(disbursementRemarks);
        trackOB.setPostDisbursementRemarks(postDisbursementRemarks);
        
        Set mapperStageListSet = mapperTrackOB.getStageListSet();

        Set newStageListSet = new LinkedHashSet();
        for (Iterator mapperStageItr = mapperStageListSet.iterator(); mapperStageItr.hasNext();) {
            OBTatLimitTrackStage mapperTrackStageOB = (OBTatLimitTrackStage) mapperStageItr.next();

            String trackingStageId = mapperTrackStageOB.getTatTrackingStageId() + "";
            String tatParamItemId = mapperTrackStageOB.getTatParamItemId() + "";
            String startDateString = mapperTrackStageOB.getStartDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getStartDate());
            String endDateString = mapperTrackStageOB.getEndDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getEndDate());
            String actualDateString = mapperTrackStageOB.getActualDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getActualDate());
            String tatApplicable = mapperTrackStageOB.getTatApplicable();
            String reason = mapperTrackStageOB.getReasonExceeding();

            OBTatLimitTrackStage _trackStageOB = (OBTatLimitTrackStage) tatTrackingStageMap.get(tatParamItemId);

            _trackStageOB.setStartDate(MaintainTatDurationUtil.getStringToDate(startDateString));
            _trackStageOB.setEndDate(MaintainTatDurationUtil.getStringToDate(endDateString));
            _trackStageOB.setActualDate(MaintainTatDurationUtil.getStringToDate(actualDateString));
            _trackStageOB.setReasonExceeding(reason);
            _trackStageOB.setTatApplicable(tatApplicable);

            //we need checking here to determine the stage active
            if (_trackStageOB.isStageActive()) {
                //from here we need to determine whether it should jump to next stage
                if (tatApplicable.equals("N") || (!startDateString.equals("null") && !actualDateString.equals("null"))) {
                    //if user set current stage not applicable...jump
                    //if user click on start and end...jump
                    activeSequence = _trackStageOB.getTatParamItem().getSequenceOrder() + 1;
                    _trackStageOB.setStageActive("N");
                }
            } else {
                //from here we need to determine whether it should remain from previous stage
                if (activeSequence == 0) {
                    //remain
                } else {
                    if (_trackStageOB.getTatParamItem().getSequenceOrder() == activeSequence) {
                        _trackStageOB.setStageActive("Y");
                    }
                }
            }

            newStageListSet.add(_trackStageOB);
            //tatTrackingStageMap.put(tatParamItemId, _trackStageOB);
        }
        trackOB.setStageListSet(newStageListSet);

        proxy.commitTatTrackingList(trackOB);

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }

}
