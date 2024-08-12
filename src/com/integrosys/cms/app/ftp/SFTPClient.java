package com.integrosys.cms.app.ftp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.sftp.SftpClientFactory;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.fcubsLimitFile.schedular.FCUBSFileConstants;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SFTPClient extends CMSFtpClient {

	private ChannelSftp command;

	private Session session;

	public SFTPClient(ResourceBundle propertyName, String connectionFor) {
		super(propertyName,connectionFor);
		command = null;

	}

	public boolean openConnection() throws Exception  {

		DefaultLogger.debug(this, "SFTPClient.opendConnection() called!.. ");
		// If the client is already connected, disconnect
		if (command != null) {
			closeConnection();
		}
		FileSystemOptions fso = new FileSystemOptions();
		try {
			System.out.println("SFTPClient.opendConnection()line 45: "+password);
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fso, "no");
			System.out.println("SFTPClient.opendConnection() : "+password.toCharArray());
			session = SftpClientFactory.createConnection(server, port, username.toCharArray(), password.toCharArray(),
					fso);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			command = (ChannelSftp) channel;

		} catch (Exception e) {
			DefaultLogger.debug(this, "Encountered Error:::"+e);
			e.printStackTrace();
			throw e;
		} 
		return command.isConnected();
	}

	public void closeConnection() {
		if (command != null) {
			command.exit();
		}
		if (session != null) {
			session.disconnect();
		}
		command = null;
	}

	public Vector<String> listFileInDir(String remoteDir) throws Exception {
		DefaultLogger.debug(this, "remoteDir:::"+remoteDir);
		try {
			Vector<LsEntry> rs = command.ls(remoteDir);
			Vector<String> result = new Vector<String>();
			for (int i = 0; i < rs.size(); i++) {
				if (!isARemoteDirectory(rs.get(i).getFilename())) {
					result.add(rs.get(i).getFilename());
				}
			}
			return result;
		} catch (Exception e) {
			DefaultLogger.error(this, "Error ecountered during method call list File In Dir(): "+e);
			e.printStackTrace();
			System.err.println(remoteDir);
			throw e;
		}
	}

	public Vector<String> listSubDirInDir(String remoteDir) throws Exception {
		Vector<LsEntry> rs = command.ls(remoteDir);
		Vector<String> result = new Vector<String>();
		for (int i = 0; i < rs.size(); i++) {
			if (isARemoteDirectory(rs.get(i).getFilename())) {
				result.add(rs.get(i).getFilename());
			}
		}
		return result;
	}

	protected boolean createDirectory(String dirName) {
		try {
			command.mkdir(dirName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	protected boolean downloadFileAfterCheck(String remotePath, String localPath) throws Exception {
		FileOutputStream outputSrr = null;
		try {
			File file = new File(localPath);
			if (!file.exists()) {
				outputSrr = new FileOutputStream(localPath);
				command.get(remotePath, outputSrr);
			}
		} catch (SftpException e) {
			try {
				System.err.println(remotePath + " not found in " + command.pwd());
			} catch (SftpException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (outputSrr != null) {
				outputSrr.close();
			}
		}
		return true;
	}

	public void downloadFile(String localPath, String remotePath) throws Exception {
		DefaultLogger.debug(this, "downloadFile() method called ..");
		FileOutputStream outputSrr = new FileOutputStream(localPath);
		try {
			command.get(remotePath, outputSrr);
		} catch (SftpException e) {
			try {
				System.err.println(remotePath + " not found in " + command.pwd());
			} catch (SftpException e1) {
				DefaultLogger.error(this, "exception encountered in downloadFile() e1 .."+e1);
				e1.printStackTrace();
				throw e1;
			}
			DefaultLogger.error(this, "exception encountered in downloadFile()  .."+e);
			e.printStackTrace();
			throw e;
		} finally {
			if (outputSrr != null) {
				try {
					outputSrr.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}

	public void uploadFile(String localPath, String remotePath) throws Exception {
		DefaultLogger.debug(this, "localPath::"+localPath+":remotePath:"+remotePath);
		FileInputStream inputSrr = new FileInputStream(localPath);
		try {
			System.out.println("SFTPClient.java uploadFile()=>inputSrr=>"+inputSrr+"  remotePath=>"+remotePath);
			command.put(inputSrr, remotePath);
			System.out.println("After command.put() ");
		} catch (SftpException e) {
			DefaultLogger.error(this,"error encountered in uploadFile(): "+e);
			System.out.println("error encountered in uploadFile(): "+e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (inputSrr != null) {
				try {
					inputSrr.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}
	
	
	
	//Added New :Change LocalPath in uploadFile()
	public void uploadFileNew(String localPath, String remotePath) throws Exception {
		DefaultLogger.debug(this, "localPath::"+localPath+":remotePath:"+remotePath);

		try {
			System.out.println("SFTPClient.java uploadFile()=>localPath=>"+localPath+"  remotePath=>"+remotePath);
			command.put(localPath, remotePath);
			System.out.println("NPA uploadFile()");
		} catch (SftpException e) {
			DefaultLogger.error(this,"error encountered in uploadFile(): "+e);
			System.out.println("NPA error encountered in uploadFile(): "+e.getMessage());

			e.printStackTrace();
			throw e;
		} 
	}

	public boolean changeDir(String remotePath) throws Exception {
		try {
			command.cd(remotePath);
		} catch (SftpException e) {
			return false;
		}
		return true;
	}

	public boolean isARemoteDirectory(String path) {
		try {
			return command.stat(path).isDir();
		} catch (SftpException e) {
			// e.printStackTrace();
		}
		return false;
	}

	public String getWorkingDirectory() {
		try {
			return command.pwd();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	
	@Override
	public boolean deleteFile(String remoteFile) {
		try {
			command.rm(remoteFile);
		} catch (SftpException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public void uploadFCUBSFile(String localPath, String remotePath,String fileName) throws Exception {
		DefaultLogger.debug(this, "localPath::"+localPath+":remotePath:"+remotePath);
		FileInputStream inputSrr = new FileInputStream(localPath);
		
		try {
			command.cd(remotePath);
		} catch (SftpException e1) {

			try {
				command.mkdir(remotePath);
				command.chmod(511, remotePath);

			} catch (SftpException e) {

				e.printStackTrace();
			}
		}
		try {
			command.put(inputSrr, remotePath+fileName);
		} catch (SftpException e) {
			DefaultLogger.error(this,"error encountered in uploadFile(): "+e);
			e.printStackTrace();
			throw e;
		} finally {
			if (inputSrr != null) {
				try {
					inputSrr.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}
	
	
	public boolean downloadFCUBSFile(String remotePath, String localPath,ArrayList<String[]> fileList) throws Exception {
		boolean isDownload = false;
		FileOutputStream outputSrr1 = null;
		FileOutputStream outputSrr2 = null;
		try {
			
			 if (fileList != null && fileList.size() > 0) {
	    			for (String[] remoteFileName : fileList) {
	    				if (!".".equalsIgnoreCase(remoteFileName[0]) && !"..".equalsIgnoreCase(remoteFileName[0]) && !".".equalsIgnoreCase(remoteFileName[1]) && !"..".equalsIgnoreCase(remoteFileName[1])){
	    					
	    					
	    					try{
	    						
	    					
	    					command.get(remotePath+remoteFileName[0]);
	    					outputSrr1 = new FileOutputStream(localPath+remoteFileName[0]);
	    					command.get(remotePath+remoteFileName[0], outputSrr1);
	    					isDownload = true;
	    					}
	    					catch(SftpException e){
	    						
	    						
	    					}
	    					
	    					try{
	    					
	    					command.get(remotePath+remoteFileName[1]);
	    					outputSrr2 = new FileOutputStream(localPath+remoteFileName[1]);
	    					command.get(remotePath+remoteFileName[1], outputSrr2);
	    					isDownload = true;
	    					}
	    					catch(SftpException e){
	    						
	    					}
	    					
	    					
	    				}
	    			}
	            }
		} catch (Exception e) {
			try {
				System.err.println(remotePath + " not found in " + command.pwd());
			} catch (SftpException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (outputSrr2 != null) {
				outputSrr2.close();
			}
			if (outputSrr1 != null) {
				outputSrr1.close();
			}
		}
		return isDownload;
	}
	
	// For PSR Limit CR
	public boolean downloadPSRFile(String remotePath, String localPath, ArrayList<String[]> fileList) throws Exception {
		boolean isDownload = false;
		FileOutputStream outputSrr1 = null;
		FileOutputStream outputSrr2 = null;
		try {
			if (fileList != null && fileList.size() > 0) {
				for (String[] remoteFileName : fileList) {
					if (!".".equalsIgnoreCase(remoteFileName[0]) && !"..".equalsIgnoreCase(remoteFileName[0])
							&& !".".equalsIgnoreCase(remoteFileName[1]) && !"..".equalsIgnoreCase(remoteFileName[1])) {

						try {
							System.out.println("downloadPSRFile........1"+remotePath+"..."+remoteFileName[0]+"..."+localPath);
							command.get(remotePath + remoteFileName[0]);
							System.out.println("downloadPSRFile........2");
							outputSrr1 = new FileOutputStream(localPath + remoteFileName[0]);
							System.out.println("downloadPSRFile........3");
							command.get(remotePath + remoteFileName[0], outputSrr1);
							System.out.println("downloadPSRFile........4");
							isDownload = true;
							System.out.println("downloadPSRFile........5");
						} catch (SftpException e) {
							System.out.println("downloadPSRFile........"+e);
							e.printStackTrace();
						}

						try {
							System.out.println("downloadPSRFile2........1"+remotePath+"..."+remoteFileName[1]+"..."+localPath);
							command.get(remotePath + remoteFileName[1]);
							System.out.println("downloadPSRFile2........2");
							outputSrr2 = new FileOutputStream(localPath + remoteFileName[1]);
							System.out.println("downloadPSRFile2........3");
							command.get(remotePath + remoteFileName[1], outputSrr2);
							System.out.println("downloadPSRFile2........4");
							isDownload = true;
							System.out.println("downloadPSRFile2........5");
						} catch (SftpException e) {
							System.out.println("downloadPSRFile2........"+e);
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				System.err.println(remotePath + " not found in " + command.pwd());
			} catch (SftpException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (outputSrr2 != null) {
				outputSrr2.close();
			}
			if (outputSrr1 != null) {
				outputSrr1.close();
			}
		}
		return isDownload;
	}
	
	
	
	
	//For FCC Collateral Liquidation File download
	
	public boolean downloadFccColFile(String remotePath, String localPath, ArrayList<String> fileList) throws Exception {
		boolean isDownload = false;
		FileOutputStream outputSrr1 = null;
		FileOutputStream outputSrr2 = null;
		try {
			if (fileList != null && fileList.size() > 0) {
				for (String remoteFileName : fileList) {
					if (!".".equalsIgnoreCase(remoteFileName) && !"..".equalsIgnoreCase(remoteFileName)
							) {
						System.out.println(">>>Attemp to download Fcc Col Liquidation file from server<<<");

						try {
							System.out.println("downloadFccColFile........1=>"+remotePath+"..."+remoteFileName+"..."+localPath);
							command.get(remotePath + remoteFileName);
							System.out.println("downloadFccColFile........2");
							outputSrr1 = new FileOutputStream(localPath + remoteFileName);
							System.out.println("downloadFccColFile........3");
							command.get(remotePath + remoteFileName, outputSrr1);
							System.out.println("downloadFccColFile........4");
							isDownload = true;
							System.out.println("downloadFccColFile........5");
						} catch (SftpException e) {
							System.out.println("downloadFccColFile........"+e);
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				System.err.println(remotePath + " not found in " + command.pwd());
			} catch (SftpException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (outputSrr2 != null) {
				outputSrr2.close();
			}
			if (outputSrr1 != null) {
				outputSrr1.close();
			}
		}
		return isDownload;
	}
	
}

