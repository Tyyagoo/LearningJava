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
import platform.exception.ResourceNotFoundException;
import platform.model.CodeSnippet;
import platform.service.CodeSnippetService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("webCodeController")
public class CodeController {

    @Autowired
    CodeSnippetService codeSnippetService;

    @GetMapping(path = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getSetupPage(Model model) {
        return "commit";
    }

    @GetMapping(path = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCodeSnippet(Model model, @PathVariable String id) {
        CodeSnippet code = codeSnippetService.getCodeSnippetById(id)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("code", code);
        return "code";
    }

    @GetMapping(path = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestSnippets(Model model) {
        List<CodeSnippet> list = codeSnippetService.getLatestSnippets(10);
        model.addAttribute("snippets", list);
        return "latest";
    }
}
