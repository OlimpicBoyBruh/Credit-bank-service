package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bank.jd.dto.api.StatementDto;
import ru.bank.jd.entity.Statement;

@Mapper(componentModel = "spring")
public interface StatementMapper {

    @Mapping(source = "client.firstName", target = "firstName")
    @Mapping(source = "client.middleName", target = "middleName")
    @Mapping(source = "client.lastName", target = "lastName")
    @Mapping(source = "client.birthdate", target = "birthdate")
    @Mapping(source = "client.passport", target = "passport")
    @Mapping(source = "creditId.creditId", target = "creditId")
    @Mapping(source = "creditId.amount", target = "amount")
    @Mapping(source = "creditId.term", target = "term")
    @Mapping(source = "creditId.monthlyPayment", target = "monthlyPayment")
    @Mapping(source = "creditId.rate", target = "rate")
    @Mapping(source = "creditId.psk", target = "psk")
    @Mapping(source = "creditId.paymentSchedule", target = "paymentSchedule")
    StatementDto statementToStatementDto(Statement statement);
}
