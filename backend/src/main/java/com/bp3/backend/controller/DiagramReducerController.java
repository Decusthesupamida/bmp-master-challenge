package com.bp3.backend.controller;

import com.bp3.backend.mapper.BmpDiagramMapper;
import com.bp3.backend.model.domain.Diagram;
import com.bp3.backend.model.dto.DiagramDto;
import com.bp3.backend.service.DiagramReducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/diagrams")
public class DiagramReducerController {

    private final DiagramReducerService diagramReducerService;
    private final BmpDiagramMapper bmpDiagramMapper;

    public DiagramReducerController(DiagramReducerService diagramReducerService,
            BmpDiagramMapper bmpDiagramMapper) {
        this.diagramReducerService = diagramReducerService;
        this.bmpDiagramMapper = bmpDiagramMapper;
    }

    @PostMapping("/reduce")
    public DiagramDto reduce(@RequestBody DiagramDto dto) {
        Diagram domain = bmpDiagramMapper.toDomain(dto);

        return bmpDiagramMapper.toDto(diagramReducerService.reduceDiagram(domain));
    }
}
