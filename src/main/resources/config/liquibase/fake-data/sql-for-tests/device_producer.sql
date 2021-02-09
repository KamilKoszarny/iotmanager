INSERT INTO device_producer(id, name)
SELECT convert(id, bigint), name
FROM CSVREAD('classpath:/config/liquibase/fake-data/device_producer.csv', null, 'fieldSeparator=;');
