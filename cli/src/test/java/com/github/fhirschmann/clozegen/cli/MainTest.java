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
package com.github.fhirschmann.clozegen.cli;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Before;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MainTest {
    File in;

    @Before
    public void setUp() throws IOException {
        in = File.createTempFile("clozegen", ".txt");
        Files.write("He can't think of anything.", in, Charsets.UTF_8);
    }

    @Test
    public void testClz() throws IOException {
        File out = File.createTempFile("clozegen", ".clz");
        String cmd = String.format("--generators preps/1 %s %s",
                in.getAbsolutePath(), out.getAbsolutePath());
        Main.main(cmd.split(" "));
        assertThat(Files.readLines(out, Charsets.UTF_8).get(0),
                is("He can't think {of}{of} anything."));
    }

    @Test
    public void testTxt() throws IOException {
        File out = File.createTempFile("clozegen", ".txt");
        String cmd = String.format("--generators preps/1 %s %s",
                in.getAbsolutePath(), out.getAbsolutePath());
        Main.main(cmd.split(" "));
        assertThat(Files.readLines(out, Charsets.UTF_8).get(0),
                is("He can't think __[of] anything."));
    }
}
