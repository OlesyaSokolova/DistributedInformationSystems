package ru.nsu.fit.sokolova.dis;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.nsu.fit.sokolova.dis.utils.XMLProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
public class Main {
    private final XMLProcessor xmlProcessor;
    private static String filePath = "";
    private static String limitOfNodes = "all";

    public static void main(String[] args) throws SQLException, IOException {
        Options options = new Options();
        options.addOption("f", "file", true, "XML file with data");
        options.addOption("l", "limit", true, "Limit number of elements to count in XML file");
        DefaultParser parser = new DefaultParser();
        CommandLine cmdLine;
        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("Wrong arguments; execution was stopped.");
            System.out.println("Wrong arguments. Usage: -f\n" +
                    "path/to/file/to/be/parsed.bz2 (xml-archive)" +
                    "-l\n" +
                    "limit_of_xml_elements_to_be_processed");
            return;
        }
        assert cmdLine != null;
        limitOfNodes = cmdLine.getOptionValue("l", "all");
        filePath = cmdLine.getOptionValue("f");

        SpringApplication.run(Main.class);
    }

   /* @EventListener(ApplicationReadyEvent.class)
    public void loadDataToDBFromFile() {
            assert xmlProcessor != null;
            System.out.println("Limit of elements to process: " + limitOfNodes);
            System.out.println("Input xml file: " + filePath);
            System.out.println("Started initializing database...Please wait");
        try {
            xmlProcessor.processDataFromFile(limitOfNodes, filePath);
        } catch (IOException | XMLStreamException | JAXBException e) {
            log.error("Error while initializing db: " + "\n" + e.getMessage());
        }
    }*/
}
