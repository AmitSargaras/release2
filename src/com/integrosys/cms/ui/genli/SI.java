package com.integrosys.cms.ui.genli;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This class represents the pertinent details about the file to be
 * downloaded.
 *
 * @since Struts 1.2.5
 * @author <a href="mailto:frank@zammetti.com">Frank W. Zammetti</a>
 */
public class SI implements StreamInfo {

    private String contentType;
    private File file;
    

    public SI(String contentType, File file) {
        this.contentType = contentType;
        this.file = file;
    }

    public String getContentType() {
        return this.contentType;
    }

  /**
   * Method to get a stream on the file to download
   *
   * @return The InputStream wrapping the file to download
   *
   */
    public InputStream getInputStream() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        return bis;
    }
}