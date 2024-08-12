/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/AbstractExemptFacilityProxy.java,v 1.13 2003/08/15 08:36:05 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.OBExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.ExemptFacilityGroupTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2003/08/15 08:36:05 $
 * Tag: $Name:  $
 */
public abstract class AbstractExemptFacilityProxy
        implements IExemptFacilityProxy {

    /**
     * Formulate the Exempt Facility trx object
     * @param anITrxContext - ITrxContext
     * @param aTrxValue -
     * @return IExempt FacilityTrxValue - the Exempt Facility trx interface formulated
     */
    protected IExemptFacilityGroupTrxValue constructTrxValue(
            ITrxContext anITrxContext, IExemptFacilityGroupTrxValue aTrxValue)
    {
        aTrxValue.setTrxContext(anITrxContext);
        aTrxValue.setTransactionType(ICMSConstant.INSTANCE_EXEMPT_FACILITY_GROUP);
        return aTrxValue;
    }


    /**
     * @param anICMSTrxValue
     * @param anOBCMSTrxParameter - OBCMSTrxParameter
     * @return ICMSTrxResult - the trx result interface
     */
    protected ITrxValue operate(ICMSTrxValue anICMSTrxValue,OBCMSTrxParameter anOBCMSTrxParameter)
            throws ExemptFacilityException {
        try {
            ITrxController controller = (new ExemptFacilityGroupTrxControllerFactory()).getController(
                    anICMSTrxValue, anOBCMSTrxParameter);
            if (controller == null) {
                throw new ExemptFacilityException("ITrxController is null!!!");
            }
            ITrxResult result = controller.operate(anICMSTrxValue,
                    anOBCMSTrxParameter);
            ITrxValue obj = result.getTrxValue();
            return obj;
        } catch (TransactionException e) {
            rollback();
            throw new ExemptFacilityException(e);
        } catch (Exception ex) {
            rollback();
            throw new ExemptFacilityException(ex.toString());
        }
    }

    protected abstract void rollback() throws ExemptFacilityException;

    /**
     * Get the transaction value containing ExemptFacilityGroup
     * This method will create a transaction if it is not already present, when this
     * module is first used by user and system is first setup.
     */
    public IExemptFacilityGroupTrxValue getExemptFacilityGroup(ITrxContext ctx, long groupID)
            throws ExemptFacilityException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_EXEMPT_FACILITY);
		IExemptFacilityGroupTrxValue trxValue = new OBExemptFacilityGroupTrxValue();

		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxValue), param);
    }

    public IExemptFacilityGroupTrxValue makerUpdateExemptFacility (ITrxContext ctx,
		IExemptFacilityGroupTrxValue trxVal, IExemptFacilityGroup exemptFacilityGroups) throws ExemptFacilityException
	{
/*		if( exemptFacilityGroups == null )
		{
			throw new ExemptFacilityException("Exempted Facility Group is null");
		}*/
		if( trxVal == null )
		{
			trxVal = new OBExemptFacilityGroupTrxValue();
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
        DefaultLogger.debug(this, "trxVal.getStatus()  " + trxVal.getStatus());
		if ( trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
			 trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE))
		{
//            System.out.println("INside CREATE ");
			param.setAction (ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_FACILITY);
		}
		else
		{
//            System.out.println("INside UPDATE  ");
			param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_FACILITY);
		}

		trxVal.setStagingExemptFacilityGroup (exemptFacilityGroups);
		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxVal), param);

	}

	public IExemptFacilityGroupTrxValue getExemptFacilityTrxValue (ITrxContext ctx)
		throws ExemptFacilityException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_EXEMPT_FACILITY);
		IExemptFacilityGroupTrxValue trxValue = new OBExemptFacilityGroupTrxValue();

		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}

    public IExemptFacilityGroupTrxValue getExemptFacilityTrxValueByTrxID (ITrxContext ctx, String trxID)
		throws ExemptFacilityException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_EXEMPT_FACILITY_GROUP_BY_TRXID);
		IExemptFacilityGroupTrxValue trxValue = new OBExemptFacilityGroupTrxValue();
		trxValue.setTransactionID (String.valueOf(trxID));
		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}

	public IExemptFacilityGroupTrxValue makerCloseExemptFacility (ITrxContext ctx,
		IExemptFacilityGroupTrxValue trxVal) throws ExemptFacilityException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_EXEMPT_FACILITY);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_EXEMPT_FACILITY);
		}
		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}

	public IExemptFacilityGroupTrxValue checkerApproveExemptFacility (
		ITrxContext ctx, IExemptFacilityGroupTrxValue trxVal) throws ExemptFacilityException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_EXEMPT_FACILITY);
		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}

	public IExemptFacilityGroupTrxValue checkerRejectExemptFacility (
		ITrxContext ctx, IExemptFacilityGroupTrxValue trxVal) throws ExemptFacilityException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_EXEMPT_FACILITY);
		return (IExemptFacilityGroupTrxValue) operate (constructTrxValue(ctx, trxVal), param);
	}

}
