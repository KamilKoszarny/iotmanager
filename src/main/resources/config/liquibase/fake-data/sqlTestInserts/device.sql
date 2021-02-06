INSERT INTO device(id, name, serial_no, model_id, site_id)
SELECT convert(id, bigint), name, serial_no, convert(model_id, bigint), convert(site_id, bigint)
FROM CSVREAD('classpath:/config/liquibase/fake-data/device.csv', null, 'fieldSeparator=;');
