INSERT INTO device_fault(id, name, description, urgency, device_id, created_by)
SELECT convert(id, bigint), name, description, convert(urgency, bigint), convert(device_id, bigint), created_by
FROM CSVREAD('classpath:/config/liquibase/fake-data/device_fault.csv', null, 'fieldSeparator=;');
