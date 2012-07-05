/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschm.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
