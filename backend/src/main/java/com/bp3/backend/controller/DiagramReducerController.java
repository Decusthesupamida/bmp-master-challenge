package com.bp3.backend.controller;

import com.bp3.backend.domain.Diagram;
import com.bp3.backend.service.DiagramReducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/diagrams")
public class DiagramReducerController {

    private final DiagramReducerService diagramReducerService;

    public DiagramReducerController(DiagramReducerService diagramReducerService) {
        this.diagramReducerService = diagramReducerService;
    }

    @PostMapping("/reduce")
    public Diagram reduce(@RequestBody Diagram diagram) {
        return diagramReducerService.reduceDiagram(diagram);
    }
}
