/*
Copyright Integro Technologies Pte Ltd
*/

package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.base.techinfra.util.AccessorUtil;
import java.io.Serializable;
import java.util.List;

/**
 *
 * ActionForm for PolicyCapAction.
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */

public class ExemptFacilityForm extends TrxContextForm implements Serializable {
		
    long exemptFacilityID;
    // String losSystem;
    String facilityCode;
    String facilityStatusExempted;
    String facilityStatusConditionalPerc;
//    String remarks;
    String[] checkSelects;

		public String[] getCheckSelects() {
				return checkSelects;
		}
			
		public void setCheckSelects(String[] checkSelects) {
				this.checkSelects = checkSelects;
		}
		
    public long getExemptFacilityID() {
        return exemptFacilityID;
    }

    public void setExemptFacilityID(long exemptFacilityID) {
        this.exemptFacilityID = exemptFacilityID;
    }

//    public String getLosSystem() {
//        return losSystem;
//    }
//
//    public void setLosSystem(String losSystem) {
//        this.losSystem = losSystem;
//    }

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

    public String getFacilityStatusConditionalPerc() {
        return facilityStatusConditionalPerc;
    }

    public void setFacilityStatusConditionalPerc(String facilityStatusConditionalPerc) {
        this.facilityStatusConditionalPerc = facilityStatusConditionalPerc;
    }

/*
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
*/

    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }

    public String[][] getMapper() {
        String[][] input = {
                {"exemptFacility","com.integrosys.cms.ui.creditriskparam.exemptFacility.ExemptFacilityMapper"},
                {"exemptFacilityMap","com.integrosys.cms.ui.creditriskparam.exemptFacility.ExemptFacilityMapper"},
                {"exemptFacilityTrxValue","com.integrosys.cms.ui.creditriskparam.exemptFacility.ExemptFacilityMapper"},
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
            };
        return input;
    }
}
