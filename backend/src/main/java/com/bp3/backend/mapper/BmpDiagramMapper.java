package com.bp3.backend.mapper;

import com.bp3.backend.model.domain.Diagram;
import com.bp3.backend.model.domain.edge.BpmEdge;
import com.bp3.backend.model.domain.edge.Edge;
import com.bp3.backend.model.domain.node.BpmNode;
import com.bp3.backend.model.domain.node.Node;
import com.bp3.backend.model.dto.DiagramDto;
import com.bp3.backend.model.dto.EdgeDto;
import com.bp3.backend.model.dto.NodeDto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BmpDiagramMapper {

    public Diagram toDomain(DiagramDto dto) {
        if (dto == null) {
            return null;
        }

        List<Node> nodes = (dto.nodes() != null)
                ? dto.nodes().stream().map(this::toDomain).collect(Collectors.toList())
                : Collections.emptyList();

        List<Edge> edges = (dto.edges() != null)
                ? dto.edges().stream().map(this::toDomain).collect(Collectors.toList())
                : Collections.emptyList();

        return new Diagram(nodes, edges);
    }

    public DiagramDto toDto(Diagram domain) {
        if (domain == null) {
            return null;
        }

        List<NodeDto> nodeDtos = (domain.getNodes() != null)
                ? domain.getNodes().stream().map(this::toDto).collect(Collectors.toList())
                : Collections.emptyList();

        List<EdgeDto> edgeDtos = (domain.getEdges() != null)
                ? domain.getEdges().stream().map(this::toDto).collect(Collectors.toList())
                : Collections.emptyList();

        return new DiagramDto(nodeDtos, edgeDtos);
    }

    private Node toDomain(NodeDto dto) {
        if (dto == null) {
            return null;
        }
        Node domain = new BpmNode();
        domain.setId(dto.id());
        domain.setName(dto.name());
        domain.setType(dto.type());
        return domain;
    }

    private NodeDto toDto(Node domain) {
        if (domain == null) {
            return null;
        }
        return new NodeDto(domain.getId(), domain.getName(), domain.getType());
    }

    private Edge toDomain(EdgeDto dto) {
        if (dto == null) {
            return null;
        }
        Edge domain = new BpmEdge();
        domain.setFrom(dto.from());
        domain.setTo(dto.to());
        return domain;
    }

    private EdgeDto toDto(Edge domain) {
        if (domain == null) {
            return null;
        }
        return new EdgeDto(domain.getFrom(), domain.getTo());
    }

}
