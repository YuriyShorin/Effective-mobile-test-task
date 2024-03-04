package ru.effectivemobile.repository;

import org.springframework.data.repository.CrudRepository;
import ru.effectivemobile.model.Phone;

import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository extends CrudRepository<Phone, UUID>  {

    Optional<Phone> findByPhone(String phone);
}
