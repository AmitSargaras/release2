/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/propertyindex/AbstractPropertyIndexFeedProxy.java,v 1.1 2003/08/20 10:59:30 btchng Exp $
 */
package com.integrosys.cms.app.feed.proxy.propertyindex;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.propertyindex.OBPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.propertyindex.PropertyIndexFeedGroupTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:30 $ Tag: $Name: $
 */
public abstract class AbstractPropertyIndexFeedProxy implements IPropertyIndexFeedProxy {

	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param aTrxValue -
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	protected IPropertyIndexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IPropertyIndexFeedGroupTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_PROPERTY_INDEX_FEED_GROUP);

		return aTrxValue;
	}

	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws PropertyIndexFeedGroupException {
		try {
			ITrxController controller = (new PropertyIndexFeedGroupTrxControllerFactory()).getController(
					anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new PropertyIndexFeedGroupException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			rollback();
			throw new PropertyIndexFeedGroupException(e);
		}
		catch (Exception ex) {
			rollback();
			throw new PropertyIndexFeedGroupException(ex.toString());
		}
	}

	protected abstract void rollback() throws PropertyIndexFeedGroupException;

	/**
	 * Formulate the checklist Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @return IPropertyIndexFeedGroupTrxValue - the checklist trx interface
	 *         formulated
	 */
	protected IPropertyIndexFeedGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IPropertyIndexFeedGroup aFeedGroup) {

		IPropertyIndexFeedGroupTrxValue feedGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			feedGroupTrxValue = new OBPropertyIndexFeedGroupTrxValue(anICMSTrxValue);
		}
		else {
			feedGroupTrxValue = new OBPropertyIndexFeedGroupTrxValue();
		}
		feedGroupTrxValue = formulateTrxValue(anITrxContext, (IPropertyIndexFeedGroupTrxValue) feedGroupTrxValue);

		feedGroupTrxValue.setStagingPropertyIndexFeedGroup(aFeedGroup);

		return feedGroupTrxValue;
	}

	/**
	 * Get the transaction value containing PropertyIndexFeedGroup This method
	 * will create a transaction if it is not already present, when this module
	 * is first used by user and system is first setup.
	 */
	public IPropertyIndexFeedGroupTrxValue getPropertyIndexFeedGroup(long groupID)
			throws PropertyIndexFeedGroupException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}

	// /**
	// * Get the transaction value containing PropertyIndexFeedGroup by trxID
	// * @param trxID the transaction ID
	// * @return the trx value containing IPropertyIndexFeedGroupTrxValue
	// */
	// public IPropertyIndexFeedGroupTrxValue
	// getPropertyIndexFeedGroupByTrxID(long trxID)
	// throws PropertyIndexFeedGroupException {
	// return null; //To change body of implemented methods use Options | File
	// Templates.
	// }

}
