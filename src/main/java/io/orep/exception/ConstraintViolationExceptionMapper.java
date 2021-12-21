package io.orep.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<Error> errors = new ArrayList<>();
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST).entity(errors);
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(new Error(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        });
        return responseBuilder.build();
    }

}
