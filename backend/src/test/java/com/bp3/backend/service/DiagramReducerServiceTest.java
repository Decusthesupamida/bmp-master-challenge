package com.bp3.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bp3.backend.domain.Diagram;
import com.bp3.backend.domain.NodeType;
import com.bp3.backend.domain.edge.Edge;
import com.bp3.backend.domain.node.Node;
import com.bp3.backend.exception.InvalidDiagramException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiagramReducerServiceTest {

    private final DiagramReducerServiceImpl reducerService = new DiagramReducerServiceImpl();

    @Test
    void testServiceReductionWithMocks() {
        List<Node> nodes = List.of(
                mockNode("0", NodeType.START),
                mockNode("1", NodeType.SERVICE_TASK),
                mockNode("2", NodeType.HUMAN_TASK),
                mockNode("4", NodeType.HUMAN_TASK),
                mockNode("5", NodeType.END)
        );

        List<Edge> edges = List.of(
                mockEdge("0", "1"),
                mockEdge("1", "2"),
                mockEdge("2", "4"),
                mockEdge("4", "5")
        );

        Diagram diagram = new Diagram(nodes, edges);

        Diagram reduced = reducerService.reduceDiagram(diagram);

        assertEquals(4, reduced.getNodes().size());
        assertEquals(3, reduced.getEdges().size());
    }

    @Test
    void testServiceReductionWithMocksWithoutStart() {
        List<Node> nodes = List.of(
                mockNode("0", NodeType.SERVICE_TASK),
                mockNode("1", NodeType.HUMAN_TASK),
                mockNode("2", NodeType.HUMAN_TASK),
                mockNode("4", NodeType.END)
        );

        List<Edge> edges = List.of(
                mockEdge("0", "1"),
                mockEdge("1", "2"),
                mockEdge("2", "4")
        );

        Diagram diagram = new Diagram(nodes, edges);

        assertThrows(InvalidDiagramException.class, () -> reducerService.reduceDiagram(diagram));
    }

    @Test
    void testServiceReductionWithEmptyNodes() {
        List<Edge> edges = List.of(
                mockEdge("0", "1"),
                mockEdge("1", "2"),
                mockEdge("2", "4"),
                mockEdge("4", "5")
        );

        Diagram diagram = new Diagram(new ArrayList<>(), edges);

        assertThrows(InvalidDiagramException.class, () -> reducerService.reduceDiagram(diagram));
    }

    private Node mockNode(String id, NodeType type) {
        Node node = mock(Node.class);
        when(node.getId()).thenReturn(id);
        when(node.getType()).thenReturn(type);
        return node;
    }

    private Edge mockEdge(String from, String to) {
        Edge edge = mock(Edge.class);
        when(edge.getFrom()).thenReturn(from);
        when(edge.getTo()).thenReturn(to);
        return edge;
    }

}
