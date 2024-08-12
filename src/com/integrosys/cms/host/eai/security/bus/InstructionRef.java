/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id: InstructionRef.java,v 1.2 2003/10/01 08:17:24 hshii Exp $
 */

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/
package com.integrosys.cms.host.eai.security.bus;

import com.integrosys.cms.host.eai.OriginatingBookingLocation;
import com.integrosys.cms.host.eai.StandardCode;

/**
 * Class InstructionRef.
 * 
 * @version $Revision: 1.2 $ $Date: 2003/10/01 08:17:24 $
 */
public class InstructionRef implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _instructionRefMapId
	 */
	private int _instructionRefMapId;

	/**
	 * keeps track of state for field: _has_instructionRefMapId
	 */
	private boolean _has_instructionRefMapId;

	/**
	 * Field _instructionRefId
	 */
	private int _instructionRefId;

	/**
	 * keeps track of state for field: _instructionRefId
	 */
	private boolean _has_instructionRefId;

	/**
	 * Field _instructionRefNumber
	 */
	private java.lang.String _instructionRefNumber;

	/**
	 * Field _instructionRefType
	 */
	private StandardCode _instructionRefType;

	/**
	 * Field _instructionBookingLocation
	 */
	private OriginatingBookingLocation _instructionBookingLocation;

	/**
	 * Field _instructionRefApprovalDate
	 */
	private java.lang.String _instructionRefApprovalDate;

	/**
	 * Field _comments
	 */
	private java.lang.String _comments;

	/**
	 * Field _userGroupInd
	 */
	private java.lang.String _userGroupInd;

	/**
	 * Field _updateStatusIndicator
	 */
	private java.lang.String _updateStatusIndicator;

	/**
	 * Field _changeIndicator
	 */
	private java.lang.String _changeIndicator;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public InstructionRef() {
		super();
	} // -- InstructionRef()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'comments'.
	 * 
	 * @return the value of field 'comments'.
	 */
	public java.lang.String getComments() {
		return this._comments;
	} // -- java.lang.String getComments()

	/**
	 * Returns the value of field 'instructionBookingLocation'.
	 * 
	 * @return the value of field 'instructionBookingLocation'.
	 */
	public OriginatingBookingLocation getInstructionBookingLocation() {
		return this._instructionBookingLocation;
	} // -- InstructionBookingLocation getInstructionBookingLocation()

	/**
	 * Returns the value of field 'instructionRefApprovalDate'.
	 * 
	 * @return the value of field 'instructionRefApprovalDate'.
	 */
	public java.lang.String getInstructionRefApprovalDate() {
		return this._instructionRefApprovalDate;
	} // -- java.lang.String getInstructionRefApprovalDate()

	/**
	 * Returns the value of field 'instructionRefId'.
	 * 
	 * @return the value of field 'instructionRefId'.
	 */
	public int getInstructionRefId() {
		return this._instructionRefId;
	} // -- int getInstructionRefId()

	/**
	 * Returns the value of field 'instructionRefNumber'.
	 * 
	 * @return the value of field 'instructionRefNumber'.
	 */
	public java.lang.String getInstructionRefNumber() {
		return this._instructionRefNumber;
	} // -- java.lang.String getInstructionRefNumber()

	/**
	 * Returns the value of field 'instructionRefType'.
	 * 
	 * @return the value of field 'instructionRefType'.
	 */
	public StandardCode getInstructionRefType() {
		return this._instructionRefType;
	} // -- InstructionRefType getInstructionRefType()

	/**
	 * Returns the value of field 'updateStatusIndicator'.
	 * 
	 * @return the value of field 'updateStatusIndicator'.
	 */
	public java.lang.String getUpdateStatusIndicator() {
		return this._updateStatusIndicator;
	} // -- java.lang.String getUpdateStatusIndicator()

	/**
	 * Returns the value of field 'userGroupInd'.
	 * 
	 * @return the value of field 'userGroupInd'.
	 */
	public java.lang.String getUserGroupInd() {
		return this._userGroupInd;
	} // -- java.lang.String getUserGroupInd()

	/**
	 * Method hasInstructionRefId
	 */
	public boolean hasInstructionRefId() {
		return this._has_instructionRefId;
	} // -- boolean hasInstructionRefId()

	/**
	 * Returns the value of field 'instructionRefMapId'.
	 * 
	 * @return the value of field 'instructionRefMapId'.
	 */
	public int getInstructionRefMapId() {
		return this._instructionRefMapId;
	} // -- java.lang.String getInstructionRefMapId()

	/**
	 * Method hasInstructionRefMapId
	 */
	public boolean hasInstructionRefMapId() {
		return this._has_instructionRefMapId;
	} // -- boolean hasInstructionRefMapId()

	/**
	 * Returns the value of field 'changeIndicator'.
	 * 
	 * @return the value of field 'changeIndicator'.
	 */
	public java.lang.String getChangeIndicator() {
		return this._changeIndicator;
	} // -- java.lang.String getChangeIndicator()

	/**
	 * Sets the value of field 'comments'.
	 * 
	 * @param comments the value of field 'comments'.
	 */
	public void setComments(java.lang.String comments) {
		this._comments = comments;
	} // -- void setComments(java.lang.String)

	/**
	 * Sets the value of field 'instructionBookingLocation'.
	 * 
	 * @param instructionBookingLocation the value of field
	 *        'instructionBookingLocation'.
	 */
	public void setInstructionBookingLocation(OriginatingBookingLocation instructionBookingLocation) {
		this._instructionBookingLocation = instructionBookingLocation;
	} // -- void setInstructionBookingLocation(InstructionBookingLocation)

	/**
	 * Sets the value of field 'instructionRefApprovalDate'.
	 * 
	 * @param instructionRefApprovalDate the value of field
	 *        'instructionRefApprovalDate'.
	 */
	public void setInstructionRefApprovalDate(java.lang.String instructionRefApprovalDate) {
		this._instructionRefApprovalDate = instructionRefApprovalDate;
	} // -- void setInstructionRefApprovalDate(java.lang.String)

	/**
	 * Sets the value of field 'instructionRefMapId'.
	 * 
	 * @param instructionRefMapId the value of field 'instructionRefMapId'
	 */
	public void setInstructionRefMapId(int instructionRefMapId) {
		this._instructionRefMapId = instructionRefMapId;
		this._has_instructionRefMapId = true;
	} // -- void setInstructionRefMapId(int)

	/**
	 * Sets the value of field 'instructionRefId'.
	 * 
	 * @param instructionRefId the value of field 'instructionRefId'
	 */
	public void setInstructionRefId(int instructionRefId) {
		this._instructionRefId = instructionRefId;
		this._has_instructionRefId = true;
	} // -- void setInstructionRefId(int)

	/**
	 * Sets the value of field 'instructionRefNumber'.
	 * 
	 * @param instructionRefNumber the value of field 'instructionRefNumber'.
	 */
	public void setInstructionRefNumber(java.lang.String instructionRefNumber) {
		this._instructionRefNumber = instructionRefNumber;
	} // -- void setInstructionRefNumber(java.lang.String)

	/**
	 * Sets the value of field 'instructionRefType'.
	 * 
	 * @param instructionRefType the value of field 'instructionRefType'.
	 */
	public void setInstructionRefType(StandardCode instructionRefType) {
		this._instructionRefType = instructionRefType;
	} // -- void setInstructionRefType(InstructionRefType)

	/**
	 * Sets the value of field 'updateStatusIndicator'.
	 * 
	 * @param updateStatusIndicator the value of field 'updateStatusIndicator'.
	 */
	public void setUpdateStatusIndicator(java.lang.String updateStatusIndicator) {
		this._updateStatusIndicator = updateStatusIndicator;
	} // -- void setUpdateStatusIndicator(java.lang.String)

	/**
	 * Sets the value of field 'userGroupInd'.
	 * 
	 * @param userGroupInd the value of field 'userGroupInd'.
	 */
	public void setUserGroupInd(java.lang.String userGroupInd) {
		this._userGroupInd = userGroupInd;
	} // -- void setUserGroupInd(java.lang.String)

	/**
	 * Sets the value of field 'changeIndicator'.
	 * 
	 * @param changeIndicator the value of field 'changeIndicator'.
	 */
	public void setChangeIndicator(java.lang.String changeIndicator) {
		this._changeIndicator = changeIndicator;
	} // -- void setChangeIndicator(java.lang.String)

}
