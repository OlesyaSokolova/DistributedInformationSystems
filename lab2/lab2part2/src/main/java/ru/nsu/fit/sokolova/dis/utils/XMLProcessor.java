package ru.nsu.fit.sokolova.dis.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import ru.nsu.fit.sokolova.dis.dao.impl.NodeDao;
import ru.nsu.fit.sokolova.dis.inserters.INSERT_STRATEGY;
import ru.nsu.fit.sokolova.dis.inserters.Inserter;
import ru.nsu.fit.sokolova.dis.inserters.InsertersFactory;
import ru.nsu.fit.sokolova.dis.inserters.impl.BatchInserter;
import ru.nsu.fit.sokolova.dis.models.Node;

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
import java.sql.SQLException;
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

    private final Map<INSERT_STRATEGY, Double> speeds;
    private int readElementsCounter;
    private final DataBaseManager dbManager;

    public XMLProcessor(String limitStr, String xmlFilePath) throws SQLException, IOException {

        this.limit = limitStr.equals(MAX_DEFINED_LIMIT) ? Integer.MAX_VALUE : Integer.parseInt(limitStr);
        this.xmlFilePath = xmlFilePath;
        readElementsCounter = 0;
        speeds = new HashMap<>();

        dbManager = new DataBaseManager();
        dbManager.initDB();
    }

    private void insertData(Inserter inserter, Node node, INSERT_STRATEGY strategyType) throws SQLException {

        if(inserter == null) {
            log.error("Inserter for strategy \"" + strategyType + "\" is not implemented. It will be skipped. Node id = " + node.getUid());
        }
        else {
            inserter.insert(node, dbManager.getConnection());
            log.info("Insert finished successfully for strategy \"" + strategyType + "\", node id = " + node.getId());
        }
    }

    public void processDataFromFile(INSERT_STRATEGY strategyType) throws IOException, XMLStreamException, SQLException, JAXBException {
        try (InputStream in = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(xmlFilePath), SIZE_OF_BUFFER))) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(in, ENCODING);
            Unmarshaller unmarshaller = JAXBContext.newInstance(Node.class).createUnmarshaller();
            Inserter inserter = InsertersFactory.getInserterByStartegyType(strategyType);
            while (readElementsCounter < limit && reader.hasNext())  {
                XMLEvent nextEvent = reader.peek();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String startElementName = startElement.getName().getLocalPart();
                    if (COUNTING_ELEMENT_NAME.equals(startElementName)) {
                        Node node = (Node) unmarshaller.unmarshal(reader);
                        insertData(inserter, node, strategyType);
                        readElementsCounter++;
                    }
                }
                reader.nextEvent();
            }
            inserter.commitChanges();

            BatchInserter batchInserter = (BatchInserter) InsertersFactory.getInserterByStartegyType(INSERT_STRATEGY.BATCH);
            if(batchInserter != null) {
                batchInserter.executeBatch();
                batchInserter.commitChanges();
            }
        }
    }

    public void collectStatistics() {
        for(INSERT_STRATEGY strategyType: INSERT_STRATEGY.values()) {
            Inserter inserter = InsertersFactory.getInserterByStartegyType(strategyType);
            if(inserter == null) {
                log.error("Inserter for strategy \"" + strategyType + "\" is not implemented, so speed is unknown. It will be skipped.");
            }
            else {
                speeds.put(strategyType, inserter.getSpeed());
            }
        }
    }

    public void finish() throws SQLException, IOException {
        readElementsCounter = 0;
        if(dbManager != null) {
            dbManager.clearDB();
        }
    }
}
