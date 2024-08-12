/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.proxy.exemptedinst;

import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.*;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.*;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.List;


/**
 * This class implements the services that are available in CMS with
 * respect to the Exempted Institution life cycle.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class AbstractExemptedInstProxy implements IExemptedInstProxy
{
    
    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx of type ITrxContext
     * @param trxValue of type ITrxValue
     * @return transaction value
     */
    private IExemptedInstTrxValue constructTrxValue(ITrxContext ctx,
    	IExemptedInstTrxValue trxValue)
    {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_EXEMPT_INST);
        return trxValue;
    }

    /**
     * Helper method to operate transactions.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws ExemptedInstException on errors encountered
     */
    private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws ExemptedInstException
    {
        if (trxVal == null) {
            throw new ExemptedInstException("IExemptedInstTrxValue is null!");
        }

        try {
            ITrxController controller = null;

            if (trxVal instanceof IExemptedInstTrxValue) {
                controller = (new ExemptedInstTrxControllerFactory()).getController(trxVal, param);
            }

            if (controller == null) {
                throw new ExemptedInstException("ITrxController is null!");
            }

            ITrxResult result = controller.operate(trxVal, param);
            ITrxValue obj = result.getTrxValue();
            return obj;
        }
        catch (ExemptedInstException e) {
            e.printStackTrace();
            rollback();
            throw e;
        }
        catch (TransactionException e) {
            e.printStackTrace();
            rollback();
            throw new ExemptedInstException("TransactionException caught! " + e.toString(), e);
        }
        catch (Exception e) {
            e.printStackTrace();
            rollback();
            throw new ExemptedInstException("Exception caught! " + e.toString(), e);
        }
    }

    /**
     * Method to rollback a transaction
     *
     * @throws ExemptedInstException on errors encountered
     */
    protected abstract void rollback() throws ExemptedInstException;

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#getExemptedInstTrxValue
    */
	public IExemptedInstTrxValue getExemptedInstTrxValue (ITrxContext ctx)
		throws ExemptedInstException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_EXEMPT_INST);
		IExemptedInstTrxValue trxValue = new OBExemptedInstTrxValue();
		
		return (IExemptedInstTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#getExemptedInstTrxValueByTrxID
    */
	public IExemptedInstTrxValue getExemptedInstTrxValueByTrxID (ITrxContext ctx, String trxID)
		throws ExemptedInstException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_EXEMPT_INST_BY_TRXID);
		IExemptedInstTrxValue trxValue = new OBExemptedInstTrxValue();
		trxValue.setTransactionID (trxID);
		return (IExemptedInstTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#makerUpdateExemptedInst
    */
	public IExemptedInstTrxValue makerUpdateExemptedInst (ITrxContext ctx,
		IExemptedInstTrxValue trxVal, IExemptedInst[] exemptInsts) throws ExemptedInstException
	{
		if( exemptInsts == null )
		{
			throw new ExemptedInstException("Exempted Institution is null");
		}
		if( trxVal == null )
		{
			trxVal = new OBExemptedInstTrxValue();			
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ( trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
			 trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) 
		{		
		
			param.setAction (ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_INST);	
		}
		else
		{
			param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_INST);
		}		
		
		trxVal.setStagingExemptedInst (exemptInsts);
		return (IExemptedInstTrxValue) operate (constructTrxValue (ctx, trxVal), param);		
		
	}
   
    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#makerCloseExemptedInst
    */
	public IExemptedInstTrxValue makerCloseExemptedInst (ITrxContext ctx,
		IExemptedInstTrxValue trxVal) throws ExemptedInstException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_EXEMPT_INST);
		}
		else {				
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_EXEMPT_INST);
		}
		return (IExemptedInstTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#checkerApproveUpdateExemptedInst
    */
	public IExemptedInstTrxValue checkerApproveUpdateExemptedInst (
		ITrxContext ctx, IExemptedInstTrxValue trxVal) throws ExemptedInstException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_EXEMPT_INST);
		return (IExemptedInstTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}

    /**
    * @see com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy#checkerRejectUpdateExemptedInst
    */
	public IExemptedInstTrxValue checkerRejectUpdateExemptedInst (
		ITrxContext ctx, IExemptedInstTrxValue trxVal) throws ExemptedInstException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_EXEMPT_INST);
		return (IExemptedInstTrxValue) operate (constructTrxValue(ctx, trxVal), param);
	}


}