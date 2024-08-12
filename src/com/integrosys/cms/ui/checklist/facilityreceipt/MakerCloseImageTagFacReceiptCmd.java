package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.imageTag.ImageTagException;
/**
 
 * @author abhijit.rudrakshawar: 
 * Command for maker to close the rejected System Bank trx value
 */

public class MakerCloseImageTagFacReceiptCmd extends AbstractCommand implements ICommonEventConstant {
    
	private IImageTagProxyManager imageTagProxyManager;
	
	
	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	/**
     * Default Constructor
     */
    public MakerCloseImageTagFacReceiptCmd() {
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
                {"IImageTagTrxValue", "com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        }
        );
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
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ImageTagException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
        try {
            IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map.get("IImageTagTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            if(trxValueIn != null) {
            IImageTagTrxValue trxValueOut = itmageTagProxyManager.makerCloseRejectedImageTag(ctx, trxValueIn);

            resultMap.put("request.ITrxValue", trxValueOut);
            }

        }catch (ImageTagException ex) {
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



