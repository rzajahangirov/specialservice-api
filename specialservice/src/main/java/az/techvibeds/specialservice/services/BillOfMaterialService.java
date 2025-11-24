package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialCreateDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialReadDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialUpdateDto;

import java.security.Principal;
import java.util.List;

public interface BillOfMaterialService {
    List<BillOfMaterialReadDto> getAllByCompany(String userEmail);

    BillOfMaterialReadDto createBom(BillOfMaterialCreateDto createDto, String name);

    BillOfMaterialReadDto updateBom(Long id, BillOfMaterialUpdateDto updateDto, String name);

    void deleteById(Long id, String name);

    List<BillOfMaterialReadDto> getAllFilteredByStatus(String status, Principal principal);

    List<BillOfMaterialReadDto> getAllSearchedBom(String keyword, Principal principal);
}
