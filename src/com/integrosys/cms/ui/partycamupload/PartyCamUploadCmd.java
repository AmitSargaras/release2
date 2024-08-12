/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.partycamupload;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class PartyCamUploadCmd extends AbstractCommand implements ICommonEventConstant {
	

//	private IPartyCamUploadProxyManager partyCamuploadProxy;


	/**
	 * Default Constructor
	 */
	
	
	public PartyCamUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE },
	        	
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "preUpload", "java.lang.String", REQUEST_SCOPE },
					{ "dataFromPartyCamUploadMv", "java.util.Set", SERVICE_SCOPE },
					{ "riskGrade", "java.util.Set", SERVICE_SCOPE }
					});				  
		}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			String strError = "";
			String fileType = "";
			String fileUploadPending ="";
			String fileCheckSum ="";			
			
			boolean preUpload=false;
			Set<String> dataFromPartyCamUploadMv=new HashSet<String>();
			Set<String> riskGrade=new HashSet<String>();
			
//			System.out.println("now abc");
			String remarks = (String) map.get("remarks");
			
			IHolidayTrxValue trxValueOut = new OBHolidayTrxValue();
			try {

					resultMap.put("fileUploadPending",fileUploadPending);
					resultMap.put("fileCheckSum",fileCheckSum);	
					resultMap.put("fileType", fileType);
			    	resultMap.put("errorEveList", strError);
			    	
			    	IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
					if(fileUploadJdbc.getPartyCamUplodCount()>0){
						preUpload=true;
					}
					if(!preUpload){
						String refreshStatus = fileUploadJdbc.spUploadTransaction("PARTYCAM");
						if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
							dataFromPartyCamUploadMv = fileUploadJdbc.cacheDataFromPartyCamUploadMV("PARTY_CAM_UPLOAD_MV");
							//DefaultLogger.info(this,"In PartyCamUploadCmd [dataFromPartyCamUploadMv] valuse is: "+dataFromPartyCamUploadMv);
						}
						
							riskGrade = fileUploadJdbc.getRiskGrade();
					}
					resultMap.put("event", map.get("event"));
					resultMap.put("preUpload",String.valueOf(preUpload));
					resultMap.put("dataFromPartyCamUploadMv", dataFromPartyCamUploadMv);
					resultMap.put("riskGrade", riskGrade);
					
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
			}
			catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			
			
	    }

/*		public IPartyCamUploadProxyManager getPartyCamuploadProxy() {
			return partyCamuploadProxy;
		}

		public void setPartyCamuploadProxy(
				IPartyCamUploadProxyManager partyCamuploadProxy) {
			this.partyCamuploadProxy = partyCamuploadProxy;
		}
*/

}
