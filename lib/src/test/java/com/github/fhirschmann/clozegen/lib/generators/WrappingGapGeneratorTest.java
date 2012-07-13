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
package com.github.fhirschmann.clozegen.lib.generators;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.quickcheck.generator.iterable.Iterables;

import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.QGapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.Gap;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class WrappingGapGeneratorTest {
    @Test
    public void testGenerate() {
        Gap gap = Gap.with("foo", "bar");
        WrappingGapGenerator gen = new WrappingGapGenerator(gap);
        assertThat(gen.generate(0).get(), is(gap));
    }

    @Test
    public void testGenerateQ() {
        for (Gap gap : Iterables.toIterable(new QGapGenerator())) {
            WrappingGapGenerator gen = new WrappingGapGenerator(gap);
            assertThat(gen.generate(0).get(), is(gap));
        }
    }

    @Test
    public void testToString() {
        Gap gap = Gap.with("foo", "bar");
        WrappingGapGenerator gen = new WrappingGapGenerator(gap);
        assertThat(gen.toString(),
                is("WrappingGapGenerator{gap=Gap{valid=[foo], invalid=[bar]}}"));
    }
}