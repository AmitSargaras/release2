/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.OBCorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.OBGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.OBPersonal;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.lad.bus.GeneratePartyLADDao;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/15 12:50:06 $ Tag: $Name: $
 */
public class GenerateLad implements Runnable {

	private String limitProfileId;

	private String customerId;

	private String dirName;

	private String relationshipMgrId;

	private String partyId;

	private String dueYear;

	private String dueMonth;

	private String segment;

	private String reportGenerationDate;

	private CountDownLatch latch;

	private AtomicInteger partialCounter;

	List<String> filesListInDir = new ArrayList<String>();

	public GenerateLad(Builder b) {
		this.limitProfileId = b.limitProfileId;
		this.customerId = b.customerId;
		this.dirName = b.dirName;
		this.relationshipMgrId = b.relationshipMgrId;
		this.partyId = b.partyId;
		this.dueYear = b.dueYear;
		this.dueMonth = b.dueMonth;
		this.segment = b.segment;
		this.reportGenerationDate = b.reportGenerationDate;
		this.latch = b.latch;
		this.partialCounter = b.partialCounter;
	}

	public static class Builder {
		private String limitProfileId;

		private String customerId;

		private String dirName;

		private String relationshipMgrId;

		private String partyId;

		private String dueYear;
		private String dueMonth;

		private String segment;

		private String reportGenerationDate;

		private CountDownLatch latch;

		private AtomicInteger partialCounter;

		public Builder partialCounter(AtomicInteger partialCounter) {
			this.partialCounter = partialCounter;
			return this;
		}

		public Builder reportGenerationDate(String reportGenerationDate) {
			this.reportGenerationDate = reportGenerationDate;
			return this;
		}

		public Builder dirName(String dirName) {
			this.dirName = dirName;
			return this;
		}

		public Builder limitProfileId(String limitProfileId) {
			this.limitProfileId = limitProfileId;
			return this;
		}

		public Builder customerId(String customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder relationshipMgrId(String relationshipMgrId) {
			this.relationshipMgrId = relationshipMgrId;
			return this;
		}

		public Builder partyId(String partyId) {
			this.partyId = partyId;
			return this;
		}

		public Builder dueMonth(String dueMonth) {
			this.dueMonth = dueMonth;
			return this;
		}

		public Builder dueYear(String dueYear) {
			this.dueYear = dueYear;
			return this;
		}

		public Builder segment(String segment) {
			this.segment = segment;
			return this;
		}

		public Builder latch(CountDownLatch latch) {
			this.latch = latch;
			return this;
		}

		public GenerateLad build() {
			return new GenerateLad(this);
		}
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 * @throws Exception
	 */
	public void generateLad() throws Exception {

		DefaultLogger.debug(this, "Inside doExecute()");

		DefaultLogger.debug(this, " Before generating ladd----------");

		boolean error = false;
		List listDate = new ArrayList();
		String ladName = "";

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();

		ILimitProfile limit = limitProxy.getLimitProfile(Long
				.valueOf(limitProfileId));

		ICheckListProxyManager proxy = CheckListProxyManagerFactory
				.getCheckListProxyManager();

		ICustomerProxy customerProxy = CustomerProxyFactory.getProxy();

		ICMSCustomer customer = customerProxy.getCustomer(Long
				.valueOf(customerId));

		long limitProfileID = limit.getLimitProfileID();
		ICheckList[] checkLists = proxy.getAllCheckList(limit);
		ICheckList[] finalCheckLists = new ICheckList[checkLists.length];
		ArrayList expList = new ArrayList();
		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");

		List ladList = partyLadDao.getLimits(limitProfileID);

		if (ladList.size() != 0) {
			ILAD ilad = (ILAD) ladList.get(0);
			ladName = ilad.getLad_name();

		}

		if (checkLists != null) {
			int a = 0;
			for (int y = 0; y < checkLists.length; y++) {
				if (!checkLists[y].getCheckListType().equals("CAM")) {

					ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y]
							.getCheckListItemList();
					if (curLadList != null) {
						ArrayList expList2 = new ArrayList();
						for (int z = 0; z < curLadList.length; z++) {

							ICheckListItem item = (ICheckListItem) curLadList[z];
							if (item != null) {
								if (!(item.getItemStatus().equals("WAIVED"))) {
									if (item.getExpiryDate() != null) {
										expList2.add(item.getExpiryDate());
									}
								}
							}
						}
						if (expList2.size() > 0) {
							finalCheckLists[a] = checkLists[y];
							a++;
						}
					}
				}
			}
		}

		boolean generateLad = false;

		if (finalCheckLists != null) {
			for (int k = 0; k < finalCheckLists.length; k++) {
				if (finalCheckLists[k] != null) {
					if (finalCheckLists[k].getCheckListItemList() != null) {
						ICheckListItem[] checkListItems = finalCheckLists[k]
								.getCheckListItemList();

						for (int m = 0; m < checkListItems.length; m++) {
							if (checkListItems[m].getExpiryDate() != null) {
								if (!(checkListItems[m].getItemStatus()
										.equals("WAIVED"))) {
									if (checkListItems[m].getExpiryDate() != null) {
										listDate.add(checkListItems[m]
												.getExpiryDate());
										generateLad = true;
									}
								}
							}
						}
						Collections.sort(listDate);
					}
				}
			}
		}

		String basePath = "/cms";
		// String basePath=(String) map.get("contextPath");
		DefaultLogger
				.debug(this,
						"There is a basePath  to generating a zip archive: "
								+ basePath);

		String filePath = PropertyManager.getValue("lad.generate.path");

		// String filePath =
		// "D:\\clims_hdfc\\HDFC_CLIMS_ORACLE\\public_html\\ladDocument";
		// createJavaDoc(ladName,limit,customer,filePath);
		DefaultLogger
				.debug(this,
						"There is a filePath  to generating a zip archive: "
								+ filePath);
		createJavaDocNew(ladName, limit, customer, filePath, dirName);

		DefaultLogger.debug(this,
				"After generating LAd this is  basePath  to generating a zip archive: "
						+ basePath);
		// String filePath =
		// PropertyManager.getValue(IReportConstants.BASE_PATH);lad.generate.path

		DefaultLogger.debug(this, "Going out of doExecute()");

	}

