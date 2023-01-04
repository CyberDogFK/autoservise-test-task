--liquibase formatted sql
--changeset <antonpavliuk>:<create-owner-seq-id>
CREATE SEQUENCE IF NOT EXISTS public.owner_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.owner_id_seq;
