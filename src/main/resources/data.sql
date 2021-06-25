delete from side_effect;
delete from vaccine;
delete from user_authority;
delete from user;
delete from authority;

INSERT INTO vaccine (research_name, manufacturer_name, vaccine_type, required_number_of_shots, available_number_of_shots) VALUES
('BNT162b2', 'Pfizer-BioNTech', 'MRNA', 2, 1000),
('mRNA-1273', 'Moderna', 'MRNA', 2, 1000),
('JNJ-78436735', 'Johnson & Johnson', 'VIRAL_VECTOR', 1, 1000),
('AZD1222', 'AstraZeneca', 'VIRAL_VECTOR', 1, 1000),
('none', 'Gamaleya Research Institute', 'VIRAL_VECTOR', 1, 1000);

INSERT INTO side_effect(short_description, long_description, frequency, vaccine_id) VALUES
('Glavobolja', 'Jaka bol u prednjem dijelu glave', 3.5, 1),
('Temperatura', 'Tjelesna temperatura preko 37 stupnjava celzijusovih', 2.3, 2),
('Bol u prsima', 'Lagana bol u prsima', 4.4, 3),
('Bol u trbuhu', 'Srednja bol u trbuhu', 5.7, 4),
('Grlobolja', 'Ostra bol u grlu', 6.4, 5);
