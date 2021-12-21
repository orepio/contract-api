package io.orep.index;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("")
public class IndexResource {

    private static final URI uri = URI.create("/q/swagger-ui");

    @GET
    @Operation(hidden = true)
    public Response get() {
        return Response.temporaryRedirect(uri).build();
    }

}
