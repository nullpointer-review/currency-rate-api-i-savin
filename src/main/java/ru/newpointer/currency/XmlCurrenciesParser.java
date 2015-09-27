package ru.newpointer.currency;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;

/**
 * Created by isavin on 27.09.15.
 */
public class XmlCurrenciesParser {

    private String xmlContent;

    public XmlCurrenciesParser(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public Currency getCurrencyByCode(String charCode) throws XMLStreamException {
        Currency currency = new Currency();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StringReader(xmlContent));
        boolean currencyFound = false;
        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
                if (xmlEvent.asStartElement().getName().getLocalPart().equals("ValCurs")) {
                    QName qName = new QName("Date");
                    currency.setDate(xmlEvent.asStartElement().getAttributeByName(qName).getValue().replaceAll("\\.","-"));
                } else if (xmlEvent.asStartElement().getName().getLocalPart().equals("CharCode")) {
                    String xmlCharCode = xmlEventReader.nextEvent().asCharacters().getData();
                    if (xmlCharCode.equals(charCode)) {
                        currencyFound = true;
                        currency.setCode(xmlCharCode);
                    }
                } else if (xmlEvent.asStartElement().getName().getLocalPart().equals("Value") && currencyFound) {
                    currency.setRate(xmlEventReader.nextEvent().asCharacters().getData());
                    break;
                }
            }
        }
        System.out.println("Currency formed: " + currency);
        return currency;
    }
}
