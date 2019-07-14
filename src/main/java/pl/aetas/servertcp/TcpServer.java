package pl.aetas.servertcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer {

	private static Logger LOG = LoggerFactory.getLogger(TcpServer.class);

	private ServerSocket server;
	private Instant dataLastReceived = Instant.now();
	private ExpectedMessagesValidator messagesValidator = new ExpectedMessagesValidator();

	private TcpServer() throws Exception {
		this.server = new ServerSocket(6789);
	}

	private void listen() throws Exception {
		String data = null;
		Socket client = this.server.accept();
		String clientAddress = client.getInetAddress().getHostAddress();
		LOG.info("New connection from " + clientAddress);

		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		while ((data = in.readLine()) != null) {
			LOG.debug("Message from " + clientAddress + ": " + data);
			messagesValidator.validate(data);

			if (Instant.now().minusSeconds(5).isAfter(dataLastReceived)) {
				LOG.error("Last data received more than 5 seconds ago. Might be worth to check network packets");
			}

			dataLastReceived = Instant.now();
		}
	}

	private InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}

	private int getPort() {
		return this.server.getLocalPort();
	}

	public static void main(String[] args) throws Exception {
		TcpServer app = new TcpServer();
		LOG.info("Running Server: " +
			"Host=" + app.getSocketAddress().getHostAddress() +
			" Port=" + app.getPort());

		app.listen();
	}
}