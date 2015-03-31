package Controller;

public class VotingServers {

	public static void main(String[] args) {

		Server1 server1 = new Server1();
		Server2 server2 = new Server2();
		Server3 server3 = new Server3();
		BackEndServer backEnd = new BackEndServer();
		server1.start();
		server3.start();
		server2.start();
		backEnd.start();
	}

}
