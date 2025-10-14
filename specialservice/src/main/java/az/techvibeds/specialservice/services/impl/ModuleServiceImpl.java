package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.module.ModuleCreateDto;
import az.techvibeds.specialservice.dtos.module.ModuleReadDto;
import az.techvibeds.specialservice.dtos.module.ModuleUpdateDto;
import az.techvibeds.specialservice.exceptions.ResourceNotFoundException;
import az.techvibeds.specialservice.models.Module;
import az.techvibeds.specialservice.repositories.ModuleRepository;
import az.techvibeds.specialservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModelMapper modelMapper;
    private final ModuleRepository moduleRepository;


    @Override
    public ModuleReadDto create(ModuleCreateDto createDto) {
        Module module = new Module();
        module.setName(createDto.getName());
        module.setIcon(createDto.getIcon());
        Module saved = moduleRepository.save(module);
        return mapToReadDto(saved);
    }

    @Override
    public ModuleReadDto update(ModuleUpdateDto updateDto) {
        Module module = moduleRepository.findById(updateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", updateDto.getId()));
        if (updateDto.getName() != null) module.setName(updateDto.getName());
        if (updateDto.getIcon() != null) module.setIcon(updateDto.getIcon());
        Module saved = moduleRepository.save(module);
        return mapToReadDto(saved);
    }

    @Override
    public void delete(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        moduleRepository.delete(module);
    }

    @Override
    public ModuleReadDto getById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module", "id", id));
        return mapToReadDto(module);
    }

    @Override
    public List<ModuleReadDto> getAll() {
        return moduleRepository.findAll().stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    private ModuleReadDto mapToReadDto(Module module) {
        return ModuleReadDto.builder()
                .id(module.getId())
                .name(module.getName())
                .icon(module.getIcon())
                .build();
    }
}
