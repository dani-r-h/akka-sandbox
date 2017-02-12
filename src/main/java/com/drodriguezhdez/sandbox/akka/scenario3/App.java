package com.drodriguezhdez.sandbox.akka.scenario3;

import com.drodriguezhdez.sandbox.akka.Greeter;

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
		final ActorSystem system = ActorSystem.create("helloakka");
		final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");
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
		
		final FunctionClass functionClass = new FunctionClass();
		eventBus.publish(functionClass.apply("returns anonymous inner class using functional interface"));
	}
	
	
}
