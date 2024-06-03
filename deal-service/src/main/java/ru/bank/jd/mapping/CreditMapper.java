package ru.bank.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.bank.jd.dto.CreditDto;
import ru.bank.jd.entity.Credit;

@Mapper
public interface CreditMapper {
    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    Credit creditDtoToCredit(CreditDto creditDto);
}
