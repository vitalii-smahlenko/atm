package com.gmail.smaglenko.atmapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class ATMRepositoryTest {
    private static final Long ATM_ID = 1L;
    @Autowired
    private ATMRepository atmRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    void getATMBalance_shouldReturnSumOfAllBanknotesInAtm_whenThereAreBanknotesInATM() {
        Integer expectedResult = 800;

        Integer actualResult = atmRepository.getATMBalance(ATM_ID);

        assertEquals(expectedResult, actualResult);
    }
}
