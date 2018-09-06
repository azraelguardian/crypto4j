package io.github.xinyangpan.crypto4j.exchange.example.huobi.special;

import java.io.PrintStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messager {

	private PrintStream out;
	private String message;

	public void println() {
		out.println(message);
	}

}
