package platform.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import platform.model.CodeSnippet;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Integer> {
    Page<CodeSnippet> findAll(Pageable pageable);
}
