package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class InsertIntoTableExecutionBehavior implements ExecutionBehavior {


    @Override
    public Object execute(QueryProperties properties) {

        String path = System.getProperty("user.dir") + File.separator +"Databases"+ File.separator + properties.databaseName + File.separator +properties.tableName;
        File dtdFile = new File(path + ".dtd");
        
     	InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(path + ".xml");
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
        
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(is);
            Element table = (Element) dom.getElementsByTagName("table").item(0);
            Element columns = (Element) table.getElementsByTagName("columns").item(0);
            ArrayList<String> columnsNames = new ArrayList<>();
            ArrayList<String> columnsTypes = new ArrayList<>();
            NodeList columnsInTable =  columns.getChildNodes();
            Node tmp ;
            for(int i =0 ;i < columnsInTable.getLength() ;i++) {
                tmp = columnsInTable.item(i);
                if (tmp.getNodeType() == Node.ELEMENT_NODE) {

                    columnsNames.add(tmp.getNodeName());
                    columnsTypes.add(tmp.getAttributes()
                            .item(0).getNodeValue());
                }
            }
            Element rows = (Element) table.getElementsByTagName("rows").item(0);
            Element row = dom.createElement("row");
            String currentColumnName;
            String currentColumnValue;
            Element column;
            int counter = 0;
            for(int i = 0 ; i < columnsNames.size();i++) {
                currentColumnName = columnsNames.get(i).toLowerCase();
                column = dom.createElement(currentColumnName);
                if(properties.columns.contains(currentColumnName)) {
                    currentColumnValue = properties.values.get(
                            properties.columns.indexOf(currentColumnName));
                    if(columnsTypes.get(i).equalsIgnoreCase("INTEGER") || columnsTypes.get(i).equalsIgnoreCase("INT")) {
                        try {
                        	Integer.parseInt(currentColumnValue);
                        } catch (Exception e) {
                            throw new RuntimeException("inconsistent data type");
                        }
                    }
                    column.setAttribute("value",currentColumnValue);
                    counter++;
                } else {
                    column.setAttribute("value","NULL");
                }
                row.appendChild(column);
            }
            if(counter != properties.columns.size()) {
                throw new RuntimeException("the is not such column");
            }
            rows.appendChild(row);
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(path + ".xml")));

            } catch (TransformerException te) {
                return false;
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
