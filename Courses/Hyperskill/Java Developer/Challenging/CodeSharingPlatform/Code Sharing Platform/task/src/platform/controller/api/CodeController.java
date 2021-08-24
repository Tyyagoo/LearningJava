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

    @GetMapping
    public ResponseEntity<CodeSnippet> getCodeSnippet() {
        Optional<CodeSnippet> code = codeSnippetService.getCodeSnippetById(0);
        return code
                .map(codeSnippet -> ResponseEntity.ok().headers(headers).body(codeSnippet))
                .orElseGet(() -> new ResponseEntity<>(headers, HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<?> createCodeSnippet(@RequestBody CodeSnippet code) {
        code.setDate(LocalDateTime.now());
        codeSnippetService.saveCodeSnippet(code);
        return ResponseEntity.ok().headers(headers).body(new EmptyJsonResponse());
    }

    @JsonSerialize
    public static class EmptyJsonResponse { }
}
