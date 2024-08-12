package com.integrosys.cms.ui.collateral.nocollateral;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.ui.collateral.CollateralForm;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IGteGovtLink;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 26, 2007 Time: 5:32:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoCollateralMapper extends CollateralMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		INoCollateral obj =  ((INoCollateral) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
		Object colObj = super.mapFormToOB((CollateralForm) cForm, inputs, obj);
		return colObj;

	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		return super.mapOBToForm((CollateralForm) cForm, obj, inputs);
	}

}
