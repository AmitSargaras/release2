package com.integrosys.cms.host.eai.support;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class EAIReferenceNumberFormatter implements ISequenceFormatter {
	/**
	 *This class provides the interface that number formatter must implement.
	 */
	public String formatSeq(String seq) throws Exception {

		StringBuffer pad = new StringBuffer();
		for (int i = (9 - seq.length()); i > 0; i--) {
			pad.append("0");
		}

		return pad.toString() + seq;

	}

}
