package com.bp3.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bp3.backend.model.domain.Diagram;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DiagramReducerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    static Stream<Path> inputFiles() throws Exception {
        return Files.list(Path.of("src/test/resources/data/in"))
                .filter(p -> p.toString().endsWith(".json"));
    }

    @ParameterizedTest
    @MethodSource("inputFiles")
    void testReduceDiagram_withInOutFiles(Path inFile) throws Exception {
        String fileName = inFile.getFileName().toString();
        Path outFile = Path.of("src/test/resources/data/out").resolve(fileName);

        Diagram inputDiagram = mapper.readValue(inFile.toFile(), Diagram.class);
        Diagram expectedDiagram = mapper.readValue(outFile.toFile(), Diagram.class);

        String responseJson = mockMvc.perform(post("/api/diagrams/reduce")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputDiagram)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Diagram actualDiagram = mapper.readValue(responseJson, Diagram.class);

        assertThat(actualDiagram.getNodes()).containsExactlyInAnyOrderElementsOf(expectedDiagram.getNodes());
        assertThat(actualDiagram.getEdges()).containsExactlyInAnyOrderElementsOf(expectedDiagram.getEdges());
    }

}
