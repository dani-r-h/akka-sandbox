package com.drodriguezhdez.sandbox.akka.scenario1;

import com.drodriguezhdez.sandbox.akka.GreeterActor;
import com.drodriguezhdez.sandbox.akka.IEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		if (args.length == 0)
			startup(new String[] { "2551", "2552", "0" });
		else
			startup(args);
	}

	public static void startup(String[] ports) {
		for (String port : ports) {
			// Override the configuration of the port
			Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
					.withFallback(ConfigFactory.load());

			// Create an Akka system
			ActorSystem system = ActorSystem.create("ClusterSystem", config);

			// Create an actor that handles cluster domain events
			final ActorRef greeterActor = system.actorOf(Props.create(GreeterActor.class), "greeterActor");
			
			greeterActor.tell(new IEvent() {
				
				public String getId() {
					return "annonymous inner class";
				}
			}, ActorRef.noSender());
			
		}
		
	}
}
