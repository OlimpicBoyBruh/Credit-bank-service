package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanStatementRequestDto;
import ru.bank.jd.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "loanStatementRequestDto.passportSeries", target = "passport.series")
    @Mapping(source = "loanStatementRequestDto.passportNumber", target = "passport.number")
    Client requestDtoToClient(LoanStatementRequestDto loanStatementRequestDto);

    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber", target = "accountNumber")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueBranch", target = "passport.issueBranch")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueDate", target = "passport.issueDate")
    void finishRegistrationRequestDtoToClient(FinishRegistrationRequestDto finishRegistrationRequestDto, @MappingTarget Client client);
}
