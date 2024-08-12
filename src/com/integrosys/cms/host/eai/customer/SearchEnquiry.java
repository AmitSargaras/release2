package com.integrosys.cms.host.eai.customer;


/**
 * CU002 Enquiry
 * 
 * @author allen
 * 
 */
public class SearchEnquiry implements java.io.Serializable {
	/*
	 * <Search> <CIFId>1000001</CIFId> <RefKey/> <CustomerName>CANDY
	 * MAH</CustomerName> <IDNumber>A33519999</IDNumber> <IDNumber2/> <Gender/>
	 * <BirthDate>20070324</BirthDate> <Page>1</Page> <PageSize>1</PageSize>
	 * <Start>1</Start> <End>1</End> </Search>
	 */

	// With Default BLANK value .
	private String sourceId = "";

	private String CIFId = "";

	private String DBKey = "";

	private String CustomerName = "";

	private String IDNumber = "";

	private String IDNumber2 = "";

	private String Gender = "";

	private String BirthDate = "";

	private String Page = "";

	private String PageSize = "";

	private String Start = "";

	private String End = "";

	public final String getBirthDate() {
		return BirthDate;
	}

	public final void setBirthDate(String birthDate) {
		BirthDate = birthDate;
	}

	public final String getCIFId() {
		return CIFId;
	}

	public final void setCIFId(String id) {
		if (id != null) {
			CIFId = id.trim();
		}
		else {
			CIFId = null;
		}
	}

	public final String getCustomerName() {
		return CustomerName;
	}

	public final void setCustomerName(String customerName) {
		if (customerName != null) {
			CustomerName = customerName.trim();
		}
		else {
			CustomerName = null;
		}

		// CustomerName = customerName;
	}

	public final String getDBKey() {
		return DBKey;
	}

	public final void setDBKey(String key) {
		DBKey = key;
	}

	public final String getEnd() {
		return End;
	}

	public final void setEnd(String end) {
		End = end;
	}

	public final String getGender() {
		return Gender;
	}

	public final void setGender(String gender) {
		Gender = gender;
	}

	public final String getIDNumber() {
		return IDNumber;
	}

	public final void setIDNumber(String number) {
		IDNumber = number;
	}

	public final String getIDNumber2() {
		return IDNumber2;
	}

	public final void setIDNumber2(String number2) {
		IDNumber2 = number2;
	}

	public final String getPage() {
		return Page;
	}

	public final void setPage(String page) {
		Page = page;
	}

	public final String getPageSize() {
		return PageSize;
	}

	public final void setPageSize(String pageSize) {
		PageSize = pageSize;
	}

	public final String getStart() {
		return Start;
	}

	public final void setStart(String start) {
		Start = start;
	}

	public final String getSourceId() {
		return sourceId;
	}

	public final void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
