package com.integrosys.cms.app.tatduration.proxy;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.tatdoc.bus.TatDocException;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamBusManager;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.tatduration.bus.ITatParamItem;
import com.integrosys.cms.app.tatduration.bus.TatParamException;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.app.tatduration.trx.OBTatParamTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 27, 2008 Time: 5:18:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatParamProxyImpl implements ITatParamProxy 
{
	private ITatParamBusManager tatParamBusManager;
	private ITatParamBusManager stageTatParamBusManager;
	private ITrxController tatParamTrxController;
	private ITrxControllerFactory trxControllerFactory;

	public ITatParamBusManager getTatParamBusManager() 
	{
		return tatParamBusManager;
	}

	public void setTatParamBusManager(ITatParamBusManager tatParamBusManager)
	{
		this.tatParamBusManager = tatParamBusManager;
	}

	public ITatParamBusManager getStageTatParamBusManager() 
	{
		return stageTatParamBusManager;
	}

	public void setStageTatParamBusManager(ITatParamBusManager stageTatParamBusManager) 
	{
		this.stageTatParamBusManager = stageTatParamBusManager;
	}

	public ITrxController getTatParamTrxController() 
	{
		return tatParamTrxController;
	}

	public void setTatParamTrxController(ITrxController tatParamTrxController) 
	{
		this.tatParamTrxController = tatParamTrxController;
	}
	
	public ITrxControllerFactory getTrxControllerFactory() 
	{
        return trxControllerFactory;
    }
	
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) 
	{
		this.trxControllerFactory = trxControllerFactory;
	}

	public ITatParamTrxValue makerSubmitTatParamDuration(ITrxContext ctx, ITatParamTrxValue trxValue, ITatParam tatParam) throws TatParamException 
	{
		Validate.notNull(tatParam, "ITatParam must not be null");
		trxValue = formulateTrxValue(ctx, trxValue, tatParam);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ITatParamConstant.ACTION_MAKER_SUBMIT_TAT_DURATION);
		return operate(getTatParamTrxController(), trxValue, param);
	}
	
	public ITatParamTrxValue checkerApproveTatParam(ITrxContext ctx, ITatParamTrxValue trxValue) throws TatParamException
	{
		 if (ctx == null)
	            throw new TatParamException("The ITrxContext is null!!!");
	        if ( trxValue == null)
	        	throw new TatParamException("The ITatParamTrxValue to be updated is null!!!");
	        
	        trxValue = formulateTrxValue(ctx,  trxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ITatParamConstant.ACTION_CHECKER_APPROVE_TAT_DURATION);
	        return operate(trxValue, param);
	}
	
	public ITatParamTrxValue checkerRejectTatParam(ITrxContext anITrxContext, ITatParamTrxValue anITatParamTrxValue) throws TatParamException 
	{
        if (anITrxContext == null)
            throw new TatParamException("The ITrxContext is null!!!");

        if (anITatParamTrxValue == null)
            throw new TatParamException("The ITatParamTrxValue to be updated is null!!!");

        anITatParamTrxValue = formulateTrxValue(anITrxContext, anITatParamTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ITatParamConstant.ACTION_CHECKER_REJECT_TAT_DURATION);
        return operate(anITatParamTrxValue, param);
    }
	
	public ITatParamTrxValue makerCloseTatParamDuration(ITrxContext anITrxContext, ITatParamTrxValue anITatParamTrxValue) throws TatParamException 
	{
        if (anITrxContext == null)
            throw new TatParamException("The ITrxContext is null!!!");

        if (anITatParamTrxValue == null)
            throw new TatParamException("The ITatParamTrxValue to be updated is null!!!");

        anITatParamTrxValue = formulateTrxValue(anITrxContext, anITatParamTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ITatParamConstant.ACTION_MAKER_CLOSE_TAT_DURATION);
        return operate(anITatParamTrxValue, param);
    }
	
	/**
	 * Maker updates tat document
	 * @param ctx of ITrxContext type
	 * @param trxValue of ITatDocTrxValue type
	 * @param tatDoc of ITatDoc type
	 * @return ITatDocTrxValue - the interface representing the tat document trx
	 *         obj
	 * @throws TatDocException on errors
	 */
	/*public ITatDocTrxValue makerUpdateTatDoc(ITrxContext ctx, ITatDocTrxValue trxValue, ITatDoc tatDoc)
			throws TatDocException {
		Validate.notNull(ctx, "ITrxContext must not be null");
		Validate.notNull(trxValue, "ITatDocTrxValue must not be null");
		Validate.notNull(tatDoc, "ITatDoc must not be null");
		trxValue = formulateTrxValue(ctx, trxValue, tatDoc);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_TAT_DOC);
		return operate(getTatDocTrxController(), trxValue, param);
	}*/


	// **************** Helper Methods ************
	private ITatParamTrxValue operate(ITrxController controller, ITrxValue trxVal, ITrxParameter param)
			throws TatParamException 
	{
		if (trxVal == null)
			throw new TatParamException("ITrxValue is null!");

		try
		{
			if (controller == null) 
				throw new TatParamException("ITrxController is null!!!");
			
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (ITatParamTrxValue) obj;

		}
		catch (TransactionException e)
		{
			throw new TatParamException("TransactionException caught! " + e.toString(), e);
		}
	}
	
	private ITatParamTrxValue operate(ITatParamTrxValue anIPrIdxTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws TatParamException 
	{
        ICMSTrxResult result = operateForResult(anIPrIdxTrxValue, anOBCMSTrxParameter);
        return (ITatParamTrxValue) result.getTrxValue();
	}

	/**
	* @param anICMSTrxValue
	* @param anOBCMSTrxParameter - OBCMSTrxParameter
	* @return ICMSTrxResult - the trx result interface
	*/
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws TatParamException 
	{
		try 
		{
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) 
		{
			throw new TatParamException(e);
		}
		catch (Exception ex) 
		{
			throw new TatParamException(ex.toString());
		}
	}
	
	private ITatParamTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ITatParam tatParam)
	{
		ITatParamTrxValue tatParamTrxValue = null;
		if (anICMSTrxValue != null) 
			tatParamTrxValue = new OBTatParamTrxValue(anICMSTrxValue);
		else
			tatParamTrxValue = new OBTatParamTrxValue();
		
		tatParamTrxValue = formulateTrxValue(anITrxContext, tatParamTrxValue);
		return tatParamTrxValue;
	}
	
	private ITatParamTrxValue formulateTrxValue(ITrxContext anITrxContext, ITatParamTrxValue trxValue)
	{
		trxValue.setTrxContext(anITrxContext);
		trxValue.setRemarks(ITatParamConstant.INSTANCE_TAT_DURATION);
		trxValue.setTransactionType(ITatParamConstant.INSTANCE_TAT_DURATION);
		return trxValue;
	}

	public ITatParamTrxValue getTatParamByTrxID(long trxID) throws TatParamException
	{
        ITatParamTrxValue trxValue = new OBTatParamTrxValue();
        trxValue.setTransactionID(String.valueOf(trxID));
        trxValue.setTransactionType(ITatParamConstant.INSTANCE_TAT_DURATION);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ITatParamConstant.ACTION_READ_TAT_PARAM);
        return operate(trxValue, param);
    }
	
	public ITatParamTrxValue getTatParamTrxValue(long tatParamId) throws TatParamException 
	{
        if (tatParamId == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
        {
            throw new TatParamException("Invalid tatParamId");
        }
        
        ITatParamTrxValue trxValue = new OBTatParamTrxValue();
        trxValue.setReferenceID(String.valueOf(tatParamId));
        trxValue.setTransactionType(ITatParamConstant.TAT_DURATION);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ITatParamConstant.ACTION_READ_TAT_PARAM_ID);
        return operate(trxValue, param);
    }
	
	public List getTatParamList() throws TatParamException
	{
		return getTatParamBusManager().getTatParamList();
	}

	public ITatParam getTatParamByApplicationType(String applicationType) throws TatParamException
	{
		return getTatParamBusManager().getTatParamByApplicationType(applicationType);
	}
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws TatParamException
	{
		return getTatParamBusManager().getTatParamItem(tatParamItemId);
	}
	
	public ITatParam getTatParam(Long tatParamId) throws TatParamException
	{
		return getTatParamBusManager().getTatParam(tatParamId);
	}
}
