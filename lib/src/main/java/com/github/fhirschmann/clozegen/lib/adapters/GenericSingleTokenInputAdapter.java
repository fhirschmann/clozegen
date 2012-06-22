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
import org.apache.uima.jcas.tcas.Annotation;
import static com.google.common.base.Preconditions.checkNotNull;
import org.uimafit.descriptor.ConfigurationParameter;

/**
 * This adapter calls a {@link SingleTokenInputGapGenerator} with the covered text of the
 * input annotation.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class GenericSingleTokenInputAdapter
        extends AbstractResource implements GeneratorAdapter {
    /**
     * The class of the generator to use. This will be passed to
     * {@link Class#forName(java.lang.String)}.
     */
    public static final String PARAM_GENERATOR_CLASS = "GeneratorClass";
    @ConfigurationParameter(name = PARAM_GENERATOR_CLASS, mandatory = true)
    private String generatorClass;

    /**
     * The underlying generator.
     */
    private SingleTokenInputGapGenerator generator;

    @Override
    public boolean initialize() {
        try {
        	@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(checkNotNull(generatorClass));
            generator = (SingleTokenInputGapGenerator) clazz.newInstance();
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
