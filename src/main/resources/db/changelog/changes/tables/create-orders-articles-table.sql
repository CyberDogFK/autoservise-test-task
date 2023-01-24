--liquibase formatted sql
--changeset <antonpavliuk>:<create-master-solved-orders-table>
CREATE TABLE IF NOT EXISTS public.orders_articles
(
    order_id bigint NOT NULL,
    articles_id bigint NOT NULL,
    CONSTRAINT article_fkey FOREIGN KEY (articles_id)
        REFERENCES public.article (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.order_articles;
