CREATE TABLE IF NOT EXISTS public.author
(
    uuid               uuid PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    name               CHARACTER VARYING(255)      NOT NULL UNIQUE,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS public.source
(
    uuid               uuid PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    name               CHARACTER VARYING(255)      NOT NULL UNIQUE,
    shortcut           CHARACTER VARYING(255)      NOT NULL UNIQUE,
    created_date       timestamp without time zone NOT NULL DEFAULT now(),
    last_modified_date timestamp without time zone NOT NULL DEFAULT now()
);

CREATE TYPE quote_state AS ENUM ('IN_REVIEW','COMMITTED','TWITTER_PUBLISHED');

CREATE TABLE IF NOT EXISTS public.quote_container
(
    uuid               uuid PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    state              quote_state                 NOT NULL DEFAULT 'IN_REVIEW',
    source_uuid        uuid                        NOT NULL REFERENCES public.source (uuid),
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS public.quote_line
(
    uuid                 uuid PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    content              TEXT                        NOT NULL,
    position             INT                         NOT NULL,
    author_uuid          uuid                        NOT NULL REFERENCES public.author (uuid),
    quote_container_uuid uuid                        NOT NULL REFERENCES public.quote_container (uuid) ON DELETE CASCADE, --TODO test
    created_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    last_modified_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL  DEFAULT now()
);


