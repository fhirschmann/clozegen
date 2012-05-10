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
            fs.add(WORDS[i], i);
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
        assertEquals(fplist.subList(1, 4), fs.getAdjacentTo("you", 1));
        assertEquals(fplist, fs.getAdjacentTo("you", 2));
    }
}
