package com.archpatterns.walletmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.archpatterns.walletmanager.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Optional<Wallet> findByUserId(Long userId);

	boolean existsByUserId(Long userId);

}