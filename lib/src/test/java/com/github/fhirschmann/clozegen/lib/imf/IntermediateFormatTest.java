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
package com.github.fhirschmann.clozegen.lib.imf;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class IntermediateFormatTest {

    @Before
    public void setUp() throws UIMAException {
    }

    @Test
    public void testFormat() throws UIMAException {
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentLanguage("en");
        jcas.setDocumentText("He studies at the university.");
        GapAnnotation gap = UIMAUtils.createGapAnnotation(jcas,
                Sets.newHashSet("at"), Sets.newHashSet("at"));
        gap.setBegin(11);
        gap.setEnd(13);
        gap.addToIndexes();
        assertEquals("He studies {at}{at} the university.",
                IntermediateFormat.format(jcas));
    }

    @Test
    public void testParse() throws UIMAException {
        String str = "He studies {at}{in,on,of,at} the university."
                + "He can't think {of}{at, in , booh!} anything better than this.";
        JCas jcas = IntermediateFormat.parse(str);

        GapAnnotation gap1 = JCasUtil.selectByIndex(jcas, GapAnnotation.class, 0);
        assertEquals(Sets.newHashSet("in", "on", "of", "at"),
                Sets.newHashSet(FSCollectionFactory.create(gap1.getAllAnswers())));
        assertEquals(Sets.newHashSet("at"),
                Sets.newHashSet(FSCollectionFactory.create(gap1.getValidAnswers())));

        GapAnnotation gap2 = JCasUtil.selectByIndex(jcas, GapAnnotation.class, 1);
        assertEquals(Sets.newHashSet("at", "in", "booh!", "of"),
                Sets.newHashSet(FSCollectionFactory.create(gap2.getAllAnswers())));
        assertEquals(Sets.newHashSet("of"),
                Sets.newHashSet(FSCollectionFactory.create(gap2.getValidAnswers())));
    }

    public void testFormatParse() throws UIMAException {
        String str = "He studies {at}{in} the university";
        JCas jcas = IntermediateFormat.parse(str);
        String result = IntermediateFormat.format(jcas);
        assertEquals(str, result);

        String str2 = "He studies {tok1}{tok2,tok3,tok4} the {tok5}{tok6,tok7}";
        JCas jcas2 = IntermediateFormat.parse(str2);
        String result2 = IntermediateFormat.format(jcas2);
        assertEquals(str2, result2);
    }
}
