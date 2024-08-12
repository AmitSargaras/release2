package com.integrosys.cms.ui.collateral.document.docdoa;

import com.integrosys.cms.ui.collateral.document.AbstractDocumentStpValidator;
import org.apache.struts.action.ActionErrors;

import java.util.Map;

public class DocDoaStpValidator extends AbstractDocumentStpValidator {
    public boolean validate(Map context) {
        if (!validateDocumentCollateral(context)) {
            return false;
        }
//		IDeedAssignment deedAssignment = (IDeedAssignment) collateral;
        if (validateAndAccumulate(context).size() <= 0
            /*StringUtils.isNotBlank(deedAssignment.getContractNumber())
                   && StringUtils.isNotBlank(deedAssignment.getContractName())
                   && deedAssignment.getContractAmt() != null
                   && deedAssignment.getContractDate() != null*/
                ) {
            // do nothing
        } else return false;
        return true;
    }

    public ActionErrors validateAndAccumulate(Map context) {
        ActionErrors errorMessages = validateAndAccumulateDocumentation(context);

        return errorMessages;
    }
}
