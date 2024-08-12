package com.integrosys.cms.batch.valuation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchParameterValidator;

/**
 * Validate parameters required to run the valuation batch job.
 * 
 * <p>
 * Required Parameters
 * <ul>
 * <li>If specialhandle = AB102, 'region' must be provided
 * <li>If specialhandle = GT409, 'country' must be provided
 * <li>For others, 'country' and 'sectype' must be provided
 * <li>source (optional)
 * </ul>
 * 
 * @author Chong Jun Yong
 * 
 */
public class ValuationParameterValidator implements BatchParameterValidator {
	public void validate(Map context) throws InvalidParameterBatchJobException {

		if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(context.get("specialhandle"))) {
			if (context.get("region") == null) {
				throw new InvalidParameterBatchJobException(
						"missing 'region' parameters required for vehicle valuation batch job.");
			}
		}
		else if (ICMSConstant.COLTYPE_GUARANTEE_GOVT_LINK.equals(context.get("specialhandle"))) {
			if (context.get("country") == null) {
				throw new InvalidParameterBatchJobException(
						"missing 'country' parameters required for cgc guarantee valuation batch job.");
			}
		}
		else {
			List missingParameters = new ArrayList();
			if (context.get("country") == null) {
				missingParameters.add("country");
			}

			if (context.get("sectype") == null) {
				missingParameters.add("sectype");
			}

			if (!missingParameters.isEmpty()) {
				throw new InvalidParameterBatchJobException("missing parameters required for valuation batch job.",
						(String[]) missingParameters.toArray(new String[0]));
			}
		}

	}
}
