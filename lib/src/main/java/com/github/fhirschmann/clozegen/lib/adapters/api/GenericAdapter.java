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
package com.github.fhirschmann.clozegen.lib.adapters.api;

import org.uimafit.descriptor.ConfigurationParameter;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Extending classes will be provided a {@link GenericAdapter#generator}
 * instance of {@link GenericAdapter#PARAM_GENERATOR_CLASS}.
 *
 * @param <T> the generator interface
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public abstract class GenericAdapter<T> extends AbstractResource
        implements GeneratorAdapter {
    /**
     * <em>[mandatory]</em>
     *
     * The class of the {@link SingleTokenInputGapGenerator} to use.
     * This will be passed to {@link Class#forName(java.lang.String)}.
     */
    public static final String PARAM_GENERATOR_CLASS = "GeneratorClass";
    @ConfigurationParameter(name = PARAM_GENERATOR_CLASS, mandatory = true)
    private String generatorClass;

    /**
     * The generator instance which will be provided for extending classes.
     */
    protected T generator;

    @SuppressWarnings("unchecked")
	@Override
    public boolean initialize() {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName(checkNotNull(generatorClass));
            checkNotNull(clazz);
            generator = (T) clazz.newInstance();
        } catch (InstantiationException ex) {
            getLogger().error(ex);
        } catch (IllegalAccessException ex) {
            getLogger().error(ex);
        } catch (ClassNotFoundException ex) {
            getLogger().error(ex);
        }

        return true;
    }
}
