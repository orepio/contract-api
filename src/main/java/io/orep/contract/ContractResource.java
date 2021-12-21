package io.orep.contract;

import io.orep.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@Slf4j
public class ContractResource {

    private final ContractService contractService;

    @GET
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Get All Contracts",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.ARRAY, implementation = Contract.class)))
            }
    )
    public Response get() {
        return Response.ok(contractService.findAll()).build();
    }

    @GET
    @Path("/{contractId}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Get Contract by contractId",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Contract.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "No Contract found for contractId provided",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response getById(@PathParam("contractId") Integer contractId) {
        Optional<Contract> optional = contractService.findById(contractId);
        return !optional.isEmpty() ? Response.ok(optional.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Contract Created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Contract.class))),
                    @APIResponse(
                            responseCode = "400",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response post(@Valid @NotNull Contract contract, @Context UriInfo uriInfo) {
        contract = contractService.save(contract);
        return Response
                .created(URI.create(String.format("%s/%s", uriInfo.getRequestUri(), contract.getContractId())))
                .entity(contract)
                .build();
    }

    @PUT
    @Path("/{contractId}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Contract updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = Contract.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "No Contract found for contractId provided",
                            content = @Content(mediaType = "application/json")),
            }
    )
    public Response put(@PathParam("contractId") Integer contractId, @Valid Contract contract) {
        if (!contract.getContractId().equals(contractId)){
            throw new ServiceException("Contract.contractId does not equal contractId path parameter");
        }
        final Contract saved = this.contractService.update(contract);
        return Response.ok(saved).build();
    }

}