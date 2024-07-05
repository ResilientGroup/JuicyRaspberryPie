package org.wensheng.juicyraspberrypie.command.handlers;

import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jdk.jshell.execution.LocalExecutionControlProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.wensheng.juicyraspberrypie.command.Handler;
import org.wensheng.juicyraspberrypie.command.Instruction;
import org.wensheng.juicyraspberrypie.command.SessionAttachment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Evaluate passed Java code using JShell.
 */
public class JavaEval implements Handler {
	/** Constructor. */
	public JavaEval() {
	}

	@Override
	@SuppressWarnings("PMD.CloseResource") // Closing is done by {@link SessionAttachment#close()}.
	public String handle(@NotNull final SessionAttachment sessionAttachment, @NotNull final Instruction instruction) {
		final JShell jshell = (JShell) sessionAttachment.getContext(this).orElseThrow();
		final List<SnippetEvent> events = jshell.eval(instruction.allArguments());
		return getEvalResult(events);
	}

	private @NotNull String getEvalResult(final List<SnippetEvent> events) {
		final StringBuilder result = new StringBuilder(256);
		for (final SnippetEvent e : events) {
			if (e.causeSnippet() == null) {
				//  We have a snippet creation event
				switch (e.status()) {
					case VALID -> result.append("Successful ");
					case RECOVERABLE_DEFINED -> result.append("With unresolved references ");
					case RECOVERABLE_NOT_DEFINED -> result.append("Possibly reparable, failed ");
					case REJECTED -> result.append("Failed ");
					default -> result.append("Unknown ");
				}
				result.append(e.previousStatus() == Snippet.Status.NONEXISTENT ? "addition" : "modification")
						.append(" of ")
						.append(e.snippet().source());
				final String value = e.value();
				if (value != null) {
					result.append(": ").append(e.value());
				}
			}
		}
		return result.toString();
	}

	@Override
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	public @NotNull Optional<Object> createContext(@NotNull final JavaPlugin plugin, @NotNull final SessionAttachment sessionAttachment) {
		return Optional.of(JShell.builder().executionEngine(new LocalExecutionControlProvider(), Map.of()).build());
	}
}
