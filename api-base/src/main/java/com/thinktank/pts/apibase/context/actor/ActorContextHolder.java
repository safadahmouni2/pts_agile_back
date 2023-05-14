package com.thinktank.pts.apibase.context.actor;

public final class ActorContextHolder {

	private ActorContextHolder() {
	}

	private static final ThreadLocal<ActorContext> CONTEXTHOLDER = new ThreadLocal<>();

	public static void setActor(ActorContext actor) {

		if (actor == null) {
			throw new IllegalArgumentException("Only non-null ActorContext instances are permitted");
		}

		CONTEXTHOLDER.set(actor);
	}

	public static ActorContext getActor() {

		ActorContext actor = CONTEXTHOLDER.get();

		if (actor == null) {
			actor = createEmptyContext();
			CONTEXTHOLDER.set(actor);
		}

		return actor;
	}

	public static ActorContext createEmptyContext() {
		return ActorContext.builder().build();
	}

	public static void removeActor() {
		CONTEXTHOLDER.remove();
	}
}
