package ru.nsu.fit.sokolova.dis;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import ru.nsu.fit.sokolova.dis.inserters.INSERT_STRATEGY;
import ru.nsu.fit.sokolova.dis.utils.DataBaseManager;
import ru.nsu.fit.sokolova.dis.utils.ResultPrinter;
import ru.nsu.fit.sokolova.dis.utils.XMLProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
public class Main {

    public static void main(String[] args) {

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
        String limitStr = cmdLine.getOptionValue("l", "all");
        String filePath = cmdLine.getOptionValue("f");

        if (filePath != null) {
            XMLProcessor xmlProcessor = null;
            try {
                xmlProcessor = new XMLProcessor(limitStr, filePath);
            } catch (SQLException | IOException e) {
                log.error("Error while initializing XMLProcessor: " + "\n" + e.getMessage());
            }
            assert xmlProcessor != null;
            System.out.println("Limit of elements to process: " + xmlProcessor.getLimit());
            System.out.println("Input xml file: " + xmlProcessor.getXmlFilePath());
            System.out.println("Started counting...Please wait");

            try {
                int n = 0;
                INSERT_STRATEGY[] strategyTypes = INSERT_STRATEGY.values();
                while (n < strategyTypes.length - 1) {
                    xmlProcessor.processDataFromFile(strategyTypes[n]);
                    xmlProcessor.finish();
                    n++;
                }
                xmlProcessor.processDataFromFile(strategyTypes[n]);
                xmlProcessor.collectStatistics();
                xmlProcessor.getDbManager().getConnection().close();
            } catch (IOException | XMLStreamException | JAXBException | SQLException ex) {
                ex.printStackTrace();
                log.error("Error while caclulating statistic: " + "\n" + ex.getMessage());
            }

            ResultPrinter.printSpeed(xmlProcessor.getSpeeds());
        }
    }
}
