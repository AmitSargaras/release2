/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ReadExemptFacilityGroupOperation.java,v 1.11 2003/08/19 07:52:43 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.*;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamDAO;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;

/**
 * This operation allows a maker to update a checklist
 *
 * @author $Author: btchng $
 * @version $Revision: 1.11 $
 * @since $Date: 2003/08/19 07:52:43 $
 * Tag: $Name:  $
 */
public class ReadExemptFacilityGroupOperation
        extends AbstractExemptFacilityTrxOperation
        implements ITrxReadOperation {

    /**
     * Defaulc Constructor
     */
    public ReadExemptFacilityGroupOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_EXEMPT_FACILITY;
    }


    /**
     * This method is used to read a transaction object given a transaction ID
     *
     * @param val is the ITrxValue object containing the parameters required for
     * retrieving a record, such as the transaction ID.
     * @return ITrxValue containing the requested data.
     * @throws com.integrosys.base.businfra.transaction.TransactionException if any other errors occur.
     */
    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        DefaultLogger.debug(this, " $$$$$$$$$$$$$$$$$$$$$$$$$ inside geTransaction ");
        try
        {
            ICMSTrxValue cmsTrxValue = super.getCMSTrxValue (val);
//            IExemptFacilityGroupTrxValue trxVal = null;
            IExemptFacilityGroup stageGrp = null;
            IExemptFacilityGroup actualGrp = null;
//			IExemptFacility[] stageExemptedInst = null;
			IExemptFacility[] actualExemptedInst = null;

            IExemptFacilityGroupTrxValue vv = (IExemptFacilityGroupTrxValue)val;

            DefaultLogger.debug(this, "reference ID = " + vv.getReferenceID());
            DefaultLogger.debug(this, "trx id = " + vv.getTransactionID());

            String trxId = vv.getTransactionID();
            SBCMSTrxManager trxManager = getTrxManager();

            // Transaction ID is null - from listing
            if (trxId == null || trxId.equals("")) {
                // Getting the Transaction for Listing page
                CreditRiskParamDAO dao = new CreditRiskParamDAO();
                String strTrxID = dao.getExemptFacilityTrxID();
//                    System.out.println("inside trxID is null");
                cmsTrxValue = trxManager.getTransaction(strTrxID);
            }
            else
            {
                cmsTrxValue = trxManager.getTransaction(trxId);
            }

            DefaultLogger.debug(this,"##################################################################################");
            DefaultLogger.debug(this,"cmsTrxValue   = "+cmsTrxValue);
            DefaultLogger.debug(this,"##################################################################################");
            vv = new OBExemptFacilityGroupTrxValue(cmsTrxValue);

            String actualRefID = vv.getReferenceID();
            String stagingRefID = vv.getStagingReferenceID();

//            System.out.println("1");
            vv = new OBExemptFacilityGroupTrxValue (cmsTrxValue);

//            System.out.println("2");

            if (actualRefID != null) {
                // get actual Exempted Institution
                SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getActualFeedBusManager();
                actualGrp = mgr.getExemptFacilityGroupByGroupID (Long.parseLong (actualRefID));
//                System.out.println("3");
                if (actualGrp != null)
                    actualExemptedInst = actualGrp.getExemptFacility();
//                System.out.println("4");
                if (actualExemptedInst != null && actualExemptedInst.length> 0 && actualExemptedInst[0] != null)
                    actualGrp.setExemptFacilityGroupID(actualExemptedInst[0].getGroupID());
//                System.out.println("5");
                vv.setExemptFacilityGroup (actualGrp);
            }

            if(stagingRefID!=null)
            {
                SBExemptFacilityBusManager stageMgr = ExemptFacilityBusManagerFactory.getStagingFeedBusManager();
//                System.out.println("6");
                stageGrp = stageMgr.getExemptFacilityGroupByGroupID (Long.parseLong (stagingRefID));
//                System.out.println("7");
                vv.setStagingExemptFacilityGroup (stageGrp);
            }
            DefaultLogger.debug(this,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            DefaultLogger.debug(this," TRXVAL " + vv);
            DefaultLogger.debug(this,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            return vv;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }

}