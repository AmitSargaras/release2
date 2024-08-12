/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/

package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;


/**
     * IExemptFacility
     * Purpose:
     * Description:
     *
     * @author $Author$
     * @version $Revision$
     * @since $Date$
     * Tag: $Name$
     */
    public class OBExemptFacility implements IExemptFacility{

        long versionTime;
        long exemptFacilityID=ICMSConstant.LONG_INVALID_VALUE;
        long groupID;
        long cmsRef=ICMSConstant.LONG_INVALID_VALUE;
        //String losSystem;
        String facilityCode;
        String facilityStatusExempted;
        double facilityStatusConditionalPerc;
        String remarks;
        String status;


        public OBExemptFacility() {
//            super();
        }

        public OBExemptFacility (IExemptFacility obj) {
            this();
            AccessorUtil.copyValue (obj, this);
        }
        public long getExemptFacilityID() {
            return exemptFacilityID;
        }

        public void setExemptFacilityID(long exemptFacilityID) {
            this.exemptFacilityID = exemptFacilityID;
        }

        public long getGroupID() {
            return groupID;
        }

        public void setGroupID(long groupID) {
            this.groupID = groupID;
        }

        public long getCmsRef() {
            return cmsRef;
        }

        public void setCmsRef(long cmsRef) {
            this.cmsRef = cmsRef;
        }

        public long getVersionTime() {
            return versionTime;
        }

        public void setVersionTime(long versionTime) {
            this.versionTime = versionTime;
        }

//        public String getLosSystem() {
//            return losSystem;
//        }
//
//        public void setLosSystem(String losSystem) {
//            this.losSystem = losSystem;
//        }

        public String getFacilityCode() {
            return facilityCode;
        }

        public void setFacilityCode(String facilityCode) {
            this.facilityCode = facilityCode;
        }

        public String getFacilityStatusExempted() {
            return facilityStatusExempted;
        }

        public void setFacilityStatusExempted(String facilityStatusExempted) {
            this.facilityStatusExempted = facilityStatusExempted;
        }

        public double getFacilityStatusConditionalPerc() {
            return facilityStatusConditionalPerc;
        }

        public void setFacilityStatusConditionalPerc(double facilityStatusConditionalPerc) {
            this.facilityStatusConditionalPerc = facilityStatusConditionalPerc;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
