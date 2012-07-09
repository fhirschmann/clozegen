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
package com.github.fhirschmann.clozegen.lib;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.uimafit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Before;
import org.junit.Test;

import com.github.fhirschmann.clozegen.lib.adapters.DummyAdapter;
import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.github.fhirschmann.clozegen.lib.constraints.resources.TypeConstraintResource;
import com.github.fhirschmann.clozegen.lib.register.AnnotatorRegisterEntry;
import com.github.fhirschmann.clozegen.lib.register.Registers;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.ART;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ClozeTestGeneratorTest {
    private ClozeTestGenerator gen;
    private AnnotatorRegisterEntry entry;
    private List<String> expected;

    @Before
    public void setUp() throws ResourceInitializationException, IOException {
        gen = new ClozeTestGenerator();
        entry = new AnnotatorRegisterEntry("test",
                GapAnnotator.class,
                GapAnnotator.CONSTRAINT_KEY,
                createExternalResourceDescription(
                    TypeConstraintResource.class,
                    TypeConstraintResource.PARAM_TYPE, ART.class.getName()),
                GapAnnotator.ADAPTER_KEY,
                createExternalResourceDescription(DummyAdapter.class));
        entry.setSupportedLanguages(Sets.newHashSet("en"));
        Registers.annotator().clear();
        Registers.annotator().add(entry);
        expected = Resources.readLines(
                Resources.getResource("clozetest/output.clz"), Charsets.UTF_8);
    }

    @Test
    public void testRun1() throws UIMAException, IOException {
        gen.activate("test", 1);
        File outFile = File.createTempFile("ctgt", ".clz");
        gen.run(Resources.getResource("clozetest/input.txt"),
                outFile.toURI().toURL(), "en");
        List<String> actual = Files.readLines(outFile, Charsets.UTF_8);
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void testRun2() throws ResourceInitializationException, IOException,
            UIMAException {
        gen.activate("test", 1);
        String result = gen.run(Resources.getResource("clozetest/input.txt"), "en");
        int i = 0;
        for (String line : result.split(System.getProperty("line.separator"))) {
            assertThat(line, is(expected.get(i++)));
        }
    }

    @Test
    public void testRun3() throws ResourceInitializationException, IOException, UIMAException {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("test", 1);
        gen.activate(map);
        JCas jcas = UIMAUtils.createJCas(
                Joiner.on(System.getProperty("line.separator")).join(Resources.readLines(
                Resources.getResource("clozetest/input.txt"), Charsets.UTF_8)), "en");
        File outFile = File.createTempFile("ctgt", ".clz");
        gen.run(jcas, outFile.toURI().toURL());
        List<String> actual = Files.readLines(outFile, Charsets.UTF_8);
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(expected.get(i)));
        }
    }
}
