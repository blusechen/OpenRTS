package openrts.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme3.system.JmeContext;

/**
 * @author wuendsch
 */
public class ServerTest {

	public ServerTest() {
	}

	@Test
	public void testInput() throws Exception {

		OpenRTSServer app = new OpenRTSServer();
		app.start(JmeContext.Type.Headless);

		// startSecondJVM(OpenRTSServer.class, true);

		boolean scanning = true;
		while (scanning) {
			Socket socket = new Socket();
			InetSocketAddress sa = new InetSocketAddress("localhost", OpenRTSServer.PORT);
			try {
				socket.connect(sa, 500);
				boolean connected = true;
				scanning = false;
			} catch (IOException e) {
				System.out.println("Connect failed, waiting and trying again");
			}
			try {
				Thread.sleep(2000);// 2 seconds
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			socket.close();
		}

		//
		// ClientEventListenerMock obj = new ClientEventListenerMock();
		// EventManager.registerForClient(obj);
		//
		// ClientManager.startClient();
		// InputEvent evt = new InputEvent(MultiplayerGameInputInterpreter.SELECT, new Point2D(1, 1), true);
		// ClientManager.getInstance().manageEvent(evt);
		//
		// ServerEvent ev = obj.getEvent();
		// Assert.assertTrue(ev instanceof WorldChangedEvent);

	}

	private void startSecondJVM(Class<? extends Object> clazz, boolean redirectStream) throws Exception {
		System.out.println(clazz.getCanonicalName());
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
		ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath, clazz.getCanonicalName());
		processBuilder.redirectErrorStream(redirectStream);
		Process process = processBuilder.start();
		process.waitFor();
		System.out.println("Fin");
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@BeforeMethod
	public void setUpMethod() throws Exception {
	}

	@AfterMethod
	public void tearDownMethod() throws Exception {
	}
}