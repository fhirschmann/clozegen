/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.fhirschmann.clozegen.lib.register;

import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.util.logging.Logger;

import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.Resource_ImplBase;

import com.github.fhirschmann.clozegen.lib.adapters.CollocationAdapter;
import com.github.fhirschmann.clozegen.lib.adapters.FrequencyAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.components.JCasFileWriter;
import com.github.fhirschmann.clozegen.lib.components.api.JCasFormatter;
import com.github.fhirschmann.clozegen.lib.constraints.resources.PrepositionConstraintResource;
import com.github.fhirschmann.clozegen.lib.formatters.IMFFormatter;
import com.github.fhirschmann.clozegen.lib.formatters.LaTeXFormatter;
import com.github.fhirschmann.clozegen.lib.formatters.PlainTextFormatter;
import com.github.fhirschmann.clozegen.lib.reader.IntermediateFormatReader;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class RegisterFactory {
    public static final Logger LOGGER = Logger.getLogger(RegisterFactory.class.getName());

    /**
     * Utility class cannot be called.
     */
    private RegisterFactory() {
    }

    /**
     * Creates a new {@link DescriptionRegister} prefilled with known descriptions
     * for gap generation.
     *
     * @return a new {@link DescriptionRegister}
     * @throws ResourceInitializationException on errors
     */
    public static AnnotatorRegister createDefaultAnnotatorRegister()
            throws ResourceInitializationException {
        AnnotatorRegister register = new AnnotatorRegister();

        AnnotatorRegisterEntry entry =
                    new AnnotatorRegisterEntry("preps",
                GapAnnotator.class,
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                CollocationAdapter.class,
                CollocationAdapter.PARAM_PATH,
                "frequencies/en/prepositions/trigrams.txt"),
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                PrepositionConstraintResource.class));

        entry.setName("Preposition Gap Generator (Collocations)");
        entry.setSupportedLanguage("en");
        register.add(entry);

        AnnotatorRegisterEntry entry2 =
                new AnnotatorRegisterEntry("preps2",
                GapAnnotator.class,
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(
                FrequencyAdapter.class,
                FrequencyAdapter.PARAM_PATH,
                "frequencies/en/prepositions/unigrams.txt"),
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                PrepositionConstraintResource.class));

        entry2.setName("Preposition Gap Generator (Baseline)");
        entry2.setSupportedLanguages("en");
        register.add(entry2);

        return register;
    }

    /**
     * Convenience method for creating register entries based on
     * {@link JCasFileWriter}.
     *
     * @param formatter the {@link JCasFormatter}
     * @param identifier the identifier for this entry
     * @param name the name of this entry
     * @return a new register entry
     */
    public static WriterRegisterEntry createJCasFileWriterEntry(
            final Class<? extends Resource_ImplBase> formatter,
            final String identifier,
            final String name) {
        WriterRegisterEntry entry = new WriterRegisterEntry(
                identifier,
                JCasFileWriter.class,
                JCasFileWriter.FORMATTER_KEY,
                createExternalResourceDescription(formatter));
        entry.setName(name);
        return entry;
    }

    /**
     * Creates a new {@link WriterRegister} prefilled with known descriptions
     * for exporting cloze tests.
     *
     * @return a new {@link WriterRegister}
     */
    public static WriterRegister createDefaultWriterRegister() {
        WriterRegister register = new WriterRegister();
        register.add(createJCasFileWriterEntry(IMFFormatter.class, "clz",
                "Intermediate Format Writer"));

        register.add(createJCasFileWriterEntry(PlainTextFormatter.class, "txt",
                "Plain Text Writer"));

        register.add(createJCasFileWriterEntry(LaTeXFormatter.class, "tex",
                "LaTeX Writer"));

        return register;
    }

    /**
     * Creates a new {@link ReaderRegister} prefilled with known descriptions
     * for creating input readers.
     *
     * @return a new reader register
     */
    public static ReaderRegister createDefaultReaderRegister() {
        ReaderRegister register = new ReaderRegister();

        ReaderRegisterEntry txt = new ReaderRegisterEntry("txt", TextReader.class);
        txt.setName("Plain-Text Reader");
        register.add(txt);

        ReaderRegisterEntry clz = new ReaderRegisterEntry(
                "clz", IntermediateFormatReader.class);
        clz.setName("IntermediateFormat Reader");
        register.add(clz);

        return register;
    }
}
