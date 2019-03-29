package com.ing.asia.bps3.configuration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@Import([JpaTestConfiguration, RepositoryTestConfiguration, ServiceTestConfiguration])
@TestConfiguration
class BpsTestConfiguration {
}
