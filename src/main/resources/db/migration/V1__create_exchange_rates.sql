CREATE TABLE exchange_rates (
                                id SERIAL PRIMARY KEY,
                                base_currency VARCHAR(3) NOT NULL,
                                target_currency VARCHAR(3) NOT NULL,
                                rate DOUBLE PRECISION NOT NULL,
                                date DATE NOT NULL
);