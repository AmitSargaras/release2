package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerRelationshipMgrInsertFileOperation extends AbstractRelationshipMgrTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerRelationshipMgrInsertFileOperation()
    {
        super();
    }

	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_FILE_INSERT;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
	    IRelationshipMgrTrxValue trxValue = super.getRelationshipMgrTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingFileMapperID()==null));

	    trxValue = createStagingFileId(trxValue);
		trxValue = createFileIdTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IRelationshipMgrTrxValue createFileIdTransaction(IRelationshipMgrTrxValue anICCIRelationshipMgrTrxValue) throws TrxOperationException,RelationshipMgrException
	{
		try
		{
			anICCIRelationshipMgrTrxValue = prepareInsertTrxValue(anICCIRelationshipMgrTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCIRelationshipMgrTrxValue);
            OBRelationshipMgrTrxValue holidayTrxValue = new OBRelationshipMgrTrxValue (trxValue);
            holidayTrxValue.setStagingFileMapperID(anICCIRelationshipMgrTrxValue.getStagingFileMapperID());
            holidayTrxValue.setFileMapperID(anICCIRelationshipMgrTrxValue.getFileMapperID());
	        return holidayTrxValue;
		}
		catch(RelationshipMgrException se)
		{
			throw new RelationshipMgrException("Error in Create RelationshipMgr Operation ");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
