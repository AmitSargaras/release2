

package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Purpose : This command edit the  selected Director Master 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class MakerEditDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	
	
	public MakerEditDirectorMasterCmd() {
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
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IDirectorMasterTrxValue trxValueIn = (OBDirectorMasterTrxValue) map.get("IDirectorMasterTrxValue");

				// Start
				boolean isDinNumberUnique = false;				
				boolean isDirectorNameUnique = false;
				
				String newDinNumber = directorMaster.getDinNo().trim();
				String newDirectorName = directorMaster.getName().trim();
				
				String dinNumber = "";
				String directorName = "";
				
				if( event.equals("maker_save_update") ){
					if( !trxValueIn.getFromState().equals("ACTIVE") ){
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
					}
					else{
						dinNumber = trxValueIn.getStagingDirectorMaster().getDinNo().toString();
						directorName = trxValueIn.getStagingDirectorMaster().getName();
						
						if( ! newDinNumber.equals(dinNumber) )
							isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
						
						if( ! newDirectorName.equals(directorName) )
							isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
					}
				}	
				
				else if( event.equals("maker_save_create") ){
					dinNumber = trxValueIn.getStagingDirectorMaster().getDinNo().toString();
					directorName = trxValueIn.getStagingDirectorMaster().getName();
					
					if(!newDinNumber.equals(dinNumber))
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
					
					if( ! newDirectorName.equals(directorName) )
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
				}
				
				else if( event.equals("maker_confirm_resubmit_edit") ){
					if( !trxValueIn.getFromState().equals("PENDING_CREATE") ){
						dinNumber = trxValueIn.getStagingDirectorMaster().getDinNo().toString();
						directorName = trxValueIn.getStagingDirectorMaster().getName();
						
						if( ! newDinNumber.equals(dinNumber) )
							isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
						
						if( ! newDirectorName.equals(directorName) )
							isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
					}
					else{
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
					}
				}
								
				else{
					dinNumber = trxValueIn.getDirectorMaster().getDinNo().toString();
					directorName = trxValueIn.getDirectorMaster().getName();
					
					if( ! newDinNumber.equals(dinNumber) )
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(newDinNumber);
					
					if( ! newDirectorName.equals(directorName) )
						isDirectorNameUnique = getDirectorMasterProxy().isDirectorNameUnique(newDirectorName);
				}
	
				if( isDinNumberUnique )
					exceptionMap.put("directorMasterdinNoError", new ActionMessage("error.string.exist","Din Number"));
				
				if( isDirectorNameUnique )
					exceptionMap.put("directorMasterNameError", new ActionMessage("error.string.exist","Director Name"));
				
				if( isDinNumberUnique || isDirectorNameUnique ){
					resultMap.put("event",event);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
				IDirectorMasterTrxValue trxValueOut = new OBDirectorMasterTrxValue();
				trxValueOut = getDirectorMasterProxy().makerCreateDirectorMaster(ctx,directorMaster);
//				resultMap.put("request.ITrxValue", trxValueOut);
				// End
				
				if( trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION) || trxValueIn.getFromState().equals(ICMSConstant.STATE_ND) ){
					trxValueOut = getDirectorMasterProxy().makerUpdateSaveCreateDirectorMaster(ctx,trxValueIn,directorMaster);
				}else if ( event.equals("maker_edit_directorMaster") || event.equals("maker_delete_directorMaster") ) {
						trxValueOut = getDirectorMasterProxy().makerUpdateDirectorMaster(ctx, trxValueIn, directorMaster);
				} else {
					String remarks = (String) map.get("remarks");
					ctx.setRemarks(remarks);
					trxValueOut = getDirectorMasterProxy().makerEditRejectedDirectorMaster(ctx, trxValueIn, directorMaster);
				} 

				resultMap.put("event",event);
				resultMap.put("request.ITrxValue", trxValueOut);
				
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