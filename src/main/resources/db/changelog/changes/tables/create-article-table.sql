--liquibase formatted sql
--changeset <antonpavliuk>:<create-article-table>
CREATE TABLE IF NOT EXISTS public.article
(
    id bigint NOT NULL,
    name character varying(256) NOT NULL,
    price numeric NOT NULL,
    CONSTRAINT article_pk PRIMARY KEY (id)
);

--rollback DROP TABLE article;
