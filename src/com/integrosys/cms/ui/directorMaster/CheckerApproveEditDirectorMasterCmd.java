package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Purpose : This command edit the  checker approved Director Master 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class CheckerApproveEditDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {

	private IDirectorMasterProxyManager directorMasterProxy;

	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(
			IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditDirectorMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IDirectorMasterTrxValue", "com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
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
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDirectorMasterTrxValue trxValueIn = (OBDirectorMasterTrxValue) map.get("IDirectorMasterTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Start 
			//if ( trxValueIn.getFromState().equals("DRAFT") || trxValueIn.getFromState().equals("ND") ) {				
				if ( trxValueIn.getStatus().equals("PENDING_CREATE") ) {
				
					boolean isDinNumberUnique = false;
					boolean isDirectorNameUnique = false;
				
					String dinNo = trxValueIn.getStagingDirectorMaster().getDinNo();
					String directorName = trxValueIn.getStagingDirectorMaster().getName();
					
					if( dinNo!=null )
						isDinNumberUnique = getDirectorMasterProxy().isDinNumberUnique(dinNo.trim());
		
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
				}
			//}
			// End
			IDirectorMasterTrxValue trxValueOut = getDirectorMasterProxy().checkerApproveDirectorMaster(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (DirectorMasterException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}