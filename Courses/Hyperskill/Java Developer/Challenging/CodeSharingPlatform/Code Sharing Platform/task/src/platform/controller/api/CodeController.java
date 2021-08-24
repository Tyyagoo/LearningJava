package platform.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

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
        CodeSnippet code = codeSnippetService.getCodeSnippetById(0).orElse(null);
        return ResponseEntity.ok()
                .headers(headers)
                .body(code);
    }
}
