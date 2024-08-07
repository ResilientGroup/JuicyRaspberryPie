package org.wensheng.juicyraspberrypie.command.handlers.world;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.wensheng.juicyraspberrypie.command.Handler;
import org.wensheng.juicyraspberrypie.command.Instruction;
import org.wensheng.juicyraspberrypie.command.SessionAttachment;

/**
 * Gets the height of the highest block at a given location.
 */
public class GetHeight implements Handler {
	/**
	 * Default GetHeight constructor.
	 */
	public GetHeight() {
	}

	@Override
	public String handle(@NotNull final SessionAttachment sessionAttachment, @NotNull final Instruction instruction) {
		final Location loc = instruction.nextLocation();
		return String.valueOf(loc.getWorld().getHighestBlockYAt(loc));
	}
}
