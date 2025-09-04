package com.bp3.backend.service;

import com.bp3.backend.domain.Diagram;
import com.bp3.backend.domain.NodeType;
import com.bp3.backend.domain.edge.BpmEdge;
import com.bp3.backend.domain.edge.Edge;
import com.bp3.backend.domain.node.Node;
import com.bp3.backend.exception.InvalidDiagramException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DiagramReducerServiceImpl implements DiagramReducerService {

    @Override
    public Diagram reduceDiagram(Diagram diagram) {
        List<Node> nodes = diagram.getNodes();
        validateDiagram(diagram);

        Set<String> allowedNodeIds = nodes.stream()
                .filter(n -> n.getType() == NodeType.START
                        || n.getType() == NodeType.END
                        || n.getType() == NodeType.HUMAN_TASK)
                .map(Node::getId)
                .collect(Collectors.toSet());

        List<Node> filteredNodes = nodes.stream()
                .filter(n -> allowedNodeIds.contains(n.getId()))
                .toList();

        Map<String, List<String>> adjacency = new HashMap<>();
        for (Edge edge : diagram.getEdges()) {
            adjacency.computeIfAbsent(edge.getFrom(), k -> new ArrayList<>()).add(edge.getTo());
        }

        Set<Edge> newEdges = new HashSet<>();
        for (String nodeId : allowedNodeIds) {
            buildEdges(nodeId, nodeId, adjacency, allowedNodeIds, newEdges);
        }

        return new Diagram(filteredNodes, new ArrayList<>(newEdges));
    }

    private void validateDiagram(Diagram diagram) {
        var nodes = diagram.getNodes();
        var edges = diagram.getEdges();

        if (CollectionUtils.isEmpty(nodes)) {
            throw new InvalidDiagramException("Diagram must have at least one node");
        }

        if (CollectionUtils.isEmpty(edges)) {
            throw new InvalidDiagramException("Diagram must have at least one edge");
        }

        boolean hasStart = nodes.stream().anyMatch(n -> n.getType() == NodeType.START);
        boolean hasEnd = nodes.stream().anyMatch(n -> n.getType() == NodeType.END);

        if (!hasStart || !hasEnd) {
            throw new InvalidDiagramException("Diagram must contain at least one START and one END node");
        }
    }

    private void buildEdges(String start, String current,
            Map<String, List<String>> adjacency,
            Set<String> allowedNodeIds,
            Set<Edge> resultEdges) {

        List<String> nextNodes = adjacency.get(current);
        if (nextNodes == null) return;

        for (String next : nextNodes) {
            if (allowedNodeIds.contains(next)) {
                Edge edge = new BpmEdge();
                edge.setFrom(start);
                edge.setTo(next);
                resultEdges.add(edge);
            } else {
                buildEdges(start, next, adjacency, allowedNodeIds, resultEdges);
            }
        }
    }
}
