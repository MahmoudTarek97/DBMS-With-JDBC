package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.DTDHandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;


public class DTDFileWriter {

	public boolean write(QueryProperties properties)
	{	
		try{
			String path = System.getProperty("user.dir") +File.separator+"Databases"+File.separator + properties.databaseName + File.separator	+ properties.tableName + ".dtd";
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));

			bw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			bw.newLine();
			bw.write("<!ELEMENT table (columns, rows)>");
			bw.newLine();
			
			
			String startElement = "<!ELEMENT ";
			String endElement = " EMPTY>";
			
			String startAttr = "<!ATTLIST ";
			String endAttr = " value CDATA #REQUIRED>";
			
			String columnsElement = "";
			for(int i = 0 ; i < properties.columns.size()/2 ; i++)
			{
				columnsElement += properties.columns.get(i) + ", ";
			}
			
			columnsElement = columnsElement.substring(0, columnsElement.length() - 2);
			
			bw.write(startElement + "column " + "(" + columnsElement + ")>");
			bw.newLine();
			
			bw.write("<!ELEMENT rows (row)*>");
			bw.newLine();
			
			bw.write(startElement + "row " + "(" + columnsElement + ")>");
			bw.newLine();
			
			for(int i = 0 ; i < properties.columns.size() / 2 ; i++)
			{
				bw.write(startElement + properties.columns.get(i) + endElement);
				bw.newLine();
				bw.write(startAttr + properties.columns.get(i) + endAttr);
				bw.newLine();
			}
			bw.close();	
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean makeTable(QueryProperties properties)
	{
		//String dtdPath = System.getProperty("user.dir") +File.separator+"Databases"+File.separator + properties.databaseName + File.separator	+ properties.tableName.toLowerCase() + ".dtd";
		String xmlPath = System.getProperty("user.dir") +File.separator+"Databases"+File.separator + properties.databaseName + File.separator	+ properties.tableName.toLowerCase() + ".xml";
		
		/*File dtdFile = new File(dtdPath);
		*/
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			
			Element table = dom.createElement("table");
			Element column = dom.createElement("columns");
			Element rows = dom.createElement("rows");
			
			
			for(int i = 0 ; i < properties.columns.size()/2 ; i++){
				Element columnValue = dom.createElement(properties.columns.get(i));
				columnValue.setAttribute("value", properties.columns.get(i + properties.columns.size()/2));
				column.appendChild(columnValue);
				
				
				
			}
				
			table.appendChild(column);
			table.appendChild(rows);
			dom.appendChild(table);
			
			
			try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                tr.transform(new DOMSource(dom), 
                        new StreamResult(new FileOutputStream(xmlPath)));
            } catch (TransformerException te) {
            	return false;
            }
		} catch(Exception e) {
			return false;
		}
		
		return true;
		
	}
}
