package com.bp3.backend.model.domain.node;

import com.bp3.backend.model.domain.NodeType;
import java.util.Objects;

public class BpmNode implements Node {

    private String id;
    private String name;
    private NodeType type;

    public BpmNode() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public void setType(NodeType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmNode bpmNode)) {
            return false;
        }
        return Objects.equals(id, bpmNode.id) && Objects.equals(name, bpmNode.name)
                && type == bpmNode.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
