package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/rest/images")
public class ImageResource {

    // Apsolutna putanja do direktorijuma sa slikama
    private static final String IMAGE_DIR = "C:/Users/Milan/Desktop/Web Project/WebShopAppREST/src/main/resources/static/images";

    @GET
    @Path("/{filename}")
    @Produces({"image/jpeg", "image/png", "image/gif"})
    public Response getImage(@PathParam("filename") String filename) {
        File file = new File(IMAGE_DIR, filename);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        try (InputStream in = new FileInputStream(file)) {
            return Response.ok(in).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
