package ru.nsu.fit.sokolova.dis.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.springframework.stereotype.Service;
import ru.nsu.fit.sokolova.dis.converters.NodeConverter;
import ru.nsu.fit.sokolova.dis.converters.TagConverter;
import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import ru.nsu.fit.sokolova.dis.dto.TagDTO;
import ru.nsu.fit.sokolova.dis.model.Node;
import ru.nsu.fit.sokolova.dis.model.Tag;
import ru.nsu.fit.sokolova.dis.service.NodeService;
import ru.nsu.fit.sokolova.dis.service.TagService;

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

@Log4j2
@Getter
@Service
@RequiredArgsConstructor
public class XMLProcessor {

    private static final String MAX_DEFINED_LIMIT = "all";
    private static final int SIZE_OF_BUFFER = 4096 * 32;
    private static final String ENCODING = "UTF-8";
    private static final String NODE_ELEMENT = "node";
    private static final String COUNTING_ELEMENT_NAME = NODE_ELEMENT;
    private final static String INFO_DELIMITER = "/";
    private static final String KEY_TO_FIND = "name";

    private int limit;
    private String xmlFilePath;

    private int readElementsCounter;

    private final NodeService nodeService;
    private final TagService tagService;

    private void saveDataToDb(Node node) {
        NodeDTO nodeDTO = NodeConverter.modelToDto(node);
        nodeService.create(nodeDTO);
        if(node.getTag().size() > 0) {
            System.out.println("TAGS FOUND: " + node.getTag().size());
        }
        for (Tag tag: node.getTag()) {
            TagDTO tagDTO = TagConverter.modelToDto(node, tag);
            tagService.create(tagDTO);
        }
        log.info("Insert finished successfully for node with id = " + node.getId());
    }

    public void processDataFromFile(String limitStr, String xmlFilePath) throws IOException, XMLStreamException, JAXBException {
        this.limit = limitStr.equals(MAX_DEFINED_LIMIT) ? Integer.MAX_VALUE : Integer.parseInt(limitStr);
        this.xmlFilePath = xmlFilePath;
        readElementsCounter = 0;
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
                        saveDataToDb(node);
                        readElementsCounter++;
                    }
                }
                reader.nextEvent();
            }
        }
    }
}
