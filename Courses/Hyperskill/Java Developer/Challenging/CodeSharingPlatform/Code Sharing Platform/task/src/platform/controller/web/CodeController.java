package platform.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

@Controller("webCodeController")
public class CodeController {

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    CodeSnippetService codeSnippetService;

    public CodeController() {
        headers.setContentType(MediaType.TEXT_HTML);
    }

    @GetMapping(path = "/code")
    public ResponseEntity<String> getCodeSnippet() {
        String html = "<html>" +
                            "<head>" +
                            "    <title>Code</title>" +
                            "</head>" +
                            "<body>" +
                            "    <pre>" +
                            codeSnippetService.getCodeSnippetById(0).map(CodeSnippet::getCode).orElse("") +
                            "</pre>" +
                            "</body>" +
                    "</html>";

        return ResponseEntity.ok()
                .headers(headers)
                .body(html);
    }
}
