package com.drodriguezhdez.sandbox.akka.scenario2;

import com.drodriguezhdez.sandbox.akka.GreeterActor;
import com.drodriguezhdez.sandbox.akka.IEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.japi.EventBus;
import akka.event.japi.ScanningEventBus;

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
			final ActorRef greeter = system.actorOf(Props.create(GreeterActor.class), "greeterActor");
			
			final EventBus eventBus = new ScanningEventBus<Object, ActorRef, String>() {
				@Override
				public int compareClassifiers(String a, String b) {
					return a.compareTo(b);
				}

				@Override
				public int compareSubscribers(ActorRef a, ActorRef b) {
					return a.compareTo(b);
				}

				@Override
				public boolean matches(String classifier, Object event) {
					return true;
				}

				@Override
				public void publish(Object event, ActorRef subscriber) {
					subscriber.tell(event, ActorRef.noSender());
				}

			};
			
			eventBus.subscribe(greeter, "");
			eventBus.publish(new IEvent() {
				
				public String getId() {
					return "From eventBus -- Anonymous inner class";
				}
			});
		}
		
		
	}
}
