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
package org.ow2.sirocco.apis.rest.cimi.tools;

import java.util.List;

import org.ow2.sirocco.apis.rest.cimi.sdk.CimiClient;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiException;
import org.ow2.sirocco.apis.rest.cimi.sdk.CreateResult;
import org.ow2.sirocco.apis.rest.cimi.sdk.Credential;
import org.ow2.sirocco.apis.rest.cimi.sdk.CredentialCreate;
import org.ow2.sirocco.apis.rest.cimi.sdk.CredentialTemplate;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "create credential")
public class CredentialCreateCommand implements Command {
    @Parameter(names = "-name", description = "name of the template", required = false)
    private String name;

    @Parameter(names = "-description", description = "description of the template", required = false)
    private String description;

    @Parameter(names = "-properties", variableArity = true, description = "key value pairs", required = false)
    private List<String> properties;

    @Parameter(names = "-ext", variableArity = true, description = "extended attributes", required = false)
    private List<String> extendedAttributes;

    @Override
    public String getName() {
        return "credential-create";
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiException {
        CredentialTemplate credentialTemplate = new CredentialTemplate();
        if (this.extendedAttributes != null) {
            for (int i = 0; i < this.extendedAttributes.size() / 2; i++) {
                credentialTemplate.setExtensionAttribute(this.extendedAttributes.get(i * 2),
                    this.extendedAttributes.get(i * 2 + 1));
            }
        }
        CredentialCreate credentialCreate = new CredentialCreate();
        credentialCreate.setCredentialTemplate(credentialTemplate);
        credentialCreate.setName(this.name);
        credentialCreate.setDescription(this.description);
        if (this.properties != null) {
            for (int i = 0; i < this.properties.size() / 2; i++) {
                credentialCreate.addProperty(this.properties.get(i * 2), this.properties.get(i * 2 + 1));
            }
        }
        CreateResult<Credential> result = Credential.createCredential(cimiClient, credentialCreate);
        if (result.getJob() != null) {
            System.out.println("Credential " + result.getJob().getTargetResourceRef() + " being created");
            JobShowCommand.printJob(result.getJob(), new ResourceSelectExpandParams());
        } else {
            CredentialShowCommand.printCredential(result.getResource(), new ResourceListParams());
        }
    }
}
