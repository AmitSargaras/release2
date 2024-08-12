package com.integrosys.cms.ui.genlad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.lad.bus.GeneratePartyLADDao;

public class DocumentBuilder implements Runnable {

	List<String> filesListInDir = new ArrayList<String>();

	private String limitProfileId;

	private String customerId;

	private String dirName;

	private String relationshipMgrId;

	private String partyId;

	private String dueYear;

	private String dueMonth;

	private List resultList;

	private File directory;

	private String zipPath;

	private AtomicInteger partialCounter = new AtomicInteger(0);

	private CountDownLatch latch;

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getRelationshipMgrId() {
		return relationshipMgrId;
	}

	public void setRelationshipMgrId(String relationshipMgrId) {
		this.relationshipMgrId = relationshipMgrId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getDueYear() {
		return dueYear;
	}

	public void setDueYear(String dueYear) {
		this.dueYear = dueYear;
	}

	public String getDueMonth() {
		return dueMonth;
	}

	public void setDueMonth(String dueMonth) {
		this.dueMonth = dueMonth;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getReportGenerationDate() {
		return reportGenerationDate;
	}

	public void setReportGenerationDate(String reportGenerationDate) {
		this.reportGenerationDate = reportGenerationDate;
	}

	private String segment;

	private String reportGenerationDate;

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

		private List resultList;

		private File directory;

		private String zipPath;

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

		public Builder resultList(List resultList) {
			this.resultList = resultList;
			return this;
		}

		public Builder directory(File directory) {
			this.directory = directory;
			return this;
		}

		public Builder zipPath(String zipPath) {
			this.zipPath = zipPath;
			return this;
		}

		public Builder latch(CountDownLatch latch) {
			this.latch = latch;
			return this;
		}

		public DocumentBuilder build() {
			return new DocumentBuilder(this);
		}

	}

	public DocumentBuilder(Builder b) {
		this.limitProfileId = b.limitProfileId;
		this.customerId = b.customerId;
		this.dirName = b.dirName;
		this.relationshipMgrId = b.relationshipMgrId;
		this.partyId = b.partyId;
		this.dueYear = b.dueYear;
		this.dueMonth = b.dueMonth;
		this.segment = b.segment;
		this.reportGenerationDate = b.reportGenerationDate;
		this.resultList = b.resultList;
		this.directory = b.directory;
		this.zipPath = b.zipPath;
		this.latch = b.latch;
		
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public String getZipPath() {
		return zipPath;
	}

	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}

	@Override
	public void run() {

		GeneratePartyLADDao partyLadDao = (GeneratePartyLADDao) BeanHouse
				.get("ladPartyDao");

		try {

			DefaultLogger.debug(this,
					" Main thread started ---------DocumentBuilder");
			latch = new CountDownLatch(resultList.size());
			ExecutorService executor = ThreadPoolSynchronizer.getInstance()
					.getExecutorService(); // of 10 threads

			DefaultLogger.debug(this,
					" Thread pool initialized ---------Threadpool");

			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				Map partyMap = (Map) iterator.next();

				Runnable lad = new GenerateLad.Builder()
						.limitProfileId((String) partyMap.get("LIMIT_ID"))
						.customerId((String) partyMap.get("CUSTOMER_ID"))
						.relationshipMgrId(relationshipMgrId).partyId(partyId)
						.dueMonth(dueMonth).latch(latch).dueYear(dueYear)
						.segment(segment).dirName(dirName)
						.reportGenerationDate(reportGenerationDate)
						.partialCounter(partialCounter).build();

				executor.execute(lad);

				DefaultLogger.debug(this,
						" Thread pool execution started ------------------");

			}

			DefaultLogger.debug(this, " Threadpool awaits--------------- ");
			latch.await();

			zipDirectory(directory, zipPath);

			DefaultLogger.debug(this,
					"File directory deletion begin---------------");
			FileUtils.forceDelete(directory);
			filesListInDir.clear();

			DefaultLogger.debug(this,
					"File directory deletion end---------------");

			if (partialCounter.intValue() == resultList.size()) {
				
				partyLadDao.updateToFail(this.relationshipMgrId, this.partyId,
						this.dueMonth, this.dueYear, this.segment,
						reportGenerationDate);
				
			}else if (partialCounter.intValue() > 0 && partialCounter.intValue()<resultList.size()) {
				
				
				partyLadDao.updateToPartialSuccess(this.relationshipMgrId,
						this.partyId, this.dueMonth, this.dueYear,
						this.segment, reportGenerationDate);
				
				
			} else  if(partialCounter.intValue()==0){
				
				partyLadDao.updateToCompletedAndFileName(
						this.relationshipMgrId, this.partyId, this.dueMonth,
						this.dueYear, this.segment, this.dirName + ".zip",
						reportGenerationDate);
			}
		} catch (Exception e) {
			e.printStackTrace();

			partyLadDao.updateToFail(this.relationshipMgrId, this.partyId,
					this.dueMonth, this.dueYear, this.segment,
					reportGenerationDate);
		}
	}

	private void zipDirectory(File dir, String zipDirName) {
		try {

			DefaultLogger.debug(this, "File zipping Begin---------------");
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

			DefaultLogger.debug(this, "File zipping end---------------");
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
