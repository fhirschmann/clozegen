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

import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.generators.model.MultisetModel;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyGapGeneratorTest {
    private MultisetModel model;
    private FrequencyGapGenerator generator;

    @Before
    public void setUp() {
        model = new MultisetModel();
        Multiset<String> ms = HashMultiset.create();
        ms.add("of", 100); // 40

        ms.add("in", 70); // 10
        ms.add("to", 60); // 0
        ms.add("for", 50); // 10

        ms.add("with", 30); // 30
        ms.add("on", 10); // 50
        model.setMultiset(ms);
    }

    @Test
    public void testGenerate() {
        generator = new FrequencyGapGenerator("to", model);
        assertThat(generator.generate(3).get(), is(Gap.with("to", "in", "for")));
    }

    @Test
    public void testGenerate2() {
        generator = new FrequencyGapGenerator("on", model);
        assertThat(generator.generate(3).get(), is(Gap.with("on", "with", "for")));
    }
}
