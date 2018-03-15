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

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities.ComparatorsFactory;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities.ComparisonBehavior;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class DeleteFromTableExecutionBehavior implements ExecutionBehavior {

	@Override
	public Object execute(QueryProperties properties) {
		String path = System.getProperty("user.dir") + File.separator + "Databases" + File.separator
				+ properties.databaseName + File.separator + properties.tableName;

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
		int counter = 0;
		try {
			db = dbf.newDocumentBuilder();
			dom = db.parse(is);
			Element table = (Element) dom.getElementsByTagName("table").item(0);
			Element columns = (Element) table.getElementsByTagName("columns").item(0);
			ArrayList<String> columnsNames = new ArrayList<>();
			ArrayList<String> columnsTypes = new ArrayList<>();
			NodeList columnsInTable = columns.getChildNodes();
			Node tmp;
			Element rows = (Element) table.getElementsByTagName("rows").item(0);
			NodeList listOfRows = rows.getElementsByTagName("row");
			for (int i = 0; i < columnsInTable.getLength(); i++) {
				tmp = columnsInTable.item(i);
				if (tmp.getNodeType() == Node.ELEMENT_NODE) {
					columnsNames.add(tmp.getNodeName());
					columnsTypes.add(tmp.getAttributes().item(0).getNodeValue());
				}
			}
			if (properties.conditions == null) {
					for (int i = 0; i < listOfRows.getLength(); i++) {
						Node x = listOfRows.item(i);
						if(x.getNodeType() == Node.ELEMENT_NODE)
						{
							rows.removeChild(listOfRows.item(i));
							i--;
							counter++;
						}	
					}
			} else if (!columnsNames.contains(properties.conditions.columnsName)) {
				throw new RuntimeException("column doesn't exist");
			} else {
				ComparisonBehavior comparator;
				comparator = (new ComparatorsFactory()).createComparator(properties.conditions.operation);
				Node propertiesOfRow;
				NodeList listOfColumns;
				boolean comparison;
				for (int i = 0; i < listOfRows.getLength(); i++) {
					propertiesOfRow = listOfRows.item(i);
					listOfColumns = propertiesOfRow.getChildNodes();
					for (int j = 0; j < listOfColumns.getLength(); j++) {
						if (listOfColumns.item(j).getNodeType() == Node.ELEMENT_NODE) {

							if (listOfColumns.item(j).getNodeName().equals(properties.conditions.columnsName)) {
								if (!listOfColumns.item(j).getAttributes().item(0).getNodeValue().equals("NULL")) {
									comparison = comparator.compare(
											listOfColumns.item(j).getAttributes().item(0).getNodeValue(),
											properties.conditions.value);
									if (comparison) {
										rows.removeChild(listOfRows.item(i));
										i--;
										counter++;
									}
								}
							}
						}
					}
				}
			}

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
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return counter;
	}
}