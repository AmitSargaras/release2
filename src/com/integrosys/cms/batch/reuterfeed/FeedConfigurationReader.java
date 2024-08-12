/*
 * Created on Jun 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedConfigurationReader {
	private static FeedConfigurationReader instance;

	private List configurationList;

	private FeedConfigurationReader() throws Exception {
		configurationList = new ArrayList();
		readConfiguration();
	}

	public static FeedConfigurationReader getInstance() throws Exception {
		if (instance == null) {
			synchronized (FeedConfigurationReader.class) {
				if (instance == null) {
					instance = new FeedConfigurationReader();
				}
			}
		}
		return instance;
	}

	public Iterator getConfigurationIterator() {
		return configurationList.iterator();
	}

	private void readConfiguration() throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		     factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			 factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			 factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(PropertyManager.getValue("feed.configuration.xml.path"));
			Element root = doc.getDocumentElement();
			processFeedConfigList(root);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private void processFeedConfigList(Element root) throws Exception {
		NodeList feedConfigList = root.getElementsByTagName("feedconfig");
		for (int i = 0; i < feedConfigList.getLength(); i++) {
			Element nextFeedConfig = (Element) (feedConfigList.item(i));
			FeedConfiguration feedConfiguration = new FeedConfiguration();
			String feedFileName = nextFeedConfig.getAttribute("feedfilename");
			String includeHeaderCol = nextFeedConfig.getAttribute("includeheadercol");
			String format = nextFeedConfig.getAttribute("format").toLowerCase();
			String batchSize = nextFeedConfig.getAttribute("batchsize");
			feedConfiguration.setFeedFileName(feedFileName);
			if (includeHeaderCol != null) {
				if ("true".equalsIgnoreCase(includeHeaderCol)) {
					feedConfiguration.setIncludeHeaderCol(true);
				}
				else {
					feedConfiguration.setIncludeHeaderCol(false);
				}
			}
			feedConfiguration.setFeedFormat(format);
			if (batchSize != null) {
				feedConfiguration.setBatchSize(Integer.parseInt(batchSize));
			}
			processFieldMapping(nextFeedConfig, feedConfiguration);
			processValidator(nextFeedConfig, feedConfiguration);
			processPostProcessor(nextFeedConfig, feedConfiguration);
			processPersistence(nextFeedConfig, feedConfiguration);
			configurationList.add(feedConfiguration);
		}
	}

	private void processFieldMapping(Element feedConfigElem, FeedConfiguration feedConfiguration) throws Exception {
		Element fieldsMapping = (Element) feedConfigElem.getElementsByTagName("fieldsmapping").item(0);
		String tableName = fieldsMapping.getAttribute("table");
		feedConfiguration.setTableName(tableName);
		Map map = new HashMap();
		if (fieldsMapping != null) {
			NodeList fields = fieldsMapping.getElementsByTagName("field");
			for (int i = 0; i < fields.getLength(); i++) {
				Element nextField = (Element) (fields.item(i));
				String fieldName = nextField.getAttribute("name");
				String fieldType = nextField.getAttribute("type");
				String columnName = nextField.getAttribute("column");
				String required = nextField.getAttribute("required");
				FeedFieldDef nextDef = new FeedFieldDef();
				nextDef.setOrder(i);
				nextDef.setFieldName(fieldName);
				nextDef.setFieldType(fieldType);
				if ((columnName != null) && !columnName.trim().equals("")) {
					nextDef.setColumnName(columnName.toLowerCase());
				}
				if (required != null) {
					if ("true".equalsIgnoreCase(required)) {
						nextDef.setRequired(true);
					}
					else {
						nextDef.setRequired(false);
					}
				}
				map.put(fieldName, nextDef);
			}
		}
		feedConfiguration.setFieldsMapping(map);
	}

	private void processValidator(Element feedConfigElem, FeedConfiguration feedConfiguration) throws Exception {
		NodeList validators = feedConfigElem.getElementsByTagName("validator");
		List validatorList = new ArrayList();
		for (int i = 0; i < validators.getLength(); i++) {
			Element nextValidatorElem = (Element) validators.item(i);
			String validatorClassName = nextValidatorElem.getAttribute("class");
			IFeedValidator nextValidator = (IFeedValidator) Class.forName(validatorClassName).newInstance();
			validatorList.add(nextValidator);
		}
		feedConfiguration.setValidatorList(validatorList);
	}

	private void processPostProcessor(Element feedConfigElem, FeedConfiguration feedConfiguration) throws Exception {
		NodeList postProcessors = feedConfigElem.getElementsByTagName("postprocessor");
		List postProcessorList = new ArrayList();
		for (int i = 0; i < postProcessors.getLength(); i++) {
			Element nextPostProcessorElem = (Element) postProcessors.item(i);
			String postProcessorClassName = nextPostProcessorElem.getAttribute("class");
			IFeedPostProcessor nextProcessor = (IFeedPostProcessor) Class.forName(postProcessorClassName).newInstance();
			postProcessorList.add(nextProcessor);
		}
		feedConfiguration.setFeedPostProcessorList(postProcessorList);
	}

	private void processPersistence(Element feedConfigElem, FeedConfiguration feedConfiguration) throws Exception {
		NodeList persisters = feedConfigElem.getElementsByTagName("persister");
		List persisterList = new ArrayList();
		for (int i = 0; i < persisters.getLength(); i++) {
			Element nextPersisterElem = (Element) persisters.item(i);
			String persisterType = nextPersisterElem.getAttribute("type");
			IFeedPersister nextPersister = null;
			if ("customized".equals(persisterType)) {
				nextPersister = processCustomerizedPersister(nextPersisterElem, feedConfiguration);
			}
			else if ("update".equals(persisterType)) {
				nextPersister = processUpdatePersister(nextPersisterElem, feedConfiguration);
			}
			else if ("insert".equals(persisterType)) {
				nextPersister = processInsertPersister(nextPersisterElem, feedConfiguration);
			}
			persisterList.add(nextPersister);
		}
		feedConfiguration.setFeedPersisterList(persisterList);
	}

	private IFeedPersister processCustomerizedPersister(Element persisterElem, FeedConfiguration feedConfiguration)
			throws Exception {
		Map customizedProperties = new HashMap();
		String persisterClassName = persisterElem.getAttribute("class");
		ICustomizedFeedPersister nextPersister = (ICustomizedFeedPersister) (Class.forName(persisterClassName)
				.newInstance());
		NodeList propertyList = persisterElem.getElementsByTagName("property");
		for (int i = 0; i < propertyList.getLength(); i++) {
			Element nextPropertyElem = (Element) propertyList.item(i);
			String propertyName = nextPropertyElem.getAttribute("name");
			String propertyValue = nextPropertyElem.getAttribute("value");
			customizedProperties.put(propertyName, propertyValue);
		}
		nextPersister.setProperties(customizedProperties);
		return nextPersister;
	}

	private IFeedPersister processUpdatePersister(Element persisterElem, FeedConfiguration feedConfiguration)
			throws Exception {
		String allFieldsInd = persisterElem.getAttribute("allfields");
		DefaultUpdateFeedPersister updatePersister = new DefaultUpdateFeedPersister();
		if ("true".equalsIgnoreCase(allFieldsInd)) {
			updatePersister.setAllFields(true);
		}
		else {
			updatePersister.setAllFields(false);
			List selectedFields = new ArrayList();
			NodeList fieldList = persisterElem.getElementsByTagName("field");
			for (int i = 0; i < fieldList.getLength(); i++) {
				Element nextField = (Element) fieldList.item(i);
				String fieldName = nextField.getAttribute("name");
				selectedFields.add(fieldName);
			}
			updatePersister.setSelectedFields(selectedFields);
		}

		List condFields = new ArrayList();
		NodeList conditionFieldList = persisterElem.getElementsByTagName("condfield");
		for (int j = 0; j < conditionFieldList.getLength(); j++) {
			Element nextCondField = (Element) conditionFieldList.item(j);
			String fieldName = nextCondField.getAttribute("name");
			condFields.add(fieldName);
		}
		updatePersister.setConditionFields(condFields);
		return updatePersister;
	}

	private IFeedPersister processInsertPersister(Element persisterElem, FeedConfiguration feedConfiguration)
			throws Exception {
		String allFieldsInd = persisterElem.getAttribute("allfields");
		String autoGenKey = persisterElem.getAttribute("autogenkey");
		String keyColumn = persisterElem.getAttribute("keycolumn");
		String sequence = persisterElem.getAttribute("sequence");
		DefaultInsertFeedPersister insertPersister = new DefaultInsertFeedPersister();
		if ("true".equals(autoGenKey)) {
			insertPersister.setAutoGenKey(true);
			insertPersister.setKeyColumn(keyColumn);
			insertPersister.setKeySequence(sequence);
		}
		if ("true".equalsIgnoreCase(allFieldsInd)) {
			insertPersister.setAllFields(true);
		}
		else {
			insertPersister.setAllFields(false);
			List selectedFields = new ArrayList();
			NodeList fieldList = persisterElem.getElementsByTagName("field");
			for (int i = 0; i < fieldList.getLength(); i++) {
				Element nextField = (Element) fieldList.item(i);
				String fieldName = nextField.getAttribute("name");
				selectedFields.add(fieldName);
			}
			insertPersister.setSelectedFields(selectedFields);
		}
		return insertPersister;
	}

	public void printConfiguration() {
		for (int i = 0; i < configurationList.size(); i++) {
			FeedConfiguration config = (FeedConfiguration) (configurationList.get(i));
			DefaultLogger.debug(this,"Next feed configuration ********************************");
			DefaultLogger.debug(this,"Batch size: *** " + config.getBatchSize());
			DefaultLogger.debug(this,"Feed file name: *** " + config.getFeedFileName());
			DefaultLogger.debug(this,"Feed format " + config.getFeedFormat());
			DefaultLogger.debug(this,"Table name " + config.getTableName());
			Map fieldMapping = config.getFieldsMapping();
			Object[] arr = fieldMapping.values().toArray();
			for (int j = 0; j < arr.length; j++) {
				FeedFieldDef nextDef = (FeedFieldDef) (arr[j]);
				DefaultLogger.debug(this,"Field name " + nextDef.getFieldName());
				DefaultLogger.debug(this,"Field order " + nextDef.getOrder());
				DefaultLogger.debug(this,"Column name " + nextDef.getColumnName());
				DefaultLogger.debug(this,"Required " + nextDef.isRequired());
			}
			List validatorList = config.getValidatorList();
			for (int k = 0; k < validatorList.size(); k++) {
				DefaultLogger.debug(this,"Next validator: " + validatorList.get(k).getClass().getName());
			}
			List postProcessorList = config.getFeedPostProcessorList();
			for (int l = 0; l < postProcessorList.size(); l++) {
				DefaultLogger.debug(this,"Next postprocessor: " + postProcessorList.get(l).getClass().getName());
			}

			List feedPersisterList = config.getFeedPersisterList();
			for (int m = 0; m < feedPersisterList.size(); m++) {
				IFeedPersister nextPersister = (IFeedPersister) (feedPersisterList.get(m));
				if (nextPersister instanceof ICustomizedFeedPersister) {
					DefaultLogger.debug(this,"Customized feed persister ");
					ICustomizedFeedPersister customizedPersister = (ICustomizedFeedPersister) nextPersister;
					Map propertiesMap = customizedPersister.getProperties();
					Object[] keyArr = propertiesMap.keySet().toArray();
					for (int n = 0; n < keyArr.length; n++) {
						DefaultLogger.debug(this,keyArr[n].toString() + "      " + propertiesMap.get(keyArr[n]).toString());
					}
				}
				else if (nextPersister instanceof DefaultUpdateFeedPersister) {
					DefaultLogger.debug(this,"Default update feed persister ");
					DefaultUpdateFeedPersister updatePersister = (DefaultUpdateFeedPersister) nextPersister;
					DefaultLogger.debug(this,"Selected fields " + updatePersister.getSelectedFields());
					DefaultLogger.debug(this,"Condition fields " + updatePersister.getConditionFields());
				}
				else if (nextPersister instanceof DefaultInsertFeedPersister) {
					DefaultLogger.debug(this,"Default insert feed persister ");
					DefaultInsertFeedPersister insertPersister = (DefaultInsertFeedPersister) nextPersister;
					DefaultLogger.debug(this,"Auto generate key: " + insertPersister.isAutoGenKey());
					DefaultLogger.debug(this,"Key column: " + insertPersister.getKeyColumn());
					DefaultLogger.debug(this,"Key sequence: " + insertPersister.getKeySequence());
					DefaultLogger.debug(this,"All fields: " + insertPersister.isAllFields());
					DefaultLogger.debug(this,"Selected fields " + insertPersister.getSelectedFields());
				}
			}

		}
	}
}
