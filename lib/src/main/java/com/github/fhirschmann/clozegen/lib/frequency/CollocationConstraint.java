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
package com.github.fhirschmann.clozegen.lib.frequency;

import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.component.Resource_ImplBase;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CollocationConstraint extends Resource_ImplBase {
    private FSMatchConstraint constraint;
    private Class<? extends Annotation> annotation;

    /**
     * @return the constraint
     */
    public FSMatchConstraint getConstraint() {
        return constraint;
    }

    /**
     * @param constraint the constraint to set
     */
    public void setConstraint(FSMatchConstraint constraint) {
        this.constraint = constraint;
    }

    /**
     * @return the annotation
     */
    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    /**
     * @param annotation the annotation to set
     */
    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

}
