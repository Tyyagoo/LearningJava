package platform.service;

import org.springframework.stereotype.Service;
import platform.model.CodeSnippet;
import java.util.Optional;

@Service
public class CodeSnippetService {
    private CodeSnippet code = new CodeSnippet(
            "public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}");


    public Optional<CodeSnippet> getCodeSnippetById(int id) {
        return Optional.of(code);
    }

    public CodeSnippet saveCodeSnippet(CodeSnippet code) {
        this.code = code;
        return this.code;
    }
}
