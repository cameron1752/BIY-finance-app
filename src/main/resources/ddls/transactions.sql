-- Table: test.transactions

-- DROP TABLE IF EXISTS test.transactions;

CREATE TABLE IF NOT EXISTS test.transactions
(
    account_id BIGINT NOT NULL,
    transaction_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date date,
    category character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    amount double precision,
    pending boolean,
    transaction_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT transactions_pkey PRIMARY KEY (account_id, transaction_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS test.transactions
    OWNER to postgres;