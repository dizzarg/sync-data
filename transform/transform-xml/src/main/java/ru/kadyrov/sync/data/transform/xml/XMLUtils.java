package ru.kadyrov.sync.data.transform.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ru.kadyrov.sync.data.domain.DuplicateException;
import ru.kadyrov.sync.data.domain.NaturalKey;
import ru.kadyrov.sync.data.domain.Pair;
import ru.kadyrov.sync.data.transform.exception.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class XMLUtils {

    public static Element rootFromDocument(Document document, String nodeName) throws ParseException {
        Element root = document.getDocumentElement();
        if(!nodeName.equalsIgnoreCase(root.getTagName())) {
            syntaxError("Name of root element should be '" + nodeName + "'");
        }
        return root;
    }

    public static Document documentFromFile(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    public static String requiredAttribute(String attributeName, Element node) {
        String attribute =node.getAttribute(attributeName);
        if(attribute == null || attribute.trim().isEmpty()){
            throw new IllegalStateException(String.format("Required attribute by name '%s' is null or empty", attributeName));
        }
        return attribute;
    }

    public static Document newDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    public static ByteBuffer documentToByte(Document doc) throws TransformerException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DOMSource source = new DOMSource(doc.getDocumentElement());
        StreamResult result = new StreamResult(baos);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.transform(source, result);
        return ByteBuffer.wrap(baos.toByteArray());
    }

    public static void syntaxError(String s) throws ParseException {
        throw new ParseException(s);
    }

    public static void duplicateError(NaturalKey department) throws DuplicateException {
        throw new DuplicateException(department, "file");
    }

}
