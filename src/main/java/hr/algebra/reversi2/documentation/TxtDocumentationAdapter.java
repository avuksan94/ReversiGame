package hr.algebra.reversi2.documentation;

//https://refactoring.guru/design-patterns/adapter
public class TxtDocumentationAdapter implements Documentable{
    private final HtmlDocumentationGenerator _htmlGenerator;

    public TxtDocumentationAdapter(HtmlDocumentationGenerator htmlGenerator) {
        _htmlGenerator = htmlGenerator;
    }

    @Override
    public String generateContent() {
        String htmlContent = _htmlGenerator.generateHtmlContent();

        return htmlContent.replaceAll("<.*?>", "\n");
    }
}
