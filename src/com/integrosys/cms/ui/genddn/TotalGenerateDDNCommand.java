/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.BaseCurrency;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/04 07:21:11 $ Tag: $Name: $
 */
public class TotalGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public TotalGenerateDDNCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			IDDN cert = (IDDN) map.get("cert");
			cert = computeTotalAmounts(cert);
			resultMap.put("cert", cert);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Helper method to compute total limit amount.
	 * 
	 * @param anIDDN of type IDDN
	 * @return an IDDN
	 * @throws DDNException on error computing the total amount
	 */
	private IDDN computeTotalAmounts(IDDN anIDDN) throws DDNException {
		if (anIDDN == null) {
			throw new DDNException("The IDDN is null !!!");
		}

		IDDNItem[] itemList = anIDDN.getDDNItemList();
		int itemCount = itemList == null ? 0 : itemList.length;

		CurrencyCode baseCurrency = BaseCurrency.getCurrencyCode();
		long totalDDNAmt = 0;
		long cleanDDNAmt = 0;
		long notCleanDDNAmt = 0;
		long amt = 0;
		for (int ii = 0; ii < itemCount; ii++) {
			if (itemList[ii].getIsDDNIssuedInd() && (itemList[ii].getDDNAmount() != null)) {
				if (!itemList[ii].isInnerLimit()) {
					amt = convertAmount(itemList[ii].getDDNAmount(), baseCurrency);
					if (itemList[ii].isCleanType()) {
						cleanDDNAmt = cleanDDNAmt + amt;
					}
					else {
						notCleanDDNAmt = notCleanDDNAmt + amt;
					}
					totalDDNAmt = totalDDNAmt + amt;
				}
			}
		}
		anIDDN.setCleanDDNAmount(new Amount(cleanDDNAmt, baseCurrency));
		anIDDN.setDDNAmount(new Amount(notCleanDDNAmt, baseCurrency));
		anIDDN.setTotalDDNAmount(new Amount(totalDDNAmt, baseCurrency));
		return anIDDN;
	}

	/**
	 * Helper method to convert amount to the given currency.
	 * 
	 * @param anAmount Amount to be converted
	 * @param aCurrencyCode of type CurrencyCode
	 * @return long
	 * @throws DDNException on error during conversion
	 */
	private long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws DDNException {
		try {
			ForexHelper forex = ForexHelper.getInstance();
			Amount amt = forex.convert(anAmount, aCurrencyCode);
			if (amt == null) {
				return 0;
			}
			return Math.round(amt.getAmount());
		}
		catch (Exception e) {
			throw new DDNException("Exception in convertAmount:" + e.toString());
		}
	}
}
