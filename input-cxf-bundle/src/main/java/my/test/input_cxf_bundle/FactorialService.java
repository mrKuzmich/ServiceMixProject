package my.test.input_cxf_bundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/maths/")
public class FactorialService {
  private Log LOGGER = LogFactory.getLog(FactorialService.class);

  private JmsTemplate sendJmsTemplate;
  private JmsTemplate receiveJmsTemplate;

  // todo multithreading
  @GET
  @Path("/getFactorial/{n}")
  public Response getFactorial(@PathParam("n") String strN) { // todo javax.validation
    Connection connection = null;
    try {
      LOGGER.debug(String.format("Request: getFactorial(%s)", strN));
        String msgLabel = sendRequestToQueue(strN);
        final String result = getResponseFromQueue(msgLabel);
        final String response = String.format("%s! = %s", strN, result);
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

  private String sendRequestToQueue(String request) throws JMSException {
    final String msgLabel = makeMsgLabel(request);
    getSendJmsTemplate().convertAndSend(request, message -> {
      message.setJMSCorrelationID(msgLabel);
      return message;
    });
    return msgLabel;
  }

  private String makeMsgLabel(String value) {
    return "ID:" + value;
  }

  private String getResponseFromQueue(String msgLabel) throws JMSException, TimeoutException {
    final Message response = getReceiveJmsTemplate()
            .receiveSelected("JMSCorrelationID='" + msgLabel + "'");
    if (response == null) throw new TimeoutException("Calculator not response.");
    return ((TextMessage) response).getText();
  }

  public JmsTemplate getSendJmsTemplate() {
    return sendJmsTemplate;
  }

  public void setSendJmsTemplate(JmsTemplate sendJmsTemplate) {
    this.sendJmsTemplate = sendJmsTemplate;
  }

  public JmsTemplate getReceiveJmsTemplate() {
    return receiveJmsTemplate;
  }

  public void setReceiveJmsTemplate(JmsTemplate receiveJmsTemplate) {
    this.receiveJmsTemplate = receiveJmsTemplate;
  }
}
