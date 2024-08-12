/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryApproveOperation.java
 *
 * Created on February 7, 2007, 2:31:19 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.trx;

import java.util.Collection;
import java.util.Iterator;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 7, 2007 Time: 2:31:19 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntryApproveOperation extends AbstractCommonCodeEntriesTrxOperation {

	private static final long serialVersionUID = -2451427757519219050L;

	public String getOperationName() {
		return ICMSConstant.COMMON_CODE_ENTRY_APPROVE;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICommonCodeEntriesTrxValue trxValue = super.getCommonCodeEntriesTrxValue(value);
		// String categoryCode = getCommonCategoryCode(trxValue);

		trxValue = updateActualCommonCodeEntries(trxValue);
		trxValue = super.updateCommonCodeEntriesTrx(trxValue);

		// DefaultLogger.debug(this, "Category Code to refresh :'" +
		// categoryCode + "'");
		// CommonCodeEntryUtil.synchronizeCommonCode(categoryCode.trim());

		return super.prepareResult(trxValue);
	}

	private String getCommonCategoryCode(ICommonCodeEntriesTrxValue trxValue) {
		String categoryCode = null;
		ICommonCodeEntries actualValue = trxValue.getStagingCommonCodeEntries();

		Collection collection = null;
		if (actualValue != null) {
			collection = actualValue.getEntries();
		}

		if ((collection != null) && !collection.isEmpty()) {
			Iterator iter = collection.iterator();

			if (iter.hasNext()) {
				OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();
				categoryCode = obEntry.getCategoryCode();
			}
		}
		return categoryCode;
	}

	public String getCommonCategoryCodeAfterApproved(ICommonCodeEntriesTrxValue trxValue) {
		String categoryCode = null;
		ICommonCodeEntries actualValue = trxValue.getCommonCodeEntries(); // after
		// approved
		// by
		// checker

		Collection collection = null;
		if (actualValue != null) {
			collection = actualValue.getEntries();
		}

		if ((collection != null) && !collection.isEmpty()) {
			Iterator iter = collection.iterator();

			if (iter.hasNext()) {
				OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();
				categoryCode = obEntry.getCategoryCode();

				// DefaultLogger.debug ( this , "AcessorUtil : " +
				// AccessorUtil.printMethodValue ( obEntry ) ) ;
			}
		}
		return categoryCode;
	}

}
