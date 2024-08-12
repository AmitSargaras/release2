//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.others.othersa;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa.IOthersa;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.others.OthersMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class OthersaMapper extends OthersMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		Object obj = ((IOthersa) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
		return super.mapFormToOB(cForm, inputs, obj);
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		return super.mapOBToForm(cForm, obj, inputs);
	}

	public static Object getObject(HashMap inputs) {
		return ((IOthersa) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}
}
