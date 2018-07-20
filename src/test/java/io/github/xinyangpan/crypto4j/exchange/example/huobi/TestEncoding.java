package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

public class TestEncoding {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String sign = "+";
		System.out.println(sign);
		System.out.println(URLEncoder.encode(sign, "utf-8"));
		DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
		builderFactory.setEncodingMode(EncodingMode.NONE);
		Map<String, Object> map = new HashMap<>();
		map.put("a", "SsmIcXfR9O/kC4x8Zxa9MgMonjAqFEkWF+UDeGHFpMSs=");
		URI uri = builderFactory.expand("https://api.huobi.pro/v1/account/accounts?AccessKeyId=061b36c2-2d372ce2-dbec74c0-b52cb&SignatureMethod=HmacSHA256&SignatureVersion=2&Timestamp=2018-07-19T11:56:07&Signature={a}", map);
		System.out.println(uri);
	}
	
}
