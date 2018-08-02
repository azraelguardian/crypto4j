package io.github.xinyangpan.crypto4j;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class Crypto4jUtils {
	
	public static String getSecret(String secretFilePath) {
		try {
			return IOUtils.toString(new FileInputStream(secretFilePath), Charset.defaultCharset());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

