package net.azzerial.shione.dialogs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.EventListener;

@SuppressWarnings("rawtypes")
public class EventWaiter implements EventListener {
	
	private final HashMap<Class<?>, Set<WaitingEvent>> waitingEvents;
	private final ScheduledExecutorService threadpool;	
	
	public EventWaiter(ScheduledExecutorService threadpool) {
		this.waitingEvents = new HashMap<>();
		this.threadpool = threadpool;
	}
	
	public <T extends Event> void addNewEvent(Class<T> classType,
							Predicate<T> condition, Consumer<T> action,
							long timeout, TimeUnit unit, Runnable timeoutAction) {
		WaitingEvent scheduled = new WaitingEvent<>(condition, action);
		Set<WaitingEvent> set = waitingEvents.computeIfAbsent(classType, c -> new HashSet<>());
		set.add(scheduled);
		
		if (timeout > 0 && unit != null) {
			threadpool.schedule(() -> {
				if (set.remove(scheduled) && timeoutAction != null) {
					timeoutAction.run();
				}
			}, timeout, unit);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(Event event) {
		Class c = event.getClass();
		
		while (c != null) {
			if (waitingEvents.containsKey(c)) {
				Set<WaitingEvent> set = waitingEvents.get(c);
				WaitingEvent[] caughtEvents = set.toArray(new WaitingEvent[set.size()]);
				
				set.removeAll(Stream.of(caughtEvents).filter(f -> f.attempt(event)).collect(Collectors.toSet()));
			}
			if (event instanceof ShutdownEvent) {
				threadpool.shutdown();
			}
			c = c.getSuperclass();
		}
	}
	
	private class WaitingEvent<T extends Event> {
		
		final Predicate<T> condition;
		final Consumer<T> action;
		
		WaitingEvent(Predicate<T> condition, Consumer<T> action) {
			this.condition = condition;
			this.action = action;
		}
		
		boolean attempt(T event) {
			if (condition.test(event)) {
				action.accept(event);
				return (true);
			}
			return (false);
		}
		
	}

}
