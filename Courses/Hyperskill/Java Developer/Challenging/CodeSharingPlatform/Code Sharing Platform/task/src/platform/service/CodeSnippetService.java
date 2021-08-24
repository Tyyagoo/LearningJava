package platform.service;

import org.springframework.stereotype.Service;
import platform.model.CodeSnippet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeSnippetService {

    private final List<CodeSnippet> codeSnippetList = new ArrayList<>();

    public Optional<CodeSnippet> getCodeSnippetById(int id) {
        return id >= codeSnippetList.size() ? Optional.empty() : Optional.of(codeSnippetList.get(id));
    }

    public CodeSnippet saveCodeSnippet(CodeSnippet code) {
        code.setDate(LocalDateTime.now());
        codeSnippetList.add(code);
        code.setId(codeSnippetList.size());
        return code;
    }

    public List<CodeSnippet> getLatestSnippets(int limit) {
        int upper = codeSnippetList.size() - 1;
        int lower = (limit > upper) ? 0 : upper - limit + 1;
        List<CodeSnippet> result = new ArrayList<>(upper - lower + 1);
        for (int i = upper; i >= lower; i--) {
            result.add(codeSnippetList.get(i));
        }
        return result;
    }
}
