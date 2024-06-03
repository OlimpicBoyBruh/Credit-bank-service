package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.FinishRegistrationRequestDto;
import ru.bank.jd.dto.LoanStatementRequestDto;
import ru.bank.jd.entity.Client;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(source = "loanStatementRequestDto.passportSeries", target = "passport.series")
    @Mapping(source = "loanStatementRequestDto.passportNumber", target = "passport.number")
    Client requestDtoToClient(LoanStatementRequestDto loanStatementRequestDto);

    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber", target = "accountNumber")
    void finishRegistrationRequestDtoToClient(FinishRegistrationRequestDto finishRegistrationRequestDto, @MappingTarget Client client);
}
