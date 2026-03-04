import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates JSON payload files for required keys and logs any failures.
 *
 * Required keys: alertType, originatingsource, eventId, messageType
 * - Performs null or empty string checks.
 * - If alertType is "FUND_TRANSFER_SUCCESSFUL" and originatingsource is
 * null/empty,
 * it will be set to "TPH".
 * - All validation results are written to the dtl-event-log folder.
 */
@Component
public class JsonPayloadValidator implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(JsonPayloadValidator.class);
    private static final String PAYLOAD_DIR = "src/main/resources/payloads";
    private static final String LOG_DIR = "../dtl-event-log"; // relative to project root
    private static final String LOG_FILE = "validation.log";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {
        Path logDirPath = Paths.get(LOG_DIR);
        if (!Files.exists(logDirPath)) {
            Files.createDirectories(logDirPath);
        }
        Path logFilePath = logDirPath.resolve(LOG_FILE);
        try (FileWriter logWriter = new FileWriter(logFilePath.toFile(), true)) {
            Files.list(Paths.get(PAYLOAD_DIR))
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> validateFile(p.toFile(), logWriter));
        }
    }

    private void validateFile(File file, FileWriter logWriter) {
        try {
            JsonNode root = objectMapper.readTree(file);
            List<String> missing = new ArrayList<>();
            // alertType
            JsonNode alertTypeNode = root.path("customFieldDetails").path("alertType");
            String alertType = alertTypeNode.isMissingNode() ? null : alertTypeNode.asText();
            if (isNullOrEmpty(alertType)) {
                missing.add("alertType");
            }
            // messageType
            JsonNode messageTypeNode = root.path("customFieldDetails").path("MessageType");
            String messageType = messageTypeNode.isMissingNode() ? null : messageTypeNode.asText();
            if (isNullOrEmpty(messageType)) {
                missing.add("messageType");
            }
            // eventId
            JsonNode eventIdNode = root.path("eventId");
            String eventId = eventIdNode.isMissingNode() ? null : eventIdNode.asText();
            if (isNullOrEmpty(eventId)) {
                missing.add("eventId");
            }
            // originatingsource
            JsonNode originNode = root.path("customFieldDetails").path("originatingsource");
            String origin = originNode.isMissingNode() ? null : originNode.asText();
            if (isNullOrEmpty(origin)) {
                if ("FUND_TRANSFER_SUCCESSFUL".equalsIgnoreCase(alertType)) {
                    // set to TPH
                    if (root instanceof ObjectNode) {
                        ((ObjectNode) root.path("customFieldDetails")).put("originatingsource", "TPH");
                        // write back the corrected file
                        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
                        logEntry(logWriter, file.getName(),
                                "originatingsource set to TPH (alertType=FUND_TRANSFER_SUCCESSFUL)");
                    }
                } else {
                    missing.add("originatingsource");
                }
            }

            if (!missing.isEmpty()) {
                String reason = "Missing/empty keys: " + String.join(", ", missing);
                logEntry(logWriter, file.getName(), reason);
            }
        } catch (IOException e) {
            logger.error("Failed to process {}: {}", file.getName(), e.getMessage());
            try {
                logEntry(logWriter, file.getName(), "Exception: " + e.getMessage());
            } catch (IOException ignored) {
            }
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void logEntry(FileWriter writer, String fileName, String message) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        writer.write(String.format("%s - %s - %s%n", timestamp, fileName, message));
        writer.flush();
    }
}
