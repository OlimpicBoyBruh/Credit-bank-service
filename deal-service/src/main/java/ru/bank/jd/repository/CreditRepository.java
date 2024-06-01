package ru.bank.jd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bank.jd.entity.Credit;
import java.util.UUID;
@Repository
public interface CreditRepository extends JpaRepository<Credit, UUID> {
}
