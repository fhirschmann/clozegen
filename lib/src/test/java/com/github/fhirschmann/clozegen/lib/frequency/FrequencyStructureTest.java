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
package com.github.fhirschmann.clozegen.lib.frequency;

import com.google.common.collect.Lists;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class FrequencyStructureTest extends TestCase {
    private FrequencyStructure<String> fs;
    private List<FrequencyPair<String>> fplist;

    private final static String[] WORDS = new
            String[] {"why", "are", "you", "reading", "this"};

    @Before
    public void setUp() {
        fs = new FrequencyStructure<String>();
        fplist = Lists.newLinkedList();

        for (int i=0; i< WORDS.length; i++) {
            fs.set(WORDS[i], i);
            fplist.add(new FrequencyPair<String>(WORDS[i], i));
        }
    }

    @Test
    public void testGetFrequency() {
        for (int i=0; i< WORDS.length; i++) {
            assertEquals(i, fs.getFrequency(WORDS[i]));
        }
    }

    @Test
    public void testZeroFrequency() {
        assertEquals(0, fs.getFrequency("---"));
    }

    @Test
    public void testGetAdjacentTo() {
        // Test by using a FrequencyPair
        FrequencyPair<String> fp = new FrequencyPair("you", 2);
        assertEquals(fplist.subList(1, 4), fs.getAdjacentTo(fp, 1));
        assertEquals(fplist, fs.getAdjacentTo(fp, 2));

        // Test by using a value
        assertEquals(FrequencyStructure.frequencyPairs2Values(fplist.subList(1, 4)),
                fs.getAdjacentTo("you", 1));
        assertEquals(FrequencyStructure.frequencyPairs2Values(fplist),
                fs.getAdjacentTo("you", 2));
    }

    @Test
    public void testGetClosestTo() {
        fs.set("foo", 100);
        fs.set("bar", 1000);

        List<FrequencyPair<String>> expected = Lists.newLinkedList();
        expected.add(new FrequencyPair<String>("this", 96));
        expected.add(new FrequencyPair<String>("reading", 97));

        // Test by using FrequencyPair
        assertEquals(expected, fs.getClosestTo(new FrequencyPair("foo", 100), 2));

        // Test by using values
        assertEquals(FrequencyStructure.frequencyPairs2Values(expected),
                fs.getClosestTo("foo", 2));
    }

    @Test
    public void testDuplicate() {
        fs.set("why", 4);
        assertEquals(WORDS.length, fs.size());
    }

    @Test
    public void testSorting() {
        fs.set("foo", 10);
        fs.set("bar", 8);

        List<FrequencyPair<String>> expected = Lists.newLinkedList();
        expected.add(new FrequencyPair<String>("this", 4));
        expected.add(new FrequencyPair<String>("bar", 8));
        expected.add(new FrequencyPair<String>("foo", 10));

        assertEquals(expected, fs.getAdjacentTo(new FrequencyPair("bar", 8), 1));
    }
}
