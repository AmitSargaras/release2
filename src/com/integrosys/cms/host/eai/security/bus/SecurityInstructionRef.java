/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityInstructionRef.java,v 1.1 2003/10/09 12:41:39 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus;


/**
 * This class represents security instruction reference.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/09 12:41:39 $ Tag: $Name: $
 */
public class SecurityInstructionRef extends InstructionRef {
	private long _securityId;

	private int _instructionRefMapId;

	private String _changeIndicator;

	/**
	 * Default constructor.
	 */
	public SecurityInstructionRef() {
		super();
	}

	/**
	 * Get security id.
	 * 
	 * @return long
	 */
	public long getSecurityId() {
		return _securityId;
	}

	/**
	 * Set security id.
	 * 
	 * @param securityId of type long
	 */
	public void setSecurityId(long securityId) {
		_securityId = securityId;
	}

	/**
	 * Get instruction reference map id.
	 * 
	 * @return int
	 */
	public int getInstructionRefMapId() {
		return _instructionRefMapId;
	}

	/**
	 * Set instruction reference map id.
	 * 
	 * @param instructionRefMapId of type int
	 */
	public void setInstructionRefMapId(int instructionRefMapId) {
		_instructionRefMapId = instructionRefMapId;
	}

	/**
	 * Get change indicator.
	 * 
	 * @return String
	 */
	public String getChangeIndicator() {
		return _changeIndicator;
	}

	/**
	 * Set change indicator.
	 * 
	 * @param changeIndicator of type String
	 */
	public void setChangeIndicator(String changeIndicator) {
		_changeIndicator = changeIndicator;
	}
}
