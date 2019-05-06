package my.test.input_cxf_bundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/maths/")
public class FactorialService {
    private Log LOGGER = LogFactory.getLog(FactorialService.class);
    private QueueConnectionFactory connectionFactory;

    @GET
    @Path("/getFactorial/{n}")
    public Response getFactorial(@PathParam("n") String strN) { // todo javax.validation
/*
        final Connection connection = connectionFactory.createQueueConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createProducer()
*/
        try {
            LOGGER.debug(String.format("Request: getFactorial(%s)", strN));
            final BigInteger result = getFactorial(Integer.valueOf(strN));
            final String response = String.format("%s! = %d", strN, result);
            LOGGER.debug("Response: " + response);
            return Response.ok(response).build();
        } catch (Exception e) {
            final String errorMsg = String.format("getFactorial error: %s: %s",
                    e.getClass().getCanonicalName(), e.getMessage());
            LOGGER.debug("Error: " + errorMsg);
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMsg)
                    .build();
        }
    }

    private BigInteger getFactorial(int n) {
        return Activator.getCalculator().apply(n);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

/*
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
*/
}
