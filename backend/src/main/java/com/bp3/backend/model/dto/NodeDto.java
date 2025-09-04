package com.bp3.backend.model.dto;

import com.bp3.backend.model.domain.NodeType;

public record NodeDto (String id, String name, NodeType type) { }
