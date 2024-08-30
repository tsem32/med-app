package com.example.javafx.rest.mapper;

import com.calendarfx.model.Entry;
import com.example.javafx.rest.dto.EntryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EntryMapper {

    public static List<Entry<?>> mapToEntryList(List<EntryDTO> dtos) {
        return dtos.stream().map(dto -> {
            Entry<?> entry = new Entry<>(dto.getDescription());
            entry.setId(dto.getId());
            entry.setInterval(LocalDateTime.of(dto.getStartDate(), dto.getStartTime()),
                               LocalDateTime.of(dto.getEndDate(), dto.getEndTime()));
            return entry;
        }).collect(Collectors.toList());
    }

    public static EntryDTO toDTO(final Entry<?> entry){
        EntryDTO entryDTO = new EntryDTO();

        entryDTO.setId(entry.getId());
        entryDTO.setDescription(entry.getTitle());
        entryDTO.setStartDate(entry.getStartDate());
        entryDTO.setEndDate(entry.getEndDate());
        entryDTO.setStartTime(entry.getStartTime());
        entryDTO.setEndTime(entry.getStartTime());

        return entryDTO;
    }
}
