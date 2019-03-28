package com.ing.asia.bps3.configuration

import org.springframework.context.annotation.Import

@Import([JpaTestConfiguration, RepositoryTestConfiguration, ServiceTestConfiguration])
class BpsTestConfiguration {
}