	private void createJavaDocNew(String ladName, ILimitProfile limit,
			ICMSCustomer customer, String basePath, String dirName)
			throws LimitException, RemoteException {
		File directory = null;

		DefaultLogger.debug(this, " Before create java doc----------");

		String dir = basePath + "/" + dirName;
		String zipName = basePath + "/" + dirName + ".zip";

		DefaultLogger
				.debug(this, " Zip name to be created----------" + zipName);

		ILimit[] limits = limit.getLimits();

		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");

		List<String[]> ladResult = partyLadDao.getLimitsForLadReport(limit
				.getLimitProfileID());
		FileOutputStream fileOutputStream = null;

		try {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse
					.get("generalParamDao");
			IGeneralParamGroup generalParamGroup = generalParamDao
					.getGeneralParamGroupByGroupType("actualGeneralParamGroup",
							"GENERAL_PARAM");
			IGeneralParamEntry[] generalParamEntries = generalParamGroup
					.getFeedEntries();
			String date = "";
			for (int i = 0; i < generalParamEntries.length; i++) {
				if (generalParamEntries[i].getParamCode().equals(
						"APPLICATION_DATE")) {
					date = generalParamEntries[i].getParamValue();

				}
			}
			String[] gaurantor = new String[50];

			/*
			 * OBSubline subline[] =(OBSubline[])
			 * customer.getCMSLegalEntity().getSublineParty();
			 * if(subline!=null){ for(int ij=0;ij<subline.length;ij++){
			 * gaurantor[ij]=subline[ij].getPartyGroup().getCustomerName(); } }
			 */

			String address1 = "";
			String address2 = "";
			String address3 = "";
			String city = "";
			String state = "";
			String country = "";
			String pinCode = "";
			String previousDate = "";
			String previousDate2 = "";
				
			IContact address[] = customer.getCMSLegalEntity()
					.getRegisteredAddress();
			IContact obContactRegOff = null;
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					if (address[i].getContactType() != null
							&& address[i].getContactType().equals(
									ICMSConstant.REGISTERED)) {
						obContactRegOff = address[i];
					}

				}
				if (obContactRegOff != null) {
					if (obContactRegOff.getAddressLine1() != null) {
						address1 = obContactRegOff.getAddressLine1();
					}
					if (obContactRegOff.getAddressLine2() != null) {
						address2 = obContactRegOff.getAddressLine2();
					}
					if (obContactRegOff.getAddressLine3() != null) {
						address3 = obContactRegOff.getAddressLine3();
					}
					if (obContactRegOff.getCity() != null) {
						ICityDAO cityDAO = (ICityDAO) BeanHouse.get("cityDAO");
						ICity city2 = cityDAO.getCityById(Long
								.parseLong(obContactRegOff.getCity().trim()));
						city = city2.getCityName();
					}
					if (obContactRegOff.getState() != null) {
						IStateDAO stateDAO = (IStateDAO) BeanHouse
								.get("stateDAO");
						IState state2 = stateDAO.getStateById(Long
								.parseLong(obContactRegOff.getState().trim()));
						state = state2.getStateName();
					}
					if (obContactRegOff.getCountryCode() != null) {
						ICountryDAO countryDAO = (ICountryDAO) BeanHouse
								.get("countryDAO");
						ICountry country2 = countryDAO.getCountryById(Long
								.parseLong(obContactRegOff.getCountryCode()
										.trim()));
						country = country2.getCountryName();
					}
					if (obContactRegOff.getPostalCode() != null) {

						pinCode = obContactRegOff.getPostalCode();
					}

				}

			}

