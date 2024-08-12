package com.integrosys.cms.host.eai;

/**
 * Base exception to be raised whenever there is record in persistent storage,
 * but EAI trying to create again. Normally compare the key between the
 * persistent storage and the EAI Message itself, not suppose to be CMS internal
 * key.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class RecordAlreadyExistsException extends EAIMessageException {

	private static final long serialVersionUID = -626069211944758883L;

	/**
	 * Default construct to provide the detailed message
	 * 
	 * @param msg the detailed message
	 */
	public RecordAlreadyExistsException(String msg) {
		super(msg);
	}

	/**
	 * Default construct to provide the detailed message and the cause for this
	 * exception raised
	 * 
	 * @param msg the detailed message
	 * @param cause the throwable cause for this exception raised
	 */
	public RecordAlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public abstract String getErrorCode();

}
