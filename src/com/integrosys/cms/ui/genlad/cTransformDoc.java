package com.integrosys.cms.ui.genlad;
///**
// * Transform docx to HTLM and PDF.
// * @author 2mdc
// */
//package com.javadocx;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.Enumeration;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Result;
//import javax.xml.transform.Source;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.tidy.Tidy;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//import org.xml.sax.InputSource;
//
//public class cTransformDoc {
//
//  public final static String SCHEMA_IMAGEDOCUMENT = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
//  public final static String OFFICEDOCUMENT = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument";
//  private String strXHTML;
//  private String strFile;
//
//  public cTransformDoc() {
//  }
//
//   
//  public String toString() {
//    return this.strXHTML;
//  }
//
//  public String getStrXHTML() {
//    return this.strXHTML;
//  }
//
//  public void setStrFile(String strFile) {
//    this.strFile = new String(strFile);
//  }
//
//  public void fValidatorXHTML() {
//    try {
//      FileInputStream input = new FileInputStream("document.html");
//      Tidy tidy = new Tidy();
//      Document xmlDoc = tidy.parseDOM(input, null);
//      try {
//        Source source = new DOMSource(xmlDoc);
//        StringWriter stringWriter = new StringWriter();
//        Result result = new StreamResult(stringWriter);
//        TransformerFactory factory = TransformerFactory.newInstance();
//        Transformer transformer = factory.newTransformer();
//        transformer.transform(source, result);
//        this.strXHTML = stringWriter.getBuffer().toString();
//        this.strXHTML.replaceFirst("<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">", "<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></META>");
//      } catch (TransformerConfigurationException e) {
//        e.printStackTrace();
//      } catch (TransformerException e) {
//        e.printStackTrace();
//      }
//    } catch (java.io.FileNotFoundException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//  }
//
//  public void fGenerateXHTML() {
//    String rscRelations = new String();
//    String rscDocument = new String();
//    try {
//      ZipFile fZip = new ZipFile(this.strFile);
//      Enumeration zipEntries = fZip.entries();
//      while (zipEntries.hasMoreElements()) {
//        ZipEntry zZip = (ZipEntry) zipEntries.nextElement();
//        if (zZip.getName().equals("_rels/.rels")) {
//          BufferedReader br = new BufferedReader(new InputStreamReader(fZip.getInputStream(zZip)));
//          String line = new String();
//          while ((line = br.readLine()) != null) {
//            rscRelations = rscRelations + line;
//          }
//          br.close();
//        }
//        if (zZip.getName().equals("word/document.xml")) {
//          BufferedReader br = new BufferedReader(new InputStreamReader(fZip.getInputStream(zZip)));
//          String line = new String();
//          while ((line = br.readLine()) != null) {
//            rscDocument = rscDocument + line;
//          }
//          br.close();
//        }
//      }
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//    try {
//      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//      DocumentBuilder builder = factory.newDocumentBuilder();
//      Document xmlDocument = builder.parse(new InputSource(new StringReader(rscRelations)));
//      NodeList xmlRelationship = xmlDocument.getElementsByTagName("Relationship");
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//    try {
//      TransformerFactory tFactory = TransformerFactory.newInstance();
//      Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource("../xsl/docx2html.xsl"));
//      FileWriter fTempXML = new FileWriter("xml_html.txt");
//      BufferedWriter fXML = new BufferedWriter(fTempXML);
//      fXML.write(rscDocument);
//      fXML.close();
//      transformer.transform(new javax.xml.transform.stream.StreamSource("xml_html.txt"), new javax.xml.transform.stream.StreamResult(new FileOutputStream("document.html")));
//      byte[] buffer = new byte[(int) new File("document.html").length()];
//      FileInputStream fResultXSL = new FileInputStream("document.html");
//      fResultXSL.read(buffer);
//      fResultXSL.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//  }
//
//  public void fGeneratePDF() {
//    try {
//      this.fGenerateXHTML();
//      this.fValidatorXHTML();
//      File fPDF = new File("document_pdf.html");
//      FileOutputStream fOpPDF = new FileOutputStream(fPDF);
//      fOpPDF.write(this.strXHTML.getBytes());
//      fOpPDF.close();
//      String inputFile = "document_pdf.html";
//      String url = new File(inputFile).toURI().toURL().toString();
//      String outputFile = "document.pdf";
//      OutputStream os = new FileOutputStream(outputFile);
//      ITextRenderer renderer = new ITextRenderer();
//      renderer.setDocument(url);
//      renderer.layout();
//      renderer.createPDF(os);
//      os.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    }
//  }
//}
