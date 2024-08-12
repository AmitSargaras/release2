package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.Iterator;

public class MultiTierFinanceObjectValidator {
    public static ActionErrors validateObject(IFacilityMaster facilityMaster) {
        ActionErrors multiTierFinanceErrors = new ActionErrors();
        Set multiTierFinSet = facilityMaster.getFacilityMultiTierFinancingSet();

        String gppPaymentModeValue = "";
        if (facilityMaster.getFacilityIslamicMaster() != null
                && facilityMaster.getFacilityIslamicMaster().getGppPaymentModeValue() != null) {
            gppPaymentModeValue = facilityMaster.getFacilityIslamicMaster().getGppPaymentModeValue();
        }

        if (multiTierFinSet != null){
            long totalTierTerm = 0;
            for (Iterator iterator = multiTierFinSet.iterator(); iterator.hasNext();) {
                IFacilityMultiTierFinancing multiTierFin =  (IFacilityMultiTierFinancing)iterator.next();

                if (!validateGracePeriod(multiTierFin, gppPaymentModeValue)) {
                    multiTierFinanceErrors.add("multiTierFinancingErrors",
                            new ActionMessage("error.grace.period.not.allowed.when.payment.mode.is",
                                    ICMSConstant.TRUE_VALUE, "'Start payment Immediately'"));
                }

//                DefaultLogger.debug(" FacilityMultiTierFinance INFO", " ====================== ");
//                DefaultLogger.debug(" FacilityMultiTierFinance INFO", " LITTLE totalTierTerm --------->" + totalTierTerm);
//                DefaultLogger.debug(" FacilityMultiTierFinance INFO", " multiTierFin.getGracePeriod() --------->" + multiTierFin.getGracePeriod());
//                DefaultLogger.debug(" FacilityMultiTierFinance INFO", " multiTierFin.getTierTerm() --------->" + multiTierFin.getTierTerm());
                if (!StringUtils.equals(multiTierFin.getGracePeriod(), ICMSConstant.TRUE_VALUE)
                        && multiTierFin.getTierTerm() != null && !"D".equals(multiTierFin.getStatus())) {
                    totalTierTerm = totalTierTerm + multiTierFin.getTierTerm().longValue();
                }
            }

            DefaultLogger.debug(" FacilityMultiTierFinance INFO", " FINAL totalTierTerm --------->" + totalTierTerm);
//            DefaultLogger.debug(" FacilityMultiTierFinance INFO", " facilityMaster.getLimit().getLimitTenor().longValue() --------->"
//                    + facilityMaster.getLimit().getLimitTenor().longValue());
            if (totalTierTerm != facilityMaster.getLimit().getLimitTenor().longValue()) {
                multiTierFinanceErrors.add("multiTierFinancingErrors",
                            new ActionMessage("error.facility.term.must.equal.total.tier.term"));
            }
        }

        DefaultLogger.debug(" FacilityMultiTierFinance Total Errors", "--------->" + multiTierFinanceErrors.size());
        return multiTierFinanceErrors.size() == 0 ? null : multiTierFinanceErrors;
    }

    /**
     * Method to validate single multitier financing OB for detail page display
     * @param rel
     * @return
     */
    public static ActionErrors validateSingleObject(IFacilityMultiTierFinancing multiTierFin, String gppPaymentModeValue) {
        ActionErrors multiTierFinanceErrors = new ActionErrors();

        if (!validateGracePeriod(multiTierFin, gppPaymentModeValue)) {
            multiTierFinanceErrors.add("gracePeriod",
                    new ActionMessage("error.grace.period.not.allowed.when.payment.mode.is",
                            ICMSConstant.TRUE_VALUE, "'Start payment Immediately'"));
        }

        return multiTierFinanceErrors.size() == 0 ? null : multiTierFinanceErrors;
    }


    /**
     * Validate grace period
     * @param multiTierFin
     * @return true when pass validation, false otherwise
     */
    private static boolean validateGracePeriod(IFacilityMultiTierFinancing multiTierFin, String gppPaymentModeValue){
        if (!(StringUtils.isBlank(gppPaymentModeValue)) && "I".equals(gppPaymentModeValue)) {
                    if (StringUtils.equals(ICMSConstant.TRUE_VALUE, multiTierFin.getGracePeriod())) {
                return false;
            }
        }
        return true;
    }

}