package ru.newpointer.currency;

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

    public static final String XML_DAILY_COURSES_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String DATE_APPENDER = "?date_req=%s";

    @RequestMapping("/currency/api/{code}")
    public Currency currency(@PathVariable String code) {
        try {
            HttpReader reader = new HttpReader(XML_DAILY_COURSES_URL);
            String xmlContent = reader.getPageContent();
            XmlCurrenciesParser parser = new XmlCurrenciesParser(xmlContent);
            return parser.getCurrencyByCode(code);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new Currency();
        } catch (IOException e) {
            e.printStackTrace();
            return new Currency();
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return new Currency();
        }
    }

    @RequestMapping("/currency/api/{code}/{date}")
    public Currency currency(@PathVariable String code,
                             @PathVariable String date) {
        String[] dateParts = date.split("-");
        StringBuffer dateFormatted = new StringBuffer(dateParts[2]);
        dateFormatted.append("/")
                .append(dateParts[1])
                .append("/")
                .append(dateParts[0]);
        try {
            HttpReader reader = new HttpReader(XML_DAILY_COURSES_URL + String.format(DATE_APPENDER, dateFormatted.toString()));
            String xmlContent = reader.getPageContent();
            XmlCurrenciesParser parser = new XmlCurrenciesParser(xmlContent);
            return parser.getCurrencyByCode(code);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new Currency();
        } catch (IOException e) {
            e.printStackTrace();
            return new Currency();
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return new Currency();
        }
    }
}
