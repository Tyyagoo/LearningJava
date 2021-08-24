package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import platform.model.CodeSnippet;
import platform.repository.CodeSnippetRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeSnippetService {
    private final CodeSnippetRepository repository;

    @Autowired
    public CodeSnippetService(CodeSnippetRepository repository) {
        this.repository = repository;
    }

    public Optional<CodeSnippet> getCodeSnippetById(int id) {
        return repository.findById(id);
    }

    public CodeSnippet saveCodeSnippet(CodeSnippet code) {
        code.setDate(LocalDateTime.now());
        return repository.save(code);
    }

    public List<CodeSnippet> getLatestSnippets(int limit) {
        return repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")))
                .getContent();
    }
}
