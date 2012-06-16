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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class DescriptionRegisterEntryTest {
    private DescriptionRegisterEntry e1;
    private DescriptionRegisterEntry e2;
    private DescriptionRegisterEntry e3;

    @Before
    public void setUp() {
        e1 = new DescriptionRegisterEntry("foo", GapAnnotator.class);
        e2 = new DescriptionRegisterEntry("bar", GapAnnotator.class);
        e3 = new DescriptionRegisterEntry("foo", GapAnnotator.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdentifier() {
        new DescriptionRegisterEntry("foo bar", GapAnnotator.class);
    }

    @Test
    public void testHashCode() {
        assertThat(e1.hashCode(), is(not(e2.hashCode())));
        assertThat(e1.hashCode(), is(e3.hashCode()));
    }

    @Test
    public void testEquals() {
        assertThat(e1, is(not(e2)));
        assertThat(e1, is(e3));
    }
}
