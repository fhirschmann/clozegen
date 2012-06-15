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
package com.github.fhirschmann.clozegen.lib.constraints.resources;

import com.github.fhirschmann.clozegen.lib.constraints.CoveredTextConstraint;
import com.github.fhirschmann.clozegen.lib.constraints.api.ConstraintResource;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.jcas.JCas;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class CoveredTextConstraintResource extends ConstraintResource {
    /** The string to match. */
    public static final String PARAM_STRING = "String";
    @ConfigurationParameter(name = PARAM_STRING, mandatory = true)
    private String string;

    @Override
    public FSMatchConstraint getConstraint(JCas jcas) {
        return new CoveredTextConstraint(string);
    }
}
