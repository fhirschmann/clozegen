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

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.util.Collection;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class UIMAUtilsTest {
    private JCas jcas;

    @Before
    public void setUp() throws UIMAException {
        jcas = JCasFactory.createJCas();
    }

    @Test
    public void testCreateGapAnnotationAll() throws UIMAException {
        GapAnnotation gap = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("true1"), Sets.newHashSet("false1", "false2"));
        Collection<String> list = FSCollectionFactory.create(gap.getAllAnswers());
        assertThat(list, hasItems("true1", "false1", "false2"));
    }

    @Test
    public void testCreateGapAnnotationValid() throws UIMAException {
        GapAnnotation gap = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("true1", "true2"), Sets.newHashSet("false1", "false2"));
        Collection<String> list = FSCollectionFactory.create(gap.getValidAnswers());
        assertThat(list, hasItems("true1", "true2"));
        assertThat(list, not(hasItems("false2")));
    }

    /**
     * Hack to exclude the private constructor from code coverage metrics.
     */
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<?>[] cons = UIMAUtils.class.getDeclaredConstructors();
        cons[0].setAccessible(true);
        cons[0].newInstance((Object[]) null);
    }

    @Test
    public void testCreateGapAnnotation_3args() {
    }

    @Test
    public void testCreateGapAnnotation_JCas_Gap() {
    }

    @Test
    public void testCopyBounds() {
    }

    @Test
    public void testGetAdjacentAnnotations() {
    }

    @Test
    public void testGetAdjacentTokens() {
    }
}
