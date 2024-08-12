package com.integrosys.cms.ui.collateral.pledgor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateralPledgor;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralAction;

public class PledgorMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		PledgorForm form = (PledgorForm) cForm;
		if (CollateralAction.EVENT_SAVE_PLEDGOR.equals(form.getEvent())) {
			List pledgorList = new ArrayList();
			if (form.getRelationship() != null) {
				ICollateralPledgor[] pledgors = new OBCollateralPledgor[form.getSelected().length];
				for (int i = 0; i < form.getSelected().length; i++) {
					int index = Integer.parseInt(form.getSelected()[i]);
					pledgors[i] = new OBCollateralPledgor();
					pledgors[i].setPledgorName(form.getPledgorName()[index]);
					pledgors[i].setLegalID(form.getCifNo()[index]);
					pledgors[i].setPlgIdTypeCode(form.getIdTypeCode()[index]);
					pledgors[i].setPlgIdType(form.getIdType()[index]);
					pledgors[i].setPlgIdNumText(form.getIdNo()[index]);
					pledgors[i].setPledgorRelnshipCode("RELATIONSHIP");
					pledgors[i].setPledgorRelnship(form.getRelationship()[index]);
					pledgorList.add(pledgors[i]);
				}
			}
			return pledgorList;
		}
		else {
			ICollateral iCol = (ICollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
					.getStagingCollateral());

			int pledgorsIndex = Integer.parseInt((String) inputs.get("index"));
			ICollateralPledgor[] pledgors = iCol.getPledgors();
			pledgors[pledgorsIndex].setPledgorRelnship(form.getRelationship()[0]);

			return pledgors;
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		return null;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "index", "java.lang.Object", REQUEST_SCOPE } });
	}
}
