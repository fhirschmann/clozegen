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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.BeforeClass;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class RegisterEntryTest {
    private RegisterEntry e1;
    private RegisterEntry e2;
    private RegisterEntry e3;

    @Before
    public void setUp() {
        e1 = new RegisterEntry("foo");
        e2 = new RegisterEntry("bar");
        e3 = new RegisterEntry("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdentifier() {
        new RegisterEntry("foo bar");
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
