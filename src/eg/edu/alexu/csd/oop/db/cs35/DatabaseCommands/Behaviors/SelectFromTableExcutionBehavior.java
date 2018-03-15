package eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors;

import java.io.*;
import java.util.ArrayList;

import javax.sound.midi.Soundbank;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities.ComparatorsFactory;
import eg.edu.alexu.csd.oop.db.cs35.DatabaseCommands.Behaviors.behaviorUtilities.ComparisonBehavior;
import eg.edu.alexu.csd.oop.db.cs35.Parser.QueryProperties;

public class SelectFromTableExcutionBehavior implements ExecutionBehavior {
    @Override
    public Object execute(QueryProperties properties) {
    	String path = System.getProperty("user.dir") + File.separator +"Databases" +File.separator + properties.databaseName + File.separator +properties.tableName + ".xml" ;
    	
    	InputStream inputStream;
		Reader reader = null;
		try {
			inputStream = new FileInputStream(path);
			try {
				reader = new InputStreamReader(inputStream, "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} catch (FileNotFoundException e1) {
			return null;
		}
		InputSource is = new InputSource(reader);
		is.setEncoding("ISO-8859-1");
		
    	Document dom ;
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance() ;
    	DocumentBuilder db = null ;
    	try {
    		db = dbf.newDocumentBuilder();
    		dom = db.parse(is);
    		Element table = (Element) dom.getElementsByTagName("table").item(0);
    		Element columns = (Element) table.getElementsByTagName("columns").item(0);
    		ArrayList<String> columnsNames = new ArrayList<>() ;
    		ArrayList<String> columnsTypes = new ArrayList<>() ;
    		NodeList columnsInTable = columns.getChildNodes() ;
    		Node tmp ;
    		Element rows = (Element) table.getElementsByTagName("rows").item(0);
    		NodeList listOfRows = rows.getElementsByTagName("row") ;
    		for (int i = 0 ; i < columnsInTable.getLength() ; i++) {
    			tmp = columnsInTable.item(i);
    			if (tmp.getNodeType() == Node.ELEMENT_NODE) {
    				columnsNames.add(tmp.getNodeName()) ;
    				columnsTypes.add(tmp.getAttributes().item(0).getNodeValue()) ;
    			}	
    		}
    		
    		ArrayList<ArrayList<Object>> selectedItems = new ArrayList<ArrayList<Object>>() ;
    		ArrayList<Object> currentRow ;
    		Node propertiesOfRow;
			NodeList listOfColumns;
    		if (properties.conditions.columnsName == null) {
    			if (properties.columns!= null && properties.columns.get(0).equals("*")) {
    				for (int i = 0 ; i < listOfRows.getLength() ; i ++) {
    					currentRow = new ArrayList<Object>() ;
    					propertiesOfRow = listOfRows.item(i);
    					listOfColumns = propertiesOfRow.getChildNodes() ;
    					for (int j = 0 ; j < listOfColumns.getLength() ; j++) {
    						if (listOfColumns.item(j).getNodeType() == Node.ELEMENT_NODE) {
    							if (columnsTypes.get(columnsNames.indexOf(listOfColumns.item(j).getNodeName())).equals("INTEGER"))
								{
									currentRow.add(Integer.parseInt(listOfColumns.item(j).getAttributes().item(0).getNodeValue())) ;
								}else {
									currentRow.add(listOfColumns.item(j).getAttributes().item(0).getNodeValue()) ;
								}
    						}
    					}
    					selectedItems.add(currentRow);
    				}
    			}else {
    				for (int i = 0 ; i < listOfRows.getLength() ; i ++) {
    					currentRow = new ArrayList<Object>() ;
    					propertiesOfRow = listOfRows.item(i);
    					listOfColumns = propertiesOfRow.getChildNodes() ;
    					for (int j = 0 ; j < listOfColumns.getLength() ; j++) {
    						if (listOfColumns.item(j).getNodeType() == Node.ELEMENT_NODE) {
    							if (properties.columns.contains(listOfColumns.item(j).getNodeName())) {
									if (columnsTypes.get(columnsNames.indexOf(listOfColumns.item(j).getNodeName())).equals("INTEGER")) {
										currentRow.add(Integer.parseInt(listOfColumns.item(j).getAttributes().item(0).getNodeValue())) ;
    									}else {
    										currentRow.add(listOfColumns.item(j).getAttributes().item(0).getNodeValue()) ;
    									}
									}
								}
    						}
    					selectedItems.add(currentRow) ;
    					}
    			}
    		}else {
    			boolean satisfyCondition = false ;
    			ComparisonBehavior comparator;
                if((properties.conditions.operation.equals("!=") || properties.conditions.operation.equals("="))){
                    if(!columnsTypes.get(columnsNames.indexOf(properties.conditions.columnsName)).equals("INTEGER")){
                        throw new RuntimeException("Cannot compare strings");
                    }
                }
                comparator = (new ComparatorsFactory()).createComparator(properties.conditions.operation);
                boolean comparison;
    			if (properties.columns.get(0) == "*") {
    				for (int i = 0 ; i < listOfRows.getLength() ; i++) {
    					satisfyCondition = false ;
    					currentRow = new ArrayList<Object>() ;
        				propertiesOfRow = listOfRows.item(i);
        				listOfColumns = propertiesOfRow.getChildNodes();
        				for (int j = 0 ; j < listOfColumns.getLength() ; j++) {
    						if (listOfColumns.item(j).getNodeType() == Node.ELEMENT_NODE) {
    							if (columnsTypes.get(columnsNames.indexOf(listOfColumns.item(j).getNodeName())).equals("INTEGER")) {
									currentRow.add(Integer.parseInt(listOfColumns.item(j).getAttributes().item(0).getNodeValue())) ;
									}else {
										currentRow.add(listOfColumns.item(j).getAttributes().item(0).getNodeValue()) ;
									}
								if (listOfColumns.item(j).getNodeName().equals(properties.conditions.columnsName)) {
									 comparison = comparator.compare(listOfColumns.item(j).getAttributes()
	                                            .item(0).getNodeValue(),
	                                            properties.conditions.value);
	                                    if (comparison) {
	                                        satisfyCondition = true ;
	                                    }
									}
    							}
    						}
        				if (satisfyCondition) {
        					selectedItems.add(currentRow);
        				}
    					}
        						
        			}else {
        				for (int i = 0 ; i < listOfRows.getLength() ; i++) {
        					satisfyCondition = false ;
        					currentRow = new ArrayList<Object>() ;
            				propertiesOfRow = listOfRows.item(i);
            				listOfColumns = propertiesOfRow.getChildNodes();
            				for (int j = 0 ; j < listOfColumns.getLength() ; j++) {
        						if (listOfColumns.item(j).getNodeType() == Node.ELEMENT_NODE) {
        							if (properties.columns.contains(listOfColumns.item(j).getNodeName())) {
        								int name =columnsNames.indexOf(listOfColumns.item(j).getNodeName());
    									if (columnsTypes.get(name).equalsIgnoreCase("INTEGER")) {
    										currentRow.add(Integer.parseInt(listOfColumns.item(j).getAttributes().item(0).getNodeValue())) ;
        									}else {
        										currentRow.add(listOfColumns.item(j).getAttributes().item(0).getNodeValue()) ;
        									}
        							}
        								if (listOfColumns.item(j).getNodeName().equals(properties.conditions.columnsName)) {
        									 comparison = comparator.compare(listOfColumns.item(j).getAttributes()
     	                                            .item(0).getNodeValue(),
     	                                            properties.conditions.value);
     	                                    if (comparison) {
     	                                        satisfyCondition = true ;
     	                                    }
        								}    								
        							}
        						}
            				if (satisfyCondition) {
            					selectedItems.add(currentRow);
            					}
        					}
        			}
    			}
    		int size = properties.columns.size();
    		if(properties.columns.size() == 1 && properties.columns.get(0).equals("*")) {
    			size = columnsNames.size();
    		}
    		Object[][] selectedItemsArr = new Object[selectedItems.size()][size];
    		for(int i=0;i<selectedItems.size();i++){
    			for(int j=0;j<size;j++){
    				if(selectedItems.get(i).get(j) instanceof Integer)
    					selectedItemsArr[i][j] = (int) selectedItems.get(i).get(j);
    				else
    					selectedItemsArr[i][j] = selectedItems.get(i).get(j);
    			}
    		}

    		createMetaDataFile(properties,columnsNames,columnsTypes);
    		return selectedItemsArr;

    	}catch(Exception e ) {
    		return null;
    	}
    	
    }
    
    public void createMetaDataFile(QueryProperties properties
		,ArrayList<String> columnsNames
		,ArrayList<String> columnsTypes) throws IOException {

    	File metaDataFile = new File("MetaData.txt");

    	metaDataFile.createNewFile();

		BufferedWriter bw = new BufferedWriter(new FileWriter("MetaData.txt"));

		if(properties.columns.size() == 1 && properties.columns.get(0).equalsIgnoreCase("*")){
			properties.columns = columnsNames;
		}

		bw.write(properties.tableName + "\n");
		bw.write(properties.columns.size()+"\n");
		StringBuilder typesBuilder = new StringBuilder();
		StringBuilder columnsBuilder = new StringBuilder();
		for(int i = 0 ;i < properties.columns.size() - 1 ;i++ ){
			typesBuilder.append(columnsTypes.get(columnsNames.indexOf(properties.columns.get(i)))+" ");
			columnsBuilder.append(properties.columns.get(i)+" ");
		}

		typesBuilder.append(columnsTypes.get(columnsNames.indexOf(properties.columns.get(properties.columns.size() -1))));
		columnsBuilder.append(properties.columns.get(properties.columns.size() -1));
		bw.write(columnsBuilder.toString()+"\n");
		bw.write(typesBuilder.toString());
		bw.close();
		
	}
}