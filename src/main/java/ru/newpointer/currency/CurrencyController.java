package ru.newpointer.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by isavin on 27.09.15.
 */
@RestController
public class CurrencyController {

    private final static Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    public static final String XML_DAILY_COURSES_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String DATE_APPENDER = "?date_req=%s";

    @RequestMapping("/currency/api/{code}")
    public Response currency(@PathVariable String code) {
        logger.info("Getting currency rate for: [{}]", code);
        return getResponse(XML_DAILY_COURSES_URL, code);
    }

    @RequestMapping("/currency/api/{code}/{date}")
    public Response currency(@PathVariable String code,
                             @PathVariable String date) {
        logger.info("Getting currency rate for: [{}, {}]", code, date);
        String[] dateParts = date.split("-");
        StringBuffer dateFormatted = new StringBuffer(dateParts[2]);
        dateFormatted.append("/")
                .append(dateParts[1])
                .append("/")
                .append(dateParts[0]);
        return getResponse(XML_DAILY_COURSES_URL + String.format(DATE_APPENDER, dateFormatted.toString()), code);
    }

    private Response getResponse(String url, String code) {
        try {
            return getCurrencyFromCbr(url, code);
        } catch (IOException e) {
            logger.error("Error reading page content: [{}]", e);
            return new Fault("Error reading page content", e.toString());
        } catch (XMLStreamException e) {
            logger.error("Error parsing XML content: [{}]", e);
            return new Fault("Error parsing XML content", e.toString());
        }
    }

    private Currency getCurrencyFromCbr(String url, String code) throws IOException, XMLStreamException {
        HttpReader reader = new HttpReader(url);
        String xmlContent = reader.getPageContent();
        XmlCurrenciesParser parser = new XmlCurrenciesParser(xmlContent);
        return parser.getCurrencyByCode(code);
    }
}
