package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.IMapper;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

import java.util.*;
import java.math.BigDecimal;


public class CustGrpIdentifierMapper extends AbstractCommonMapper implements IMapper, ICommonEventConstant {


    public CustGrpIdentifierMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"deleteItem", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "Inside mapFormToOB ");
        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) cForm;

//        ICustGrpIdentifier obj = (ICustGrpIdentifier) iobj;
        String event = aForm.getEvent();
        ICustGrpIdentifier obj = (ICustGrpIdentifier) map.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        debug("---------------------- Before -------------------------");
        debug("mapFormToOB...Deleted ItemType = " + aForm.getItemType());
        this.print("Before ", itrxValue);

        IGroupCreditGrade[] newGradeObjList = CustGrpIdentifierMapperHelper.getGroupCreditGrade(itrxValue, aForm);
        IGroupMember[] newGrpMemberList = CustGrpIdentifierMapperHelper.getGrpMember(itrxValue, aForm);

        Map subLimitMap = CustGrpIdentifierMapperHelper.getGrpSubLimit(itrxValue, aForm);
        IGroupSubLimit[] newGrpSubLimitList = (IGroupSubLimit[]) subLimitMap.get("limitList");

        Map othLimitMap = CustGrpIdentifierMapperHelper.getGrpOtrLimit(itrxValue, aForm);
        IGroupOtrLimit[] newGrpOtrLimitList = (IGroupOtrLimit[]) othLimitMap.get("limitList");
        
        if (itrxValue != null) {
            if (((Boolean)subLimitMap.get("errDelete")).booleanValue() || ((Boolean)othLimitMap.get("errDelete")).booleanValue()) {
                itrxValue.setHasDeleteErr(true);
                DefaultLogger.debug(this, "setHasDeleteErr="+itrxValue.isHasDeleteErr());
            }
        }

