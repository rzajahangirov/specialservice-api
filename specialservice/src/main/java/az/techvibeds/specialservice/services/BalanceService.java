package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.balace.CreateBalanceDto;
import az.techvibeds.specialservice.models.Balance;
import az.techvibeds.specialservice.models.Partner;

public interface BalanceService {
    Balance createBalance(CreateBalanceDto balanceDto);

}
