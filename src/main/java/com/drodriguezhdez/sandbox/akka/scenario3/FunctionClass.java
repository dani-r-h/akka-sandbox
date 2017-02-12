package com.drodriguezhdez.sandbox.akka.scenario3;

import java.util.function.Function;

import com.drodriguezhdez.sandbox.akka.IEvent;

public class FunctionClass implements Function<String, IEvent> {

	public IEvent apply(final String id) {
		return new IEvent() {
			
			public String getId() {
				return id;
			}
		};
	}

}
