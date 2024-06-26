package org.wensheng.juicyraspberrypie.command.handlers.events;

import org.wensheng.juicyraspberrypie.command.HandlerVoid;
import org.wensheng.juicyraspberrypie.command.Instruction;
import org.wensheng.juicyraspberrypie.command.Registry;

/**
 * Clear all event queues.
 */
public class Clear implements HandlerVoid {
	/**
	 * The registry associated with this handler.
	 */
	private final Registry registry;

	/**
	 * Create a new Clear event handler.
	 *
	 * @param registry The registry to associate with this handler.
	 */
	public Clear(final Registry registry) {
		this.registry = registry;
	}

	@Override
	public void handleVoid(final Instruction instruction) {
		registry.getHandlers().stream()
				.filter(handler -> handler instanceof EventQueue<?>)
				.map(handler -> (EventQueue<?>) handler)
				.forEach(EventQueue::clearQueue);
	}
}