/*
        if (newGrpSubLimitList != null && newGrpSubLimitList.length > 0) {
            debug(" jjjjjjjjjjjjjjjjjjjj newGrpSubLimitList " + newGrpSubLimitList.length);
        } else {
            debug(" jjjjjjjjjjjjjjjjjjjj newGrpSubLimitList = null");
        }
*/

        if (obj == null) {
            obj = new OBCustGrpIdentifier();
            //debug("Created New OBCustGrpIdentifier()");
        }

        try {
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGrpNo())) {
                obj.setGrpNo(Long.valueOf(aForm.getGrpNo()).longValue());
            }

            // Used to check the duplicate entity id only.. Not persistence
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getMasterGroupEntityID())) {
                obj.setMasterGroupEntityID(Long.valueOf(aForm.getMasterGroupEntityID()).longValue());
            }

            obj.setGroupName(aForm.getGroupName());
            obj.setGroupType(aForm.getGroupTypeCD());
            obj.setAccountMgmt(trim(aForm.getAccountMgmtCD()));
            obj.setGroupCounty(trim(aForm.getCountyCD()));
            obj.setGroupCurrency(trim(aForm.getCurrencyCD()));
            obj.setBusinessUnit(trim(aForm.getBusinessUnit()));
            obj.setGroupAccountMgrCode(trim(aForm.getGroupAccountMgrCode()));
            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupAccountMgrID())) {
                obj.setGroupAccountMgrID(Long.valueOf(aForm.getGroupAccountMgrID()).longValue());
            }
            obj.setApprovedBy(trim(aForm.getApprovedBy()));
            obj.setGroupRemarks(aForm.getGroupRemarks());
            if ("Y".equals(aForm.getMasterGroupInd())) {
                obj.setMasterGroupInd(true);
            }

            if ("Y".equals(aForm.getIsBGELInd())) {
                obj.setIsBGEL(true);
            }

            obj.setInternalLmt(aForm.getInternalLmt());

            try {
                if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupLmt())
                        && !AbstractCommonMapper.isEmptyOrNull(aForm.getCurrencyCD())) {
//                    obj.setGroupLmt(CurrencyManager.convertToAmount(locale, aForm.getCurrencyCD(), aForm.getGroupLmt()));
                    //String lmt = aForm.getGroupLmt().replaceAll(",","");
                    String lmt = aForm.getGroupLmt();
                    int lc = lmt.indexOf(",");
                    while (lc != -1) {
                        String left = lmt.substring(0,lc);
                        String right = lmt.substring(lc+1);
                        lmt = left+right;
                        lc = lmt.indexOf(",");
                    }
                    obj.setGroupLmt(new Amount(new BigDecimal(lmt), new CurrencyCode(aForm.getCurrencyCD())));
                } else {
                    obj.setGroupLmt(null);
                }
            } catch (Exception e) {e.printStackTrace();}

            if (CustGroupUIHelper.DELETED.equals(aForm.getStatus())) {
                obj.setStatus(CustGroupUIHelper.DELETED);
            } else {
                obj.setStatus(CustGroupUIHelper.ACTIVE);
            }

            obj.setLastReviewDt(UIUtil.mapFormString_OBDate(locale, obj.getLastReviewDt(), aForm.getLastReviewDt()));
            obj.setGroupCreditGrade(newGradeObjList);
            obj.setGroupSubLimit(newGrpSubLimitList);
            obj.setGroupMember(newGrpMemberList);
            obj.setGroupOtrLimit(newGrpOtrLimitList);

            CustGroupUIHelper.printChildMembers("Staging ", obj);

            //itrxValue.setStagingCustGrpIdentifier(obj);

            this.print("After ", itrxValue);
            debug("---------------------- After -------------------------");

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " CustGrpIdentifierMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        OBCustGrpIdentifier oo = (OBCustGrpIdentifier) obj;
        //debug("Going out of  mapFormToOB \n\n " + oo.toString() + "\n\n");
        return obj;
    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOBToForm ");
        CustGrpIdentifierForm aForm = (CustGrpIdentifierForm) cForm;
        ICustGrpIdentifier iObj = (ICustGrpIdentifier) obj;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        //debug("locale = " + locale);

        try {

            aForm.setGrpNo(String.valueOf(iObj.getGrpNo()));

            // Used to check the duplicate entity id only.. Not persistence
            aForm.setMasterGroupEntityID(String.valueOf(iObj.getMasterGroupEntityID()));

            aForm.setGrpIDRef(String.valueOf(iObj.getGrpIDRef()));
            aForm.setGroupName(iObj.getGroupName());
            aForm.setGroupTypeCD(trim(iObj.getGroupType()));
            aForm.setAccountMgmtCD(iObj.getAccountMgmt());
            aForm.setCountyCD(trim(iObj.getGroupCounty()));
            aForm.setCurrencyCD(trim(iObj.getGroupCurrency()));
            aForm.setBusinessUnit(trim(iObj.getBusinessUnit()));

            aForm.setGroupAccountMgrID(trim(String.valueOf(iObj.getGroupAccountMgrID())));
            aForm.setGroupAccountMgrName(CustGrpIdentifierUIHelper.getGroupAccountMgrName(iObj.getGroupAccountMgrID()));

            aForm.setApprovedBy(trim(iObj.getApprovedBy()));
            aForm.setGroupRemarks(iObj.getGroupRemarks());
            aForm.setMasterGroupInd(iObj.getMasterGroupInd() ? "Y" : "N");
            aForm.setIsBGELInd(iObj.getIsBGEL() ? "Y" : "N");
            aForm.setGroupAccountMgrCode(trim(String.valueOf(iObj.getGroupAccountMgrCode())));
            aForm.setInternalLmt(iObj.getInternalLmt());
            aForm.setGroupLmt(UIUtil.mapAmountToString(locale, iObj.getGroupLmt()));

            if (CustGroupUIHelper.DELETED.equals(iObj.getStatus())) {
                aForm.setStatus(CustGroupUIHelper.DELETED);
            } else {
                aForm.setStatus(CustGroupUIHelper.ACTIVE);
            }

            aForm.setLastReviewDt(UIUtil.mapOBDate_FormString(locale, iObj.getLastReviewDt()));

            aForm.setDeleteItem(new String[0]);

            CustGrpIdentifierMapperHelper.setMemberListOBtoForm(aForm, iObj.getGroupMember());

        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.debug(this + " CustGrpIdentifierMapper", "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }
        // debug("Going out of  mapOBToForm \n\n " + aForm.toString() + "\n\n");

        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }


    private void print(String msg, ICustGrpIdentifierTrxValue itrxValue) {
        ICustGrpIdentifier stagingObj = null;
        if (itrxValue != null) {
            stagingObj = itrxValue.getStagingCustGrpIdentifier();
            if (stagingObj != null) {

                IGroupCreditGrade[] gradeList = stagingObj.getGroupCreditGrade();
                if (gradeList != null && gradeList.length > 0) {
                    debug(msg + " gradeList " + gradeList.length);
                } else {
                    debug(msg + " gradeList = null");
                }

                IGroupSubLimit[] grpSubLimitList = stagingObj.getGroupSubLimit();
                if (grpSubLimitList != null && grpSubLimitList.length > 0) {
                    debug(msg + "grpSubLimitList " + grpSubLimitList.length);
                } else {
                    debug(msg + "grpSubLimitList = null");
                }

                IGroupMember[] grpMemberList = stagingObj.getGroupMember();
                if (grpMemberList != null && grpMemberList.length > 0) {
                    debug(msg + "grpMemberList " + grpMemberList.length);
                } else {
                    debug(msg + "grpMemberList = null");
                }

                IGroupOtrLimit[] grpOtrLimitList = stagingObj.getGroupOtrLimit();
                if (grpOtrLimitList != null && grpOtrLimitList.length > 0) {
                    debug(msg + "grpOtherLimitList " + grpOtrLimitList.length);
                } else {
                    debug(msg + "grpOtherLimitList = null");
                }

            } else {
                debug(msg + "profile IS  NULL");
            }
        } else {
            debug(msg + " itrxValue IS  NULL");
        }


    }

    private void debug(String msg) {
    	DefaultLogger.debug(this,"CustGrpIdentifierMapper,  " + msg);
    }


    private String trim(String code) {
        if (code != null && !code.equals("")) {
            code = code.trim();
        }
        return code;
    }


}
