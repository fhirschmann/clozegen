public class WriterPlugin implements Plugin {
    @Override
    public void init() {
        WriterRegisterEntry entry =
            new WriterRegisterEntry("txt",
                JCasFileWriter.class,
                JCasFileWriter.FORMATTER_KEY,
                createExternalResourceDescription(PlainTextFormatter.class));
        entry.setName("Plain Text Writer");
        Registers.writer().add(entry);
    }
}
