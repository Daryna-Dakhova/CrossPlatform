import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

    public static void main(String[] args) {
        try {
            readXMLWithSAX();
            getNationalGroups();
            selectAndSavePopularNames();
            readAndDisplayDOMXML("Popular_Names_Sorted.xml");
            printXMLStructure();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void readXMLWithSAX() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLHandler xmlHandler = new XMLHandler();
        saxParser.parse(new File("Popular_Baby_Names_NY.xml"), xmlHandler);
        List<String> tags = xmlHandler.getTags();
        System.out.println("Список тегів у XML документі:");
        for (String tag : tags) {
            System.out.println(tag);
        }
    }

    private static void getNationalGroups() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        NationalityHandler nationalityHandler = new NationalityHandler();
        saxParser.parse(new File("Popular_Baby_Names_NY.xml"), nationalityHandler);
        List<String> nationalities = nationalityHandler.getNationalities();
        System.out.println("\nНазви національних груп:");
        for (String nationality : nationalities) {
            System.out.println(nationality);
        }
    }

    private static void selectAndSavePopularNames() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File("Popular_Baby_Names_NY.xml"));

        NodeList rowNodes = document.getElementsByTagName("row");
        List<NameData> nameDataList = new ArrayList<>();
        for (int i = 0; i < rowNodes.getLength(); i++) {
            Element rowElement = (Element) rowNodes.item(i);
            String name = rowElement.getElementsByTagName("nm").item(0).getTextContent();
            String gender = rowElement.getElementsByTagName("gndr").item(0).getTextContent();
            int count = Integer.parseInt(rowElement.getElementsByTagName("cnt").item(0).getTextContent());
            int rating = Integer.parseInt(rowElement.getElementsByTagName("rnk").item(0).getTextContent());
            nameDataList.add(new NameData(name, gender, count, rating));
        }
        Collections.sort(nameDataList);
        System.out.println("\nНайбільш популярні імена:");
        for (NameData nameData : nameDataList.subList(0, Math.min(10, nameDataList.size()))) {
            System.out.println(nameData);
        }

        saveToXML(nameDataList);
    }

    private static void saveToXML(List<NameData> nameDataList) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("popular_names");
            document.appendChild(root);

            for (NameData nameData : nameDataList) {
                Element nameElement = document.createElement("name");
                nameElement.setAttribute("gender", nameData.gender);
                nameElement.setAttribute("count", String.valueOf(nameData.count));
                nameElement.setAttribute("rank", String.valueOf(nameData.rating));
                nameElement.setTextContent(nameData.name);
                root.appendChild(nameElement);
            }

            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource domSource = new javax.xml.transform.dom.DOMSource(document);
            javax.xml.transform.stream.StreamResult streamResult = new javax.xml.transform.stream.StreamResult(new File("Popular_Names_Sorted.xml"));
            transformer.transform(domSource, streamResult);

            System.out.println("\nІнформація збережена до файлу Popular_Names_Sorted.xml");

        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void readAndDisplayDOMXML(String fileName) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));
            NodeList nameNodes = document.getElementsByTagName("name");
            System.out.println("\nІнформація з файлу " + fileName + ":");
            for (int i = 0; i < nameNodes.getLength(); i++) {
                Node node = nameNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getTextContent();
                    String gender = element.getAttribute("gender");
                    String count = element.getAttribute("count");
                    String rank = element.getAttribute("rank");
                    System.out.println("Name: " + name +
                            ", Gender: " + gender +
                            ", Count: " + count +
                            ", Rank: " + rank);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printXMLStructure() throws ParserConfigurationException, SAXException, IOException {
        System.out.println("\nВиведення структури XML документу:");
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        StructureHandler structureHandler = new StructureHandler();
        saxParser.parse(new File("Popular_Baby_Names_NY.xml"), structureHandler);
    }

    private static class XMLHandler extends DefaultHandler {
        private List<String> tags = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (!tags.contains(qName)) {
                tags.add(qName);
            }
        }

        public List<String> getTags() {
            return tags;
        }
    }

    private static class NationalityHandler extends DefaultHandler {
        private List<String> nationalities = new ArrayList<>();
        private boolean inEthcty = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("ethcty")) {
                inEthcty = true;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (inEthcty) {
                String nationality = new String(ch, start, length);
                if (!nationalities.contains(nationality)) {
                    nationalities.add(nationality);
                }
                inEthcty = false;
            }
        }

        public List<String> getNationalities() {
            return nationalities;
        }
    }

    private static class NameData implements Comparable<NameData> {
        private String name;
        private String gender;
        private int count;
        private int rating;

        public NameData(String name, String gender, int count, int rating) {
            this.name = name;
            this.gender = gender;
            this.count = count;
            this.rating = rating;
        }

        @Override
        public int compareTo(NameData o) {
            return Integer.compare(this.rating, o.rating);
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Gender: " + gender + ", Count: " + count + ", Rank: " + rating;
        }
    }

    private static class StructureHandler extends DefaultHandler {
        private List<String> tags = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (!tags.contains(qName)) {
                tags.add(qName);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("Теги у XML документі:");
            for (String tag : tags) {
                System.out.println(tag);
            }
        }
    }
}
