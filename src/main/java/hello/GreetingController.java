package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.axis.message.MessageElement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

@RestController
public class GreetingController {
    //TODO написать тесты
    //TODO сделать сборку war
    //TODO обработка ошибок
    //TODO форматировать json...
    //TODO отладить
    //TODO почистить
    //TODO сдать!

    public static final String XML_DAILY_COURSES_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String DATE_APPENDER = "?date_req=02/03/2002";

//    @RequestMapping("/currency/api/{code}")
    public String currency(@PathVariable String code) {
        StringBuffer sb = new StringBuffer();
        URL url;
        try {
            // get URL content
            url = new URL(XML_DAILY_COURSES_URL);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), Charset.forName("CP1251")))) {
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                    sb.append(inputLine);
                }
            }

            return parseXml(sb.toString(), code);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String parseXml(String xmlContent, String currencyCode) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xmlContent));
        boolean currencyFound = false;
        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
                if (xmlEvent.asStartElement().getName().getLocalPart().equals("CharCode")) {
                    if (xmlEventReader.nextEvent().asCharacters().getData().equals(currencyCode)) {
                        currencyFound = true;
                    }
                } else if (xmlEvent.asStartElement().getName().getLocalPart().equals("Value") && currencyFound) {
                    return xmlEventReader.nextEvent().asCharacters().getData();
                }
            }
        }
        return null;
    }
}
