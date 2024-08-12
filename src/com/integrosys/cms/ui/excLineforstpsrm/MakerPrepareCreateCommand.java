package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excLineforstpsrm.trx.IExcLineForSTPSRMTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerPrepareCreateCommand  extends AbstractCommand implements ICommonEventConstant,
IExcLineForSTPSRMConstant {
	
	public MakerPrepareCreateCommand() {
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{"isNew", String.class.getName(), REQUEST_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ POJO_TRX, IExcLineForSTPSRMTrxValue.class.getName(), SERVICE_SCOPE },
			{ "lineNoList", List.class.getName(), SERVICE_SCOPE },
			{"isNew", String.class.getName(), REQUEST_SCOPE}
		};
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		IFacilityNewMasterJdbc jdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
		resultMap.put("lineNoList", jdbc.getAllActiveLineNumbers());
		
		String isNew = (String) map.get("isNew");
		
		resultMap.put("isNew", isNew);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}