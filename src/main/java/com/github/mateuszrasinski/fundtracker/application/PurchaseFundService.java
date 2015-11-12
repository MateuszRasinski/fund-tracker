package com.github.mateuszrasinski.fundtracker.application;

import com.github.mateuszrasinski.fundtracker.domain.fund.Fund;
import com.github.mateuszrasinski.fundtracker.domain.fund.FundRepository;
import com.github.mateuszrasinski.fundtracker.domain.user.User;
import com.github.mateuszrasinski.fundtracker.domain.user.UserRepository;
import com.github.mateuszrasinski.fundtracker.publishedlanguage.Identity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public class PurchaseFundService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundRepository fundRepository;

    public void registerFundPurchase(Identity userId, Identity fundId, MonetaryAmount amount, ZonedDateTime date) {
        User user = userRepository.find(userId).orElseThrow(RuntimeException::new);
        Fund fund = fundRepository.find(fundId).orElseThrow(RuntimeException::new);
        user.registerFundPurchase(fund, amount, date);
        userRepository.save(user);
    }
}