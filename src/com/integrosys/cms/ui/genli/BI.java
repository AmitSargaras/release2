package com.integrosys.cms.ui.genli;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts.actions.DownloadAction.StreamInfo;

/**
 * This class represents the pertinent details about the file to be
 * downloaded.
 *
 * @since Struts 1.2.5
 * @author <a href="mailto:frank@zammetti.com">Frank W. Zammetti</a>
 */
public class BI implements StreamInfo {

    protected String contentType;
    protected byte[] bytes;

    

    public BI(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
    }

    public String getContentType() {
        return this.contentType;
    }
    
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }
}