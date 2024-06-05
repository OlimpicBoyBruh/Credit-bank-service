package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.entity.Credit;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    Credit creditDtoToCredit(CreditDto creditDto);
}
