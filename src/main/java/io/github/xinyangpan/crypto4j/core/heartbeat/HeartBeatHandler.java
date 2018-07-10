package io.github.xinyangpan.crypto4j.core.heartbeat;

public interface HeartBeatHandler {

	void sendPing();

	void pingTimeout();

}
