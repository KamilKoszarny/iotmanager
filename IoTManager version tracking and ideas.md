##Ideas

- fix: created info disappears after modification (front only)
- Device Faults: reporting, fixing, managing, statistics
- statuses of entities: ACTIVE, DELETED, PROPOSED
- condition of Devices: PERFECT, IMPERFECT, BROKEN, IN_REPAIR
- proposing new DeviceModel, DeviceType, Producer by Users for Admin approval
- generic AuditedEntity: creationTime, createdBy, modificationTime, modifiedBy to be extended by Entities
- Producer role
- Fixer role
- deleting: behaviour like Site for other entities

####In next version

##Versions history

####0.2.3 (09.02.2021)

- show faults in Device view
- faults user access
- add buttons: Device in Site view, Fault in Device view
- audit extend: Site, Device, Fault

####0.2.2 (08.02.2021)

- show Faults in Device view
- Faults user access

###0.2.1 (07.02.2021)

- Device Faults: CRUD
- show devices in Site view
- extend: NotYourEntityException: preventing users from seeing/modifying not owned entities

####0.1.9 (06.02.2021)

- fix: routing of User after deletion/sorting of Site/Device
- fix: Site require User
- deleting Site with Devices (shown on confirm dialog)
- adding device to any site for admin
- different labels for user and admin Device/Site adding

#####0.1.8.2 (06.02.2021)

- <sub>Tests: filtering and User Entities -> 100% coverage of mine Resource and Service lines

#####0.1.8.1 (05.02.2021)

- <sub>Tests: including Roles and userId (Forbidden, NotYourEntity), filling db with csv and comparing to csv
- <sub>Lombok Getter Setter ToString for DTOs (instead of Data not compatible with EqualsVerifier tests)

####0.1.8 (31.01.2021)

- add a Device: filtering DeviceModels by Producer and DeviceType

####0.1.7 (30.01.2021)

- add a Device by User, with correct comboboxes values, including fix of access message
- User limited to viewing for: DeviceProducer, DeviceType, DeviceModel

####0.1.6 (22.01.2021)

- NotYourEntityException: preventing users from seeing/modifying not owned entities

####0.1.5 (21.01.2021)

- User Sites: showing only owned Sites for Users
- navbar localization: PL

####0.1.4 (20.01.2021)

- User Devices: showing only owned Devices for Users

####0.1.3 (19.01.2021)

- removed entity Address - moved to Site
- relationships one-way required constrains
- maxlength validations

####0.1.2 (17.01.2021)

- Site - User relationship
- added backend services to all entities
- homepage customization

###0.1.1 (16.01.2021)

- Initial build by JHipster
- configuration, database connections
- Generic CRUD of entities by JHipster
