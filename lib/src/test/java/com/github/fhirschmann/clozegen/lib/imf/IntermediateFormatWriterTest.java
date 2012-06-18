/*
 * Copyright (C) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.fhirschmann.clozegen.lib.imf;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.pipeline.Pipeline;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import org.uimafit.factory.JCasFactory;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class IntermediateFormatWriterTest {
    @Test
    public void testIMFWriter() throws UIMAException, IOException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText("Foo bar foo.");
        GapAnnotation annotation = UIMAUtils.createGapAnnotation(jcas, Gap.with("mooh"));
        annotation.setBegin(0);
        annotation.setEnd(3);
        annotation.addToIndexes();
        Pipeline pipeline = new Pipeline();
        File file = new File("/home/fabian/test.txt");
        pipeline.addStep(createPrimitiveDescription(
                IntermediateFormatWriter.class,
                IntermediateFormatWriter.PARAM_OUTPUT_FILE, file.getAbsolutePath()));
        pipeline.run(jcas);
        List<String> lines = Files.readLines(file, Charsets.UTF_8);
        assertThat(lines, hasItem("{mooh}{mooh} bar foo."));
    }
}
