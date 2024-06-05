package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bank.jd.dto.ScoringDataDto;
import ru.bank.jd.dto.api.FinishRegistrationRequestDto;
import ru.bank.jd.dto.api.LoanOfferDto;
import ru.bank.jd.entity.Client;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    @Mapping(source = "loanOfferDto.requestedAmount", target = "amount")
    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "client.firstName", target = "firstName")
    @Mapping(source = "client.lastName", target = "lastName")
    @Mapping(source = "client.middleName", target = "middleName")
    @Mapping(source = "client.birthdate", target = "birthdate")
    @Mapping(source = "client.passport.series", target = "passportSeries")
    @Mapping(source = "client.passport.number", target = "passportNumber")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber", target = "accountNumber")
    ScoringDataDto clientAndFinishRequestAndLoanOfferToScoringDto(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto, LoanOfferDto loanOfferDto);
}