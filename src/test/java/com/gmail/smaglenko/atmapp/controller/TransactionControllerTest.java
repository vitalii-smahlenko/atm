package com.gmail.smaglenko.atmapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.TransactionService;
import com.gmail.smaglenko.atmapp.service.UserService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @MockBean
    private TransactionService transactionService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void transferMoney_shouldSuccessfullyTransferMoney_whenCorrectRequest() throws Exception {
        Long sourceAccountId = 1L;
        Long destinationAccountID = 2L;
        BigDecimal amount = new BigDecimal(100);

        mockMvc.perform(put("/transaction/transfer-money/from-account/{sourceAccountId}"
                                + "/to-account/{destinationAccountID}/amount/{amount}",
                        sourceAccountId, destinationAccountID, amount))
                .andExpect(status().isOk())
                .andExpect(content().string("The operation was successful!"));
    }
}
