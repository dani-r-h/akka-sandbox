package com.drodriguezhdez.sandbox.akka;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor{

	@Override
	public void onReceive(Object obj) throws Exception {
		IEvent event = (IEvent) obj;
		System.out.println(event.getId());	
		
	}


}
