--liquibase formatted sql
--changeset <antonpavliuk>:<create-car-table>
CREATE TABLE IF NOT EXISTS public.car
(
    id bigint NOT NULL,
    reg_number character varying(255),
    year integer,
    brand_id bigint,
    model_id bigint,
    owner_id bigint,
    CONSTRAINT car_pkey PRIMARY KEY (id),
    CONSTRAINT owner_fkey FOREIGN KEY (owner_id)
        REFERENCES public.owner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT model_fkey FOREIGN KEY (model_id)
        REFERENCES public.model (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT brand_fkey FOREIGN KEY (brand_id)
        REFERENCES public.brand (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

--rollback DROP TABLE car;