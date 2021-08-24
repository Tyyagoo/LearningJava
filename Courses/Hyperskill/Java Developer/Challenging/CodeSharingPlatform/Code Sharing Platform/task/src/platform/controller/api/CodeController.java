package platform.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController("apiCodeController")
@RequestMapping(path = "${v1API}/code")
public class CodeController {

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    CodeSnippetService codeSnippetService;

    public CodeController() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CodeSnippet> getCodeSnippet(@PathVariable Integer id) {
        Optional<CodeSnippet> code = codeSnippetService.getCodeSnippetById(id - 1);
        return code
                .map(codeSnippet -> ResponseEntity.ok().headers(headers).body(codeSnippet))
                .orElseGet(() -> new ResponseEntity<>(headers, HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/latest")
    public ResponseEntity<List<CodeSnippet>> getLatestSnippets() {
        return ResponseEntity.ok()
                .headers(headers)
                .body(codeSnippetService.getLatestSnippets(10));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<IdResponse> createCodeSnippet(@RequestBody CodeSnippet code) {
        code = codeSnippetService.saveCodeSnippet(code);
        return ResponseEntity.ok().headers(headers).body(new IdResponse(String.valueOf(code.getId())));
    }

    @JsonSerialize
    public static class IdResponse {
        private String id;

        public IdResponse() {}

        public IdResponse(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
