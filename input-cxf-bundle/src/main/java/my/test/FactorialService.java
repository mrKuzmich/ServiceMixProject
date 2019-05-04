package my.test;

import my.test.calculator.FactorialCalculator;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.math.BigInteger;

@Path("/maths/")
public class FactorialService {
    private QueueConnectionFactory connectionFactory;

    @GET
    @Path("/getFactorial/{n}")
    public String getFactorial(@PathParam("n") String strN) {
        System.out.println("Get factorial " + strN);
/*
        final Connection connection = connectionFactory.createQueueConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.createProducer()
*/
        return String.format("%s! = %d", strN, getFactorial(Integer.valueOf(strN)));
    }

    private BigInteger getFactorial(int n) {
/*
        return n <= 1 ? BigInteger.ONE : BigInteger.valueOf(n).multiply(getFactorial(n -1));
*/
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
