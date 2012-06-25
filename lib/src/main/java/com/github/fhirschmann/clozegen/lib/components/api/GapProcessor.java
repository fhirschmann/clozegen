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
package com.github.fhirschmann.clozegen.lib.components.api;

import java.util.List;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintProvider;

/**
 * A Gap Processor is a class which processes gaps. This can be, for example,
 * an Annotator which generates gaps or a writer which collects frequency data.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public interface GapProcessor {
    /**
     * Called for each annotation which is matched by
     * {@link ConstraintProvider#getConstraint(JCas)}.
     *
     * @param jcas the JCas
     * @param annotationList the list of annotations in a sentence
     * @param index the index of the annotation in question
     */
    void process(final JCas jcas, List<Annotation> annotationList, int index);
}
