package com.integrosys.cms.ui.tatdoc;

import java.text.SimpleDateFormat;
import java.util.*;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generateli.bus.OBLiDoc;
import com.integrosys.cms.app.generateli.proxy.GenerateLiDocProxyManagerFactory;
import com.integrosys.cms.app.generateli.proxy.IGenerateLiDocProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.MaintainTatDurationUtil;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.systemparameters.propertyindex.PropertyIdxUIHelper;

public class ReadTatDocCommand extends TatDocCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
                {IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE},
                {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
//				{ "tatParamStageTrackingString", "java.lang.String", REQUEST_SCOPE},
                {TatDocForm.MAPPER, "com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack", FORM_SCOPE},
                {"isStartAction", "java.lang.String", REQUEST_SCOPE},
//                {"activeTatParamItemId", "java.lang.Long", REQUEST_SCOPE},
//                {"workingDaysMap", "java.util.Map", GLOBAL_SCOPE},
//                {CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE},
//                {"trxId", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
//                {TatDocForm.MAPPER, "com.integrosys.cms.app.tatdoc.bus.ITatDoc", FORM_SCOPE},
//                {"tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE},
                {"customerID", "java.lang.String", REQUEST_SCOPE},
                {"tatFormMapper", "java.util.HashMap", SERVICE_SCOPE},
                {"limitProfileID", "java.lang.String", REQUEST_SCOPE},
                {"stagePreDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stageDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stagePostDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"reasonValList", "java.util.Collection", REQUEST_SCOPE},
//                {"workingDaysMap", "java.util.Map", GLOBAL_SCOPE},
//                {"canFlowToDisbursementStage", "java.lang.Boolean", SERVICE_SCOPE},
//                {"canFlowToPostDisbursementStage", "java.lang.Boolean", SERVICE_SCOPE},
                {TatDocForm.MAPPER, "com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        HashMap tatFormMapper = new HashMap();

        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

//        String trxId = (String) map.get("trxId");
//        customer.getCCCStatus();
//
        ITatDocProxy proxy = getTatDocProxy();
//        ITatDocTrxValue tatDocTrxValue = null;
//        String solicitorDate = null;
//        boolean canFlowToDisbursementStage = false;
//        boolean canFlowToPostDisbursementStage = false;
//
//        if (trxId != null) {
//            tatDocTrxValue = proxy.getTatDocTrxValueByTrxID(trxId);
//        } else {
//            tatDocTrxValue = proxy.getTrxValueByLimitProfileID(limit.getLimitProfileID());
//            if (tatDocTrxValue == null) {
//                Date date = proxy.getDateOfInstructionToSolicitor(limit.getLimitProfileID());
//
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
//                solicitorDate = date != null ? sdf.format(date) : null;
//            }
//        }
//
//        if (tatDocTrxValue != null && tatDocTrxValue.getTatDoc() != null) {
//            ITatDoc actualTatDoc = tatDocTrxValue.getTatDoc();
//			DefaultLogger.debug(this, "actualTatDoc is not null!");
//            canFlowToDisbursementStage = (actualTatDoc.getDocPAExcuteDate() != null);
//            canFlowToPostDisbursementStage = (actualTatDoc.getDisbursementDocCompletionDate() != null)
//                    && (actualTatDoc.getDisbursementDate() != null);
//        }
//
        String event = (String) map.get("event");
//        ITatDoc getTatDoc = null;
//        if (tatDocTrxValue != null) {
//            if (TatDocAction.EVENT_CHECKER_READ.equals(event) || TatDocAction.EVENT_READ_EDIT_TODO.equals(event)
//                    || TatDocAction.EVENT_READ_CLOSE.equals(event) || TatDocAction.EVENT_VIEW.equals(event)
//                    || TatDocAction.EVENT_VIEW_CHECKER.equals(event)) {
//                getTatDoc = tatDocTrxValue.getStagingTatDoc();
//            } else {
//                getTatDoc = tatDocTrxValue.getTatDoc();
//            }
//        }
//
//		DefaultLogger.debug(this, ">>>>>> canFlowToDisbursementStage = " + canFlowToDisbursementStage);
//		DefaultLogger.debug(this, ">>>>>> canFlowToPostDisbursementStage = " + canFlowToPostDisbursementStage);

//        tatFormMapper.put("tatDocTrxValue", tatDocTrxValue);
        tatFormMapper.put("limitProfileID", String.valueOf(limit.getLimitProfileID()));
        tatFormMapper.put("customerID", customer.getCMSLegalEntity().getLEReference());
        tatFormMapper.put("customerName", customer.getCustomerName());
//        tatFormMapper.put("solicitorDate", solicitorDate);
//        result.put("tatDocTrxValue", tatDocTrxValue);
//        result.put("customerID", String.valueOf(customer.getCustomerID()));
//        result.put("limitProfileID", String.valueOf(limit.getLimitProfileID()));
//        result.put("canFlowToDisbursementStage", new Boolean(canFlowToDisbursementStage));
//        result.put("canFlowToPostDisbursementStage", new Boolean(canFlowToPostDisbursementStage));
//        result.put(TatDocForm.MAPPER, getTatDoc);


        //paramItemList
        OBTatLimitTrack trackOB = proxy.getTatStageTrackingListByLimitProfileId(limit.getLimitProfileID(), limit.getApplicationType());

        // Processing the list
//        List paramItemList = new ArrayList(tatParam.getTatParamItemList());
        List preDibursment = new ArrayList(), dibursment = new ArrayList(), postDibursment = new ArrayList();
        OBTatLimitTrack mapperTrackOB = (OBTatLimitTrack) map.get(TatDocForm.MAPPER);
        String isStartAction = (String) map.get("isStartAction");
        String branch = limit.getOriginatingLocation().getOrganisationCode();

        if (TatDocAction.EVENT_UPDATE_DATE.equals(event)) {
            long activeSequence = 0;

            String preDisbursementRemarks = mapperTrackOB.getPreDisbursementRemarks();
            String disbursementRemarks = mapperTrackOB.getDisbursementRemarks();
            String postDisbursementRemarks = mapperTrackOB.getPostDisbursementRemarks();

            Set mapperStageListSet = mapperTrackOB.getStageListSet();

            for (Iterator mapperStageItr = mapperStageListSet.iterator(); mapperStageItr.hasNext();) {
                OBTatLimitTrackStage mapperTrackStageOB = (OBTatLimitTrackStage) mapperStageItr.next();

                String trackingStageId = mapperTrackStageOB.getTatTrackingStageId() + "";
                String tatParamItemId = mapperTrackStageOB.getTatParamItemId() + "";
                String startDateString = mapperTrackStageOB.getStartDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getStartDate());
                String endDateString = mapperTrackStageOB.getEndDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getEndDate());
                String actualDateString = mapperTrackStageOB.getActualDate() == null ? "null" : MaintainTatDurationUtil.getFormattedDate(mapperTrackStageOB.getActualDate());
                String tatApplicable = mapperTrackStageOB.getTatApplicable();
                String reason = mapperTrackStageOB.getReasonExceeding();

                Set _stageListSet = trackOB.getStageListSet();

                for (Iterator _stageItr = _stageListSet.iterator(); _stageItr.hasNext();) {
                    OBTatLimitTrackStage _trackStageOB = (OBTatLimitTrackStage) _stageItr.next();

                    if (String.valueOf(_trackStageOB.getTatParamItemId()).equals(tatParamItemId)) {
                        if (_trackStageOB.isStageActive())
                            activeSequence = _trackStageOB.getTatParamItem().getSequenceOrder();

                        _trackStageOB.setStartDate(MaintainTatDurationUtil.getStringToDate(startDateString));
                        _trackStageOB.setEndDate(MaintainTatDurationUtil.getStringToDate(endDateString));
                        _trackStageOB.setActualDate(MaintainTatDurationUtil.getStringToDate(actualDateString));
                        _trackStageOB.setReasonExceeding(reason);
                        _trackStageOB.setTatApplicable(tatApplicable);

                        if (_trackStageOB.getTatParamItem().getSequenceOrder() == activeSequence)
                            _trackStageOB.setStageActive("Y");
                        else
                            _trackStageOB.setStageActive("N");
                    }
                }
            }

            Calendar curTime = Calendar.getInstance();
//            Map workingDaysMap = (Map) map.get("workingDaysMap");
//            if (workingDaysMap == null)
//                workingDaysMap = proxy.getWorkingDayMap(branch, curTime.get(Calendar.YEAR));

            Set stageListSet = trackOB.getStageListSet();
            for (Iterator stageItr = stageListSet.iterator(); stageItr.hasNext();) {
                OBTatLimitTrackStage trackStageOB = (OBTatLimitTrackStage) stageItr.next();

                if (trackStageOB.getTatParamItem().getSequenceOrder() >= activeSequence) {
                    // if the user input is Start button
                    if ("Y".equals(isStartAction)) {
                        trackStageOB.setStartDate(curTime.getTime());
                        double duration = trackStageOB.getTatParamItem().getDuration();
                        String type = trackStageOB.getTatParamItem().getDurationType();
                        Date nextDate = MaintainTatDurationUtil.getDurationDate(proxy, branch, trackStageOB.getStartDate(), duration, type);
                        curTime.setTime(nextDate);
                        trackStageOB.setEndDate(curTime.getTime());
                        trackStageOB.setActualDate(null);
                    }
                    // if the user input is End button
                    else {
                        trackStageOB.setActualDate(curTime.getTime());
                        isStartAction = "Y";
                    }
                }
            }

//			result.put("workingDaysMap", workingDaysMap);
        }

        Set stageListSet = trackOB.getStageListSet();
        for (Iterator itr = stageListSet.iterator(); itr.hasNext();) {
            OBTatLimitTrackStage trackStageOB = (OBTatLimitTrackStage) itr.next();
            OBTatParamItem item = trackStageOB.getTatParamItem();
            if ("1".equals(item.getStageType())) {
                // If ActualDate is empty, and application type & sequence matches apps.gen.li.auto.endDate in documentation.properties
                // Sets the ActualDate to the latest LI document generated date (for staging only)
                if (trackStageOB.getActualDate() == null &&
                  PropertiesConstantHelper.isGenerateLIApplicable(limit.getApplicationType(), item.getSequenceOrder())) {

                    Date latestGenerateDate = null;
                    IGenerateLiDocProxyManager docProxy = GenerateLiDocProxyManagerFactory.getProxyManager();
                    List LiDocList = docProxy.getAllLiDocList();
                    Iterator LiDocListItr = LiDocList.iterator();
                    while (LiDocListItr.hasNext()) {
                        if (latestGenerateDate == null) {
                            latestGenerateDate = ((OBLiDoc)LiDocListItr.next()).getLastGenDate();
                        } else {
                            // Get the latest Generate LI date
                            OBLiDoc nextOBLiDoc = (OBLiDoc)LiDocListItr.next();
                            latestGenerateDate = getLaterDate(latestGenerateDate, nextOBLiDoc.getLastGenDate());
                        }
                    }

                    // only when LI Generate Date exists...
                    if (latestGenerateDate != null) {
                        trackStageOB.setActualDate(latestGenerateDate);

                        // Sync start date with end date if start date does not exist
                        if (trackStageOB.getStartDate() == null) {
                            trackStageOB.setStartDate(trackStageOB.getActualDate());

                            // Calculate Forcasted End Date
                            Calendar curTime = Calendar.getInstance();
                            double duration = trackStageOB.getTatParamItem().getDuration();
                            String type = trackStageOB.getTatParamItem().getDurationType();
                            Date nextDate = MaintainTatDurationUtil.getDurationDate(proxy, branch, trackStageOB.getStartDate(), duration, type);
                            curTime.setTime(nextDate);
                            trackStageOB.setEndDate(curTime.getTime());

                        } else if (trackStageOB.getActualDate() != getLaterDate(trackStageOB.getStartDate(),trackStageOB.getActualDate()) ){
                            // make sure generated end date is not earlier than start date
                            trackStageOB.setActualDate(trackStageOB.getStartDate());
                        }
                    }
                }
                preDibursment.add(trackStageOB);
            }
            else if ("2".equals(item.getStageType()))
                dibursment.add(trackStageOB);
            else if ("3".equals(item.getStageType()))
                postDibursment.add(trackStageOB);
        }

        result.put("stagePreDibursmentList", preDibursment);
        result.put("stageDibursmentList", dibursment);
        result.put("stagePostDibursmentList", postDibursment);
        result.put(TatDocForm.MAPPER, trackOB);

        CommonCodeList commonCode = CommonCodeList.getInstance("REASON_NON_COMP_TAT");
        List optionList = commonCode.getOptionList();
        result.put("reasonValList", commonCode.getOptionList());

        DefaultLogger.debug(this, "Going out of doExecute()");

        result.put("tatFormMapper", tatFormMapper);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    private Date getLaterDate(Date dateOne, Date dateTwo) {
        Calendar calOne = Calendar.getInstance();
        calOne.setTime(dateOne);
        Calendar calTwo = Calendar.getInstance();
        calTwo.setTime(dateTwo);
        if (calOne.before(calTwo)) {
            return dateTwo;
        } else {
            return dateOne;
        }
    }
}
