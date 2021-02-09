export interface IAbstractAuditing {
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class AbstractAuditing implements IAbstractAuditing {
  constructor(public createdBy?: string, public createdDate?: Date, public lastModifiedBy?: string, public lastModifiedDate?: Date) {}
}
