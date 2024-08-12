package com.integrosys.cms.ui.genli;

import org.apache.struts.action.ActionMapping;

/**
 * This class defines a custom action mapping is used when downloading a file
 * from the file system.  To use it, set the type of an action mapping in
 * struts-config.xml to this classname.  All the attributes in the config file
 * are set with set-property elements.  Struts handles populating the instance
 * of this class with those values for us and the instance will be passed to
 * the Action indicated in the mapping.
 *
 * @since Struts 1.2.5
 * @author <a href="mailto:frank@zammetti.com">Frank W. Zammetti</a>
 */
public class FileDownloadActionMapping extends ActionMapping {

  // Full path to the file to download.  Note that this is NOT relative to the
  // webapp, it can be anywhere on the file system (local or remote via mapped
  // drives or mounted volumes)
  private String filePath;

  // The content type header to be set in the response
  private String contentType;

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFilePath() {
    return this.filePath;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentType() {
    return this.contentType;
  }

}