INSERT INTO device_model(id, name, producer_id, type_id)
SELECT convert(id, bigint), name, convert(producer_id, bigint), convert(type_id, bigint)
FROM CSVREAD('classpath:/config/liquibase/fake-data/device_model.csv', null, 'fieldSeparator=;');
