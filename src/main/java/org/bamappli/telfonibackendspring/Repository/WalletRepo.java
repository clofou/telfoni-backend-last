package org.bamappli.telfonibackendspring.Repository;


import org.bamappli.telfonibackendspring.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet, Long> {

}
