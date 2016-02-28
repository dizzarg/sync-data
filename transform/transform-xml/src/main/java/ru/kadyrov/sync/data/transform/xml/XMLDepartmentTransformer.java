package ru.kadyrov.sync.data.transform.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.kadyrov.sync.data.domain.DuplicateException;
import ru.kadyrov.sync.data.domain.NaturalKey;
import ru.kadyrov.sync.data.domain.Pair;
import ru.kadyrov.sync.data.transform.DepartmentTransformer;
import ru.kadyrov.sync.data.transform.exception.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class XMLDepartmentTransformer implements DepartmentTransformer {

    public static final String DEPARTMENTS = "Departments";
    public static final String DEPARTMENT = "Department";
    public static final String DEP_CODE = "DepCode";
    public static final String DEP_JOB = "DepJob";

    @Override
    public Map<NaturalKey, String> fromFile(File file) throws ParseException, DuplicateException {
        try {
            Document document = XMLUtils.documentFromFile(file);
            Element root = XMLUtils.rootFromDocument(document, DEPARTMENTS);
            return readDepartment(root.getChildNodes());
        } catch (ParserConfigurationException e) {
            System.out.println("Config error");
            throw new ParseException(e);
        } catch (SAXException | IOException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public ByteBuffer toFile(Map<NaturalKey, String> department) throws IOException {
        try {
            Document document = XMLUtils.newDocument();
            Element root = document.createElement(DEPARTMENTS);
            for (NaturalKey naturalKey : department.keySet()) {
                Element element = document.createElement(DEPARTMENT);
                element.setAttribute(DEP_CODE, naturalKey.getCode());
                element.setAttribute(DEP_JOB, naturalKey.getJob());
                element.setTextContent(department.get(naturalKey));
                root.appendChild(element);
            }
            document.appendChild(root);
            return XMLUtils.documentToByte(document);
        } catch (ParserConfigurationException | TransformerException e) {
           throw new IOException(e);
        }
    }

    private HashMap<NaturalKey, String> readDepartment(NodeList childNodes) throws ParseException, DuplicateException {
        HashMap<NaturalKey, String> department = new HashMap<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if(item.getNodeType() == Node.ELEMENT_NODE){
                Pair<NaturalKey, String> departmentPair = readDepartmentItem((Element) item);
                if(department.containsKey(departmentPair.getKey())){
                    XMLUtils.duplicateError(departmentPair.getKey());
                }
                department.put(departmentPair.getKey(), departmentPair.getValue());
            }
        }
        return department;
    }

    public static Pair<NaturalKey, String> readDepartmentItem(Element element) throws DuplicateException, ParseException {
        if(!DEPARTMENT.equalsIgnoreCase(element.getNodeName())) {
            XMLUtils.syntaxError("Name of root element should be '"+ DEPARTMENT +"'");
        }
        String code = XMLUtils.requiredAttribute(DEP_CODE, element);
        String job = XMLUtils.requiredAttribute(DEP_JOB, element);
        NaturalKey naturalKey = new NaturalKey(code, job);
        return Pair.of(naturalKey, element.getTextContent());
    }



}
