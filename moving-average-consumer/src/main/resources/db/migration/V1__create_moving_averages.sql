CREATE TABLE moving_averages (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    average_price NUMERIC(20, 8) NOT NULL,
    window_size INTEGER NOT NULL,
    sample_count INTEGER NOT NULL,
    calculated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_moving_averages_symbol_time ON moving_averages (symbol, calculated_at DESC);