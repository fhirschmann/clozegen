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

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.collect.Iterables;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.NN;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class TypeConstraintResourceTest {
    @Test
    public void testGetConstraint() throws ResourceInitializationException,
            UIMAException, IOException {
        List<GapAnnotation> annotations = ConstraintResourceUtils.
                getGapAnnotationsForConstraint(
                "He can't think of anything", "en",
                TypeConstraintResource.class,
                TypeConstraintResource.PARAM_TYPE,
                NN.class.getName());
        assertThat(Iterables.getOnlyElement(annotations).
                getCoveredText(), is("anything"));
    }
    @Test
    public void testToString() {
        TypeConstraintResource cs = new TypeConstraintResource();
        assertThat(cs.toString(), is("TypeConstraintResource{types=null}"));
    }
}
