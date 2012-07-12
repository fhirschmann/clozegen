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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CoveredTextConstraintResourceTest {

    @Test
    public void testGetConstraint() throws UIMAException, IOException {
        List<GapAnnotation> annotations = ConstraintResourceUtils.
                getGapAnnotationsForConstraint(
                "He can't think of anything.", "en",
                CoveredTextConstraintResource.class,
                CoveredTextConstraintResource.PARAM_TYPE, Token.class.getName(),
                CoveredTextConstraintResource.PARAM_MATCH, "of,anything");

        assertThat(annotations.size(), is(2));
        assertThat(annotations.get(0).getCoveredText(), is("of"));
        assertThat(annotations.get(1).getCoveredText(), is("anything"));
    }

    @Test
    public void testToString() {
        CoveredTextConstraintResource cs = new CoveredTextConstraintResource();
        assertThat(cs.toString(), is("CoveredTextConstraintResource{match=null}"));
    }
}