			String[] facilityName = new String[20];
			Arrays.fill(facilityName, "-");
			String[] releasableAmount = new String[20];
			Arrays.fill(releasableAmount, "-");
			String[] releasedAmount = new String[20];
			Arrays.fill(releasedAmount, "-");
			String[] outstandingAmount = new String[20];
			Arrays.fill(outstandingAmount, "-");

			List ls = new ArrayList();
			BigDecimal totalLimit = new BigDecimal("0");
			BigDecimal totalOutStanding = new BigDecimal("0");
			BigDecimal totalReleasableLimit = new BigDecimal("0");

			String sublimitString="*";
			String openBracket="(";
			String closeBracket=")";
		
			if (limits != null) {

				for (int abc = 0; abc < limits.length; abc++) {

					ICollateralAllocation[] allocation = limits[abc]
							.getCollateralAllocations();
					if (allocation != null) {
						int c2 = 0;
						for (int c1 = 0; c1 < allocation.length; c1++) {
							//int c2 = 0;
							if (allocation[c1].getCollateral() instanceof OBCorporateThirdParty) {
								OBCorporateThirdParty collateral = (OBCorporateThirdParty) allocation[c1]
										.getCollateral();

								OBCorporateThirdParty collateral2 = (OBCorporateThirdParty) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor[c2] = collateral2
												.getGuarantersName();
								}

							} else if (allocation[c1].getCollateral() instanceof OBGovernment) {
								OBGovernment collateral = (OBGovernment) allocation[c1]
										.getCollateral();
								OBGovernment collateral2 = (OBGovernment) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor[c2] = collateral2
												.getGuarantersName();
								}

							} else if (allocation[c1].getCollateral() instanceof OBPersonal) {
								OBPersonal collateral = (OBPersonal) allocation[c1]
										.getCollateral();
								OBPersonal collateral2 = (OBPersonal) CollateralProxyFactory
										.getProxy().getCollateral(
												collateral.getCollateralID(),
												true);
								if (collateral2.getGuarantersName() != null) {
									c2++;
									if (c2 <= 10)
										gaurantor[c2] = collateral2
												.getGuarantersName();
								}

							}
						}
					}

				}

				int count = 0;
				for (String[] arr : ladResult) {

					String facilityCategory = arr[0];
					String totalReleasedAmount = arr[1];
					String totalReleasableAmount = arr[2];
					String totalUtilizedAmount = arr[3];

					if (facilityCategory != null) {
						facilityName[count] = facilityCategory;
					} else {
						facilityName[count] = "-";
					}

					if (totalReleasedAmount != null) {
						releasedAmount[count] = totalReleasedAmount;
						if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
						totalLimit = totalLimit.add(new BigDecimal(
								totalReleasedAmount));
						}
					
					} else {
						releasedAmount[count] = "-";
					}

