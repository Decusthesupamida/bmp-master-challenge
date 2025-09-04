package com.bp3.backend.model.dto;

import java.util.List;

public record DiagramDto (List<NodeDto> nodes, List<EdgeDto> edges) { }
