package com.test.gravahal;

import com.codahale.metrics.health.HealthCheck;

public class GameHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
