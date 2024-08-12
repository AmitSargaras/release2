package com.integrosys.cms.ui.systemHandOff;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.batch.eod.EndOfDaySyncMastersServiceImpl;
import com.integrosys.cms.batch.eod.IEndOfDaySyncMastersService;
public class SystemHandOffAction extends CommonAction {
	IEndOfDaySyncMastersService endOfDaySyncMastersService;
	
	private final static String PERFORM_EOD = "performEod";
	private final static String PERFORM_EOD_MASTER_SYNC = "performEodMasterSync";
	private final static String PERFORM_ADF_RBI = "performAdfRbi";
	private final static String PERFORM_ADF_RBI_LIST = "performAdfRbiList";
	private final static String PERFORM_EOY = "performEoy";
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD 
	private final static String PERFORM_BASEL_UPDATE_REPORT = "performBaselUpdateReport";
	private final static String DOWNLOAD_FCUBS_FILE = "downloadFcubsFile";
	
	private Map nameCommandMap;
	
	 

	public Map getNameCommandMap() {
		return nameCommandMap;
	}
	
	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public IEndOfDaySyncMastersService getEndOfDaySyncMastersService() {
		return endOfDaySyncMastersService=new EndOfDaySyncMastersServiceImpl();
	}

	protected ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (PERFORM_EOD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(PERFORM_EOD);
		} else if (PERFORM_EOD_MASTER_SYNC.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EndOfDaySyncCommand();
		} else if (PERFORM_EOY.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get(PERFORM_EOY);
			objArray[1] = (ICommand) new EndOfYearRecurrentCommand();
		}else if (PERFORM_ADF_RBI.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(PERFORM_ADF_RBI);
		}
		// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
		else if (PERFORM_BASEL_UPDATE_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(PERFORM_BASEL_UPDATE_REPORT);
		}
		else if (DOWNLOAD_FCUBS_FILE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(DOWNLOAD_FCUBS_FILE);
		}
		//Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap map1, HashMap map2) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		if (event.equals(PERFORM_EOD)	) {
			forwardName = PERFORM_EOD;
		}else if(PERFORM_EOD_MASTER_SYNC.equals(event)) {
			forwardName = PERFORM_EOD_MASTER_SYNC;
		}else if (event.equals(PERFORM_EOY)) {
			forwardName = PERFORM_EOY;
		}else if (event.equals(PERFORM_ADF_RBI)) {
			forwardName = PERFORM_ADF_RBI;
		}else if (event.equals(PERFORM_ADF_RBI_LIST)) {
			forwardName = PERFORM_ADF_RBI_LIST;
		}
		//Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Starts
		else if (event.equals(PERFORM_BASEL_UPDATE_REPORT)) {
			forwardName = PERFORM_BASEL_UPDATE_REPORT;
		}
		
		else if (event.equals(DOWNLOAD_FCUBS_FILE)) {
			forwardName = DOWNLOAD_FCUBS_FILE;
		}
		
		//Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD || Ends
		return forwardName;
	}
}
