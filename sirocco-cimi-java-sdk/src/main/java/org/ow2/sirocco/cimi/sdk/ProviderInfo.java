/**
 *
 * SIROCCO
 * Copyright (C) 2013 Orange
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 */
package org.ow2.sirocco.cimi.sdk;

public class ProviderInfo {
    private org.ow2.sirocco.cimi.domain.ProviderInfo info;

    public ProviderInfo(final org.ow2.sirocco.cimi.domain.ProviderInfo info) {
        this.info = info;
    }

    public String getProviderAccountId() {
        return this.info.getProviderAccountId();
    }

    public String getProviderAssignedId() {
        return this.info.getProviderAssignedId();
    }

    public String getProviderName() {
        return this.info.getProviderName();
    }

    public String getLocation() {
        return this.info.getLocation();
    }

}
