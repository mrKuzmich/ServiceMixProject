package my.test.input_cxf_bundle;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.IllegalFormatException;
import java.util.logging.Logger;

@Path("/maths/")
public class FactorialService {
  private Logger LOGGER = Logger.getLogger(FactorialService.class.getName());

  private JmsTemplate sendJmsTemplate;
  private JmsTemplate receiveJmsTemplate;

  // todo multithreading
  @GET
  @Path("/getFactorial/{n}")
  public Response getFactorial(@PathParam("n") String strN) { // todo javax.validation
    try {
      LOGGER.info(String.format("Request: getFactorial(%s)", strN));
      String msgLabel = sendRequestToQueue(strN);
      final String result = getResponseFromQueue(msgLabel);
      LOGGER.info(String.format("Response: %s! = %s", strN, result));
      return Response.ok(result).build();
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new WebApplicationException(e);
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
    if (response == null) throw new WebApplicationException("Calculator not response.");
    return processResponse(response);
  }

  private String processResponse(Object message) {
    try {
      // possible exception
      if (message instanceof ObjectMessage && ((ObjectMessage) message).getObject() instanceof Throwable) {
        final Throwable throwable = (Throwable) ((ObjectMessage) message).getObject();
        if (throwable instanceof IllegalArgumentException)
          throw new BadRequestException("Request parameters error", throwable);
        else
          throw new InternalServerErrorException("The processing queue returned an exception.",
                  (Throwable) ((ObjectMessage) message).getObject());
      } else if (message instanceof TextMessage) // todo split processing
        return ((TextMessage) message).getText();
      else
        throw new InternalServerErrorException("Undefined returned response");
    } catch (JMSException e) {
      throw new InternalServerErrorException("Error processing response", e);
    }
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
