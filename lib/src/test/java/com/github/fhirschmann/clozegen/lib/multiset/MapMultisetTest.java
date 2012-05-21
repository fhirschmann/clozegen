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
package com.github.fhirschmann.clozegen.lib.multiset;

import com.google.common.collect.ImmutableSet;
import junit.framework.TestCase;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class MapMultisetTest {
    private MapMultiset<String, String> mms;

    @Before
    public void setUp() {
        mms = MapMultiset.create();
        mms.add("university", "of", 42);
        mms.add("university", "in", 4);
    }

    public void testGetCount() {
        assertEquals(42, mms.get("university").count("of"));
        assertEquals(4, mms.get("university").count("in"));
    }

    public void testZeroCount() {
        assertEquals(0, mms.get("asdf").count("asdf"));
    }

    public void testAdd() {
        mms.add("foo", "bar", 12);
        mms.add("foo", "bar", 12);
        assertEquals(24, mms.get("foo").count("bar"));
    }

    public void testTotal() {
        assertEquals(46, mms.total());
    }

    public void testToString() {
        assertEquals("{university=[of x 42, in x 4]}", mms.toString());
    }

    public void testSorted() {
        mms.add("foo","bar2", 40);
        mms.add("foo","bar1", 10);
        mms.add("foo","bar3", 50);

        assertEquals(ImmutableSet.of("bar3", "bar2", "bar1"),
                mms.getSorted("foo").elementSet());
    }
}
