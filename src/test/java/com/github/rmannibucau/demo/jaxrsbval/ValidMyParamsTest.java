package com.github.rmannibucau.demo.jaxrsbval;

import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.RandomPort;
import org.apache.openejb.testing.SimpleLog;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import java.net.URL;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.junit.Assert.assertEquals;

@SimpleLog
@EnableServices("jaxrs")
@RunWith(ApplicationComposer.class)
@Classes(cdi = true, value = ValidMyParams.class, context = "test")
public class ValidMyParamsTest {
    @RandomPort("http")
    private URL base;

    @Test
    public void valid() {
        final ValidMyParams.Result result = newClient()
            .post(Entity.entity(new ValidMyParams.Param("long enough"), APPLICATION_JSON_TYPE), ValidMyParams.Result.class);
        assertEquals("prefix: long enough", result.getValue());
    }

    @Test(expected = WebApplicationException.class)
    public void invalidInput() {
        newClient().post(Entity.entity(new ValidMyParams.Param("short"), APPLICATION_JSON_TYPE), ValidMyParams.Result.class);
    }

    @Test(expected = WebApplicationException.class)
    public void invalidOutput() {
        newClient().post(Entity.entity(new ValidMyParams.Param("just  "), APPLICATION_JSON_TYPE), ValidMyParams.Result.class);
    }

    private Invocation.Builder newClient() {
        return ClientBuilder.newBuilder().build().target(base.toExternalForm())
            .path("test/valid")
            .request();
    }
}
