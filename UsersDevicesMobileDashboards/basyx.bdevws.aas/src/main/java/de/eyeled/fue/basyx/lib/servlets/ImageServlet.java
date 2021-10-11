package de.eyeled.fue.basyx.lib.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {	
	private static final long serialVersionUID = -4268648530342563540L;
	private String imageData;
	
	public ImageServlet(String imageData) {
		super();
		this.imageData = imageData;
	}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // it is the responsibility of the container to close output stream
        OutputStream os = response.getOutputStream();
        boolean error = false;
        
        if(imageData == null) {
        	error = true;
        }
        else {
        	if(imageData.startsWith("data:")) {
        		imageData = imageData.split(",")[1];
        	}
        	try {
        		byte[] decoded = Base64.getMimeDecoder().decode(imageData);
		        if (decoded.length == 0) {
		        	error = true;
		        } else {
		            ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
		            response.setContentType("image/jpeg");
		
		            byte[] buffer = new byte[1024];
		            int bytesRead;
		
		            while ((bytesRead = bis.read(buffer)) != -1) {
		                os.write(buffer, 0, bytesRead);
		            }
		        }
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		System.err.println("error get image: "+e.getMessage());
	        	error = true;
        	}
        }
        
        if(error) {
            response.setContentType("text/plain");
            os.write("Failed to send image".getBytes());
        }
    }
}