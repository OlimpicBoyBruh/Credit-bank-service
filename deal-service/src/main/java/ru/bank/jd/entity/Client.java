package ru.bank.jd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import ru.bank.jd.converter.Passport;
import ru.bank.jd.dto.enumerated.Gender;
import ru.bank.jd.dto.enumerated.MaritalStatus;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private String accountNumber;
    @JoinColumn(name = "passportId")
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Passport passport;
}