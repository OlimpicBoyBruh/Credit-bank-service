package ru.bank.jd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.jd.entity.Client;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
}
