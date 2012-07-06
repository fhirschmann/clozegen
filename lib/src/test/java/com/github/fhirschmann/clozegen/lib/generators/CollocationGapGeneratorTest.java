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
package com.github.fhirschmann.clozegen.lib.generators;

import com.github.fhirschmann.clozegen.lib.generators.CollocationGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.model.CollocationModel;
import com.google.common.base.Optional;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationGapGeneratorTest {
    private CollocationModel model;
    private CollocationGapGenerator generator;

    @Before
    public void setUp() {
        Multiset<String> trigrams = LinkedHashMultiset.create();
        trigrams.add("as in the", 20);
        trigrams.add("as of the", 11);
        trigrams.add("as by a", 55);

        model = new CollocationModel();
        model.setMultiset(trigrams);
    }

    @Test
    public void testGenerate() {
        generator = new CollocationGapGenerator(
                Lists.newArrayList("as", "in", "the"), model);
        Gap gap = new Gap();
        gap.addValidAnswers("in");
        gap.addInvalidAnswers("by");
        assertThat(generator.generate(2), is(Optional.of(gap)));
    }

    @Test
    public void testTrigramConstraint() {
        model.getMultiset().add("as of the");
        generator = new CollocationGapGenerator(
                Lists.newArrayList("as", "in", "the"), model);
        Gap gap = new Gap();
        gap.addValidAnswers("in");
        gap.addInvalidAnswers("by");
        assertThat(generator.generate(2), is(Optional.of(gap)));
    }

    @Test
    public void testNull() {
        generator = new CollocationGapGenerator(
                Lists.newArrayList("xx", "yy", "zz"), model);
        assertFalse(generator.generate(2).isPresent());
    }
}
