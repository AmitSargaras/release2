/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/TATValidator.java,v 1.19 2005/04/05 05:22:54 cwtan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Arrays;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;

/**
 * This is the class that contains the business logic pertaining to TAT.
 * 
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2005/04/05 05:22:54 $ Tag: $Name: $
 */
public class TATValidator {
	/**
	 * This method validates the correctness of the TAT entry, and prepares it
	 * by setting the reference date.
	 * 
	 * @param tat is the new ITATEntry to be added
	 * @param entries is the ITATEntry[] to add the new TAT entry into
	 * @return ITATEntry[] containing the newly added TAT entry with reference
	 *         date
	 * @throws LimitException if TAT violation is detected
	 */
	public static ITATEntry[] processTAT(ITATEntry tat, ITATEntry[] entries) throws LimitException {
		if (null == tat) {
			throw new LimitException("ITATEntry is null!");
		}

		String code = tat.getTATServiceCode();
		ITATEntry[] newEntries = null;

		if (isValidationRequired(entries)) {
			// sort the entries first
			Arrays.sort(entries, new TATComparator());
			// get the last entry
			// ITATEntry last = entries[entries.length - 1];
			ITATEntry last = getLastValidTATEntry(entries);
			String lastCode = "";
			if (last != null) {
				lastCode = last.getTATServiceCode();
			}
			// big if/else here.
			if (code.equals(ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL)) {
				// no check required
				if (last != null) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					tat.setReferenceDate(tat.getTATStamp());
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)) {
				if (lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are not allowed to Send Draft BFL when last code is : " + lastCode);
					e.setErrorCode(ICMSErrorCodes.BFL_SEND_DRAFT_NOT_ALLOWED);
					throw e;
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)) {
				if (lastCode.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are not allowed to Ack Recieve BFL when last code is : " + lastCode);
					e.setErrorCode(ICMSErrorCodes.BFL_ACK_REC_DRAFT_NOT_ALLOWED);
					throw e;
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)) {
				if (lastCode.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are not allowed to Issue Clean-type BFL when last code is : "
									+ lastCode);
					e.setErrorCode(ICMSErrorCodes.BFL_ISSUE_CLEAN_NOT_ALLOWED);
					throw e;
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL)) {
				if (lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_CUSTOMER_ACCEPT_BFL)) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are not allowed to Perform Customer Accept BFL when last code is : "
									+ lastCode);
					e.setErrorCode(ICMSErrorCodes.BFL_CUSTOMER_ACCEPT_NOT_ALLOWED);
					throw e;
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_GEN_SCC)) {
				// no check required
				if (last != null) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					tat.setReferenceDate(tat.getTATStamp());
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_GEN_CCC)) {
				// no check required
				if (last != null) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					tat.setReferenceDate(tat.getTATStamp());
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_COMPLETE_BCA)) {
				// no check required
				if (last != null) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					tat.setReferenceDate(tat.getTATStamp());
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)) {
				if (lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_SEND_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ACK_REC_DRAFT_BFL)
						|| lastCode.equals(ICMSConstant.TAT_CODE_ISSUE_CLEAN_BFL) // ||
				//lastCode.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)
				) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are attempting to Issue Draft BFL when either Issue Draft or Special Issue already exist!");
					e.setErrorCode(ICMSErrorCodes.BFL_ISSUE_DRAFT_NOT_ALLOWED);
					throw e;
				}
			}
			else if (code.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)) { // Special
																					// handling
				if (lastCode.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)) {
					tat.setReferenceDate(last.getTATStamp());
				}
				else {
					LimitException e = new LimitException(
							"Invalid TAT Entry! You are attempting to Special Issue BFL when either Issue Draft or Special Issue already exist!");
					e.setErrorCode(ICMSErrorCodes.BFL_SPECIAL_ISSUE_NOT_ALLOWED);
					throw e;
				}
			}
			else {
				LimitException e = new LimitException("Invalid TAT Entry! Unknown TAT Code: " + code);
				e.setErrorCode(ICMSErrorCodes.BFL_UNKNOWN_TAT_CODE);
				throw e;
			}

			// since no error, continue
			newEntries = new ITATEntry[entries.length + 1];
			System.arraycopy(entries, 0, newEntries, 0, entries.length);
			newEntries[newEntries.length - 1] = tat; // assigning the new entry
		}
		else {
			// check that this is eitehr the issue draft, or special issue
			// cleantype
			if (code.equals(ICMSConstant.TAT_CODE_ISSUE_DRAFT_BFL)
					|| code.equals(ICMSConstant.TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL)
					|| code.equals(ICMSConstant.TAT_CODE_GEN_CCC) || code.equals(ICMSConstant.TAT_CODE_GEN_SCC)
					|| code.equals(ICMSConstant.TAT_CODE_COMPLETE_BCA)
					|| code.equals(ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL)) {

				tat.setReferenceDate(tat.getTATStamp()); // since this is a new
															// entry, set both
															// dates to be the
															// same
				if ((entries == null) || (entries.length == 0)) {
					newEntries = new ITATEntry[] { tat };
				}
				else {
					newEntries = new ITATEntry[entries.length + 1];
					System.arraycopy(entries, 0, newEntries, 0, entries.length);
					newEntries[newEntries.length - 1] = tat; // assigning the
																// new entry
				}
			}
			else {
				LimitException e = new LimitException("Invalid TAT Entry! Code received is: " + code);
				e.setErrorCode(ICMSErrorCodes.BFL_INVALID_FIRST_CODE);
				throw e;
			}
		}
		return newEntries;
	}

	/**
	 * Helper method to process error
	 */
	private static boolean isValidationRequired(ITATEntry[] entries) {
		if ((entries == null) || (entries.length == 0)) {
			return false;
		}

		for (int ii = 0; ii < entries.length; ii++) {
			if (!(entries[ii].getTATServiceCode().equals(ICMSConstant.TAT_CODE_GEN_CCC)
					|| entries[ii].getTATServiceCode().equals(ICMSConstant.TAT_CODE_GEN_SCC)
					|| entries[ii].getTATServiceCode().equals(ICMSConstant.TAT_CODE_COMPLETE_BCA) || entries[ii]
					.getTATServiceCode().equals(ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to get the last TAT entry based on the flow of BFL.
	 * 
	 * @param entries a list of TAT entries
	 * @return the last TAT entry
	 */
	private static ITATEntry getLastValidTATEntry(ITATEntry[] entries) {
		for (int ii = entries.length - 1; ii >= 0; ii--) {
			if (!(ICMSConstant.TAT_CODE_GEN_CCC.equals(entries[ii].getTATServiceCode())
					|| ICMSConstant.TAT_CODE_GEN_SCC.equals(entries[ii].getTATServiceCode())
					|| ICMSConstant.TAT_CODE_COMPLETE_BCA.equals(entries[ii].getTATServiceCode()) || ICMSConstant.TAT_CODE_PRINT_REMINDER_BFL
					.equals(entries[ii].getTATServiceCode()))) {
				return entries[ii];
			}
		}
		return null;
	}
}