package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.PassportDto;
import ru.bank.jd.dto.FinishRegistrationRequestDto;

@Mapper
public interface PassportDtoMapper {
    PassportDtoMapper INSTANCE = Mappers.getMapper(PassportDtoMapper.class);

    @Mapping(source = "finishRegistrationRequestDto.passportIssueDate", target = "issueDate")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueBranch", target = "issueBranch")
    void updatePassportFromRequestDto(FinishRegistrationRequestDto finishRegistrationRequestDto, @MappingTarget PassportDto passportDto);
}
