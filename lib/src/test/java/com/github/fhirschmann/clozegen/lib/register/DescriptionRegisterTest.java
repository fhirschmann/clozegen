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
package com.github.fhirschmann.clozegen.lib.register;

import com.github.fhirschmann.clozegen.lib.components.GapAnnotator;
import com.google.common.collect.Sets;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DescriptionRegisterTest {
    DescriptionRegisterEntry e1;
    DescriptionRegisterEntry e2;
    DescriptionRegisterEntry e3;
    DescriptionRegister r;

    @Before
    public void setUp() {
        r = new DescriptionRegister();
        e1 = new DescriptionRegisterEntry("foo", GapAnnotator.class);
        e1.setSupportedLanguages(Sets.newHashSet("de", "en"));
        e2 = new DescriptionRegisterEntry("bar", GapAnnotator.class);
        e2.setSupportedLanguages(Sets.newHashSet("de"));
        e3 = new DescriptionRegisterEntry("baz", GapAnnotator.class);
        e3.setSupportedLanguages(Sets.newHashSet("en"));
        Collections.addAll(r, e1, e2, e3);
    }

    @Test
    public void testContainsIdentifier() {
        assertTrue(r.containsIdentifier("foo"));
        assertTrue(r.containsIdentifier("bar"));
        assertTrue(r.containsIdentifier("baz"));
        assertFalse(r.containsIdentifier("asdf"));
    }

    @Test
    public void testAdd() {
        r.clear();
        assertThat(r.add(e1), is(true));
        assertThat(r, hasItem(e1));
        assertThat(r.add(e1), is(false));
    }

    @Test
    public void testGetAnnotatorsForLanguage() {
        assertThat(r.getAnnotatorsForLanguage("de"), hasItems(e1, e2));
    }

    @Test
    public void testGet() {
        assertThat(r.get("foo"), is(e1));
    }

    @Test
    public void testDelegate() {
        assertThat(r.delegate(), hasItems(e1, e2, e3));
    }
}
