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
package com.github.fhirschmann.clozegen.lib.register;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A register of mappings from file extensions to {@link ReaderRegisterEntry}.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ReaderRegister extends ForwardingMap<String, ReaderRegisterEntry> {
    public static final Logger LOGGER = Logger.getLogger(ReaderRegister.class.getName());

    /**
     * The entry register.
     */
    private Map<String, ReaderRegisterEntry> register;

    /**
     * Creates a new empty register.
     */
    public ReaderRegister() {
        register = Maps.newHashMap();
    }

    @Override
    protected Map<String, ReaderRegisterEntry> delegate() {
        return register;
    }
}
