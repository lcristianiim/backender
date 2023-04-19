package org.backender.repositories;

public interface MappingDto<D, E> {
    E dtoToEntity(D dto);
    D entityToDto(E entity);
}
