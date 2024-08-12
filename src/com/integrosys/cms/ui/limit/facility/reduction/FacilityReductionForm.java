package com.integrosys.cms.ui.limit.facility.reduction;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.common.FacilityIncrementalReductionCommonForm;

public class FacilityReductionForm extends FacilityIncrementalReductionCommonForm {
	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.reduction.FacilityReductionMapper";
	
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}		
	
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}		
}
