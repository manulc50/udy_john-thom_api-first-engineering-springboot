package org.mlorenzo.apifirst.apifirstserver;

import org.junit.jupiter.api.Test;
import org.mlorenzo.apifirst.apifirstserver.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ApifirstServerApplicationTests {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testDataLoad() {
		assertThat(customerRepository.count()).isGreaterThan(0);
	}

}
