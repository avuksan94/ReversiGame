package hr.algebra.reversi2.documentation;

public class TxtDocumentationAdapter implements Documentable{
    private HtmlDocumentationGenerator htmlGenerator;

    public TxtDocumentationAdapter(HtmlDocumentationGenerator htmlGenerator) {
        this.htmlGenerator = htmlGenerator;
    }

    @Override
    public String generateContent() {
        String htmlContent = htmlGenerator.generateHtmlContent();

        return htmlContent.replaceAll("<.*?>", "\n");
    }
}
