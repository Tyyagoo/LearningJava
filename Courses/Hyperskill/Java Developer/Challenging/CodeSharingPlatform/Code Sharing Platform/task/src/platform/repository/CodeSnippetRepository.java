package platform.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import platform.model.CodeSnippet;

import java.util.UUID;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, UUID> {
    Page<CodeSnippet> findByTimeRestrictedFalseAndViewsRestrictedFalse(Pageable pageable);
}
