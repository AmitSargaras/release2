package com.integrosys.cms.app.ws.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

@Service
public class FCUBSReportDataLogHelper {

	/*@Autowired
	private IFCUBSDataLogDao FCUBSDataLogDao;

	public void setFCUBSDataLogDao(IFCUBSDataLogDao FCUBSDataLogDao) {
		this.FCUBSDataLogDao = FCUBSDataLogDao;
	}*/
	

	public void fcubsReportDataLoggingActivity(OBFCUBSReportDataLog fcubsReportObj,String fileName, Date requestDate) throws FCUBSDataLogException,Exception {

		try {
			System.out.println("FCUBSDataLogHelper.java => Inside fcubsDataLoggingActivity.");
			//OBFCUBSReportDataLog fcubsObj = new OBFCUBSReportDataLog();
			
			
			IFCUBSReportDataLogDao fcubsDataLogDao = (IFCUBSReportDataLogDao)BeanHouse.get("FCUBSReportDataLogDao");
			fcubsDataLogDao.createFCUBSReportDataLog(fcubsReportObj);
			
		} catch (FCUBSDataLogException e) {
			e.printStackTrace();
			throw e;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
		

	}

	
	private String fetchBranchCode(String liabBranch) throws Exception {
		
		 DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get BranchCode");
		 ILimitDAO dao = LimitDAOFactory.getDAO();
		 String fccBranchCode =dao.getBranchCodeFromId(liabBranch);
		 DefaultLogger.debug(this,"FCUBSLimitFileUploadJob after BranchCode"+fccBranchCode);
		 return fccBranchCode;
		}
	
	
	

}
