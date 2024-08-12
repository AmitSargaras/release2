package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeDao;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.Map;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 3, 2008
 * Time: 6:13:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBCommonCodeEntryWrapper extends OBCommonCodeEntry implements ICommonCodeWrapper, Serializable {

    public static final String REFERENCE_CODE_RLOS = "RLOS";

    private static final String[] MATCHING_PROPERTIES = new String[] { "entryCode" };
    private static final String[] REF_ENTRY_CODE_MATCHING_PROPERTIES = new String[] { "entryCode", "refEntryCode" };
    private static final String[] ENTRY_NAME_MATCHING_PROPERTIES = new String[] { "entryCode", "entryName", "refEntryCode" };

    private static final String[] IGNORED_PROPERTIES
            = new String[] {"entryId", "entryCode", "categoryCode", "activeStatus", "entrySource", "groupId", "entryId",
                            "categoryCodeId", "status" };


    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        // use "entryCode" + "refEntryCode" as matching key
        // for category code [CENTRE_TYPE_MAP], [MODEL]
        if (com.integrosys.cms.ui.collateral.CategoryCodeConstant.CENTRE_TYPE_MAP.equals(getCategoryCode())
                || com.integrosys.cms.ui.collateral.CategoryCodeConstant.ASSET_MODEL_TYPE.equals(getCategoryCode())
                || com.integrosys.cms.ui.collateral.CategoryCodeConstant.ASSET_MODEL_YEAR_MAP.equals(getCategoryCode())) {
            return REF_ENTRY_CODE_MATCHING_PROPERTIES;
        }

        // use "entryCode" + "entryName" + "refEntryCode" as matching key
        // for category code [FAC_PRODUCT_MAP]
        if (com.integrosys.cms.ui.limit.CategoryCodeConstant.FACILITY_PRODUCT_MAP.equals(getCategoryCode())) {
            return ENTRY_NAME_MATCHING_PROPERTIES;
        }
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        boolean activeStatus = true;
        if (ICMSConstant.ORG_CODE.equals(paramProperty.getLocalName()) ||
                com.integrosys.cms.ui.collateral.CategoryCodeConstant.RC_BRANCH.equals(paramProperty.getLocalName())) {
            // default active status to 'false'.
            // active status will be set to 'true' again, when the ref_entry_code is not null (by dependency parameter)
            activeStatus = false;
        }

        String remoteEntityName = paramProperty.getRemoteEntityName();
        if (remoteEntityName.equals("paramTSPRBaseRateType") ||
                remoteEntityName.equals("paramTSPRFacilityID") ||
                remoteEntityName.equals("paramTSPRLSMCode") ||
                remoteEntityName.equals("paramTSPRRBSPurposeCode") ||
                remoteEntityName.equals("paramTSPRLimitDescription") ||
                remoteEntityName.equals("paramTSPRProductType")) {
//                if (this.getIsDel() != null) {
//                    if (this.getIsDel().trim().equals("Y")) {
//                        activeStatus = false;
//                    } else {
//                        activeStatus = true;
//                    }
//                }
            String statusStr = this.getIsDel() == null ? "" : this.getIsDel().trim();
            setActiveStatusStr(statusStr);
            activeStatus = getActiveStatusStr().equals("1") ? true : false;
        }

        updatePropertiesForSaving(activeStatus, paramProperty);
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        updatePropertiesForSaving(false, paramProperty);
    }


    private void updatePropertiesForSaving(boolean activeStatus, IParameterProperty paramProperty) {
        setActiveStatus(activeStatus);
        setCategoryCode(paramProperty.getLocalName());      //local name refers to category code

        String remoteEntityName = paramProperty.getRemoteEntityName();
        String source = ICommonCodeDao.ENTRY_SOURCE_SIBS;

        if (remoteEntityName.equals("paramTSPRBaseRateType") ||
                remoteEntityName.equals("paramTSPRFacilityID") ||
                remoteEntityName.equals("paramTSPRLSMCode") ||
                remoteEntityName.equals("paramTSPRRBSPurposeCode") ||
                remoteEntityName.equals("paramTSPRLimitDescription") ||
                remoteEntityName.equals("paramTSPRProductType")) {
            source = ICommonCodeDao.ENTRY_SOURCE_TSPR;
        }

        setEntrySource(source);

//        DefaultLogger.debug(this, "getEntryCode() : '" + getEntryCode() + "'");
//        DefaultLogger.debug(this, "getEntryId() ::::::::: " + getEntryId());

        // set reference entry code for ADDRESS_TYPE ('4')
        if (CategoryCodeConstant.ADDRESS_TYPE.equals(getCategoryCode())) {
            if (getEntryCode() != null &&
                    ("M".equals(getEntryCode().trim()) || "P".equals(getEntryCode().trim()) || "E".equals(getEntryCode().trim()))) {
                setRefEntryCode(REFERENCE_CODE_RLOS);
            }
        }

        // set country code as 'MY' for INSURER_NAME ('INSURER_NAME')
        if (com.integrosys.cms.ui.collateral.CategoryCodeConstant.INSURER_NAME.equals(getCategoryCode())) {
            setCountry(ICMSUIConstant.COUNTRY_MALAYSIA);
        }

        // set country code as 'MY' for CENTRE & CENTRE_TYPE_MAP
        if (ICMSConstant.CATEGORY_CODE_ORG_GROUP.equals(getCategoryCode())
                || com.integrosys.cms.ui.collateral.CategoryCodeConstant.CENTRE_TYPE_MAP.equals(getCategoryCode())) {
            setCountry(ICMSUIConstant.COUNTRY_MALAYSIA);
        }

        // trim String Attributes
        if (getEntryCode() != null) {
            setEntryCode(getEntryCode().trim());
        }
        if (getEntryName() != null) {
            setEntryName(getEntryName().trim());
        }
        if (getCountry() != null) {
            setCountry(getCountry().trim());
        }
        if (getRefEntryCode() != null) {
            setRefEntryCode(getRefEntryCode().trim());
        }

        // default Country to 'MY' if sempty
        if (CommonUtil.isEmpty(getCountry())) {
            setCountry(ICMSUIConstant.COUNTRY_MALAYSIA);
        }

        Map setupDetails = paramProperty.getSetupDetailMap();
        if(setupDetails != null) {
            setCategoryCodeId(((Long)setupDetails.get(ICommonCodeWrapper.KEY_CATEGORY_ID)).longValue());
        }
    }


}
