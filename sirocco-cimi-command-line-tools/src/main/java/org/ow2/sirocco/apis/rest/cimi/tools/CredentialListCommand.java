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

import org.nocrala.tools.texttablefmt.Table;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiClient;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiException;
import org.ow2.sirocco.apis.rest.cimi.sdk.Credential;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

@Parameters(commandDescription = "list machine images")
public class CredentialListCommand implements Command {
    public static String COMMAND_NAME = "credential-list";

    @ParametersDelegate
    private ResourceListParams listParams = new ResourceListParams("id", "name");

    @Override
    public String getName() {
        return CredentialListCommand.COMMAND_NAME;
    }

    @Override
    public void execute(final CimiClient cimiClient) throws CimiException {
        List<Credential> creds = Credential.getCredentials(cimiClient, this.listParams.buildQueryParams());

        Table table = CommandHelper.createResourceListTable(this.listParams, "id", "name", "description", "created", "updated",
            "properties", "publicKey");

        for (Credential cred : creds) {
            CommandHelper.printResourceCommonAttributes(table, cred, this.listParams);
            if (this.listParams.isSelected("publicKey")) {
                if (cred.getPublicKey() != null) {
                    table.addCell(cred.getPublicKey().substring(0, 10) + "...");
                } else {
                    table.addCell("");
                }
            }
        }
        System.out.println(table.render());
    }
}
