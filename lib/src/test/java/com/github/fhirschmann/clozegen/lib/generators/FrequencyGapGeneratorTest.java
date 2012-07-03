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

        generator = new FrequencyGapGenerator();
        generator.initialize(model);
    }

    @Test
    public void testGenerate() {
        generator.initialize("to");
        assertThat(generator.generate(3).get(), is(Gap.with("to", "in", "for")));
    }

    @Test
    public void testGenerate2() {
        generator.initialize("on");
        assertThat(generator.generate(3).get(), is(Gap.with("on", "with", "for")));
    }
}
