--liquibase formatted sql
--changeset <antonpavliuk>:<create-owner-table>
CREATE TABLE IF NOT EXISTS public.owner
(
    id bigint NOT NULL,
    CONSTRAINT owner_pkey PRIMARY KEY (id)
)

--rollback DROP TABLE IF EXISTS public.owner;
