--liquibase formatted sql
--changeset <antonpavliuk>:<create-owner-cars-table>
CREATE TABLE IF NOT EXISTS public.owner_cars
(
    owner_id bigint NOT NULL,
    cars_id bigint NOT NULL,
    CONSTRAINT cars_fkey UNIQUE (cars_id),
    CONSTRAINT owner_fkey FOREIGN KEY (owner_id)
        REFERENCES public.owner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cars_fk FOREIGN KEY (cars_id)
        REFERENCES public.car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE IF EXISTS public.owner_cars;
