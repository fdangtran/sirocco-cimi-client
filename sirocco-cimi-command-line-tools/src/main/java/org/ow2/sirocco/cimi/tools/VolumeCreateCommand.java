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
package org.ow2.sirocco.cimi.tools;

import java.util.List;

import org.ow2.sirocco.cimi.sdk.CimiClient;
import org.ow2.sirocco.cimi.sdk.CimiClientException;
import org.ow2.sirocco.cimi.sdk.CreateResult;
import org.ow2.sirocco.cimi.sdk.Volume;
import org.ow2.sirocco.cimi.sdk.VolumeConfiguration;
import org.ow2.sirocco.cimi.sdk.VolumeCreate;
import org.ow2.sirocco.cimi.sdk.VolumeTemplate;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "create volume")
public class VolumeCreateCommand implements Command {
    @Parameter(names = "-template", description = "id of the template", required = false)
    private String templateId;

    @Parameter(names = "-config", description = "id of the config", required = false)
    private String configId;

    @Parameter(names = "-capacity", description = "capacity of the volume in megabytes", required = false)
    private Integer capacityMB;

    @Parameter(names = "-name", description = "name of the template", required = false)
    private String name;

    @Parameter(names = "-description", description = "description of the template", required = false)
    private String description;

    @Parameter(names = "-properties", variableArity = true, description = "key value pairs", required = false)
    private List<String> properties;

    @Override
    public String getName() {
        return "volume-create";
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiClientException {
        if (this.templateId == null && this.configId == null && this.capacityMB == null) {
            throw new ParameterException("You need to provide either a template id, a config id or a capacity");
        }
        VolumeCreate volumeCreate = new VolumeCreate();
        VolumeTemplate volumeTemplate;
        if (this.templateId != null) {
            volumeCreate.setVolumeTemplateRef(this.templateId);
        } else if (this.configId != null) {
            volumeTemplate = new VolumeTemplate();
            volumeTemplate.setVolumeConfigRef(this.configId);
            volumeCreate.setVolumeTemplate(volumeTemplate);
        } else {
            volumeTemplate = new VolumeTemplate();
            VolumeConfiguration volumeConfig = new VolumeConfiguration();
            volumeConfig.setCapacity(this.capacityMB * 1000);
            // XXX
            volumeConfig.setType("");
            volumeTemplate.setVolumeConfig(volumeConfig);
            volumeCreate.setVolumeTemplate(volumeTemplate);
        }

        volumeCreate.setName(this.name);
        volumeCreate.setDescription(this.description);
        if (this.properties != null) {
            for (int i = 0; i < this.properties.size() / 2; i++) {
                volumeCreate.addProperty(this.properties.get(i * 2), this.properties.get(i * 2 + 1));
            }
        }
        CreateResult<Volume> result = Volume.createVolume(cimiClient, volumeCreate);
        if (result.getJob() != null) {
            System.out.println("Job:");
            JobShowCommand.printJob(result.getJob(), new ResourceSelectExpandParams());
        }
        System.out.println("Volume being created:");
        VolumeShowCommand.printVolume(result.getResource(), new ResourceSelectExpandParams());
    }
}
