INSERT INTO device_fault(id, name, description, urgency, device_id)
SELECT convert(id, bigint), name, description, convert(urgency, bigint), convert(device_id, bigint)
FROM CSVREAD('classpath:/config/liquibase/fake-data/device_fault.csv', null, 'fieldSeparator=;');
