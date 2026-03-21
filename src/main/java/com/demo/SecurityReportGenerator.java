package com.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Generates a markdown vulnerability summary from an OWASP Dependency-Check XML report.
 */
public class SecurityReportGenerator {

    private static final String[] ORDERED_SEVERITIES = {"CRITICAL", "HIGH", "MEDIUM", "LOW", "UNKNOWN"};

    public static void main(String[] args) throws Exception {
        String inputPath = args.length > 0 ? args[0] : "dependency-check-report.xml";
        String outputPath = args.length > 1 ? args[1] : "target/security-summary.md";

        Path source = Paths.get(inputPath);
        Path output = Paths.get(outputPath);

        Map<String, Integer> counts = parseSeverityCounts(source);
        writeMarkdownReport(output, counts, source);

        System.out.println("Generated security summary: " + output.toAbsolutePath());
    }

    private static Map<String, Integer> parseSeverityCounts(Path inputPath) throws Exception {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String severity : ORDERED_SEVERITIES) {
            counts.put(severity, 0);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputPath.toFile());

        NodeList severityNodes = document.getElementsByTagNameNS("*", "severity");
        for (int i = 0; i < severityNodes.getLength(); i++) {
            Node node = severityNodes.item(i);
            if (node instanceof Element) {
                String severity = node.getTextContent().trim().toUpperCase(Locale.ROOT);
                if (!counts.containsKey(severity)) {
                    severity = "UNKNOWN";
                }
                counts.put(severity, counts.get(severity) + 1);
            }
        }

        return counts;
    }

    private static void writeMarkdownReport(Path output, Map<String, Integer> counts, Path sourceFile) throws IOException {
        Files.createDirectories(output.getParent());
        int total = counts.values().stream().mapToInt(Integer::intValue).sum();

        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            writer.write("# Security Scan Summary\n\n");
            writer.write("Generated: " + Instant.now() + "\\\n\n");
            writer.write("Source report: `" + sourceFile.toString() + "`\\\n\n");
            writer.write("## Vulnerabilities by Severity\n\n");
            writer.write("| Severity | Count |\n");
            writer.write("| --- | ---: |\n");
            for (String severity : ORDERED_SEVERITIES) {
                writer.write("| " + severity + " | " + counts.get(severity) + " |\n");
            }
            writer.write("| TOTAL | " + total + " |\n\n");

            writer.write("## Chart (Mermaid)\n\n");
            writer.write("```mermaid\n");
            writer.write("pie title Vulnerability Severity Breakdown\n");
            for (String severity : ORDERED_SEVERITIES) {
                writer.write("\"" + severity + "\" : " + counts.get(severity) + "\n");
            }
            writer.write("```\n");
        }
    }
}
