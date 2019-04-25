package my.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/maths/")
public class FactorialService {
    @GET
    @Path("/factorial/{n}")
    public String factorial(@PathParam("n") String strN) {
        System.out.println("Get factorial " + strN);
        return "fuck(1)";
    }
}
