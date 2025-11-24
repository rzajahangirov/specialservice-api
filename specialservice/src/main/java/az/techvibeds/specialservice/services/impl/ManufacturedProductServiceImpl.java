package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.manufacturedproduct.ManufacturedProductCreateDto;
import az.techvibeds.specialservice.models.ManufacturedProduct;
import az.techvibeds.specialservice.repositories.ManufacturedProductRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ManufacturedProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ManufacturedProductServiceImpl implements ManufacturedProductService {
    private final ManufacturedProductRepository manufacturedProductRepository;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    @Override
    public ManufacturedProduct createProduct(ManufacturedProductCreateDto manufacturedProductCreateDto, String userEmail) {
        ManufacturedProduct manufacturedProduct = modelMapper.map(manufacturedProductCreateDto, ManufacturedProduct.class);
        manufacturedProduct.setCompany(companyService.findByUserEmail(userEmail));
        manufacturedProductRepository.save(manufacturedProduct);
        return manufacturedProduct;
    }
}
