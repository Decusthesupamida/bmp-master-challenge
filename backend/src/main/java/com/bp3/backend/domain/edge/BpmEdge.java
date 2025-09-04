package com.bp3.backend.domain.edge;

import java.util.Objects;

public class BpmEdge implements Edge {

    private String from;
    private String to;

    public BpmEdge() {}

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public void setFrom(String nodeId) {
        this.from = nodeId;
    }

    @Override
    public void setTo(String nodeId) {
        this.to = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmEdge bpmEdge)) {
            return false;
        }
        return Objects.equals(from, bpmEdge.from) && Objects.equals(to, bpmEdge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
