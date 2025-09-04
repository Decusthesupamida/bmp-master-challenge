package com.bp3.backend.domain;

import com.bp3.backend.domain.node.Node;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The different types of {@link Node}s within a BPM process diagram.
 */
public enum NodeType {
    GATEWAY, END, HUMAN_TASK, SERVICE_TASK, START;

    @JsonCreator
    public static NodeType fromString(String key) {
        return NodeType.valueOf(key.toUpperCase());
    }
}
