package com.integrosys.cms.app.recurrent.annexure.trx;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.bus.SBRecurrentBusManager;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchDao;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Abstract Operation to read Recurrent Checklist Transaction. Subclasses to
 * implement {@link #doInTrxManager(ICMSTrxValue)} will suffice.
 * 
 * @author Chong Jun Yong
 * @since 08.09.2008
 */
public abstract class AbstractAnnexureReadOperation extends CMSTrxOperation implements ITrxReadOperation {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private SBRecurrentBusManager recurrentBusManager;

	private SBRecurrentBusManager stagingRecurrentBusManager;
	
	/**
	 * @return the recurrentBusManager
	 */
	public SBRecurrentBusManager getRecurrentBusManager() {
		return recurrentBusManager;
	}

	/**
	 * @return the stagingRecurrentBusManager
	 */
	public SBRecurrentBusManager getStagingRecurrentBusManager() {
		return stagingRecurrentBusManager;
	}

	/**
	 * @param recurrentBusManager the recurrentBusManager to set
	 */
	public void setRecurrentBusManager(SBRecurrentBusManager recurrentBusManager) {
		this.recurrentBusManager = recurrentBusManager;
	}

	/**
	 * @param stagingRecurrentBusManager the stagingRecurrentBusManager to set
	 */
	public void setStagingRecurrentBusManager(SBRecurrentBusManager stagingRecurrentBusManager) {
		this.stagingRecurrentBusManager = stagingRecurrentBusManager;
	}

	public abstract ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException;

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(val);
			trxValue = doInTrxManager(trxValue);

			OBRecurrentCheckListTrxValue1 newValue = new OBRecurrentCheckListTrxValue1(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			logger.debug("Transaction Id : [" + val.getTransactionID() + "]Actual Reference: [" + actualRef
					+ "] , Staging Reference: [" + stagingRef + "]");

			if (StringUtils.isNotBlank(actualRef)) {
				IRecurrentCheckList actual = getRecurrentBusManager().getRecurrentCheckListByID(
						Long.parseLong(actualRef));
				newValue.setCheckList(actual);
			}
//			if (StringUtils.isNotBlank(stagingRef)) {
//				IRecurrentCheckList stage = getStagingRecurrentBusManager().getRecurrentCheckListByID(
//						Long.parseLong(stagingRef));
//				newValue.setStagingCheckList(stage);
//			}
						// testing
			ISystemBankBranchDao dao  =(ISystemBankBranchDao) BeanHouse.get("systemBankBranchDao");
			IRecurrentCheckList stage;
			try {
				long id=Long.parseLong(stagingRef);
				stage = dao.getRecurrentCheckList(id);
				
				
				IRecurrentCheckListItem[] recurrentCheckListItemsList =dao.getRecurrentCheckListItem(id);
				for(int i=0; i< recurrentCheckListItemsList.length;i++){
					IRecurrentCheckListSubItem[] recurrentCheckListSubItemsList=dao.getRecurrentCheckListSubItem(recurrentCheckListItemsList[i].getCheckListItemID());
					recurrentCheckListItemsList[i].setRecurrentCheckListSubItemList(recurrentCheckListSubItemsList);
				}
				stage.setCheckListItemList(recurrentCheckListItemsList);
				newValue.setStagingCheckList(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//
			return newValue;
		}
		catch (Throwable t) {
			logger.error("error encountered when reading transaction [" + val + "]", t);
			throw new TrxOperationException("error encountered when reading transaction [" + val + "]", t);
		}
	}
}
