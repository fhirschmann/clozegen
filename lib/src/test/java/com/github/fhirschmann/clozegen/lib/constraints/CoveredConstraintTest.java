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
package com.github.fhirschmann.clozegen.lib.constraints;

import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintResource;
import com.github.fhirschmann.clozegen.lib.generators.api.Gap;
import com.github.fhirschmann.clozegen.lib.type.GapAnnotation;
import com.github.fhirschmann.clozegen.lib.util.UIMAUtils;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import java.util.Iterator;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CoveredConstraintTest {
    @Test
    public void testGetConstraint() throws UIMAException {
        JCas jcas = UIMAUtils.createTestJCas();
        FSMatchConstraint constraint = new CoveredTextConstraint("He");
        GapAnnotation annotation = UIMAUtils.createGapAnnotation(jcas, Gap.with("foo"));
        annotation.setBegin(0);
        annotation.setEnd(2);
        annotation.addToIndexes();
        assertTrue(constraint.match(annotation));
    }
}
