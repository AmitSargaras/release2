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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

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

public class DynamicGenerateLad implements Runnable {

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
		
		private ResourceBundle bundle;

		List<String> filesListInDir = new ArrayList<String>();

		public DynamicGenerateLad(Builder b) {
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
			this.bundle=b.bundle;
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
			
			private ResourceBundle bundle;

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

			public DynamicGenerateLad build() {
				return new DynamicGenerateLad(this);
			}

			public Builder ResourceBundle(ResourceBundle bundle) {
				// TODO Auto-generated method stub
				this.bundle=bundle;
				return this;
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
			
			long currentTimeMillis = System.currentTimeMillis();
			String cifId = customer.getCifId();
			ladName=cifId+"_"+currentTimeMillis;
			
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
				//List<String> gaurantor = new ArrayList<String>();
				Set<String> gaurantor = new HashSet<String>();
				
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
				int ladResultSize=ladResult.size();
				String[] facilityName = new String[ladResultSize];
				String[] releasableAmount = new String[ladResultSize];
				String[] releasedAmount = new String[ladResultSize];
				String[] outstandingAmount = new String[ladResultSize];

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
								if(!("D".equals(allocation[c1].getHostStatus()))){
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
											gaurantor.add(collateral2
													.getGuarantersName());
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
											gaurantor.add(collateral2
													.getGuarantersName());
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
											gaurantor.add(collateral2
													.getGuarantersName());
									}

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
							if(null !=facilityCategory  && !facilityCategory.endsWith(sublimitString)){
							totalOutStanding = totalOutStanding.add(new BigDecimal(
									totalUtilizedAmount));
							}

						} else {
							outstandingAmount[count] = "NIL";
						}

						count++;

					}

			

				} 

				/*
				 * replacing base path for local testing replace while giving
				 */
				// POI apparently can't create a document from scratch,
				// so we need an existing empty dummy document
				DefaultLogger.debug(this,"There is a filePath  while inputstream of file : "+ basePath);
			
				// org.apache.poi.hslf.model.Table table= new
				// org.apache.poi.hslf.model.Table(5, 5);
				// centered paragraph with large font size
			
				// Table table2=s.getTable(p);
				// character run text
	            XWPFDocument document = new XWPFDocument();
	           
	        //    System.out.println("text.generatelad.acknowledgeAmount:"+bundle.getString("text.generatelad.acknowledgeAmount"));
				if (date != null) {
					runParagraphText(document,bundle.getString("text.generatelad.date")+date, false,1,ParagraphAlignment.RIGHT,false);
					
					runParagraphText(document,bundle.getString("text.generatelad.to"), false,0,ParagraphAlignment.LEFT,false);
					
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
					previousDate2 = day + " day of " + month + " " + year;
					// z++;
				}
			
			
				runParagraphTextWithoutLineSpace(document,customer.getCustomerName(), false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,address1, false,0,ParagraphAlignment.LEFT,false);
				if(null!=address2 && !"".equalsIgnoreCase(address2))
					runParagraphTextWithoutLineSpace(document,address2, false,0,ParagraphAlignment.LEFT,false);
				if(null!=address3 && !"".equalsIgnoreCase(address3))
					runParagraphTextWithoutLineSpace(document,address3, false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,city, false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,state, false,0,ParagraphAlignment.LEFT,false);
				runParagraphTextWithoutLineSpace(document,country, false,0,ParagraphAlignment.LEFT,false);
				if(null!=pinCode && !"".equalsIgnoreCase(pinCode))
					runParagraphTextWithoutLineSpace(document,pinCode, false,2,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.dearSirs"), false,0,ParagraphAlignment.LEFT,false);
				runParagraphText(document,bundle.getString("text.generatelad.creditFacility"), true, 1,ParagraphAlignment.CENTER,false);
				
				XWPFParagraph createParagraph1 = document.createParagraph();
				createParagraph1.setAlignment(ParagraphAlignment.LEFT);
				createRunText(createParagraph1,bundle.getString("text.generatelad.amountOutStanding1"),false);
				createRunText(createParagraph1," "+previousDate,true);
				createRunText(createParagraph1,bundle.getString("text.generatelad.amountOutStanding2"),false);

				XWPFTable table = document.createTable();

				// create first row
				XWPFTableRow tableHeader = table.getRow(0);
				tableHeader.setHeight(10);
				tableHeader.getCell(0).setParagraph(
						getParagraph(bundle.getString("text.generatelad.natureOfFacility"),true));
				tableHeader.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);

				tableHeader.addNewTableCell();
				tableHeader.getCell(1).setParagraph(
						getParagraph(bundle.getString("text.generatelad.amountOfDocumentExecuted"),true));
				tableHeader.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);

				tableHeader.addNewTableCell();
				tableHeader.getCell(2)
						.setParagraph(getParagraph(bundle.getString("text.generatelad.limitsInForce"),true));
				tableHeader.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);

				tableHeader.addNewTableCell();
				tableHeader.getCell(3)
						.setParagraph(getParagraph(bundle.getString("text.generatelad.amountOutstandingInRupees"),true));
				tableHeader.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				

				for (int i = 0; i < ladResultSize; i++) {
					XWPFTableRow valueRow = table.createRow();
					
	                valueRow.getCell(0).setText(facilityName[i]);
					valueRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
						
					if(null !=facilityName[i] && facilityName[i].endsWith(sublimitString)){
						valueRow.getCell(1).setText(openBracket+UIUtil
								.formatWithCommaAndDecimal(releasableAmount[i])+closeBracket);
						valueRow.getCell(2).setText(openBracket+UIUtil
								.formatWithCommaAndDecimal(releasedAmount[i])+closeBracket);
						
						valueRow.getCell(3).setText(openBracket+UIUtil
								.formatWithCommaAndDecimal(outstandingAmount[i])+closeBracket);
						
						}else{
							valueRow.getCell(1).setText(UIUtil
									.formatWithCommaAndDecimal(releasableAmount[i]));	
							valueRow.getCell(2).setText(UIUtil
									.formatWithCommaAndDecimal(releasedAmount[i]));
							
							
							valueRow.getCell(3).setText(UIUtil
									.formatWithCommaAndDecimal(outstandingAmount[i]));
						}	
					
					valueRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
						
					valueRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
						
					
					valueRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				}
				   XWPFTableRow valueRow = table.createRow();
				
	               valueRow.getCell(0).setParagraph(getParagraph(bundle.getString("text.generatelad.total"), true));
				   valueRow.getCell(0).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				   valueRow.getCell(1).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalReleasableLimit)),true));
				   valueRow.getCell(1).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				   valueRow.getCell(2).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalLimit)),true));
				   valueRow.getCell(2).setVerticalAlignment(XWPFVertAlign.CENTER);
					
				   valueRow.getCell(3).setParagraph(getParagraph(UIUtil.formatWithCommaAndDecimal(String.valueOf(totalOutStanding)),true));
				   valueRow.getCell(3).setVerticalAlignment(XWPFVertAlign.CENTER);
				   
				   runParagraphText(document,bundle.getString("text.generatelad.sublimit"), false,0,ParagraphAlignment.LEFT,false);
				   
				   if("0".equals(String.valueOf(totalOutStanding))){
					   runParagraphText(document,"(Rupees Zero only)", true,0,ParagraphAlignment.RIGHT,false);	   
				   }else{
					   runParagraphText(document,"("+ UIUtil.numberToWordsIndia(String.valueOf(totalOutStanding)).trim()+" only)", true,0,ParagraphAlignment.RIGHT,false);
				   }

				   runParagraphText(document,bundle.getString("text.generatelad.acknowledgeConfirm"), false,0,ParagraphAlignment.LEFT,false);
				//   int gaurantorLength = gaurantor.size();
				   
