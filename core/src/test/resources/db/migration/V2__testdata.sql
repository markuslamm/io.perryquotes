insert into public.source(uuid, name, shortcut) values('938685b4-8a82-49f9-acfe-25b98d07c5d3','Die Dritte Macht', 'SB1');
insert into public.source(uuid, name, shortcut) values('b82d099d-e130-4e51-bdaa-e876843f2ab3', 'Das Mutanten-Korps', 'SB2');
insert into public.source(uuid, name, shortcut) values('9ca7a65c-f865-4d63-b240-b4613d7e05f3', 'Der Unsterbliche', 'SB3');
insert into public.source(uuid, name, shortcut) values('08f44560-1b3e-443d-8c3b-02c72cadd8db', 'Der kosmische Lockvogel', 'SB4');
insert into public.source(uuid, name, shortcut) values('3e94b9f2-1c80-4bbe-8a66-0873bdf0c0e2', 'Vorstoß nach Arkon', 'SB5');

insert into public.author(uuid, name) values ('78d09d6a-11b0-4c81-9062-958a1e5099bc', 'Perry Rhodan');
insert into public.author(uuid, name) values ('d3e7ae98-7512-4f57-ba4b-ab0e1ee69403', 'Atlan');
insert into public.author(uuid, name) values ('f16b6102-ecf9-4c76-ada6-7cdda13f371f', 'Thora');
insert into public.author(uuid, name) values ('e6bf98e3-28c1-4ef5-b703-f4ad3c3d1ce8', 'Reginald Bull');
insert into public.author(uuid, name) values ('d2dbd67d-f07d-4dc4-a2ca-3b827753125e', 'Lemmy Danger');
insert into public.author(uuid, name) values ('ebd37772-d687-46e0-946d-d1245c050d87', 'Melbar Kasom');
insert into public.author(uuid, name) values ('53e25886-2322-4a10-a448-5363a60e1567', 'Icho Tolot');
insert into public.author(uuid, name) values ('31cda374-cd69-49f5-ae06-fe4cc4d8054a', 'Roy Danton');

INSERT INTO public.quote_container(uuid, state, source_uuid ) VALUES ('14d73f59-fe05-4057-b3d4-737a000ec8ad','IN_REVIEW', '938685b4-8a82-49f9-acfe-25b98d07c5d3');
-- Container 1, Perry1, Atlan2
INSERT INTO public.quote_line (uuid, content, position , author_uuid, quote_container_uuid)
values ('289300a1-2f6c-4fd0-8c98-fc258b50b5ea', 'SB1 Perry quote text', 1, '78d09d6a-11b0-4c81-9062-958a1e5099bc', '14d73f59-fe05-4057-b3d4-737a000ec8ad');

INSERT INTO public.quote_line (uuid, content, position , author_uuid, quote_container_uuid)
values ('d03114e4-3695-4a5c-9dc1-7b77515904d9', 'SB1 Atlan quote text', 2, 'd3e7ae98-7512-4f57-ba4b-ab0e1ee69403', '14d73f59-fe05-4057-b3d4-737a000ec8ad');