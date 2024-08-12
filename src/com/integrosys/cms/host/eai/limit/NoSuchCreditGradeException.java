package com.integrosys.cms.host.eai.limit;

public class NoSuchCreditGradeException extends EaiLimitProfileMessageException {

	public NoSuchCreditGradeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	public NoSuchCreditGradeException(String aaNo,String creditGradeID)
	{
		super("CreditGrade info not found in the system; for AA Number [" + aaNo + "], Credit Grade ID ["
				+ creditGradeID + "].");
	}

	private static final long serialVersionUID = 1L;

}
