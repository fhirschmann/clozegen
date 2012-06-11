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
package com.github.fhirschmann.clozegen.lib.generator;

import com.github.fhirschmann.clozegen.lib.multiset.MapMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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


        MapMultiset<String, String> after = MapMultiset.create();
        after.add("as", "in", 20);
        after.add("as", "of", 10);
        after.add("as", "by", 5);

        MapMultiset<String, String> before = MapMultiset.create();
        before.add("the", "in", 20);

        model = new CollocationModel();
        model.setNGrams(trigrams);
        model.setBefore(before);
        model.setAfter(after);
    }

    @Test
    public void testGenerate() {
        generator = new CollocationGapGenerator("as", "in", "the", model);
        Gap gap = new Gap();
        gap.setValidAnswers("in");
        gap.setInvalidAnswers("of");
        assertEquals(gap, generator.generate(2));
    }

    @Test
    public void testTrigramConstraint() {
        model.getNGrams().add("as of the");
        generator = new CollocationGapGenerator("as", "in", "the", model);
        Gap gap = new Gap();
        gap.setValidAnswers("in");
        gap.setInvalidAnswers("by");
        assertEquals(gap, generator.generate(2));
    }

    @Test
    public void testNull() {
        generator = new CollocationGapGenerator("xx", "yy", "zz", model);
        assertNull(generator.generate(2));
    }
}
