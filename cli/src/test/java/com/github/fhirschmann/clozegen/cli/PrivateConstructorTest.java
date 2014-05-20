/*
 * Copyright (c) 2012 Fabian Hirschmann <fabian@hirschmann.email>
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
package com.github.fhirschmann.clozegen.cli;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * This is a pretty bad hack to exclude private constructors in utility classes
 * from test coverage.
 *
 * @author Fabian Hirschmann <fabian@hirschmann.email>
 */
public class PrivateConstructorTest {
    @Test
    public void testPrivateConstructors() throws Exception {
        Set<Class<? extends Object>> set = Sets.newHashSet();
        set.add(Utils.class);

        for (Class<? extends Object> clazz : set) {
            Constructor<?>[] cons = clazz.getDeclaredConstructors();
            cons[0].setAccessible(true);
            cons[0].newInstance((Object[]) null);
        }
    }
}
