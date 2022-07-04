package ru.nsu.fit.sokolova.dis.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import ru.nsu.fit.sokolova.dis.models.Node;
import ru.nsu.fit.sokolova.dis.models.Tag;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Getter
public class XMLProcessor {

    private static final String MAX_DEFINED_LIMIT = "all";
    private static final int SIZE_OF_BUFFER = 4096 * 32;
    private static final String ENCODING = "UTF-8";
    private static final String NODE_ELEMENT = "node";
    private static final String COUNTING_ELEMENT_NAME = NODE_ELEMENT;
    private final static String INFO_DELIMITER = "/";
    private static final String KEY_TO_FIND = "name";

    private final int limit;
    private final String xmlFilePath;

    private final Map<String, Map<String, Integer>> stat;
    private long timeSpent;
    private int readElementsCounter;
    private int specifiedNodesCounter;


    public XMLProcessor(String limitStr, String xmlFilePath) {

        this.limit = limitStr.equals(MAX_DEFINED_LIMIT) ? Integer.MAX_VALUE : Integer.parseInt(limitStr);
        this.xmlFilePath = xmlFilePath;
        stat = new HashMap<>();
        readElementsCounter = 0;
        specifiedNodesCounter = 0;
    }

    public void calcStat() throws XMLStreamException, IOException, JAXBException {
        log.info("Started calculating statistic.");
        long start = System.currentTimeMillis();
        readData();
        log.info("Finished calculating statistic.");
        long end =  System.currentTimeMillis();
        timeSpent = end - start;
    }

    private void readData() throws IOException, XMLStreamException, JAXBException {
        try (InputStream in = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(xmlFilePath), SIZE_OF_BUFFER))) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(in, ENCODING);
            Unmarshaller unmarshaller = JAXBContext.newInstance(Node.class).createUnmarshaller();

            while (readElementsCounter < limit && reader.hasNext())  {
                XMLEvent nextEvent = reader.peek();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String startElementName = startElement.getName().getLocalPart();
                    if (COUNTING_ELEMENT_NAME.equals(startElementName)) {
                        Node node = (Node) unmarshaller.unmarshal(reader);
                        String user = node.getUser();
                        String uid = String.valueOf(node.getUid());
                        String changeset = String.valueOf(node.getChangeset());
                        String id = uid + INFO_DELIMITER + user;
                        stat.putIfAbsent(id, new HashMap<>());
                        stat.get(id).merge(changeset, 1, Integer::sum);

                        for (Tag tag: node.getTag()) {
                            String key = tag.getK();
                            if(key.equals(KEY_TO_FIND)) {
                                specifiedNodesCounter++;
                                log.info("Found " + KEY_TO_FIND + " at node with id " + node.getId());
                                break;
                            }
                        }
                        readElementsCounter++;
                    }
                }
            reader.nextEvent();
            }
        }
    }
}
