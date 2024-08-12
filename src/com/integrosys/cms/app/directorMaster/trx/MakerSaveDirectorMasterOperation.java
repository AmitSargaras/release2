package com.integrosys.cms.app.directorMaster.trx;


import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */
public class MakerSaveDirectorMasterOperation extends AbstractDirectorMasterTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveDirectorMasterOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_DIRECTOR_MASTER;
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
	    IDirectorMasterTrxValue trxValue = super.getDirectorMasterTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingDirectorMaster()==null));

	    trxValue = createStagingDirectorMaster(trxValue);
		trxValue = createDirectorMasterTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IDirectorMasterTrxValue createDirectorMasterTransaction(IDirectorMasterTrxValue anICCDirectorMasterTrxValue) throws TrxOperationException,DirectorMasterException
	{
		try
		{
            anICCDirectorMasterTrxValue = prepareTrxValue(anICCDirectorMasterTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCDirectorMasterTrxValue);
            OBDirectorMasterTrxValue directorMasterTrxValue = new OBDirectorMasterTrxValue (trxValue);
            directorMasterTrxValue.setStagingDirectorMaster (anICCDirectorMasterTrxValue.getStagingDirectorMaster());
            directorMasterTrxValue.setDirectorMaster(anICCDirectorMasterTrxValue.getDirectorMaster());
	        return directorMasterTrxValue;
		}
		catch(DirectorMasterException se)
		{
			throw new DirectorMasterException("Error in Create System Bank Branch Operation ");
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
