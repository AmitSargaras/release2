/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

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
 * Purpose : This command create the  selected Director Master 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class MakerCreateDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	
	
	public MakerCreateDirectorMasterCmd() {
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
				OBDirectorMaster directorMaster = (OBDirectorMaster) map.get("directorMasterObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				// Start
				IDirectorMasterTrxValue trxValueIn = (OBDirectorMasterTrxValue) map.get("IDirectorMasterTrxValue");
				boolean validateCode = false;
				if ( trxValueIn == null  )
					validateCode = true;
				
				else if( trxValueIn.getFromState().equals("PENDING_PERFECTION") )
					validateCode = true;
						
				if (validateCode) {
					boolean isDinNumberUnique = false;
					boolean isDirectorNameUnique = false;
					
					String dinNumber = directorMaster.getDinNo();
					String directorName = directorMaster.getName();
					
					if( dinNumber!=null )
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(dinNumber.trim());
		
					if( isDinNumberUnique )
						exceptionMap.put("directorMasterdinNoError", new ActionMessage("error.string.exist","Din Number"));
					
					if( directorName!=null )
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(directorName.trim());
		
					if( isDirectorNameUnique )
						exceptionMap.put("directorMasterNameError", new ActionMessage("error.string.exist","Director Name"));
										
					if( isDinNumberUnique || isDirectorNameUnique ){
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					IDirectorMasterTrxValue trxValueOut = new OBDirectorMasterTrxValue();
					trxValueOut = getDirectorMasterProxy().makerCreateDirectorMaster(ctx,directorMaster);
					resultMap.put("request.ITrxValue", trxValueOut);
				}
				// End
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
