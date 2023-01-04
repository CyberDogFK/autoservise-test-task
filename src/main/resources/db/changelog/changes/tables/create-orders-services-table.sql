--liquibase formatted sql
--changeset <antonpavliuk>:<create-order-services-table>
CREATE TABLE IF NOT EXISTS public.orders_services
(
    order_id bigint NOT NULL,
    services_id bigint NOT NULL,
    CONSTRAINT service_fk FOREIGN KEY (services_id)
        REFERENCES public.service (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.order_services;
