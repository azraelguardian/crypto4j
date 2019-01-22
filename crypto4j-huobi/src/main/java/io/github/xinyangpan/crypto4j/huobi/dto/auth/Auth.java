package io.github.xinyangpan.crypto4j.huobi.dto.auth;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.hash.Hashing;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Auth {
	private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	@JsonProperty("op")
	private String op;
	@JsonProperty("AccessKeyId")
	private String accessKeyId;
	@JsonProperty("SignatureMethod")
	private String signatureMethod;
	@JsonProperty("SignatureVersion")
	private String signatureVersion;
	@JsonProperty("Timestamp")
	private String timestamp;
	@JsonProperty("Signature")
	private String signature;
	@JsonIgnore
	private String requestParam;

	@SneakyThrows
	public static Auth auth(String accessKey, String secret) {
		Auth auth = new Auth();
		auth.op = "auth";
		auth.accessKeyId = accessKey;
		auth.signatureMethod = "HmacSHA256";
		auth.signatureVersion = "2";
		auth.timestamp = LocalDateTime.now(ZoneOffset.UTC).format(PATTERN);
		auth.requestParam = String.format("AccessKeyId=%s&SignatureMethod=%s&SignatureVersion=%s&Timestamp=%s", auth.accessKeyId, auth.signatureMethod, auth.signatureVersion, URLEncoder.encode(auth.timestamp, "UTF-8").replaceAll("\\+", "%20"));
		String toSignString = "GET\napi.huobi.pro:443\n/ws/v1\n" + auth.requestParam;
		log.debug("toSignString: \n{}", toSignString);
		auth.signature = Base64.getEncoder().encodeToString(Hashing.hmacSha256(secret.getBytes()).hashBytes(toSignString.getBytes()).asBytes());
		return auth;
	}

}
