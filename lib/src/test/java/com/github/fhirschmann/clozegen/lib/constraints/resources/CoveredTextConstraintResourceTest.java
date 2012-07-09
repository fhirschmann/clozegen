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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Iterables;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.BeforeClass;
import org.uimafit.util.JCasUtil;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.Resource;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;


/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CoveredTextConstraintResourceTest {
    @Test
    public void testGetConstraint() throws UIMAException, IOException {
        AnalysisEngineDescription desc = createPrimitiveDescription(GapAnnotator.class,
                GapAnnotator.ADAPTER_KEY,
                 createExternalResourceDescription(DummyAdapter.class),
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    CoveredTextConstraintResource.class,
                    CoveredTextConstraintResource.PARAM_TYPE, Token.class.getName(),
                    CoveredTextConstraintResource.PARAM_STRING, "of"));

        JCas jcas = UIMAUtils.createJCas("He can't think of anything.", "en");
        Pipeline pipeline = new Pipeline();
        pipeline.add(BreakIteratorSegmenter.class);
        pipeline.add(desc);
        pipeline.run(jcas);

        GapAnnotation annotation = Iterables.getOnlyElement(
                JCasUtil.select(jcas, GapAnnotation.class));
        assertThat(annotation.getCoveredText(), is("of"));
    }
}
