package io.github.xinyangpan.crypto4j.chainup.example;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class ChainupHttpsExample {

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://ws.hiex.pro/kline-api/ws");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		InputStream inputStream = con.getInputStream();
		System.out.println(IOUtils.toString(inputStream, "utf8"));
	}

}
