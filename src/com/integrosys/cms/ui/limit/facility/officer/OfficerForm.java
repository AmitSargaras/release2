package com.integrosys.cms.ui.limit.facility.officer;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class OfficerForm extends FacilityMainForm {

	private String relationshipCodeEntryCode;// Relationship Code

	private String sequenceNumber;// Officer Sequence No.

	private String officerTypeEntryCode;// Officer Type

	private String officerEntryCode;// Officer

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.officer.OfficerMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	/**
	 * @return the relationshipCodeEntryCode
	 */
	public String getRelationshipCodeEntryCode() {
		return relationshipCodeEntryCode;
	}

	/**
	 * @param relationshipCodeEntryCode the relationshipCodeEntryCode to set
	 */
	public void setRelationshipCodeEntryCode(String relationshipCodeEntryCode) {
		this.relationshipCodeEntryCode = relationshipCodeEntryCode;
	}

	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the officerTypeEntryCode
	 */
	public String getOfficerTypeEntryCode() {
		return officerTypeEntryCode;
	}

	/**
	 * @param officerTypeEntryCode the officerTypeEntryCode to set
	 */
	public void setOfficerTypeEntryCode(String officerTypeEntryCode) {
		this.officerTypeEntryCode = officerTypeEntryCode;
	}

	/**
	 * @return the officerEntryCode
	 */
	public String getOfficerEntryCode() {
		return officerEntryCode;
	}

	/**
	 * @param officerEntryCode the officerEntryCode to set
	 */
	public void setOfficerEntryCode(String officerEntryCode) {
		this.officerEntryCode = officerEntryCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
