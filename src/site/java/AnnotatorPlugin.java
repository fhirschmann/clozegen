public class AnnotatorPlugin implements Plugin {
    @Override
    public void init() {
        AnnotatorRegisterEntry entry = new AnnotatorRegisterEntry("articles",
                GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraintResource.class,
                    TypeConstraintResource.PARAM_TYPE,
                    ART.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                creatExternalResourceDescription(StupidArticleAdapter.class));
        entry.setName("Article Gap Generator");
        entry.setSupportedLanguages("en");
        Registers.annotator().add(entry);
    }
}
