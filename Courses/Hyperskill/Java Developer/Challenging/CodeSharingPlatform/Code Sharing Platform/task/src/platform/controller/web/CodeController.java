package platform.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller("webCodeController")
public class CodeController {

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    CodeSnippetService codeSnippetService;

    public CodeController() {
        headers.setContentType(MediaType.TEXT_HTML);
    }

    @GetMapping(path = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getSetupPage(Model model) {
        return "commit";
    }

    @GetMapping(path = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCodeSnippet(Model model, @PathVariable Integer id) {
        CodeSnippet code = codeSnippetService.getCodeSnippetById(id - 1)
                .orElse(new CodeSnippet("404 - Not Found"));
        model.addAttribute("code", code);
        return "code";
    }

    @GetMapping(path = "/code/latest")
    public String getLatestSnippets(Model model) {
        List<CodeSnippet> list = codeSnippetService.getLatestSnippets(10);
        model.addAttribute("snippets", list);
        return "latest";
    }
}
