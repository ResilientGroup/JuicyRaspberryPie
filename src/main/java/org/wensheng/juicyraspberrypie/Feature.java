package org.wensheng.juicyraspberrypie;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Product features that can be enabled or disabled.
 * In production, system properties (e.g. {@code -Dfeature.FROBNIZER=true}) are used to enable or disable features.
 * Automated tests can also override the feature state for the duration of a test.
 */
public enum Feature {
	/** Evaluate passed Java code using JShell. */
	JAVA_EVAL(false);

	/** The per-thread override tri-state of the feature. */
	private static final ThreadLocal<Boolean> OVERRIDE = new ThreadLocal<>();

	/** Flag whether the feature is active by default. */
	private final boolean activeByDefault;

	/** Constructor. */
	Feature(final boolean activeByDefault) {
		this.activeByDefault = activeByDefault;
	}

	/** Return whether the feature is currently active. */
	public boolean isActive() {
		final Boolean override = OVERRIDE.get();
		return override == null
				? Boolean.parseBoolean(System.getProperty("feature." + name(), Boolean.toString(activeByDefault)))
				: override;
	}

	/** (Temporarily) enable the feature for the current thread. Note that it's safer to use the {@code while...()} methods, as they
	 * automatically reset. */
	public void enable() {
		OVERRIDE.set(true);
	}

	/** (Temporarily) disable the feature for the current thread. Note that it's safer to use the {@code while...()} methods, as they
	 *  automatically reset. */
	public void disable() {
		OVERRIDE.set(false);
	}

	/** Restore the feature to its default state for the current thread after a (temporary) override via {@link #enable()} or
	 *  {@link #disable()}. */
	public void reset() {
		OVERRIDE.remove();
	}

	/** Run the given runnable while the feature state is overridden. */
	public void whileSet(final boolean enable, @NotNull final Runnable runnable) {
		whileSet(enable, () -> {
			runnable.run();
			return null;
		});
	}

	/** Run the given supplier while the feature state is overridden for the current thread. */
	@SuppressFBWarnings("ME_ENUM_FIELD_SETTER") // temporary modification of something that does not represent the enum's identity
	public <T> T whileSet(final boolean enable, @NotNull final Supplier<T> supplier) {
		OVERRIDE.set(enable);
		try {
			return supplier.get();
		} finally {
			reset();
		}
	}

	/** Run the given runnable while the feature is enabled for the current thread. */
	public void whileEnabled(@NotNull final Runnable runnable) {
		whileSet(true, runnable);
	}

	/** Run the given supplier while the feature is enabled for the current thread. */
	public <T> T whileEnabled(@NotNull final Supplier<T> supplier) {
		return whileSet(true, supplier);
	}

	/** Run the given runnable while the feature is disabled for the current thread. */
	public void whileDisabled(@NotNull final Runnable runnable) {
		whileSet(false, runnable);
	}

	/** Run the given supplier while the feature is disabled for the current thread. */
	public <T> T whileDisabled(@NotNull final Supplier<T> supplier) {
		return whileSet(false, supplier);
	}
}
