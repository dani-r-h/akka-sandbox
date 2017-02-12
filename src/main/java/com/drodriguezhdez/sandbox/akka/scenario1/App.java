package com.drodriguezhdez.sandbox.akka.scenario1;

import com.drodriguezhdez.sandbox.akka.Greeter;
import com.drodriguezhdez.sandbox.akka.IEvent;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final ActorSystem system = ActorSystem.create("helloakka");
    	final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");
    	
    	greeter.tell(new IEvent() {
			
			public String getId() {
				return "annonymous inner class";
			}
		}, ActorRef.noSender());
    }
}
