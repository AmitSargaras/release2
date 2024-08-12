package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.vessel.IVessel;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Map;

public class AssetVesselStpValidator extends AbstractAssetBasedStpValidator {
    public boolean validate(Map context) {
        if (!validateAssetBasedCollateral(context)) {
            return false;
        }
//		IVessel vessel = (IVessel) collateral;
        if (validateAndAccumulate(context).size() <= 0
            /*StringUtils.isNotBlank(vessel.getPubTransport())
                   && vessel.getChattelSoldDate() != null
                   && StringUtils.isNotBlank(vessel.getRlSerialNumber())
                   && vessel.getRepossessionDate() != null
                   && StringUtils.isNotBlank(vessel.getModelNo())*/
                ) {
            // do nothing
        } else return false;
        return true;
    }

    public ActionErrors validateAndAccumulate(Map context) {
        ActionErrors errorMessages = validateAndAccumulateAssetBased(context);

        IVessel vessel = (IVessel) context.get(COL_OB);
        // Allow passive
        if (StringUtils.isBlank(vessel.getPubTransport())) {
            errorMessages.add("pubTransport", new ActionMessage("error.mandatory"));
        }
        // Model number
        if (StringUtils.isBlank(vessel.getModelNo())) {
            errorMessages.add("modelNo", new ActionMessage("error.mandatory"));
        }
        return errorMessages;
    }
}
