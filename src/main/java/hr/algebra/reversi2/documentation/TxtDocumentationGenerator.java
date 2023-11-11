package hr.algebra.reversi2.documentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TxtDocumentationGenerator {
    private final TxtDocumentationAdapter _adapter;

    public TxtDocumentationGenerator() {
        _adapter = new TxtDocumentationAdapter(new HtmlDocumentationGenerator());
    }

    public void generateTxtDocumentation(Path path) {
        String txtContent = _adapter.generateContent();

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, txtContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error writing TXT documentation file", e);
        }
    }
}
