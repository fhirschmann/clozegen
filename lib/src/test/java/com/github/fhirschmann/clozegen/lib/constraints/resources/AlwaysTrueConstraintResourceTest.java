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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AlwaysTrueConstraintResourceTest {
    @Test
    public void testGetConstraint() throws UIMAException {
        AlwaysTrueConstraintResource cs = new AlwaysTrueConstraintResource();
        JCas jcas = UIMAUtils.createJCas("foo", "en");
        GapAnnotation an = UIMAUtils.createGapAnnotation(jcas, Gap.with("bar"));
        an.setBegin(0);
        an.setEnd(3);
        an.addToIndexes();
        assertTrue(cs.getConstraint(jcas).match(an));
    }

    @Test
    public void testToString() {
        AlwaysTrueConstraintResource cs = new AlwaysTrueConstraintResource();
        assertThat(cs.toString(), is("AlwaysTrueConstraintResource{}"));
    }
}
