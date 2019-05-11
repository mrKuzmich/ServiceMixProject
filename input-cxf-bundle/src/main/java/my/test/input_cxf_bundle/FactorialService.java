package my.test.input_cxf_bundle;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/maths/")
public class FactorialService {
  private Log LOGGER = LogFactory.getLog(FactorialService.class);

  private ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
  private Connection jmsConnection;

  @GET
  @Path("/getFactorial/{n}")
  public Response getFactorial(@PathParam("n") String strN) { // todo javax.validation
    Connection connection = null;
    try {
      LOGGER.debug(String.format("Request: getFactorial(%s)", strN));
      connection = getConnectionFactory().createConnection("smx", "smx");
      try {
        connection.start();
        String msgLabel = sendRequestToQueue(connection, strN);
        final String result = getResponseFromQueue(connection, msgLabel);
        connection.stop();
        final String response = String.format("%s! = %s", strN, result);
        LOGGER.debug("Response: " + response);
        return Response.ok(response).build();
      } finally {
        if (connection != null) connection.close();
      }
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

  private String sendRequestToQueue(Connection connection, String request) throws JMSException {
    Session session = null; // todo singleton?
    try {
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      final Queue queue = session.createQueue("inputQueue");
      final MessageProducer producer = session.createProducer(queue);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      final Message msg = session.createTextMessage(request);
      // need to somehow labeled a message to filter the response
      msg.setJMSCorrelationID("ID:" + request);
      producer.send(msg);
      return msg.getJMSCorrelationID();
    } finally {
      if (session != null)
        session.close();
    }
  }

  private String getResponseFromQueue(Connection connection, String msgLabel) throws JMSException, TimeoutException {
    Session session = null;
    try {
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      final Queue queue = session.createQueue("outputQueue");
      final MessageConsumer consumer = session.createConsumer(queue, "JMSCorrelationID='" + msgLabel + "'");
      final Message response = consumer.receive(1000); // todo define variable
      if (response == null) throw new TimeoutException("Calculator not response.");
      return ((TextMessage) response).getText();
    } finally {
      if (session != null)
        session.close();
    }
  }

  public ConnectionFactory getConnectionFactory() {
    return connectionFactory;
  }
}
