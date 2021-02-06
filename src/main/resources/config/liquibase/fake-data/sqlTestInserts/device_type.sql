INSERT INTO device_type(id, name, category)
SELECT convert(id, bigint), name, category
FROM CSVREAD('classpath:/config/liquibase/fake-data/device_type.csv', null, 'fieldSeparator=;');
