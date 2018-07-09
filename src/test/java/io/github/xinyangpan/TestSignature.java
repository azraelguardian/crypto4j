package io.github.xinyangpan;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.base.Joiner;

public class TestSignature {
	
	public static void main(String[] args) {
//		String signature = "10c86f4348893afa639f7297c862f0fd49bdb82a";
		String token = "11abc";
		String timestamp = "1523277261";
		String nonce = "331050487";
		

		String[] strs = new String[] { token, timestamp, nonce };
		Arrays.sort(strs);
		String concat = Joiner.on("").join(strs);
		String calculateSignature = DigestUtils.sha1Hex(concat);
		System.out.println(calculateSignature);
	}

}
