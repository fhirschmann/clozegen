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
package com.github.fhirschmann.clozegen.lib.frequency;

import com.google.common.base.Objects;

/**
 * This is a generic way of mapping a frequency to a value.
 *
 * @author Fabian Hirschmann <fabian@hirschm.net>
 */
public class ValueCount<T> implements Comparable {
    /** The count (number of occurrences). */
    private Integer count;

    /** The value to assign the count to. */
    private T value;

    /**
     * Initialize the Mapping.
     *
     * @param value the value to assign the count to
     * @param count the count to assign
     */
    public ValueCount(final T value, final int count) {
        this.value = value;
        this.count = count;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final T value) {
        this.value = value;
    }

    @Override
    public int compareTo(final Object object) {
        final ValueCount other = (ValueCount) object;
        return count.compareTo(other.getCount());
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getValue(), getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue(), getCount());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (other.getClass() != this.getClass())) {
            return false;
        }
        final ValueCount valueCount = (ValueCount) other;
        return valueCount.getValue().equals(getValue());
    }
}
