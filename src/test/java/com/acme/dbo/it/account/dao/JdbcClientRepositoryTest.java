package com.acme.dbo.it.account.dao;

import com.acme.dbo.account.dao.ClientRepository;
import com.acme.dbo.account.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // @JdbcTest issue
@ActiveProfiles("it")
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcClientRepositoryTest {
    @Autowired ClientRepository clients;

    @Test
    public void shouldGetAllClientsWhenPrepopulatedDb() throws SQLException {
        assertThat(clients.findAllClients()).contains(
                new Client(1L, "admin@acme.com", true),
                new Client(2L, "client@acme.com", true),
                new Client(3L, "disabled@acme.com", false)
        );
    }

    @Test
    public void shouldGetClientWhenInserted() throws SQLException {
        Long clientId = clients.save(new Client(null,"dummy@email.com", true));

        assertThat(clients.findAllClients()).contains(
                new Client(clientId, "dummy@email.com", true)
        );
    }
}
