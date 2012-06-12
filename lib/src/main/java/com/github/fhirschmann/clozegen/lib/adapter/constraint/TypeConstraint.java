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
package com.github.fhirschmann.clozegen.lib.adapter.constraint;

import com.github.fhirschmann.clozegen.lib.adapter.api.Constraint;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FSTypeConstraint;
import org.uimafit.descriptor.ConfigurationParameter;


/**
 * A generic constraint which matches exactly one type.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class TypeConstraint extends Constraint {
    /** The type to match. */
    public static final String PARAM_TYPE = "Types";
    @ConfigurationParameter(name = PARAM_TYPE, mandatory = true)
    private String type;

    @Override
    public FSMatchConstraint getConstraint() {
        FSTypeConstraint cons = ConstraintFactory.instance().
                createTypeConstraint();
        cons.add(type);
        return cons;
    }
}
