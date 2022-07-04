package elements_processors;

import javax.xml.stream.events.StartElement;
import java.util.Map;

public interface ElementProcessor {
    void processElement(Map<String, Map<String, Integer>> statToUpdate, StartElement startElement, String parentElementName);
    boolean elementIsProcessed();
    int getElementCounter();
}
