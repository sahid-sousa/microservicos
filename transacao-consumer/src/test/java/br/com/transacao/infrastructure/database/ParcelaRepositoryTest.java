package br.com.transacao.infrastructure.database;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ParcelaRepositoryTest {

    @Autowired
    ParcelaRepository parcelaRepository;

}