package com.integrosys.cms.app.image.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the image package. This exception is thrown for
 * any errors during image upload processing
 * 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 02:39:04 $ Tag: $Name: $
 */

public class ImageUploadException extends OFAException {

	/**
	 * Constructor
	 * 
	 * @param msg -
	 *            the message string
	 */
	public ImageUploadException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * 
	 * @param msg -
	 *            message String
	 * @param t -
	 *            Throwable
	 */
	public ImageUploadException(String msg, Throwable t) {
		super(msg, t);
	}
}
