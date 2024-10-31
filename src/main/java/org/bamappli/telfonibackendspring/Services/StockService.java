package org.bamappli.telfonibackendspring.Services;

import lombok.AllArgsConstructor;
import org.bamappli.telfonibackendspring.Entity.Stock;
import org.bamappli.telfonibackendspring.Repository.StockRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StockService implements CrudService<Long, Stock>{

    private final StockRepo stockRepo;

    @Override
    public Stock creer(Stock stock) {
        return stockRepo.save(stock);
    }

    @Override
    public Stock modifer(Long id, Stock stock) {
        Optional<Stock> stock1 = stockRepo.findById(id);

        if (stock1.isPresent()){
            Stock stockExist = stock1.get();
            if (stock.getTelephone() != null) stockExist.setTelephone(stock.getTelephone());
            if (stock.getQuantite() != null) stockExist.setQuantite(stock.getQuantite());
            return stockRepo.save(stockExist);
        }else{
            throw new IllegalArgumentException("Le Stock n'existe pas ou l'utilisateur connecte n'as pas le droit d'effectuer cette action");
        }
    }

    @Override
    public Optional<Stock> trouver(Long id) {
        return stockRepo.findById(id);
    }

    @Override
    public List<Stock> recuperer() {
        return stockRepo.findAll();
    }

    @Override
    public void supprimer(Long id) {
        stockRepo.deleteById(id);
    }
}
