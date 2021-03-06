/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
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
 *  $Id$
 *
 */

package org.ow2.sirocco.cimi.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ow2.sirocco.cimi.domain.CimiObjectCommonAbstract;

/**
 * Base class of all first-class CIMI resources.
 * 
 * @param <E> the element type
 */
public abstract class Resource<E extends CimiObjectCommonAbstract> {

    protected CimiClient cimiClient;

    protected E cimiObject;

    Resource(final CimiClient cimiClient, final E cimiObject) {
        this.cimiClient = cimiClient;
        this.cimiObject = cimiObject;
        // this.cimiObject.setProperties(new HashMap<String, String>()); // XXX
    }

    /**
     * Returns the human-readable name of the resource.
     * 
     * @return the name
     */
    public String getName() {
        return this.cimiObject.getName();
    }

    /**
     * Returns the id of the resource.
     * 
     * @return the id
     */
    public String getId() {
        if (this.cimiObject.getId() != null) {
            return this.cimiObject.getId();
        } else {
            return this.cimiObject.getHref();
        }
    }

    /**
     * Returns the description of the resource.
     * 
     * @return the description
     */
    public String getDescription() {
        return this.cimiObject.getDescription();
    }

    /**
     * Returns the time when this resource was created.
     * 
     * @return the created
     */
    public Date getCreated() {
        return this.cimiObject.getCreated();
    }

    /**
     * Returns the properties of the resource (map of key/value pairs).
     * 
     * @return the properties
     */
    public Map<String, String> getProperties() {
        return this.cimiObject.getProperties();
    }

    /**
     * Returns the time at which the last explicit attribute update was made on
     * the resource.
     * 
     * @return the updated
     */
    public Date getUpdated() {
        return this.cimiObject.getUpdated();
    }

    /**
     * Sets the human-readable name of the resource.
     * 
     * @param name the new name
     */
    public void setName(final String name) {
        this.cimiObject.setName(name);
    }

    /**
     * Sets the description of the resource.
     * 
     * @param description the new description
     */
    public void setDescription(final String description) {
        this.cimiObject.setDescription(description);
    }

    /**
     * Sets the properties of the resource (map of key/value pairs).
     * 
     * @param properties the properties
     */
    public void setProperties(final Map<String, String> properties) {
        this.cimiObject.setProperties(properties);
    }

    /**
     * Adds a property (key/value pair) to the resource.
     * 
     * @param key property key
     * @param value property value
     */
    public void addProperty(final String key, final String value) {
        if (this.cimiObject.getProperties() == null) {
            this.cimiObject.setProperties(new HashMap<String, String>());
        }
        this.cimiObject.getProperties().put(key, value);
    }

    /**
     * Retrieves an extension attribute for this resource.
     * 
     * @param name the name of the attribute
     * @return the attribute value
     */
    public Object getExtensionAttribute(final String name) {
        return this.cimiObject.getExtensionAttribute(name);
    }

    /**
     * Retrieves the map containing all the extension attributes defined on this
     * resource.
     * 
     * @return the extension attributes
     */
    public Map<String, Object> getExtensionAttributes() {
        return this.cimiObject.getExtensionAttributes();
    }

    /**
     * Sets an extension attribute on this resource.
     * 
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    public void setExtensionAttribute(final String name, final Object value) {
        this.cimiObject.addExtensionAttribute(name, value);
    }

    E getResource() {
        return this.cimiObject;
    }

}
