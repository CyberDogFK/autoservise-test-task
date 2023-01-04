--liquibase formatted sql
--changeset <antonpavliuk>:<create-brand-seq-id>
CREATE SEQUENCE IF NOT EXISTS public.brand_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.article_id_seq;
