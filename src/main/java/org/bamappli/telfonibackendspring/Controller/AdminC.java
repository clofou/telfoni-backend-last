package org.bamappli.telfonibackendspring.Controller;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.DTO.WalletDTO;
import org.bamappli.telfonibackendspring.Entity.Brand;
import org.bamappli.telfonibackendspring.Services.BrandService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "admin")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AdminC {

    private AdminCService adminCService;
    private BrandService brandService;

    @PostMapping(path = "client/wallet/recharge")
    void rechargerCompte(@RequestBody WalletDTO wallet) {
        adminCService.rechargerCompte(wallet);
    }

    @PostMapping(path = "client/wallet/retrait")
    void retirerDuCompte(@RequestBody WalletDTO wallet) {
        adminCService.retirerArgent(wallet);
    }

    @PostMapping(path = "brand/ajout")
    public Brand creer(@RequestBody Brand brand){
        return brandService.creer(brand);
    }
}
