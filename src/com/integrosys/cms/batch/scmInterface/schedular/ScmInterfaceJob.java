package com.integrosys.cms.batch.scmInterface.schedular;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.json.dao.IJsInterfaceLogDao;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.ws.ILineWebserviceClient;
import com.integrosys.cms.app.json.ws.IPartyWebserviceClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupJdbc;

public class ScmInterfaceJob {

	private final static Logger logger = LoggerFactory.getLogger(ScmInterfaceJob.class);
	
	private IJsInterfaceLogDao logDao;
	

	public IJsInterfaceLogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(IJsInterfaceLogDao logDao) {
		this.logDao = logDao;
	}

	public static void main(String[] args) {

		new ScmInterfaceJob().execute();
	}
	
	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String scmInterfaceServerName = bundle.getString("scm.interface.server.name");
		System.out.println("<<<<In execute() ScmInterfaceJob Starting....>>>>" + scmInterfaceServerName);
		if (null != scmInterfaceServerName && scmInterfaceServerName.equalsIgnoreCase("app1")) {
			processFailedPartyService();
			processFailedReleaseLineService();
		}
	}
	

	public void processFailedPartyService() {
		IPartyGroupJdbc partyJdbc = (IPartyGroupJdbc)BeanHouse.get("partyGroupJdbc");
		IPartyWebserviceClient clientPartyImpl = (IPartyWebserviceClient)BeanHouse.get("partyWebServiceClient");	
		
		try {
			System.out.println("Starting ScmInterfaceJob for failed party web services");
			Map<String, Integer> failedPartyMap = null;
			long successCountAdd = 0;
			long successCountUpd = 0;
			failedPartyMap = partyJdbc.getFailedPartyRequestforScm();//records with error count = 5
			try{
				System.out.println("**63*** ScmInterfaceJob for Party ****processFailedPartyService()*****Inserting max error records to backup table ");
			int partyInserionCount =	partyJdbc.insertPartyToInterfaceLogBackupTable();
				System.out.println("**65*** ScmInterfaceJob for Party****processFailedPartyService()***** Insertion completed******* No. of records inserted are : "+partyInserionCount);
			}catch(Exception e) {
			System.out.println(" ************67*********ScmInterfaceJob for  party *****inside catch of insertPartyToInterfaceLogBackupTable Exception is : "+e);	
			}
			System.out.println("Size of the Map is "+failedPartyMap.size());
			if(failedPartyMap != null && failedPartyMap.size() > 0) {
				System.out.println("Total of "+ failedPartyMap.size() +" request(s) to be resent to SCM System for CAM/Customer");
				for(String partyId : failedPartyMap.keySet()) {
					int partyCnt = failedPartyMap.get(partyId);
					if (partyCnt>1) {
					System.out.println("Resending latest failed request for party/CAM for party Id: "+partyId+":" + partyCnt);
					try {
						List<Long> failedIdforAdd = new ArrayList<Long>();
						failedIdforAdd = partyJdbc.getFailedListforAdd(partyId);
						try {
							System.out.println("Going to method partyJdbc.updateSCFJSLogTableForPartyAndCam() AFTER getFailedListforAdd..");
							partyJdbc.updateSCFJSLogTableForPartyAndCam(partyId);
							System.out.println("After partyJdbc.updateSCFJSLogTableForPartyAndCam() AFTER getFailedListforAdd..");
						}
						catch(Exception e) {
							System.out.println("Exception in while updating status to Fail PARTY/CAM SCF JS TABLE other than max records whrere status is Error..e=>"+e);
							e.printStackTrace();
						}

						for(int i = 0; i<failedIdforAdd.size();i++) {
						System.out.println("Inside the loop to resend failed requests for add ");
						List log =  partyJdbc.getInterfaceLogDetailsForParty(failedIdforAdd.get(i));
						OBJsInterfaceLog logData = (OBJsInterfaceLog) log.get(0);
					    boolean success = true;
					    System.out.println("logId for failed request for party/CAM for add: " + logData.getId());
						success = clientPartyImpl.processFailedPartyRequests(logData);
						if(success) {
							successCountAdd++;
							System.out.println("Successfully resent failed request for party/CAM with Id: " + failedIdforAdd.get(i));
						}
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Exception caught inside sendRequest with error: " + e.getMessage());
					}
					}else {
						System.out.println("Resending failed request for party/CAM for party Id: "+partyId+":" + partyCnt);
						try {
							List<Long> failedIdforupdate = new ArrayList<Long>();
							failedIdforupdate = partyJdbc.getFailedListforUpd(partyId);
							
							try {
								System.out.println("Going to method partyJdbc.updateSCFJSLogTableForPartyAndCam() AFTER getFailedListforUpd..");
								partyJdbc.updateSCFJSLogTableForPartyAndCam(partyId);
								System.out.println("After partyJdbc.updateSCFJSLogTableForPartyAndCam() AFTER getFailedListforUpd....");
							}
							catch(Exception e) {
								System.out.println("Exception in while updating status to Fail PARTY/CAM SCF JS TABLE other than max records whrere status is Error..e=>"+e);
								e.printStackTrace();
							}

							for(int i = 0; i<failedIdforupdate.size();i++) {
							System.out.println("Inside the loop to resend failed requests for update ");	
							List log =  partyJdbc.getInterfaceLogDetailsForParty(failedIdforupdate.get(i));
							OBJsInterfaceLog logData = (OBJsInterfaceLog) log.get(0);
							
							System.out.println("logId for failed request for party/CAM for update : " + logData.getId());
						    boolean success = true;
							success = clientPartyImpl.processFailedPartyRequests(logData);
							if(success) {
								successCountUpd++;
								System.out.println("Successfully resent failed request for party/CAM with Id: " + failedIdforupdate.get(i));
							}
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Exception caught inside sendRequest with error: " + e.getMessage());
						}
					}
				}
				System.out.println(successCountAdd +" request(s) to SCM that failed in add is resent successfully");
				System.out.println(successCountUpd +" request(s) to SCM that failed in update is resent successfully");

			}
			
		}catch (Exception e) {
			System.out.println("ScmInterfaceJob in catch Exception......" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void processFailedReleaseLineService() {
		ILimitDAO lineDao = LimitDAOFactory.getDAO();
		ILineWebserviceClient clientLineImpl = (ILineWebserviceClient)BeanHouse.get("lineWebServiceClient");
		System.out.println("Inside ScmInterfaceJob ..processFailedReleaseLineService() method .. ");
		try {
			System.out.println("Starting ScmInterfaceJob for Release Line");
			System.out.println("For date "+CommonUtil.getCurrentDate());
			List<Long> failedList = new ArrayList<Long>();
			failedList = lineDao.getFailedReleaseLineRequestforScm();
			
			try{
				System.out.println("**163*** ScmInterfaceJob for Release Line*********processFailedReleaseLineService()**** Inserting max error records to backup table ");
				int lineInserionCount	= lineDao.insertLineToInterfaceLogBackupTable();
				System.out.println("***165** ScmInterfaceJob for Release Line*****processFailedReleaseLineService()***** Insertion completed******* No. of records inserted are : "+lineInserionCount);
			}catch(Exception e) {
			System.out.println(" ************167*********ScmInterfaceJob for  Line ***processFailedReleaseLineService()****inside catch of insertPartyToInterfaceLogBackupTable Exception is : "+e);	
			}
			
			System.out.println("Size of the list is "+failedList.size());
			if(failedList != null && failedList.size() > 0) {
				long successCount = 0;
				System.out.println("Total of "+ failedList.size() + " request(s) to be resent to SCM System for Release Line");
				
				try {
					System.out.println("Going to method lineDao.updateSCFJSLogTableForLine()..");
					lineDao.updateSCFJSLogTableForLine();
					System.out.println("After lineDao.updateSCFJSLogTableForLine()..");
				}
				catch(Exception e) {
					System.out.println("Exception in while updating status to Fail  LINE SCF JS TABLE  other than max records whrere status is Error..e=>"+e);
					e.printStackTrace();
				}
				
				for(int i=0;i<failedList.size();i++) {
					Long logId = failedList.get(i);
					System.out.println("Resending all the failed request for Release Line  with Id: " + logId);
					try {
						List log =  lineDao.getInterfaceLogDetailsForLine(logId);
						OBJsInterfaceLog logData = (OBJsInterfaceLog) log.get(0);
					     boolean success = true;
						success = clientLineImpl.processFailedReleaseLineRequests(logData);
						if(success) {
							successCount++;
							System.out.println("Successfully resent failed request for Release Line with Id: " + logId);
						}
					} catch (Exception e) {
						System.out.println("Exception caught inside processFailedReleaseLineService sendRequest with error: " + e);
						e.printStackTrace();
					}
				}
				System.out.println(successCount +" request(s) to SCM for Release webservice resent successfully");
			}
			
		}catch (Exception e) {
			System.out.println("Exception in ScmInterfaceJob in catch Exception......" + e);
			e.printStackTrace();
		}
	}
}
