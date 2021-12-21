CREATE TABLE contract
(
    contract_id SERIAL PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    execution_date DATE
);
ALTER SEQUENCE contract_contract_id_seq RESTART 1000000;