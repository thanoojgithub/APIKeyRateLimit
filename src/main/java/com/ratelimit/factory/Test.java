package com.ratelimit.factory;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws UnknownHostException {

		File f = new File("application.properties");
		System.out.println("Test.main() "+f.getAbsolutePath());
		System.out.println("Test.main() "+Long.parseLong(InetAddress.getLocalHost().getHostAddress().replace(".", "")));
	}

}
