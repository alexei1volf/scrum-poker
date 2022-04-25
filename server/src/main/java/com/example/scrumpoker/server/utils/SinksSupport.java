package com.example.scrumpoker.server.utils;

import reactor.core.publisher.Sinks;

public class SinksSupport {

    public static final Sinks.EmitFailureHandler RETRY_NON_SERIALIZED = (signalType, emission) -> emission == Sinks.EmitResult.FAIL_NON_SERIALIZED;
}
