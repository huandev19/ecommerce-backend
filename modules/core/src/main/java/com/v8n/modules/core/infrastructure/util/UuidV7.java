package com.v8n.modules.core.infrastructure.util;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

/**
 * UUID v7 generator using uuid-creator library.
 * <p>
 * UUID v7 is time-ordered (48-bit Unix timestamp in milliseconds + 74 random bits),
 * which is B-tree index friendly and reduces database fragmentation compared to UUID v4.
 * <p>
 * Usage: {@code UuidV7.generate()} instead of {@code UUID.randomUUID()}
 */
public final class UuidV7 {

    private UuidV7() {
        // Utility class - prevent instantiation
    }

    /**
     * Generate a time-ordered UUID v7.
     *
     * @return UUID v7 instance
     */
    public static UUID generate() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    /**
     * Generate a UUID v7 and return as string.
     *
     * @return UUID v7 string representation
     */
    public static String generateString() {
        return generate().toString();
    }
}