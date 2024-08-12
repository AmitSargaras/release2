package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import org.apache.struts.action.ActionErrors;

import java.util.Set;

public class BbaVariPackageObjectValidator {
    public static ActionErrors validateObject(IFacilityMaster facilityMaster) {
        ActionErrors bbaVariPackageErrors = new ActionErrors();

        DefaultLogger.debug(" FacilityBBAVariPackage Total Errors", "--------->" + bbaVariPackageErrors.size());
        return bbaVariPackageErrors.size() == 0 ? null : bbaVariPackageErrors;
    }
}