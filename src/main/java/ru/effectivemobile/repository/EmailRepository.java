package ru.effectivemobile.repository;

import org.springframework.data.repository.CrudRepository;
import ru.effectivemobile.model.Email;

import java.util.Optional;
import java.util.UUID;

public interface EmailRepository extends CrudRepository<Email, UUID> {

    Optional<Email> findByEmail(String email);
}
