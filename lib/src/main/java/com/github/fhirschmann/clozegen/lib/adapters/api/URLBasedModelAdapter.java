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

import java.io.IOException;

import org.uimafit.descriptor.ConfigurationParameter;

import com.github.fhirschmann.clozegen.lib.generators.model.URLBasedModel;
import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;

/**
 * Extending classes will have to declare a model which extends {@link URLBasedModel} of
 * which a new instance will be created.
 *
 * <p>
 * The {@link URLBasedModelAdapter#model} will by loaded by calling
 * {@link URLBasedModel#load(URL)}. This works by creating a new instance
 * of {@code <M>}.
 * </p>
 *
 * @param <M> a model which extends {@link URLBasedModel}
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class URLBasedModelAdapter<M extends URLBasedModel> extends AbstractResource {
    /**
     * The underlying model. This variable can be used in extending classes.
     */
    protected M model;

    /**
     * The path to the model. This parameter will be passed to
     * {@link URLBasedModel#load(URL)}.
     */
    public static final String PARAM_PATH = "Path";
    @ConfigurationParameter(name = PARAM_PATH, mandatory = true)
    private String path;

    @SuppressWarnings("unchecked")
    @Override
    public boolean initialize() {
        @SuppressWarnings("serial")
        TypeToken<M> type = new TypeToken<M>(getClass()) {};
        try {
            model = (M) type.getRawType().newInstance();
            model.load(Resources.getResource(path));
            return true;
        } catch (InstantiationException ex) {
            getLogger().error(ex);
        } catch (IllegalAccessException ex) {
            getLogger().error(ex);
        } catch (IOException ex) {
            getLogger().error(ex);
        }
        return false;
    }
}
