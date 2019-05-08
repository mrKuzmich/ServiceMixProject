package my.test.input_cxf_bundle;

import my.test.calc_bundle.calculator.FactorialCalculatorImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/maths/")
public class FactorialService {
    private Log LOGGER = LogFactory.getLog(FactorialService.class);

    private ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    private Connection jmsConnection;

    @GET
    @Path("/getFactorial/{n}")
    public Response getFactorial(@PathParam("n") String strN) { // todo javax.validation
        try {
            LOGGER.debug(String.format("Request: getFactorial(%s)", strN));
            sendRequestToQueue(strN);
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

    private void sendRequestToQueue(String request) throws JMSException {
        final Connection connection = getConnectionFactory().createConnection("smx", "smx");
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Queue queue = session.createQueue("inputQueue");
        final MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        final Message msg = session.createTextMessage(request);
        producer.send(msg);
        session.close();
    }

    private String getResponseFromQueue() {
        return null;
    }

    private BigInteger getFactorial(int n) {
        //return Activator.getCalculator().apply(n);
        return new FactorialCalculatorImpl().apply(n);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Connection getJmsConnection() {
        return jmsConnection;
    }

    public void setJmsConnection(Connection jmsConnection) {
        this.jmsConnection = jmsConnection;
    }
}
