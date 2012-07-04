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
package com.github.fhirschmann.clozegen.lib.formatters;

import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import com.google.common.collect.Sets;
import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.uimafit.factory.JCasFactory;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PlainTextFormatterTest {

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
        PlainTextFormatter formatter = new PlainTextFormatter();
        assertEquals("He studies __[at] the university.",
                formatter.format(jcas));
    }
}
