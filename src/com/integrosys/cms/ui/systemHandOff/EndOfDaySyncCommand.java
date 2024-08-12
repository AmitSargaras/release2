package com.integrosys.cms.ui.systemHandOff;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.eod.IEndOfDayBatchService;
import com.integrosys.cms.batch.eod.IEndOfDaySyncMastersService;

public class EndOfDaySyncCommand extends AbstractCommand implements ICommonEventConstant {
	
	
	IEndOfDaySyncMastersService endOfDaySyncMastersService;
	
	public IEndOfDaySyncMastersService getEndOfDaySyncMastersService() {
		return endOfDaySyncMastersService;
	}

	public void setEndOfDaySyncMastersService(
			IEndOfDaySyncMastersService endOfDaySyncMastersService) {
		this.endOfDaySyncMastersService = endOfDaySyncMastersService;

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", SERVICE_SCOPE}
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		StringBuffer log = new StringBuffer();
		DefaultLogger.info(this, "Start EOD Master Synchronization");
		
		if(getEndOfDaySyncMastersService()==null)
			setEndOfDaySyncMastersService((IEndOfDaySyncMastersService)BeanHouse.get("EndOfDaySyncMastersServiceImpl"));
		
		getEndOfDaySyncMastersService().performEODSyncClimsToCps();
		
		DefaultLogger.info(this, "EOD Master CLIMS To CPS Syncronization End: ");
		
		getEndOfDaySyncMastersService().performEODSyncCpsToClims();
		
		DefaultLogger.info(this, "EOD Master CPS To CLIMS Syncronization End: ");
		
		HashMap resultMap = new HashMap();
		
		resultMap.put("log", log.toString());
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	


}
