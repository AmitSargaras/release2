package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Mar 9, 2008
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class CustGrpIdentifierFormValidator {

    private static String LOGOBJ = CustGrpIdentifierFormValidator.class.getName();

    private static String MAXIMUM_ALLOWED_AMOUNT_15_STR = IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR;
    private static double MAXIMIUM_ALLOWED_AMOUNT_15 = IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15;
    private static String DEFAULT_CURRENCY = IGlobalConstant.DEFAULT_CURRENCY;
    private static int MIN_INT_NAME = 3;
    public static final String ERROR_PRECENTAGE_INVALID = "error.cust.relationship.percentages.invalid";

    public static ActionErrors validateInput(CustGrpIdentifierForm aForm, Locale locale) {
        ActionErrors errors = new ActionErrors();
        String errorCode = "";
        String regex = "[0-9]*";

        String event = aForm.getEvent();
//        System.out.println("CustGrpIdentifierFormValidator aForm event " + event);

        try {

            if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

                // validation of mgel  for maker 2
                if ((aForm.getEvent().equals(CustGrpIdentifierAction.EVENT_MAKER2_SUBMIT)
                        || aForm.getEvent().equals(CustGrpIdentifierAction.EVENT_MAKER2_SAVE)
                        || aForm.getEvent().equals(CustGrpIdentifierAction.EVENT_PREPARE2))) {

                    if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getDescription(), false)).equals(Validator.ERROR_NONE)) {
                        errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
                        DefaultLogger.debug(LOGOBJ, "aForm.getDescription() = " + aForm.getDescription());
                    }
                }

                // validation of group  for maker
                if (aForm.getEvent().equals("submit")
                        || aForm.getEvent().equals("save")
                        || aForm.getEvent().equals("update")) {

                    if (!(errorCode = Validator.checkString(aForm.getGroupName(), true, 5, 100)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupName", new ActionMessage("error.string.valid.groupName"));
                        DefaultLogger.debug(" ERROR occured , groupName is  mandatory with 5 min char", "");
                    }
                    if (!(errorCode = Validator.checkString(aForm.getMasterGroupInd(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("masterGroupInd", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, "masterGroupInd = " + aForm.getMasterGroupInd());
                    } else {
                        if ("N".equals(aForm.getMasterGroupInd())) {
                            if (isSubGroupExist(aForm)) {
                                errors.add("masterGroupInd", new ActionMessage("error.group.notmastergroup"));
                                DefaultLogger.debug(LOGOBJ, " masterGroupInd = " + aForm.getMasterGroupInd());
                            }
                        }
                    }

                    validateGroupMember(aForm, locale, errors);

//                     if (!(errorCode = Validator.checkString(aForm.getGroupAccountMgrID(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
//                        errors.add("groupAccountMgrID", new ActionMessage("error.string.mandatory"));
//                       DefaultLogger.debug(LOGOBJ, " ERROR occured , groupAccountMgrID is  mandatory");
//
//                     }

                    if (!(errorCode = Validator.checkString(aForm.getGroupTypeCD(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupTypeCD", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " ERROR occured , groupTypeCD is  mandatory");

                    }

                    if (!(errorCode = Validator.checkString(aForm.getCountyCD(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("countyCD", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " ERROR occured , countyCD is  mandatory");

                    }

                    if (!(errorCode = Validator.checkString(aForm.getGroupAccountMgrCode(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupAccountMgrCode", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " ERROR occured , groupAccountMgrCode is  mandatory");
                    }

                    if (!(errorCode = Validator.checkString(aForm.getAccountMgmtCD(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("accountMgmtCD", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " ERROR occured , accountMgmtCD is  mandatory");
                    }

                    if (!(errorCode = Validator.checkString(aForm.getBusinessUnit(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("businessUnit", new ActionMessage("error.string.mandatory", "1", "3"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getBusinessUnit()= " + aForm.getBusinessUnit());
                    }

                    if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getDescription(), false)).equals(Validator.ERROR_NONE)) {
                        errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
                        DefaultLogger.debug(LOGOBJ, "aForm.getDescription() = " + aForm.getDescription());
                    }

                }

                // validation of Group when adding sub Group
                if ("prepare".equals(aForm.getEvent())) {
                    if (!(errorCode = Validator.checkString(aForm.getGroupName(), true, 5, 100)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupName", new ActionMessage("error.string.valid.groupName"));
                        DefaultLogger.debug(" ERROR occured , groupName is  mandatory with 5 min char", "");
                    }
                    if (!(errorCode = Validator.checkString(aForm.getMasterGroupInd(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("masterGroupInd", new ActionMessage("error.string.mandatory"));
                        DefaultLogger.debug(LOGOBJ, "masterGroupInd = " + aForm.getMasterGroupInd());
                    } else {
                        if ("N".equals(aForm.getMasterGroupInd())) {
                            if (CustGroupUIHelper.GROUPGROUP.equals(aForm.getItemType())) {
                                errors.add("masterGroupInd", new ActionMessage("error.group.notmastergroup"));
                                DefaultLogger.debug(LOGOBJ, " masterGroupInd = " + aForm.getMasterGroupInd());
                            }
                        }
                    }

                    validateGroupMember(aForm, locale, errors);
                    
                }
            }

            // validation when searching group/Customer
            if ("list".equals(event)) {
                if ("1".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, MIN_INT_NAME, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("customerName", new ActionMessage("error.string.int_cciCustomername"));
//                        DefaultLogger.debug(LOGOBJ, " (List) CustomerName() must be " + MIN_INT_NAME + " min char  " + aForm.getCustomerName());
                    }
                } else if ("2".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getLeIDType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
                        errors.add("leIDType", new ActionMessage("error.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLeIDType() = " + aForm.getLeIDType());
                    }
                    if (!(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("legalID", new ActionMessage("error.string.legalid"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLegalID() = " + aForm.getLegalID());
                    }
                } else if ("3".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("idNO", new ActionMessage("error.string.idno"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getIdNO() = " + aForm.getIdNO());
                    }
                } else if ("4".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkNumber(aForm.getGrpNo(), true, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_17)).equals(Validator.ERROR_NONE)) {
                        errors.add("grpNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_17_STR));
                        DefaultLogger.debug(LOGOBJ, " aForm.getGrpNo() = " + aForm.getGrpNo());

                    } else if (aForm.getGrpNo().indexOf(".") != -1) {
                        errors.add("grpNo", new ActionMessage("error.amount.decimalnotallowed"));
                    } else if (!Validator.checkPattern(aForm.getGrpNo(), regex)) {
                		errors.add("grpNo", new ActionMessage("error.number.format"));
                	}
                } else if ("5".equals(aForm.getGobutton())) {
                    if (!(errorCode = Validator.checkString(aForm.getGroupName(), true, 5, 40)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupName", new ActionMessage("error.string.int_ccigroupName"));
                        DefaultLogger.debug(LOGOBJ, " aForm.groupName() = " + aForm.getGroupName());

                    }
                }
            }

            // added by Jitu for validation for remarks columns
            //     validation for remarks   for checker/checker2
            if (CustGrpIdentifierAction.CHECKER_APPROVE_EDIT_CGID.equals(aForm.getEvent())
                    || CustGrpIdentifierAction.CHECKER_REJECT_EDIT_CGID.equals(aForm.getEvent())
                    || CustGrpIdentifierAction.CHECKER2_APPROVE_EDIT_CGID.equals(aForm.getEvent())
                    || CustGrpIdentifierAction.CHECKER2_REJECT_EDIT_CGID.equals(aForm.getEvent())
                    ) {

                if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getDescription(), false)).equals(Validator.ERROR_NONE)) {
                    errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
                    DefaultLogger.debug(LOGOBJ, "aForm.getDescription() = " + aForm.getDescription());
                }
            }

            //just validate when BGEL Ind set to Y
            if (StringUtils.equals(aForm.getIsBGELInd(), "Y")) {
                if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGroupLmt())) {
                    if (!(errorCode = Validator.checkAmount(aForm.getGroupLmt(), false, 0, MAXIMIUM_ALLOWED_AMOUNT_15, DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
                        errors.add("groupLmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", MAXIMUM_ALLOWED_AMOUNT_15_STR));
                        DefaultLogger.debug(LOGOBJ, "aForm.getGroupLmt() =" + aForm.getGroupLmt());
                    }
                } else {
                    errors.add("groupLmt", new ActionMessage("error.amount.mandatory"));
                    DefaultLogger.debug(LOGOBJ, "groupLmt is mandatory");
                }

                if (AbstractCommonMapper.isEmptyOrNull(aForm.getCurrencyCD())) {
                    errors.add("currencyCD", new ActionMessage("error.string.mandatory"));
                    DefaultLogger.debug(" ERROR occured , currency is  mandatory", "");
                }

                if (AbstractCommonMapper.isEmptyOrNull(aForm.getInternalLmt())) {
                    errors.add("internalLmt", new ActionMessage("error.string.mandatory"));
                    DefaultLogger.debug(" ERROR occured , InternalLmt is  mandatory", "");
                }

                if (!(errorCode = Validator.checkDate(aForm.getLastReviewDt(), true, locale)).equals(Validator.ERROR_NONE)) {
                    errors.add("lastReviewDt", new ActionMessage("error.date.mandatory", "1", "256"));
                    DefaultLogger.debug(LOGOBJ, " Not valid date, getLastReviewDt() = " + aForm.getLastReviewDt());
                }
                
                if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getGroupRemarks(), false, 4000, 6)).equals(Validator.ERROR_NONE)) {
	                errors.add("groupRemarks", RemarksValidatorUtil.getErrorMessage(errorCode, 4000, 6));
	                DefaultLogger.debug(" ERROR occured , grpRemarks length has exceeded", "");
	            }
           
            }
            
            
            // validation when deleting items
            if (CustGrpIdentifierAction.EVENT_DELETE_ITEM.equals(event) || CustGrpIdentifierAction.EVENT_DELETE2_ITEM.equals(event)) {
            	String[] deleteItemId = aForm.getDeleteItem();
                
                String itemType = aForm.getItemType();

                if (deleteItemId == null || deleteItemId.length == 0) {
                	
                	if (CustGroupUIHelper.GROUPSUBLIMIT.equals(itemType)) {
                		errors.add("groupSubLimitListSelect", new ActionMessage("error.chk.del.records"));
                    }
                	else if (CustGroupUIHelper.GROUPOTRLIMIT.equals(itemType)) {
                		errors.add("groupOtherLimitListSelect", new ActionMessage("error.chk.del.records"));
                	}
                	else if (CustGroupUIHelper.GROUPCREDITGRADE.equals(itemType)) {
                		errors.add("groupGroupCreditGradeListSelect", new ActionMessage("error.chk.del.records"));
                	}
                	else if (CustGroupUIHelper.GROUPMEMBER.equals(itemType)) {
                		errors.add("groupGroupMembersListSelect", new ActionMessage("error.chk.del.records"));
                	}
                	
                }
            }
            
        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, ", No of Errors..." + errors.size());

        return errors;

    }

    //Validation for Group Memeber List
    private static void validateGroupMember(CustGrpIdentifierForm aForm, Locale locale, ActionErrors errors) {
    
        String[] deleteItemId = aForm.getDeleteItem();
        List deleteItemListIdList = new ArrayList();

        if (deleteItemId != null && deleteItemId.length > 0) {
            deleteItemListIdList = Arrays.asList((String[]) deleteItemId);
            DefaultLogger.debug(LOGOBJ, ">>>>>>>>>>>>>> deleteItemListIdList : " + deleteItemListIdList);
        }

        String[] percentOwned = aForm.getPercentOwned();
        String errorCode = null;

        if (percentOwned != null && percentOwned.length > 0) {
            for (int i = 0; i < percentOwned.length; i++) {
                if (!(deleteItemListIdList != null && deleteItemListIdList.contains(i + ""))) {

                    //Modified this code to proper message   (min 0, max 100  with 2 decimal)
                    String errMsg = Validator.checkNumber(percentOwned[i], false, 0, 100, 3, locale);
                    if (!errMsg.equals(Validator.ERROR_NONE)) {
                        DefaultLogger.debug("errMessage is ", "" + errMsg);
                        if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
                            errors.add("percentOwned" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "100"));
                        } else if (errMsg.equals("decimalexceeded")) {
                            errors.add("percentOwned" + i, new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
                        } else {
                            errors.add("percentOwned" + i, new ActionMessage("error.number." + errMsg));
                        }
                    }
                    // Validation is  working but message is wrong id we enter 1.222
                    /* if (!(errorCode = Validator.checkNumber(percentOwned[i], false, 1, 100, 3, locale)).equals(Validator.ERROR_NONE)){
                        //errors.add("percentOwned" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", 100+""));
                        //if (!(errorCode = Validator.checkAmount(percentOwned[i], false, 0, 100, DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)){
                      //  errors.add("percentOwned" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", 100 + ""));
                     //   errors.add("groupLmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
                        DefaultLogger.debug(LOGOBJ, " percentOwned[" + i + "] = " + percentOwned[i]);
                    }*/
                }
            }
        }


        String masterGroupEntityID = aForm.getMasterGroupEntityID();

        String[] entityIDs = aForm.getEntityID();
        String[] relationNames = aForm.getRelationName();

        if (relationNames != null && relationNames.length > 0) {
            for (int i = 0; i < relationNames.length; i++) {
                if (!(deleteItemListIdList != null && deleteItemListIdList.contains(i + ""))) {
                    String relationName = relationNames[i];
                    String entityID = entityIDs[i];
                    DefaultLogger.debug(LOGOBJ, "masterGroupEntityID is  " + masterGroupEntityID);
                    DefaultLogger.debug(LOGOBJ, "entityID is  " + entityID);
                    if (trimToUpperCase(entityID).equals(trimToUpperCase(masterGroupEntityID))) {
                        //no need to check this because the group have no chance to add itselt because the search result will not
                        //include a master group.
                        //errors.add("groupName"+ i, new ActionMessage("error.group.ownEntityID"));
                        //DefaultLogger.debug(LOGOBJ, " Added to Itself Group Name = " + relationName);
                    } else {
                        if (!(errorCode = Validator.checkString(relationName, true, 0, 40)).equals(Validator.ERROR_NONE)) {
                            errors.add("relationName" + i, new ActionMessage("error.string.mandatory"));
                            DefaultLogger.debug(LOGOBJ, " aForm.relationName() = " + relationName);

                        }
                    }


                }
            }
        }

    }


    /**
     * @param aForm
     * @return true
     */
    private static boolean isSubGroupExist(CustGrpIdentifierForm aForm) {
        String[] deleteItemId = aForm.getDeleteItem();
        List deleteItemListIdList = new ArrayList();
        if (deleteItemId != null && deleteItemId.length > 0) {
            deleteItemListIdList = Arrays.asList((String[]) deleteItemId);
        }

        String[] entityTypes = aForm.getEntityType();
        String[] relationNames = aForm.getRelationName();

        if (relationNames != null && relationNames.length > 0) {
            for (int i = 0; i < relationNames.length; i++) {
                if (!(deleteItemListIdList != null && deleteItemListIdList.contains(i + ""))) {
                    String entityType = entityTypes[i];
                    if (CustGroupUIHelper.ENTITY_TYPE_GROUP.equals(entityType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * @param str
     * @return string
     */

    private static String trimToUpperCase(String str) {
        if (!AbstractCommonMapper.isEmptyOrNull(str)) {
            return str.trim().toUpperCase();
        } else {
            return "";
        }
    }


}
