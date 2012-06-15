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

import java.util.Map;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.uimafit.component.Resource_ImplBase;

/**
 * An extension to {@link Resource_ImplBase} which provides a more convenient
 * {@link AbstractResource#initialize()}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class AbstractResource extends Resource_ImplBase {
    @Override
    public boolean initialize(final ResourceSpecifier aSpecifier,
            final Map<String, Object> aAdditionalParams)
            throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        } else {
            return initialize();
        }
    }

    /**
     * Called upon resource initialization. Overwrite this method if required.
     *
     * @return true on success
     */
    public boolean initialize() {
        return true;
    }
}
