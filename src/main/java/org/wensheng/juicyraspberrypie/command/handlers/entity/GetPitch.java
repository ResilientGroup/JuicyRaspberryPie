package org.wensheng.juicyraspberrypie.command.handlers.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.wensheng.juicyraspberrypie.command.Handler;
import org.wensheng.juicyraspberrypie.command.Instruction;
import org.wensheng.juicyraspberrypie.command.SessionAttachment;
import org.wensheng.juicyraspberrypie.command.entity.EntityProvider;

/**
 * Get the tile of an entity.
 */
public class GetPitch implements Handler {
	/**
	 * The entity provider associated with this handler.
	 */
	private final EntityProvider entityProvider;

	/**
	 * Create a new GetPitch event handler.
	 *
	 * @param entityProvider The entity provider to associate with this handler.
	 */
	public GetPitch(final EntityProvider entityProvider) {
		this.entityProvider = entityProvider;
	}

	@Override
	public String handle(@NotNull final SessionAttachment sessionAttachment, @NotNull final Instruction instruction) {
		final Entity entity = entityProvider.getEntity(sessionAttachment, instruction);
		return String.valueOf(entity.getLocation().getPitch());
	}
}
