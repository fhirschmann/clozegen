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
package com.github.fhirschmann.clozegen.lib.util;

import java.net.URL;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Additional utilities dealing with Resources.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public final class Resources2 {
    /**
     * Utility class.
     */
    private Resources2() {
    }

    /**
     * Returns a {@code URL} pointing to {@code resourceName} if the resource is found in
     * the class path. {@code Resources.class.getClassLoader()} is used to locate the
     * resource.
     *
     * @param resourceName the resource in question
     * @return the url of the resource
     */
    public static URL getResource(final String resourceName) {
        URL url = Thread.currentThread().getContextClassLoader().
                getResource(resourceName);
        checkArgument(url != null, "resource %s not found.", resourceName);
        return url;
    }
}
