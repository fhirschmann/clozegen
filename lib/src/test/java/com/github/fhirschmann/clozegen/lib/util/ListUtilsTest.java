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
package com.github.fhirschmann.clozegen.lib.util;

import com.google.common.collect.Lists;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ListUtilsTest extends TestCase {
    private List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);

    @Test
    public void testAdjacentTo() {
        assertEquals(ListUtils.getAdjacentTo(list, 3, 1),
                Lists.newArrayList(2, 3, 4));
    }

    @Test
    public void testAdjacentToUpperBound() {
        assertEquals(ListUtils.getAdjacentTo(list, 5, 1),
                Lists.newArrayList(4, 5));
    }

    @Test
    public void testAdjacentToLowerBound() {
        assertEquals(ListUtils.getAdjacentTo(list, 1, 1),
                Lists.newArrayList(1, 2));
    }
}
