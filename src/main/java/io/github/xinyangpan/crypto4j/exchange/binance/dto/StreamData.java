package io.github.xinyangpan.crypto4j.exchange.binance.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StreamData {
	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private String stream;
	private String data;

	public StreamData() {
	}
	
	public StreamData(String stream, String data) {
		super();
		this.stream = stream;
		this.data = data;
	}

	public <T> T readData(Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(data, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format("StreamData [stream=%s, data=%s]", stream, data);
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
