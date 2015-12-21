package com.github.rmannibucau.demo.jaxrsbval;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.beans.ConstructorProperties;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("valid")
@Produces(APPLICATION_JSON)
public class ValidMyParams {
    @POST
    @ValidateOnExecution
    @Valid // valid output
    public Result post(@Valid /* valid input */ final Param param) {
        return new Result("prefix: " + param.value);
    }

    public static class Result {
        @NotNull
        @Size(min = 16)
        private String value;

        @ConstructorProperties({ "value" })
        public Result(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Param {
        @NotNull
        @Size(min = 6)
        private String value;

        @ConstructorProperties({ "value" })
        public Param(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
