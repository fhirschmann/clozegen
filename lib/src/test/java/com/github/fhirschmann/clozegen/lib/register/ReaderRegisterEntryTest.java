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
package com.github.fhirschmann.clozegen.lib.register;

import com.github.fhirschmann.clozegen.lib.reader.IntermediateFormatReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReaderRegisterEntryTest {
    private ReaderRegisterEntry entry;

    @Before
    public void setUp() {
        entry = new ReaderRegisterEntry("txt", TextReader.class);
        entry.setName("name");
    }

    @Test
    public void testGetName() {
        assertThat(entry.getName(), is("name"));
    }

    @Test
    public void testSetReaderClass() {
        entry.setReaderClass(IntermediateFormatReader.class);
        assertThat(entry.getReaderClass().getName(),
              is("com.github.fhirschmann.clozegen.lib.reader.IntermediateFormatReader"));
    }

    @Test
    public void testToString() {
        System.out.println(entry.toString());
        assertThat(entry.toString(), is("ReaderRegisterEntry{identifier=txt, name=name, "
                + "class=de.tudarmstadt.ukp.dkpro.core.io.text.TextReader}"));
    }
}
