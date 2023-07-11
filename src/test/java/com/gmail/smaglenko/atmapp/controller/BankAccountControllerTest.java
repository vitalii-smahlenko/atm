package com.gmail.smaglenko.atmapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.gmail.smaglenko.atmapp.dto.BankAccountDto;
import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.util.mapper.BankAccountDtoMapper;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {
    @MockBean
    private BankAccountService bankAccountService;
    @MockBean
    private BankAccountDtoMapper bankAccountDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void create_shouldCreateBankAccount_whenCorrectRequest() throws Exception {
        Long userId = 1L;
        Long bankAccountId = 1L;
        BankAccount bankAccount = new BankAccount();
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(bankAccountId);

        when(bankAccountService.create(userId)).thenReturn(bankAccount);
        when(bankAccountDtoMapper.mapToDto(bankAccount)).thenReturn(bankAccountDto);

        mockMvc.perform(post("/bank_account/create/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(bankAccountId.intValue())));
    }
}
