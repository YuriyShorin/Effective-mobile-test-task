package ru.effectivemobile.repository;


import org.springframework.data.repository.CrudRepository;
import ru.effectivemobile.model.Account;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {

}
