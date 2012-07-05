/*
 * The MIT License
 *
 * Copyright 2012 Fabian Hirschmann <fabian@hirschm.net>.
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
package com.github.fhirschmann.clozegen.lib.reader;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.imf.IntermediateFormat;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.ibm.icu.text.CharsetDetector;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.uimafit.descriptor.ConfigurationParameter;
import static org.apache.commons.io.IOUtils.closeQuietly;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.uimafit.util.JCasUtil;

/**
 * Reads the Intermediate Format.
 *
 * <p> This is basically a copy of {@link TextReader} with one minor adjustment.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class IntermediateFormatReader extends TextReader {

    /**
     * Automatically detect encoding.
     *
     * @see CharsetDetector
     */
    public static final String ENCODING_AUTO = "auto";
    /**
     * Name of configuration parameter that contains the character encoding used by the
     * input files.
     */
    public static final String PARAM_ENCODING = ComponentParameters.PARAM_SOURCE_ENCODING;
    @ConfigurationParameter(name = PARAM_ENCODING, mandatory = true,
    defaultValue = "UTF-8")
    private String encoding;

    @Override
    public void getNext(final CAS aCAS) throws IOException, CollectionException {
        Resource res = nextFile();
        initCas(aCAS, res);

        InputStream is = null;
        JCas jcas;
        try {
            is = new BufferedInputStream(res.getInputStream());
            if (ENCODING_AUTO.equals(encoding)) {
                CharsetDetector detector = new CharsetDetector();
                jcas = IntermediateFormat.parse(IOUtils.toString(
                        detector.getReader(is, null)));
            } else {
                jcas = IntermediateFormat.parse(IOUtils.toString(is, encoding));
            }
            aCAS.setDocumentText(jcas.getDocumentText());
            for (GapAnnotation annotation : JCasUtil.select(jcas, GapAnnotation.class)) {
                // This is not very elegeant and copies the GapAnnotation
                // from one CAS to another
                Gap gap = UIMAUtils.createGap(annotation);
                GapAnnotation annotation2 = UIMAUtils.
                        createGapAnnotation(aCAS.getJCas(), gap);
                UIMAUtils.copyBounds(annotation, annotation2);
                aCAS.addFsToIndexes(annotation2);
            }
        } catch (UIMAException ex) {
            getLogger().error(ex);
        } finally {
            closeQuietly(is);
        }
    }
}
