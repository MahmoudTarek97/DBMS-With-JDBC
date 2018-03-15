package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DTDHandlers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DTDValidator {

	public boolean validateWithDTDUsingDOM(String xml) 
	{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			builder.setErrorHandler(
				new ErrorHandler() {
					@Override
					public void warning(SAXParseException e) throws SAXException {
						System.out.println("WARNING : " + e.getMessage());
		            }

		            @Override
					public void error(SAXParseException e) throws SAXException {
		            	System.out.println("ERROR : " + e.getMessage());
		            }

		            @Override
					public void fatalError(SAXParseException e) throws SAXException {
		            	System.out.println("FATAL : " + e.getMessage());
		            }
		        });
		     builder.parse(new InputSource(xml));
		     return true;
		    }
		    catch (Exception e) {
		    	return false;
		    }
	}
	
}