					if (totalReleasableAmount != null) {
						releasableAmount[count] = totalReleasableAmount;
						if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
						totalReleasableLimit = totalReleasableLimit
								.add(new BigDecimal(totalReleasableAmount));
						}
					} else {
						releasableAmount[count] = "NIL";
					}

					if (totalUtilizedAmount != null) {

						outstandingAmount[count] = totalUtilizedAmount;

						totalOutStanding = totalOutStanding.add(new BigDecimal(
								totalUtilizedAmount));

					} else {
						outstandingAmount[count] = "NIL";
					}

					count++;

				}

				for (int pqr = limits.length; pqr < 20; pqr++) {
					facilityName[pqr] = "-";
					releasedAmount[pqr] = "-";
					releasableAmount[pqr] = "-";
					outstandingAmount[pqr] = "-";
				}

			} else {

				for (int pqrs = 0; pqrs < 20; pqrs++) {
					facilityName[pqrs] = "-";
					releasedAmount[pqrs] = "-";
					releasableAmount[pqrs] = "-";
					outstandingAmount[pqrs] = "-";
				}

			}

			/*
			 * replacing base path for local testing replace while giving
			 */
			// POI apparently can't create a document from scratch,
			// so we need an existing empty dummy document
			DefaultLogger.debug(this,
					"There is a filePath  while inputstream of file : "
							+ basePath);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					basePath + "/LADFormat.doc"));

			HWPFDocument doc = new HWPFDocument(fs);

			// org.apache.poi.hslf.model.Table table= new
			// org.apache.poi.hslf.model.Table(5, 5);
			// centered paragraph with large font size
			Range range = doc.getRange();
			Range r1 = doc.getRange();
			DefaultLogger.debug(this, "Trying to generate");
			for (int i = 0; i < r1.numSections(); ++i) {
				org.apache.poi.hwpf.usermodel.Section s = r1.getSection(i);
				for (int x = 0; x < s.numParagraphs(); x++) {
					Paragraph p = s.getParagraph(x);

					// Table table2=s.getTable(p);
					for (int z = 0; z < p.numCharacterRuns(); z++) {
						// character run
						CharacterRun run = p.getCharacterRun(z);
						// character run text
						String text = run.text();

						DefaultLogger.debug(this,
								" File replacement started----------");
						if (text.equalsIgnoreCase("DDDDDDDDDDD")) {
							// String theDate = "28-05-2009";
							if (date != null) {
								run.replaceText("DDDDDDDDDDD", date);
								GregorianCalendar gc = new GregorianCalendar();
								DateFormat df = new SimpleDateFormat(
										"dd-MMM-yyyy");
								Date startDate = df.parse(date);
								gc.setTime(startDate);
								int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
								gc.roll(Calendar.DAY_OF_YEAR, -1);
								int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
								if (dayAfter > dayBefore) {
									gc.roll(Calendar.YEAR, -1);
								}
								gc.get(Calendar.DATE);
								java.util.Date yesterday = gc.getTime();

								Calendar calendar = Calendar
										.getInstance(TimeZone.getDefault());
								calendar.setTime(yesterday);
								String year = Integer.valueOf(
										calendar.get(Calendar.YEAR)).toString();

								String day = UIUtil.convertToOrdinal(calendar
										.get(Calendar.DAY_OF_MONTH));
								String month = new SimpleDateFormat("MMMM")
										.format(calendar.getTime());

								previousDate = day + " " + month + " " + year;
								previousDate2 = day + " day of  " + month + " " + year;
								// z++;
							}
						}
						String replaceName = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

						if (text.equalsIgnoreCase(replaceName)) {
							String theapplicantName = customer
									.getCustomerName();
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(theapplicantName);
							if (theapplicantName.length() != replaceName
									.length()) {
								for (int abc = 0; abc < (replaceName.length() - theapplicantName
										.length()); abc++) {
									stringBuildermid.append(" ");
								}
							}

							if (theapplicantName != null) {
								run.replaceText(replaceName,
										stringBuildermid.toString());
								// z++;
							}

						} else if (text.equalsIgnoreCase("BC41")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(previousDate);

							run.replaceText("BC41", stringBuildermid.toString());
						} else if (text.equalsIgnoreCase("BC5$2")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(previousDate2);

							run.replaceText("BC5$2", stringBuildermid.toString());
						}

						else if (text
								.equalsIgnoreCase("ADD1                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(address1);
							if (address1.length() != 75) {
								for (int abc = 0; abc < (75 - address1.length()); abc++) {
									stringBuildermid.append(" ");
								}
							}
							run.replaceText(
									"ADD1                                                                       ",
									stringBuildermid.toString());
						} else if (text
								.equalsIgnoreCase("ADD2                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(address2);
							if (address2.length() != 75) {
								for (int abc = 0; abc < (75 - address2.length()); abc++) {
									stringBuildermid.append(" ");
								}
							}
							run.replaceText(
									"ADD2                                                                       ",
									stringBuildermid.toString());
						} else if (text
								.equalsIgnoreCase("ADD3                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(address3);
							if (address3.length() != 75) {
								for (int abc = 0; abc < (75 - address3.length()); abc++) {
									stringBuildermid.append(" ");
								}
							}
							run.replaceText(
									"ADD3                                                                       ",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("CITY11111111111111111111111111")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(city);
							/*
							 * if (city.length() != 30) { for (int abc = 0; abc
							 * < (30 - city.length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("CITY11111111111111111111111111",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("STATE1111111111111111111111111")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(state);
							/*
							 * if (state.length() != 30) { for (int abc = 0; abc
							 * < (30 - state.length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("STATE1111111111111111111111111",
									stringBuildermid.toString());
						} else if (text
								.equalsIgnoreCase("COUNTRY                                           ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(country);
							/*
							 * if (country.length() != 50) { for (int abc = 0;
							 * abc < (50 - country.length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"COUNTRY                                           ",
									stringBuildermid.toString());
						}else if (text.trim().equalsIgnoreCase("COUNTRY")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(country);
							/*
							 * if (country.length() != 50) { for (int abc = 0;
							 * abc < (50 - country.length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									text,
									stringBuildermid.toString());
						}
						
						
						
						
						
						
						
						
						
						else if (text.equalsIgnoreCase("PINCOD")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(pinCode);
							/*
							 * if (pinCode.length() != 75) { for (int abc = 0;
							 * abc < (6 - pinCode.length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("PINCOD",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("FAC1                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[0]);
							/*
							 * if (facilityName[0].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[0] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"FAC1                                                                       ",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("DOC2                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[1]);
							/*
							 * if (facilityName[1].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[1] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC2                                                                       ",
									stringBuildermid.toString());

						} else

						if (text.equalsIgnoreCase("DOC3                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[2]);
							/*
							 * if (facilityName[2].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[2] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC3                                                                       ",
									stringBuildermid.toString());

						} else

						if (text.equalsIgnoreCase("DOC4                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[3]);
							/*
							 * if (facilityName[3].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[3] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC4                                                                       ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC5                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[4]);
							/*
							 * if (facilityName[4].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[4] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC5                                                                       ",
									stringBuildermid.toString());

						} else

						if (text.equalsIgnoreCase("DOC6                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[5]);
							/*
							 * if (facilityName[5].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[5] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC6                                                                       ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC7                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[6]);
							/*
							 * if (facilityName[6].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[6] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC7                                                                       ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC8                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[7]);
							/*
							 * if (facilityName[7].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[7] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC8                                                                       ",
									stringBuildermid.toString());
							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC9                                                                       ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[8]);
							/*
							 * if (facilityName[8].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[8] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC9                                                                       ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC10                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[9]);
							/*
							 * if (facilityName[9].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[9] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC10                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC11                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[10]);
							/*
							 * if (facilityName[10].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[10] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC11                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC12                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[11]);
							/*
							 * if (facilityName[11].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[11] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC12                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC13                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[12]);
							/*
							 * if (facilityName[12].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[12] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC13                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC14                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[13]);
							/*
							 * if (facilityName[13].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[13] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC14                                                                      ",
									stringBuildermid.toString());
							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC15                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[14]);
							/*
							 * if (facilityName[14].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[14] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC15                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC16                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[15]);
							/*
							 * if (facilityName[15].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[15] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC16                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC17                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[16]);
							/*
							 * if (facilityName[16].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[16] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC17                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC18                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[17]);
							/*
							 * if (facilityName[17].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[17] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC18                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC19                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[18]);
							/*
							 * if (facilityName[18].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[18] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC19                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("DOC20                                                                      ")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid.append(facilityName[19]);
							/*
							 * if (facilityName[19].length() != 75) { for (int
							 * abc = 0; abc < (75 - facilityName[19] .length());
							 * abc++) { stringBuildermid.append(" "); } }
							 */
							run.replaceText(
									"DOC20                                                                      ",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC21")) {

							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[0] && facilityName[0].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[0])+closeBracket);
							}else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[0]));	
							}
							/*
							 * if (releasedAmount[0].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[0]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC21",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC22")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[1] && facilityName[1].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[1])+closeBracket);
							}else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[1]));	
							}
							/*
							 * if (releasedAmount[1].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[1]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC22",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC23")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[2] && facilityName[2].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[2])+closeBracket);
						    }else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[2]));	
							}
							/*
							 * if (releasedAmount[2].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[2]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC23",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC24")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[3] && facilityName[3].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[3])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[3]));	
								}
							/*
							 * if (releasedAmount[3].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[3]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC24",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC25")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[4] && facilityName[4].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[4])+closeBracket);
						    }else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[4]));	
							}
							/*
							 * if (releasedAmount[4].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[4]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC25",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC26")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[5] && facilityName[5].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[5])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[5]));	
								}
							/*
							 * if (releasedAmount[5].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[5]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC26",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC27")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[6] && facilityName[6].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[6])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[6]));	
								}
							/*
							 * if (releasedAmount[6].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[6]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC27",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC28")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[7] && facilityName[7].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[7])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[7]));	
								}
							/*
							 * if (releasedAmount[7].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[7]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC28",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC29")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[8] && facilityName[8].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[8])+closeBracket);
						    }else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[8]));	
							}
							/*
							 * if (releasedAmount[8].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[8]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC29",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC30")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[9] && facilityName[9].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[9])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[9]));	
								}
							/*
							 * if (releasedAmount[9].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[9]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC30",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC31")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[10] && facilityName[10].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[10])+closeBracket);
						      }else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[10]));	
							}
							/*
							 * if (releasedAmount[10].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[10]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC31",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC32")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[11] && facilityName[11].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[11])+closeBracket);
							 }else{
									stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[11]));	
								}
							/*
							 * if (releasedAmount[11].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[11]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC32",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC33")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[12] && facilityName[12].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[12])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[12]));	
							}
							/*
							 * if (releasedAmount[12].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[12]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC33",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC34")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[13] && facilityName[13].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[13])+closeBracket);
						    }else{
							stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[13]));	
						      }
							/*
							 * if (releasedAmount[13].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[13]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC34",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC35")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[14] && facilityName[14].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[14])+closeBracket);
							}else{
								stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[14]));	
							      }
							/*
							 * if (releasedAmount[14].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[14]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC35",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC36")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[15] && facilityName[15].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[15])+closeBracket);
						    }else{
							 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[15]));	
						      }
							/*
							 * if (releasedAmount[15].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[15]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC36",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC37")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[16] && facilityName[16].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[16])+closeBracket);
							 }else{
								 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[16]));	
							      }
							/*
							 * if (releasedAmount[16].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[16]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC37",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC38")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[17] && facilityName[17].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[17])+closeBracket);
						     }else{
							 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[17]));	
						      }
							/*
							 * if (releasedAmount[17].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[17]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC38",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC39")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[18] && facilityName[18].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[18])+closeBracket);
							}else{
								 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[18]));	
							      }
							/*
							 * if (releasedAmount[18].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[18]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC39",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC40")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[19] && facilityName[19].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasedAmount[19])+closeBracket);
						     }else{
							 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasedAmount[19]));	
						      }
							/*
							 * if (releasedAmount[19].length() != 20) { for (int
							 * abc = 0; abc < (20 - releasedAmount[19]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC40",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[0] && facilityName[0].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasableAmount[0])+closeBracket);
							}else{
								 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[0]));	
							    }
							/*
							 * if (releasableAmount[0].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[0]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC", stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC1")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[1] && facilityName[1].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[1])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[1]));	
								    }
							/*
							 * if (releasableAmount[1].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[1]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC1",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC2")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[2] && facilityName[2].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[2])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[2]));	
								    }
							/*
							 * if (releasableAmount[2].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[2]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC2",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC3")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[3] && facilityName[3].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[3])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[3]));	
								    }
							/*
							 * if (releasableAmount[3].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[3]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC3",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC4")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[4] && facilityName[4].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[4])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[4]));	
								    }
							/*
							 * if (releasableAmount[4].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[4]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC4",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC5")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[5] && facilityName[5].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[5])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[5]));	
								    }
							/*
							 * if (releasableAmount[5].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[5]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC5",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC6")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[6] && facilityName[6].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[6])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[6]));	
								    }
							/*
							 * if (releasableAmount[6].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[6]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC6",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC7")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[7] && facilityName[7].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[7])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[7]));	
								    }
							/*
							 * if (releasableAmount[7].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[7]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC7",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC8")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[8] && facilityName[8].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[8])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[8]));	
								    }
							/*
							 * if (releasableAmount[8].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[8]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC8",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC9")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[9] && facilityName[9].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[9])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[9]));	
								    }
							/*
							 * if (releasableAmount[9].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[9]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC9",
									stringBuildermid.toString());
							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC10")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[10] && facilityName[10].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[10])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[10]));	
								    }
							/*
							 * if (releasableAmount[10].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[10]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC10",
									stringBuildermid.toString());
							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC11")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[11] && facilityName[11].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[11])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[11]));	
								    }
							/*
							 * if (releasableAmount[11].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[11]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC11",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC12")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[12] && facilityName[12].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[12])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[12]));	
								    }
							/*
							 * if (releasableAmount[12].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[12]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC12",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC13")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[13] && facilityName[13].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[13])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[13]));	
								    }
							/*
							 * if (releasableAmount[13].length() != 23) { for
							 * (int abc = 0; abc < (23 - releasableAmount[13]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC13",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC14")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[14] && facilityName[14].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[14])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[14]));	
								    }
							/*
							 * if (releasableAmount[14].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[14]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC14",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC15")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[15] && facilityName[15].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[15])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[15]));	
								    }
							/*
							 * if (releasableAmount[15].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[15]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC15",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC16")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[16] && facilityName[16].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[16])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[16]));	
								    }
							/*
							 * if (releasableAmount[16].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[16]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC16",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC17")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[17] && facilityName[17].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[17])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[17]));	
								    }
							/*
							 * if (releasableAmount[17].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[17]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC17",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC18")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[18] && facilityName[18].endsWith(sublimitString)){
								stringBuildermid
										.append(openBracket+UIUtil
												.formatWithCommaAndDecimal(releasableAmount[18])+closeBracket);
								}else{
									 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[18]));	
								    }
							/*
							 * if (releasableAmount[18].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[18]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC18",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC19")) {
							StringBuilder stringBuildermid = new StringBuilder();
							if(null !=facilityName[19] && facilityName[19].endsWith(sublimitString)){
							stringBuildermid
									.append(openBracket+UIUtil
											.formatWithCommaAndDecimal(releasableAmount[19])+closeBracket);
							}else{
								 stringBuildermid.append(UIUtil.formatWithCommaAndDecimal(releasableAmount[19]));	
							    }
							/*
							 * if (releasableAmount[19].length() != 20) { for
							 * (int abc = 0; abc < (20 - releasableAmount[19]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC19",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC20")) {

							String totalLimitString = String
									.valueOf(totalOutStanding);

							run.replaceText("$ABC20",
									UIUtil.numberToWordsIndia(totalLimitString));

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC42")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[0]));

							run.replaceText("$ABC42",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC43")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[1]));
							/*
							 * if (outstandingAmount[1].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[1]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC43",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC44")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[2]));
							/*
							 * if (outstandingAmount[2].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[2]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC44",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC45")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[3]));
							/*
							 * if (outstandingAmount[3].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[3]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC45",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC46")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[4]));
							/*
							 * if (outstandingAmount[4].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[4]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC46",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$ABC47")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[5]));
							/*
							 * if (outstandingAmount[5].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[5]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC47",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC48")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[6]));
							/*
							 * if (outstandingAmount[6].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[6]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC48",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC49")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[7]));
							/*
							 * if (outstandingAmount[7].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[7]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC49",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$ABC50")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[8]));
							/*
							 * if (outstandingAmount[8].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[8]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$ABC50",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF1")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[9]));
							/*
							 * if (outstandingAmount[9].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[9]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF1",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF2")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[10]));
							/*
							 * if (outstandingAmount[10].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[10]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF2",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$DEF3")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[11]));
							/*
							 * if (outstandingAmount[11].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[11]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF3",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF4")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[12]));
							/*
							 * if (outstandingAmount[12].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[12]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF4",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF5")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[13]));
							/*
							 * if (outstandingAmount[13].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[13]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF5",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF6")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[14]));
							/*
							 * if (outstandingAmount[14].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[14]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF6",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF7")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[15]));
							/* k */
							run.replaceText("$DEF7",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF8")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[16]));
							/*
							 * if (outstandingAmount[16].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[16]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF8",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF9")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[17]));
							/*
							 * if (outstandingAmount[17].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[17]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF9",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$DEF10")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[18]));
							/*
							 * if (outstandingAmount[18].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[18]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF10",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						} else

						if (text.equalsIgnoreCase("$DEF11")) {
							StringBuilder stringBuildermid = new StringBuilder();
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(outstandingAmount[19]));
							/*
							 * if (outstandingAmount[19].length() != 20) { for
							 * (int abc = 0; abc < (20 - outstandingAmount[19]
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF11",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						else

						if (text.equalsIgnoreCase("$DEF12")) {
							StringBuilder stringBuildermid = new StringBuilder();
							String totalOutStandingString = String
									.valueOf(totalOutStanding);
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(totalOutStandingString));
							/*
							 * if (totalOutStandingString.length() != 20) { for
							 * (int abc = 0; abc < (20 - totalOutStandingString
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$DEF12",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						if (text.equalsIgnoreCase("$BCA2")) {
							StringBuilder stringBuildermid = new StringBuilder();
							String totalOutStandingString = String
									.valueOf(totalLimit);
							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(totalOutStandingString));
							/*
							 * if (totalOutStandingString.length() != 20) { for
							 * (int abc = 0; abc < (20 - totalOutStandingString
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$BCA2",
									stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						if (text.equalsIgnoreCase("$TOT01")) {
							StringBuilder stringBuildermid = new StringBuilder();
							String totalOutStandingString = String
									.valueOf(totalReleasableLimit);

							stringBuildermid
									.append(UIUtil
											.formatWithCommaAndDecimal(totalOutStandingString));
							/*
							 * if (totalOutStandingString.length() != 20) { for
							 * (int abc = 0; abc < (20 - totalOutStandingString
							 * .length()); abc++) {
							 * stringBuildermid.append(" "); } }
							 */
							run.replaceText("$TOT01", stringBuildermid.toString());

							DefaultLogger.debug(this,
									"1111111111111111111111111111");
						}

						for (int s1 = 0; s1 < 50; s1++) {
							if (gaurantor[s1] == null) {
								gaurantor[s1] = "";
							}
						}

						if (text.equalsIgnoreCase("$BCA3\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[0].trim().equals("")){
							 * stringBuildermid.append("1."); }
							 */
							stringBuildermid.append(gaurantor[0]);

							run.replaceText("$BCA3\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA4\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[1].trim().equals("")){
							 * stringBuildermid.append("2."); }
							 */
							stringBuildermid.append(gaurantor[1]);

							run.replaceText("$BCA4\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA5\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[2].trim().equals("")){
							 * stringBuildermid.append("3."); }
							 */
							stringBuildermid.append(gaurantor[2]);

							run.replaceText("$BCA5\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA6\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[3].trim().equals("")){
							 * stringBuildermid.append("4."); }
							 */
							stringBuildermid.append(gaurantor[3]);

							run.replaceText("$BCA6\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA7\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[4].trim().equals("")){
							 * stringBuildermid.append("5."); }
							 */
							stringBuildermid.append(gaurantor[4]);

							run.replaceText("$BCA7\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA8\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[5].trim().equals("")){
							 * stringBuildermid.append("6."); }
							 */
							stringBuildermid.append(gaurantor[5]);

							run.replaceText("$BCA8\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA9\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[6].trim().equals("")){
							 * stringBuildermid.append("7."); }
							 */
							stringBuildermid.append(gaurantor[6]);

							run.replaceText("$BCA9\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA10\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[7].trim().equals("")){
							 * stringBuildermid.append("8."); }
							 */
							stringBuildermid.append(gaurantor[7]);

							run.replaceText("$BCA10\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA11\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[8].trim().equals("")){
							 * stringBuildermid.append("9."); }
							 */
							stringBuildermid.append(gaurantor[8]);

							run.replaceText("$BCA11\r",
									stringBuildermid.toString());
						} else

						if (text.equalsIgnoreCase("$BCA12\r")) {
							StringBuilder stringBuildermid = new StringBuilder();
							/*
							 * if(!gaurantor[9].trim().equals("")){
							 * stringBuildermid.append("10."); }
							 */
							stringBuildermid.append(gaurantor[9]);

							run.replaceText("$BCA12\r",
									stringBuildermid.toString());
						}

					}
				}
			}

			// add a custom document property (needs POI 3.5; POI 3.2 doesn't
			// save custom properties)
			DocumentSummaryInformation dsi = doc
					.getDocumentSummaryInformation();
			CustomProperties cp = dsi.getCustomProperties();
			if (cp == null)
				cp = new CustomProperties();
			cp.put("myProperty", "foo bar baz");
			dsi.setCustomProperties(cp);
			DefaultLogger.debug(this,
					"There is a filePath  while output stream of file : "
							+ basePath);

			directory = new File(dir);
			if (!directory.exists()) {
				synchronized (this) {
					if (!directory.exists()) {
						directory.mkdir();
					}
				}
			}
			fileOutputStream = new FileOutputStream(dir + "/" + ladName
					+ ".doc");

			doc.write(fileOutputStream);

			/*
			 * byte[] b = doc.getDataStream();
			 * 
			 * // //////////////////////create the zip file and copy the
			 * modified // files into it ZipOutputStream out = new
			 * ZipOutputStream(new FileOutputStream( "my.zip"));
			 * out.putNextEntry(new ZipEntry(basePath + "/" + ladName)); for
			 * (int j = 0; j < b.length; j++) { out.write(b[j]); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Cannot generate Lad Document", e);
		} finally {
			try {
				fileOutputStream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {

		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");
		try {
			generateLad();

		} catch (Exception e) {
			partialCounter.incrementAndGet();
			e.printStackTrace();
		}

		latch.countDown();

	}

	private void zipDirectory(File dir, String zipDirName) {
		try {
			populateFilesList(dir);
			// now zip files one by one
			// create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				// for ZipEntry we need to keep only relative file path, so we
				// used substring on absolute path
				ZipEntry ze = new ZipEntry(filePath.substring(dir
						.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				// read the file and write to ZipOutputStream
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method populates all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(file);
		}
	}

}
