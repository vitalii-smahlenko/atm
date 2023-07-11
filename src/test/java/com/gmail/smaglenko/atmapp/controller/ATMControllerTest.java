package com.gmail.smaglenko.atmapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.gmail.smaglenko.atmapp.dto.ATMDto;
import com.gmail.smaglenko.atmapp.model.ATM;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.invocation.InvocationOnMock;
import com.gmail.smaglenko.atmapp.model.Banknote;
import com.gmail.smaglenko.atmapp.service.ATMService;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.service.mapper.ATMDtoMapper;
import com.gmail.smaglenko.atmapp.service.mapper.BanknoteDtoMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ATMController.class)
class ATMControllerTest {
    private static final Long ATM_ID = 1L;
    private static final Long BANK_ACCOUNT_ID = 1L;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ATMService atmService;
    @MockBean
    private ATMDtoMapper atmDtoMapper;
    @MockBean
    private BanknoteDtoMapper banknoteDtoMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
    private ATM atm;
    private ATMDto atmDto;

    @BeforeEach
    void setUp() {
        atm = new ATM();
        atmDto = new ATMDto();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createAtm_shouldCreateAtm_whenCorrectRequest() throws Exception {
        atmDto.setId(ATM_ID);

        //Need to use InvocationOnMock, because the mapToDto() and save() methods
        // are called at the same time
        when(atmDtoMapper.mapToDto(any())).thenAnswer((InvocationOnMock invocation) -> {
            atm = invocation.getArgument(0);
            return atmDto;
        });
        when(atmService.save(any())).thenAnswer((InvocationOnMock invocation) -> {
            atm = invocation.getArgument(0);
            return atm;
        });

        mockMvc.perform(post("/atm/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"banknotes\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addMoneyToAtm_shouldAddBanknoteToATM_whenCorrectRequest() throws Exception {
        mockMvc.perform(post("/atm/add-money/atm/{atmId}", ATM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"value\": 100},{\"value\": 200},{\"value\": 500}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("ATM replenished successfully"));
    }

    @Test
    void deposit_statusCode200_whenCorrectRequest() throws Exception {
        mockMvc.perform(put("/atm/deposit/atm/{atmId}/bank-account/{bankAccountId}",
                        ATM_ID, BANK_ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"value\": 100},{\"value\": 100},{\"value\": 200},"
                                + "{\"value\": 200},{\"value\": 500},{\"value\": 500}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("The operation was successful!"));
    }

    @Test
    void withdraw_statusCode200_whenCorrectRequest() throws Exception {
        Integer amount = 1000;

        mockMvc.perform(put("/atm/withdraw/atm/{atmId}/bank-account/{bankAccountId}"
                                + "/amount/{amount}",
                        ATM_ID, BANK_ACCOUNT_ID, amount))
                .andExpect(status().isOk())
                .andExpect(content().string("The operation was successful!"));
    }

    @Test
    void getAllBanknotes_shouldReturnAllBanknotes_whenCorrectRequest() throws Exception {
        List<Banknote> banknotes = List.of(new Banknote(), new Banknote());

        when(atmService.getAllBanknotes(ATM_ID)).thenReturn(banknotes);

        mockMvc.perform(get("/atm/get-all-banknotes/{atmId}", ATM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(banknotes.size())));
    }
}
