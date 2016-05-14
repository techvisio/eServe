package com.techvisio.eserve.util;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Scanner;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.xmlgraphics.util.MimeConstants;

public class PDFGenerator implements Serializable {
	

	public void generate(String fileName, String templateFilePath, Map<String, Object> params){
		
//		final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//		final String template = new Scanner(new File(externalContext.getRealPath(templateFilePath))).useDelimiter("\\Z").next();
//		final StringWriter content = new StringWriter();
//		
//		        // Use Velocity to replace placeholders in template with desired values
//		        Velocity.init();
//		        VelocityContext velocityContext = new VelocityContext();
//		        velocityContext.put("param", params);
//		        Velocity.evaluate(velocityContext, content, "", template);
//		        final String sourceXml = content.toString();
//		 
//		        // Prepare output stream to which PDF will be written
//		        final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//		        response.setHeader("Content-disposition", "inline; filename=\"" + fileName + "\"");
//		        response.setContentType("application/pdf");
//		        response.setHeader("attachment", fileName);
//		 
//		        // Run FOP transformer which will generate PDF and write it to the stream
//		        final ServletOutputStream out = response.getOutputStream();
//		        final Fop fop = FopFactory.newInstance().newFop(MimeConstants.MIME_PDF, out);
//		        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		        transformer.setParameter("versionParam", "2.0");
//		        final Source src = new StreamSource(new StringReader(sourceXml));
//		        final Result res = new SAXResult(fop.getDefaultHandler());
//		        transformer.transform(src, res);
//		 
//		        // Close stream and http response - PDF file will be downloaded to user's machine
//		        out.flush();
//		        out.close();
//		        response.flushBuffer();
//		        FacesContext.getCurrentInstance().responseComplete();
	}
}
