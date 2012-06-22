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
package com.github.fhirschmann.clozegen.lib.adapters;

import com.github.fhirschmann.clozegen.lib.adapters.api.AbstractResource;
import com.github.fhirschmann.clozegen.lib.adapters.api.GeneratorAdapter;
import com.github.fhirschmann.clozegen.lib.generators.api.GapGenerator;
import com.github.fhirschmann.clozegen.lib.generators.api.SingleTokenInputGapGenerator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.uima.jcas.tcas.Annotation;
import org.uimafit.component.Resource_ImplBase;
import org.uimafit.descriptor.ExternalResource;

/**
 * This adapter calls a {@link SingleTokenInputGapGenerator} with the covered text of the
 * input annotation.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class SingleTokenInputAdapter
        extends AbstractResource implements GeneratorAdapter {
    public static final String RES_GENERATOR = "GeneratorClass";
    @ExternalResource(key = RES_GENERATOR)
    private String generatorClass;

    private SingleTokenInputGapGenerator generator;

    @Override
    public boolean initialize() {
        try {
            generator = (SingleTokenInputGapGenerator) Class.
                    forName(generatorClass).newInstance();
        } catch (InstantiationException ex) {
            getLogger().error(ex);
        } catch (IllegalAccessException ex) {
            getLogger().error(ex);
        } catch (ClassNotFoundException ex) {
            getLogger().error(ex);
        }

        return true;
    }

    @Override
    public GapGenerator generator(
            final List<Annotation> annotationList, final int offset) {
        generator.initialize(annotationList.get(offset).getCoveredText());
        return generator;
    }
}
