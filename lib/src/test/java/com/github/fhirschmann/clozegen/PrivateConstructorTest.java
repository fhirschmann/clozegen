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
package com.github.fhirschmann.clozegen;

import com.github.fhirschmann.clozegen.lib.imf.IntermediateFormat;
import com.github.fhirschmann.clozegen.lib.multiset.ReadMultisets;
import com.github.fhirschmann.clozegen.lib.multiset.WriteMultisets;
import com.github.fhirschmann.clozegen.lib.pipeline.PipelineFactory;
import com.github.fhirschmann.clozegen.lib.register.RegisterFactory;
import com.github.fhirschmann.clozegen.lib.util.*;
import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.util.Set;
import org.junit.Test;

/**
 * This is a pretty bad hack to exclude private constructors in utility classes
 * from test coverage.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class PrivateConstructorTest {
    @Test
    public void testPrivateConstructors() throws Exception {
        Set<Class<? extends Object>> set = Sets.newHashSet();
        set.add(CollectionUtils.class);
        set.add(MiscUtils.class);
        set.add(MultisetUtils.class);
        set.add(UIMAUtils.class);
        set.add(ReadMultisets.class);
        set.add(WriteMultisets.class);
        set.add(PipelineFactory.class);
        set.add(RegisterFactory.class);
        set.add(IntermediateFormat.class);
        set.add(JCasFactory2.class);

        for (Class<? extends Object> clazz : set) {
            Constructor<?>[] cons = clazz.getDeclaredConstructors();
            cons[0].setAccessible(true);
            cons[0].newInstance((Object[]) null);
        }
    }
}
