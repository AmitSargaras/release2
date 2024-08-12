
package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;


/**
 * Purpose :  This command save the  selected Director Master
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public class MakerSaveDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IDirectorMasterProxyManager directorMasterProxy;

	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerSaveDirectorMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"IDirectorMasterTrxValue", "com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"dinNo", "java.lang.String", REQUEST_SCOPE},
	        		{ "directorMasterObj", "com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
			HashMap exceptionMap = new HashMap();
			try {
				String event = (String) map.get("event");
				OBDirectorMaster directorMaster = (OBDirectorMaster) map.get("directorMasterObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IDirectorMasterTrxValue trxValueOut = new OBDirectorMasterTrxValue();
				IDirectorMasterTrxValue trxValueIn = (OBDirectorMasterTrxValue) map.get("IDirectorMasterTrxValue");
				
				boolean isDinNumberUnique = false;
				boolean isDirectorNameUnique = false;
				
				String dinNumber = "";
				String directorName = "";
				
				if(event.equals("maker_update_draft_directorMaster")){
					// Start
					
					String newDinNumber = directorMaster.getDinNo();//(String)map.get("dinNo");
					dinNumber = trxValueIn.getDirectorMaster().getDinNo().toString();
					
					String newDirectorName = directorMaster.getName().trim();
					directorName = trxValueIn.getDirectorMaster().getName();
				
					if( ! newDinNumber.equals(dinNumber) )
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber.trim());
					
					if( isDinNumberUnique )
						exceptionMap.put("directorMasterdinNoError", new ActionMessage("error.string.exist","Din Number"));
					
					if( ! newDirectorName.equals(directorName) )
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
					
					if( isDirectorNameUnique )
						exceptionMap.put("directorMasterNameError", new ActionMessage("error.string.exist","Director Name"));
					
					if( isDinNumberUnique || isDirectorNameUnique ){
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					// End	
					
					trxValueOut = getDirectorMasterProxy().makerUpdateSaveUpdateDirectorMaster(ctx, trxValueIn, directorMaster);					
					resultMap.put("request.ITrxValue", trxValueOut);
				}else{
					// Start
					dinNumber = directorMaster.getDinNo();//(String)map.get("dinNo");
					directorName = directorMaster.getName();
					
					isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(dinNumber.trim());
					isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(directorName);
		
					if(isDinNumberUnique )
						exceptionMap.put("directorMasterdinNoError", new ActionMessage("error.string.exist","Din Number"));
					
					if( isDirectorNameUnique )
						exceptionMap.put("directorMasterNameError", new ActionMessage("error.string.exist","Director Name"));
					
					if( isDinNumberUnique || isDirectorNameUnique ){
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					// End
					trxValueOut = getDirectorMasterProxy().makerSaveDirectorMaster(ctx,directorMaster);					
					resultMap.put("request.ITrxValue", trxValueOut);
				}
			}catch (DirectorMasterException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }
}