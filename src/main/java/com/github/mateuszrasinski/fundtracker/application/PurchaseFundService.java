package com.github.mateuszrasinski.fundtracker.application;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.Registry;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryId;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryService;
import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.domain.user.UserId;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public class PurchaseFundService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private RegistryService registryService;

    @Autowired
    private RegistryRepository registryRepository;

    @Transactional
    public void registerFundPurchase(UserId userId, FundId fundId, MonetaryAmount amount, ZonedDateTime date) {
        addTransactionToRegistry(userId, fundId, amount, date);
    }

    private void addTransactionToRegistry(UserId userId, FundId fundId, MonetaryAmount amount, ZonedDateTime date) {
        User user = userRepository.find(userId).orElseThrow(RuntimeException::new);
        Fund fund = fundRepository.find(fundId).orElseThrow(RuntimeException::new);
        Registry registry = registryService.findOrCreate(user, fund);

        registry.addTransaction(amount, date);

        registryRepository.save(registry);
    }

    @Transactional
    public void addRegistryForUser(UserId userId, RegistryId registryId) {
        User user = userRepository.find(userId).orElseThrow(RuntimeException::new);

        user.addRegistry(registryId);

        userRepository.save(user);
    }
}
