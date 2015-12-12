package com.github.mateuszrasinski.fundtracker.application;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundId;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.domain.registry.Registry;
import com.github.mateuszrasinski.fundtracker.domain.registry.RegistryRepository;
import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.domain.user.UserId;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class PurchaseFundService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private RegistryRepository registryRepository;

    public void registerFundPurchase(UserId userId, FundId fundId, MonetaryAmount amount, ZonedDateTime date) {
        User user = userRepository.find(userId).orElseThrow(RuntimeException::new);
        Fund fund = fundRepository.find(fundId).orElseThrow(RuntimeException::new);
        Registry registry = registryRepository.findAllOfUser(user)
                                              .filter(r -> r.getFund().equals(fund))
                                              .findAny()
                                              .orElseGet(() -> createNewRegistry(user, fund));
        registry.addTransaction(amount, date);
        registryRepository.save(registry);
        userRepository.save(user);
    }

    private Registry createNewRegistry(User user, Fund fund) {
        //TODO: RegistryFactory
        Registry registry = new Registry(fund, new ArrayList<>());
        user.addRegistry(registry.identity());
        return registry;
    }
}
