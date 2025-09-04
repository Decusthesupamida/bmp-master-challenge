package com.bp3.backend.domain;

import com.bp3.backend.domain.edge.BpmEdge;
import com.bp3.backend.domain.edge.Edge;
import com.bp3.backend.domain.node.BpmNode;
import com.bp3.backend.domain.node.Node;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

public class Diagram {
    @JsonDeserialize(contentAs = BpmNode.class)
    private List<Node> nodes;
    @JsonDeserialize(contentAs = BpmEdge.class)
    private List<Edge> edges;

    public Diagram(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public Diagram() {
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