//				   for(int i=0;i<gaurantorLength;i++){
//					   runParagraphText(document,gaurantor.get(i), true,0,ParagraphAlignment.LEFT,false);
//				   }
				   
				   Iterator<String> iterator1 = gaurantor.iterator();
					while(iterator1.hasNext()){
						runParagraphText(document,iterator1.next(), true,0,ParagraphAlignment.LEFT,false);
					}

				    runParagraphText(document,bundle.getString("text.generatelad.gaurantorDebt"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.yoursSincerely"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.forHdfcBank"), true,3,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.authorizedSignatory"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.theManager"), false,0,ParagraphAlignment.LEFT,true);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.hDFCBankLtd"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.sandozHouse"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.drAnnieBesantRd"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.worli"), false,1,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.dearSir"), false,0,ParagraphAlignment.LEFT,false);
					
					XWPFParagraph createParagraph = document.createParagraph();
					
					createParagraph.setAlignment(ParagraphAlignment.LEFT);
					createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount1"),false);
					createRunText(createParagraph," "+previousDate2+" ",true);
					createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount2"),false);
					createRunText(createParagraph," "+UIUtil.formatWithCommaAndDecimal(String.valueOf(totalOutStanding))+" ",true);
					if("0".equals(String.valueOf(totalOutStanding))){
						createRunText(createParagraph,"(Rupees Zero only) ", true);
					}else{
						createRunText(createParagraph,"("+UIUtil.numberToWordsIndia(String.valueOf(totalOutStanding)).trim()+" only) ", true);
					}
					createRunText(createParagraph,bundle.getString("text.generatelad.acknowledgeAmount3"),false);
					
					runParagraphText(document,bundle.getString("text.generatelad.furtherAcknowledge"), false,1,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.for"), false,3,ParagraphAlignment.LEFT,false);
					runParagraphText(document,bundle.getString("text.generatelad.authorizedSignatory"), false,1,ParagraphAlignment.LEFT,false);
					
				/*	String datePlace = MessageFormat.format(bundle.getString("text.generatelad.datePlace"),date,country);
					runParagraphText(document,datePlace, false,0,ParagraphAlignment.LEFT,false); */
					
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.date"), false,0,ParagraphAlignment.LEFT,false);
					runParagraphTextWithoutLineSpace(document,bundle.getString("text.generatelad.datePlace"), false,1,ParagraphAlignment.LEFT,false);
					System.out.println("Size of guaranter "+gaurantor.size());
					//CLMJ-32 changes 
					//if(gaurantor.size()>0){
						
					runParagraphText(document,bundle.getString("text.generatelad.foregoingConfirmation"), false,0,ParagraphAlignment.LEFT,false);
					//String datedPlace = MessageFormat.format(bundle.getString("text.generatelad.datedPlace"),date,country);
					//runParagraphText(document,bundle.getString("text.generatelad.datedPlace"), false,0,ParagraphAlignment.LEFT,false);
					//}
					
//					for(int i=0;i<gaurantorLength;i++){
//						runParagraphText(document,gaurantor.get(i), true,0,ParagraphAlignment.LEFT,false);
//					   }
					Iterator<String> iterator2 = gaurantor.iterator();
					while(iterator2.hasNext()){
						runParagraphText(document,iterator2.next(), true,0,ParagraphAlignment.LEFT,false);
					}
									
				DefaultLogger.debug(this,"There is a filePath  while output stream of file : "+ basePath);

				directory = new File(dir);
				if (!directory.exists()) {
					synchronized (this) {
						if (!directory.exists()) {
							directory.mkdir();
						}
					}
				}
				fileOutputStream = new FileOutputStream(dir + "/" + ladName+ ".doc");

				document.write(fileOutputStream);
				fileOutputStream.close();
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

			GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse.get("ladPartyDao");
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
		
		
		
		private static void createRunText(XWPFParagraph createParagraph, String runText, boolean isBold) {
			   XWPFRun createRun = createParagraph.createRun();
			   createRun.setText(runText);
			   createRun.setBold(isBold);
		}
		
		private static void runParagraphText(XWPFDocument document, String text,
				boolean isBold, int noOfBreaks, ParagraphAlignment allignment,boolean pageBreak) {
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(allignment);
		    paragraph.setPageBreak(pageBreak);
			XWPFRun run = paragraph.createRun();
			run.setText(text);
			run.setBold(isBold);
			
			for (int i = 0; i < noOfBreaks; i++) {
				run.addBreak();

			}

		}
		
		

		private static void runParagraphTextWithoutLineSpace(XWPFDocument document, String text,
				boolean isBold, int noOfBreaks, ParagraphAlignment allignment,boolean pageBreak) {
			XWPFParagraph paragraph = document.createParagraph();
			
			paragraph.setSpacingAfter(0);
			paragraph.setSpacingAfterLines(0);
			paragraph.setSpacingBefore(0);
			paragraph.setSpacingBeforeLines(0);
		    paragraph.setAlignment(allignment);
		    paragraph.setPageBreak(pageBreak);
			XWPFRun run = paragraph.createRun();
			run.setText(text);
			run.setBold(isBold);
			
			for (int i = 0; i < noOfBreaks; i++) {
				run.addBreak();

			}

		}
		
		
		private static void addBreak(XWPFDocument document,int noOfBreaks) {
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			for (int i = 0; i < noOfBreaks; i++) {
				run.addBreak();
			}
		}

		/**
		 * @param document
		 * @param string
		 * @return
		 */
		private static XWPFParagraph getParagraph(String text,boolean isBold) {
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun paragraphOneRunOne = paragraph.createRun();
			paragraphOneRunOne.setBold(isBold);
			paragraphOneRunOne.setText(text);
			return paragraph;
		}

}
