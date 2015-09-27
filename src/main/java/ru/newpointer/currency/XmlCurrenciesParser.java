package ru.newpointer.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;

/**
 * Парсинг XML-содержимое с помощью StAX парсера
 */
public class XmlCurrenciesParser {

    private final static Logger logger = LoggerFactory.getLogger(XmlCurrenciesParser.class);
    private String xmlContent;

    public XmlCurrenciesParser(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public Currency getCurrencyByCode(String charCode) throws XMLStreamException {
        logger.info("Forming currency for char code: [{}]", charCode);
        //в случае неблагополучного исхода возвращать будем не null, а пустую Currency
        Currency currency = new Currency();
        boolean currencyFound = false;//признак остановки во время парсинга

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xmlContent));

        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
                //проставим дату из полученного xml
                if (xmlEvent.asStartElement().getName().getLocalPart().equals("ValCurs")) {
                    QName qName = new QName("Date");
                    String date = xmlEvent.asStartElement().getAttributeByName(qName).getValue();
                    currency.setDate(date.replaceAll("[/\\.]","-"));
                    //нашли тег CharCode
                } else if (xmlEvent.asStartElement().getName().getLocalPart().equals("CharCode")) {
                    String xmlCharCode = xmlEventReader.nextEvent().asCharacters().getData();
                    //его содержимое равно переданному коду валюты
                    if (xmlCharCode.equals(charCode)) {
                        currencyFound = true;//нужная валюта найдена
                        currency.setCode(xmlCharCode);//проставляем код валюты
                    }
                    //нашли тег Value и валюта уже найдена
                } else if (xmlEvent.asStartElement().getName().getLocalPart().equals("Value") && currencyFound) {
                    //проставляем значение курса и выходим из цикла парсинга
                    currency.setRate(xmlEventReader.nextEvent().asCharacters().getData());
                    break;
                }
            }
        }
        logger.info("Currency formed: [{}]", currency);
        return currency;
    }
}
