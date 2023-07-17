package ru.testit.reporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.testit.reporter.models.Test;


public class XmlFileWriter {
    private final Path outputDirectory;

    public XmlFileWriter(final Path outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void write(final List<Test> testResults) throws TransformerFactoryConfigurationError {
        String filename = "test-results.xml";
        try {
            createDirectories(outputDirectory);
            Document document = convertTestResultsToDocument(testResults);
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(Paths.get(String.valueOf(outputDirectory), filename).toFile());
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectories(final Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Document convertTestResultsToDocument(final List<Test> testResults) throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element testsuite = document.createElement("testsuite");

        document.appendChild(testsuite);

        for (Test testResult : testResults) {
            Element testcase = document.createElement("testcase");

            testcase.setAttribute("name", testResult.getName());
            testcase.setAttribute("classname", testResult.getClassName());

            if (testResult.getThrowable() != null) {
                Element failure = document.createElement("failure");

                failure.setAttribute("message", testResult.getThrowable().toString());

                testcase.appendChild(failure);
            }

            testsuite.appendChild(testcase);
        }

        return document;
    }
}
