package com.sk.spring.umgmt.persistance.mapper;

@FunctionalInterface
public interface DtoToEntityMapper <S,R> {
	
	R toEntity(S s );
	
	public default  R mapToEntity(DtoToEntityMapper <S,R> mapper, S s) {
		return mapper.toEntity(s);
	}

}
