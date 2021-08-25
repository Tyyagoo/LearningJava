package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import platform.model.CodeSnippet;
import platform.repository.CodeSnippetRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeSnippetService {
    private final CodeSnippetRepository repository;

    @Autowired
    public CodeSnippetService(CodeSnippetRepository repository) {
        this.repository = repository;
    }

    public Optional<CodeSnippet> getCodeSnippetById(String id) {
        final Integer zero = 0;
        Optional<CodeSnippet> codeSnippet;
        try {
            codeSnippet = repository.findById(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            // If an invalid UUID is passed, the resource doesn't exists.
            codeSnippet = Optional.empty();
        }

        // checks if isn't expired.
        codeSnippet = codeSnippet.map(code -> {
            if (code.isViewsRestricted()) {
                if (zero.compareTo(code.getViews()) >= 0) {
                    // TODO: Delete expired snippets from database;
                    return null;
                }
            }

            if (code.isTimeRestricted()) {
                if (ChronoUnit.SECONDS.between(code.getDate(), LocalDateTime.now()) >= code.getTime()) {
                    // TODO: Delete expired snippets from database;
                    return null;
                }
            }

            return code;
        });

        codeSnippet.ifPresent(code -> {
            if (code.isViewsRestricted()) {
                code.setViews(code.getViews() - 1);
                updateSnippet(code);
            }

            if (code.isTimeRestricted()) {
                int delta = (int) ChronoUnit.SECONDS.between(code.getDate(), LocalDateTime.now());
                int time = Math.max(0, code.getTime() - delta);
                code.setTime(time);
            }
        });

        return codeSnippet;
    }

    public CodeSnippet saveCodeSnippet(CodeSnippet code) {
        code.setDate(LocalDateTime.now());
        code.checkRestrictions();
        CodeSnippet r = repository.save(code);
        System.out.println(r);
        return r;
    }

    private CodeSnippet updateSnippet(CodeSnippet code) {
        return repository.save(code);
    }

    public List<CodeSnippet> getLatestSnippets(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date"));
        return repository.findByTimeRestrictedFalseAndViewsRestrictedFalse(pageable).getContent();
    }
}
